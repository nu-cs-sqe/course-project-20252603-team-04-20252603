package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

import model.Property;
import util.Constants;

import model.Player;

public class PlayerTests {

    // ==================================================================================================
    // Test suite for the buy method of the Player class, covering various scenarios
    // including edge cases
    // ==================================================================================================
    @Test
    public void Tests_Buying_With_No_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.remove(0.0);

        assertTrue(success);
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged when buying with no money");
    }

    @Test
    public void Tests_Buying_With_Enough_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.remove(-10.0);

        assertFalse(success, "Buying with negative amount should be rejected");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }

    @Test
    public void Tests_Buying_With_Insufficient_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.remove(101.0);

        assertFalse(success, "Buying with insufficient funds should be rejected");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }

    @Test
    public void Tests_Buying_With_Exact_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.remove(100.0);

        assertTrue(success, "Buying with exact funds should be successful");
        assertEquals(0, player.getBalance(), 0.001, "Balance should = 0 after buying with exact funds");
    }

    @Test

    public void Tests_Buying_With_Slightly_Less_Than_Balance() {
        Player player = new Player("John", 100.0);
        boolean success = player.remove(99.0);

        assertTrue(success, "Buying with valid amount should be successful");
        assertEquals(1.0, player.getBalance(), 0.001, "Balance should decrease by the purchase amount");
    }

    @Test
    public void Tests__Buying_With_Max_Integer() {
        Player player = new Player("John", 100.0);
        boolean success = player.remove(Double.MAX_VALUE);

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
        boolean success = player.receive(0.0);

        assertTrue(success);
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged when selling with no money");
    }

    @Test
    public void Tests_Selling_With_Negative_Money() {
        Player player = new Player("John", 100.0);
        boolean success = player.receive(-10.0);

        assertFalse(success, "Selling with negative amount should not be allowed");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should not change");
    }

    @Test
    public void Tests_Selling_With_Valid_Money() {
        Player player = new Player("John", 100.0);

        boolean success = player.receive(50.0);

        assertTrue(success, "Selling with valid amount should be successful");
        assertEquals(150.0, player.getBalance(), 0.001, "Balance should increase by the sale amount");
    }

    @Test
    public void Tests_Selling_With_Max_Amout() {
        Player player = new Player("John", 100.0);
        boolean success = player.receive(Double.MAX_VALUE);

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
        Property propertyMock = EasyMock.createMock(Property.class);

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
        Property propertyMock = EasyMock.createMock(Property.class);

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
        Property propertyMock = EasyMock.createMock(Property.class);

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
        Property propertyMock = EasyMock.createMock(Property.class);

        EasyMock.replay(propertyMock);

        boolean success = player.removeProperty(propertyMock);

        assertFalse(success, "Removing a non-existing property should be rejected");
    }

    @Test
    public void Test_Removing_Property_From_Player_With_No_Properties() {
        Player player = new Player("John", 100.0);
        Property propertyMock = EasyMock.createMock(Property.class);

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
        Property propertyMock = EasyMock.createMock(Property.class);

        EasyMock.expect(propertyMock.getPrice()).andReturn(100.0);
        EasyMock.replay(propertyMock);

        player.addProperty(propertyMock);
        boolean success = player.sellProperty(propertyMock);

        assertTrue(success, "Selling an owned property should be successful");
        assertFalse(player.getOwnedProperties().contains(propertyMock), "Property should be removed from ownership");

        assertEquals(200.0, player.getBalance(), 0.001, "Balance should increase by the property price");
    }

    @Test
    
    public void Test_Selling_Unowned_Property() {
        Player player = new Player("John", 100.0);
        Property propertyMock = EasyMock.createMock(Property.class);
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

    @Test
    public void Test_Selling_Owned_Property_When_Receive_Fails() {
        Player player = new Player("John", 100.0);
        Property propertyMock = EasyMock.createMock(Property.class);

        EasyMock.expect(propertyMock.getPrice()).andReturn(Double.MAX_VALUE);
        EasyMock.replay(propertyMock);

        player.addProperty(propertyMock);
        boolean success = player.sellProperty(propertyMock);

        assertFalse(success, "Selling should fail when receive rejects the price");
        assertTrue(player.getOwnedProperties().contains(propertyMock),
                "Property should remain owned when sale fails");
        assertEquals(100.0, player.getBalance(), 0.001, "Balance should remain unchanged");
    }
    // ==================================================================================================
    // Test suite for goToJail method
    // ==================================================================================================
    @Test
    public void Test_GoToJail_Valid_Position() {
   
        Player player = new Player("John", 100.0);
        boolean success = player.goToJail(Constants.JAIL_POSITION);
        
        assertTrue(success, "Sending player to valid jail position should be successful");
        assertTrue(player.inJail(), "Player should be marked as in jail");
        assertEquals(10, player.getPosition(), "Player position should be set to 10 (the jail tile)");
    }

    @Test
    public void Test_GoToJail_Negative_Position() {
        Player player = new Player("John", 100.0);
        int initialPosition = player.getPosition();
        boolean success = player.goToJail(-1);
        
        assertFalse(success, "Negative jail position should be rejected");
        assertFalse(player.inJail(), "Player should not be in jail");
        assertEquals(initialPosition, player.getPosition(), "Player position should remain unchanged");
    }

    @Test
    public void Test_GoToJail_Max_Integer_Position() {
        Player player = new Player("John", 100.0);
        int initialPosition = player.getPosition();
        boolean success = player.goToJail(Integer.MAX_VALUE);
        
        assertFalse(success, "Maximum integer jail position should be rejected");
        assertFalse(player.inJail(), "Player should not be in jail");
        assertEquals(initialPosition, player.getPosition(), "Player position should remain unchanged");
    }

    @Test
    public void Test_GoToJail_Min_Integer_Position() {
        Player player = new Player("John", 100.0);
        int initialPosition = player.getPosition();
        boolean success = player.goToJail(Integer.MIN_VALUE);
        
        assertFalse(success, "Minimum integer jail position should be rejected");
        assertFalse(player.inJail(), "Player should not be in jail");
        assertEquals(initialPosition, player.getPosition(), "Player position should remain unchanged");
    }

    // ==================================================================================================
    // Test suite for leaveJail method
    // ==================================================================================================
    
    @Test
    public void Test_LeaveJail_When_In_Jail() {
        Player player = new Player("John", 100.0);
        player.goToJail(10); // Setup: Put player in jail
        assertTrue(player.inJail(), "Setup confirmation: Player should be in jail initially");
        
        boolean success = player.leaveJail();
        int positionAfterLeaving = 11;
        
        assertTrue(success, "Player who is in jail should leave successfully");
        assertFalse(player.inJail(), "inJail flag should be false after leaving jail");
        assertEquals(positionAfterLeaving, player.getPosition(), "Player position should advance by 1 after leaving jail");
    }

    @Test
    public void Test_LeaveJail_When_Not_In_Jail() {
        Player player = new Player("John", 100.0); // Never put in jail
        assertFalse(player.inJail(), "Setup confirmation: Player should not be in jail initially");
        
        boolean success = player.leaveJail();
        
        assertFalse(success, "Expected no change/failure when leaving jail while not in jail");
        assertFalse(player.inJail(), "inJail flag should still be false");
    }

    // ==================================================================================================
    // Test suite for isBankrupt method
    // ==================================================================================================

    @Test
    public void Test_IsBankrupt_Zero_Balance() {
        Player player = new Player("John", 0.0);
        
        boolean bankrupt = player.isBankrupt();
        
        assertTrue(bankrupt, "Player with 0.0 balance should be considered bankrupt");
        assertFalse(player.getActive(), "Player should be marked as inactive after being bankrupt");
    }

    @Test
    public void Test_IsBankrupt_Slightly_Above_Zero() {
        Player player = new Player("John", 0.01);
        
        boolean bankrupt = player.isBankrupt();
        
        assertFalse(bankrupt, "Player with balance slightly above 0 should not be bankrupt");
        assertTrue(player.getActive(), "Player should remain active since balance is above 0");

    }

    @Test
    public void Test_IsBankrupt_Negative_Balance() {
        Player player = new Player("John", -10.0);
        
        boolean bankrupt = player.isBankrupt();
        
        assertTrue(bankrupt, "Player with negative balance should be considered bankrupt");
        assertFalse(player.getActive(), "Player should be marked as inactive after being bankrupt");
    }

    // ==================================================================================================
    // Test suite for getActive method
    // ==================================================================================================

    @Test
    public void Test_GetActive_When_Active() {
        Player player = new Player("John", 100.0);
        
        boolean active = player.getActive();
        
        assertTrue(active, "Player should be active when balance is positive");
    }

    @Test
    public void Test_GetActive_When_Inactive() {
        Player player = new Player("John", 0.0);
        player.isBankrupt(); // This sets active to false
        
        boolean active = player.getActive();
        
        assertFalse(active, "Player should be inactive after going bankrupt");
    }

    // ==================================================================================================
    // Test suite for getName method
    // ==================================================================================================

    @Test
    public void Test_GetName_Returns_Correct_Name() {
        String playerName = "Alice";
        Player player = new Player(playerName, 100.0);
        
        String retrievedName = player.getName();
        
        assertEquals(playerName, retrievedName, "getName() should return the name provided during construction");
    }

    @Test
    public void Test_GetName_With_Different_Name() {
        String playerName = "Bob";
        Player player = new Player(playerName, 500.0);
        
        String retrievedName = player.getName();
        
        assertEquals(playerName, retrievedName, "getName() should return the correct name for different players");
    }

    // ==================================================================================================
    // Test suite for getJailTurnCount method
    // ==================================================================================================

    @Test
    public void Test_GetJailTurnCount_Initially_Zero() {
        Player player = new Player("John", 100.0);
        
        int jailTurnCount = player.getJailTurnCount();
        
        assertEquals(0, jailTurnCount, "Player should have jailTurnCount of 0 when created");
    }

    @Test
    public void Test_GetJailTurnCount_After_Going_To_Jail() {
        Player player = new Player("John", 100.0);
        player.goToJail(10);
        
        int jailTurnCount = player.getJailTurnCount();
        
        assertEquals(1, jailTurnCount, "jailTurnCount should be 1 after going to jail");
    }

    @Test
    public void Test_GetJailTurnCount_After_Leaving_Jail() {
        Player player = new Player("John", 100.0);
        player.goToJail(10);
        player.leaveJail();
        
        int jailTurnCount = player.getJailTurnCount();
        
        assertEquals(0, jailTurnCount, "jailTurnCount should be 0 after leaving jail");
    }

    // ==================================================================================================
    // Test suite for incrementJailTurnCount method (TC37–TC40)
    // ==================================================================================================

    @Test
    public void TC37_IncrementJailTurnCount_FromZero_IncrementsToOne() {
        Player player = new Player("John", 100.0);

        player.incrementJailTurnCount();

        assertEquals(1, player.getJailTurnCount(),
                "jailTurnCount should be 1 after incrementing from 0");
        assertFalse(player.inJail(), "incrementJailTurnCount should not change inJail");
        assertEquals(0, player.getPosition(),
                "incrementJailTurnCount should not change position");
    }


    @Test
    public void TC38_IncrementJailTurnCount_FromOne_IncrementsToTwo() {
        Player player = new Player("John", 100.0);
        player.goToJail(Constants.JAIL_POSITION);

        player.incrementJailTurnCount();

        assertEquals(2, player.getJailTurnCount(),
                "jailTurnCount should be 2 after incrementing from 1");
        assertTrue(player.inJail(), "player should remain in jail");
        assertEquals(Constants.JAIL_POSITION, player.getPosition(),
                "position should remain unchanged");
    }
    @Test
    public void TC39_IncrementJailTurnCount_FromTwo_IncrementsToMax() {
        Player player = new Player("John", 100.0);
        player.goToJail(Constants.JAIL_POSITION);
        player.incrementJailTurnCount();

        player.incrementJailTurnCount();

        assertEquals(Constants.MAX_JAIL_TURNS, player.getJailTurnCount(),
                "jailTurnCount should reach MAX_JAIL_TURNS after incrementing from 2");
        assertTrue(player.inJail(), "player should remain in jail");
    }

  


}
