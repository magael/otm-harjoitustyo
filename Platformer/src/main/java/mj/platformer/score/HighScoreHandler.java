
package mj.platformer.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class HighScoreHandler {
    
    private int highScore;

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
    
    public void writeHighScore(String filePath) {
        try {
            // Create a new file if one is not found at project / executable root
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(Integer.toString(highScore));
            // Close the output stream
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void readHighScore(String filePath) {
        try {
            FileInputStream stream = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                highScore = Integer.parseInt(line);
            }
            br.close();
        } catch (Exception e) {
        }
    }
}
