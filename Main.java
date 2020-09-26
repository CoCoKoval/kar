import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private static ArrayList<Road> roads = new ArrayList();
    private AnchorPane anchorPane = new AnchorPane();
    private ArrayList<Kar> kar = new ArrayList<>();
    private Color[] colors = {Color.RED, Color.PINK, Color.GREEN, Color.YELLOW, Color.BLUE};

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Jopa");
        initShit();
        primaryStage.setScene(new Scene(anchorPane, 1000, 1000));
        primaryStage.show();
    }

    private void initShit() {
        for (Road i: roads) {
            System.out.println(i.getX1() + " " + i.getY1() + " " + i.getX2() + " " + i.getY2());
            Line line = new Line(i.getX1(),i.getY1(),i.getX2(),i.getY2());
            line.setStrokeWidth(15);
            anchorPane.getChildren().add(line);
        }
        for (int i = 0; i < 1+Math.random()*5; i++) {
            Kar temp = new Kar(Math.random(), (int) (Math.random()*roads.size()));
            kar.add(temp);
            temp.shape.setStroke(colors[(int)(Math.random()*5)]);
            anchorPane.getChildren().add(temp.shape);
        }
        AnimationTimer timer = new MyTimer();
        timer.start();
    }


    public static void main(String[] args) {
        int x = 100,y = 100;
        for (int i = 0; i < 5; i++) {
            int X = (int)(50+Math.random()*1000),Y = (int)(50+Math.random()*1000);
            roads.add(new Road(x, y, X, Y, 0.1 + Math.random()*10.0));
            x = X; y = Y;
        }

        launch(args);
    }

    private class MyTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            //doHandle();
            for (Kar j: kar) {
                if (j.progress >= 1) {
                    j.currentRoad++;
                    j.progress = 0;
                }
                try {
                    Road road = roads.get(j.currentRoad);
                    int kar1 = (road.getX2() - road.getX1()), kar2 = (road.getY2() - road.getY1());
                    double L = Math.sqrt(kar1*kar1 + kar2*kar2);
                    j.shape.setStartX(road.getX1() + j.progress * kar1);
                    j.shape.setStartY(road.getY1() + j.progress * (road.getY2() - road.getY1()));
                    j.progress += road.getMaxSpeed()/L;
                    j.shape.setEndX(road.getX1() + j.progress * (road.getX2() - road.getX1()));
                    j.shape.setEndY(road.getY1() + j.progress * (road.getY2() - road.getY1()));
                } catch (Exception e) {

                }
            }
        }

        /*
        private void doHandle() {
            opacity -= 0.01;
            lbl.opacityProperty().set(opacity);

            if (opacity <= 0) {
                stop();
                System.out.println("Animation stopped");
            }
        }*/
    }
}
