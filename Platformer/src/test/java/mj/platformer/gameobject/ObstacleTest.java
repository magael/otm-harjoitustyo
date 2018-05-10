package mj.platformer.gameobject;

import mj.platformer.gameobject.movement.GameObjectMover;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ObstacleTest {

    private Obstacle o;
    private Shape sprite;
    private int x;
    private int y;
    private int speed;
    private GameObjectMover mover;

    @Before
    public void setUp() {
        sprite = new Rectangle(10, 10);
        x = 10;
        y = 10;
        speed = 10;
        o = new Obstacle(sprite, x, y);
        o.setMover(new GameObjectMover(o));
        mover = o.getMover();
    }
    
    //GameObject tests
    @Test
    public void getSpriteWorks() {
        assertEquals(sprite, o.getSprite());
    }
    
    //Obstacle tests
    @Test
    public void startsWithZeroSpeed() {
        assertEquals(0, mover.getSpeed(), 0.0);
    }

    @Test
    public void setSpeedWorks() {
        mover.setSpeed(speed);
        assertEquals(speed, mover.getSpeed(), 0.0);
    }
    
    @Test
    public void moveDoesNothingIfSpeedIsZero() {
        o.update();
        assertEquals(x, o.getX(), 0);
    }

    @Test
    public void moveWorks() {
        mover.setSpeed(speed);
        o.update();
        assertEquals(x - speed, o.getX(), 0);
    }
    
    @Test
    public void onCollisionReturnsGameOver() {
        assertTrue(o.onCollision());
    }
}
