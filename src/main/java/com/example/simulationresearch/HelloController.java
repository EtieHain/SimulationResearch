package com.example.simulationresearch;

import GestionObjects.GestionObjects;
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


    public void Afficher(){

        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        for(int idx = 0; idx < GestionObjects.NbrAgent; idx++)
        {
            GestionObjects.agents[idx].Deplacement();
            GestionObjects.agents[idx].targetDetection();
        }
        GestionObjects.Affichage(gc);
    }
    public void EspaceVide(){
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        Image space = new Image( "background3.png" );
        gc.drawImage(space, 0, 0);
    }

}