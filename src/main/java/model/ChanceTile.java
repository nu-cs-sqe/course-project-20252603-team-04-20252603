package model;

public class ChanceTile implements Tile {

   
    

    @Override
    public TileType getName() {
        return TileType.CHANCE;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        if (player == null) {
            throw new IllegalArgumentException("player must be non-null");
        }
        if (player.getActive()) {
            game.getChanceDeck().draw();
        }
    }

}
