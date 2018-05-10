package mj.platformer.level;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelCreatorTest {

    private int groundLevel;
    private double objectX;
    private LevelCreator lc;

    @Before
    public void setUp() {
        int canvasWidth = 800;
        int tileSize = 32;
        int goSpeed = 15;
        groundLevel = 50;
        objectX = 10;
        lc = new LevelCreator(canvasWidth, tileSize, groundLevel, goSpeed, Color.BURLYWOOD, Color.GREENYELLOW);
    }

    @Test
    public void addingToGroundLevelsWorks() {
        lc.addGroundLevelPosition(objectX, groundLevel);
        assertEquals(groundLevel, lc.getGroundLevels().get(objectX), 0);
    }

    @Test
    public void addingToGameObjectPositionsWorks() {
        lc.addGroundLevelPosition(objectX, groundLevel);
        assertTrue(lc.getGameObjectPositions().contains(objectX));
    }

    @Test(expected = Exception.class)
    public void throwsExceptionIfBadFilePath() throws Exception {
        String filePath = "wrong/no_file.bad";
        lc.createObjectsFromFile(filePath);
    }
    
    @Test
    public void levelGenerationFromFileWorks() throws Exception {
        // When interpreted as level data, the test file reads 01212
        String filePath = "test/test_file.txt";
        lc.createObjectsFromFile(filePath);
        //the 4 objects in the list are {Obstacle, Platform, Obstacle, Platform}
        assertEquals(4, lc.getObjects().size());
    }
}
