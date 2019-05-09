package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Apple extends Fruit {

    public Apple(double centerX, double centerY, double mradius, double xVelocity, double yVelocity) {

        super(centerX, centerY, mradius, xVelocity, yVelocity);
        getView().setFill(new ImagePattern(new Image("file:FruitImages/apple.png")));
    }

    @Override
    public void slice(double angle) {
        Game.createSemiBalls(0,  this.getCenterX()+10, this.getCenterY()+10, angle,"file:FruitImages/halfApple2.png","file:FruitImages/halfApple1.png");
    }
}
