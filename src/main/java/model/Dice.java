package model;

import java.util.Random;

public class Dice {

    private final Random rand;

    public Dice(Random rand) {
        this.rand = rand;
    }

    public int getDieOne() {
        return this.rand.nextInt(6) + 1;
    }

}
