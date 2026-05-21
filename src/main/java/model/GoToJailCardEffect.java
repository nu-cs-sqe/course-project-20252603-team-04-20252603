package model;

import util.Constants;

public class GoToJailCardEffect implements CardEffect {

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
        g.setPlayerPosition(p, Constants.JAIL_POSITION);
        p.goToJail(Constants.JAIL_POSITION);
    }
}
