package model;

public class GoToJailTile implements Tile {

    @Override
    public TileType getName() {
        return TileType.GOTOJAIL;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
    }
}
