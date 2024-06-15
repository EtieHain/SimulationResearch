package com.example.simulationresearch;

import GestionObjects.GestionObjects;
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

    static int frameCount = 0;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simulation research");
        stage.setScene(scene);

        //Créer un objet de la class HelloController
        HelloController Ctrl_Global = fxmlLoader.getController();

        final long startNanoTime = System.nanoTime();

        //Boucle d'affichage
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //Code l'état de fonctionement de la simulation
                if(Situation == 1) {
                    Ctrl_Global.Afficher(activeTheme[3]);
                    frameCount++;
                }
                //Code l'état de pause de la simulation
                else if(Situation == 3){
                    Ctrl_Global.AffichageStop(activeTheme[3]);
                }
                //Code l'état de redémarrage de la simulation
                else if(Situation == 2){
                    GestionObjects.creationObjects(NbrAgent, activeTheme[0], activeTheme[2]);
                    Ctrl_Global.AffichageStop(activeTheme[3]);
                    frameCount =0;
                }
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}