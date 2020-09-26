import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Kar{
    protected double progress;
    protected int currentRoad;
    protected Line shape;

    public Kar(double progress, int currentRoad) {
        this.progress = progress;
        this.shape = new Line();
        this.shape.setFill(Color.RED);
        this.shape.setStrokeWidth(12);
        this.currentRoad = currentRoad;
    }

    public void setCurrentRoad(int currentRoad) {
        this.currentRoad = currentRoad;
    }

    public int getCurrentRoad() {
        return currentRoad;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
