package model;

public interface Tile {
    TileType getName();
    void landOn(Player player, GameEngine game);
}
