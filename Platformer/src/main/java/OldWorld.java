
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Camera;

// First iteration of the platforming game,
// using Group for the root object,
// javafx.animation.Timeline for the game loop
// and javafx.scene.canvas.GraphicsContext for "drawing" entities.
public class OldWorld extends Application {

    int tileSize;
    int canvasWidth, canvasHeight;
    int playerWidth, playerHeight;
    Color color1, color2, color3, color4, color5;

    public OldWorld() {
        tileSize = 32;
        canvasWidth = 900;
        canvasHeight = 640;

        //REFACTOR INTO PLAYER OBJECT
        playerWidth = tileSize;
        playerHeight = tileSize;
        //^^^

        //Initialize the color palette
        color1 = Color.rgb(8, 28, 37);
        color2 = Color.rgb(3, 37, 29);
        color3 = Color.rgb(58, 113, 89);
        color4 = Color.rgb(139, 192, 114);
        color5 = Color.rgb(222, 244, 208);

        //The original Game Boy palette
//        Color color1 = Color.web("#0f380f");
//        Color color2 = Color.web("#306230");
//        Color color3 = Color.web("#8bac0f");
//        Color color4 = Color.web("#9bbc0f");        
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //Initialize the Stage components - refactor into new method?
        stage.setTitle("Platformer");

        Group root = new Group();

        Scene scene = new Scene(root, canvasWidth, canvasHeight, color5);
        stage.setScene(scene);

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Initialize the game loop and get the starting time - refactor?
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        final long timeStart = System.currentTimeMillis();

        //The Game Loop - refactor? or with above init
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017), // 60 FPS
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                //1. Process input

                //2. Update / integrate / physics
                double t = (System.currentTimeMillis() - timeStart) / 1000.0;
                //update entities

                //3. Render
                // Clear the canvas
                gc.clearRect(0, 0, canvasWidth, canvasHeight); // Eventually we might want to draw the ground only once and clear only the rest of the screen.

                //temp (adjust to eg. for each entity / gameobject...)
                //draw ground
                gc.setFill(color3);
                int groundLevel = (int) (canvasHeight / 1.618);
                gc.fillRect(0, groundLevel, canvasWidth, canvasHeight - groundLevel);

                // draw player
                gc.setFill(color1);
                int playerX = canvasWidth - (playerWidth) - (int) (canvasWidth / 1.618);
                gc.fillRect(playerX, groundLevel - playerHeight, playerWidth, playerHeight);
                // end of temp
            }
        });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        stage.show();
    }
}
