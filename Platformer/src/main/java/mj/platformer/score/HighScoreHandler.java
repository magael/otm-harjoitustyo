package mj.platformer.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class HighScoreHandler {

    private int highScore;
    private HashMap<Integer, Integer> highScores;

    public HighScoreHandler() {
        this.highScores = new HashMap<>();
    }

    public int getHighScore() {
        return highScore;
    }

    public int getHighScore(int level) {
        if (highScores.containsKey(level)) {
            return highScores.get(level);
        }
        return 0;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setHighScore(int score, int level) {
        highScores.put(level, score);
    }

    public void writeHighScore(String filePath, int levelCount) {
        try {
            // Create a new file if one is not found at project / executable root
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(writer);

            if (levelCount < 2) {
                bw.write(Integer.toString(highScore));
            } else {
                for (int i = 1; i <= levelCount; i++) {
                    bw.write(Integer.toString(highScores.get(i)) + "\n");
                }
            }
            
            // Close the output stream
            bw.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void readHighScore(String filePath, int levelCount) {
        try {
            FileInputStream stream = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);

            readScores(br, levelCount);

            br.close();
        } catch (Exception e) {
            noFileFound(levelCount);
        }
    }

    private void noFileFound(int levelCount) {
        if (levelCount > 1) {
            for (int i = 1; i <= levelCount; i++) {
                highScores.put(i, 0);
            }
        } else {
            highScore = 0;
        }

        System.out.println("No viable high score file found. High score set to 0.");
    }

    private void readScores(BufferedReader br, int levelCount) throws IOException, NumberFormatException {
        String line = "";
        int i = 1;
        while ((line = br.readLine()) != null) {
            if (levelCount < 2) {
                highScore = Integer.parseInt(line);
                break;
            }
            highScores.put(i, Integer.parseInt(line));
            i++;
        }
    }
}
