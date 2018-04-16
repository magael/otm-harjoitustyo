package mj.platformer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

// Using javafx.animation.AnimationTimer for the game loop,
// javafx.scene.layout.Pane as the root node
// and methods of javafx.scene.Node for updating position.
public class World extends Application {

    private final int tileSize;
    private final int canvasWidth, canvasHeight;
    private int groundLevel;
    private int playerWidth, playerHeight, playerStartX, playerStartY;
    private Color color1, color2, color3, color4, color5;
    private Color playerColor, platformColor, groundColor, backgroundColor;
    private boolean gameOver;

    public World() { // Move these to init()?
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

        //palette tests
//        color1 = Color.rgb(109, 204, 50);
//        color2 = Color.rgb(255, 87, 96);
//        color1 = Color.WHEAT;
//        color2 = Color.SALMON;
//        color3 = Color.CADETBLUE;
//        color4 = Color.DARKSLATEGREY;
        gameOver = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        playerColor = color4;
        platformColor = color3;
        groundColor = color2;
        backgroundColor = color1;
//        playerColor = color1;
//        platformColor1 = color2;
//        groundColor = color3;
//        backgroundColor = color4;

        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);

        GameObject ground = createGround();
        pane.getChildren().add(ground.getSprite());

        Platform platform1 = createPlatform(tileSize, tileSize, canvasWidth, groundLevel - tileSize);
//        Platform platform1 = createPlatform(tileSize * 3, tileSize, canvasWidth, groundLevel - tileSize);
        platform1.setSpeed(5);
        pane.getChildren().add(platform1.getSprite());
        
        //read platforms from file test
        ArrayList<Platform> platforms = new ArrayList<>();
        Scanner scanner = new Scanner("level1.txt");
        //end of read platforms from file test
        

        Player player = createPlayer();
        pane.getChildren().add(player.getSprite());

        Scene scene = new Scene(pane, backgroundColor);
        stage.setTitle("Platformer");
        stage.setScene(scene);

        Map<KeyCode, Boolean> buttonsDown = initInput(scene);

        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                //platform movement test
                if (!gameOver) {
                    player.setGrounded(isGrounded(player));

                    if (buttonsDown.getOrDefault(KeyCode.SPACE, false)) {
                        player.jump();
                    }
                    
                    platform1.move();
                    if (!player.getGrounded()) {
                        player.updatePosition();
                    }

                    checkCollisions(player, platform1);
                }
            }
        }.start();

        stage.show();
    }

    private Map<KeyCode, Boolean> initInput(Scene scene) {
        Map<KeyCode, Boolean> buttonsDown = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            buttonsDown.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            buttonsDown.put(event.getCode(), Boolean.FALSE);
        });
        return buttonsDown;
    }

    private Platform createPlatform(int width, int height, int x, int y) {
        Polygon platformSprite = new Polygon();
        platformSprite.setFill(platformColor);
        platformSprite.getPoints().addAll(new Double[]{
            ((double) tileSize / 2), 0.0,
            0.0, (double) tileSize,
            (double) tileSize, (double) tileSize});
//        Shape platformSprite = new Rectangle(width, height, platformColor);
//        Node platformSprite = new Rectangle(width, height, platformColor);
        return new Platform(platformSprite, x, y, width, height);
    }

    private GameObject createGround() {
//        Node groundSprite = new Rectangle(canvasWidth, tileSize / 2, groundColor);
        Shape groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
//        Node groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, groundColor);
//        return new Ground(groundSprite, 0, groundLevel);
        return new Platform(groundSprite, 0, groundLevel, canvasWidth, canvasHeight - groundLevel);
    }

    public Player createPlayer() {
        Shape playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
//        Node playerSprite = new Rectangle(playerWidth, playerHeight, playerColor);
        return new Player(playerSprite, playerStartX, playerStartY);
    }

    private void checkCollisions(Player player, Platform platform) {
        // atm ends game on any collision.

//        // the first version: tested only on Rectangles
//        double playerX = player.getSprite().getTranslateX();
//        double playerY = player.getSprite().getTranslateY();
//        double platformX = platform.getSprite().getTranslateX();
//        double platformY = platform.getSprite().getTranslateY();
//        double platformWidth = platform.getWidth();
//        double platformHeight = platform.getHeight();
//        
//        if ((playerY + playerHeight) >= platformY && playerY <= (platformY + platformHeight)) {
//            player.setGrounded(true);
//            if ((playerX + playerWidth) >= platformX && playerX <= (platformX + platformWidth)) {
////                System.out.println("collision");
////                maybe add blinking player sprite effect
//                gameOver = true;
//            }
//        }

        // alternative version: assumes player sprites are JavaFX Shapes
        Shape collisionArea = Shape.intersect(player.getSprite(), platform.getSprite());
        gameOver = collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    private boolean isGrounded(Player player) {
        double playerY = player.getSprite().getTranslateY();
        if ((playerY + playerHeight) >= groundLevel) {
            //clamp position
            player.getSprite().setTranslateY(groundLevel - playerHeight);
            
            player.setFalling(false);
            player.setVelocity(0);
            
            return true;
        }
        return false;
    }

//    public void setGameOver(boolean gameOver) {
//        this.gameOver = gameOver;
//    }
}
