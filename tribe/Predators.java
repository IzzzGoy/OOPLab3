package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public class Predators extends Tribe {

    private final double needAnimals = 1.9;

    public Predators(String name, Territory terr) {
        super(TypesOfTribes.PREDATORS,name, terr);
        food_production = 13.0;
        attack = 25.0;
    }

    @Override
    protected void Eat() {
        for (Territory territory: ourTerritories) {
            territory.EatAnimals(needAnimals);
        }
    }

}
