package mj.platformer.collision;

import javafx.scene.shape.Shape;
import mj.platformer.gameobject.GameObject;
import mj.platformer.gameobject.Player;

public class CollisionHandler {

    public boolean handleCollisions(Player player, GameObject go) {
        // At the moment ends the game on any collision.
        // This version assumes player sprites are JavaFX Shapes.
//        if (obstacle.isClose(player, 32)) {
        Shape collisionArea = Shape.intersect(player.getSprite(), go.getSprite());
        if (collisionArea.getBoundsInLocal().getWidth() != -1) {
            return go.onCollision();
        }
//        }
        return false;
    }

    public boolean isGrounded(Player player, int playerHeight, int groundLevel) {
        double playerY = player.getY();
        if ((playerY + playerHeight) >= groundLevel) {
            //clamp position
            player.setY(groundLevel - playerHeight);

            player.setFalling(false);
            player.setVelocity(0);

            return true;
        }
        return false;
    }

}
