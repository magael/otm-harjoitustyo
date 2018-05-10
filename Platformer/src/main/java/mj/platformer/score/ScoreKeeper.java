package mj.platformer.score;

import java.util.ArrayList;

/**
 * The score and winning state handling class.
 * 
 * @author Maguel
 */
public class ScoreKeeper {

    private long score;
    private int playerScoringPosition;
    private int scoringPositionIndex;
    private ArrayList<Integer> scoringPositions;
    private String startText;
    private boolean gameWon;

    /**
     * Constructor for the ScoreKeeper class.
     * 
     * @param playerStartX
     */
    public ScoreKeeper(int playerStartX) {
        score = 0;
        scoringPositions = new ArrayList<>();
        scoringPositionIndex = 0;
        playerScoringPosition = playerStartX;
        startText = "";
        gameWon = false;
    }

    /**
     * The score is updated when the player passes a moving GameObject.
     * This is simulated with the playerScoringPosition, that moves towards the
     * original positions of each gameObject on the actual gameObjectSpeed.
     * The score is increased more the larger the level and the further ahead
     * in the level the passing object is.
     * 
     * @param gameStart whether the actual game is running
     * @param gameObjectSpeed
     * @param level
     */
    public void updateScore(boolean gameStart, double gameObjectSpeed, int level) {
        playerScoringPosition += gameObjectSpeed;
        if (scoringPositionIndex < scoringPositions.size()
                && playerScoringPosition >= scoringPositions.get(scoringPositionIndex)) {
            score += 50 * level * (scoringPositionIndex + 1);
            scoringPositionIndex += 1;
            startText = "";
        }
        setState(gameStart);
    }

    /**
     * Adds the object's x position to the list of GameObject positions.
     * @param objectX
     */
    public void addPosition(int objectX) {
        scoringPositions.add(objectX);
    }

    public long getScore() {
        return score;
    }

    public String getStartText() {
        return startText;
    }

    public boolean getGameWon() {
        return gameWon;
    }

    private void setState(boolean gameStart) {
        if (gameStart && score == 0) {
            startText = "Dodge the spikes!\nPress Space to jump";
        } else if (playerScoringPosition > scoringPositions.get(scoringPositions.size() - 1)) {
            startText = "Congratulations!\nLevel cleared!\nPress 'R' to play again."
                    + "\nor 'B' to go back to the menu.";
            gameWon = true;
        }
    }
}
