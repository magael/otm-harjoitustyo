package mj.platformer.gameobject;

import javafx.scene.shape.Shape;

/**
 * The player's "avatar" class, that is a subclass of the abstract class GameObject.
 * 
 * @see mj.platformer.gameobject.GameObject
 * 
 * @author Maguel
 */
public class Player extends GameObject {

    private boolean grounded;
    private boolean falling;
    private double speed;
    private double acceleration;
    private double velocity;
    private double maxVelocity;
    private double progress;

    /**
     * Constructor for the Player class.
     * Besides the usual GameObejct parameters, Player requires an integer
     * to store the sprite's width.
     * 
     * @param sprite
     * @param x
     * @param y
     * @param width 
     */
    public Player(Shape sprite, int x, int y, int width) {
        super(sprite, x, y);
        grounded = true;
        falling = false;
        speed = 1;
        acceleration = 1.5;
        velocity = 0;
        maxVelocity = 15;
        progress = x + width;
    }

    /**
     * The player is set airborne.
     * 
     * @return true if the player was able to jump, otherwise false
     */
    public boolean jump() {
        if (grounded) { 
            grounded = false;
            return true;
        }
        return false;
    }

    /**
     * Controls the jumping-related momentum and position of the player character.
     */
    @Override
    public void update() {
        if (velocity >= maxVelocity) {
            falling = true;
        }

        updateVelocity();

        this.setY(this.getY() - velocity);
    }

    private void updateVelocity() {
        if (!falling) {
            velocity += speed * acceleration;
        } else {
            velocity -= speed * acceleration;
        }
    }

    public boolean getFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean getGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
