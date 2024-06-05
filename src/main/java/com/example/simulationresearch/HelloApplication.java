package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import Objects.Agent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.simulationresearch.HelloController;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static LectureConfig.LectureConfig.agentsCommunicationRange;
import static LectureConfig.LectureConfig.agentsDetectionRange;
import static com.example.simulationresearch.InterfaceController.*;

import com.example.simulationresearch.InterfaceController.*;

public class HelloApplication extends Application {
    static File file;
    static int Demarage = 0;
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        //Lis le fichier de base
        file = new File("src/main/resources/configuration.txt");
        LectureConfig.LectureFichier(file);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(),1100,800);
        stage.setTitle("Spaceship");
        stage.setScene(scene);

        HelloController Ctrl_Global = fxmlLoader.getController();



        final long startNanoTime = System.nanoTime();
        System.out.println(startNanoTime);
        GestionObjects.creationObjects(5, imageAgent, imageTarget);
        Ctrl_Global.Afficher(BackGround);
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //LectureConfig.LectureFichier(file);

                if(Situation == 1) {
                    //Afficher les objets et l'animation
                    Ctrl_Global.Afficher(BackGround);
                }
                else if(Situation == 2){
                    //Recrer les objets a leurs positions initial
                    GestionObjects.creationObjects(5,imageAgent, imageTarget);
                    //Affiche les nouveaux objet a leur position initial en boucle tant que Situation rest a 2
                    Ctrl_Global.Afficher(BackGround);
                }
                else{

                }
            }
        }.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}