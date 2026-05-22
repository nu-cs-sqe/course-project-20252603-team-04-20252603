package model;

public class GoBackThreeSpacesCardEffect implements CardEffect {

    private static final int BOARD_SIZE = 32;
    private static final int SPACES_BACK = 3;

    @Override
    public void apply(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        int currentPosition = game.getPlayerPosition(player);
        int newPosition = ((currentPosition - SPACES_BACK) % BOARD_SIZE + BOARD_SIZE) % BOARD_SIZE;
        game.setPlayerPosition(player, newPosition);
    }
}
