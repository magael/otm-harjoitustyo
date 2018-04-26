package mj.platformer.gameobject;

import mj.platformer.gameobject.movement.GameObjectMover;
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
    
    public void setMover(GameObjectMover mover) {
        this.mover = mover;
    }

    @Override
    public void update() {
        mover.move();
    }
}
