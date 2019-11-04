package sample;

import java.util.Random;

public enum TypesOfTribes {
    PREDATORS,
    HERBIVORES,
    OMNIVOROUS;

    public static TypesOfTribes getType() {
        int index = new Random().nextInt(2);
        switch (index) {
            case 0:
                return PREDATORS;
            case 1:
                return HERBIVORES;
            default:
                return OMNIVOROUS;
        }
    }
}
