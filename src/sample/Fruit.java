package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.concurrent.Callable;

import static java.lang.Math.sqrt;

public abstract class Fruit implements ISliceable {
    private final DoubleProperty xVelocity ; // pixels per second
    private final DoubleProperty yVelocity ;
    private final ReadOnlyDoubleWrapper speed ;
    private final double radius; // pixels
    private double gravity=1;
    private final Circle view;


    public Fruit(double centerX, double centerY, double radius,
                 double xVelocity, double yVelocity) {

        this.view = new Circle(centerX, centerY, radius);
        this.xVelocity = new SimpleDoubleProperty(this, "xVelocity", xVelocity);
        this.yVelocity = new SimpleDoubleProperty(this, "yVelocity", yVelocity);
        this.speed = new ReadOnlyDoubleWrapper(this, "speed");
        speed.bind(Bindings.createDoubleBinding(new Callable<Double>() {

            @Override
            public Double call() throws Exception {
                final double xVel = getXVelocity();
                final double yVel = getYVelocity();
                return sqrt(xVel * xVel + yVel * yVel);
            }
        }, this.xVelocity, this.yVelocity));
        this.radius = radius;
        view.setRadius(radius);

    }

    @Override
    public void slice( double angle) {

    }


    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getRadius() {
        return radius;
    }

    public final double getXVelocity() {
        return xVelocity.get();
    }

    public final void setXVelocity(double xVelocity) {
        this.xVelocity.set(xVelocity);
    }

    public final DoubleProperty xVelocityProperty() {
        return xVelocity;
    }

    public final double getYVelocity() {
        return yVelocity.get();
    }

    public final void setYVelocity(double yVelocity) {
        this.yVelocity.set(yVelocity);
    }

    public final DoubleProperty yVelocityProperty() {
        return yVelocity;
    }

    public final double getSpeed() {
        return speed.get();
    }

    public final ReadOnlyDoubleProperty speedProperty() {
        return speed.getReadOnlyProperty() ;
    }

    public final double getCenterX() {
        return view.getCenterX();
    }

    public final void setCenterX(double centerX) {
        view.setCenterX(centerX);
    }

    public final DoubleProperty centerXProperty() {
        return view.centerXProperty();
    }

    public final double getCenterY() {
        return view.getCenterY();
    }

    public final void setCenterY(double centerY) {
        view.setCenterY(centerY);
    }

    public final DoubleProperty centerYProperty() {
        return view.centerYProperty();
    }

    public Shape getView() {
        return view;
    }
}
