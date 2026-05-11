package model;

import java.util.Random;

public class Dice {

    private final Random rand;
    private int dieOne;
    private int dieTwo;


    Dice(Random rand) {
        this.rand = rand;
    }

    public void roll() {
        int NUM_DIE_SIDES = 6;
        dieOne = rand.nextInt(NUM_DIE_SIDES) + 1;
        dieTwo = rand.nextInt(NUM_DIE_SIDES) + 1;
    }

    public int getDieOne() {
        return dieOne;
    }

    public int getDieTwo() {
        return dieTwo;
    }

    public int getTotal() {
        return dieOne + dieTwo;
    }

    public boolean isDoubles() {
        return dieOne == dieTwo;
    }

}
