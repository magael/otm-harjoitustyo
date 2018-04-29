package mj.platformer.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import mj.platformer.collision.CollisionHandler;
import mj.platformer.gameobject.Player;
import mj.platformer.input.InputHandler;
import mj.platformer.level.LevelCreator;
import mj.platformer.level.ScoreKeeper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import mj.platformer.gameobject.GameObject;
import mj.platformer.input.InputListener;
import mj.platformer.level.GroundLevelHandler;

/**
 * The main class for the platforming game. Initializes the UI, entities and
 * systems. Contains the game loop.
 *
 * @author Maguel
 */
public class World extends Application {

    private int tileSize;
    private int canvasWidth, canvasHeight;
    private int groundLevel;
    private int playerWidth, playerHeight, playerStartX, playerStartY;
    private int goSpeed;
    private int highScore;
    private String lvlFilePath;
    private String title;
    private ArrayList<GameObject> movingObjects;
    private boolean gameStart;
    private boolean gameOver;
    private Color color1, color2, color3, color4;
    private Color playerColor, obstacleColor, groundColor, backgroundColor;
    private Text startText;
    private Text scoreText;
    private Text highScoreText;

    private Player player;
    private InputHandler inputHandler;
    private ScoreKeeper scoreKeeper;
    private GroundLevelHandler groundLevelHandler;

    /**
     * Launches the application, calling the start(Stage) method.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializations and the game loop.
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        initWorld();
        Pane pane = initPane();
        initGameObjects(pane);
        initText(pane);
        Scene scene = initScene(pane, stage);
        initInput(scene);
        CollisionHandler collisionHandler = new CollisionHandler();
        try {
            readHighScore(title + "-highscore.txt");
        } catch (Exception e) {
            highScore = 0;
        }

        /**
         * The game loop is based on javafx.animation.AnimationTimer.
         */
        new AnimationTimer() {
            /**
             *
             * @param currentTime
             */
            @Override
            public void handle(long currentTime) {
                if (!inputHandler.getButtonsDown().isEmpty()) {
                    gameStart = true;
                }
                if (gameStart && !gameOver) {
                    inputHandler.handlePlayerInput(player);
                    update();
                } else if (gameOver) {
                    gameOverEvent();
                }
            }

            private void gameOverEvent() {
                int score = scoreKeeper.getScore();
                if (score > highScore) {
                    highScore = score;
                    try {
                        writeHighScore(title + "-highscore.txt");
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                highScoreText.setText("Your score: " + score + "\nHigh score: " + highScore);

                if (!scoreKeeper.getGameWon()) {
                    startText.setText("Ouch! Game over.\nPress 'R' to try again.");
                }
                if (inputHandler.handleRestartInput()) {
                    try {
                        restart(pane, scene, stage);
                    } catch (Exception ex) {
                        Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            private void update() {
                if (!player.getGrounded()) {
                    player.update();
                }
                groundLevel = groundLevelHandler.setGroundLevel(groundLevel, goSpeed, player);
                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));

                for (GameObject go : movingObjects) {
                    go.update();

                    gameOver = collisionHandler.handleCollisions(player, go);
                    if (gameOver) {
                        break;
                    }
                }

                scoreKeeper.updateScore(gameStart);
                scoreText.setText("Score: " + scoreKeeper.getScore());
                startText.setText(scoreKeeper.getStartText());
                if (!gameOver) {
                    gameOver = scoreKeeper.getGameWon();
                }
            }
        }.start();

        stage.show();
    }

    private void initWorld() {
        tileSize = 32;
        canvasWidth = 900;
        canvasHeight = 640;
        groundLevel = (int) (canvasHeight / 1.618);

        playerWidth = tileSize;
        playerHeight = tileSize;
        playerStartX = canvasWidth - (playerWidth / 2) - (int) (canvasWidth / 1.618);
        playerStartY = groundLevel - playerHeight;
        goSpeed = 5;

        initColors();

        gameStart = false;
        gameOver = false;

        lvlFilePath = "leveldata/level1.cfg";

        scoreKeeper = new ScoreKeeper(goSpeed, playerStartX);
    }

    private void initColors() {
        color1 = Color.web("#7BC1BA");
        color2 = Color.web("#191919");
        color3 = Color.web("#D6D3BD");
        color4 = Color.web("#EE4466");

        playerColor = color4;
        obstacleColor = color3;
        groundColor = color2;
        backgroundColor = color1;
    }

    private Pane initPane() {
        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);
        return pane;
    }

    private Scene initScene(Pane pane, Stage stage) {
        Scene scene = new Scene(pane, backgroundColor);
        title = "Escape Spikeworld";
        stage.setTitle(title);
        stage.setScene(scene);
        return scene;
    }

    private void initText(Pane pane) {
        scoreText = new Text(26, 42, "Score: 0");
        scoreText.setFill(color2);
        scoreText.setFont(Font.font(26));
        pane.getChildren().add(scoreText);

        startText = new Text((canvasWidth / 2) - 120, 100, "Press any key to start");
        startText.setFill(color2);
        startText.setFont(Font.font(26));
        pane.getChildren().add(startText);

        highScoreText = new Text((canvasWidth / 2) - 120, canvasHeight - ((canvasHeight - groundLevel) / 2), "");
        highScoreText.setFill(color4);
        highScoreText.setFont(Font.font(26));
        pane.getChildren().add(highScoreText);
    }

    private void initGameObjects(Pane pane) throws Exception {
        movingObjects = new ArrayList<>();
        LevelCreator lvlCreator = new LevelCreator(canvasWidth, tileSize, groundLevel, goSpeed, obstacleColor, groundColor);
        movingObjects = lvlCreator.createObjects(lvlFilePath);
        for (GameObject go : movingObjects) {
            pane.getChildren().add(go.getSprite());
            scoreKeeper.addPosition((int) go.getX() + tileSize);
        }

        groundLevelHandler = new GroundLevelHandler(lvlCreator);

        pane.getChildren().add(createGround());

        this.player = createPlayer();
        pane.getChildren().add(player.getSprite());
    }

    private void writeHighScore(String filePath) throws IOException {
        try {
            // Create a new file if one is not found at project / executable root
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(Integer.toString(highScore));
            // Close the output stream
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void readHighScore(String filePath) {
        try {
            FileInputStream stream = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                highScore = Integer.parseInt(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private Shape createGround() {
        Shape groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
        groundSprite.setTranslateY(groundLevel);
        return groundSprite;
    }

    private Player createPlayer() {
        Shape playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
        playerSprite.setStroke(color2);
        return new Player(playerSprite, playerStartX, playerStartY, playerWidth);
    }

    private void initInput(Scene scene) {
        InputListener il = new InputListener();
        this.inputHandler = new InputHandler(il.initInput(scene));
    }

    private void restart(Pane pane, Scene scene, Stage stage) throws Exception {
        initWorld();
        pane = initPane();
        initGameObjects(pane);
        initText(pane);
        startText.setText("");
        scene = initScene(pane, stage);
        initInput(scene);
        gameStart = true;
    }
}
