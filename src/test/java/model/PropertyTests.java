package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.easymock.EasyMock;
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

    @Test
    public void TC4_Constructor_Max_Double_Price() {
        double maxPrice = Double.MAX_VALUE;
        Property property = new Property("Test Property", maxPrice, 50.0, null);

        assertEquals(maxPrice, property.getPrice(), "Property should accept Double.MAX_VALUE");
    }
    // ==================================================================================================
    // Test suite for isOwnedBy(Player player)
    // ==================================================================================================
     @Test
    public void TC11_IsOwnedBy_Null_Player() {
        Property property = new Property("Test Property", 100.0, 50.0, null);
        
        assertFalse(property.isOwnedBy(null));
    }

    @Test
    public void TC12_IsOwnedBy_Player_Is_Owner() {
        // Arrange: Create mock player
        Player mockOwner = EasyMock.createMock(Player.class);
        EasyMock.replay(mockOwner);
        
        Property property = new Property("Test Property", 100.0, 50.0, mockOwner);
        
        // Act & Assert
        assertTrue(property.isOwnedBy(mockOwner));
    }

    @Test
    public void TC13_IsOwnedBy_Player_Not_Owner() {
        // Arrange
        Player mockOwner = EasyMock.createMock(Player.class);
        Player mockOtherPlayer = EasyMock.createMock(Player.class);
        EasyMock.replay(mockOwner, mockOtherPlayer);
        
        Property property = new Property("Test Property", 100.0, 50.0, mockOwner);
        
        // Act & Assert
        assertFalse(property.isOwnedBy(mockOtherPlayer));
    }

    @Test
    public void TC14_IsOwnedBy_Property_Unowned() {
        // Arrange
        Player mockPlayer = EasyMock.createMock(Player.class);
        EasyMock.replay(mockPlayer);
        
        Property property = new Property("Test Property", 100.0, 50.0, null);
        
        // Act & Assert
        assertFalse(property.isOwnedBy(mockPlayer));
    }
}


