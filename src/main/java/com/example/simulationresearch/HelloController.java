package com.example.simulationresearch;

import GestionObjects.ObjectsGestion;
import LectureConfig.ConfigReading;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class HelloController {
    @FXML
    private Canvas myCanvas;
    @FXML
    private AnchorPane ap;
    @FXML
    private ScrollPane sp;


    public void Afficher(){
        myCanvas.setWidth(ConfigReading.dimensionCaneva[0]);
        myCanvas.setHeight(ConfigReading.dimensionCaneva[1]);
        myCanvas.setLayoutX((ap.getWidth()-myCanvas.getWidth())/2);
        myCanvas.setLayoutY((ap.getHeight()-myCanvas.getHeight())/2);

        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        int NbrFound = 0;
        for(int idx = 0; idx < ObjectsGestion.NbrAgent; idx++)
        {
            if(ObjectsGestion.agents[idx].getState()[0]&&!ObjectsGestion.agents[idx].getState()[1]) ObjectsGestion.testCommunication(idx);
            else ObjectsGestion.agents[idx].targetDetection();
            if(!ObjectsGestion.agents[idx].getState()[0]||!ObjectsGestion.agents[idx].getState()[1]) ObjectsGestion.agents[idx].Deplacement();
            if (ObjectsGestion.agents[idx].getState()[0]) NbrFound++;
        }
        ObjectsGestion.Affichage(gc);
        if (NbrFound >= ObjectsGestion.NbrObjectif) {
            InterfaceController.Situation=0;
        }
    }
}