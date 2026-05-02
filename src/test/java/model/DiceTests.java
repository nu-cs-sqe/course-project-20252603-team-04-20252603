package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiceTests {

    @Test
    public void roll_OnLowestValues_SetDiceTo1And1() {

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(0);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);
        dice.roll();

        int expected = 1;
        assertEquals(expected, dice.getDieOne());
        assertEquals(expected, dice.getDieTwo());
    }

    @Test
    public void roll_OnHighestValues_SetDiceTo6And6() {

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(5);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);
        dice.roll();

        int expected = 6;
        assertEquals(expected, dice.getDieOne());
        assertEquals(expected, dice.getDieTwo());
    }

    @Test
    public void getDieOne_OnLowestRoll_Return1() {
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(0);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);
        dice.roll();

        int expected = 1;
        assertEquals(expected, dice.getDieOne());
    }

    @Test
    public void getDieOne_OnHighestRoll_Return6() {
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(5);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);
        dice.roll();

        int expected = 6;
        assertEquals(expected, dice.getDieOne());
    }

    @Test
    public void getDieTwo_OnLowestRoll_Return1() {
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(0);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);
        dice.roll();

        int expected = 1;
        assertEquals(expected, dice.getDieTwo());
    }

}
