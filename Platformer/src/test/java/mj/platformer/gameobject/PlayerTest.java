package mj.platformer.gameobject;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    Shape sprite;
    Player player;
    int x;
    int y;

    @Before
    public void setUp() {
        sprite = new Rectangle(100, 100);
        player = new Player(sprite, 0, 0, 100);
        x = 10;
        y = 10;
    }

    //GameObject tests
    @Test
    public void getSpriteWorks() {
        assertEquals(sprite, player.getSprite());
    }
    
    @Test
    public void setSpriteWorks() {
        Circle c = new Circle(x);
        player.setSprite(c);
        assertEquals(c, player.getSprite());
    }
    
    @Test
    public void getXWorks() {
        assertEquals(player.getSprite().getTranslateX(), player.getX(), 0);
    }
    
    @Test
    public void getYWorks() {
        assertEquals(player.getSprite().getTranslateY(), player.getY(), 0);
    }
    
    @Test
    public void setXWorks() {
        player.setX(100);
        assertEquals(100, player.getX(), 0);
    }
    
    @Test
    public void setYWorks() {
        player.setY(100);
        assertEquals(100, player.getY(), 0);
    }
    
    @Test
    public void getWidthWorks() {
        assertEquals(player.getWidth(), 100);
    }

    // Player tests
    @Test
    public void playerStartsGrounded() {
        assertTrue(player.getGrounded());
    }
    
    @Test
    public void jumpDoesNothingIfNotGrounded() {
        player.setGrounded(false);
        assertFalse(player.jump());
    }

    @Test
    public void jumpingChangesGroundedToFalse() {
        player.jump();
        assertFalse(player.getGrounded());
    }

    @Test
    public void playerStartsNotFalling() {
        assertFalse(player.getFalling());
    }

    @Test
    public void playerMovesVerticallyByJumping() {
        double playerStartY = player.getY();
        player.jump();
        player.update();
        assertNotEquals(playerStartY, player.getY());
    }

    @Test
    public void playerStartsFallingAfterPeakVelocity() {
        player.jump();
        player.setVelocity(player.getMaxVelocity());
        player.update();
        assertTrue(player.getFalling());
    }
    
    @Test
    public void playerStartsWithoutVelocity() {
        assertEquals(0, player.getVelocity(), 0);
    }
    
    @Test
    public void jumpingChangesVelocity() {
        player.jump();
        player.update();
        assertNotEquals(0, player.getVelocity(), 0);
    }

    @Test
    public void setGroundedWorks() {
        player.setGrounded(false);
        assertFalse(player.getGrounded());
    }

    @Test
    public void setFallingWorks() {
        player.setFalling(true);
        assertTrue(player.getFalling());
    }
    
    @Test
    public void setProgressWorks() {
        player.setProgress(10);
        assertEquals(10, player.getProgress(), 0);
    }
}
