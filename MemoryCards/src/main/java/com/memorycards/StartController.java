package com.memorycards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML Button startBtn;
    @FXML Button helpBtn;
    @FXML ImageView view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image logo = new Image(StartController.class.getResource("images/logoTR.png").toString());
        view.setImage(logo);
    }

    public void start(ActionEvent event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    public void help(ActionEvent event) {

    }
}