package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Orange extends Fruit {
    public static final int POINT = 12;
    public Orange(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:FruitImages/orange.png")));

    }

    @Override
    public void slice(double angle) {
        Game.createHalfFruits(0,  this.getCenterX()+10, this.getCenterY()+10, angle,"file:FruitImages/halfOrange1.png","file:FruitImages/halfOrange2.png");

    }
}
