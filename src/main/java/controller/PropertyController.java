package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Player;
import model.Property;

public class PropertyController {

    public boolean promptPurchase(Player player, Property property) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(property, "Property cannot be null");
        if (property.isOwned()) {
            return false;
        }
        if (!player.canAfford(property.getPrice())) {
            return false;
        }
        return true;
    }

    public boolean buyProperty(Player player, Property property) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(property, "Property cannot be null");
        return property.purchase(player);
    }

    public void declineProperty(Player player, Property property) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(property, "Property cannot be null");
    }

    public boolean handleRentPayment(Player renter, Property property) {
        Objects.requireNonNull(renter, "Renter cannot be null");
        Objects.requireNonNull(property, "Property cannot be null");
        return property.chargeRent(renter);
    }

    public boolean handleForcedSale(Player player, double requiredAmount) {
        Objects.requireNonNull(player, "Player cannot be null");
        if (player.canAfford(requiredAmount)) {
            return true;
        }
        List<Property> properties = new ArrayList<>(player.getOwnedProperties());
        for (Property prop : properties) {
            if (player.canAfford(requiredAmount)) {
                break;
            }
            double resaleValue = prop.getResaleValue();
            prop.resetOwner();
            player.receive(resaleValue);
        }
        return player.canAfford(requiredAmount);
    }
}
