package model;

import util.Constants;

import java.util.Objects;

public class GoTile implements Tile {

    @Override
    public TileType getName() {
        return TileType.GO;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(game, "GameEngine cannot be null");
        player.receive(Constants.GO_BONUS);
    }

}
