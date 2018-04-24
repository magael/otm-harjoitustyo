package mj.platformer.level;

import java.util.ArrayList;
import java.util.HashMap;
import mj.platformer.gameobject.Player;

public class GroundLevelHandler {

    private HashMap<Integer, Integer> groundLevels;
    private ArrayList<Integer> gameObjectPositions;
    int groundLevelIndex;

    public GroundLevelHandler(LevelCreator lvlCreator) {
        gameObjectPositions = lvlCreator.getGameObjectPositions();
        groundLevels = lvlCreator.getGroundLevels();
        groundLevelIndex = 0;
    }

    public int setGroundLevel(int groundLevel, int goSpeed, Player player) {
        player.setProgress(player.getProgress() + goSpeed);
        if (groundLevelIndex < gameObjectPositions.size()
                && player.getProgress() >= gameObjectPositions.get(groundLevelIndex)
                && groundLevels.containsKey(gameObjectPositions.get(groundLevelIndex))) {
            groundLevel = groundLevels.get(gameObjectPositions.get(groundLevelIndex));
            groundLevelIndex++;
            if (player.getGrounded()) {
                player.setFalling(true);
            }
        }
        return groundLevel;
    }

}
