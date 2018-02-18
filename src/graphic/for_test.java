package graphic;
import com.company.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class for_test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series<Number, Number> points = new XYChart.Series<>();
        XYChart.Series<Number, Number> polynom_line = new XYChart.Series<>();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.getData().add(points);
        chart.getData().add(polynom_line);
        chart.getStylesheets().add(for_test.class.getResource("chart.css").toExternalForm());

        double[] xs = {1, 2, 3};
        double[] ys = {1.5, 1.9, 2.8};

        for (int i = 0; i < xs.length; ++i)
            points.getData().add(new XYChart.Data<>(xs[i], ys[i]));

        Approx a = new Approx(1, xs, ys);
        for (double x = 0.0, y; x <= 5.0; x += 0.05){
            y = a.getValue(x);
            polynom_line.getData().add(new XYChart.Data<>(x, y));
        }

        Scene scene = new Scene(chart);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    public static void main(String[] args){
        launch(for_test.class, args);
    }

}
