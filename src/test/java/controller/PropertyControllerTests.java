package controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import model.Player;
import model.Property;

public class PropertyControllerTests {


    @Test
    public void TC1_PromptPurchase_NullPlayer_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Property property = EasyMock.createMock(Property.class);
        EasyMock.replay(property);

        assertThrows(NullPointerException.class,
                () -> controller.promptPurchase(null, property));

        EasyMock.verify(property);
    }

    @Test
    public void TC2_PromptPurchase_NullProperty_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(NullPointerException.class,
                () -> controller.promptPurchase(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void TC3_PromptPurchase_PropertyAlreadyOwned_ReturnsFalse() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.replay(player, property);

        boolean result = controller.promptPurchase(player, property);

        assertFalse(result);
        EasyMock.verify(player, property);
    }

    @Test
    public void TC4_PromptPurchase_PlayerCannotAfford_ReturnsFalse() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(200.0);
        EasyMock.expect(player.canAfford(200.0)).andReturn(false);
        EasyMock.replay(player, property);

        boolean result = controller.promptPurchase(player, property);

        assertFalse(result);
        EasyMock.verify(player, property);
    }

    @Test
    public void TC5_PromptPurchase_PlayerCanAffordExact_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        EasyMock.replay(player, property);

        boolean result = controller.promptPurchase(player, property);

        assertTrue(result);
        EasyMock.verify(player, property);
    }

    @Test
    public void TC6_PromptPurchase_PlayerCanAffordSurplus_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        EasyMock.replay(player, property);

        boolean result = controller.promptPurchase(player, property);

        assertTrue(result);
        EasyMock.verify(player, property);
    }


    @Test
    public void TC7_BuyProperty_NullPlayer_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Property property = EasyMock.createMock(Property.class);
        EasyMock.replay(property);

        assertThrows(NullPointerException.class,
                () -> controller.buyProperty(null, property));

        EasyMock.verify(property);
    }

    @Test
    public void TC8_BuyProperty_NullProperty_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(NullPointerException.class,
                () -> controller.buyProperty(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void TC9_BuyProperty_SuccessfulPurchase_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.purchase(player)).andReturn(true);
        EasyMock.replay(player, property);

        boolean result = controller.buyProperty(player, property);

        assertTrue(result);
        EasyMock.verify(player, property);
    }

    @Test
    public void TC10_BuyProperty_FailedPurchase_ReturnsFalse() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.purchase(player)).andReturn(false);
        EasyMock.replay(player, property);

        boolean result = controller.buyProperty(player, property);

        assertFalse(result);
        EasyMock.verify(player, property);
    }


    @Test
    public void TC11_DeclineProperty_NullPlayer_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Property property = EasyMock.createMock(Property.class);
        EasyMock.replay(property);

        assertThrows(NullPointerException.class,
                () -> controller.declineProperty(null, property));

        EasyMock.verify(property);
    }

    @Test
    public void TC12_DeclineProperty_NullProperty_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(NullPointerException.class,
                () -> controller.declineProperty(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void TC13_DeclineProperty_ValidInputs_CompletesWithoutException() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.replay(player, property);

        assertDoesNotThrow(() -> controller.declineProperty(player, property));

        EasyMock.verify(player, property);
    }


    @Test
    public void TC14_HandleRentPayment_NullRenter_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Property property = EasyMock.createMock(Property.class);
        EasyMock.replay(property);

        assertThrows(NullPointerException.class,
                () -> controller.handleRentPayment(null, property));

        EasyMock.verify(property);
    }

    @Test
    public void TC15_HandleRentPayment_NullProperty_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();
        Player renter = EasyMock.createMock(Player.class);
        EasyMock.replay(renter);

        assertThrows(NullPointerException.class,
                () -> controller.handleRentPayment(renter, null));

        EasyMock.verify(renter);
    }

    @Test
    public void TC16_HandleRentPayment_SuccessfulRent_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player renter = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.chargeRent(renter)).andReturn(true);
        EasyMock.replay(renter, property);

        boolean result = controller.handleRentPayment(renter, property);

        assertTrue(result);
        EasyMock.verify(renter, property);
    }

    @Test
    public void TC17_HandleRentPayment_FailedRent_ReturnsFalse() {
        PropertyController controller = new PropertyController();
        Player renter = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        EasyMock.expect(property.chargeRent(renter)).andReturn(false);
        EasyMock.replay(renter, property);

        boolean result = controller.handleRentPayment(renter, property);

        assertFalse(result);
        EasyMock.verify(renter, property);
    }


    @Test
    public void TC18_HandleForcedSale_NullPlayer_ThrowsNullPointerException() {
        PropertyController controller = new PropertyController();

        assertThrows(NullPointerException.class,
                () -> controller.handleForcedSale(null, 100.0));
    }

    @Test
    public void TC19_HandleForcedSale_RequiredAmountZero_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.canAfford(0.0)).andReturn(true);
        EasyMock.replay(player);

        boolean result = controller.handleForcedSale(player, 0.0);

        assertTrue(result);
        EasyMock.verify(player);
    }

    @Test
    public void TC20_HandleForcedSale_PlayerAlreadyHasEnough_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.canAfford(500.0)).andReturn(true);
        EasyMock.replay(player);

        boolean result = controller.handleForcedSale(player, 500.0);

        assertTrue(result);
        EasyMock.verify(player);
    }

    @Test
    public void TC21_HandleForcedSale_NoProperties_ReturnsFalse() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.canAfford(500.0)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
        EasyMock.expect(player.canAfford(500.0)).andReturn(false);
        EasyMock.replay(player);

        boolean result = controller.handleForcedSale(player, 500.0);

        assertFalse(result);
        EasyMock.verify(player);
    }

    @Test
    public void TC22_HandleForcedSale_OnePropertyCoversAmount_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        Set<Property> properties = new LinkedHashSet<>();
        properties.add(property);

        EasyMock.expect(player.canAfford(300.0)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(properties);
        EasyMock.expect(player.canAfford(300.0)).andReturn(false);
        EasyMock.expect(property.getResaleValue()).andReturn(400.0);
        property.resetOwner();
        EasyMock.expect(player.receive(400.0)).andReturn(true);
        EasyMock.expect(player.canAfford(300.0)).andReturn(true);
        EasyMock.replay(player, property);

        boolean result = controller.handleForcedSale(player, 300.0);

        assertTrue(result);
        EasyMock.verify(player, property);
    }

    @Test
    public void TC24_HandleForcedSale_StopsAfterPlayerBecomesAffordable_ReturnsTrue() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property firstProperty = EasyMock.createMock(Property.class);
        Property secondProperty = EasyMock.createMock(Property.class);
        Set<Property> properties = new LinkedHashSet<>();
        properties.add(firstProperty);
        properties.add(secondProperty);

        EasyMock.expect(player.canAfford(300.0)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(properties);
        EasyMock.expect(player.canAfford(300.0)).andReturn(false);
        EasyMock.expect(firstProperty.getResaleValue()).andReturn(400.0);
        firstProperty.resetOwner();
        EasyMock.expect(player.receive(400.0)).andReturn(true);
        EasyMock.expect(player.canAfford(300.0)).andReturn(true);
        EasyMock.expect(player.canAfford(300.0)).andReturn(true);
        EasyMock.replay(player, firstProperty, secondProperty);

        boolean result = controller.handleForcedSale(player, 300.0);

        assertTrue(result);
        EasyMock.verify(player, firstProperty, secondProperty);
    }

    @Test
    public void TC23_HandleForcedSale_SellAllPropertiesStillInsufficient_ReturnsFalse() {
        PropertyController controller = new PropertyController();
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);
        Set<Property> properties = new LinkedHashSet<>();
        properties.add(property);

        EasyMock.expect(player.canAfford(1000.0)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(properties);
        EasyMock.expect(player.canAfford(1000.0)).andReturn(false);
        EasyMock.expect(property.getResaleValue()).andReturn(50.0);
        property.resetOwner();
        EasyMock.expect(player.receive(50.0)).andReturn(true);
        EasyMock.expect(player.canAfford(1000.0)).andReturn(false);
        EasyMock.replay(player, property);

        boolean result = controller.handleForcedSale(player, 1000.0);

        assertFalse(result);
        EasyMock.verify(player, property);
    }
}
