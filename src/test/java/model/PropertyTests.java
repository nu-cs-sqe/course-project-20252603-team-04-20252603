package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import util.Constants;

public class PropertyTests {

    @Test
    public void TC1_Constructor_Creates_Property_With_Valid_Values() {
        // Arrange
        double expectedPrice = 100.0;
        double expectedRent = 50.0;

        // Act
        Property property = new Property("Test Property", expectedPrice, expectedRent, Constants.NO_OWNER);

        // Assert
        assertEquals(expectedPrice, property.getPrice(), 0.001, "Property price should match constructor input");
        assertEquals(expectedRent, property.getRent(), 0.001, "Property rent should match constructor input");
        assertNull(property.getOwner(), "Newly created property should have no owner");
    }

    @Test
    public void TC2_Constructor_With_Zero_Price_And_Rent() {
        Property property = new Property("Test Property", 0.0, 0.0, null);

        assertEquals(0.0, property.getPrice(), 0.001);
        assertEquals(0.0, property.getRent(), 0.001);
    }

    @Test
    public void TC3_Constructor_With_Negative_Price_Should_Reject() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Property("Test Property", -50.0, 50.0, null);
        }, "Negative price should be rejected");
    }

    

}
