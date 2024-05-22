module com.example.simulationresearch {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simulationresearch to javafx.fxml;
    exports com.example.simulationresearch;
}