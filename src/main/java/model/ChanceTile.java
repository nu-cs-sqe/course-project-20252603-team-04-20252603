package model;

import java.util.Objects;

public class ChanceTile implements Tile {

   
    

    @Override
    public TileType getName() {
        return TileType.CHANCE;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(game, "GameEngine cannot be null");
        if (player.getActive()) {
            game.getChanceDeck().draw();
        }
    }

}
