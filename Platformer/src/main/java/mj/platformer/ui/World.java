package mj.platformer.ui;

import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Player;
import mj.platformer.gameobject.GameObject;
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
import mj.platformer.collision.CollisionHandler;
import mj.platformer.input.InputHandler;
import mj.platformer.level.LevelCreator;
import mj.platformer.level.ScoreKeeper;

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
    private int obstacleSpeed;
    private Text scoreText;
    private Text startText;
    private String lvlFilePath;

    private Player player;
    private InputHandler inputHandler;
    private ScoreKeeper scoreKeeper;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initWorld();
        Pane pane = initPane();
        initText(pane);
        initGameObjects(pane);
        Scene scene = initScene(pane, stage);
        initInputHandler(scene);
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

                scoreKeeper.updateScore(scoreText, startText, gameStart);
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
        lvlFilePath = "leveldata/level1.cfg";
        obstacleSpeed = 5;
        obstacles = new ArrayList<>();
        scoreKeeper = new ScoreKeeper(obstacleSpeed, playerStartX);

        scoreText = new Text(26, 42, "");
        scoreText.setFill(color3);
        scoreText.setFont(Font.font(26));
        startText = new Text((canvasWidth / 2) - 120, 100, "");
        startText.setFill(color4);
        startText.setFont(Font.font(26));
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
        // refactor the text stuff into a Score or TextUI object or sumn?
        scoreText.setText("Score: 0");
        startText.setText("Press any key to start");
        pane.getChildren().add(scoreText);
        pane.getChildren().add(startText);
    }

    private void initGameObjects(Pane pane) throws Exception {
        pane.getChildren().add(createGround());
        
        this.player = createPlayer();
        pane.getChildren().add(player.getSprite());
        
        LevelCreator lvlCreator = new LevelCreator(canvasWidth, tileSize, groundLevel, obstacleSpeed, obstacleColor);
        obstacles = lvlCreator.createObstacles(lvlFilePath);
        for (Obstacle o : obstacles) {
            pane.getChildren().add(o.getSprite());
            scoreKeeper.addPosition((int) o.getX() + tileSize);
        }
    }

    public void initInputHandler(Scene scene) {
        this.inputHandler = new InputHandler();
        inputHandler.initInput(scene);
    }

    private void restart(Pane pane, Scene scene, Stage stage) throws Exception {
        initWorld();
        pane = initPane();
        initText(pane);
        initGameObjects(pane);
        scene = initScene(pane, stage);
        initInputHandler(scene);
        gameOver = false;
        gameStart = true;
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
}
