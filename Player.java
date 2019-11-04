package sample;

import javafx.scene.paint.Color;
import sample.tribe.Herbivores;
import sample.tribe.Omnivorous;
import sample.tribe.Predators;
import sample.tribe.Tribe;

import java.util.ArrayList;

public class Player {

    private Tribe tribe;
    private Color color;
    private int evolutionPoints;

    public Player(final Color color, String name, TypesOfTribes type, Territory territory) {
        switch (type) {
            case OMNIVOROUS:
                tribe = new Omnivorous(name,territory);
                break;
            case HERBIVORES:
                tribe = new Herbivores(name,territory);
                break;
            case PREDATORS:
                tribe = new Predators(name,territory);
                break;
        }
        this.color = color;
        evolutionPoints = 0;
        territory.Colonization(tribe);
    }

    protected void Update(ArrayList<Territory> territories) {
        tribe.Update(territories);
    }

    public int getEvolutionPoints() {
        return evolutionPoints;
    }

    public long getPopulation() {
        long population = 0;
        for (Territory territory: tribe.getOurTerritories()) {
            population += territory.getPopulation();
        }
        return population;
    }

    public void AddEvolutionPoints() {
        evolutionPoints++;
    }

    public void AddAttack() {
        if (evolutionPoints > 0) {
            tribe.addAttack();
            evolutionPoints--;
        }
    }

    public String getName() {
        return tribe.getName();
    }

    public void AddFoodProduction() {
        if (evolutionPoints > 0) {
            tribe.addFoodProduction();
            evolutionPoints--;
        }
    }

    public void AddBorn() {
        if (evolutionPoints > 0) {
            tribe.addBorn();
            evolutionPoints--;
        }
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Territory> getTerritories() {
        return tribe.getOurTerritories();
    }

    public Tribe getTribe() {
        return tribe;
    }
}
