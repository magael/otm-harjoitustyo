package mj.platformer.ui;

import java.io.BufferedReader;
import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Player;
import mj.platformer.gameobject.GameObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import mj.platformer.collision.CollisionHandler;
import mj.platformer.input.InputHandler;

// Using javafx.animation.AnimationTimer for the game loop,
// javafx.scene.layout.Pane as the root node
// and methods of javafx.scene.Node for updating position.
public class World extends Application {

    private int tileSize;
    private int canvasWidth, canvasHeight;
    private int groundLevel;
    private int playerWidth, playerHeight, playerStartX, playerStartY;
    private Color color1, color2, color3, color4, color5;
    private Color playerColor, obstacleColor, groundColor, backgroundColor;
    private boolean gameStart;
    private boolean gameOver;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Integer> scoringPositions;
    private int scoringPositionIndex;
    private int playerScoringPosition;
    private int score;
    private int obstacleSpeed;
    private Text scoreText;
    private Text startText;

    private Player player;
    private InputHandler inputHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initWorld();
        Pane pane = initPane();
        initText(pane);
        initGameObjects(pane);
//        Player player = initGameObjects(pane);
        Scene scene = initScene(pane, stage);
//        InputHandler inputHandler = new InputHandler();
        this.inputHandler = new InputHandler();
        inputHandler.initInput(scene);
        CollisionHandler collisionHandler = new CollisionHandler();

        //The game loop
        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (!inputHandler.getButtonsDown().isEmpty()) {
                    gameStart = true;
                }
                if (gameStart && !gameOver) {
                    inputHandler.handlePlayerInput(player);
                    update();
                } else if (gameOver) {
                    startText.setText("Ouch! Game over.\nPress 'R' to try again.");
                    // would be nice to have only spacebutton but that might lead to buttonmashers missing the game over ui
                    if (inputHandler.handleRestartInput()) {
                        try {
                            restart(pane, scene, stage);
                        } catch (Exception ex) {
                            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            private void update() {
                if (!player.getGrounded()) {
                    player.updatePosition();
                }

                for (Obstacle o : obstacles) {
                    o.move();

                    gameOver = collisionHandler.handleCollisions(player, o);
                    if (gameOver) {
                        break;
                    }
                }

                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));

                updateScore();
            }
        }.start();

        stage.show();
    }

    private void initWorld() {
        // reading these from a file might be nice
        tileSize = 32;
        canvasWidth = 900;
        canvasHeight = 640;
        groundLevel = (int) (canvasHeight / 1.618);
        playerWidth = tileSize;
        playerHeight = tileSize;
        playerStartX = canvasWidth - (playerWidth / 2) - (int) (canvasWidth / 1.618);
        playerStartY = groundLevel - playerHeight;

        color1 = Color.rgb(8, 28, 37);
        color2 = Color.rgb(3, 37, 29);
        color3 = Color.rgb(58, 113, 89);
        color4 = Color.rgb(139, 192, 114);
        color5 = Color.rgb(222, 244, 208);

        playerColor = color4;
        obstacleColor = color3;
        groundColor = color2;
        backgroundColor = color1;

        gameStart = false;
        gameOver = false;
        obstacleSpeed = 5;
        obstacles = new ArrayList<>();
        scoringPositions = new ArrayList<>();
        scoringPositionIndex = 0;
        playerScoringPosition = playerStartX;
        score = 0;

        scoreText = new Text(26, 42, "");
        scoreText.setFill(color3);
        scoreText.setFont(Font.font(26));
        startText = new Text((canvasWidth / 2) - 120, 100, "");
        startText.setFill(color4);
        startText.setFont(Font.font(26));
    }

    private Scene initScene(Pane pane, Stage stage) {
        Scene scene = new Scene(pane, backgroundColor);
        stage.setTitle("Escape Spikeworld");
        stage.setScene(scene);
        return scene;
    }

    private Pane initPane() {
        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);
        return pane;
    }

    private void initGameObjects(Pane pane) throws Exception {
        GameObject ground = createGround();
        pane.getChildren().add(ground.getSprite());
        this.player = createPlayer();
        pane.getChildren().add(player.getSprite());
        readLevelFile(pane);
    }

    private void initText(Pane pane) {
        // refactor the text stuff into a Score or TextUI object or sumn?
        scoreText.setText("Score: 0");
        startText.setText("Press any key to start");
        pane.getChildren().add(scoreText);
        pane.getChildren().add(startText);
    }

    private void restart(Pane pane, Scene scene, Stage stage) throws Exception {
        initWorld();
        pane = initPane();
        initText(pane);
        initGameObjects(pane);
        scene = initScene(pane, stage);
        this.inputHandler = new InputHandler();
        this.inputHandler.initInput(scene);
        gameOver = false;
        gameStart = true;
    }

    private void updateScore() {
        //Add to the score when the player passes another obstacle
        playerScoringPosition += obstacleSpeed;
        if (scoringPositionIndex < scoringPositions.size()
                && playerScoringPosition >= scoringPositions.get(scoringPositionIndex)) {
            score += 100 * (scoringPositionIndex + 1);
            scoringPositionIndex += 1;
            scoreText.setText("Score: " + score);
            startText.setText("");
        }
        if (gameStart && score == 0) {
            startText.setText("Dodge the spikes!\nPress Space to jump");
        }
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private void readLevelFile(Pane pane) throws Exception {
        String lvlDataLine = "";
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream("level1.txt");

        if (!obstacles.isEmpty()) {
            obstacles = new ArrayList<>();
        }
        int obstacleX = canvasWidth + (5 * tileSize);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((lvlDataLine = br.readLine()) != null) {
                for (int j = 0; j < lvlDataLine.length(); j++) {
                    if (lvlDataLine.charAt(j) == '1') {
                        obstacleX = fileObstacle(obstacleX, pane);
                    } else if (lvlDataLine.charAt(j) == '0') {
                        obstacleX += tileSize;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private int fileObstacle(int obstacleX, Pane pane) {
        Obstacle p = createObstacle(tileSize, tileSize, obstacleX, groundLevel - tileSize);
        p.setSpeed(obstacleSpeed);
        obstacles.add(p);
        pane.getChildren().add(p.getSprite());
        obstacleX += tileSize;
        scoringPositions.add(obstacleX);
        return obstacleX;
    }

    private Obstacle createObstacle(int width, int height, int x, int y) {
        Polygon obstacleSprite = new Polygon();
        obstacleSprite.setFill(obstacleColor);
        obstacleSprite.getPoints().addAll(new Double[]{
            ((double) tileSize / 2), 0.0,
            0.0, (double) tileSize,
            (double) tileSize, (double) tileSize});
        return new Obstacle(obstacleSprite, x, y, width, height);
    }

    private GameObject createGround() {
        Shape groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
        return new Obstacle(groundSprite, 0, groundLevel, canvasWidth, canvasHeight - groundLevel);
    }

    private Player createPlayer() {
        Shape playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
        return new Player(playerSprite, playerStartX, playerStartY);
    }
}
