package model;

import util.Constants;

public class AdvanceToGoCardEffect implements CardEffect {

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
        g.setPlayerPosition(p, Constants.GO_POSITION);
        p.receive(Constants.GO_BONUS);
    }
}
