package graphic;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;

import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private FileChooser fileChooser;
    private InputStream inp;
    public Spinner spinner;
    public LineChart chart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;
    private double dragX;
    private double dragY;
    private double startX, endX;
    private boolean isEditingChart = false;
    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        configureFileChooser();

        InputStream inp = this.getClass().getResourceAsStream("input.txt");
        model = new Model(inp);

        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);
        fillChart();
        fillSpinner();
    }

    private void configureFileChooser() {
        fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
    }

    private void fillChart() {
        chart.getData().removeAll(chart.getData());

        XYChart.Series points = new XYChart.Series();
        XYChart.Series approxPolynom = new XYChart.Series();

        chart.getData().add(points);
        chart.getData().add(approxPolynom);

        double[] X = model.getX();
        double[] Y = model.getY();

        for (int i = 0; i < X.length; ++i)
            points.getData().add(new XYChart.Data<>(X[i], Y[i]));

        Arrays.sort(X);
        double minX = X[0];
        double maxX = X[X.length - 1];
        double borderWidth = (maxX - minX)/4;
        double delta = 0.05;

        xAxis.setLowerBound(minX - borderWidth);
        xAxis.setUpperBound(maxX + borderWidth);
        xAxis.setTickUnit(delta*10);

        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (double y, x = minX - borderWidth; x <= maxX + borderWidth; x += delta) {
            y =  model.getApproxValue(x);
            approxPolynom.getData().add(new XYChart.Data<>(x, y));
            if (y < minY)
                minY = y;
            else
                if (y > maxY)
                    maxY = y;
        }

        yAxis.setLowerBound(minY - borderWidth);
        yAxis.setUpperBound(maxY + borderWidth);
        yAxis.setTickUnit(delta*10);

    }

    private void fillSpinner() {
        int maxDeg = model.getMaxApproxDegree();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxDeg, maxDeg);
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    private void zoom(ScrollEvent event){
        double minX = xAxis.getLowerBound();
        double maxX = xAxis.getUpperBound();
        double minY = yAxis.getLowerBound();
        double maxY = yAxis.getUpperBound();
        double delta = 2.5;
        double direction = event.getDeltaY();
        if (direction > 0) {
            if (maxX - minX <= 0.5)
                return;

            xAxis.setLowerBound(minX + (maxX - minX) / delta / 3);
            xAxis.setUpperBound(maxX - (maxX - minX) / delta / 3);
            yAxis.setLowerBound(minY + (maxY - minY) / delta / 3);
            yAxis.setUpperBound(maxY - (maxY - minY) / delta / 3);

        } else {
            xAxis.setLowerBound(minX - (maxX - minX) / delta);
            xAxis.setUpperBound(maxX + (maxX - minX) / delta);
            yAxis.setLowerBound(minY - (maxY - minY) / delta);
            yAxis.setUpperBound(maxY + (maxY - minY) / delta);
        }
    }

    @FXML
    private void initDrag(MouseEvent event){
        dragX = xAxis.getValueForDisplay(event.getX()).doubleValue();
        dragY = yAxis.getValueForDisplay(event.getY()).doubleValue();
        System.out.println(dragX);
        System.out.println(dragY);
    }

    @FXML
    private void moveChartArea(MouseEvent event) {
//        System.out.println("Dragging");
        double deltaX = dragX - xAxis.getValueForDisplay(event.getX()).doubleValue();
        double deltaY = dragY - yAxis.getValueForDisplay(event.getY()).doubleValue();

        xAxis.setLowerBound(xAxis.getLowerBound() + deltaX);
        xAxis.setUpperBound(xAxis.getUpperBound() + deltaX);

        yAxis.setLowerBound(yAxis.getLowerBound() + deltaY);
        yAxis.setUpperBound(yAxis.getUpperBound() + deltaY);
    }

    @FXML
    private void selectInputFile(ActionEvent event) throws Exception{
        File file = this.fileChooser.showOpenDialog(null);
        if (file != null){
            InputStream inp = new FileInputStream(file);
            this.model = new Model(inp);
            fillSpinner();
            fillChart();
        }
    }

    @FXML
    private void startApprox(ActionEvent event) throws Exception{
        if (model == null)
            return;
        int approx_deg = (int)spinner.getValue();
        model.setApprox(approx_deg);
        fillChart();
    }

    @FXML
    private void switchTo(ActionEvent event){
        //...
    }
}
