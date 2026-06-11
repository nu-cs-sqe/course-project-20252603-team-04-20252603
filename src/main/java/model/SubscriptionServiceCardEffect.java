package model;

import util.Constants;

public class SubscriptionServiceCardEffect implements CardEffect {

    @Override
    public void apply(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        if (player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)) {
            player.remove(Constants.SUBSCRIPTION_SERVICE_FEE);
        } else {
            game.removeBankruptPlayer(player);
        }
    }
}
