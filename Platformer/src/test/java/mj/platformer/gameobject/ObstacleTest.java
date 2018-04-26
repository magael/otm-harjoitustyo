package mj.platformer.gameobject;

import mj.platformer.gameobject.movement.GameObjectMover;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ObstacleTest {

    Obstacle o;
    Shape sprite;
    int x;
    int y;
    int speed;
    GameObjectMover mover;

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
    
    @Test
    public void setSpriteWorks() {
        Circle c = new Circle(x);
        o.setSprite(c);
        assertEquals(c, o.getSprite());
    }
    
    @Test
    public void getXWorks() {
        assertEquals(o.getSprite().getTranslateX(), o.getX(), 0);
    }
    
    @Test
    public void getYWorks() {
        assertEquals(o.getSprite().getTranslateY(), o.getY(), 0);
    }
    
    @Test
    public void setXWorks() {
        o.setX(100);
        assertEquals(100, o.getX(), 0);
    }
    
    @Test
    public void setYWorks() {
        o.setY(100);
        assertEquals(100, o.getY(), 0);
    }
    
    //Obstacle tests
    @Test
    public void startsWithZeroSpeed() {
        assertEquals(0, mover.getSpeed());
    }

    @Test
    public void setSpeedWorks() {
        mover.setSpeed(speed);
        assertEquals(speed, mover.getSpeed());
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
