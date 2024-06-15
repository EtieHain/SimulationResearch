package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.ConfigReading;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.example.simulationresearch.InterfaceController.*;

public class HelloApplication extends Application {


    private int NbrAgent = 5;
    static File file;

    static long startTime;
    static float simulationTime = 0.00f;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simulation research");
        stage.setScene(scene);

        //Créer un objet de la class HelloController
        HelloController Ctrl_Global = fxmlLoader.getController();

        final long startNanoTime = System.nanoTime();
        startTime = startNanoTime;

        //Boucle d'affichage
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //Code l'état de fonctionement de la simulation
                if(Situation == 1) {
                    Ctrl_Global.Afficher(BackGround);
//                    simulationTime = ((currentNanoTime-startTime)/1000000000f)/**(ConfigReading.agentSpeed/5)*/;
                    simulationTime++;
                }
                //Code l'état de pause de la simulation
                else if(Situation == 3){
                    Ctrl_Global.AffichageStop(BackGround);
                }
                //Code l'état de redémarrage de la simulation
                else if(Situation == 2){
                    GestionObjects.creationObjects(NbrAgent, imageAgent, imageTarget);
                    Ctrl_Global.AffichageStop(BackGround);
//                    startTime=currentNanoTime;
                    simulationTime=0;
                }
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}