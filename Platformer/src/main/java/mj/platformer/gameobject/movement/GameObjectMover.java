package mj.platformer.gameobject.movement;

import mj.platformer.gameobject.GameObject;

public class GameObjectMover {

    private GameObject go;
    private double speed;

    public GameObjectMover(GameObject go) {
        this.go = go;
        this.speed = 0;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void move() {
        go.setX(go.getX() - speed);
    }
}
