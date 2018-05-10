package mj.platformer.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import mj.platformer.gameobject.GameObject;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Platform;
import mj.platformer.utils.CustomFileReader;

public class LevelCreator {

    private int tileSize;
    private int groundLevel;
    private double goSpeed;
    private int platformHeight;
    private int platformWidth;
    private double obstacleX;
    private HashMap<Double, Integer> groundLevels;
    private ArrayList<Double> gameObjectPositions;
    private Color obstacleColor;
    private Color groundColor;
    private ArrayList<GameObject> objects;
    boolean previousWasPlatform;

    public LevelCreator(int canvasWidth, int tileSize, int groundLevel, double goSpeed, Color obstacleColor, Color groundColor) {
        this.tileSize = tileSize;
        this.groundLevel = groundLevel;
        this.goSpeed = goSpeed;
        this.obstacleColor = obstacleColor;
        this.groundColor = groundColor;
        groundLevels = new HashMap<>();
        gameObjectPositions = new ArrayList<>();
        platformHeight = tileSize;
        platformWidth = tileSize;
        objects = new ArrayList<>();
        previousWasPlatform = false;
        obstacleX = canvasWidth + (10 * tileSize); // starting 5 tiles off screen
    }

    public ArrayList<Double> getGameObjectPositions() {
        return gameObjectPositions;
    }

    public HashMap<Double, Integer> getGroundLevels() {
        return groundLevels;
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public void createObjectsFromFile(String filePath) throws Exception {
        String lvlDataLine = "";
        int i = 0;
        CustomFileReader lvlReader = new CustomFileReader();
        ArrayList<String> lvlData = lvlReader.readFile(filePath);
        while (i < lvlData.size() && (lvlDataLine = lvlData.get(i)) != null) {
            createObjectsFromLine(lvlDataLine);
            i++;
        }
    }

    public void createObjectsFromLine(String lvlDataLine) {
        for (int j = 0; j < lvlDataLine.length(); j++) {
            switch (lvlDataLine.charAt(j)) {
                case '0':
                    addGround();
                    break;
                case '1':
                    addObstacle();
                    break;
                case '2':
                    addPlatform();
                    break;
                default:
                    break;
            }
        }
    }

    public void addGround() {
        obstacleX += tileSize;
        addGroundLevelPosition(obstacleX, groundLevel);
        previousWasPlatform = false;
    }

    public void addPlatform() {
        objects.add(createPlatform(obstacleX, groundLevel - platformHeight, platformWidth, platformHeight));
        if (!previousWasPlatform) {
            addGroundLevelPosition(obstacleX, groundLevel - platformHeight);
        }
        obstacleX += platformWidth;
        previousWasPlatform = true;
    }

    public void addObstacle() {
        objects.add(createObstacle(obstacleX, groundLevel - (tileSize - 4)));
        addGround();
    }

    public void addGroundLevelPosition(double obstacleX, int groundLevel) {
        groundLevels.put(obstacleX, groundLevel);
        gameObjectPositions.add(obstacleX);
    }

    private Obstacle createObstacle(double x, int y) {
        Polygon obstacleSprite = new Polygon();
        obstacleSprite.setFill(obstacleColor);
        setObstacleGeometry(obstacleSprite);
        Obstacle o = new Obstacle(obstacleSprite, x, y, tileSize);
        o.getMover().setSpeed(goSpeed);
        return o;
    }

    private void setObstacleGeometry(Polygon obstacleSprite) {
        obstacleSprite.getPoints().addAll(new Double[]{
            ((double) tileSize / 2), 0.0,
            0.0, (double) tileSize,
            (double) tileSize, (double) tileSize});
    }

    private Platform createPlatform(double x, int y, int width, int height) {
        Rectangle sprite = new Rectangle(width, height, groundColor);
        Platform p = new Platform(sprite, x, y, width);
        p.getMover().setSpeed(goSpeed);
        return p;
    }
}
