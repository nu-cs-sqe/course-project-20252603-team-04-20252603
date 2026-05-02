package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiceTests {

    @Test
    public void getDieOne_OneRandomRoll_Return0() {

        Dice dice = new Dice();

        int expected = 0;
        assertEquals(expected, dice.getDieOne());

    }

    @Test
    public void getDieOne_OneRandomRoll_Return1() {

        Dice dice = new Dice();

        int expected = 1;
        assertEquals(expected, dice.getDieOne());

    }

}
