package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    private boolean startUp = true;
    private boolean isPause = false;
    private boolean threadWork = true;
    private boolean game = true;
    private int Xint;
    private int Yint;
    private ArrayList<Territory> land = new ArrayList<>(2500);
    private Player player;
    private ArrayList<Bot> bots = new ArrayList<>(3);
    private Controller controller;
    private Timer timer;
    private AnimationTimer animationTimer;
    private int frameCount = 0;
    private int playerEVOChanse = 0;
    private int bot1EVOChanse = 0;
    private int bot2EVOChanse = 0;
    private int bot3EVOChanse = 0;

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
        controller = loader.getController();
        ScrollPane gamePane = (ScrollPane) root.lookup("#gamePane");
        Button addAttack = (Button) root.lookup("#AddAttackButton");
        addAttack.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.AddAttack();
            }
        });
        Button addFoodProdution = (Button) root.lookup("#AddFoodProdutionButton");
        addFoodProdution.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.AddFoodProduction();
            }
        });
        Button addBorning = (Button) root.lookup("#AddBorningButton");
        addBorning.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.AddBorn();
            }
        });

        root.onKeyPressedProperty().set(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    isPause = !isPause;
                    System.out.println("Space was pressed");
                }
            }
        });

        gamePane.onKeyPressedProperty().set(new EventHandler<KeyEvent>() {
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

                    Xint = ((int) x) / 20;
                    Yint = ((int) y / 20);

                    player = new Player(Color.web("#3f51b5"), "Player", TypesOfTribes.OMNIVOROUS, land.get(Xint + Yint * 50));
                    startUp = false;

                    draw(controller);

                }
                else {
                    double x = mouseEvent.getX() + (1000 - gamePane.getWidth()) * gamePane.getHvalue();
                    double y = mouseEvent.getY() + (1000 - gamePane.getHeight()) * gamePane.getVvalue();

                    Xint = ((int) x) / 20;
                    Yint = ((int) y / 20);
                    Territory territory = land.get(Xint + Yint * 50);
                    controller.setCellStats(territory.getOwnerName(),Integer.toString(territory.getPopulation()),Integer.toString(territory.getWater()),Double.toString(territory.getPlants()),Double.toString(territory.getAnimals()));
                }
            }
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                int defitedCount = 0;

                frameCount++;
                if (frameCount == 30) {
                    frameCount = 0;
                }
                changeInfo();
                if (player != null) {
                    if (player.getPopulation() == 0) defitedCount++;
                    for (Bot bot: bots) {
                        if (bot.getPopulation() == 0) defitedCount++;
                    }
                    if (defitedCount == 3) {
                        game = false;
                        controller.clearCanvas();
                        animationTimer.stop();
                    }
                    else {
                        draw(controller);
                        controller.EVOPintsValueRefresh(Integer.toString(player.getEvolutionPoints()));
                        controller.ShowPlayerStat(Long.toString(player.getPopulation()), Double.toString(player.getTribe().getAttack()), Double.toString(player.getTribe().getFood_production()), Double.toString(player.getTribe().getBorn()));
                    }
                }
                else {
                    controller.clearCanvas();
                    controller.FieldInit();
                }
            }
        };
        animationTimer.start();

        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    while (player == null) {
                        Thread.sleep(600);
                    }
                    while (game) {
                        if (!isPause) {
                            for (Territory territory: land) {
                                territory.Update();
                            }
                            player.Update(land);
                            for (Bot bot : bots) {
                                bot.Update(land);
                            }
                            EVOChanseRoll();
                        }
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        },0,500);




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
        for (int i = 0; i < land.size(); i++) {
            if (land.get(i).getOwner() != null) {
                if (land.get(i).getOwnerName().equals(player.getName())) {
                    controller.draw((i % 50) * 20, (i / 50) * 20, player.getColor());
                }
                else if (land.get(i).getOwnerName().equals(bots.get(0).getName())) {
                    controller.draw((i % 50) * 20, (i / 50) * 20, bots.get(0).getColor());
                }
                else if (land.get(i).getOwnerName().equals(bots.get(1).getName())) {
                    controller.draw((i % 50) * 20, (i / 50) * 20, bots.get(1).getColor());
                }
                else if (land.get(i).getOwnerName().equals(bots.get(2).getName())) {
                    controller.draw((i % 50) * 20, (i / 50) * 20, bots.get(2).getColor());
                }
            }

        }
        controller.FieldInit();
    }

    private void changeInfo() {
        controller.setCellStats(land.get(Xint + Yint * 50).getOwnerName(),Integer.toString(land.get(Xint + Yint * 50).getPopulation()),Integer.toString(land.get(Xint + Yint * 50).getWater()),Double.toString(land.get(Xint + Yint * 50).getPlants()),Double.toString(land.get(Xint + Yint * 50).getAnimals()));
    }

    private void EVOChanseRoll() {
        playerEVOChanse += new Random().nextInt(50);
        bot1EVOChanse += new Random().nextInt(50);
        bot2EVOChanse += new Random().nextInt(50);
        bot3EVOChanse += new Random().nextInt(50);
        if (playerEVOChanse >= 100) {
            player.AddEvolutionPoints();
            playerEVOChanse = 0;
        }
        if (bot1EVOChanse >= 100) {
            bots.get(0).AddEvolutionPoints();
            bot1EVOChanse = 0;
        }
        if (bot2EVOChanse >= 100) {
            bots.get(1).AddEvolutionPoints();
            bot2EVOChanse = 0;
        }
        if (bot3EVOChanse >= 100) {
            bots.get(2).AddEvolutionPoints();
            bot3EVOChanse = 0;
        }
    }
}
