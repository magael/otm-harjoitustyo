package mj.platformer.score;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreKeeperTest {

    private ScoreKeeper sk;
    private int gameObjectSpeed;

    @Before
    public void setUp() {
        gameObjectSpeed = 5;
        int playerStartX = 0;
        sk = new ScoreKeeper(playerStartX);
    }
    
    @Test
    public void gameWonStartsAsFalse() {
        assertFalse(sk.getGameWon());
    }
    
    @Test
    public void gameWonIfPlayerClearsLevel() {
        sk.addPosition(-1);
        sk.updateScore(true, gameObjectSpeed, 1);
        assertTrue(sk.getGameWon());
    }
    
    @Test
    public void startTextIsWinTextIfPlayerClearsLevel() {
        sk.addPosition(-1);
        sk.updateScore(true, gameObjectSpeed, 1);
        assertEquals("Congratulations!\nLevel cleared!\nPress 'R' to play again."
                + "\nor 'B' to go back to the menu.", sk.getStartText());
    }

    @Test
    public void scoreStartsAsZero() {
        assertEquals(0, sk.getScore(), 0);
    }

    @Test
    public void scoreIncreasedWhenPlayerPassesScoringPosition() {
        sk.addPosition(5);
        sk.updateScore(true, gameObjectSpeed, 1);
        assertTrue(sk.getScore() > 0);
    }

    @Test
    public void scoreNotIncreasedIfPlayerNotPastScoringPosition() {
        sk.updateScore(true, gameObjectSpeed, 1);
        assertEquals(0, sk.getScore(), 0);
    }

    @Test
    public void startTextStartsAsEmpty() {
        assertEquals("", sk.getStartText());
    }

    @Test
    public void startTextEmptyWhenPlayerPassesScoringPosition() {
        sk.addPosition(5);
        sk.updateScore(true, gameObjectSpeed, 1);
        assertEquals("", sk.getStartText());
    }

    @Test
    public void startTextIsHelpTextIfPlayerNotPastScoringPosition() {
        sk.updateScore(true, gameObjectSpeed, 1);
        assertEquals("Dodge the spikes!\nPress Space to jump", sk.getStartText());
    }
}
