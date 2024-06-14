//package com.example.simulationresearch;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.stage.FileChooser;
//
//import static com.example.simulationresearch.HelloApplication.file;
//
//public class InterfaceController {
//
//    static int Situation = 1;
//
//    @FXML
//    void btnStartClick(){
//        Situation = 1;
//    }
//    @FXML
//    void btnStopClick(){
//        Situation = 0;
//    }
//    @FXML
//    void btnResetClick(){
//        Situation = 2;
//    }
//}
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

import static LectureConfig.ConfigReading.*;
        import static com.example.simulationresearch.HelloApplication.file;

public class InterfaceController {

    public static int Situation = 0;
    public static Image imageAgent = new Image("ship.png",40,40,false,false);
    public static Image imageTarget = new Image("alien.png",40,40,false,false);
    public static Image BackGround = new Image("bg.png");
     public static Image stopImg = new Image ("shipstop.png",40,40,false,false);
//    static Image BackGround = new Image("bg.png",LectureConfig.dimensionCaneva[0],LectureConfig.dimensionCaneva[1],false,false);


    //Configurations des labels
    @FXML
    public Label lbl_PositionTarget = new Label("Position of the target : (0;0)");
    @FXML
    private Label lbl_AgentComm = new Label("Radius communication target : 0.00");
    @FXML
    private Label lbl_TargetComm = new Label("Radius communication agent : 0.00");
    @FXML
    public Button btnStart;
    @FXML
    public Button btnStop;
    @FXML
    public Button btnReset;
    @FXML
    public Canvas canvasInterface;




    //Créer toutes les images utilisées pour le backgound, le traget et les agents
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



    //Code des actions de tout les boutons
    @FXML
    void btnStartClick(){
        Situation = 1;

        btnStart.setDisable(true);
        btnStop.setDisable(false);
    }
    @FXML
    void btnStopClick(){
        Situation = 3;


        btnStart.setDisable(false);
        btnStop.setDisable(true);
    }
    @FXML
    void btnResetClick(){
        Situation = 2;

        btnStart.setDisable(false);
        btnStop.setDisable(true);
    }
    @FXML
    void btnSelectFileClick(){
        FileChooser fichierConfig = new FileChooser();
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
            //btnStop.setDisable(false);
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


    //Code des changements des themes prédéfinis
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
