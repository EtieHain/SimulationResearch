package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import static com.example.simulationresearch.InterfaceController.*;
import static com.example.simulationresearch.HelloApplication.*;


public class HelloController {
    @FXML
    private Canvas myCanvas;                //Variable du canvas ou serra affiché la simulation
    @FXML
    private Label lbl_Timmer;               //Variable qui affichera le temps de simulation (valeur du Timmer)
    @FXML
    private AnchorPane ap;
    @FXML
    private ScrollPane sp;


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

        //Code qui affiche la valeur du Timmer dans son label
        float RescherchTime = 0.00f;            //Créer une variable float
        //Stock la variable du Timmer arrondie à deux dixième dans la variable créer avant
        RescherchTime = Math.round((simulationTime/60)*100.0f)/100.0f;
        //Change le texte du label avec la valeur du Timmer
        lbl_Timmer.setText(String.valueOf(RescherchTime));

        if (NbrFound >= GestionObjects.NbrObjectif) {
            InterfaceController.Situation = 3;
//            InterfaceController.btnStart.setDisable(true);
            lbl_Timmer.setText(String.valueOf("Target found in " + RescherchTime));
        }

    }


    public void AffichageStop (Image BackGround){
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        GestionObjects.Affichage(gc, BackGround);
    }
}