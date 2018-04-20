package mj.platformer.level;

import java.io.PrintStream;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.utils.LevelDataReader;

public class LevelCreator { // vai GameObjectCreator?

    int canvasWidth;
    int tileSize;
    int groundLevel;
    int obstacleSpeed;
    Color obstacleColor;

    public LevelCreator(int canvasWidth, int tileSize, int groundLevel, int obstacleSpeed, Color obstacleColor) {
        this.canvasWidth = canvasWidth;
        this.tileSize = tileSize;
        this.groundLevel = groundLevel;
        this.obstacleSpeed = obstacleSpeed;
        this.obstacleColor = obstacleColor;
    }

    public ArrayList<Obstacle> createObstacles(String filePath) {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        String lvlDataLine = "";
        int i = 0;
        int obstacleX = canvasWidth + (5 * tileSize);

        try {
            LevelDataReader lvlReader = new LevelDataReader();
            ArrayList<String> lvlData = lvlReader.readLevelFile(filePath);
            while (i < lvlData.size() && (lvlDataLine = lvlData.get(i)) != null) {
                while (lvlDataLine.startsWith("#")) {
                    i++;
                    lvlDataLine = lvlData.get(i);
                }
                for (int j = 0; j < lvlDataLine.length(); j++) {
                    if (lvlDataLine.charAt(j) == '1') {
                        obstacles.add(createObstacle(tileSize, tileSize, obstacleX, groundLevel - tileSize));
                        obstacleX += tileSize;
                    } else if (lvlDataLine.charAt(j) == '0') {
                        obstacleX += tileSize;
                    }
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        }

        return obstacles;
    }

    private Obstacle createObstacle(int width, int height, int x, int y) {
        Polygon obstacleSprite = new Polygon();
        obstacleSprite.setFill(obstacleColor);
        obstacleSprite.getPoints().addAll(new Double[]{
            ((double) tileSize / 2), 0.0,
            0.0, (double) tileSize,
            (double) tileSize, (double) tileSize});
        Obstacle o = new Obstacle(obstacleSprite, x, y, width, height);
        o.setSpeed(obstacleSpeed);
        return o;
    }
}
