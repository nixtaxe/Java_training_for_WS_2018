package graphic;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    private double startX, endX;
    private boolean isEditingChart = false;
    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        configureFileChooser();

        InputStream inp = this.getClass().getResourceAsStream("input.txt");
        model = new Model(inp);

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

        for (double x = minX - borderWidth; x <= maxX + borderWidth; x += delta)
            approxPolynom.getData().add(new XYChart.Data<>(x, model.getApproxValue(x)));
    }

    private void fillSpinner() {
        int maxDeg = model.getMaxApproxDegree();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxDeg, maxDeg);
        spinner.setValueFactory(valueFactory);
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
