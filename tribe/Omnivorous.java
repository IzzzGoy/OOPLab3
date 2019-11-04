package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public class Omnivorous extends Tribe {

    private final double needPlants = 0.5;
    private final double needAnimals = 0.2;

    public Omnivorous(String name, Territory start) {

        super(TypesOfTribes.OMNIVOROUS, name, start);
    }

    @Override
    protected void Eat() {
        for (Territory territory: ourTerritories) {
            territory.EatPlants(needPlants);
            territory.EatAnimals(needAnimals);
        }
    }

}
