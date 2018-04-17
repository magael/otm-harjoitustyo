package mj.platformer.gameobject;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

public abstract class GameObject {

//    private Node sprite;
//    public GameObject(Node sprite, int x, int y) {
//        this.sprite = sprite;
//        this.sprite.setTranslateX(x);
//        this.sprite.setTranslateY(y);
//    }
    private Shape sprite;

    public GameObject(Shape sprite, int x, int y) {
        this.sprite = sprite;
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

//    public Node getSprite() {
//        return sprite;
//    }
    public Shape getSprite() {
        return sprite;
    }

//    public void setSprite(Node sprite) {
//        this.sprite = sprite;
//    }
    public void setSprite(Shape sprite) {
        this.sprite = sprite;
    }
    
    // get x, y
    // set x, y
}
