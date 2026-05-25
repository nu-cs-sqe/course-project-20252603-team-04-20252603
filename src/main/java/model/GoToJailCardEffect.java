package model;

import util.Constants;

public class GoToJailCardEffect implements CardEffect {

    @Override
    public void apply(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        player.goToJail(Constants.JAIL_POSITION);
    }
}
