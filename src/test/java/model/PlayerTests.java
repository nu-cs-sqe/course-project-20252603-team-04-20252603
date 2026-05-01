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
        boolean success = player.buy(Double.MAX_VALUE);

        assertFalse(success, "Buying with maximum amount should be rejected/properly handled");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }
    public void Tests__Buying_With_Min_Integer() {
        Player player = new Player("John", 100.0);
        boolean success = player.buy(Double.MIN_VALUE);

        assertFalse(success, "Buying with maximum amount should be rejected/properly handled");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }

    // ==================================================================================================
    // Test suite for the sell method of the Player class, covering various scenarios including edge cases
    //===================================================================================================
    @Test
    public void Tests_Selling_With_No_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.sell(0.0);

        assertTrue(success);
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged when selling with no money");
    }
    @Test
    public void Tests_Selling_With_Negative_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.sell(-10.0);

        assertFalse(success, "Selling with negative amount should not be allowed");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }
    @Test 
    public void Tests_Selling_With_Valid_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.sell(50.0);

        assertTrue(success, "Selling with valid amount should be successful");
        assertEquals(150.0, player.getBalance(), 0.001, "Balance should increase by the sale amount");
    }
    @Test
    public void Tests_Selling_With_Max_Amout() {
        Player player = new Player("John", 100.0);
        boolean success = player.sell(Double.MAX_VALUE);

        assertFalse(success, "Selling with maximum amount should fail due to overflow");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }
    // ==================================================================================================
    // Test suite for the canAfford method of the Player class, covering BVA scenarios
    // ==================================================================================================
    
    @Test
    public void Tests_CanAfford_With_Zero_Amount() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(0.0);
        
        assertTrue(result, "Should be able to afford 0 amount (TC10)");
    }

    @Test
    public void Tests_CanAfford_Slightly_Less_Than_Balance() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(99.99);
        
        assertTrue(result, "Should be able to afford amount slightly less than balance (TC11)");
    }

    @Test
    public void Tests_CanAfford_Exact_Balance() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(100.0);
        
        assertTrue(result, "Should be able to afford exact balance amount (TC12)");
    }

    @Test
    public void Tests_CannotAfford_Slightly_Greater_Than_Balance() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(100.01);
        
        assertFalse(result, "Should not be able to afford amount greater than balance (TC13)");
    }

    @Test
    public void Tests_CanAfford_Negative_Amount() {
        Player player = new Player("John", 100.0);
        
        boolean result = player.canAfford(-10.0);
        assertTrue(result, "Negative amounts should evaluate to true if checking strictly mathematically, or validly handled (TC14)");
    }

    @Test
    public void Tests_CannotAfford_Max_Double() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(Double.MAX_VALUE);
        
        assertFalse(result, "Should not be able to afford Double.MAX_VALUE (TC14b)");
    }






    

}
