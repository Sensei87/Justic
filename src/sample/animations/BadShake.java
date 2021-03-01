package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class BadShake {

    private TranslateTransition transition;

    public BadShake(Node node) {
        transition = new TranslateTransition(Duration.millis(100), node);
        transition.setFromX(0f);
        transition.setByX(10f);
        transition.setCycleCount(3);
        transition.setAutoReverse(true);
    }
    public void playAnimation() {
        transition.playFromStart();
    }
}
