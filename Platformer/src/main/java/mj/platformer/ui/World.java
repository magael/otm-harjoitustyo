package mj.platformer.ui;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
import mj.platformer.ui.audio.AudioHandler;

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
    private int levelCount;
    private int shake;
    private double goSpeed;
    private ArrayList<GameObject> movingObjects;
    private String lvlFilePath;
    private String highScoreFilePath;
    private String title;
    private boolean gameStarted;
    private boolean gameOver;
    private boolean gamePaused;
    private boolean reflectionOn;
    private Reflection playerReflection;
    private Color color1, color2, color3, color4;
    private Color playerColor, obstacleColor, groundColor, backgroundColor;
    private Text startText;
    private Text scoreText;
    private Text highScoreText;
    private Text levelText;
    private Pane mainPane;
    private Scene mainScene;
    private MediaPlayer mediaPlayer;
    
    private Player player;
    private InputHandler inputHandler;
    private ScoreKeeper scoreKeeper;
    private GroundLevelHandler groundLevelHandler;
    private LevelCreator lvlCreator;

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
        //audio
        String jumpSound = "audio/jump.mp3";
        AudioHandler audioHandler = new AudioHandler();
        audioHandler.addClip(jumpSound);
//      Background Music by PlayOnLoop.com 
//      Licensed under Creative Commons By Attribution 3.0
        audioHandler.addMusic("audio/POL-ninja-panda-short.wav");
        
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
                    level = inputHandler.levelInput();
                    if (level > 0 && level <= levelCount) {
                        setMainScene(stage, "leveldata/level" + Integer.toString(level) + ".cfg");
                    }
                } else {
                    if (inputHandler.pauseInput()) {
                        gamePaused = !gamePaused;
                        if (gamePaused) {
                            levelText.setText("PAUSED");
                        } else {
                            levelText.setText("Level: " + level);
                        }
                    }
                    
                    if (gameStarted && !gameOver && !gamePaused) {
                        if (inputHandler.playerInput(player)) {
                            audioHandler.playClip(jumpSound);
                        }
                        
                        update();
                    } else if (gameOver) {
                        gameOverEvent();
                    }
                }
            }
            
            private void gameOverEvent() {
                long score = scoreKeeper.getScore();
                if (score > highScoreHandler.getHighScore(level)) {
                    highScoreHandler.setHighScore(score, level);
                    highScoreHandler.writeHighScore(highScoreFilePath, levelCount);
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
                //update player vertical position
                if (!player.getGrounded()) {
                    player.update();
                }
                // check for ground
                groundLevel = groundLevelHandler.setGroundLevel(groundLevel, goSpeed, player);
                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));
                updatePlayerEffect();

                // screenshake
                if (!player.getGrounded()) {
                    shake = 12;
                } else {
                    screenShake();
                }

                // move obstacles and platforms and check for collisions
                for (GameObject go : movingObjects) {
                    go.update();
                    
                    if (!gameOver) {
                        gameOver = collisionHandler.handleCollisions(player, go);
                    }
                }

                // update the score
                scoreKeeper.updateScore(gameStarted, goSpeed, level);
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
            stage.setScene(mainScene);
            initObjectsTextAndInput(mainPane, mainScene);
        }
    }
    
    private void initWorld() {
        title = "Escape Spikeworld";
        highScoreFilePath = title.replace(" ", "_") + "_highscore.txt";
        
        levelCount = 2;
        
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
        gamePaused = false;
        
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
    
    private void initObjectsTextAndInput(Pane pane, Scene scene) {
        initGameObjects(pane);
        initText(pane);
        initInput(scene);
        levelText.setText("Level: " + level);
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
        
        levelText = new Text(canvasWidth - 120, 42, "");
        levelText.setFill(color2);
        levelText.setFont(Font.font(26));
        pane.getChildren().add(levelText);
    }
    
    private void initStartSceneText(Pane pane) {
        Text titleText = new Text(200, 200, title);
        titleText.setFill(color4);
        titleText.setFont(Font.font("", FontWeight.BOLD, 40));
        pane.getChildren().add(titleText);
        
        startText = new Text(200, 300, "Select a level.\n\n"
                + "Press 1 for Level 1 (Easy).\n\n"
                + "Press 2 for Level 2 (Hard).");
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
        lvlCreator = new LevelCreator(canvasWidth, tileSize, groundLevel, goSpeed, obstacleColor, groundColor);
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
        scene = initScene(pane, stage, backgroundColor);
        initObjectsTextAndInput(pane, scene);
        startText.setText("");
    }
    
    private void screenShake() {
        if (shake > 0) {
            int random = ThreadLocalRandom.current().nextInt(-shake, shake + 1);
            
            player.setX(player.getX() + random);
            for (GameObject go : movingObjects) {
                go.setX(go.getX() + random);
            }
            
            shake -= 1;

            //weirdly works without any of this next stuff, but leaving it here for now as a note
//            HashMap<Double, Integer> groundLevels = lvlCreator.getGroundLevels();
//            
//            for (double key : groundLevels.keySet()) {
//                key += random;
//            }
//            lvlCreator.addGroundLevelPosition(player.getX(), groundLevel);
        } else {
            shake = 0;
        }
    }
}
