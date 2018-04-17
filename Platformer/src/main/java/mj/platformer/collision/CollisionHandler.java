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

//        // the first version: tested only on Rectangles
//        double playerX = player.getSprite().getTranslateX();
//        double playerY = player.getSprite().getTranslateY();
//        double platformX = platform.getSprite().getTranslateX();
//        double platformY = platform.getSprite().getTranslateY();
//        double platformWidth = platform.getWidth();
//        double platformHeight = platform.getHeight();
//        
//        if ((playerY + playerHeight) >= platformY && playerY <= (platformY + platformHeight)) {
//            player.setGrounded(true);
//            if ((playerX + playerWidth) >= platformX && playerX <= (platformX + platformWidth)) {
////                System.out.println("collision");
////                maybe add blinking player sprite effect
//                gameOver = true;
//            }
//        }
    }

    public boolean isGrounded(Player player, int playerHeight, int groundLevel) {
        double playerY = player.getSprite().getTranslateY();
        if ((playerY + playerHeight) >= groundLevel) {
            //clamp position
            player.getSprite().setTranslateY(groundLevel - playerHeight);

            player.setFalling(false);
            player.setVelocity(0);

            return true;
        }
        return false;
    }

}
