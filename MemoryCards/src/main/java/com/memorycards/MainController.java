package com.memorycards;

import animatefx.animation.Shake;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {
    @FXML GridPane gridPane;
    @FXML Label move;
    @FXML Label time;
    Board board = new Board();
    Card firstCard = null;
    Card secondCard = null;
    Tracker tracker = new Tracker();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // as soon as the game starts set the initial cards view which is a question mark
       initialCardsView();
       // then populate the cards on the board randomly each time
       board.populateCards();
       // keep checking cards till a pair is found
       checkCardsView();
       // start the timer on the UI and update it constantly
       startTimer();
    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (tracker.timeLimit > 0) {
                    Platform.runLater(() -> {
                        if (tracker.timeLimit >= 60) {
                            if (tracker.getTimer() >= 10)
                                time.setText("1:" + (tracker.getTimer() - 1));
                            else
                                time.setText("1:0" + (tracker.getTimer() - 1));
                        } else {
                            if (tracker.getTimer() > 10)
                                time.setText("0:" + (tracker.getTimer() - 1));
                            else
                                time.setText("0:0" + (tracker.getTimer() - 1));
                        }
                        tracker.countDown--;
                        tracker.timeLimit--;
                    });
                } else {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }

    private void initialCardsView() {
        int card = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Image image = new Image(Board.class.getResource("images/q.png").toString());
                ImageView view = new ImageView(image);
                view.setFitWidth(40);
                view.setFitHeight(40);
                ((Button) gridPane.getChildren().get(card)).setGraphic(view);
                card++;
            }
        }
    }
    private void checkCardsView() {
        int card = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                gridPane.getChildren().get(card).setUserData(row + "," + col);
                int selectedRow = row;
                int selectedCol = col;
                gridPane.getChildren().get(card).setOnMouseClicked(event -> {
                    changeCardView(event);
                    setCardsView(selectedRow, selectedCol);
                });
                card++;
            }
        }
    }

    private void setCardsView(int selectedRow, int selectedCol) {
        if (firstCard == null) {
            firstCard = board.cards[selectedRow][selectedCol];
            firstCard.setFlipped(true);
        } else if (secondCard == null) {
            secondCard = board.cards[selectedRow][selectedCol];
            secondCard.setFlipped(true);

            // if they don't have the same reference address then compare
            if (firstCard != secondCard) {
                // keep updating the moves label
                int moves = tracker.trackMoves(firstCard, secondCard);
                move.setText("Moves: " + moves);

                // as soon as we get two cards we compare them if they matches
                int firstIndex = (firstCard.getRow() * 4) + firstCard.getColumn();
                int secondIndex = (secondCard.getRow() * 4) + secondCard.getColumn();

                Button selectedCard1 = (Button) gridPane.getChildren().get(firstIndex);
                Button selectedCard2 = (Button) gridPane.getChildren().get(secondIndex);

                // if they are paired, remains the same view, if not, then back to question mark
                if (firstCard.getName().equals(secondCard.getName())) {
                    selectedCard1.setDisable(true);
                    selectedCard2.setDisable(true);
                } else {
                    // flip them back to initial state
                    firstCard.setFlipped(false);
                    secondCard.setFlipped(false);

                    // play the error sound
                    Sound.playSound("error");

                    // Shake Animation if they aren't paired
                    new Shake(selectedCard1).play();
                    new Shake(selectedCard2).play();

                    Image image = new Image(Board.class.getResource("images/q.png").toString());
                    ImageView view1 = new ImageView(image);
                    view1.setFitWidth(40);
                    view1.setFitHeight(40);
                    ImageView view2 = new ImageView(image);
                    view2.setFitWidth(40);
                    view2.setFitHeight(40);

                    // Delaying the unpaired cards for 1.5s and then flip them backward
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), ev -> {
                        selectedCard1.setGraphic(view1);
                        selectedCard2.setGraphic(view2);
                    }));
                    timeline.play();
                }
            // else take another second card to compare
            } else secondCard = null;
        } else {
            // else take two others
            firstCard = board.cards[selectedRow][selectedCol];
            secondCard = null;

            // update the flip state
            firstCard.setFlipped(true);
        }
    }

    private void changeCardView(MouseEvent event) {
        Node source = (Node) event.getSource();
        String rowAndCol = (String) source.getUserData();

        int row = Integer.parseInt(rowAndCol.split(",")[0]);
        int col = Integer.parseInt(rowAndCol.split(",")[1]);

        String image = board.cards[row][col].getName();
        Image selectedImage = new Image(Board.class.getResource("images/" + image + ".png").toString());
        ImageView view = new ImageView(selectedImage);
        view.setFitHeight(50);
        view.setFitWidth(50);
        ((Button) source).setGraphic(view);
    }
}