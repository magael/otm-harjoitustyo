
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    
    Node sprite;
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
}
