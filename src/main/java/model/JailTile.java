package model;

public class JailTile implements Tile{
    @Override
    public TileType getName() {
        return TileType.JAIL;
    }
    @Override
    public void landOn(Player player, GameEngine engine) {

    }
}
