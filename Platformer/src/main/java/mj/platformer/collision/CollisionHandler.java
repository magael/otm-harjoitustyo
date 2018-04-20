package mj.platformer.collision;

import javafx.scene.shape.Shape;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Player;

public class CollisionHandler {

    public boolean handleCollisions(Player player, Obstacle obstacle) {
        // At the moment ends the game on any collision.
        // This version assumes player sprites are JavaFX Shapes.
        Shape collisionArea = Shape.intersect(player.getSprite(), obstacle.getSprite());
        if (collisionArea.getBoundsInLocal().getWidth() != -1) {
            return true;
        }
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
