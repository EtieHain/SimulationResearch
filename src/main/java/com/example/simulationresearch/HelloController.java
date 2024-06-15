package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import SwingFXUtils.SwingFXUtils;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.global.opencv_imgcodecs;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static com.example.simulationresearch.InterfaceController.*;
import static com.example.simulationresearch.HelloApplication.*;


public class HelloController {
    @FXML
    private Canvas myCanvas;                //Variable du canvas ou serra affiché la simulation
    @FXML
    private Label lbl_Timmer;               //Variable qui affichera le temps de simulation (valeur du Timmer)
    @FXML
    private AnchorPane ap;
    @FXML
    private ScrollPane sp;
    private int nbrImg = 0;
    private float lastTime = 0f;
    private File[] files;


    public void Afficher(Image BackGround) {
        myCanvas.setWidth(ConfigReading.dimensionCaneva[0]);
        myCanvas.setHeight(ConfigReading.dimensionCaneva[1]);
        myCanvas.setLayoutX((ap.getWidth() - myCanvas.getWidth()) / 2);
        myCanvas.setLayoutY((ap.getHeight() - myCanvas.getHeight()) / 2);

        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        int NbrFound = 0;
        for (int idx = 0; idx < GestionObjects.NbrAgent; idx++) {
            if (GestionObjects.agents[idx].getState()[0] && !GestionObjects.agents[idx].getState()[1])
                GestionObjects.testCommunication(idx);
            else GestionObjects.agents[idx].targetDetection();
            if (!GestionObjects.agents[idx].getState()[0] || !GestionObjects.agents[idx].getState()[1])
                GestionObjects.agents[idx].Deplacement();
            if (GestionObjects.agents[idx].getState()[0]) NbrFound++;
        }

        GestionObjects.Affichage(gc, BackGround);
        if (NbrFound >= GestionObjects.NbrObjectif) {
            InterfaceController.Situation = 3;
        }

        //Code qui affiche la valeur du Timmer dans son label
        float ResearchTime = 0.00f;            //Créer une variable float
        //Stock la variable du Timmer arrondie à deux dixième dans la variable créer avant
        ResearchTime = Math.round(simulationTime*100.0f)/100.0f;
        System.out.println(simulationTime);
        //Change le texte du label avec la valeur du Timmer
        lbl_Timmer.setText(String.valueOf("Target found in " + ResearchTime));
        if(simulationTime - lastTime > 0.02f)
        {
            // Créer une WritableImage pour capturer le contenu du Canvas
            WritableImage writableImage = new WritableImage((int) myCanvas.getWidth(), (int) myCanvas.getHeight());
            myCanvas.snapshot(null, writableImage);

            // Créer un Task pour la création et l'enregistrement de l'image
            Task<Void> createImageTask = new Task<>() {
                @Override
                protected Void call() {

                    // Enregistrer l'image dans un fichier
                    File file = new File(  "Images/" +String.format("%04d",nbrImg) + ".png");
                    nbrImg++;
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                        System.out.println("Image enregistrée sous : " + file.getAbsolutePath());
                    } catch (IOException e) {
                        System.err.println("Erreur lors de l'enregistrement de l'image : " + e.getMessage());
                    }
                    return null;
                }
                @Override
                protected void failed() {
                    super.failed();
                    // Afficher une alerte ou mettre à jour l'interface utilisateur après une erreur
                    System.out.println("erreur de création de l'image");
                }
            };

            // Exécuter le Task dans un autre thread
            new Thread(createImageTask).start();
            lastTime = simulationTime;
        }
    }

    public void AffichageStop (Image BackGround)
    {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        GestionObjects.Affichage(gc, BackGround);

        if(simulationTime > 1)
        {
            String outputFilePath = "output.mp4";
            int frameRate = (int) (nbrImg / simulationTime); // Frames per second

            // Create a FFmpegFrameRecorder instance
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, 800, 800);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            recorder.setFrameRate(frameRate);
            recorder.setVideoBitrate(25 * 1024 * 1024);

            // Start the recorder inside a try-catch block
            try {
                recorder.startUnsafe();
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                e.printStackTrace();
                System.err.println("Erreur lors du démarrage de l'enregistreur.");
                return;
            }
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

            // Directory containing images
            File dir = new File("Images");
            File[] files = dir.listFiles((d, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

            if (files != null) {
                for (File file : files) {
                    // Read the image file
                    IplImage image = opencv_imgcodecs.cvLoadImage(file.getAbsolutePath());

                    // Convert the image to a Frame
                    Frame frame = converter.convert(image);

                    // Record the frame
                    try {
                        recorder.record(frame);
                    } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                        e.printStackTrace();
                        System.err.println("Erreur lors de l'enregistrement du cadre : " + file.getName());
                    }

                    // Release the image
                    image.release();
                }
            }

            // Stop the recorder inside a try-catch block
            try {
                recorder.stop();
                recorder.release();
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'arrêt de l'enregistreur.");
            }
            int a = 0;
        }
    }
}