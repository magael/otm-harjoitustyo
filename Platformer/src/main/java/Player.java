
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class Player extends GameObject {

    Point2D motion;

    public Player(Node sprite, int x, int y) {
        super(sprite, x, y);
        motion = new Point2D(0, 0);
    }
    
    public void jump() {
        
    }
}
