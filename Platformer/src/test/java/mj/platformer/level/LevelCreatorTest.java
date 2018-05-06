package mj.platformer.level;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import mj.platformer.gameobject.GameObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelCreatorTest {

    private int groundLevel;
    private double obstacleX;
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
    
    @Test
    public void gameRunsWithNoLevelObjectsIfBadFilePath() {
        String filePath = "wrong/no_file.bad";
        lc.createObjectsFromFile(filePath);
        assertEquals(new ArrayList<GameObject>(), lc.getObjects());
    }
}
