
package mj.platformer.level;

import java.util.ArrayList;
import javafx.scene.text.Text;

public class ScoreKeeper {
    
    int score;
    int obstacleSpeed;
    int playerScoringPosition;
    int scoringPositionIndex;
    ArrayList<Integer> scoringPositions;

    public ScoreKeeper(int obstacleSpeed, int playerStartX) {
        score = 0;
        this.obstacleSpeed = obstacleSpeed;
        scoringPositions = new ArrayList<>();
        scoringPositionIndex = 0;
        playerScoringPosition = playerStartX;
    }
    
    public void updateScore(Text scoreText, Text startText, boolean gameStart) {
        playerScoringPosition += obstacleSpeed;
        if (scoringPositionIndex < scoringPositions.size()
                && playerScoringPosition >= scoringPositions.get(scoringPositionIndex)) {
            score += 100 * (scoringPositionIndex + 1);
            scoringPositionIndex += 1;
            scoreText.setText("Score: " + score);
            startText.setText("");
        }
        if (gameStart && score == 0) {
            startText.setText("Dodge the spikes!\nPress Space to jump");
        }
    }

    public void addPosition(int obstacleX) {
        scoringPositions.add(obstacleX);
    }

    public int getScore() {
        return score;
    }
}
