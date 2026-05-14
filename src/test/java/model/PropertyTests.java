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

    
}