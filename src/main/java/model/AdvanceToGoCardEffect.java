package model;

public class AdvanceToGoCardEffect implements CardEffect {

    private static final int GO_POSITION = 0;
    private static final double GO_BONUS = 200.0;

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
        g.setPlayerPosition(p, GO_POSITION);
        p.receive(GO_BONUS);
    }
}
