package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

import static LectureConfig.ConfigReading.*;
import static com.example.simulationresearch.HelloApplication.file;


public class InterfaceController {

    public static int Situation = 0;        //Variables qui définira l'état du code
    //Créer la variable image de l'agent par default
    public static Image imageAgent = new Image("ship.png",40,40,false,false);
    //Créer la variable image de l'agent quand il est arrivé au target par default
    public static Image stopImg = new Image ("shipstop.png",40,40,false,false);
    //Créer la variable image du target par default
    public static Image imageTarget = new Image("alien.png",40,40,false,false);
    //Créer la variable image du background par default
    public static Image BackGround = new Image("bg.png");


    //Créer les labels qui afficheront les valeurs du fichier de configurations
    @FXML
    public Label lbl_PositionTarget = new Label("Position of the target : (0;0)");
    @FXML
    private Label lbl_AgentComm = new Label("Radius communication target : 0.00");
    @FXML
    private Label lbl_TargetComm = new Label("Radius communication agent : 0.00");


    //Créer les différents boutons pour changer l'état du code
    @FXML
    public Button btnStart;         //Bouton qui démarre la simulation
    @FXML
    public Button btnStop;          //Bouton qui met en pause la simulation
    @FXML
    public Button btnReset;         //Bouton qui redemarrer la simulation
    @FXML
    public Button btnSelectFile;    //Bouton qui active la sélection d'un fichier de config


    //Créer le canvas de l'arrière plan de l'interface
    @FXML
    public Canvas canvasInterface;


    //Créer toutes les images utilisées pour les agents, le target et le background
    private final Image ship = new Image("ship.png",40,40,false,false);
    private final Image shipStop = new Image("shipstop.png",40,40,false,false);
    private final Image bee = new Image("abeille.png",40,40,false,false);
    private final Image beeStop = new Image("abeillestop.png",40,40,false,false);
    private final Image helico = new Image("helico.png",40,40,false,false);
    private final Image helicoStop = new Image("helicostop.png",40,40,false,false);
    private final Image alien = new Image("alien.png",40,40,false,false);
    private final Image tournesol = new Image("tournesol.png",40,40,false,false);
    private final Image heliport = new Image("heliport.png",40,40,false,false);
    private final Image space = new Image("bg.png");
    private final Image grass = new Image("grass.png");


    //Code l'action du bouton de démarrage
    @FXML
    void btnStartClick(){
        Situation = 1;                          //Met le code en état de fonctionnement

        btnStart.setDisable(true);              //Désactive le bouton Start
        btnStop.setDisable(false);              //Active le bouton Stop
    }
    //Code l'action du bouton de mise en pause
    @FXML
    void btnStopClick(){
        //Test si le code est déja en état stop
        if(Situation == 3) {
            //Si oui
            btnStart.setDisable(true);          //Désactive le bouton Start
            btnStop.setDisable(true);           //Désactive le bouton Stop
        }
        else{
            //Si non
            btnStart.setDisable(true);          //Désactive le bouton Start
            btnStop.setDisable(false);          //Active le bouton Start
            Situation = 3;                      //Met le code en état Stop
        }
    }
    //Code l'action du bouton de redémarrage
    @FXML
    void btnResetClick(){
        Situation = 2;                          //Met le code en état Reset

        btnStart.setDisable(false);             //Active le bouton Start
        btnStop.setDisable(true);               //Désactive le bouton Start
    }
    //Code l'action du bouton de lecture de fichier
    @FXML
    void btnSelectFileClick(){

        FileChooser fichierConfig = new FileChooser();
        File initialDirectory = new File("src/main/resources");
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fichierConfig.setInitialDirectory(initialDirectory);
        }
        file = fichierConfig.showOpenDialog(null);

        if(file != null) {

            ConfigReading.ConfigReading(file);

            lbl_PositionTarget.setText("Position of the target : (" + posCible[0] + ";" + posCible[1] + ")");
            lbl_TargetComm.setText("Radius communication target : " + agentsDetectionRange);
            lbl_AgentComm.setText("Radius communication agent : " + agentsCommunicationRange);

            //Force un reset quand nouveau fichier
            Situation = 2;

            GestionObjects.creationObjects(5, imageAgent, imageTarget);

            btnStart.setDisable(false);
            btnReset.setDisable(false);
        }
    }


    //Code des changement des images des agents
    @FXML
    void imgShipeClick(){
        imageAgent = ship;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(ship, shipStop);
        }
    }
    @FXML
    void imgBeeClick(){
        imageAgent = bee;
        //stopImg = helicoStop;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(bee, beeStop);
        }
    }
    @FXML
    void imgHelicoClick(){
        imageAgent = helico;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(helico, helicoStop);
        }
    }


    //Code des changements de l'image du target
    @FXML
    void imgAlienClick(){
        imageTarget = alien;
        GestionObjects.target.changeImage(alien, alien);
    }
    @FXML
    void imgTournesolClick(){
        imageTarget = tournesol;
        GestionObjects.target.changeImage(tournesol, tournesol);
    }
    @FXML
    void imgHeliportClick(){
        imageTarget = heliport;
        GestionObjects.target.changeImage(heliport, heliport);
    }


    //Code des changements des themes prédéfinis (Change image background, agents et target)
    @FXML
    void imgSpaceClick(){
        BackGround = space;

        imageAgent = ship;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(ship, shipStop);
        }

        imageTarget = alien;
        GestionObjects.target.changeImage(alien, alien);

        GraphicsContext gcInterface = canvasInterface.getGraphicsContext2D();
        gcInterface.drawImage(space,0,0);
    }
    @FXML
    void imgGrassBeeClick(){
        BackGround = grass;

        imageAgent = bee;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(bee, beeStop);
        }

        imageTarget = tournesol;
        GestionObjects.target.changeImage(tournesol, tournesol);

        GraphicsContext gcInterface = canvasInterface.getGraphicsContext2D();
        gcInterface.drawImage(grass,0,0);
    }
    @FXML
    void imgGrassHelicoClick(){
        BackGround = grass;

        imageAgent = helico;
        for(int idx = 0; idx < GestionObjects.NbrAgent;idx++){
            GestionObjects.agents[idx].changeImage(helico, helicoStop);
        }

        imageTarget = heliport;
        GestionObjects.target.changeImage(heliport, heliport);

        GraphicsContext gcInterface = canvasInterface.getGraphicsContext2D();
        gcInterface.drawImage(grass,0,0);
    }
}
