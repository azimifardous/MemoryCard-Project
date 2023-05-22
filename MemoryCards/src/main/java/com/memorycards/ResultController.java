package com.memorycards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultController {
    @FXML ImageView resultImage;
    @FXML Label resultMoves;
    @FXML Label timePlayed;

    public void setResults(boolean hasWon, int timePlayed, int moves) {
        resultMoves.setText(String.valueOf(moves));
        if (hasWon) {
            if (timePlayed < 60) {
                if (timePlayed < 10) this.timePlayed.setText("0:0" + timePlayed);
                else this.timePlayed.setText("0:" + timePlayed);
            } else {
                timePlayed -= 60;
                if (timePlayed < 10) this.timePlayed.setText("1:0" + timePlayed);
                else this.timePlayed.setText("1:" + timePlayed);
            }
        } else this.timePlayed.setText("2:00");

        Image result;
        result = (hasWon) ?
                new Image(getClass().getResource("images/ResultImages/Win.png").toString())
                : new Image(getClass().getResource("images/ResultImages/Lose.png").toString());
        resultImage.setImage(result);
    }
    public void playAgain(ActionEvent event) throws IOException {
        // Load Main controller
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    // simply close the program
    public void closeGame(){ System.exit(0); }
}
