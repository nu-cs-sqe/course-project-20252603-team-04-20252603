package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int BOARD_SIZE = 32;

    private final List<Tile> tiles;
    private final Map<Player, Integer> playerPositions;

    public Board(List<Tile> tiles) {
        this.tiles = tiles;
        this.playerPositions = new HashMap<>();
    }

    public void initializeBoard() {
        if (tiles.size() != BOARD_SIZE) {
            throw new IllegalStateException("Board must contain exactly 32 tiles");
        }
    }

    public Tile getTile(int boardIndex) {
        if(boardIndex < 0 || boardIndex >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException(boardIndex + " is an out of bounds index");
        }

        return tiles.get(boardIndex);
    }

    public int getPlayerPosition(Player player) {
        if (!playerPositions.containsKey(player)) {
            throw new IllegalArgumentException("Player does not have a stored board position");
        }

        return playerPositions.get(player);
    }

    public void setPlayerPosition(Player player, int boardIndex) {

        if (boardIndex < 0 || boardIndex >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException(boardIndex + " is an out of bounds index");
        }

        playerPositions.put(player, boardIndex);
    }

    public void movePlayer(Player player, int spaces) {
        if (!playerPositions.containsKey(player)) {
            throw new IllegalArgumentException("Player must be on the board before moving");
        }

        if (spaces < 2 || spaces > 12) {
            throw new IllegalArgumentException("Spaces must range from 2-12");
        }

        int currentPosition = playerPositions.get(player);
        int newPosition = (currentPosition + spaces) % BOARD_SIZE;

        playerPositions.put(player, newPosition);
    }

}
