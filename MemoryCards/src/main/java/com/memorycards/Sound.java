package com.memorycards;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    private static Media media;
    private static MediaPlayer mediaPlayer;

    public static void playSound(String sound) {
        media = new Media(Sound.class.getResource("sounds/" + sound + ".mp3").toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

}
