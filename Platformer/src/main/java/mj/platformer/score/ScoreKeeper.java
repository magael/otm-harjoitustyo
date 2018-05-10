package mj.platformer.score;

import java.util.ArrayList;

public class ScoreKeeper {

    private long score;
    private int playerScoringPosition;
    private int scoringPositionIndex;
    private ArrayList<Integer> scoringPositions;
    private String startText;
    private boolean gameWon;

    public ScoreKeeper(int playerStartX) {
        score = 0;
        scoringPositions = new ArrayList<>();
        scoringPositionIndex = 0;
        playerScoringPosition = playerStartX;
        startText = "";
        gameWon = false;
    }

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

    public void addPosition(int obstacleX) {
        scoringPositions.add(obstacleX);
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
