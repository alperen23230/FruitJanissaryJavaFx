package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

import java.util.concurrent.Callable;

import static java.lang.Math.sqrt;

public class HalfFruit {
    private final DoubleProperty xVelocity ; // pixels per second
    private final DoubleProperty yVelocity ;
    private final ReadOnlyDoubleWrapper speed ;
    private final ImageView view;
    private final double angle1=0;
    private final double radius=0;
    private double gravity=1;

    public HalfFruit(double centerX, double centerY,
                     double xVelocity, double yVelocity, double angle1) {

        this.view =  new ImageView();
          /*  ImageView half1 = new ImageView();
            half1.setImage(new Image("file:images/applehalf1.png"));
            half1.setRotate(angle1);
            ImageView half2 = new ImageView();
            half2.setImage(new Image("file:images/applehalf2.png"));
            half2.setRotate(-angle1);*/
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
    }

    public double getAngle1() {
        return angle1;
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
        return view.getX();
    }

    public final void setCenterX(double centerX) {
        view.setX(centerX);
    }

    public final double getCenterY() {
        return view.getY();
    }

    public final void setCenterY(double centerY) {
        view.setY(centerY);
    }


    public ImageView getView() {
        return view;
    }
}
