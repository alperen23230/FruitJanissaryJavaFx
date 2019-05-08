package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public interface ISliceable {
    DoubleProperty xVelocity = null; // pixels per second
    DoubleProperty yVelocity = null;
    ReadOnlyDoubleWrapper speed = null;
    double radius = 0; // pixels
    double gravity=1;
    Circle view = null;

    void slice(double angle);

    double getGravity();

    void setGravity(double gravity);

    double getRadius();

    double getXVelocity();

    void setXVelocity(double xVelocity);

    DoubleProperty xVelocityProperty() ;

    double getYVelocity();

    void setYVelocity(double yVelocity);

    DoubleProperty yVelocityProperty();

    double getSpeed();

    ReadOnlyDoubleProperty speedProperty();

    double getCenterX();

    void setCenterX(double centerX) ;

    DoubleProperty centerXProperty();

    double getCenterY();

    void setCenterY(double centerY);

    DoubleProperty centerYProperty();

    Shape getView();


}
