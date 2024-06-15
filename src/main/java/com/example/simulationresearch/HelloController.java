package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import SwingFXUtils.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static com.example.simulationresearch.HelloApplication.*;


public class HelloController {
    @FXML
    public Canvas myCanvas;                //Variable du canvas ou serra affiché la simulation
    @FXML
    private Label lbl_Timmer;               //Variable qui affichera le temps de simulation (valeur du Timmer)
    @FXML
    private AnchorPane ap;
    @FXML
    private ScrollPane sp;
    static public int nbrImg = 0;
    static public float lastTime = 0f;
    private File[] files;
    static public float ResearchTime = 0.00f;


    public void Afficher(Image BackGround) {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        int NbrFound = 0;
        for (int idx = 0; idx < GestionObjects.NbrAgent; idx++) {
            if (GestionObjects.agents[idx].getState()[0] && !GestionObjects.agents[idx].getState()[1])
                GestionObjects.testCommunication(idx);
            else GestionObjects.agents[idx].targetDetection();
            if (!GestionObjects.agents[idx].getState()[0] || !GestionObjects.agents[idx].getState()[1])
                GestionObjects.agents[idx].Deplacement();
            if (GestionObjects.agents[idx].getState()[0]) NbrFound++;
        }

        GestionObjects.Affichage(gc, BackGround);

        //Code qui affiche la valeur du Timer dans son label
        //Stock la variable du Timmer arrondie à deux dixième dans la variable créer avant
        ResearchTime = Math.round((simulationTime/60)*100.0f)/100.0f;
        //Change le texte du label avec la valeur du Timmer
        lbl_Timmer.setText(String.valueOf(ResearchTime));

        if (NbrFound >= GestionObjects.NbrObjectif) {
            InterfaceController.Situation = 3;
            lbl_Timmer.setText(String.valueOf("Target found in " + ResearchTime));
        }

        //Test si 3 frames sont passées
        if(simulationTime - lastTime > 1)
        {
            // Créer une WritableImage pour capturer le contenu du Canvas
            WritableImage writableImage = new WritableImage((int) myCanvas.getWidth(), (int) myCanvas.getHeight());
            myCanvas.snapshot(null, writableImage);

            // Création un Task pour la création et l'enregistrement de l'image
            Task<Void> createImageTask = new Task<>() {
                @Override
                protected Void call() {

                    // Enregistrer l'image dans un fichier
                    File file = new File(  "Images/" +String.format("%04d",nbrImg) + ".png");
                    nbrImg++;
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                    } catch (IOException e) {
                        System.err.println("Erreur lors de l'enregistrement de l'image : " + e.getMessage());
                    }
                    return null;
                }
                //Fonction si la task ne s'effectue pas
                @Override
                protected void failed() {
                    super.failed();
                    System.out.println("erreur de création de l'image");
                }
            };

            // Exécuter le Task dans un autre thread
            new Thread(createImageTask).start();
            lastTime = simulationTime;
        }
    }


    public void AffichageStop (Image BackGround){
        myCanvas.setWidth(ConfigReading.dimensionCaneva[0]);
        myCanvas.setHeight(ConfigReading.dimensionCaneva[1]);
        myCanvas.setLayoutX(ap.getWidth()/2-myCanvas.getWidth()/2);
        myCanvas.setLayoutY(ap.getHeight()/2-myCanvas.getHeight()/2);

        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        GestionObjects.Affichage(gc, BackGround);
    }
}