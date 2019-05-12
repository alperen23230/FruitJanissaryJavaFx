package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Lemon extends Fruit {

    public static final int POINT = 15;
    public Lemon(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:FruitImages/Lemon.png")));

    }

    @Override
    public void slice(double angle) {
        Game.createHalfFruits( this.getCenterX()+10, this.getCenterY()+10, angle,"file:FruitImages/halfLemon1.png","file:FruitImages/halfLemon2.png");

    }
}
