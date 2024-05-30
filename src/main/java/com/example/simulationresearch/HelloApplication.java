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
        System.out.println(startNanoTime);
        GestionObjects.creationObjects(1);
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                for(int idx = 0;idx < GestionObjects.NbrAgent;idx++)
                {
                    GestionObjects.agents[idx].Deplacement();
                }
                GestionObjects.Affichage(gc);
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}