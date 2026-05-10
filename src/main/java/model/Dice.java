package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Random;

public class Dice {

    private final Random rand;
    private int dieOne;
    private int dieTwo;

    // Had to add warnings suppressor to bypass spotbugs...
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Random is injected to make dice rolls deterministic in unit tests."
    )
    public Dice(Random rand) {
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
