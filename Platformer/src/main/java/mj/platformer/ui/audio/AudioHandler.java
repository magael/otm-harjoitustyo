package mj.platformer.ui.audio;

import java.io.File;
import java.util.HashMap;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * AudioHandler class that plays (and stops) music clips and background music.
 *
 * @author Maguel
 */
public class AudioHandler {

    private HashMap<String, AudioClip> clips;
    private MediaPlayer musicPlayer;

    /**
     * Constructor for the AudioHandler class.
     */
    public AudioHandler() {
        clips = new HashMap<>();
    }

    /**
     * Adds a clip to a map of AudioClips.
     * @param source the file path acts as key to the AudioClip object
     */
    public void addClip(String source) {
        try {
            AudioClip clip = new AudioClip(new File(source).toURI().toString());
            clips.put(source, clip);
        } catch (Exception e) {
            System.out.println("Error: Audioclip unavailable.");
        }
    }

    /**
     * Initializes the private MediaPlayer and gives it an audio file to be set
     * as background music.
     * @param source file path of the audiofile
     */
    public void addMusic(String source) {
        try {
            Media sound = new Media(new File(source).toURI().toString());
            musicPlayer = new MediaPlayer(sound);
        } catch (Exception e) {
            System.out.println("Error: Music file unavailable.");
        }
    }

    /**
     * Plays the given clip, provided it has been added to the private map "clips".
     * @param source the file path acts as key to the AudioClip object
     */
    public void playClip(String source) {
        if (clips.containsKey(source)) {
            clips.get(source).play();
        } else {
            System.out.println("Error: Audioclip unavailable.");
        }
    }

    /**
     * Starts playing the background music on an endless loop.
     */
    public void playMusic() {
        try {
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Error: Music unavailable.");
        }
    }

    /**
     * Stops playing the background music.
     */
    public void stopMusic() {
        try {
            musicPlayer.stop();
        } catch (Exception e) {
            System.out.println("Error: Music unavailable.");
        }
    }

}
