package mj.platformer.level;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import mj.platformer.gameobject.GameObject;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Platform;
import mj.platformer.utils.CustomFileReader;

/**
 * The LevelCreator class generates the level objects form a given filepath.
 * 
 * @author Maguel
 */
public class LevelCreator {

    private int tileSize;
    private int groundLevel;
    private double goSpeed;
    private int platformHeight;
    private int platformWidth;
    private double objectX;
    private HashMap<Double, Integer> groundLevels;
    private ArrayList<Double> gameObjectPositions;
    private Color obstacleColor;
    private Color groundColor;
    private ArrayList<GameObject> objects;
    private boolean previousWasPlatform;

    /**
     * Constructor for the LevelCreator class.
     * 
     * @param canvasWidth
     * @param tileSize
     * @param groundLevel
     * @param goSpeed
     * @param obstacleColor
     * @param groundColor
     */
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
        objectX = canvasWidth + (10 * tileSize); // starting 10 tiles off screen
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

    /**
     * Gathers data lines from the level data file.
     * 
     * @param filePath path of the level data file
     * @throws Exception
     */
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

    /**
     * Differentiates between different symbols representing different objects.
     * 
     * @param lvlDataLine
     */
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

    /**
     * The groundLevel is set to default, and the x position counter is moved
     * forward a tile width.
     */
    public void addGround() {
        objectX += tileSize;
        addGroundLevelPosition(objectX, groundLevel);
        previousWasPlatform = false;
    }

    /**
     * Adds a new Platform to the list of GameObjects.
     * Adjusts the ground level if needed.
     * The x position counter is moved forward a tile width.
     */
    public void addPlatform() {
        objects.add(createPlatform(objectX, groundLevel - platformHeight, platformWidth, platformHeight));
        if (!previousWasPlatform) {
            addGroundLevelPosition(objectX, groundLevel - platformHeight);
        }
        objectX += platformWidth;
        previousWasPlatform = true;
    }

    /**
     * Adds a new Obstacle to the list of GameObjects and calls addGround().
     */
    public void addObstacle() {
        objects.add(createObstacle(objectX, groundLevel - (tileSize - 4)));
        addGround();
    }

    /**
     * Adds an oject's x position to the list of GameObjects, and also the
     * y position to the list of changes in ground level.
     * 
     * @param objectX
     * @param groundLevel
     */
    public void addGroundLevelPosition(double objectX, int groundLevel) {
        groundLevels.put(objectX, groundLevel);
        gameObjectPositions.add(objectX);
    }

    private Obstacle createObstacle(double x, int y) {
        Polygon obstacleSprite = new Polygon();
        obstacleSprite.setFill(obstacleColor);
        setObstacleGeometry(obstacleSprite);
        Obstacle o = new Obstacle(obstacleSprite, x, y);
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
        Platform p = new Platform(sprite, x, y);
        p.getMover().setSpeed(goSpeed);
        return p;
    }
}
