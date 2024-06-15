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
    public static Image BackGround = new Image("bcg.png",0,1000,true,false);


    public static boolean isOn = false;


    //Créer les labels qui afficheront les valeurs du fichier de configurations
    @FXML
    public Label lbl_PositionTarget;
    @FXML
    private Label lbl_AgentComm;
    @FXML
    private Label lbl_TargetComm;


    //Créer les différents boutons pour changer l'état du code
    @FXML
    public Button btnStart;         //Bouton qui démarre la simulation
    @FXML
    public Button btnReset;         //Bouton qui redemarrer la simulation
    @FXML
    public Button btnSelectFile;    //Bouton qui active la sélection d'un fichier de config
    @FXML
    public Button btnExport;    //Bouton d'expportation en Mp4 de la simulation

    //Créer le canvas de l'arrière plan de l'interface
    @FXML
    public Canvas canvasInterface;


    //Créer toutes les images utilisées pour les agents, le target et le background
    private final Image ship = new Image("ship.png",40,40,false,false);
    private final Image shipStop = new Image("shipstop.png",40,40,false,false);
    private final Image alien = new Image("alien.png",40,40,false,false);
    private final Image space = new Image("bg.png");
    private final Image screen = new Image("ibg.png");
    private final Image[] spaceTheme = {ship,shipStop,alien,space,screen};

    private final Image bee = new Image("abeille.png",40,40,false,false);
    private final Image beeStop = new Image("abeillestop.png",40,40,false,false);
    private final Image tournesol = new Image("tournesol.png",40,40,false,false);
    private final Image grass = new Image("grass.png");
    private final Image hive = new Image("hive.png");
    private final Image[] hiveTheme = {bee,beeStop,tournesol,grass,hive};

    private final Image helico = new Image("helico.png",40,40,false,false);
    private final Image helicoStop = new Image("helicostop.png",40,40,false,false);
    private final Image heliport = new Image("heliport.png",40,40,false,false);
    private final Image roofs = new Image("bgh.png");
    private final Image brick = new Image("brick.png");
    private final Image[] cityTheme = {helico,helicoStop,heliport,roofs,brick};


    //Code l'action du bouton de démarrage
    @FXML
    void btnStartClick(){
        if(isOn){
            btnStart.setText("Start");
            isOn = false;
            Situation = 3;
        }else{
            btnStart.setText("Stop");
            isOn = true;
            Situation = 1;
        }
    }

    @FXML
    void btnExportClick(){
        //Code d'exportation

    }

    //Code l'action du bouton de redémarrage
    @FXML
    void btnResetClick(){
        btnStart.setText("Start");
        isOn = false;
        Situation = 2;                          //Met le code en état Reset
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

            BackGround = new Image(BackGround.getUrl(),0, dimensionCaneva[1],true,false);
            lbl_PositionTarget.setText("Position of the target : ( " + posCible[0] + " ; " + posCible[1] + " )");
            lbl_TargetComm.setText("Radius communication target : " + agentsDetectionRange);
            lbl_AgentComm.setText("Radius communication agent : " + agentsCommunicationRange);

            //Force un reset quand nouveau fichier
            Situation = 2;

            btnStart.setDisable(false);
            btnReset.setDisable(false);

            btnStart.setText("Start");
            isOn = false;
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
        gcInterface.drawImage(screen,0,0);
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

    @FXML
    void spaceThemeClick(){

    }
}
