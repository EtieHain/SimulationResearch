package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import com.example.simulationresearch.InterfaceController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

import static LectureConfig.LectureConfig.*;
import static com.example.simulationresearch.InterfaceController.*;


public class HelloController {
    @FXML
    private Canvas myCanvas;


    public void Afficher(Image bg){
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        for(int idx = 0; idx < GestionObjects.NbrAgent; idx++)
        {
            GestionObjects.agents[idx].Deplacement();
            GestionObjects.agents[idx].targetDetection();
        }
        GestionObjects.Affichage(gc, bg);
    }

    /*
    public void affichageDataFile(){
        FXMLLoader fxmlLoaderInterface = new FXMLLoader(HelloApplication.class.getResource("Interface.fxml"));
        InterfaceController Ctrl_Interface = fxmlLoaderInterface.getController();

        Ctrl_Interface.setLbl_PositionTarget("Position of the target : (" + posCible[0] + ";" + posCible[1] + ")");
        Ctrl_Interface.setLbl_TargetComm("Radius communication target : " + agentsDetectionRange);
        Ctrl_Interface.setLbl_AgentComm("Radius communication agent : " + agentsCommunicationRange);
    }

     */
}