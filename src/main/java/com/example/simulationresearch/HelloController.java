package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class HelloController {
    @FXML
    private Canvas myCanvas;
    @FXML
    private AnchorPane ap;


    public void Afficher(){
        myCanvas.setWidth(LectureConfig.dimensionCaneva[0]);
        myCanvas.setHeight(LectureConfig.dimensionCaneva[1]);
        myCanvas.setLayoutX((ap.getWidth()-myCanvas.getWidth())/2);
        myCanvas.setLayoutY((ap.getHeight()-myCanvas.getHeight())/2);

        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        int NbrFound = 0;
        for(int idx = 0; idx < GestionObjects.NbrAgent; idx++)
        {
            if(GestionObjects.agents[idx].getState()[0]&&!GestionObjects.agents[idx].getState()[1])GestionObjects.testCommunication(idx);
            else GestionObjects.agents[idx].targetDetection();
            if(!GestionObjects.agents[idx].getState()[0]||!GestionObjects.agents[idx].getState()[1])GestionObjects.agents[idx].Deplacement();
            if (GestionObjects.agents[idx].getState()[0]) NbrFound++;
        }
        GestionObjects.Affichage(gc);
        if (NbrFound >= (GestionObjects.NbrAgent / 2 + 1)) {
            InterfaceController.Situation=2;
        }
    }
    public void EspaceVide(){
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        Image space = new Image( "bg.png" );
        gc.drawImage(space, 0, 0);
    }

}