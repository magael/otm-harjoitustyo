package mj.platformer.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * HighScoreHandler handles file IO to read, write and store the high score.
 * The high score for each level is stored in a local external textfile.
 * 
 * @author Maguel
 */
public class HighScoreHandler implements HighScoreDao {

    private HashMap<Integer, Long> highScores;

    /**
     * Constructor for the HighScoreHandler class.
     */
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

    /**
     * Write the high score for each level from given file path.
     * Creates a new file if one is not found at project / executable root.
     * 
     * @param filePath
     * @param levelCount amount of levels in the game
     */
    @Override
    public void writeHighScore(String filePath, int levelCount) {
        try {
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(writer);

            for (int i = 1; i <= levelCount; i++) {
                bw.write(Long.toString(highScores.get(i)) + "\n");
            }

            bw.close();
        } catch (Exception e) {
            System.err.println("Error when attempting to write to or create a high score file: " + e);
        }
    }

    /**
     * Read the high score for each level from given file path.
     * If the read attempt fails, the high score is set to 0.
     * 
     * @param filePath
     * @param levelCount amount of levels in the game
     */
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
