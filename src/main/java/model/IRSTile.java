package model;

public class IRSTile implements Tile{
    @Override
    public TileType getName() {
        return TileType.IRS;
    }
    @Override
    public void landOn(Player player, GameEngine game) {
       if (player == null) {
           throw new NullPointerException("Invalid Player or GameEngine");
       }
        player.remove(200);
    }
}
