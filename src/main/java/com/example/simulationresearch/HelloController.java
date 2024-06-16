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

/**
 * Controller class of our application
 */
public class HelloController {
    @FXML
    public Canvas myCanvas;                //Variable du canvas ou serra affiché la simulation
    @FXML
    private Label lbl_Timmer;               //Variable qui affichera le temps de simulation (valeur du Timmer)
    @FXML
    private AnchorPane ap;                 //pane contenant les élément

    static public int nbrImg = 0; //variable serant a lexport
    static public float lastTime = 0f; //variable servant a lexport
    private File[] files; //variable servant à lexport

    static public float ResearchTime = 0.00f; //variable de calcul du temps de simul en fonction du nbr de frame
    static boolean SimulationDone = false; //variabel d'état de la simul

    /**
     * This Method displays the elements on the canvas and make the element move
     * @param BackGround
     */
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
        ResearchTime = Math.round(((float) frameCount /60)*100.0f)/100.0f;
        //Change le texte du label avec la valeur du Timmer
        lbl_Timmer.setText(String.valueOf(ResearchTime));

        //test de fin de simul si cible trouvée
        if (NbrFound >= GestionObjects.NbrObjectif) {
            SimulationDone = true;
            InterfaceController.Situation = 3;
            lbl_Timmer.setText(String.valueOf("Found in " + ResearchTime + " sec."));
        }
        //condition si temnps écoulé
        else if (ResearchTime>=60){
            SimulationDone = true;
            InterfaceController.Situation = 3;
            lbl_Timmer.setText(String.valueOf("Target not found !"));
        }

        //Test si 3 frames sont passées
        if(frameCount - lastTime > 2)
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
            lastTime = frameCount;
        }
    }

    /**
     * this method is called to display the elements when nothing has to move (pause or finished)
     * @param BackGround
     */
    public void AffichageStop (Image BackGround){
        myCanvas.setWidth(ConfigReading.dimensionCaneva[0]);
        myCanvas.setHeight(ConfigReading.dimensionCaneva[1]);
        myCanvas.setLayoutX(ap.getWidth()/2-myCanvas.getWidth()/2);
        myCanvas.setLayoutY(ap.getHeight()/2-myCanvas.getHeight()/2);

        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        GestionObjects.Affichage(gc, BackGround);
    }
}