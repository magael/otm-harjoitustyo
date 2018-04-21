package mj.platformer.input;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import mj.platformer.gameobject.Player;
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;

public class InputHandlerTest {

    InputHandler ih;
    Player p;

    @Before
    public void setUp() {
        ih = new InputHandler();
        p = new Player(new Rectangle(64, 64), 0, 0);
    }

    @Test
    public void buttonsInitialize() {
        assertTrue(ih.getButtonsDown().isEmpty());
    }
    
    @Test
    public void registersPlayerJumpInput() {
        ih.getButtonsDown().put(KeyCode.SPACE, Boolean.TRUE);
        assertTrue(ih.handlePlayerInput(p));
    }
    
    @Test
    public void handlePlayerInputDoesNothingIfThereIsNoJumpInput() {
        assertFalse(ih.handlePlayerInput(p));
    }
    
    @Test
    public void registersRestartInput() {
        ih.getButtonsDown().put(KeyCode.R, Boolean.TRUE);
        assertTrue(ih.handleRestartInput());
    }
    
    @Test
    public void restartInputDoesNothingIfThereIsNoRestartInput() {
        assertFalse(ih.handleRestartInput());
    }
}
