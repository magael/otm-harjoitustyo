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

    @Override
    public void update() {
        mover.move();
    }

    @Override
    public boolean onCollision() {
        // Returns gameOver = true
        return true;
    }
//    
//    public boolean isClose(Player player, int tileSize) {
//        double thisX = this.getX();
//        double playerX = player.getX();
//        if ((thisX + (double) tileSize) >= playerX
//                && thisX < (playerX + (double) tileSize)) {
//            return true;
//        }
//        return false;
//    }
}
