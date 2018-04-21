package mj.platformer.input;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import mj.platformer.gameobject.Player;

public class InputHandler {
    
    private Map<KeyCode, Boolean> buttonsDown;

    public InputHandler() {
        buttonsDown = new HashMap<>();
    }

    public void initInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            buttonsDown.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            buttonsDown.put(event.getCode(), Boolean.FALSE);
        });
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
