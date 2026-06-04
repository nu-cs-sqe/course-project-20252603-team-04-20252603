package controller;

import model.Player;
import model.Property;

public class PropertyController {

    public boolean promptPurchase(Player player, Property property) {
        if (player == null || property == null) {
            throw new IllegalArgumentException("Player and property cannot be null");
        }
        return false;
    }
}
