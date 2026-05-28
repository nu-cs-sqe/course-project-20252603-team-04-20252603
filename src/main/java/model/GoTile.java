package model;

public class GoTile implements Tile {

    @Override
    public TileType getName() {
        return TileType.GO;
    }

    @Override
    public void landOn(Player player, GameEngine game) {

    }

}
