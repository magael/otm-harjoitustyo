
import javafx.scene.Node;

public class Platform extends GameObject {

//    Point2D motion;
    private int speed;

    public Platform(Node sprite, int x, int y) {
        super(sprite, x, y);
//        motion = new Point2D(0, 0);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void move() {
        Node sprite = this.getSprite();
        sprite.setTranslateX(sprite.getTranslateX() - speed);
    }
}
