
package mj.platformer.level;

import java.util.ArrayList;

public class ScoreKeeper {
    
    private int score;
    private int obstacleSpeed;
    private int playerScoringPosition;
    private int scoringPositionIndex;
    private ArrayList<Integer> scoringPositions;
    private String startText;
    private boolean gameWon;

    public ScoreKeeper(int obstacleSpeed, int playerStartX) {
        score = 0;
        this.obstacleSpeed = obstacleSpeed;
        scoringPositions = new ArrayList<>();
        scoringPositionIndex = 0;
        playerScoringPosition = playerStartX;
        startText = "";
        gameWon = false;
    }
    
    public void updateScore(boolean gameStart) {
        playerScoringPosition += obstacleSpeed;
        if (scoringPositionIndex < scoringPositions.size()
                && playerScoringPosition >= scoringPositions.get(scoringPositionIndex)) {
            score += 100 * (scoringPositionIndex + 1);
            scoringPositionIndex += 1;
            startText = "";
        }
        if (gameStart && score == 0) {
            startText = "Dodge the spikes!\nPress Space to jump";
        } else if (playerScoringPosition > scoringPositions.get(scoringPositions.size() - 1)) {
            startText = "Congratulations!\nLevel cleared!\nPress 'R' to play again.";
            gameWon = true;
        }
    }

    public void addPosition(int obstacleX) {
        scoringPositions.add(obstacleX);
    }

    public int getScore() {
        return score;
    }

    public String getStartText() {
        return startText;
    }
    
    public boolean getGameWon() {
        return gameWon;
    }
}
