package mj.platformer.ui.audio;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AudioHandler {

    private HashMap<String, AudioClip> clips;
    private MediaPlayer musicPlayer;

    public AudioHandler() {
        clips = new HashMap<>();
    }

    public void addClip(String source) {
        try {
            AudioClip clip = new AudioClip(new File(source).toURI().toString());
            clips.put(source, clip);
        } catch (Exception e) {
            System.out.println("Error: Audioclip unavailable.");
        }
    }

    public void addMusic(String source) {
        try {
            Media sound = new Media(new File(source).toURI().toString());
            musicPlayer = new MediaPlayer(sound);
            playMusic();
        } catch (Exception e) {
            System.out.println("Error: Music file unavailable.");
        }
    }

    public void playClip(String source) {
        if (clips.containsKey(source)) {
            clips.get(source).play();
        }
    }

    public void playMusic() {
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }

}
