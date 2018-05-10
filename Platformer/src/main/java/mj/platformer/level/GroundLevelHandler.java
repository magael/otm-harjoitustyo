package mj.platformer.level;

import java.util.ArrayList;
import java.util.HashMap;
import mj.platformer.gameobject.Player;

public class GroundLevelHandler {

    private HashMap<Double, Integer> groundLevels;
    private ArrayList<Double> gameObjectPositions;
    private int groundLevelIndex;

    public GroundLevelHandler(LevelCreator lvlCreator) {
        gameObjectPositions = lvlCreator.getGameObjectPositions();
        groundLevels = lvlCreator.getGroundLevels();
        groundLevelIndex = 0;
    }

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
