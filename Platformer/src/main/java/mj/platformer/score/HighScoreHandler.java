package mj.platformer.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class HighScoreHandler implements HighScoreDao {

    private HashMap<Integer, Long> highScores;

    public HighScoreHandler() {
        this.highScores = new HashMap<>();
    }

    @Override
    public long getHighScore(int level) {
        if (highScores.containsKey(level)) {
            return highScores.get(level);
        }
        return 0;
    }

    @Override
    public void setHighScore(long score, int level) {
        highScores.put(level, score);
    }

    @Override
    public void writeHighScore(String filePath, int levelCount) {
        try {
            // Create a new file if one is not found at project / executable root
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(writer);

            for (int i = 1; i <= levelCount; i++) {
                bw.write(Long.toString(highScores.get(i)) + "\n");
            }

            // Close the output stream
            bw.close();
        } catch (Exception e) {
            System.err.println("Error when attempting to write to or create a high score file: " + e.getMessage());
        }
    }

    @Override
    public void readHighScore(String filePath, int levelCount) {
        try {
            FileInputStream stream = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);

            readScores(br);

            br.close();
        } catch (Exception e) {
            noFileFound(levelCount);
        }
    }

    private void noFileFound(int levelCount) {
        for (int i = 1; i <= levelCount; i++) {
            highScores.put(i, (long) 0);
        }

        System.out.println("No viable high score file found. High score set to 0.");
    }

    private void readScores(BufferedReader br) throws IOException, NumberFormatException {
        String line = "";
        int i = 1;
        while ((line = br.readLine()) != null) {
            highScores.put(i, Long.parseLong(line));
            i++;
        }
    }
}
