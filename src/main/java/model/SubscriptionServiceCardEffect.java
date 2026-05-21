package model;

import util.Constants;

public class SubscriptionServiceCardEffect implements CardEffect {

    @Override
    public void apply(Object player, Object game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        Player p = (Player) player;
        GameEngine g = (GameEngine) game;
        if (!p.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        if (p.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)) {
            p.remove(Constants.SUBSCRIPTION_SERVICE_FEE);
        } else if (p.getOwnedProperties().isEmpty()) {
            g.removeBankruptPlayer(p);
        }
    }
}
