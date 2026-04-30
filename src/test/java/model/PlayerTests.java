package model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Model.Player;

public class PlayerTests {

    // ==================================================================================================
    // Test suite for the buy method of the Player class, covering various scenarios including edge cases
    // ==================================================================================================
    @Test 
    public void Tests_Buying_With_No_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(0.0);

        assertTrue(success);
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged when buying with no money");
    }

    @Test
    public void Tests_Buying_With_Enough_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(-10.0);

        assertFalse(success, "Buying with negative amount should be rejected");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }

    @Test
    public void Tests_Buying_With_Insufficient_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(101.0);

        assertFalse(success, "Buying with insufficient funds should be rejected");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }

    @Test 
    public void Tests_Buying_With_Exact_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(100.0);

        assertTrue(success, "Buying with exact funds should be successful");
        assertEquals(0, player.getBalance(), 0.001, "Balance should = 0 after buying with exact funds");
    }
    @Test

    public void Tests_Buying_With_Valid_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(50.0);

        assertTrue(success, "Buying with valid amount should be successful");
        assertEquals(50.0, player.getBalance(), 0.001, "Balance should decrease by the purchase amount");
    }

    @Test 

    public void Tests__Buying_With_Max_Integer() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(Integer.MAX_VALUE);

        assertFalse(success, "Buying with maximum amount should be rejected/properly handled");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }




    

}
