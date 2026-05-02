package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiceTests {

    @Test
    public void getDieOne_OneRandomRoll_Return1() {

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(0);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);

        int expected = 1;
        assertEquals(expected, dice.getDieOne());

    }

    @Test
    public void getDieOne_OneRandomRoll_Return6() {

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(5);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);

        int expected = 6;
        assertEquals(expected, dice.getDieOne());

    }

    @Test
    public void roll_OnRandomRoll_Return2() {

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(0);
        EasyMock.replay(rand);

        Dice dice = new Dice(rand);

        int expected = 2;
        assertEquals(expected, dice.roll());
    }

    @Test
    public void roll_OnRandomRoll_Return12() {

        Random rand = new Random();

        Dice dice = new Dice(rand);

        int expected = 12;
        assertEquals(expected, dice.roll());

    }

}
