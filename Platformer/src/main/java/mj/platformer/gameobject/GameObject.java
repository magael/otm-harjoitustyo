package mj.platformer.gameobject;

import javafx.scene.shape.Shape;

public abstract class GameObject {

    private Shape sprite;

    public GameObject(Shape sprite, int x, int y) {
        this.sprite = sprite;
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

    public Shape getSprite() {
        return sprite;
    }

    public void setSprite(Shape sprite) {
        this.sprite = sprite;
    }

    public double getX() {
        return sprite.getTranslateX();
    }

    public double getY() {
        return sprite.getTranslateY();
    }

    public void setX(double value) {
        sprite.setTranslateX(value);
    }

    public void setY(double value) {
        sprite.setTranslateY(value);
    }

    public boolean onCollision() {
        // Returns gameOver = false
        return false;
    }

    public void update() {
    }
}
