package com.example.simulationresearch;

import GestionObjects.GestionObjects;
import LectureConfig.LectureConfig;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.example.simulationresearch.InterfaceController.Situation;

public class HelloApplication extends Application {

    static File file;

    static long startTime;
    static float sum;
    static float n;
    @Override
    public void start(Stage stage) throws IOException {
        File file = new File("src/main/resources/configuration.txt");
        LectureConfig.LectureFichier(file);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hello-view.fxml"));
        //FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("Interface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1100,800);
        stage.setTitle("CACA");
        stage.setScene(scene);

        HelloController Ctrl_Global = fxmlLoader.getController();
        //InterfaceController Ctrl_Interface = fxmlLoader2.getController();

        final long startNanoTime = System.nanoTime();
        startTime = startNanoTime;
        GestionObjects.creationObjects(5);

        new AnimationTimer()
        {
            float simulationTime=0;
            public void handle(long currentNanoTime)
            {
                if(Situation == 1) {

                    Ctrl_Global.Afficher();
                    if(Situation==2){
                        n++;
                        simulationTime = ((currentNanoTime-startTime)/1000000000f)*(LectureConfig.agentSpeed/5);
                        System.out.println("Test n° "+n+" - Target found in " +simulationTime + " seconds");
                        sum+=simulationTime;
                        System.out.println("Average time : "+sum/n+" seconds");
                    }
                }
                else if(Situation == 0){

                }
                else{
                    GestionObjects.creationObjects(5);
//                    Ctrl_Global.Afficher();
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