
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Second version of the platforming game (old: platformer.OldWorld.java),
// using javafx.animation.AnimationTimer for the game loop,
// javafx.scene.layout.Pane as the root node
// and methods of javafx.scene.Node (and possibly Point2D) for updating position.
public class World extends Application {

    final int tileSize;
    final int canvasWidth, canvasHeight;
    int groundLevel;
    int playerWidth, playerHeight, playerStartX, playerStartY;
    Color color1, color2, color3, color4, color5;

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
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(canvasWidth, canvasHeight);

        Player player1 = createPlayer();
        pane.getChildren().add(player1.getSprite());

        Ground ground = createGround();
        pane.getChildren().add(ground.getSprite());
        
        Platform platform1 = createPlatform(tileSize * 3, tileSize, canvasWidth, groundLevel - tileSize);
        platform1.setSpeed(5);
        pane.getChildren().add(platform1.getSprite());
        
        Scene scene = new Scene(pane, color5);
        stage.setTitle("Platformer");
        stage.setScene(scene);

        initInput(scene);

        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
//                if (buttonsDown.getOrDefault(KeyCode.SPACE, false)) {
//                    player1.jump();
//                }

                //platform movement test
                platform1.move();

            }
        }.start();
        
        stage.show();
    }

    private void initInput(Scene scene) {
        Map<KeyCode, Boolean> buttonsDown = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            buttonsDown.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            buttonsDown.put(event.getCode(), Boolean.FALSE);
        });
    }

    private Platform createPlatform(int width, int height, int x, int y) {
        Node platformSprite = new Rectangle(width, height, color2);
        return new Platform(platformSprite, x, y);
    }
    
    private Ground createGround() {
        Node groundSprite = new Rectangle(canvasWidth, canvasHeight - groundLevel, color3);
        return new Ground(groundSprite, 0, groundLevel);
    }

    public Player createPlayer() {
        Node playerSprite = new Rectangle(playerWidth, playerHeight, color1);
        return new Player(playerSprite, playerStartX, playerStartY);
    }
}
