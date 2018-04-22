package mj.platformer.input;

import java.util.Map;
import javafx.scene.input.KeyCode;
import mj.platformer.gameobject.Player;

public class InputHandler {
    
    private Map<KeyCode, Boolean> buttonsDown;

    public InputHandler(Map<KeyCode, Boolean> buttonsDown) {
        this.buttonsDown = buttonsDown;
    }

    public boolean handlePlayerInput(Player player) {
        if (buttonsDown.getOrDefault(KeyCode.SPACE, false)) {
            player.jump();
            return true;
        }
        return false;
    }

    public Map<KeyCode, Boolean> getButtonsDown() {
        return buttonsDown;
    }

    public boolean handleRestartInput() {
        if (buttonsDown.getOrDefault(KeyCode.R, false)) {
            return true;
        }
        return false;
    }

}
