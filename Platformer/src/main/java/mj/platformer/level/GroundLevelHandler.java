package mj.platformer.level;

import java.util.ArrayList;
import java.util.HashMap;
import mj.platformer.gameobject.Player;

/**
 * A class to dynamically adjust the game's current ground level.
 * 
 * @author Maguel
 */
public class GroundLevelHandler {

    private HashMap<Double, Integer> groundLevels;
    private ArrayList<Double> gameObjectPositions;
    private int groundLevelIndex;

    /**
     * The constructor for the ground level handler.
     * The game object positions and ground levels are derived from
     * the LevelCreator class.
     * 
     * @param lvlCreator
     */
    public GroundLevelHandler(LevelCreator lvlCreator) {
        gameObjectPositions = lvlCreator.getGameObjectPositions();
        groundLevels = lvlCreator.getGroundLevels();
        groundLevelIndex = 0;
    }

    /**
     * * 
     * As the player advances on the level, the ground level is set to either
     * the default ground sprite's or a platform's upper corner.
     * 
     * @param groundLevel
     * @param goSpeed
     * @param player
     * @return the current ground level (y axis integer value)
     */
    public int setGroundLevel(int groundLevel, double goSpeed, Player player) {
        player.setProgress(player.getProgress() + goSpeed);
        
        if (groundLevelIndex < gameObjectPositions.size()
                && player.getProgress() >= gameObjectPositions.get(groundLevelIndex)
                && groundLevels.containsKey(gameObjectPositions.get(groundLevelIndex))) {
            groundLevel = updateGroundLevel();
            applyGravity(player);
        }
        return groundLevel;
    }

    private int updateGroundLevel() {
        int groundLevel;
        groundLevel = groundLevels.get(gameObjectPositions.get(groundLevelIndex));
        groundLevelIndex++;
        return groundLevel;
    }

    private void applyGravity(Player player) {
        if (player.getGrounded()) {
            player.setFalling(true);
        }
    }

}
