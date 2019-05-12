package sample;

import javafx.beans.property.DoubleProperty;

import javafx.scene.shape.Shape;

public interface ISliceable {
    DoubleProperty xVelocity = null;
    DoubleProperty yVelocity = null;

    void slice(double angle);

    double getGravity();

    void setGravity(double gravity);

    double getRadius();

    double getXVelocity();

    double getYVelocity();

    double getCenterX();

    void setCenterX(double centerX) ;


    double getCenterY();

    void setCenterY(double centerY);


    Shape getView();


}
