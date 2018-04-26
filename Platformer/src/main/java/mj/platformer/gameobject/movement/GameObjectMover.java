package mj.platformer.gameobject.movement;

import mj.platformer.gameobject.GameObject;

public class GameObjectMover {

    private GameObject go;
    private int speed;

    public GameObjectMover(GameObject go) {
        this.go = go;
        this.speed = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move() {
        go.setX(go.getX() - speed);
    }
}
