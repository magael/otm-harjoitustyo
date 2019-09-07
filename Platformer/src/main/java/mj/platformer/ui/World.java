package mj.platformer.ui;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import mj.platformer.collision.CollisionHandler;
import mj.platformer.gameobject.Player;
import mj.platformer.gameobject.GameObject;
import mj.platformer.ui.audio.AudioHandler;
import mj.platformer.ui.input.InputListener;
import mj.platformer.ui.input.InputHandler;
import mj.platformer.level.LevelCreator;
import mj.platformer.level.GroundLevelHandler;
import mj.platformer.score.ScoreKeeper;
import mj.platformer.score.HighScoreHandler;
import org.apache.commons.math3.random.RandomDataGenerator;

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
    private int maxShake;
    private int shake;
    private long pauseTime;
    private double goSpeed;
    private ArrayList<GameObject> movingObjects;
    private String lvlFilePath;
    private String jumpSound;
    private String highScoreFilePath;
    private String title;
    private boolean gameStarted;
    private boolean gameOver;
    private boolean gamePaused;
    private boolean reflectionOn;
    private boolean musicOn;
    private boolean sfxOn;
    private boolean noFrameCap;
    private Reflection playerReflection;
    private Color color1, color2, color3, color4;
    private Color playerColor, obstacleColor, groundColor, backgroundColor;
    private Text startText;
    private Text scoreText;
    private Text highScoreText;
    private Text levelText;
    private Pane mainPane;
    private Scene mainScene;

    private Player player;
    private InputHandler inputHandler;
    private ScoreKeeper scoreKeeper;
    private GroundLevelHandler groundLevelHandler;
    private LevelCreator lvlCreator;
    private AudioHandler audioHandler;

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
        audioHandler = initAudio();
        CollisionHandler collisionHandler = new CollisionHandler();
        HighScoreHandler highScoreHandler = new HighScoreHandler();
        highScoreHandler.readHighScore(highScoreFilePath, levelCount); // if no highscore, highscore = 0

        // Start scene
        Pane startPane = initPane();
        Scene startScene = initScene(startPane, stage);
        initInput(startScene);
        initStartText(startPane);

        // Options scene
        Pane optionsPane = initPane();
        Scene optionsScene = initScene(optionsPane, stage);

        // Info scene
        Pane infoPane = initPane();
        Scene infoScene = initScene(infoPane, stage);

        // Main Scene
        mainPane = initPane();
        mainScene = initScene(mainPane, stage);

        /**
         * The game loop is based on javafx.animation.AnimationTimer.
         */
        new AnimationTimer() {
            long lastTime = System.nanoTime();
            double amountOfTicks = 60.0;
            double ns = 1000000000 / amountOfTicks;
            double delta = 0;

            /**
             *
             * @param currentTime
             */
            @Override
            public void handle(long currentTime) {
                if (!gameStarted) { // start scene loop
                    // level selection input
                    level = inputHandler.levelInput();
                    if (level > 0 && level <= levelCount) {
                        setMainScene(stage, "leveldata/level" + Integer.toString(level) + ".cfg");
                    }

                    // "options" input
                    if (inputHandler.optionsInput()) {
                        initOptionsUI(optionsPane);
                        setScene(stage, optionsScene);
                    }

                    // "information" input
                    if (inputHandler.infoInput()) {
                        initInfoUI(infoPane);
                        setScene(stage, infoScene);
                    }

                    if (inputHandler.backToMenuInput()) {
                        setStartScene(stage, startScene);
                    }
                } else { // main scene loop
//                    long now = System.nanoTime();
                    long now = currentTime;
                    delta += (now - lastTime) / ns;
                    lastTime = now;
                    double fps = 1000000.0 / (lastTime - (lastTime = System.nanoTime()));

                    // possible pause input
                    // the timer prevents single keystrokes from registering as
                    // multiple, and prevents slowing the game down by holding P
                    if (inputHandler.pauseInput() && pauseTime + 250000000 < currentTime) {
                        gamePaused = !gamePaused;
                        if (gamePaused) {
                            levelText.setText("PAUSED");
                        } else {
                            levelText.setText("Level: " + level);
                        }
                        pauseTime = currentTime;
                    }

                    if (gameOver) {
                        delta = 0;
                        gameOverEvent();
                    } else if (gameStarted && !gamePaused) {
                        // jumping input and sfx
                        if (inputHandler.playerInput(player) && sfxOn) {
                            audioHandler.playClip(jumpSound);
                        }
                        // object positions, collisions and the score
                        if (noFrameCap) {
                            update();
                        } else {
                            while (delta >= 1) {
                                update();
                                delta--;
                            }
                        }
                    } else {
                        // paused
                        delta--;
                    }

                    if (inputHandler.quitInput()) {
                        quit();
                    }

                    System.out.println(fps);
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
                // update player vertical position
                if (!player.getGrounded()) {
                    player.update();
                }
                // check for ground
                groundLevel = groundLevelHandler.setGroundLevel(groundLevel, goSpeed, player);
                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));
                updatePlayerEffect();

                // screenshake
                if (!player.getGrounded()) {
                    shake = maxShake;
                } else if (shake > 0) {
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

    private void quit() {
        Platform.exit();
        System.exit(0);
    }

    private void setStartScene(Stage stage, Scene startScene) {
        gameStarted = false;
        setScene(stage, startScene);
    }

    private void setScene(Stage stage, Scene scene) {
        stage.setScene(scene);
        initInput(scene);
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

        tileSize = 32;
        canvasWidth = 900;
        canvasHeight = 640;
        groundLevel = (int) (canvasHeight / 1.618);

        playerWidth = tileSize;
        playerHeight = tileSize;
        playerStartX = canvasWidth - (playerWidth / 2) - (int) (canvasWidth / 1.618);
        playerStartY = groundLevel - playerHeight;

        levelCount = 2;
        goSpeed = 5;
        maxShake = 0;
        pauseTime = 0;
        musicOn = false;
        sfxOn = false;
        noFrameCap = false;

        initColors();
        newGame();
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

    private AudioHandler initAudio() {
        jumpSound = "audio/jump.mp3";
        String musicFile = "audio/POL-ninja-panda-short.wav";
        AudioHandler audioHandler = new AudioHandler();
        audioHandler.addClip(jumpSound);
        audioHandler.addMusic(musicFile);
        if (musicOn) {
            audioHandler.playMusic();
        }
        return audioHandler;
    }

    private Pane initPane() {
        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);
        pane.setStyle("-fx-background: #191919;");
        return pane;
    }

    private Scene initScene(Pane pane, Stage stage) {
        Scene scene = new Scene(pane, groundColor);
        stage.setTitle(title);
        stage.setScene(scene);
        return scene;
    }

    private void initOptionsUI(Pane pane) {
        // title
        Text optionsText = new Text("Options");
        optionsText.setFill(playerColor);

        // screen shake on/off
        HBox shakeRow = new HBox(10);
        shakeRow.setPadding(new Insets(10));
        Label screenShake = new Label("Screen shake");
        Button shakeButton = new Button("On/Off");
        shakeButton.setOnAction(e -> {
            if (maxShake > 0) {
                maxShake = 0;
            } else {
                maxShake = 12;
            }
        });
        shakeRow.getChildren().addAll(screenShake, shakeButton);

        // music on/off
        HBox musicRow = new HBox(10);
        musicRow.setPadding(new Insets(10));
        Label music = new Label("Music");
        Button musicButton = new Button("On/Off");
        musicButton.setOnAction(e -> {
            if (musicOn) {
                audioHandler.stopMusic();
            } else {
                audioHandler.playMusic();
            }
            musicOn = !musicOn;
        });
        musicRow.getChildren().addAll(music, musicButton);

        // sfx on/off
        HBox sfxRow = new HBox(10);
        musicRow.setPadding(new Insets(10));
        Label sfx = new Label("Sound effects");
        Button sfxButton = new Button("On/Off");
        sfxButton.setOnAction(e -> {
            sfxOn = !sfxOn;
        });
        sfxRow.getChildren().addAll(sfx, sfxButton);

        // frame cap on/off
        HBox frameCapRow = new HBox(10);
        frameCapRow.setPadding(new Insets(10));
        Label frameLabel = new Label("Frame rate cap");
        Button frameCapButton = new Button("On/Off");
        frameCapButton.setOnAction(e -> {
            noFrameCap = !noFrameCap;
        });
        frameCapRow.getChildren().addAll(frameLabel, frameCapButton);

        // back to menu text
        Text backText = new Text("Press 'B' to go back to the main menu.");
        backText.setFill(backgroundColor);

        // adding everything together
        VBox vBox = new VBox(30);
        vBox.setPadding(new Insets(100));
        vBox.getChildren().addAll(optionsText, shakeRow, musicRow, sfxRow, frameCapRow, backText);
        pane.getChildren().add(vBox);
    }

    private void initInfoUI(Pane pane) {
        Text info = new Text(200, 150, "Controls:\n"
                + "Jumping: Spacebar.\n"
                + "After game over: R to retry, B to return to the menu.\n"
                + "Pause: P.\n"
                + "Quit: Q or close the window.\n\n"
                + "The high score for each level will be stored at\n"
                + highScoreFilePath + ".\n\n"
                + "Credits:\n"
                + "Game by Mikael Jaakkola,\n"
                + "licensed under the MIT License.\n"
                + "Background music by PlayOnLoop.com,\n"
                + "licensed under Creative Commons By Attribution 3.0.");
        info.setFill(playerColor);
        info.setFont(Font.font(18));

        Text back = new Text(200, 550, "Press 'B' to go back to the menu.");
        back.setFill(backgroundColor);
        back.setFont(Font.font(18));

        pane.getChildren().addAll(info, back);
    }

    private void initObjectsTextAndInput(Pane pane, Scene scene) {
        initGameObjects(pane);
        initMainText(pane);
        initInput(scene);
        levelText.setText("Level: " + level);
    }

    private void initMainText(Pane pane) {
        scoreText = new Text(26, 42, "Score: 0");
        scoreText.setFill(color2);
        scoreText.setFont(Font.font(26));

        startText = new Text((canvasWidth / 2) - 120, 100, "");
        startText.setFill(color2);
        startText.setFont(Font.font(26));

        highScoreText = new Text((canvasWidth / 2) - 120, canvasHeight - ((canvasHeight - groundLevel) / 2), "");
        highScoreText.setFill(color4);
        highScoreText.setFont(Font.font(26));

        levelText = new Text(canvasWidth - 120, 42, "");
        levelText.setFill(color2);
        levelText.setFont(Font.font(26));

        pane.getChildren().addAll(scoreText, startText, highScoreText, levelText);
    }

    private void initStartText(Pane pane) {
        Text titleText = new Text(200, 150, title);
        titleText.setFill(color4);
        titleText.setFont(Font.font("", FontWeight.BOLD, 40));

        startText = new Text(200, 250, "Select a level.\n\n"
                + "Press 1 for Level 1 (Easy).\n\n"
                + "Press 2 for Level 2 (Hard).");
        startText.setFill(color1);
        startText.setFont(Font.font(26));

        Text optionsText = new Text(200, 475, "Press 'O' for options.");
        optionsText.setFill(playerColor);
        optionsText.setFont(Font.font(18));

        Text infoText = new Text(200, 525, "Press 'I' for information.");
        infoText.setFill(playerColor);
        infoText.setFont(Font.font(18));

        pane.getChildren().addAll(titleText, startText, optionsText, infoText);
    }

    private void initInput(Scene scene) {
        InputListener il = new InputListener();
        this.inputHandler = new InputHandler(il.initInput(scene));
    }

    private void initGameObjects(Pane pane) {
        //the main scene background
        pane.getChildren().add(new Rectangle(canvasWidth, canvasHeight, backgroundColor));

        //init platforms and obstacles
        movingObjects = new ArrayList<>();
        lvlCreator = new LevelCreator(canvasWidth, tileSize, groundLevel, goSpeed, obstacleColor, groundColor);
        try {
            lvlCreator.createObjectsFromFile(lvlFilePath);
        } catch (Exception e) {
            System.out.println("Error when attempting to read the level data file:\n"
                    + e + "\nApplication closing.");
            quit();
        }
        movingObjects = lvlCreator.getObjects();
        for (GameObject go : movingObjects) {
            pane.getChildren().add(go.getSprite());
            scoreKeeper.addPosition((int) go.getX() + tileSize); // scoring positions = moving object positions
        }

        groundLevelHandler = new GroundLevelHandler(lvlCreator);
        //the ground sprite
        pane.getChildren().add(createGround());

        //init the player
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
        playerSprite.setStroke(color2);
        playerSprite.setStrokeWidth(2);
        playerSprite.setEffect(new Reflection());
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
        newGame();
        pane = initPane();
        scene = initScene(pane, stage);
        initObjectsTextAndInput(pane, scene);
        startText.setText("");
    }

    private void newGame() {
        gameOver = false;
        gamePaused = false;
        scoreKeeper = new ScoreKeeper(playerStartX);
    }

    // apply a pseudorandom vertical offset to the player and other object sprites
    // ensuring the last stutter goes opposite of the moving direction
    // using Apache Commons Math library
    private void screenShake() {
        int random = 1;
        if (shake > 1) {
            random = new RandomDataGenerator().nextInt(-shake, shake + 1);
        }

        player.setX(player.getX() + random);
        for (GameObject go : movingObjects) {
            go.setX(go.getX() + random);
        }

        shake -= 1;
    }
}
