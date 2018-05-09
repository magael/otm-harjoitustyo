package mj.platformer.input;

import java.util.Map;
import javafx.scene.input.KeyCode;
import mj.platformer.gameobject.Player;

/**
 * Handles user keyboard input.
 * 
 * @author Maguel
 */
public class InputHandler {

    private Map<KeyCode, Boolean> buttonsDown;

    /**
     *
     * @param buttonsDown
     */
    public InputHandler(Map<KeyCode, Boolean> buttonsDown) {
        this.buttonsDown = buttonsDown;
    }

    /**
     *
     * @return map of keys pressed & released
     */
    public Map<KeyCode, Boolean> getButtonsDown() {
        return buttonsDown;
    }

    /**
     *
     * @return which level the user chooses, or 0 if no input detected
     */
    public int levelInput() {
        if (genericInput(KeyCode.DIGIT1)) {
            return 1;
        }
        if (genericInput(KeyCode.DIGIT2)) {
            return 2;
        }
        return 0;
    }

    /**
     *
     * @param player
     * @return true if input = space and jumping succeeds, else false
     */
    public boolean playerInput(Player player) {
        if (genericInput(KeyCode.SPACE)) {
            return player.jump();
        }
        return false;
    }

    /**
     *
     * @return true if key pressed, else false
     */
    public boolean restartInput() {
        return genericInput(KeyCode.R);
    }

    /**
     *
     * @return true if key pressed, else false
     */
    public boolean backToMenuInput() {
        return genericInput(KeyCode.B);
    }

    /**
     *
     * @return true if key pressed, else false
     */
    public boolean pauseInput() {
        return genericInput(KeyCode.P);
    }
    
    /**
     *
     * @return true if key pressed, else false
     */
    public boolean optionsInput() {
        return genericInput(KeyCode.O);
    }

    private boolean genericInput(KeyCode kc) {
        return (buttonsDown.getOrDefault(kc, false));
    }
}
