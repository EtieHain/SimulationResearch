package com.example.simulationresearch;

import GestionObjects.GestionObjects;
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
        stage.setTitle( "Timeline Example" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );
        Canvas canvas = new Canvas( 600, 600 );
        root.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();
        System.out.println(startNanoTime);
        GestionObjects.creationObjects(5);
        new AnimationTimer()
        {

            public void handle(long currentNanoTime)
            {
                GestionObjects.Affichage(gc);
                Agent.angle++;
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}