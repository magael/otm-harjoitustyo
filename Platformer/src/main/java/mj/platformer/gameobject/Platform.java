package mj.platformer.gameobject;

import javafx.scene.shape.Shape;

public class Platform extends GameObject {

    GameObjectMover mover;

    public Platform(Shape sprite, int x, int y) {
        super(sprite, x, y);
        mover = new GameObjectMover(this);
    }

    public GameObjectMover getMover() {
        return mover;
    }

    @Override
    public void update() {
        mover.move();
    }
}
