package com.memorycards;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Memory Card Game - Flipping Tiles");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResource("images/logo.png").toString());
        stage.getIcons().add(icon);
        stage.setOnCloseRequest(event -> {
            stage.getOnCloseRequest();
            System.exit(0);
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}