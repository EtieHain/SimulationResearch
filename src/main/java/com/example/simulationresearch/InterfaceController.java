package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

/**
 * Controller class of the interface
 */
public class InterfaceController {

    public static int Situation = 0;        //Variables qui définira l'état du code
    //Créer la variable image de l'agent par default

    public static boolean isOn = false; //variable d'état du btn paly/pause

    @FXML
    public ImageView interfaceBG; //background de l'interface
    @FXML
    public ImageView btnPlayPause; //btn play/pause
    @FXML
    public ImageView btnReset;         //Bouton de reset de la simulation
    @FXML
    public ImageView btnExport;    //Bouton d'expportation en Mp4 de la simulation
    @FXML
    public ImageView btnSelectFile;    //Bouton qui active la sélection d'un fichier de config

    //Créer les labels qui afficheront les valeurs du fichier de configurations
    @FXML
    public Label lbl_PositionTarget; //position de la target
    @FXML
    private Label lbl_AgentComm; //radius de comm
    @FXML
    private Label lbl_TargetComm; //radius de detec
    @FXML
    private Label bgLBL; //titre du choix des background
    @FXML
    private Label agentLBL; //titre du choix des agent
    @FXML
    private Label targetLBL; //titre du choix de la target

    @FXML
    public ScrollPane themeList; //liste des theme
    @FXML
    public ScrollPane bgSP; //liste de background
    @FXML
    public ScrollPane agentSP; //liste des agents
    @FXML
    public ScrollPane targetSP; //liste des cibles

    //images du btn play/pause
    private final Image playIcon = new Image("playIcon.png");
    private final Image pauseIcon = new Image("pauseIcon.png");

    //Créer toutes les images utilisées pour les agents, la target et le background
    private static final Image ship = new Image("ship.png",40,40,false,false);
    private static final Image shipStop = new Image("shipstop.png",40,40,false,false);
    private static final Image alien = new Image("alien.png",40,40,false,false);
    private static final Image space = new Image("space.png",0,800,true,false);
    private static final Image screen = new Image("ibg.png");
    //enregistrement des image dans un theme
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
    private static final Image map = new Image("map.png");
    private static final Image phone = new Image("phone.png");
    private static final Image[] policeTheme = {police,policeStop,money,map,phone};

    private static final Image shark = new Image("shark.png",40,40,false,false);
    private static final Image sharkStop = new Image("sharkstop.png",40,40,false,false);
    private static final Image turtle = new Image("turtle.png",40,40,false,false);
    private static final Image floor = new Image("floor.png");
    private static final Image bubble = new Image("bubbles.png");
    private static final Image[] sharkTheme = {shark,sharkStop,turtle,floor,bubble};

    private static final Image dragon = new Image("dragon.png",40,40,false,false);
    private static final Image dragonStop = new Image("dragonStop.png",40,40,false,false);
    private static final Image nest = new Image("nest.png",40,40,false,false);
    private static final Image rocks = new Image("rocks.png");
    private static final Image scale = new Image("scales.png");
    private static final Image[] dragonTheme = {dragon,dragonStop,nest,rocks,scale};

    private static final Image merry = new Image("merry.png",40,40,false,false);
    private static final Image merryStop = new Image("merrystop.png",40,40,false,false);
    private static final Image op = new Image("op.png",40,40,false,false);
    private static final Image sea = new Image("sea.png");
    private static final Image planks = new Image("planks.png");
    private static final Image[] luffyTheme = {merry,merryStop,op,sea,planks};

    //appliquage du theme au démarrage
    public static Image[] activeTheme = spaceTheme.clone();

    /**
     * This method is called when the play/pause button is pressed
     */
    @FXML
    void btnStartClick(){
        //si la simul est en marche
        if(!SimulationDone) {
            //inversion de l'état du btn
            if (isOn) {
                //pause
                btnPlayPause.setImage(playIcon);
                isOn = false;
                Situation = 3;
            } else {
                //play
                btnPlayPause.setImage(pauseIcon);
                isOn = true;
                Situation = 1;
                //suppréssion des images au démarrage
                if (frameCount == 0) {
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
    }

    /**
     * this method exports the MP4 video of the simulation
     */
    @FXML
    void btnExportClick(){
        //Code d'exportation

        String outputFilePath = "output.mp4";   //Nom du fichier vidéo de sortie
        int frameRate = (int) (nbrImg / ResearchTime); // Frames per second

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
        // Affiche une fenêtre indiquant le répertoire de la vidéo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        File video = new File(outputFilePath);
        alert.setContentText("Fichier vidéo créé dans le répértoire : \n" + video.getAbsoluteFile());
        alert.showAndWait();
    }

    /**
     * this method is called when the reset button is pressed
     */
    @FXML
    void btnResetClick(){
        //set l'état du btn play/pause
        btnPlayPause.setImage(playIcon);
        SimulationDone =false;
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

    /**
     * this method is called when the select file button is called
     */
    @FXML
    void btnSelectFileClick(){
        //séléction du fichier
        FileChooser fichierConfig = new FileChooser();
        File initialDirectory = new File("src/main/resources");
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fichierConfig.setInitialDirectory(initialDirectory);
        }
        file = fichierConfig.showOpenDialog(null);

        //si fichier valide
        if(file != null) {
            //appel de la fontion de lecture
            ConfigReading.ConfigReading(file);

            //affichage des paramètres
            activeTheme[3] = new Image(activeTheme[3].getUrl(),dimensionCaneva[0], dimensionCaneva[1],false,false);
            lbl_PositionTarget.setText("Position of the target : ( " + posCible[0] + " ; " + posCible[1] + " )");
            lbl_TargetComm.setText("Radius communication target : " + agentsDetectionRange);
            lbl_AgentComm.setText("Radius communication agent : " + agentsCommunicationRange);

            //Force un reset quand nouveau fichier
            btnResetClick();

            //active les btn
            btnPlayPause.setDisable(false);
            btnPlayPause.setOpacity(1);
            btnReset.setDisable(false);
            btnReset.setOpacity(1);
            btnExport.setDisable(false);
            btnExport.setOpacity(1);
            themeList.setDisable(false);

            //set l'état du btn play/pause
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

    /**
     * This method deletes all the images saved during the simulation
     *
     * @param directory :dossier dans lequel tous les fichier seront supprimés
     */
    public static void deleteDirectory(File directory) {
        //Vérifie que le dossier existe
        if (directory.exists())
        {
            //Fait une liste de tous le fichier dans le dossier
            File[] files = directory.listFiles();
            //Si le dossier n'est pas vide
            if (files != null)
            {
                //Boucle qui passe par tous les fichier du dossier
                for (File file : files)
                {
                    //Boucle qui essaye de supprimer le fichier jusqu'a ce qu'elle réussisse
                    while(!file.delete())
                    {
                        try
                        {
                            //Si la suppression ne marche pas on attent 10ms avant de rééssayer
                            Thread.sleep(10);
                        }
                        catch (InterruptedException e)
                        {
                            Thread.currentThread().interrupt();
                            System.err.println("Le thread a été interrompu.");
                        }
                    }
                }
            }
        }
    }

    /**
     * this method will update the theme of the simulation
     */
    void updateTheme(){
        //update le background de l'interface
        interfaceBG.setImage(activeTheme[4]);
        //update les images des agents
        for(int i = 0; i < GestionObjects.NbrAgent;i++){
            GestionObjects.agents[i].changeImage(activeTheme[0], activeTheme[1]);
        }
        //update de background de la simul
        activeTheme[3]=new Image(activeTheme[3].getUrl(), dimensionCaneva[1],dimensionCaneva[1],false,false );
        //update l'image de la target
        GestionObjects.target.changeImage(activeTheme[2],null);
    }

    /**
     * this method selects the space theme
     */
    @FXML
    void spaceThemeClick(){
        //application du theme
        activeTheme = spaceTheme.clone();
        //update du theme
        updateTheme();
        //désactivation du theme custom
        disableCustomChoice();
    }
    /**
     * this method selects the hive theme
     */
    @FXML
    void hiveThemeClick(){
        activeTheme = hiveTheme.clone();
        updateTheme();
        disableCustomChoice();
    }
    /**
     * this method selects the city theme
     */
    @FXML
    void cityThemeClick(){
        activeTheme = cityTheme.clone();
        updateTheme();
        disableCustomChoice();
    }
    /**
     * this method selects the police theme
     */
    @FXML
    void policeThemeClick(){
        activeTheme = policeTheme.clone();
        updateTheme();
        disableCustomChoice();
    }
    /**
     * this method selects the shark theme
     */
    @FXML
    void sharkThemeClick(){
        activeTheme = sharkTheme.clone();
        updateTheme();
        disableCustomChoice();
    }
    /**
     * this method selects the dragon theme
     */
    @FXML
    void dragonThemeClick(){
        activeTheme = dragonTheme.clone();
        updateTheme();
        disableCustomChoice();
    }
    /**
     * this method selects the luffy theme
     */
    @FXML
    void luffyThemeClick(){
        activeTheme = luffyTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    /**
     * this method enable the customization of the theme
     */
    @FXML
    void customThemeClicked(){
        //afficher les liste d'images
        bgSP.setVisible(true);
        agentSP.setVisible(true);
        targetSP.setVisible(true);
        //afficher les titres correspondant
        bgLBL.setVisible(true);
        agentLBL.setVisible(true);
        targetLBL.setVisible(true);
    }
    /**
     * this method disable the customization of the theme
     */
    void disableCustomChoice(){
        //cacher les liste d'images
        bgSP.setVisible(false);
        agentSP.setVisible(false);
        targetSP.setVisible(false);
        //cacher les titres correspondant
        bgLBL.setVisible(false);
        agentLBL.setVisible(false);
        targetLBL.setVisible(false);
    }

    /**
     * this method selects the background of the space theme
     */
    @FXML
    void bgSpaceClicked(){
        //changement des background
        activeTheme[3]=spaceTheme[3];
        activeTheme[4]=spaceTheme[4];
        //update
        updateTheme();
    }
    /**
     * this method selects the background of the hive theme
     */
    @FXML
    void bgHiveClicked(){
        activeTheme[3]=hiveTheme[3];
        activeTheme[4]=hiveTheme[4];
        updateTheme();
    }
    /**
     * this method selects the background of the city theme
     */
    @FXML
    void bgCityClicked(){
        activeTheme[3]=cityTheme[3];
        activeTheme[4]=cityTheme[4];
        updateTheme();
    }
    /**
     * this method selects the background of the police theme
     */
    @FXML
    void bgPoliceClicked(){
        activeTheme[3]=policeTheme[3];
        activeTheme[4]=policeTheme[4];
        updateTheme();
    }
    /**
     * this method selects the background of the shark theme
     */
    @FXML
    void bgSharkClicked(){
        activeTheme[3]=sharkTheme[3];
        activeTheme[4]=sharkTheme[4];
        updateTheme();
    }
    /**
     * this method selects the background of the dragon theme
     */
    @FXML
    void bgDragonClicked(){
        activeTheme[3]=dragonTheme[3];
        activeTheme[4]=dragonTheme[4];
        updateTheme();
    }
    /**
     * this method selects the background of the luffy theme
     */
    @FXML
    void bgLuffyClicked(){
        activeTheme[3]=luffyTheme[3];
        activeTheme[4]=luffyTheme[4];
        updateTheme();
    }

    /**
     * this method selects the agent of the space theme
     */
    @FXML
    void agentSpaceClicked(){
        //changement de l'image de l'agent
        activeTheme[0]=spaceTheme[0];
        activeTheme[1]=spaceTheme[1];
        //update
        updateTheme();
    }
    /**
     * this method selects the agent of the hive theme
     */
    @FXML
    void agentHiveClicked(){
        activeTheme[0]=hiveTheme[0];
        activeTheme[1]=hiveTheme[1];
        updateTheme();
    }
    /**
     * this method selects the agent of the city theme
     */
    @FXML
    void agentCityClicked(){
        activeTheme[0]=cityTheme[0];
        activeTheme[1]=cityTheme[1];
        updateTheme();
    }
    /**
     * this method selects the agent of the police theme
     */
    @FXML
    void agentPoliceClicked(){
        activeTheme[0]=policeTheme[0];
        activeTheme[1]=policeTheme[1];
        updateTheme();
    }
    /**
     * this method selects the agent of the shark theme
     */
    @FXML
    void agentSharkClicked(){
        activeTheme[0]=sharkTheme[0];
        activeTheme[1]=sharkTheme[1];
        updateTheme();
    }
    /**
     * this method selects the agent of the dragon theme
     */
    @FXML
    void agentDragonClicked(){
        activeTheme[0]=dragonTheme[0];
        activeTheme[1]=dragonTheme[1];
        updateTheme();
    }
    /**
     * this method selects the agent of the luffy theme
     */
    @FXML
    void agentLuffyClicked(){
        activeTheme[0]=luffyTheme[0];
        activeTheme[1]=luffyTheme[1];
        updateTheme();
    }

    /**
     * this method selects the target of the space theme
     */
    @FXML
    void targetSpaceClicked(){
        //changement de la cible
        activeTheme[2]=spaceTheme[2];
        //update
        updateTheme();
    }
    /**
     * this method selects the target of the hive theme
     */
    @FXML
    void targetHiveClicked(){
        activeTheme[2]=hiveTheme[2];
        updateTheme();
    }
    /**
     * this method selects the target of the city theme
     */
    @FXML
    void targetCityClicked(){
        activeTheme[2]=cityTheme[2];
        updateTheme();
    }
    /**
     * this method selects the target of the police theme
     */
    @FXML
    void targetPoliceClicked(){
        activeTheme[2]=policeTheme[2];
        updateTheme();
    }
    /**
     * this method selects the target of the shark theme
     */
    @FXML
    void targetSharkClicked(){
        activeTheme[2]=sharkTheme[2];
        updateTheme();
    }
    /**
     * this method selects the target of the dragon theme
     */
    @FXML
    void targetDragonClicked(){
        activeTheme[2]=dragonTheme[2];
        updateTheme();
    }
    /**
     * this method selects the target of the luffy theme
     */
    @FXML
    void targetLuffyClicked(){
        activeTheme[2]=luffyTheme[2];
        updateTheme();
    }

    /**
     * this method make the select file button bigger when the mouse is on it
     */
    @FXML
    void onFileMouseIn(){
        //set la taille et la position du btn
        btnSelectFile.setFitHeight(46);
        btnSelectFile.setFitWidth(46);
        btnSelectFile.setLayoutX(127);
        btnSelectFile.setLayoutY(94);
    }
    /**
     * this method make the select file button smaller when the mouse is not on it
     */
    @FXML
    void onFileMouseOut(){
        //set la taille et la position du btn
        btnSelectFile.setFitHeight(40);
        btnSelectFile.setFitWidth(40);
        btnSelectFile.setLayoutX(130);
        btnSelectFile.setLayoutY(97);
    }

    /**
     * this method make the play/pause file button bigger when the mouse is on it
     */
    @FXML
    void onPlayPauseMouseIn(){
        if(!SimulationDone) {
            btnPlayPause.setFitHeight(58);
            btnPlayPause.setFitWidth(58);
            btnPlayPause.setLayoutX(124);
            btnPlayPause.setLayoutY(689);
        }else{
            btnPlayPause.setFitHeight(50);
            btnPlayPause.setFitWidth(50);
            btnPlayPause.setLayoutX(128);
            btnPlayPause.setLayoutY(693);
        }
    }
    /**
     * this method make the play/pause file button smaller when the mouse is not on it
     */
    @FXML
    void onPlayPauseMouseOut(){
        btnPlayPause.setFitHeight(50);
        btnPlayPause.setFitWidth(50);
        btnPlayPause.setLayoutX(128);
        btnPlayPause.setLayoutY(693);
    }

    /**
     * this method make the reset button bigger when the mouse is on it
     */
    @FXML
    void onResetMouseIn(){
        btnReset.setFitHeight(58);
        btnReset.setFitWidth(58);
        btnReset.setLayoutX(40);
        btnReset.setLayoutY(689);
    }
    /**
     * this method make the reset button smaller when the mouse is not on it
     */
    @FXML
    void onResetMouseOut(){
        btnReset.setFitHeight(50);
        btnReset.setFitWidth(50);
        btnReset.setLayoutX(44);
        btnReset.setLayoutY(693);
    }

    /**
     * this method make the export button bigger when the mouse is on it
     */
    @FXML
    void onExportMouseIn(){
        btnExport.setFitHeight(66);
        btnExport.setFitWidth(66);
        btnExport.setLayoutX(208);
        btnExport.setLayoutY(685);
    }
    /**
     * this method make the export button smaller when the mouse is not on it
     */
    @FXML
    void onExportMouseOut(){
        btnExport.setFitHeight(56);
        btnExport.setFitWidth(56);
        btnExport.setLayoutX(213);
        btnExport.setLayoutY(690);
    }
}
