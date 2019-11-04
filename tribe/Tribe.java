package sample.tribe;

import sample.Territory;
import sample.TypesOfTribes;

import java.util.ArrayList;

public abstract class Tribe {

    protected TypesOfTribes type;
    protected String name;
    protected ArrayList<Territory> ourTerritories;

    protected double attack;
    protected double born;
    protected double food_production;

    public Tribe(TypesOfTribes type, String nameOfTribe, Territory startTerritory) {
        this.type = type;
        name = nameOfTribe;
        ourTerritories = new ArrayList<>();
        ourTerritories.add(startTerritory);
        //attack = Math.random() + 1;
        born = 0.5;
    }

    public double getFood_production() {
        return food_production;
    }

    public void Exodus (Territory territory) {
        ourTerritories.remove(territory);
    }

    protected abstract void Eat();

    public TypesOfTribes getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void Update(ArrayList<Territory> territories) {

        Eat();
        Grow();

        for (int i = 0; i < ourTerritories.size(); i++) {
            Territory ourTerritory = ourTerritories.get(i);
            int index = territories.indexOf(ourTerritory);
            if (index < 50) {
                Colonise(ourTerritory, territories.get(index + 50));
                Attack(ourTerritory, territories.get(index + 50));
                Migration(ourTerritory, territories.get(index + 50));
                DoAttAndCol(territories, ourTerritory, index);
            } else if (index > 2449) {
                Colonise(ourTerritory, territories.get(index - 50));
                Attack(ourTerritory, territories.get(index - 50));
                Migration(ourTerritory, territories.get(index - 50));
                DoAttAndCol(territories, ourTerritory, index);
            } else {
                Colonise(ourTerritory, territories.get(index + 50));
                Attack(ourTerritory, territories.get(index + 50));
                Colonise(ourTerritory, territories.get(index - 50));
                Attack(ourTerritory, territories.get(index - 50));
                Migration(ourTerritory, territories.get(index + 50));
                Migration(ourTerritory, territories.get(index - 50));
                DoAttAndCol(territories, ourTerritory, index);
            }
        }
    }

    private void DoAttAndCol(ArrayList<Territory> territories, Territory ourTerritory, int index) {
        if (index % 50 != 0) {
            Colonise(ourTerritory,territories.get(index - 1));
            Attack(ourTerritory,territories.get(index - 1));
            Migration(ourTerritory,territories.get(index - 1));
        }
        if ((index + 1) % 50 != 0 ) {
            Colonise(ourTerritory,territories.get(index + 1));
            Attack(ourTerritory,territories.get(index + 1));
            Migration(ourTerritory,territories.get(index + 1));
        }
    }

    public void addAttack() {
        switch (this.type) {
            case PREDATORS:
                attack += 4.7;
                break;
            case HERBIVORES:
                attack += 3.8;
                break;
            case OMNIVOROUS:
                attack += 1.3;
                break;
        }
    }

    public void addBorn() {
        switch (this.type) {
            case PREDATORS:
                born += 0.3;
                break;
            case HERBIVORES:
                born += 0.7;
                break;
            case OMNIVOROUS:
                born += 0.5;
                break;
        }
    }

    public void addFoodProduction() {
        switch (this.type) {
            case PREDATORS:
                food_production += 1.5;
                break;
            case HERBIVORES:
                food_production += 5.2;
                break;
            case OMNIVOROUS:
                food_production += 3.7;
                break;
        }
    }

    private void Grow() {                                                                   //Увеличение популяции
        for (Territory territory : ourTerritories) {
            territory.changePopulation((int) (territory.getPopulation() * born));
        }
    }

    private void Attack (final Territory startTerritory, Territory endTerritory) {
        if (ourTerritories.contains(endTerritory) || endTerritory.getOwner() == null) {
            return;
        }
        int warriors = (int)(attack * 1/5 * startTerritory.getPopulation());
        if (endTerritory.Conquest(this,warriors / 10)) {
            ourTerritories.add(endTerritory);
            startTerritory.changePopulation(-((1/5 * startTerritory.getPopulation())/10));
        }
        else {
            startTerritory.changePopulation(-(1/5 * startTerritory.getPopulation())/20);
        }
    }

    private void Colonise(Territory startTerritory, Territory endTerritory) {
        if (ourTerritories.contains(endTerritory) || endTerritory.getOwner() != null) {
            return;
        }
        if (startTerritory.getPopulation() > 200) {
            endTerritory.Colonization(this);
            ourTerritories.add(endTerritory);
            startTerritory.changePopulation(-49);
            endTerritory.changePopulation(49);
        }
    }

    private void Migration(Territory startTerritory, Territory endTerritory) {
        if (!ourTerritories.contains(endTerritory) || endTerritory.getOwner() == null) {
            return;
        }
        if (endTerritory.getPopulation() != endTerritory.getWater() && (startTerritory.getPopulation() - (endTerritory.getWater() - endTerritory.getPopulation()) / 10) > 0) {
            startTerritory.changePopulation(-(endTerritory.getWater() - endTerritory.getPopulation()) / 10);
            endTerritory.changePopulation((endTerritory.getWater() - endTerritory.getPopulation()) / 10);
        }
    }

    public final ArrayList<Territory> getOurTerritories() {
        return ourTerritories;
    }

    public double getAttack() {
        return attack;
    }

    public double getBorn() {
        return born;
    }
}
