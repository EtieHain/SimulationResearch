package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import Objects.Agent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

import static LectureConfig.LectureConfig.*;
import static com.example.simulationresearch.HelloApplication.file;

public class InterfaceController {

    static int Situation = 0;
    static Image imageAgent = new Image("ship.png",40,40,false,false);
    static Image imageTarget = new Image("target.png",40,40,false,false);
    static Image BackGround = new Image("background3.jpg",LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1],false,false);


    //Configurations des labels
    @FXML
    public Label lbl_PositionTarget = new Label("Position of the target : (0;0)");
    @FXML
    private Label lbl_AgentComm = new Label("Radius communication target : 0.00");
    @FXML
    private Label lbl_TargetComm = new Label("Radius communication agent : 0.00");
    @FXML
    private Label lbl_Timmer;


    //Créer toutes les images utilisées pour le backgound, le traget et les agents
    private final Image ship = new Image("ship.png",40,40,false,false);
    private final Image star = new Image("star.png",40,40,false,false);
    private final Image alien = new Image("target.png",40,40,false,false);
    private final Image earth = new Image("earth.png",40,40,false,false);
    private final Image background2 = new Image("background.jpg",LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1],false,false);
    //private final Image background2 = new Image("background.jpg");


    //Code des actions de tout les boutons
    @FXML
    void btnStartClick(){
        Situation = 1;
    }
    @FXML
    void btnStopClick(){
        Situation = 0;
    }
    @FXML
    void btnResetClick(){
        Situation = 2;
    }
    @FXML
    void btnSelectFileClick(){
        FileChooser fichierConfig = new FileChooser();
        file = fichierConfig.showOpenDialog(null);

        LectureConfig.LectureFichier(file);

        lbl_PositionTarget.setText("Position of the target : (" + posCible[0] + ";" + posCible[1] + ")");
        lbl_TargetComm.setText("Radius communication target : " + agentsDetectionRange);
        lbl_AgentComm.setText("Radius communication agent : " + agentsCommunicationRange);

        //Force un reset quand nouveau fichier
        Situation = 2;
    }

    //Code des changement des images des agents
    @FXML
    void imgShipeClick(){
        imageAgent = ship;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(ship);
        }
    }
    @FXML
    void imgStarClick(){
        imageAgent = star;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(star);
        }
    }

    //Code des changements de l'image du target
    @FXML
    void imgAlienClick(){
        imageTarget = alien;
        GestionObjects.cible.changeImage(alien);
    }
    @FXML
    void imgEarthClick(){
        imageTarget = earth;
        GestionObjects.cible.changeImage(earth);
    }

    //Code des changements des themes prédéfinis
    @FXML
    void imgSpaceClick(){
        BackGround = background2;
        imageAgent = ship;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(ship);
        }
        imageTarget = alien;
        GestionObjects.cible.changeImage(alien);
    }
}
