package graphic;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import static java.lang.Double.isNaN;

public class Controller {
//    private final FileChooser openFileChooser;
    private InputStream inp;
    private int n;
    private int m;
    private boolean isEditingChart = false;
    private Model model;

//    {
//        openFileChooser = new FileChooser();
//        openFileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
//        openFileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("txt"));
//    }

    @FXML
    private void selectInputFile(ActionEvent event){

    }

    @FXML
    private void changeApproxDegree(ActionEvent event){
        double x = Integer.parseInt(((Spinner)event.getSource()).getPromptText());
        if (isNaN(x) || n >= m)
            //Заглушка. Должно вызываться исключение
            n = 1;
        else
            n = (int)x;
    }

    @FXML
    private void startApprox(ActionEvent event){
        System.out.print("Approx works");
    }

    @FXML
    private void switchTo(ActionEvent event){

    }
}
