package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiceTests {

    @Test
    public void roll_OnLowestValues_SetDiceTo1And1() {

        Dice dice = new Dice();
        dice.roll();

        int expected = 1;
        assertEquals(expected, dice.getDieOne());
        assertEquals(expected, dice.getDieTwo());
    }

}
