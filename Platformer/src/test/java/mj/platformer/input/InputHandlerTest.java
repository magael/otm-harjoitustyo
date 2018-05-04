package mj.platformer.input;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import mj.platformer.gameobject.Player;
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;

//InputListener has been excluded as part of the UI generation.

public class InputHandlerTest {
    private InputHandler ih;
    private Player p;

    @Before
    public void setUp() {
        Map<KeyCode, Boolean> buttonsDown = new HashMap<>();
        ih = new InputHandler(buttonsDown);
        p = new Player(new Rectangle(64, 64), 0, 0, 64);
    }

    @Test
    public void buttonsInitialize() {
        assertTrue(ih.getButtonsDown().isEmpty());
    }
    
    @Test
    public void registersPlayerJumpInput() {
        ih.getButtonsDown().put(KeyCode.SPACE, Boolean.TRUE);
        assertTrue(ih.playerInput(p));
    }
    
    @Test
    public void handlePlayerInputDoesNothingIfThereIsNoJumpInput() {
        assertFalse(ih.playerInput(p));
    }
    
    @Test
    public void registersRestartInput() {
        ih.getButtonsDown().put(KeyCode.R, Boolean.TRUE);
        assertTrue(ih.restartInput());
    }
    
    @Test
    public void restartInputDoesNothingIfThereIsNoRestartInput() {
        assertFalse(ih.restartInput());
    }
}
