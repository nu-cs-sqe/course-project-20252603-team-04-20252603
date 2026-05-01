package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

import model.Properties;

import model.Player;

public class PlayerTests {

    // ==================================================================================================
    // Test suite for the buy method of the Player class, covering various scenarios
    // including edge cases
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
    // Test suite for the sell method of the Player class, covering various
    // scenarios including edge cases
    // ===================================================================================================
    @Test
    public void Tests_Selling_With_No_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.sell(0.0); // 0 * 0.8 = 0

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

        // Passed value is 50.0. Added amount should be 50.0 * 0.8 = 40.0.
        // New balance = 100.0 + 40.0 = 140.0
        boolean success = player.sell(50.0);

        assertTrue(success, "Selling with valid amount should be successful");
        assertEquals(140.0, player.getBalance(), 0.001, "Balance should increase by 80% of the sale amount");
    }

    @Test
    public void Tests_Selling_With_Max_Amout() {
        Player player = new Player("John", 100.0);
        boolean success = player.sell(Double.MAX_VALUE);

        assertFalse(success, "Selling with maximum amount should fail due to overflow");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }
    // ==================================================================================================
    // Test suite for the canAfford method of the Player class, covering BVA
    // scenarios
    // ==================================================================================================

    @Test
    public void Tests_CanAfford_With_Zero_Amount() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(0.0);

        assertTrue(result, "Should be able to afford 0 amount");
    }

    @Test
    public void Tests_CanAfford_Slightly_Less_Than_Balance() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(99.99);

        assertTrue(result, "Should be able to afford amount slightly less than balance");
    }

    @Test
    public void Tests_CanAfford_Exact_Balance() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(100.0);

        assertTrue(result, "Should be able to afford exact balance amount");
    }

    @Test
    public void Tests_CannotAfford_Slightly_Greater_Than_Balance() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(100.01);

        assertFalse(result, "Should not be able to afford amount greater than balance");
    }

    @Test
    public void Tests_CanAfford_Negative_Amount() {
        Player player = new Player("John", 100.0);

        boolean result = player.canAfford(-10.0);
        assertFalse(result,
                "Negative amounts should evaluate to true if checking strictly mathematically, or validly handled");
    }

    @Test
    public void Tests_CannotAfford_Max_Double() {
        Player player = new Player("John", 100.0);
        boolean result = player.canAfford(Double.MAX_VALUE);

        assertFalse(result, "Should not be able to afford Double.MAX_VALUE");
    }

    //
    // Test suite for addproperty class
    //
    @Test
    public void Test_Adding_Property_To_Player() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);

        EasyMock.replay(propertyMock);

        boolean success = player.addProperty(propertyMock);

        assertTrue(success, "Adding a property should be successful");
        assertEquals(1, player.getOwnedProperties().size(), "Player should own the added property");
        assertTrue(player.getOwnedProperties().contains(propertyMock),
                "Player's owned properties should contain the added property");

    }

    @Test
    public void Test_Adding_Null_Property_To_Player() {
        Player player = new Player("John", 100.0);

        boolean success = player.addProperty(null);

        assertFalse(success, "Adding a null property should be rejected");
        assertTrue(player.getOwnedProperties().isEmpty(),
                "Player should not own any properties after attempting to add null");
    }

    @Test
    public void Test_Adding_Duplicate_Property_To_Player() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);

        EasyMock.replay(propertyMock);

        boolean firstAddSuccess = player.addProperty(propertyMock);
        boolean secondAddSuccess = player.addProperty(propertyMock);

        assertTrue(firstAddSuccess, "First addition of a property should be successful");
        assertFalse(secondAddSuccess, "Adding the same property again should be rejected");
        assertEquals(1, player.getOwnedProperties().size(), "Player should only own one instance of the property");
    }

    //
    // Test suite for removeProperty method
    ///
    @Test
    public void Test_Removing_Property_From_Player_Exists() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);

        EasyMock.replay(propertyMock);

        player.addProperty(propertyMock);
        boolean success = player.removeProperty(propertyMock);

        assertTrue(success, "Removing an existing property should be successful");
        assertFalse(player.getOwnedProperties().contains(propertyMock),
                "Player's owned properties should no longer contain the removed property");
    }

    @Test
    public void Test_Removing_Property_From_Player_Not_Exists() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);

        EasyMock.replay(propertyMock);

        boolean success = player.removeProperty(propertyMock);

        assertFalse(success, "Removing a non-existing property should be rejected");
    }

    @Test
    public void Test_Removing_Property_From_Player_With_No_Properties() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);

        EasyMock.replay(propertyMock);

        boolean success = player.removeProperty(propertyMock);

        assertFalse(success, "Removing a property from a player with no properties should be rejected");
    }

    @Test
    public void Test_Removing_Null_Property_From_Player() {
        Player player = new Player("John", 100.0);

        boolean success = player.removeProperty(null);

        assertFalse(success, "Removing a null property should be rejected");
    }

    //
    // Test suite for sell property method
    //
    @Test

    public void Tests_Selling_Owned_Property() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);

        EasyMock.expect(propertyMock.getPrice()).andReturn(100.0);
        EasyMock.replay(propertyMock);

        player.addProperty(propertyMock);
        boolean success = player.sellProperty(propertyMock);

        assertTrue(success, "Selling an owned property should be successful");
        assertFalse(player.getOwnedProperties().contains(propertyMock), "Property should be removed from ownership");

        assertEquals(180.0, player.getBalance(), 0.001, "Balance should increase by 80% of the property price");
    }

    @Test
    public void Test_Selling_Unowned_Property() {
        Player player = new Player("John", 100.0);
        Properties propertyMock = EasyMock.createMock(Properties.class);
        EasyMock.replay(propertyMock);

        boolean success = player.sellProperty(propertyMock);

        assertFalse(success, "Selling an unowned property should fail");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged");
    }

    @Test
    public void Test_Selling_Null_Property() {
        Player player = new Player("John", 100.0);

        boolean success = player.sellProperty(null);

        assertFalse(success, "Selling a null property should fail");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged");
    }

}
