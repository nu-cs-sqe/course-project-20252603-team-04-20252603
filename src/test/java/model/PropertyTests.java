package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import util.OwnershipStatus;
import util.Constants;
import model.GameEngine;

public class PropertyTests {

    // Constructor validation
    @Test
    public void TC1_Constructor_Creates_Property_With_Valid_Values() {
        double expectedPrice = 100.0;
        double expectedRent = 50.0;

        Property property = new Property("Test Property", expectedPrice, expectedRent);

        assertEquals(expectedPrice, property.getPrice(), 0.001, "Property price should match constructor input");
        assertEquals(expectedRent, property.getRent(), 0.001, "Property rent should match constructor input");
        assertEquals(false, property.isOwned(), "Newly created property should be unowned");
    }

    @Test
    public void TC2_Constructor_With_Zero_Price_And_Rent() {
        Property property = new Property("Test Property", 0.0, 0.0);

        assertEquals(0.0, property.getPrice(), 0.001);
        assertEquals(0.0, property.getRent(), 0.001);
    }

    @Test
    public void TC3_Constructor_With_Negative_Price_Should_Reject() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Property("Test Property", -50.0, 50.0);
        }, "Negative price should be rejected");
    }

    @Test
    public void TC4_Constructor_With_Maximum_Double_Price() {
        Property property = new Property("Test Property", Double.MAX_VALUE, 50.0);

        assertEquals(Double.MAX_VALUE, property.getPrice(), "Should handle maximum double price");
    }

    @Test
    public void TC5_Constructor_Creates_Property_With_Valid_Rent() {
        double expectedPrice = 100.0;
        double expectedRent = 50.0;

        Property property = new Property("Test Property", expectedPrice, expectedRent);

        assertEquals(expectedRent, property.getRent(), 0.001, "Property rent should match constructor input");
    }

    @Test
    public void TC6_Constructor_With_Zero_Rent() {
        Property property = new Property("Test Property", 100.0, 0.0);

        assertEquals(0.0, property.getRent(), 0.001, "Property with zero rent should be created");
    }

    @Test
    public void TC7_Constructor_With_Negative_Rent_Should_Reject() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Property("Test Property", 100.0, -25.0);
        }, "Negative rent should be rejected");
    }

    @Test
    public void TC8_Constructor_With_Rent_Greater_Than_Price() {
        Property property = new Property("Test Property", 50.0, 100.0);

        assertEquals(50.0, property.getPrice(), 0.001);
        assertEquals(100.0, property.getRent(), 0.001, "System allows rent greater than price");
    }

    // Ownership checks
    @Test
    public void TC9_Property_Unowned_Returns_False() {
        Property property = new Property("Test Property", 100.0, 50.0);

        assertEquals(false, property.isOwned(), "Newly created property should not be owned");
    }

    @Test
    public void TC10_Property_Owned_Returns_True() {
        Player player = new Player("Alice", 200.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(player); // Sets ownershipStatus to OWNED

        assertEquals(true, property.isOwned(), "Purchased property should be owned");
    }

    @Test
    public void TC11_Check_Ownership_With_Null_Player_Returns_False() {
        Property property = new Property("Test Property", 100.0, 50.0);

        boolean result = property.isOwnedBy(null);

        assertEquals(false, result, "Null player should never own property");
    }

    @Test
    public void TC12_Check_Ownership_Player_Is_Owner_Returns_True() {
        Player owner = new Player("Alice", 200.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.isOwnedBy(owner);

        assertEquals(true, result, "Owner should own the property");
    }

    @Test
    public void TC13_Check_Ownership_Player_Is_Not_Owner_Returns_False() {
        Player owner = new Player("Alice", 200.0);
        Player notOwner = new Player("Bob", 200.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.isOwnedBy(notOwner);

        assertEquals(false, result, "Non-owner should not own property");
    }

    @Test
    public void TC14_Check_Ownership_Property_Unowned_Returns_False() {
        Player player = new Player("Alice", 200.0);
        Property property = new Property("Test Property", 100.0, 50.0);

        boolean result = property.isOwnedBy(player);

        assertEquals(false, result, "Unowned property should return false");
    }

    // Purchase behavior
    @Test
    public void TC15_Valid_Purchase_By_Player_With_Enough_Balance() {
        Player player = new Player("Alice", 200.0);
        Property property = new Property("Test Property", 100.0, 50.0);

        property.purchase(player);

        assertEquals(true, property.isOwned(), "Property should be owned after purchase");
        assertEquals(true, property.isOwnedBy(player), "Purchasing player should own the property");
        assertEquals(100.0, player.getBalance(), 0.001, "Player balance should decrease by property price");
    }

    @Test
    public void TC16_Purchase_With_Exact_Balance() {
        Player player = new Player("Alice", 100.0);
        Property property = new Property("Test Property", 100.0, 50.0);

        boolean result = property.purchase(player);

        assertEquals(true, result, "Purchase should succeed with exact balance");
        assertEquals(true, property.isOwned(), "Property should be owned after purchase");
        assertEquals(true, property.isOwnedBy(player), "Purchasing player should own the property");
        assertEquals(0.0, player.getBalance(), 0.001, "Player balance should be zero after purchase");
    }

    @Test
    public void TC17_Purchase_With_Insufficient_Balance() {
        Player player = new Player("Alice", 50.0);
        Property property = new Property("Test Property", 100.0, 50.0);

        boolean result = property.purchase(player);

        assertEquals(false, result, "Purchase should fail with insufficient balance");
        assertEquals(false, property.isOwned(), "Property should remain unowned");
        assertEquals(false, property.isOwnedBy(player), "Purchasing player should not own the property");
        assertEquals(50.0, player.getBalance(), 0.001, "Player balance should not change");
    }

    @Test
    public void TC18_Purchase_Null_Player() {
        Property property = new Property("Test Property", 100.0, 50.0);

        boolean result = property.purchase(null);

        assertEquals(false, result, "Purchase with null player should fail");
        assertEquals(false, property.isOwned(), "Property should remain unowned");
    }

    @Test
    public void TC19_Purchase_Already_Owned_Property() {
        Player owner = new Player("Alice", 200.0);
        Player buyer = new Player("Bob", 200.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.purchase(buyer);

        assertEquals(false, result, "Purchase of already owned property should fail");
        assertEquals(true, property.isOwnedBy(owner), "Original owner should still own the property");
        assertEquals(false, property.isOwnedBy(buyer), "Buyer should not own the property");
        assertEquals(200.0, buyer.getBalance(), 0.001, "Buyer balance should not change");
    }

    @Test
    public void TC20_Purchase_Zero_Price_Property() {
        Player player = new Player("Alice", 100.0);
        Property property = new Property("Test Property", 0.0, 50.0);

        boolean result = property.purchase(player);

        assertEquals(true, result, "Purchase of free property should succeed");
        assertEquals(true, property.isOwned(), "Property should be owned after purchase");
        assertEquals(true, property.isOwnedBy(player), "Purchasing player should own the property");
        assertEquals(100.0, player.getBalance(), 0.001, "Player balance should not change for free property");
    }

    @Test
    public void TC21_Charge_Rent_With_Player_Having_Exact_Amount() {
        Player owner = new Player("Alice", 100.0);
        Player renter = new Player("Bob", 50.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.chargeRent(renter);

        assertEquals(true, result, "Rent charge should succeed");
        assertEquals(0.0, renter.getBalance(), 0.001, "Renter balance should be zero after paying rent");
        assertEquals(50.0, owner.getBalance(), 0.001, "Owner should receive exact rent amount");
    }

    @Test
    public void TC22_Charge_Rent_With_Player_Having_More_Than_Rent() {
        Player owner = new Player("Alice", 100.0);
        Player renter = new Player("Bob", 100.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.chargeRent(renter);

        assertEquals(true, result, "Rent charge should succeed");
        assertEquals(50.0, renter.getBalance(), 0.001, "Renter balance should decrease by rent amount");
        assertEquals(50.0, owner.getBalance(), 0.001, "Owner should receive exact rent amount");
    }

    @Test
    public void TC23_Charge_Rent_With_Insufficient_Balance() {
        Player owner = new Player("Alice", 100.0);
        Player renter = new Player("Bob", 25.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.chargeRent(renter);

        assertEquals(false, result, "Rent charge should fail with insufficient balance");
        assertEquals(25.0, renter.getBalance(), 0.001, "Renter balance should not change");
        assertEquals(0.0, owner.getBalance(), 0.001,
                "Owner balance should be zero after purchase but no rent received");
    }

    @Test
    public void TC24_Charge_Rent_To_Null_Player() {
        Player owner = new Player("Alice", 100.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.chargeRent(null);

        assertEquals(false, result, "Rent charge to null player should fail");
        assertEquals(0.0, owner.getBalance(), 0.001,
                "Owner balance should be zero after purchase but no rent received");
    }

    @Test
    public void TC25_Charge_Rent_On_Unowned_Property() {
        Player player = new Player("Alice", 100.0);
        Property property = new Property("Test Property", 100.0, 50.0);

        boolean result = property.chargeRent(player);

        assertEquals(false, result, "Rent charge on unowned property should fail");
        assertEquals(100.0, player.getBalance(), 0.001, "Player balance should not change");
    }

    @Test
    public void TC26_Owner_Pays_Rent_To_Self() {
        Player owner = new Player("Alice", 100.0);
        Property property = new Property("Test Property", 100.0, 50.0);
        property.purchase(owner);

        boolean result = property.chargeRent(owner);

        assertEquals(false, result, "Owner should not pay rent to themselves");
        assertEquals(0.0, owner.getBalance(), 0.001, "Owner balance should remain the same after purchase");
    }

    @Test
    public void TC27_Charge_Zero_Rent() {
        Player owner = new Player("Alice", 100.0);
        Player renter = new Player("Bob", 100.0);
        Property property = new Property("Test Property", 100.0, 0.0);
        property.purchase(owner);

        boolean result = property.chargeRent(renter);

        assertEquals(true, result, "Rent charge should succeed even with zero rent");
        assertEquals(100.0, renter.getBalance(), 0.001, "Renter balance should not change for zero rent");
        assertEquals(0.0, owner.getBalance(), 0.001,
                "Owner balance should be zero after purchase and zero rent received");
    }


}