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
        File file = new File("src/main/resources/configuration.txt");
        ConfigReading.ConfigReading(file);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1100,800);
        stage.setTitle("Simulation research");
        stage.setScene(scene);

        HelloController Ctrl_Global = fxmlLoader.getController();

        final long startNanoTime = System.nanoTime();
        startTime = startNanoTime;
        GestionObjects.creationObjects(NbrAgent, imageAgent, imageTarget);

        new AnimationTimer()
        {
            //static float simulationTime=0;
            public void handle(long currentNanoTime)
            {
                if(Situation == 1) {
                    Ctrl_Global.Afficher(BackGround);
                    simulationTime = ((currentNanoTime-startTime)/1000000000f)*(ConfigReading.agentSpeed/5);

                }
                else if(Situation == 3){
                    Ctrl_Global.AffichageStop(BackGround);

                }
                else if(Situation == 2){
                    GestionObjects.creationObjects(5, imageAgent, imageTarget);
                    Ctrl_Global.AffichageStop(BackGround);
                    startTime=currentNanoTime;
//                    Situation=1;
                }
            }

        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}