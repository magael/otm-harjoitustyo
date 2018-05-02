package mj.platformer.level;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelCreatorTest {

    private int groundLevel;
    private int obstacleX;
    private LevelCreator lc;    
    
    @Before
    public void setUp() {
        int canvasWidth = 800;
        int tileSize = 32;
        int goSpeed = 15;
        groundLevel = 50;
        obstacleX = 10;
        lc = new LevelCreator(canvasWidth, tileSize, groundLevel, goSpeed, Color.BURLYWOOD, Color.GREENYELLOW);
    }

    @Test
    public void addingToGroundLevelsWorks() {
        lc.addGroundLevelPosition(obstacleX, groundLevel);
        assertEquals(groundLevel, lc.getGroundLevels().get(obstacleX), 0);
    }
    
    @Test
    public void addingToGameObjectPositionsWorks() {
        lc.addGroundLevelPosition(obstacleX, groundLevel);
        assertTrue(lc.getGameObjectPositions().contains(obstacleX));
    }
}
