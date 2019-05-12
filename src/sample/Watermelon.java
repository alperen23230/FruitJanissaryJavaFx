package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Watermelon extends Fruit {
    public static final int POINT = 8;

    public Watermelon(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:FruitImages/Watermelon.png")));

    }

    @Override
    public void slice(double angle) {
        Game.createHalfFruits(0,  this.getCenterX()+10, this.getCenterY()+10, angle,"file:FruitImages/halfWatermelon2.png","file:FruitImages/halfWatermelon1.png");
    }
}
