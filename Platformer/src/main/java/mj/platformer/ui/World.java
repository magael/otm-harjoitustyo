package mj.platformer.ui;

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

// Using javafx.animation.AnimationTimer for the game loop,
// javafx.scene.layout.Pane as the root node
// and methods of javafx.scene.Node for updating position.
public class World extends Application {

    private int tileSize;
    private int canvasWidth, canvasHeight;
    private int groundLevel;
    private int playerWidth, playerHeight, playerStartX, playerStartY;
    private int goSpeed;
    private int highScore;
    private String lvlFilePath;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initWorld();
        Pane pane = initPane();
        initGameObjects(pane);
        initText(pane);
        Scene scene = initScene(pane, stage);
        initInput(scene);
        CollisionHandler collisionHandler = new CollisionHandler();
        highScore = 0;
        
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
                    gameOverEvent();
                }
            }

            private void gameOverEvent() {
                int score = scoreKeeper.getScore();
                if (score > highScore) {
                    highScore = score;
                }
                highScoreText.setText("Your score: " + score + "\nHigh score: " + highScore);

                startText.setText("Ouch! Game over.\nPress 'R' to try again.");
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

                for (GameObject go : movingObjects) {
                    go.update();

                    gameOver = collisionHandler.handleCollisions(player, go);
                    if (gameOver) {
                        break;
                    }
                }

                groundLevel = groundLevelHandler.setGroundLevel(groundLevel, goSpeed, player);
                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));

                scoreKeeper.updateScore(scoreText, startText, gameStart);
            }
        }.start();

        stage.show();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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
        color1 = Color.rgb(8, 28, 37);
        color2 = Color.rgb(3, 37, 29);
        color3 = Color.rgb(58, 113, 89);
        color4 = Color.rgb(139, 192, 114);
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
        stage.setTitle("Escape Spikeworld");
        stage.setScene(scene);
        return scene;
    }

    private void initText(Pane pane) {
        scoreText = new Text(26, 42, "Score: 0");
        scoreText.setFill(color3);
        scoreText.setFont(Font.font(26));
        pane.getChildren().add(scoreText);

        startText = new Text((canvasWidth / 2) - 120, 100, "Press any key to start");
        startText.setFill(color4);
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

    private Shape createGround() {
        Shape groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
        groundSprite.setTranslateY(groundLevel);
        return groundSprite;
    }

    private Player createPlayer() {
        Shape playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
        return new Player(playerSprite, playerStartX, playerStartY);
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
