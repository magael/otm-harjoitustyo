package mj.platformer.gameobject;

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

    @Before
    public void setUp() {
        sprite = new Rectangle(10, 10);
        x = 10;
        y = 10;
        speed = 10;
        o = new Obstacle(sprite, x, y);
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
        assertEquals(0, o.getSpeed());
    }

    @Test
    public void setSpeedWorks() {
        o.setSpeed(speed);
        assertEquals(speed, o.getSpeed());
    }
    
    @Test
    public void moveDoesNothingIfSpeedIsZero() {
        o.move();
        assertEquals(x, o.getX(), 0);
    }

    @Test
    public void moveWorks() {
        o.setSpeed(speed);
        o.move();
        assertEquals(x - speed, o.getX(), 0);
    }
}
