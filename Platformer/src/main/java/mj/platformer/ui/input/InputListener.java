
package mj.platformer.ui.input;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * InputListener initializes the events for keyboard input.
 * 
 * @author Maguel
 */
public class InputListener {

    private Map<KeyCode, Boolean> buttonsDown;
    
    /**
     * Constructor for the InputListener class.
     */
    public InputListener() {
        buttonsDown = new HashMap<>();
    }
    
    /**
     * Log's the user's keyboard presses and releases into a map.
     * 
     * @param scene where the event is being listened to
     * @return map (of keycode, boolean) indicating key press and release
     */
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
