package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;

public class Game {
    private ObservableList<ISliceable> fruits = FXCollections.observableArrayList();
    private static ObservableList<HalfFruit> halfFruits = FXCollections.observableArrayList();

    public double delay = 50.0;
    public double angle;
    public static int duration = 0;
    public static int score=0;
    public static int fail=0;
    private  final double RADIUS = 25 ;
    private  final double SPEED = 250 ;

    private static String loggedInUsername;
    private boolean isStop = false;

    private Timer timer;
    private Stage stage1;
    private TimerTask timerTask;
    private AnimationTimer animationTimer;
    private AnimationTimer worldTimer;
    private static RotateTransition rotateTransitionhalFruit1;
    private static RotateTransition rotateTransitionhalFruit2;

    private final FrameStats frameStats = new FrameStats() ;

    //This method for game start
    public void gameStart(){
        //For game duration
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                duration++;
            }
        };
        timer.schedule(timerTask,0,1000);

        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutX(10);
        pauseButton.setLayoutY(550);

        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(70);
        exitButton.setLayoutY(550);

        Pane ballContainer = new Pane();
        ballContainer.getChildren().addAll(pauseButton, exitButton);

        //This Lambda expression for pause the game scene
        pauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            if(isStop == false){
                isStop = true;
                stopGame();
                pauseButton.setText("Resume");
            } else {
                isStop = false;
                timerTask.run();
                animationTimer.start();
                worldTimer.start();
                pauseButton.setText("Pause");
            }
        });

        //This Lambda expression for exit the game
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameExit();
        });


        BackgroundImage backgroundImage= new BackgroundImage(new Image("file:FruitImages/background.jpg",800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        ballContainer.setBackground(new Background(backgroundImage));

        //this is the main game loop
         animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //creates fruits or bombs every specific time
                if(frameStats.getFrameCount()%100000000 == delay  ) {

                    createFruits( RADIUS, SPEED, 150+Math.random()*500, 600);

                    //making game harder
                    if(score<60){delay+=50;}
                    else if(score>=60 && score<120){delay+=40;}
                    else if(score>=120 && score<220){delay +=30;}
                    else { delay += 20;}
                }
                //Removing halfFruits
                for (int i = 0; i < fruits.size(); i++) {
                    if( fruits.get(i).getCenterY()-fruits.get(i).getRadius()>ballContainer.getHeight()+1 || fruits.get(i).getCenterX()-fruits.get(i).getRadius()>ballContainer.getWidth()+1 || (fruits.get(i).getCenterX()+fruits.get(i).getRadius())<0) {
                        if(fruits.get(i) instanceof Fruit){
                            fail++;}
                        fruits.remove(fruits.get(i));
                    }}
                if(fail>2){
                    gameOver();
                }
            }

        };
        animationTimer.start();

        //this is our mouse event that checks if sliceable object is sliced. We used only entry point to slice a fruit.
        ballContainer.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event ->  {
                        for (int i = 0; i < fruits.size(); i++) {
                            if((Math.abs(fruits.get(i).getCenterX() - event.getX()) <= fruits.get(i).getRadius()) && (Math.abs(fruits.get(i).getCenterY() - event.getY()) <= fruits.get(i).getRadius())) {

                                if(event.getX()>=fruits.get(i).getCenterX() && event.getY()<=fruits.get(i).getCenterY()) { angle = Math.abs(Math.asin((event.getX()-fruits.get(i).getCenterX())/fruits.get(i).getRadius()))*57.3; }
                                else if(event.getX()>=fruits.get(i).getCenterX() && event.getY()>=fruits.get(i).getCenterY() ) {angle= 90+Math.abs(Math.asin((Math.abs(event.getY()-fruits.get(i).getCenterY()))/fruits.get(i).getRadius()))*57.3;}
                                else if(event.getX()<=fruits.get(i).getCenterX() && event.getY()>=fruits.get(i).getCenterY() ) {angle= 180+Math.abs(Math.asin((Math.abs(event.getX()-fruits.get(i).getCenterX()))/fruits.get(i).getRadius()))*57.3;}
                                else { angle= 270+Math.abs(Math.asin((event.getY()-fruits.get(i).getCenterY())/fruits.get(i).getRadius())*57.3); }

                                //increasing score by fruit's scores
                                if(fruits.get(i) instanceof Apple){
                                    fruits.get(i).slice(angle);
                                    score+=((Apple) fruits.get(i)).POINT;
                                    createSplashEffect(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), ballContainer, "file:FruitImages/red-splash.png" );
                                }
                                else if(fruits.get(i) instanceof Orange){
                                    fruits.get(i).slice(angle);
                                    score+=((Orange) fruits.get(i)).POINT;
                                    createSplashEffect(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), ballContainer,"file:FruitImages/orange-splash.png" );
                                }
                                else if(fruits.get(i) instanceof Lemon){
                                    fruits.get(i).slice(angle);
                                    score+=((Lemon) fruits.get(i)).POINT;
                                    createSplashEffect(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), ballContainer ,"file:FruitImages/yellow-splash.png");
                                }
                                else if(fruits.get(i) instanceof Watermelon){
                                    //Watermelon
                                    fruits.get(i).slice(angle);
                                    score+=((Watermelon) fruits.get(i)).POINT;
                                    createSplashEffect(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(), ballContainer,"file:FruitImages/green-splash.png" );
                                } else {
                                   gameOverBomb(fruits.get(i).getCenterX(), fruits.get(i).getCenterY(),ballContainer);
                                }
                                fruits.remove(fruits.get(i));
                            }}
                });


        //if a fruit or bomb is created or removed, it prints or makes it remove on screen
        fruits.addListener((ListChangeListener.Change<? extends ISliceable> change) -> {
                while (change.next()) {
                    for (ISliceable b : change.getAddedSubList()) {
                        ballContainer.getChildren().add(b.getView());
                    }
                    for (ISliceable b : change.getRemoved()) {
                        ballContainer.getChildren().remove(b.getView());
                    }
                }
        });

        //if a halfFruit is created or removed, it prints or makes it removed on screen
        halfFruits.addListener((ListChangeListener.Change<? extends HalfFruit> change) ->  {
                while (change.next()) {
                    for (HalfFruit b : change.getAddedSubList()) {
                        ballContainer.getChildren().add(b.getView());
                    }
                    for (HalfFruit b : change.getRemoved()) {
                        ballContainer.getChildren().remove(b.getView());
                    }
                }
        });

        //creates first fruit
        createFruits( RADIUS, SPEED, 400, 600);

        BorderPane root = new BorderPane();
        final Label stats = new Label();
        stats.textProperty().bind(frameStats.textProperty());

        root.setCenter(ballContainer);
        root.setBottom(stats);

        final Scene scene = new Scene(root, 800, 600);
        stage1 = new Stage();
        stage1.setTitle("Fruit Janissary");
        stage1.setResizable(false);
        stage1.setScene(scene);
        stage1.show();
        startAnimation();
    }
    //This method for logged In Username info
    public static void initData(String username){
        loggedInUsername = username;
    }

    public void gameOverBomb(double initialX, double initialY, Pane pane){
        Timeline showSlash = new Timeline(
                new KeyFrame(Duration.ZERO, e ->
                {
                    createBombEffect(initialX,initialY,pane);
                    stopGame();
                }
                ),
                new KeyFrame(Duration.millis(1000), e->
                {
                    gameOver();
                }
                )
        );
        showSlash.setCycleCount(1);
        showSlash.play();
    }

    public void gameOver(){
        stopGame();
        Parent p = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGame.fxml"));
        try {
            p = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(p);
        EndGameController controller = loader.getController();
        controller.initData(score, duration,loggedInUsername);

        stage1.setScene(scene);

        fail = 0;
        score = 0;
        duration = 0;
        fruits.removeAll();
        halfFruits.removeAll();
    }
    public void gameExit(){
        stopGame();
        Parent p = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        try {
            p = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(p);
        stage1.setScene(scene);
        fail = 0;
        score = 0;
        duration = 0;
        fruits.removeAll();
        halfFruits.removeAll();
    }

    public void stopGame(){
        timerTask.cancel();
        animationTimer.stop();
        worldTimer.stop();
    }

    public void createBombEffect(double initialX, double initialY, Pane pane){
        // Define the slash image and create the object for it
        ImageView bombEffect = new ImageView("file:FruitImages/bombEffect.gif");

        bombEffect.setFitWidth(150);
        bombEffect.setFitHeight(150);

        // Firstly make the slash invisible
        bombEffect.setVisible(false);

        // Tie the trace with our full watermelon layout x, y
        bombEffect.setLayoutX(initialX);
        bombEffect.setLayoutY(initialY);

        // Add the trace to the pane
        pane.getChildren().add(bombEffect);

        // Make animation to show the trace just for 400 millisecond
        Timeline showEffect = new Timeline(
                new KeyFrame(Duration.ZERO, e ->
                {
                    bombEffect.setVisible(true);
                }
                ),
                new KeyFrame(Duration.millis(1000), e->
                {
                    bombEffect.setVisible(false);
                }
                )
        );

        showEffect.setCycleCount(1);
        showEffect.play();
    }

    public void createSplashEffect(double initalX, double initalY, Pane pane, String path)
    {
        ImageView splash = new ImageView(path);

        splash.setFitWidth(50);
        splash.setFitHeight(50);

        splash.setVisible(false);

        splash.setLayoutX(initalX);
        splash.setLayoutY(initalY);

        pane.getChildren().add(splash);

        Timeline showSplash = new Timeline(
                new KeyFrame(Duration.ZERO, e ->
                {
                    splash.setVisible(true);
                }
                ),
                new KeyFrame(Duration.millis(400), e->
                {
                    splash.setVisible(false);
                }
                )
        );

        showSplash.setCycleCount(1);
        showSplash.play();
    }

    //updates screen
    private void startAnimation() {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        worldTimer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    long elapsedTime = timestamp - lastUpdateTime.get();
                    updateWorld(elapsedTime);
                    frameStats.addFrame(elapsedTime);
                }
                lastUpdateTime.set(timestamp);
            }
        };
        worldTimer.start();
    }

    //this is the part where fruits and halfFruits move (translate) by gravity
    private void updateWorld(long elapsedTime) {
        double elapsedSeconds = elapsedTime / 1_000_000_000.0;
        double difficulty = 4.6;

        for (HalfFruit b : halfFruits) {
            b.setCenterX(b.getCenterX() + elapsedSeconds * b.getXVelocity());
            b.setCenterY((b.getCenterY() + elapsedSeconds * b.getYVelocity()) - difficulty * b.getGravity());
            b.setGravity(b.getGravity() - 0.05);
        }

        for (ISliceable b : fruits) {
            b.setCenterX(b.getCenterX() + elapsedSeconds * b.getXVelocity());
            b.setCenterY((b.getCenterY() + elapsedSeconds * b.getYVelocity()) - difficulty * b.getGravity());
            b.setGravity(b.getGravity() - 0.02);
        }
    }

    //this is the method to create fruits
    private void createFruits( double mradius,double mspeed, double initialX, double initialY) {

        final Random rng = new Random();
        double angle = 2 * PI * rng.nextDouble();

        int random = rng.nextInt(5);

        switch (random){
            case 0: //apple
                fruits.add(new Apple(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 1: //lemon
                fruits.add(new Lemon(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 2: //orange
                fruits.add(new Orange(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 3: //watermelon
                fruits.add(new Watermelon(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            case 4: //bomb
                fruits.add(new Bomb(initialX, initialY, mradius, mspeed*cos(angle), -Math.abs(mspeed*sin(angle))));
                break;
            default: break;

        }
    }

    //this is the method for create half fruits
    public static void createHalfFruits( double mspeed, double initialX, double initialY, double angle1, String pathHalf1, String pathHalf2) {
        final Random rng = new Random();

        final double angle = 2 * PI * rng.nextDouble();

        HalfFruit halfFruit1 = new HalfFruit( mspeed*cos(angle), -Math.abs(mspeed*sin(angle)) );
        halfFruit1.getView().setImage(new Image(pathHalf1));
        halfFruit1.getView().setFitHeight(50);
        halfFruit1.getView().setFitWidth(25);

        HalfFruit halfFruit2 = new HalfFruit( mspeed*cos(angle), -Math.abs(mspeed*sin(angle)));
        halfFruit2.getView().setImage(new Image(pathHalf2));
        halfFruit2.getView().setFitHeight(50);
        halfFruit2.getView().setFitWidth(25);

        rotateTransitionhalFruit1 = new RotateTransition(Duration.seconds(3),halfFruit1.getView());
        rotateTransitionhalFruit1.setFromAngle(angle1);
        rotateTransitionhalFruit1.setToAngle(-360);
        rotateTransitionhalFruit1.setAutoReverse(true);
        rotateTransitionhalFruit1.play();

        rotateTransitionhalFruit2 = new RotateTransition(Duration.seconds(3),halfFruit2.getView());
        rotateTransitionhalFruit2.setFromAngle(angle1);
        rotateTransitionhalFruit2.setToAngle(360);
        rotateTransitionhalFruit2.setAutoReverse(true);
        rotateTransitionhalFruit2.play();


        if(angle1<90){
            halfFruit1.getView().setX(initialX-25);
            halfFruit1.getView().setY(initialY-25);
            halfFruit2.getView().setX(initialX);
            halfFruit2.getView().setY(initialY);
            halfFruit1.getView().setRotate(angle1);
            halfFruit2.getView().setRotate(angle1);
        }
        else if(angle1>90 && angle1<180){
            halfFruit1.getView().setX(initialX);
            halfFruit1.getView().setY(initialY);
            halfFruit2.getView().setX(initialX+25);
            halfFruit2.getView().setY(initialY-25);
            halfFruit1.getView().setRotate(angle1-180);
            halfFruit2.getView().setRotate(angle1-180);
        }
        else if(angle1>180 && angle1<270){
            halfFruit1.getView().setX(initialX);
            halfFruit1.getView().setY(initialY);
            halfFruit2.getView().setX(initialX+25);
            halfFruit2.getView().setY(initialY+25);
            halfFruit1.getView().setRotate(angle1-180);
            halfFruit2.getView().setRotate(angle1-180);
        }
        else {
            halfFruit1.getView().setX(initialX-25);
            halfFruit1.getView().setY(initialY+25);
            halfFruit2.getView().setX(initialX);
            halfFruit2.getView().setY(initialY);
            halfFruit1.getView().setRotate(angle1);
            halfFruit2.getView().setRotate(angle1);
        }

        halfFruits.add(halfFruit1);
        halfFruits.add(halfFruit2);
    }


    private static class FrameStats {
        private long frameCount ;
        private double meanFrameInterval ;
        private final ReadOnlyStringWrapper text = new ReadOnlyStringWrapper(this, "text", "Frame count: 0 Average frame interval: N/A");

        public long getFrameCount() {
            return frameCount;
        }

        public void addFrame(long frameDurationNanos) {
            meanFrameInterval = (meanFrameInterval * frameCount + frameDurationNanos / 1_000_000.0) / (frameCount + 1) ;
            frameCount++ ;
            text.set(toString());
        }

        public ReadOnlyStringProperty textProperty() {
            return text.getReadOnlyProperty() ;
        }

        @Override
        public String toString() {
            return String.format("SCORE: %d, MISS: %d, DURATION: %d Second",score, fail, duration);
        }
    }
}
