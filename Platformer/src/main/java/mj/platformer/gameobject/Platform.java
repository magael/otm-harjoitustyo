package mj.platformer.gameobject;

import mj.platformer.gameobject.movement.GameObjectMover;
import javafx.scene.shape.Shape;

/**
 * The platform object class, extending the abstract GameObject class.
 * The platforms move towards the player and the player can jump on them.
 * 
 * @author Maguel
 */
public class Platform extends GameObject {

    private GameObjectMover mover;

    /**
     * Constructor for the Platform class.
     * Initializes the GameObjectMover used by this instance.
     * 
     * @param sprite
     * @param x
     * @param y
     */
    public Platform(Shape sprite, double x, int y) {
        super(sprite, x, y);
        mover = new GameObjectMover(this);
    }

    public GameObjectMover getMover() {
        return mover;
    }
    
    public void setMover(GameObjectMover mover) {
        this.mover = mover;
    }

    @Override
    public void update() {
        mover.move();
    }
}
