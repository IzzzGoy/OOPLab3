package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public class Herbivores extends Tribe{

    private final double needPlants = 0.8;

    public Herbivores(String name, Territory start) {
        super(TypesOfTribes.HERBIVORES, name, start);
    }

    @Override
    protected void Eat() {
        for (Territory territory: ourTerritories) {
            territory.EatPlants(needPlants);
        }
    }

}
