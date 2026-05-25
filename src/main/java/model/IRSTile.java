package model;

public class IRSTile implements Tile{
    @Override
    public TileType getName() {
        return TileType.IRS;
    }
    @Override
    public void landOn(Player player, GameEngine game) {

        boolean paid = player.remove(200);
        if (!paid) {
            game.removeBankruptPlayer(player);
        }
    }
}
