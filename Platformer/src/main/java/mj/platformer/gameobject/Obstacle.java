package mj.platformer.gameobject;

import javafx.scene.shape.Shape;

public class Obstacle extends GameObject {

    private int speed;

    public Obstacle(Shape sprite, int x, int y) {
        super(sprite, x, y);
        this.speed = 0;
    }

    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move() {
        Shape sprite = this.getSprite();
        sprite.setTranslateX(sprite.getTranslateX() - speed);
    }
}
