package mj.platformer.score;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreKeeperTest {

    private ScoreKeeper sk;

    @Before
    public void setUp() {
        int obstaclespeed = 5;
        int playerStartX = 0;
        sk = new ScoreKeeper(obstaclespeed, playerStartX);
    }
    
    @Test
    public void gameWonStartsAsFalse() {
        assertFalse(sk.getGameWon());
    }
    
    @Test
    public void gameWonIfPlayerClearsLevel() {
        sk.addPosition(-1);
        sk.updateScore(true);
        assertTrue(sk.getGameWon());
    }
    
    @Test
    public void startTextIsWinTextIfPlayerClearsLevel() {
        sk.addPosition(-1);
        sk.updateScore(true);
        assertEquals("Congratulations!\nLevel cleared!\nPress 'R' to play again.", sk.getStartText());
    }

    @Test
    public void scoreStartsAsZero() {
        assertEquals(0, sk.getScore(), 0);
    }

    @Test
    public void scoreIncreasedWhenPlayerPassesScoringPosition() {
        sk.addPosition(5);
        sk.updateScore(true);
        assertTrue(sk.getScore() > 0);
    }

    @Test
    public void scoreNotIncreasedIfPlayerNotPastScoringPosition() {
        sk.updateScore(true);
        assertEquals(0, sk.getScore(), 0);
    }

    @Test
    public void startTextStartsAsEmpty() {
        assertEquals("", sk.getStartText());
    }

    @Test
    public void startTextEmptyWhenPlayerPassesScoringPosition() {
        sk.addPosition(5);
        sk.updateScore(true);
        assertEquals("", sk.getStartText());
    }

    @Test
    public void startTextIsHelpTextIfPlayerNotPastScoringPosition() {
        sk.updateScore(true);
        assertEquals("Dodge the spikes!\nPress Space to jump", sk.getStartText());
    }
}
