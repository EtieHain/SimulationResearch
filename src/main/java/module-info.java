module com.example.simulationresearch {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.bytedeco.ffmpeg;
    requires org.bytedeco.javacv;
    requires org.bytedeco.opencv;


    opens com.example.simulationresearch to javafx.fxml;
    exports com.example.simulationresearch;
}