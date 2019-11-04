package sample;

import javafx.scene.paint.Color;

public class Bot extends Player{

    private BotsType botsType;

    public Bot(final Color color, String name, TypesOfTribes type, Territory territory) {
        super(color,name,type,territory);
        botsType = BotsType.getType();
    }
}
