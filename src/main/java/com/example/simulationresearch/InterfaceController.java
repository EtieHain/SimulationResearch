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
        Situation = 2;
    }
}
