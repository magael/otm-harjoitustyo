package mj.platformer.gameobject;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class Obstacle extends GameObject {

    private int speed;
    private int width;
    private int height;

//    public Platform(Node sprite, int x, int y, int width, int height) {
    public Obstacle(Shape sprite, int x, int y, int width, int height) {
        super(sprite, x, y);
        this.width = width;
        this.height = height;
        this.speed = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void move() {
        Node sprite = this.getSprite();
        sprite.setTranslateX(sprite.getTranslateX() - speed);
    }
    
//    public boolean onScreen() {
//        // int thisX = this.getSprite.getTranslateX
//        // if (thisX > 0 && thisX < World.getScreenWidth) return true;
//        return false;
//    }
    
//    public boolean isClose(Player player) {
//        double thisX = this.getSprite().getTranslateX();
//        double playerX = player.getSprite().getTranslateX();
//        if ((thisX + (double) width) >= playerX
//                && thisX < (playerX + player.getSprite().getLayoutBounds().getMaxX())) {
//            return true;
//        }
//        return false;
//    }
}
