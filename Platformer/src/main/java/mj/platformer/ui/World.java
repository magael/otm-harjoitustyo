package mj.platformer.ui;

import mj.platformer.gameobject.Obstacle;
import mj.platformer.gameobject.Player;
import mj.platformer.gameobject.GameObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
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

    private final int tileSize;
    private final int canvasWidth, canvasHeight;
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

    public World() {
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
        // refactor the text stuff into a method and call from start? maybe even a Score or TextUI object or sumn?
        scoreText = new Text(26, 42, "Score: 0");
        scoreText.setFill(color3);
        scoreText.setFont(Font.font(26));
        startText = new Text((canvasWidth / 2) - 120, 100, "Press any key to start");
        startText.setFill(color4);
        startText.setFont(Font.font(26));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);

        pane.getChildren().add(scoreText);
        pane.getChildren().add(startText);

//        Obstacle platform1 = createPlatform(tileSize, tileSize, canvasWidth, groundLevel - tileSize); // earlier version
////        Platform platform1 = createPlatform(tileSize * 3, tileSize, canvasWidth, groundLevel - tileSize); // earlier version
//        platform1.setSpeed(5);
//        pane.getChildren().add(platform1.getSprite());
        GameObject ground = createGround();
        pane.getChildren().add(ground.getSprite());

        Player player = createPlayer();
        pane.getChildren().add(player.getSprite());

        readLevelFile(pane);

        Scene scene = new Scene(pane, backgroundColor);
        stage.setTitle("Escape Spikeworld");
        stage.setScene(scene);

//        Map<KeyCode, Boolean> buttonsDown = initInput(scene);
        InputHandler inputHandler = new InputHandler();
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
//                    input();
                    inputHandler.handlePlayerInput(player);
                    update();
                } else if (gameOver) {
                    startText.setText("Ouch! Game over.");
                }
            }

            private void update() {
                if (!player.getGrounded()) {
                    player.updatePosition();
                }
                for (Obstacle o : obstacles) {
                    o.move();

//                    checkCollisions(player, p); // earlier version
                    // hoping it speeds things up to first check if the object is even close to the player
                    if (o.getSprite().getTranslateX() < playerStartX + playerWidth
                            && o.getSprite().getTranslateX() > playerStartX) {
                        gameOver = collisionHandler.handleCollisions(player, o);
                        if (gameOver) {
                            break;
                        }
                    }
                }
//                player.setGrounded(isGrounded(player)); // earlier version
                player.setGrounded(collisionHandler.isGrounded(player, playerHeight, groundLevel));

                updateScore();
            }

//            private void input() { // earlier version
//                if (buttonsDown.getOrDefault(KeyCode.SPACE, false)) {
//                    player.jump();
//                }
//            }
        }.start();

        stage.show();
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

    private void readLevelFile(Pane pane) {
        try {
            int obstacleX = canvasWidth + (5 * tileSize);
            File lvl = new File("src/main/java/mj/platformer/data/level1.txt");
            Scanner scanner = new Scanner(lvl);
            while (scanner.hasNextLine()) {
                String platformData = scanner.nextLine();
                for (int i = 0; i < platformData.length(); i++) {
                    if (platformData.charAt(i) == '1') {
                        obstacleX = fileObstacle(obstacleX, pane);
                    } else if (platformData.charAt(i) == '0') {
                        obstacleX += tileSize;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // if the intention is to loop around, reset obstaclepos & scanner in handle() & edit the above it so it works there
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
//        Shape platformSprite = new Rectangle(width, height, platformColor); // earlier version
//        Node platformSprite = new Rectangle(width, height, platformColor); // earlier version
        return new Obstacle(obstacleSprite, x, y, width, height);
    }

    private GameObject createGround() {
//        Node groundSprite = new Rectangle(canvasWidth, tileSize / 2, groundColor); // earlier version
        Shape groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
//        Node groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor); // earlier version
//        return new Ground(groundSprite, 0, groundLevel); // earlier version
        return new Obstacle(groundSprite, 0, groundLevel, canvasWidth, canvasHeight - groundLevel);
    }

    private Player createPlayer() {
        Shape playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
//        Node playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
        return new Player(playerSprite, playerStartX, playerStartY);
    }

//    private Map<KeyCode, Boolean> initInput(Scene scene) { // earlier version
//        Map<KeyCode, Boolean> buttonsDown = new HashMap<>();
//        scene.setOnKeyPressed(event -> {
//            buttonsDown.put(event.getCode(), Boolean.TRUE);
//        });
//        scene.setOnKeyReleased(event -> {
//            buttonsDown.put(event.getCode(), Boolean.FALSE);
//        });
//        return buttonsDown;
//    }
//    
//    private void checkCollisions(Player player, Obstacle obstacle) { // earlier version
//        // atm ends game on any collision.
//
////        // the first version: tested only on Rectangles
////        double playerX = player.getSprite().getTranslateX();
////        double playerY = player.getSprite().getTranslateY();
////        double platformX = platform.getSprite().getTranslateX();
////        double platformY = platform.getSprite().getTranslateY();
////        double platformWidth = platform.getWidth();
////        double platformHeight = platform.getHeight();
////        
////        if ((playerY + playerHeight) >= platformY && playerY <= (platformY + platformHeight)) {
////            player.setGrounded(true);
////            if ((playerX + playerWidth) >= platformX && playerX <= (platformX + platformWidth)) {
//////                System.out.println("collision");
//////                maybe add blinking player sprite effect
////                gameOver = true;
////            }
////        }
//        // alternative version: assumes player sprites are JavaFX Shapes
//        // hoping it speeds things up to first check if the object is even close to the player
//        if (obstacle.getSprite().getTranslateX() < playerStartX + playerWidth
//                && obstacle.getSprite().getTranslateX() > playerStartX) {
//            Shape collisionArea = Shape.intersect(player.getSprite(), obstacle.getSprite());
//            if (collisionArea.getBoundsInLocal().getWidth() != -1) {
//                gameOver = true;
//            }
//        }
//    }
//    
//    private boolean isGrounded(Player player) { // earlier version
//        double playerY = player.getSprite().getTranslateY();
//        if ((playerY + playerHeight) >= groundLevel) {
//            //clamp position
//            player.getSprite().setTranslateY(groundLevel - playerHeight);
//
//            player.setFalling(false);
//            player.setVelocity(0);
//
//            return true;
//        }
//        return false;
//    }
}
