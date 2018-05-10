package mj.platformer.gameobject;

import javafx.scene.shape.Shape;

/**
 * This abstract class server as a base for all entities of the game, such as
 * the player, platforms and obstacles.
 *
 * @author Maguel
 */
public abstract class GameObject {

    private Shape sprite;

    /**
     * The constructor for the class GameObject. Initializes the position. The
     * class stores a JavaFX Shape Node which is used as the obejct's sprite.
     *
     * @param sprite
     * @param x
     * @param y
     */
    public GameObject(Shape sprite, double x, int y) {
        this.sprite = sprite;
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

    public Shape getSprite() {
        return sprite;
    }

    public double getX() {
        return sprite.getTranslateX();
    }

    public double getY() {
        return sprite.getTranslateY();
    }

    /**
     * The GameObject's position on screen is set via it's sprite. The method
     * setX(double) set's the object's x coordinate.
     *
     * @param value
     */
    public void setX(double value) {
        sprite.setTranslateX(value);
    }

    /**
     * The GameObject's position on screen is set via it's sprite. The method
     * setY(double) set's the object's y coordinate.
     *
     * @param value
     */
    public void setY(double value) {
        sprite.setTranslateY(value);
    }

    /**
     * The default is that colliding with a particular Game Object "does
     * nothing". The method is overwritten in any subclass that wishes to
     * trigger an event on collision, such as setting the gameOver = true.
     *
     * @return boolean value to be used by the World class' gameOver variable.
     */
    public boolean onCollision() {
        // Returns gameOver = false
        return false;
    }

    /**
     * The update method's functionality is unique to each subclass of GameObject.
     */
    public abstract void update();
}
