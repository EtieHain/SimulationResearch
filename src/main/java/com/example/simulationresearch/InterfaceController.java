package com.example.simulationresearch;

import javafx.fxml.FXML;

public class InterfaceController {

    static int Situation = 1;

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
