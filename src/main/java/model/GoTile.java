package model;

public class GoTile implements Tile {

    private static final double GO_REWARD = 200.0;

    @Override
    public TileType getName() {
        return TileType.GO;
    }

    @Override
    public void landOn(Player player, GameEngine game) {
        player.receive(GO_REWARD);
    }

}
