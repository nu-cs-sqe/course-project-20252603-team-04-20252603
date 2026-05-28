package model;

import java.util.Objects;

public class GoTile implements Tile {

    private static final double GO_REWARD = 200.0;

    @Override
    public TileType getName() {
        return TileType.GO;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        Objects.requireNonNull(player, "Player cannot be null");
        player.receive(GO_REWARD);
    }

}
