package mj.platformer.ui;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import mj.platformer.collision.CollisionHandler;
import mj.platformer.gameobject.Player;
import mj.platformer.gameobject.GameObject;
import mj.platformer.input.InputListener;
import mj.platformer.input.InputHandler;
import mj.platformer.level.LevelCreator;
import mj.platformer.level.GroundLevelHandler;
import mj.platformer.score.ScoreKeeper;
import mj.platformer.score.HighScoreHandler;

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
    private int level;
    private double goSpeed;
    private ArrayList<GameObject> movingObjects;
    private String lvlFilePath;
    private String highScoreFilePath;
    private String title;
    private boolean gameStarted;
    private boolean gameOver;
    private boolean reflectionOn;
    private Reflection playerReflection;
    private Color color1, color2, color3, color4;
    private Color playerColor, obstacleColor, groundColor, backgroundColor;
    private Text startText;
    private Text scoreText;
    private Text highScoreText;
    private Pane mainPane;
    private Scene mainScene;

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
     */
    @Override
    public void start(Stage stage) {
        initWorld();
        CollisionHandler collisionHandler = new CollisionHandler();
        HighScoreHandler highScoreHandler = new HighScoreHandler();
        highScoreHandler.readHighScore(highScoreFilePath, 2); // if no highscore, highscore = 0

        // Start scene
        Pane startPane = initPane();
        Scene startScene = initScene(startPane, stage, groundColor);
        initInput(startScene);
        initStartSceneText(startPane);

        // Main Scene
        mainPane = initPane();
        mainScene = initScene(mainPane, stage, backgroundColor);

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
                if (!gameStarted) {
                    if (inputHandler.levelInput() == 1) {
                        setMainScene(stage, "leveldata/level1.cfg");
                        level = 1;
                    }
                    if (inputHandler.levelInput() == 2) {
                        setMainScene(stage, "leveldata/level2.cfg");
                        level = 2;
                    }
                } else {
                    if (gameStarted && !gameOver) {
                        inputHandler.playerInput(player);
                        update();
                    } else if (gameOver) {
                        gameOverEvent();
                    }
                }
            }

            private void gameOverEvent() {
                int score = scoreKeeper.getScore();
                if (score > highScoreHandler.getHighScore(level)) {
                    highScoreHandler.setHighScore(score, level);
                    highScoreHandler.writeHighScore(highScoreFilePath, 2);
                }
                highScoreText.setText("Your score: " + score + "\nHigh score: "
                        + highScoreHandler.getHighScore(level));
                if (!scoreKeeper.getGameWon()) {
                    startText.setText("Ouch! Game over.\nPress 'R' to try again"
                            + "\nor 'B' to go back to the menu.");
                }

                if (inputHandler.restartInput()) {
                    restart(mainPane, mainScene, stage);
                }

                if (inputHandler.backToMenuInput()) {
                    setStartScene(stage, startScene);
                }
            }

            private void update() {
                if (!player.getGrounded()) {
                    player.update();
                }
                groundLevel = groundLevelHandler.setGroundLevel(groundLevel, goSpeed, player);
                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));
                updatePlayerEffect();

                for (GameObject go : movingObjects) {
                    go.update();

                    if (go.getX() <= playerStartX + playerWidth && go.getX() + tileSize >= playerStartX) {
                        gameOver = collisionHandler.handleCollisions(player, go);
                    }
                }

                scoreKeeper.updateScore(gameStarted, goSpeed);
                scoreText.setText("Score: " + scoreKeeper.getScore());
                startText.setText(scoreKeeper.getStartText());
                if (!gameOver) {
                    gameOver = scoreKeeper.getGameWon();
                }
            }
        }.start();

        stage.setScene(startScene);
        stage.show();
    }

    private void setStartScene(Stage stage, Scene startScene) {
        stage.setScene(startScene);
        gameStarted = false;
        initInput(startScene);
    }

    private void setMainScene(Stage stage, String lvlFilePath) {
        this.lvlFilePath = lvlFilePath;
        gameStarted = true;

        if (gameOver) {
            restart(mainPane, mainScene, stage);
        } else {
            initGameObjects(mainPane);
            initText(mainPane);
            stage.setScene(mainScene);
            initInput(mainScene);
        }
    }

    private void initWorld() {
        title = "Escape Spikeworld";
        highScoreFilePath = title.replace(" ", "_") + "_highscore.txt";

        tileSize = 32;
        canvasWidth = 900;
        canvasHeight = 640;
        groundLevel = (int) (canvasHeight / 1.618);

        goSpeed = 5;

        initColors();

        playerWidth = tileSize;
        playerHeight = tileSize;
        playerStartX = canvasWidth - (playerWidth / 2) - (int) (canvasWidth / 1.618);
        playerStartY = groundLevel - playerHeight;

        gameOver = false;

        scoreKeeper = new ScoreKeeper(playerStartX);
    }

    private void initColors() {
        color1 = Color.web("#7BC1BA");
        color2 = Color.web("#191919");
        color3 = Color.web("#6AB583");
        color4 = Color.web("#EE4466");

        playerColor = color3;
        obstacleColor = color4;
        groundColor = color2;
        backgroundColor = color1;
    }

    private Pane initPane() {
        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);
        return pane;
    }

    private Scene initScene(Pane pane, Stage stage, Color bgcolor) {
        Scene scene = new Scene(pane, bgcolor);
        stage.setTitle(title);
        stage.setScene(scene);
        return scene;
    }

    private void initText(Pane pane) {
        scoreText = new Text(26, 42, "Score: 0");
        scoreText.setFill(color2);
        scoreText.setFont(Font.font(26));
        pane.getChildren().add(scoreText);

        startText = new Text((canvasWidth / 2) - 120, 100, "");
        startText.setFill(color2);
        startText.setFont(Font.font(26));
        pane.getChildren().add(startText);

        highScoreText = new Text((canvasWidth / 2) - 120, canvasHeight - ((canvasHeight - groundLevel) / 2), "");
        highScoreText.setFill(color4);
        highScoreText.setFont(Font.font(26));
        pane.getChildren().add(highScoreText);
    }

    private void initStartSceneText(Pane pane) {
        startText = new Text(canvasWidth / 3, (canvasHeight - (int) (canvasHeight / 1.618)),
                "Choose a level.\nPress 1 for Level 1: Easy.\nPress 2 for Level 2: Hard.");
        startText.setFill(color1);
        startText.setFont(Font.font(26));
        pane.getChildren().add(startText);
    }

    private void initInput(Scene scene) {
        InputListener il = new InputListener();
        this.inputHandler = new InputHandler(il.initInput(scene));
    }

    private void initGameObjects(Pane pane) {
        movingObjects = new ArrayList<>();
        LevelCreator lvlCreator = new LevelCreator(canvasWidth, tileSize, groundLevel, goSpeed, obstacleColor, groundColor);
        lvlCreator.createObjectsFromFile(lvlFilePath);
        movingObjects = lvlCreator.getObjects();
        for (GameObject go : movingObjects) {
            pane.getChildren().add(go.getSprite());
            scoreKeeper.addPosition((int) go.getX() + tileSize);
        }

        groundLevelHandler = new GroundLevelHandler(lvlCreator);

        pane.getChildren().add(createGround());

        this.player = createPlayer();
        player.getSprite().setEffect(new Reflection());
        pane.getChildren().add(player.getSprite());
    }

    private Shape createGround() {
        Shape groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
        groundSprite.setTranslateY(groundLevel);
        return groundSprite;
    }

    private Player createPlayer() {
        Shape playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
        playerSprite.setStroke(color2);
        playerSprite.setStrokeWidth(2);
        return new Player(playerSprite, playerStartX, playerStartY, playerWidth);
    }

    private void updatePlayerEffect() {
        if (!player.getGrounded()) {
            playerReflection = null;
            reflectionOn = false;
        } else if (!reflectionOn) {
            playerReflection = new Reflection();
            reflectionOn = true;
        }
        player.getSprite().setEffect(playerReflection);
    }

    private void restart(Pane pane, Scene scene, Stage stage) {
        initWorld();
        pane = initPane();
        initGameObjects(pane);
        initText(pane);
        startText.setText("");
        scene = initScene(pane, stage, backgroundColor);
        initInput(scene);
    }
}
