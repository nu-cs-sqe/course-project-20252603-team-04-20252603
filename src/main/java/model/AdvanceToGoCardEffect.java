package model;

import util.Constants;

public class AdvanceToGoCardEffect implements CardEffect {

    @Override
    public void apply(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        game.setPlayerPosition(player, Constants.GO_POSITION);
        player.receive(Constants.GO_BONUS);
    }
}
