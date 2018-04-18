
import mj.platformer.gameobject.Player;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

//    Node sprite;
    Shape sprite;
    Player player;

    @Before
    public void setUp() {
        sprite = new Rectangle(100, 100);
        player = new Player(sprite, 0, 0);
    }

    @Test
    public void getSpriteWorks() {
        assertEquals(player.getSprite(), sprite);
    }

    @Test
    public void playerStartsGrounded() {
        assertEquals(player.getGrounded(), true);
    }
    
    @Test
    public void jumpDoesNothingIfNotGrounded() {
        player.setGrounded(false);
        assertEquals(player.jump(), false);
    }

    @Test
    public void jumpingChangesGroundedToFalse() {
        player.jump();
        assertEquals(player.getGrounded(), false);
    }

    @Test
    public void playerStartsNotFalling() {
        assertEquals(player.getFalling(), false);
    }

    @Test
    public void playerMovesVerticallyByJumping() {
        double playerStartY = player.getSprite().getTranslateY();
        player.jump();
        player.updatePosition();
        assertNotEquals(playerStartY, player.getSprite().getTranslateY());
    }

    @Test
    public void playerStartsFallingAfterPeakVelocity() {
        player.jump();
        player.setVelocity(player.getMaxVelocity());
        player.updatePosition();
        assertEquals(player.getFalling(), true);
    }
    
    @Test
    public void playerStartsWithoutVelocity() {
        assertEquals(player.getVelocity(), 0, 0);
    }
    
    @Test
    public void jumpingChangesVelocity() {
        player.jump();
        player.updatePosition();
        assertNotEquals(player.getVelocity(), 0, 0);
    }

    //kind of useless tests just to make the report look nicer
    @Test
    public void setGroundedWorks() {
        player.setGrounded(false);
        assertEquals(player.getGrounded(), false);
    }

    @Test
    public void setFallingWorks() {
        player.setFalling(true);
        assertEquals(player.getFalling(), true);
    }
}
