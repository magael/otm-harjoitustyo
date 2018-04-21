package mj.platformer.collision;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CollisonHandlerTest {

    CollisionHandler ch;
    Player player;
    Obstacle obstacle;
    int groundLevel;
    int tileSize;
    
    @Before
    public void setUp() {
        tileSize = 16;
        player = new Player(new Rectangle(tileSize, tileSize), 0, 10);
        obstacle = new Obstacle(new Rectangle(tileSize, tileSize), 20, 10);
        groundLevel = tileSize;
        ch = new CollisionHandler();
    }
    
    @Test
    public void noCollisionWhenSpritesDontIntersect() {
        assertFalse(ch.handleCollisions(player, obstacle));
    }
    
    @Test
    public void collisionDetectedWhenSpritesIntersect() {
        obstacle.setX(5);
        assertTrue(ch.handleCollisions(player, obstacle));
    }
    
    @Test
    public void groundDetectionWorks() {
        assertTrue(ch.isGrounded(player, tileSize, groundLevel));
    }
    
    @Test
    public void noGroundDetectedWhenPlayerIsAirborne() {
        player.setY(-1);
        assertFalse(ch.isGrounded(player, tileSize, groundLevel));
    }
}
