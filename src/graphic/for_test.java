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
        XYChart.Series<Number, Number> interp_line = new XYChart.Series<>();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.getData().add(points);
        chart.getData().add(polynom_line);
        chart.getData().add(interp_line);
        chart.getStylesheets().add(for_test.class.getResource("chart.css").toExternalForm());

        double[] xs = {1, 2, 3, 4, 5};
        double[] ys = {0.5, 1.9, 2.8, 5, 5.5};

        for (int i = 0; i < xs.length; ++i)
            points.getData().add(new XYChart.Data<>(xs[i], ys[i]));

        int approx_n = 3;
        Approx a = new Approx(approx_n, xs, ys);
        Interp b = new Interp(xs, ys);
        for (double x = 0.0, y; x <= 5.5; x += 0.05){
            y = a.getValue(x);
            polynom_line.getData().add(new XYChart.Data<>(x, y));
            y = b.getValue(x);
            interp_line.getData().add(new XYChart.Data<>(x, y));
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
