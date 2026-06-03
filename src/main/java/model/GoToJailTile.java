package model;

import util.Constants;

public class GoToJailTile implements Tile {

    @Override
    public TileType getName() {
        return TileType.GOTOJAIL;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must be non-null");
        }
        if (!player.getActive()) {
            return;
        }
        player.goToJail(Constants.JAIL_POSITION);
    }
}
