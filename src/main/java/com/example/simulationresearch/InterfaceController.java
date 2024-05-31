package com.example.simulationresearch;

import LectureConfig.LectureConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Random;

public class InterfaceController {

    static int Situation = 0;

    @FXML
    void btnStartClick(){
        Situation = 1;
    }
    @FXML
    void btnStopClick(){
        Situation = 0;
    }
    @FXML
    void btnResetClick(){
        Random rand = new Random();
        LectureConfig.dimensionCaneva[0] = rand.nextInt(501)+200;
        LectureConfig.dimensionCaneva[1] = rand.nextInt(501)+200;
        LectureConfig.posCible[0] = rand.nextInt(LectureConfig.dimensionCaneva[0]+1);
        LectureConfig.posCible[1] = rand.nextInt(LectureConfig.dimensionCaneva[1]+1);
        System.out.println(LectureConfig.dimensionCaneva[0]+"x"+LectureConfig.dimensionCaneva[1]);
        Situation = 2;
    }
}
