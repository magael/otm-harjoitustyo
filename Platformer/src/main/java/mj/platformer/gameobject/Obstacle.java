package mj.platformer.gameobject;

import javafx.scene.shape.Shape;

public class Obstacle extends GameObject {

    GameObjectMover mover;

    public Obstacle(Shape sprite, int x, int y) {
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

    @Override
    public boolean onCollision() {
        // Returns gameOver = true
        return true;
    }
}
