package mj.platformer.gameobject;

import mj.platformer.gameobject.movement.GameObjectMover;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlatformTest {

    Platform p;
    Shape sprite;
    int x;
    int y;
    int speed;
    GameObjectMover mover;

    @Before
    public void setUp() {
        sprite = new Rectangle(10, 20);
        x = 20;
        y = 20;
        speed = 20;
        p = new Platform(sprite, x, y, 10);
        p.setMover(new GameObjectMover(p));
        mover = p.getMover();
    }
    
    //GameObject tests
    @Test
    public void getSpriteWorks() {
        assertEquals(sprite, p.getSprite());
    }
    
    @Test
    public void setSpriteWorks() {
        Rectangle r = new Rectangle(x, x);
        p.setSprite(r);
        assertEquals(r, p.getSprite());
    }
    
    @Test
    public void getXWorks() {
        assertEquals(p.getSprite().getTranslateX(), p.getX(), 0);
    }
    
    @Test
    public void getYWorks() {
        assertEquals(p.getSprite().getTranslateY(), p.getY(), 0);
    }
    
    @Test
    public void setXWorks() {
        p.setX(100);
        assertEquals(100, p.getX(), 0);
    }
    
    @Test
    public void setYWorks() {
        p.setY(100);
        assertEquals(100, p.getY(), 0);
    }
    
    //Platform tests
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
        p.update();
        assertEquals(x, p.getX(), 0);
    }

    @Test
    public void moveWorks() {
        mover.setSpeed(speed);
        p.update();
        assertEquals(x - speed, p.getX(), 0);
    }
    
    @Test
    public void onCollisionReturnsGameOver() {
        assertFalse(p.onCollision());
    }
}
