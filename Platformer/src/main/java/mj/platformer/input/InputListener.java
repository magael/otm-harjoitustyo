
package mj.platformer.input;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputListener {

    private Map<KeyCode, Boolean> buttonsDown;
    
    public InputListener() {
        buttonsDown = new HashMap<>();
    }
    
    public Map<KeyCode, Boolean> initInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            buttonsDown.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            buttonsDown.put(event.getCode(), Boolean.FALSE);
        });
        return buttonsDown;
    }

}
