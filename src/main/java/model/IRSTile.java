package model;
import util.Constants;

import java.util.Objects;

public class IRSTile implements Tile{
    @Override
    public TileType getName() {
        return TileType.IRS;
    }
    @Override
    public void landOn(Player player, GameEngine game) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(game, "GameEngine cannot be null");

        boolean paid = player.remove(Constants.GO_BONUS);
        if (!paid) {
            game.removeBankruptPlayer(player);
        }
    }
}
