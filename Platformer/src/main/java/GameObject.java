
import javafx.scene.Node;

public abstract class GameObject {

    private Node sprite;

    public GameObject(Node sprite, int x, int y) {
        this.sprite = sprite;
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

    public Node getSprite() {
        return sprite;
    }
}
