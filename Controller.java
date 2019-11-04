package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    VBox statBox;
    @FXML
    HBox populationStat;
    @FXML
    VBox evolutionBox;
    @FXML
    TabPane actionTab;
    @FXML
    Label populationLabel;
    @FXML
    Label populationValue;
    @FXML
    Canvas gameField;
    @FXML
    ScrollPane gamePane;
    @FXML
    HBox OwnerBox;
    @FXML
    HBox PopulationBox;
    @FXML
    HBox WaterBox;
    @FXML
    HBox ResourceBox;
    @FXML
    Label OwnerLabel;
    @FXML
    Label WaterLbel;
    @FXML
    Label PlantsLabel;
    @FXML
    Label AnimalsLabel;

    private GraphicsContext graphicsContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        OwnerBox.setSpacing(10);
        PopulationBox.setSpacing(10);
        WaterBox.setSpacing(10);
        ResourceBox.setSpacing(10);

        populationStat.setSpacing(20);
        gamePane.setPrefWidth(1000);
        gamePane.setMaxWidth(1000);
        gameField.setHeight(1000);
        gameField.setWidth(1000);
        actionTab.setMinWidth(300);
        gamePane.setMaxHeight(1000);
        graphicsContext = gameField.getGraphicsContext2D();
        FieldInit();
    }

    public void draw(double X, double Y, Color color) {
        int Xint = ((int) X) / 20;
        int Yint = ((int) Y / 20);

        graphicsContext.setFill(color);
        graphicsContext.fillRect((double)Xint * 20, (double) Yint * 20, 20, 20);
    }

    public void setCellStats(String owner, String population, String water, String plants, String animals) {
        OwnerLabel.setText(owner);
        populationLabel.setText(population);
        WaterLbel.setText(water);
        PlantsLabel.setText(plants);
        AnimalsLabel.setText(animals);
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0,0,1000,1000);
    }
    public void FieldInit() {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                graphicsContext.setStroke(Color.BLACK);
                graphicsContext.setLineWidth(0.3);
                graphicsContext.strokeRect(i * 20, j * 20, 20,20);
            }
        }
    }

    public void showPopulation(double X, double Y) {
        populationLabel.setText(Double.toString(X));
        populationValue.setText(Double.toString(Y));
    }
}
