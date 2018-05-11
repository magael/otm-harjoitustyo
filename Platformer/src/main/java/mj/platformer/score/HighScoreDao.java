package mj.platformer.score;

/**
 *
 * @author Maguel
 */
public interface HighScoreDao {

    long getHighScore(int level);

    void setHighScore(long score, int level);

    /**
     * Read the high score for each level from given file path.
     * 
     * @param filePath
     * @param levelCount amount of levels in the game
     */
    void readHighScore(String filePath, int levelCount);

    /**
     * Write the high score for each level from given file path.
     * 
     * @param filePath
     * @param levelCount amount of levels in the game
     */
    void writeHighScore(String filePath, int levelCount);

}
