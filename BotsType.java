package sample;

import java.util.Random;

public enum BotsType {
    ATTACKER,
    POPULATION_MASTER,
    DEFENDER,
    MONSTER;

    public static BotsType getType() {
        int rand = new Random().nextInt(3);
        switch (rand) {
            case 0:
                return ATTACKER;
            case 1:
                return POPULATION_MASTER;
            case 2:
                return DEFENDER;
            case 3:
                return MONSTER;
            default:
                return DEFENDER;
        }
    }
}



