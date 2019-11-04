package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    private boolean startUp = true;
    private boolean isPause = false;
    private boolean threadWork = true;
    private ArrayList<Territory> land = new ArrayList<>(2500);
    private Player player;
    private ArrayList<Bot> bots = new ArrayList<>(3);

    {
        for (int i = 0; i < 2500; i++) {
            land.add(new Territory());
        }
        bots.add(new Bot(Color.BROWN,"Bot1",TypesOfTribes.getType(),land.get(new Random().nextInt(2500))));
        bots.add(new Bot(Color.CORAL,"Bot2",TypesOfTribes.getType(),land.get(new Random().nextInt(2500))));
        bots.add(new Bot(Color.DARKGREEN,"Bot3",TypesOfTribes.getType(),land.get(new Random().nextInt(2500))));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        ScrollPane gamePane = (ScrollPane) root.lookup("#gamePane");

        root.onKeyPressedProperty().set(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    isPause = !isPause;
                    System.out.println("Space was pressed");
                }
            }
        });

        gamePane.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (startUp) {
                    double x = mouseEvent.getX() + (1000 - gamePane.getWidth()) * gamePane.getHvalue();
                    double y = mouseEvent.getY() + (1000 - gamePane.getHeight()) * gamePane.getVvalue();

                    int Xint = ((int) x) / 20;
                    int Yint = ((int) y / 20);

                    player = new Player(Color.web("#3f51b5"), "Player", TypesOfTribes.OMNIVOROUS, land.get(Xint + Yint * 50));
                    startUp = false;

                    draw(controller);

                }
                else {
                    double x = mouseEvent.getX() + (1000 - gamePane.getWidth()) * gamePane.getHvalue();
                    double y = mouseEvent.getY() + (1000 - gamePane.getHeight()) * gamePane.getVvalue();

                    int Xint = ((int) x) / 20;
                    int Yint = ((int) y / 20);
                    Territory territory = land.get(Xint + Yint * 50);
                    controller.setCellStats(territory.getOwnerName(),Integer.toString(territory.getPopulation()),Integer.toString(territory.getWater()),Double.toString(territory.getPlants()),Double.toString(territory.getAnimals()));


                }
            }
        });

        Thread thread = new Thread(() -> {
            try {
                int count = 0;
                while (player == null) {
                    Thread.sleep(600);
                }
                while (threadWork) {
                    if (count > 1000) {
                        break;
                    }
                    player.Update(land);
                    for (Bot bot: bots) {
                        bot.Update(land);
                    }
                    draw(controller);
                    Thread.sleep(600);
                    System.out.println(count);
                    count++;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        thread.start();



        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void draw(Controller controller) {
        controller.clearCanvas();
        ArrayList<Territory> territoryArrayList = player.getTerritories();
        for (Territory territory :territoryArrayList) {
            controller.draw((land.indexOf(territory) % 50) * 20, (land.indexOf(territory) / 50) * 20, player.getColor());
        }
        for (Bot bot : bots) {
            for (Territory territory : bot.getTerritories()) {
                controller.draw((land.indexOf(territory) % 50) * 20, (land.indexOf(territory) / 50) * 20, bot.getColor());
            }
        }
        controller.FieldInit();
    }
}
