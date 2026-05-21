package model;

public class GoBackThreeSpacesCardEffect implements CardEffect {

    private static final int BOARD_SIZE = 32;
    private static final int SPACES_BACK = 3;

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
        int currentPosition = g.getPlayerPosition(p);
        int newPosition = ((currentPosition - SPACES_BACK) % BOARD_SIZE + BOARD_SIZE) % BOARD_SIZE;
        g.setPlayerPosition(p, newPosition);
    }
}
