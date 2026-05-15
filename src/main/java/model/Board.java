package model;

import java.util.List;

public class Board {

    private static final int BOARD_SIZE = 32;

    private final List<Tile> tiles;

    public Board(List<Tile> tiles) {
        this.tiles = tiles;
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
}
