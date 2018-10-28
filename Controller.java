package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txt1;

    @FXML
    private TextField txt2;

    @FXML
    private TextField txt3;

    @FXML
    private TextField txt4;

    @FXML
    private Button btn;

    @FXML
    void initialize() {
        btn.setOnAction(event -> {
                Stage stage = new Stage();
                stage.setTitle("Plot");

                AppController appController = new AppController();
                appController.plot(stage,Double.parseDouble(txt1.getText()),Double.parseDouble(txt2.getText()),Double.parseDouble(txt3.getText()),Double.parseDouble(txt4.getText()));
        });
    }
}

