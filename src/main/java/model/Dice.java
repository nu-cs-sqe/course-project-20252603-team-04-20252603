package model;

import java.util.Random;

public class Dice {

    private Random rand;
    private int dieOne;
    private int dieTwo;

    public Dice(Random rand) {
        this.rand = rand;
    }

    public void roll() {
        dieOne = rand.nextInt(6) + 1;
        dieTwo = rand.nextInt(6) + 1;
    }

    public int getDieOne() {
        return dieOne;
    }

    public int getDieTwo() {
        return dieTwo;
    }

    public int getTotal() {
        return 2;
    }

}
