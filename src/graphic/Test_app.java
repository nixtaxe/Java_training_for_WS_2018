package graphic;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import static java.lang.Double.isNaN;

public class Test_app extends Application{
    @FXML
    private FileChooser fileChooser;
    @FXML
    public Spinner spinner;
    @FXML
    public LineChart chart;
    @FXML
    private boolean isEditingChart = false;
    @FXML
    private Model model;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("test_ui.fxml"));
        initModel();
        initSpinner(root);
        initChart(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initModel(){
        InputStream inp = this.getClass().getResourceAsStream("input.txt");
        this.model = new Model(inp);
    }

    private void initSpinner(Parent root) {
        this.spinner = (Spinner) root.lookup("#spinner");
        fillSpinner();
    }

    private void initChart(Parent root){
        this.chart = (LineChart) root.lookup("#chart");
        fillChart();
    }

    private void fillSpinner(){
        int maxDeg = this.model.getMaxApproxDegree();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxDeg, maxDeg);
        this.spinner.setValueFactory(valueFactory);
    }

    private void fillChart(){
        this.chart.getData().removeAll(chart.getData());

        XYChart.Series points = new XYChart.Series();
        XYChart.Series approx_polynom = new XYChart.Series();

        this.chart.getData().add(points);
        this.chart.getData().add(approx_polynom);

        double[] X = model.getX();
        double[] Y = model.getY();

        for (int i = 0; i < X.length; ++i)
            points.getData().add(new XYChart.Data<>(X[i], Y[i]));

        for (double x = 0.0; x <= 10; x += 0.05)
            approx_polynom.getData().add(new XYChart.Data<>(x, model.getApproxValue(x)));
    }

    private void configureFileChooser() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
    }

    @FXML
    private void selectInputFile(ActionEvent event) throws Exception{
        configureFileChooser();
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
            initModel();
        int approx_deg = (int)this.spinner.getValue();
        this.model.setApprox(approx_deg);
        fillChart();
    }

    @FXML
    private void switchTo(ActionEvent event){

    }
}