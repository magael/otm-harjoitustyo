package mj.platformer.collision;

import javafx.scene.shape.Shape;
import mj.platformer.gameobject.GameObject;
import mj.platformer.gameobject.Player;

/**
 *
 * @author Maguel
 */
public class CollisionHandler {

    /**
     * Determines if there is a collision, and what to do in that case. This
     * version assumes the sprites are JavaFX Shapes.
     *
     * @param player
     * @param go
     * @return true if the game should end due to a particular collision,
     * otherwise false
     */
    public boolean handleCollisions(Player player, GameObject go) {
        Shape collisionArea = Shape.intersect(player.getSprite(), go.getSprite());
        if (collisionArea.getBoundsInLocal().getWidth() != -1) {
            return go.onCollision();
        }

        return false;
    }

    /**
     * If the player sprite's bottom side is at ground level or below, the y
     * position is set at ground level and variables are changed to indicate the
     * lack of motion.
     *
     * @param player
     * @param playerHeight
     * @param groundLevel
     * @return true if the player is at ground level, otherwise false
     */
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
