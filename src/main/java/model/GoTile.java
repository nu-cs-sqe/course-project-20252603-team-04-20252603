package model;

import util.Constants;

public class GoTile implements Tile {

    @Override
    public TileType getName() {
        return TileType.GO;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must be non-null");
        }
        player.receive(Constants.GO_BONUS);
    }
}
