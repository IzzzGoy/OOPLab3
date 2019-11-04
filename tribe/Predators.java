package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public class Predators extends Tribe {

    private final double needAnimals = 0.4;

    public Predators(String name, Territory terr) {
        super(TypesOfTribes.PREDATORS,name, terr);
    }

    @Override
    protected void Eat() {
        for (Territory territory: ourTerritories) {
            territory.EatAnimals(needAnimals);
        }
    }

}
