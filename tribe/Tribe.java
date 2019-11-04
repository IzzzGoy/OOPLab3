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
        attack = Math.random() + 1;
        born = Math.random() + 1;
        food_production = Math.random() + 1;
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
                DoAttAndCol(territories, ourTerritory, index);
            } else if (index > 2449) {
                Colonise(ourTerritory, territories.get(index - 50));
                Attack(ourTerritory, territories.get(index - 50));
                DoAttAndCol(territories, ourTerritory, index);
            } else {
                Colonise(ourTerritory, territories.get(index + 50));
                Attack(ourTerritory, territories.get(index + 50));
                Colonise(ourTerritory, territories.get(index - 50));
                Attack(ourTerritory, territories.get(index - 50));
                DoAttAndCol(territories, ourTerritory, index);
            }
        }
    }

    private void DoAttAndCol(ArrayList<Territory> territories, Territory ourTerritory, int index) {
        if (index % 50 != 0) {
            Colonise(ourTerritory,territories.get(index - 1));
            Attack(ourTerritory,territories.get(index - 1));
        }
        if ((index + 1) % 50 != 0 ) {
            Colonise(ourTerritory,territories.get(index + 1));
            Attack(ourTerritory,territories.get(index + 1));
        }
    }

    public void addAttack() {
        switch (this.type) {
            case PREDATORS:
                attack += 0.7;
                break;
            case HERBIVORES:
                attack += 0.5;
                break;
            case OMNIVOROUS:
                attack += 0.3;
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
                food_production += 0.5;
                break;
            case HERBIVORES:
                food_production += 1;
                break;
            case OMNIVOROUS:
                food_production += 0.7;
                break;
        }
    }

    private void addNeighbors(int index, ArrayList<Territory> neighbors, ArrayList<Territory> territories) {
        if (index % 50 != 0) {                                                          //Условие для левого края
            neighbors.add(territories.get(index - 1));
        }
        if ((index + 1) % 50 != 0 ) {                                                   //Условие для правого края
            neighbors.add(territories.get(index + 1));
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
        int warriors = (int)(attack * 1/10 * startTerritory.getPopulation());
        if (endTerritory.Conquest(this,warriors)) {
            ourTerritories.add(endTerritory);
            startTerritory.changePopulation(-(warriors/2));
        }
        else {
            startTerritory.changePopulation(-warriors);
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

    public final ArrayList<Territory> getOurTerritories() {
        return ourTerritories;
    }

}
