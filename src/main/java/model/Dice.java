package model;

import java.util.Random;

public class Dice {

    private final Random rand;
    private int dieOne;
    private int dieTwo;

    public Dice(Random rand) {
        this.rand = rand;
    }

    public int getDieOne() {
        return this.rand.nextInt(6) + 1;
    }

    public int roll() {
        dieOne = rand.nextInt(6) + 1;
        dieTwo = rand.nextInt(6) + 1;
        return dieOne + dieTwo;
    }

}
