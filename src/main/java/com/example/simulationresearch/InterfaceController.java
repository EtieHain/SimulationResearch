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


public class InterfaceController {

    public static int Situation = 0;        //Variables qui définira l'état du code
    //Créer la variable image de l'agent par default

    public static boolean isOn = false;

    @FXML
    public ImageView interfaceBG;
    @FXML
    public ImageView btnPlayPause;
    @FXML
    public ImageView btnReset;         //Bouton qui redemarrer la simulation
    @FXML
    public ImageView btnExport;    //Bouton d'expportation en Mp4 de la simulation
    @FXML
    public ImageView btnSelectFile;    //Bouton qui active la sélection d'un fichier de config

    //Créer les labels qui afficheront les valeurs du fichier de configurations
    @FXML
    public Label lbl_PositionTarget;
    @FXML
    private Label lbl_AgentComm;
    @FXML
    private Label lbl_TargetComm;
    @FXML
    private Label bgLBL;
    @FXML
    private Label agentLBL;
    @FXML
    private Label targetLBL;

    @FXML
    public ScrollPane themeList;
    @FXML
    public ScrollPane bgSP;
    @FXML
    public ScrollPane agentSP;
    @FXML
    public ScrollPane targetSP;

    private final Image playIcon = new Image("playIcon.png");
    private final Image pauseIcon = new Image("pauseIcon.png");

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

    public static Image[] activeTheme = spaceTheme.clone();

    //Code l'action du bouton de démarrage
    @FXML
    void btnStartClick(){
        if(!TargetFound) {
            if (isOn) {
                btnPlayPause.setImage(playIcon);
                isOn = false;
                Situation = 3;
            } else {
                btnPlayPause.setImage(pauseIcon);
                isOn = true;
                Situation = 1;
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

    //Code l'action du bouton de redémarrage
    @FXML
    void btnResetClick(){
        btnPlayPause.setImage(playIcon);
        TargetFound=false;
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

            activeTheme[3] = new Image(activeTheme[3].getUrl(),dimensionCaneva[0], dimensionCaneva[1],false,false);
            lbl_PositionTarget.setText("Position of the target : ( " + posCible[0] + " ; " + posCible[1] + " )");
            lbl_TargetComm.setText("Radius communication target : " + agentsDetectionRange);
            lbl_AgentComm.setText("Radius communication agent : " + agentsCommunicationRange);

            //Force un reset quand nouveau fichier
            btnResetClick();

            btnPlayPause.setDisable(false);
            btnPlayPause.setOpacity(1);
            btnReset.setDisable(false);
            btnReset.setOpacity(1);
            btnExport.setDisable(false);
            btnExport.setOpacity(1);
            themeList.setDisable(false);

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

    void updateTheme(){
        interfaceBG.setImage(activeTheme[4]);
        for(int i = 0; i < GestionObjects.NbrAgent;i++){
            GestionObjects.agents[i].changeImage(activeTheme[0], activeTheme[1]);
        }
        activeTheme[3]=new Image(activeTheme[3].getUrl(), dimensionCaneva[1],dimensionCaneva[1],false,false );
        GestionObjects.target.changeImage(activeTheme[2],null);
    }

    @FXML
    void spaceThemeClick(){
        activeTheme = spaceTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void hiveThemeClick(){
        activeTheme = hiveTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void cityThemeClick(){
        activeTheme = cityTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void policeThemeClick(){
        activeTheme = policeTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void sharkThemeClick(){
        activeTheme = sharkTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void dragonThemeClick(){
        activeTheme = dragonTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void luffyThemeClick(){
        activeTheme = luffyTheme.clone();
        updateTheme();
        disableCustomChoice();
    }

    @FXML
    void customThemeClicked(){
        bgSP.setVisible(true);
        agentSP.setVisible(true);
        targetSP.setVisible(true);

        bgLBL.setVisible(true);
        agentLBL.setVisible(true);
        targetLBL.setVisible(true);
    }
    void disableCustomChoice(){
        bgSP.setVisible(false);
        agentSP.setVisible(false);
        targetSP.setVisible(false);

        bgLBL.setVisible(false);
        agentLBL.setVisible(false);
        targetLBL.setVisible(false);
    }

    @FXML
    void bgSpaceClicked(){
        activeTheme[3]=spaceTheme[3];
        activeTheme[4]=spaceTheme[4];
        updateTheme();
    }
    @FXML
    void bgHiveClicked(){
        activeTheme[3]=hiveTheme[3];
        activeTheme[4]=hiveTheme[4];
        updateTheme();
    }
    @FXML
    void bgCityClicked(){
        activeTheme[3]=cityTheme[3];
        activeTheme[4]=cityTheme[4];
        updateTheme();
    }
    @FXML
    void bgPoliceClicked(){
        activeTheme[3]=policeTheme[3];
        activeTheme[4]=policeTheme[4];
        updateTheme();
    }
    @FXML
    void bgSharkClicked(){
        activeTheme[3]=sharkTheme[3];
        activeTheme[4]=sharkTheme[4];
        updateTheme();
    }
    @FXML
    void bgDragonClicked(){
        activeTheme[3]=dragonTheme[3];
        activeTheme[4]=dragonTheme[4];
        updateTheme();
    }
    @FXML
    void bgLuffyClicked(){
        activeTheme[3]=luffyTheme[3];
        activeTheme[4]=luffyTheme[4];
        updateTheme();
    }

    @FXML
    void agentSpaceClicked(){
        activeTheme[0]=spaceTheme[0];
        activeTheme[1]=spaceTheme[1];
        updateTheme();
    }
    @FXML
    void agentHiveClicked(){
        activeTheme[0]=hiveTheme[0];
        activeTheme[1]=hiveTheme[1];
        updateTheme();
    }
    @FXML
    void agentCityClicked(){
        activeTheme[0]=cityTheme[0];
        activeTheme[1]=cityTheme[1];
        updateTheme();
    }
    @FXML
    void agentPoliceClicked(){
        activeTheme[0]=policeTheme[0];
        activeTheme[1]=policeTheme[1];
        updateTheme();
    }
    @FXML
    void agentSharkClicked(){
        activeTheme[0]=sharkTheme[0];
        activeTheme[1]=sharkTheme[1];
        updateTheme();
    }
    @FXML
    void agentDragonClicked(){
        activeTheme[0]=dragonTheme[0];
        activeTheme[1]=dragonTheme[1];
        updateTheme();
    }
    @FXML
    void agentLuffyClicked(){
        activeTheme[0]=luffyTheme[0];
        activeTheme[1]=luffyTheme[1];
        updateTheme();
    }

    @FXML
    void targetSpaceClicked(){
        activeTheme[2]=spaceTheme[2];
        updateTheme();
    }
    @FXML
    void targetHiveClicked(){
        activeTheme[2]=hiveTheme[2];
        updateTheme();
    }
    @FXML
    void targetCityClicked(){
        activeTheme[2]=cityTheme[2];
        updateTheme();
    }
    @FXML
    void targetPoliceClicked(){
        activeTheme[2]=policeTheme[2];
        updateTheme();
    }
    @FXML
    void targetSharkClicked(){
        activeTheme[2]=sharkTheme[2];
        updateTheme();
    }
    @FXML
    void targetDragonClicked(){
        activeTheme[2]=dragonTheme[2];
        updateTheme();
    }
    @FXML
    void targetLuffyClicked(){
        activeTheme[2]=luffyTheme[2];
        updateTheme();
    }

    @FXML
    void onFileMouseIn(){
            btnSelectFile.setFitHeight(46);
            btnSelectFile.setFitWidth(46);
            btnSelectFile.setLayoutX(127);
            btnSelectFile.setLayoutY(94);
    }
    @FXML
    void onFileMouseOut(){
        btnSelectFile.setFitHeight(40);
        btnSelectFile.setFitWidth(40);
        btnSelectFile.setLayoutX(130);
        btnSelectFile.setLayoutY(97);
    }

    @FXML
    void onPlayPauseMouseIn(){
        if(!TargetFound) {
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
    @FXML
    void onPlayPauseMouseOut(){
        btnPlayPause.setFitHeight(50);
        btnPlayPause.setFitWidth(50);
        btnPlayPause.setLayoutX(128);
        btnPlayPause.setLayoutY(693);
    }

    @FXML
    void onResetMouseIn(){
        btnReset.setFitHeight(58);
        btnReset.setFitWidth(58);
        btnReset.setLayoutX(40);
        btnReset.setLayoutY(689);
    }
    @FXML
    void onResetMouseOut(){
        btnReset.setFitHeight(50);
        btnReset.setFitWidth(50);
        btnReset.setLayoutX(44);
        btnReset.setLayoutY(693);
    }

    @FXML
    void onExportMouseIn(){
        btnExport.setFitHeight(66);
        btnExport.setFitWidth(66);
        btnExport.setLayoutX(208);
        btnExport.setLayoutY(685);
    }
    @FXML
    void onExportMouseOut(){
        btnExport.setFitHeight(56);
        btnExport.setFitWidth(56);
        btnExport.setLayoutX(213);
        btnExport.setLayoutY(690);
    }
}
