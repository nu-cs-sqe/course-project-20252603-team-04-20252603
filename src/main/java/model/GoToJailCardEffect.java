package model;

public class GoToJailCardEffect implements CardEffect {

    private static final int JAIL_POSITION = 8;

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
        g.setPlayerPosition(p, JAIL_POSITION);
        p.goToJail(JAIL_POSITION);
    }
}
