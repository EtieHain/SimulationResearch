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
import com.example.simulationresearch.HelloController;

import java.io.IOException;
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        LectureConfig.LectureFichier();
        /*
        stage.setTitle( "Simulation Research" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );
        Canvas canvas = new Canvas( LectureConfig.dimensionCaneva[0], LectureConfig.dimensionCaneva[1] );
        root.getChildren().add( canvas );
         */

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1100,800);
        stage.setTitle("CACA");
        stage.setScene(scene);
        HelloController test = fxmlLoader.getController();

        final long startNanoTime = System.nanoTime();
        System.out.println(startNanoTime);
        GestionObjects.creationObjects(5);
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                test.Afficher();
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}