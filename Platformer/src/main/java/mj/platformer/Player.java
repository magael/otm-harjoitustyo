package mj.platformer;


import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class Player extends GameObject {

    // speed, velocity...
    private boolean grounded;
    private boolean falling;
    private double speed;
    private double acceleration;
    private double velocity;
    private double maxVelocity;

//    public Player(Node sprite, int x, int y) {
    public Player(Shape sprite, int x, int y) {
        super(sprite, x, y);
        grounded = false;
        falling = false; // think about how to init and test this. 
        speed = 1;
        acceleration = 1.5;
        velocity = 0;
        maxVelocity = 15;
    }

    public void jump() {
        if (grounded) { // unit test this
            grounded = false;
        }
    }

    public void updatePosition() {
        if (velocity >= maxVelocity) {
            falling = true;
        }

        if (!falling) {
            velocity += speed * acceleration;
        } else {
            velocity -= speed * acceleration;
        }
        
        this.getSprite().setTranslateY(this.getSprite().getTranslateY() - velocity);
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
}
