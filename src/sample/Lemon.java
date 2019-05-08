package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Lemon extends Fruit {
    public Lemon(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:FruitImages/Lemon.png")));

    }

    @Override
    public void slice(double angle) {
        MainGameSceneController.createSemiBalls(0,  this.getCenterX()+10, this.getCenterY()+10, angle,"file:FruitImages/halfLemon1.png","file:FruitImages/halfLemon2.png");

    }
}
