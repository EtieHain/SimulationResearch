package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;

import java.io.File;
import java.io.IOException;

import static LectureConfig.ConfigReading.*;
import static com.example.simulationresearch.HelloApplication.file;
import static com.example.simulationresearch.HelloApplication.frameCount;
import static com.example.simulationresearch.HelloController.*;


public class InterfaceController {

    public static int Situation = 0;        //Variables qui définira l'état du code
    //Créer la variable image de l'agent par default


    public static boolean isOn = false;

    @FXML
    public ImageView interfaceBG;

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

    @FXML
    public ScrollPane themeList;

    //Créer toutes les images utilisées pour les agents, le target et le background
    private static final Image ship = new Image("ship.png",40,40,false,false);
    private static final Image shipStop = new Image("shipstop.png",40,40,false,false);
    private static final Image alien = new Image("alien.png",40,40,false,false);
    private static final Image space = new Image("space.png",0,800,true,false);
    private static final Image screen = new Image("ibg.png");
    private static final Image[] spaceTheme = {ship,shipStop,alien,space,screen};

    private static final Image bee = new Image("abeille.png",40,40,false,false);
    private static final Image beeStop = new Image("abeillestop.png",40,40,false,false);
    private static final Image tournesol = new Image("tournesol.png",40,40,false,false);
    private static final Image grass = new Image("grass.png");
    private static final Image hive = new Image("hive.png");
    private static final Image[] hiveTheme = {bee,beeStop,tournesol,grass,hive};

    private static final Image helico = new Image("helico.png",40,40,false,false);
    private static final Image helicoStop = new Image("helicostop.png",40,40,false,false);
    private static final Image heliport = new Image("heliport.png",40,40,false,false);
    private static final Image roofs = new Image("bgh.png");
    private static final Image brick = new Image("brick.png");
    private static final Image[] cityTheme = {helico,helicoStop,heliport,roofs,brick};

    private static final Image police = new Image("police.png",40,40,false,false);
    private static final Image policeStop = new Image("policestop.png",40,40,false,false);
    private static final Image money = new Image("money.png",40,40,false,false);
    private static final Image map = new Image("bgh.png");
    private static final Image note = new Image("brick.png");
    private static final Image[] policeTheme = {police,policeStop,money,map,note};

    private static final Image shark = new Image("shark.png",40,40,false,false);
    private static final Image sharkStop = new Image("sharkstop.png",40,40,false,false);
    private static final Image turtle = new Image("turtle.png",40,40,false,false);
    private static final Image floor = new Image("bgh.png");
    private static final Image bubble = new Image("brick.png");
    private static final Image[] sharkTheme = {shark,sharkStop,turtle,floor,bubble};

    private static final Image dragon = new Image("dragon.png",40,40,false,false);
    private static final Image dragonStop = new Image("dragonStop.png",40,40,false,false);
    private static final Image nest = new Image("nest.png",40,40,false,false);
    private static final Image lava = new Image("grass.png");
    private static final Image scale = new Image("brick.png");
    private static final Image[] dragonTheme = {dragon,dragonStop,nest,lava,scale};

    private static final Image merry = new Image("shark.png",40,40,false,false);
    private static final Image merryStop = new Image("sharkstop.png",40,40,false,false);
    private static final Image op = new Image("turtle.png",40,40,false,false);
    private static final Image sea = new Image("bgh.png");
    private static final Image planks = new Image("brick.png");
    private static final Image[] luffyTheme = {merry,merryStop,op,sea,planks};

    public static Image[] activeTheme = spaceTheme;

    public static Image imageAgent = new Image("ship.png",40,40,false,false);
    //Créer la variable image de l'agent quand il est arrivé au target par default
    public static Image stopImg = new Image ("shipstop.png",40,40,false,false);
    //Créer la variable image du target par default
    public static Image targetImg = new Image("alien.png",40,40,false,false);
    //Créer la variable image du background par default
    public static Image BackGround = new Image("space.png",0,1000,true,false);

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
            if(frameCount == 0 )
            {
                nbrImg = 0;
                lastTime = 0;
                //Supprime les images
                File file = new File("Images");
                deleteDirectory(file);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void btnExportClick(){
        //Code d'exportation
        String outputFilePath = "output.mp4";
        int frameRate = (int) (nbrImg / ResearchTime);            //Créer une variable float; // Frames per second

        // Creation et configuration d'une instance de FFMpeg
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, dimensionCaneva[0], dimensionCaneva[1]);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setFrameRate(frameRate);
        recorder.setVideoBitrate(10 * 1024 * 1024);

        // Démarage du recoder
        try {
            recorder.startUnsafe();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du démarrage de l'enregistreur.");
            return;
        }
        //Conversion
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        // Création d'un tableau d'image avec toutes les images
        File dir = new File("Images");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

        if (files != null) {
            //Boucle qui passe image par image
            for (File file : files) {
                // Read the image file
                IplImage image = opencv_imgcodecs.cvLoadImage(file.getAbsolutePath());

                // Conversion de l'image en frame
                Frame frame = converter.convert(image);

                // Record la frame
                try {
                    recorder.record(frame);
                } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                    e.printStackTrace();
                    System.err.println("Erreur lors de l'enregistrement du cadre : " + file.getName());
                }

                // Release l'image
                image.release();
            }
        }

        // Stop le recorder
        try {
            recorder.stop();
            recorder.release();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'arrêt de l'enregistreur.");
        }
    }

    //Code l'action du bouton de redémarrage
    @FXML
    void btnResetClick(){
        btnStart.setText("Start");
        isOn = false;
        Situation = 2;                          //Met le code en état Reset

        //Supprime les images
        File file = new File("Images");
        nbrImg = 0;
        deleteDirectory(file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            btnExport.setDisable(false);
            themeList.setDisable(false);

            btnStart.setText("Start");
            isOn = false;
        }
        else
        {
            // Afficher une alerte ou mettre à jour l'interface utilisateur après une erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Pas de fichier sélectionné !");
            alert.showAndWait();
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
        targetImg = alien;
        GestionObjects.target.changeImage(alien, alien);
    }
    @FXML
    void imgTournesolClick(){
        targetImg = tournesol;
        GestionObjects.target.changeImage(tournesol, tournesol);
    }
    @FXML
    void imgHeliportClick(){
        targetImg = heliport;
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

        targetImg = alien;
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

        targetImg = tournesol;
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

        targetImg = heliport;
        GestionObjects.target.changeImage(heliport, heliport);

        GraphicsContext gcInterface = canvasInterface.getGraphicsContext2D();
        gcInterface.drawImage(grass,0,0);
    }


    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Supprimer le fichier
                    if (!file.delete()) {
                        System.err.println("Échec de la suppression du fichier : " + file.getAbsolutePath());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    void updateTheme(){
        interfaceBG.setImage(activeTheme[4]);
        for(int i = 0; i < GestionObjects.NbrAgent;i++){
            GestionObjects.agents[i].changeImage(activeTheme[0], activeTheme[1]);
        }
        activeTheme[3]=new Image(activeTheme[3].getUrl(), 0,dimensionCaneva[1],true,false );
        GestionObjects.target.changeImage(activeTheme[2],null);
    }

    @FXML
    void spaceThemeClick(){
        activeTheme = spaceTheme;
        updateTheme();
    }

    @FXML
    void hiveThemeClick(){
        activeTheme = hiveTheme;
        updateTheme();
    }

    @FXML
    void cityThemeClick(){
        activeTheme = cityTheme;
        updateTheme();
    }

    @FXML
    void policeThemeClick(){
        activeTheme = policeTheme;
        updateTheme();
    }

    @FXML
    void sharkThemeClick(){
        activeTheme = sharkTheme;
        updateTheme();
    }

    @FXML
    void dragonThemeClick(){
        activeTheme = dragonTheme;
        updateTheme();
    }

    @FXML
    void luffyThemeClick(){
        activeTheme = luffyTheme;
        updateTheme();
    }

    @FXML
    void customThemeClicked(){

    }
}
