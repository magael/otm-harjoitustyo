package mj.platformer.level;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import mj.platformer.gameobject.GameObject;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Platform;
import mj.platformer.utils.LevelDataReader;

public class LevelCreator { // vai GameObjectCreator?

    int canvasWidth;
    int tileSize;
    int groundLevel;
    int goSpeed;
    HashMap<Integer, Integer> groundLevels;
    ArrayList<Integer> gameObjectPositions;
    //maybe colors could be determined in this class, rather than passed from World?
    Color obstacleColor;
    Color groundColor;

    public LevelCreator(int canvasWidth, int tileSize, int groundLevel, int goSpeed, Color obstacleColor, Color groundColor) {
        this.canvasWidth = canvasWidth;
        this.tileSize = tileSize;
        this.groundLevel = groundLevel;
        this.goSpeed = goSpeed;
        this.obstacleColor = obstacleColor;
        this.groundColor = groundColor;
        this.groundLevels = new HashMap<>();
        this.gameObjectPositions = new ArrayList<>();
    }

    public ArrayList<Integer> getGameObjectPositions() {
        return gameObjectPositions;
    }
    
    public HashMap<Integer, Integer> getGroundLevels() {
        return groundLevels;
    }

    public ArrayList<GameObject> createObjects(String filePath) {
        ArrayList<GameObject> objects = new ArrayList<>();
        String lvlDataLine = "";
        int i = 0;
        int obstacleX = canvasWidth + (5 * tileSize);
        int platformHeight = tileSize;
        int platformWidth = tileSize;
//        Random random = new Random();

        try {
            LevelDataReader lvlReader = new LevelDataReader();
            ArrayList<String> lvlData = lvlReader.readLevelFile(filePath);
            while (i < lvlData.size() && (lvlDataLine = lvlData.get(i)) != null) {
                for (int j = 0; j < lvlDataLine.length(); j++) {
//                    int rand = 1 + random.nextInt(2);
//                    int platformHeight = tileSize * rand;
                    if (lvlDataLine.charAt(j) == '2') {
                        objects.add(createPlatform(obstacleX, groundLevel - platformHeight, platformWidth, platformHeight));
                        obstacleX += platformWidth;
                        //to add: if last was platform, don't put into groundlvls, don't add into positions
                        groundLevels.put(obstacleX, groundLevel - platformHeight);
                    } else if (lvlDataLine.charAt(j) == '1') {
                        objects.add(createObstacle(obstacleX, groundLevel - tileSize));
                        obstacleX += tileSize;
                        groundLevels.put(obstacleX, groundLevel);
                    } else if (lvlDataLine.charAt(j) == '0') {
                        obstacleX += tileSize;
                        groundLevels.put(obstacleX, groundLevel);
                    }
                    gameObjectPositions.add(obstacleX);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        }

        return objects;
    }

    private Obstacle createObstacle(int x, int y) {
        Polygon obstacleSprite = new Polygon();
        obstacleSprite.setFill(obstacleColor);
        obstacleSprite.getPoints().addAll(new Double[]{
            ((double) tileSize / 2), 0.0,
            0.0, (double) tileSize,
            (double) tileSize, (double) tileSize});
        Obstacle o = new Obstacle(obstacleSprite, x, y);
        o.getMover().setSpeed(goSpeed);
        return o;
    }

    private Platform createPlatform(int x, int y, double width, double height) {
        Rectangle sprite = new Rectangle(width, height, groundColor);
        Platform p = new Platform(sprite, x, y);
        p.getMover().setSpeed(goSpeed);
        return p;
    }
}
