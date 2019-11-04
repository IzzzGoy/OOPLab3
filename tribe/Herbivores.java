package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public class Herbivores extends Tribe{

    private final double needPlants = 2.5;

    public Herbivores(String name, Territory start) {
        super(TypesOfTribes.HERBIVORES, name, start);
        food_production = 51.3;
        attack = 10.8;
    }

    @Override
    protected void Eat() {
        for (Territory territory: ourTerritories) {
            territory.EatPlants(needPlants);
        }
    }

}
