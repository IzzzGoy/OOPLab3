package sample;

import javafx.scene.paint.Color;
import sample.tribe.Tribe;

import java.util.Random;

public class Territory {
    private double fertility;
    private double recourse;
    private double plants;
    private int water;
    private double animals;
    private int population;
    private double exhaustion = 1.2;

    private final double animalsBorningCoefficient = 0.8;
    private final double animalsNeedPlants = 0.9;
    private final double plantGrowing = 5.5;

    private Tribe owner = null;

    public Territory() {
        population = 0;
        fertility = Math.random() * 5;
        recourse = Math.random() * 200;
        plants = Math.random() * 5000;
        water = new Random().nextInt(2000) + 150;
        animals = Math.random() * 1000;
    }

    public int getPopulation() {
        return population;
    }

    public void changePopulation(int population) {
        this.population += population;
        if (this.population > water) {
            this.population = water;
        }
    }

    public void Update() {
        System.out.println(exhaustion);
        if (owner == null) {
            plants += plants * plantGrowing * 1.2 * fertility;
            plants -= animals * animalsNeedPlants;
            animals += animals * animalsBorningCoefficient * 1.2;
            plants /= exhaustion;
            animals /= exhaustion;
            exhaustion += 0.05;
            return;
        }
        if (animals == 0 || plants == 0) {
            owner.Exodus(this);
            population = 0;
            owner = null;
            return;
        }
        switch (owner.getType()) {
            case OMNIVOROUS:
                plants += plants * plantGrowing * owner.getFood_production() * fertility;
                plants -= animals * animalsNeedPlants;
                animals += animals * animalsBorningCoefficient * owner.getFood_production();
                plants /= exhaustion;
                animals /= exhaustion;
                exhaustion += 0.05;
                break;
            case HERBIVORES:
                plants += plants * plantGrowing * owner.getFood_production() * fertility;
                plants -= animals * animalsNeedPlants;
                animals += animals * animalsBorningCoefficient * 1.2;
                plants /= exhaustion;
                animals /= exhaustion;
                exhaustion += 0.05;
            case PREDATORS:
                plants += plants * plantGrowing  * fertility;
                plants -= animals * animalsNeedPlants;
                animals += animals * animalsBorningCoefficient * owner.getFood_production();
                plants /= exhaustion;
                animals /= exhaustion;
                exhaustion += 0.05;
            default:
                plants += plants * plantGrowing  * fertility;
                plants -= animals * animalsNeedPlants;
                animals += animals * animalsBorningCoefficient * 1.2;
                plants /= exhaustion;
                animals /= exhaustion;
                exhaustion += 0.05;
        }
    }

    public void EatPlants(double coefficient) {
        plants -= population * coefficient;
        if (plants < 0) {
            population += (int)(plants / coefficient);
            plants = 1;
        }
    }

    public void EatAnimals(double coefficient) {
        animals -= population * coefficient;
        if (animals < 0) {
            population += (int)(animals / coefficient);
            animals = 2;
        }
    }

    public double getAnimals() {
        return animals;
    }

    public double getPlants() {
        return plants;
    }

    public int getWater() {
        return water;
    }

    public double getRecourse() {
        return recourse;
    }

    public String getOwnerName() {
        if (owner != null) {
            return owner.getName();
        }
        return "Nobody";
    }

    public void Colonization(Tribe colonist) {
        if (owner == null) {
            owner = colonist;
            population = 2;
        }
    }

    public Tribe getOwner() {
        return owner;
    }

    public boolean Conquest(Tribe invader, int warriors) {
        population -= warriors;
        if (population < 0) {
            population = Math.abs(population);
            Territory territory = this;
            owner.Exodus(territory);
            owner = invader;
            return true;
        }
        return false;
    }


}
