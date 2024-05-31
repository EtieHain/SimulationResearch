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
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    static boolean Play = true;
    @Override
    public void start(Stage stage) throws IOException {
        LectureConfig.LectureFichier();
        stage.setTitle( "Simulation Research" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );
        Canvas canvas = new Canvas( LectureConfig.dimensionCaneva[0], LectureConfig.dimensionCaneva[1] );
        root.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();
        GestionObjects.creationObjects(5);
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                float simulationTime = (float)(currentNanoTime-startNanoTime)/1000000000f;
                if(Play) {
                    int NbrFound = 0;
                    for (int idx = 0; idx < GestionObjects.NbrAgent; idx++) {
                        if(GestionObjects.agents[idx].getState()[0]&&!GestionObjects.agents[idx].getState()[1])GestionObjects.testCommunication(idx);
                        else GestionObjects.agents[idx].targetDetection();
                        if(!GestionObjects.agents[idx].getState()[0]||!GestionObjects.agents[idx].getState()[1])GestionObjects.agents[idx].Deplacement();
                        if (GestionObjects.agents[idx].getState()[0]) NbrFound++;
                    }
                    GestionObjects.Affichage(gc);
                    if (NbrFound >= (int) (GestionObjects.NbrAgent / 2 + 1)) {
                        Play = false;
                        System.out.println((simulationTime*(LectureConfig.agentSpeed*100/500)));
                    }
                }
            }
        }.start();
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}