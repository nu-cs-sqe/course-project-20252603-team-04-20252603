package model;

import util.Constants;

public class AIBubblePopCardEffect implements CardEffect {

    @Override
    public void apply(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        if (player.canAfford(Constants.AI_BUBBLE_POP_FEE)) {
            player.remove(Constants.AI_BUBBLE_POP_FEE);
        } else {
            game.removeBankruptPlayer(player);
        }
    }
}
