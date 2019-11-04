package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public class Omnivorous extends Tribe {

    private final double needPlants = 2.1;
    private final double needAnimals = 1.5;

    public Omnivorous(String name, Territory start) {

        super(TypesOfTribes.OMNIVOROUS, name, start);
        food_production = 25.8;
        attack = 18.2;
    }

    @Override
    protected void Eat() {
        for (Territory territory: ourTerritories) {
            territory.EatPlants(needPlants);
            territory.EatAnimals(needAnimals);
        }
    }

}
