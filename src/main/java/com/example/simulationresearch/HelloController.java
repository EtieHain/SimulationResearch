package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Canvas myCanvas;
    @FXML
    private Label lbl_Timmer;
    @FXML
    private AnchorPane ap;
    @FXML
    private ScrollPane sp;


    public void Afficher(Image BackGround) {
        myCanvas.setWidth(ConfigReading.dimensionCaneva[0]);
        myCanvas.setHeight(ConfigReading.dimensionCaneva[1]);
        myCanvas.setLayoutX((ap.getWidth() - myCanvas.getWidth()) / 2);
        myCanvas.setLayoutY((ap.getHeight() - myCanvas.getHeight()) / 2);

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
        if (NbrFound >= GestionObjects.NbrObjectif) {
            InterfaceController.Situation = 3;
        }

        float RescherchTime = 0.00f;
        RescherchTime = Math.round(simulationTime*100.0f)/100.0f;

        lbl_Timmer.setText(String.valueOf("Target found in " + RescherchTime));
        simulationTime = 0.00f;
    }

    public void AffichageStop (Image BackGround){
        myCanvas.setWidth(ConfigReading.dimensionCaneva[0]);
        myCanvas.setHeight(ConfigReading.dimensionCaneva[1]);
        myCanvas.setLayoutX((ap.getWidth() - myCanvas.getWidth()) / 2);
        myCanvas.setLayoutY((ap.getHeight() - myCanvas.getHeight()) / 2);

        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        GestionObjects.Affichage(gc, BackGround);
    }
}