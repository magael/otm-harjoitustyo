package mj.platformer.gameobject;

import mj.platformer.gameobject.movement.GameObjectMover;
import javafx.scene.shape.Shape;

/**
 * The obstacle class, which the player is supposed to avoid touching.
 * A subclass of the abstract class GameObject (in the same package).
 * 
 * @author Maguel
 */
public class Obstacle extends GameObject {

    private GameObjectMover mover;

    /**
     * Constructor for the Obstacle class.
     * Initializes the GameObjectMover used by this instance.
     * 
     * @param sprite
     * @param x
     * @param y 
     */
    public Obstacle(Shape sprite, double x, int y) {
        super(sprite, x, y);
        mover = new GameObjectMover(this);
    }

    public GameObjectMover getMover() {
        return mover;
    }

    public void setMover(GameObjectMover mover) {
        this.mover = mover;
    }

    /**
     * The obstacle is moved according to how the instance of the
     * GameObjectMover is configured.
     */
    @Override
    public void update() {
        mover.move();
    }

    /**
     * When the player collides with an obstacle, it's gameOver = true.
     * 
     * @return true
     */
    @Override
    public boolean onCollision() {
        return true;
    }
}
