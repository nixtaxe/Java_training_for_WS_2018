package graphic;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;

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
    public Button editingBtn;
    private double dragX;
    private double dragY;
    private int nearestDataInd;
    private double startX, endX;
    private boolean isEditingChart;
    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        configureFileChooser();

        InputStream inp = this.getClass().getResourceAsStream("input.txt");
        model = new Model(inp);

        initChart();
        fillSpinner();
    }

    private void configureFileChooser() {
        fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
    }

    private void initChart(){
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);

        double delta = 0.05;
        xAxis.setTickUnit(1);
        yAxis.setTickUnit(1);

        double[] sortedX = model.getX();
        Arrays.sort(sortedX);
        double minX = sortedX[0];
        double maxX = sortedX[sortedX.length - 1];
        double borderWidth = (maxX - minX)/4;

        double[] sortedY = model.getY();
        Arrays.sort(sortedY);
        double minY = sortedY[0];
        double maxY = sortedY[sortedY.length - 1];
        double borderHeight = (maxY - minY)/4;

        addChartPoints(model.getX(), model.getY());
        fillChart(minX - borderWidth, maxX + borderWidth,
                minY - borderHeight, maxY + borderHeight);
    }

    private void addChartPoints(double[] X, double[] Y){
        chart.getData().removeAll(chart.getData());
        XYChart.Series points = new XYChart.Series();
        chart.getData().add(points);

        for (int i = 0; i < X.length; ++i)
            points.getData().add(new XYChart.Data<>(X[i], Y[i]));

    }

    private void fillChart(double leftBound, double rightBound, double lowerBound, double upperBound) {
        if (chart.getData().size() > 1)
            chart.getData().removeAll(chart.getData().get(1));
        XYChart.Series approxPolynom = new XYChart.Series();
        chart.getData().add(approxPolynom);

        double[] X = model.getX();
        double delta = 0.05;
        for (double y, x = leftBound; x <= rightBound; x += delta) {
            y = model.getApproxValue(x);
            approxPolynom.getData().add(new XYChart.Data<>(x, y));
        }

        xAxis.setLowerBound(leftBound);
        yAxis.setLowerBound(lowerBound);

        xAxis.setUpperBound(rightBound);
        yAxis.setUpperBound(upperBound);
    }

    private void fillSpinner() {
        int maxDeg = model.getMaxApproxDegree();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxDeg, maxDeg);
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    private void zoom(ScrollEvent event){
        if (isEditingChart)
            return;
        double minX = xAxis.getLowerBound();
        double maxX = xAxis.getUpperBound();
        double minY = yAxis.getLowerBound();
        double maxY = yAxis.getUpperBound();
        double delta = 2.5;
        double borderWidth = (maxX - minX) / delta;
        double borderHeight = (maxY - minY) / delta;
        double direction = event.getDeltaY();
        if (direction > 0) {
            if (maxX - minX <= 2)
                return;

            borderHeight /= 3;
            borderWidth /= 3;
            fillChart(minX + borderWidth, maxX - borderWidth,
                    minY + borderHeight, maxY - borderHeight);
        } else {
            if (maxX - minX >= 50)
                return;

            fillChart(minX - borderWidth, maxX + borderWidth,
                    minY - borderHeight, maxY + borderHeight);
        }
    }

    @FXML
    private void initDrag(MouseEvent event){
        if (!isEditingChart) {
            dragX = xAxis.getValueForDisplay(event.getSceneX()).doubleValue();
            dragY = yAxis.getValueForDisplay(event.getSceneY()).doubleValue();
            System.out.println(dragX);
            System.out.println(dragY);
        }
        else {
            nearestDataInd = 0;
            double distance = Double.MAX_VALUE;
            dragX = xAxis.getValueForDisplay(event.getX()).doubleValue();

            ObservableList<XYChart.Data> points = ((XYChart.Series)chart.getData().get(0)).getData();
            for (int i = 0; i < points.size(); ++i) {
                XYChart.Data data = points.get(i);
                double xData = (double)data.getXValue();
                double dataDistance = Math.abs(dragX - xData);
                if (dataDistance < distance) {
                    distance = dataDistance;
                    nearestDataInd = i;
                }
            }
        }
    }

    @FXML
    private void moveChartArea(MouseEvent event) {
        if (!isEditingChart) {
            double deltaX = dragX - xAxis.getValueForDisplay(event.getX()).doubleValue();
            double deltaY = dragY - yAxis.getValueForDisplay(event.getY()).doubleValue();

            fillChart(xAxis.getLowerBound() + deltaX, xAxis.getUpperBound() + deltaX,
                    yAxis.getLowerBound() + deltaY, yAxis.getUpperBound() + deltaY);
        }
        else {
            double[] Y = model.getY();
            Y[nearestDataInd] = yAxis.getValueForDisplay(event.getY()).doubleValue();
            model.setApprox(Y);
            addChartPoints(model.getX(), model.getY());
            fillChart(xAxis.getLowerBound(), xAxis.getUpperBound(),
                    yAxis.getLowerBound(), yAxis.getUpperBound());
        }
    }

    @FXML
    private void selectInputFile(ActionEvent event) throws Exception{
        File file = this.fileChooser.showOpenDialog(null);
        if (file != null){
            InputStream inp = new FileInputStream(file);
            this.model = new Model(inp);
            initChart();
            fillSpinner();
        }
    }

    @FXML
    private void startApprox(ActionEvent event) throws Exception{
        int approx_deg = (int)spinner.getValue();
        model.setApproxDeg(approx_deg);
        fillChart(xAxis.getLowerBound(), xAxis.getUpperBound(), yAxis.getLowerBound(), yAxis.getUpperBound());
    }

    @FXML
    private void switchTo(ActionEvent event) {
        if (isEditingChart) {
            isEditingChart = false;
            editingBtn.setText("Edit chart");
        }
        else {
            isEditingChart = true;
            editingBtn.setText("Move chart");
        }
    }
}
