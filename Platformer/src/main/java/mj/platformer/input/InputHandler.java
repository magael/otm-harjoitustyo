package mj.platformer.input;

import java.util.Map;
import javafx.scene.input.KeyCode;
import mj.platformer.gameobject.Player;

public class InputHandler {
    
    private Map<KeyCode, Boolean> buttonsDown;

    public InputHandler(Map<KeyCode, Boolean> buttonsDown) {
        this.buttonsDown = buttonsDown;
    }

    public Map<KeyCode, Boolean> getButtonsDown() {
        return buttonsDown;
    }

    public boolean playerInput(Player player) {
        if (buttonsDown.getOrDefault(KeyCode.SPACE, false)) {
            player.jump();
            return true;
        }
        return false;
    }

    public boolean restartInput() {
        if (buttonsDown.getOrDefault(KeyCode.R, false)) {
            return true;
        }
        return false;
    }
    
    public int levelInput() {
        if (buttonsDown.getOrDefault(KeyCode.DIGIT1, false)) {
            return 1;
        }
        if (buttonsDown.getOrDefault(KeyCode.DIGIT2, false)) {
            return 2;
        }
        return 0;
    }

    public boolean backToMenuInput() {
        if (buttonsDown.getOrDefault(KeyCode.B, false)) {
            return true;
        }
        return false;
    }
}
