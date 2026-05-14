package model;

import java.util.List;

public class Board {

    public Board(List<Tile> tiles) {

    }

    public void initializeBoard() {
        throw new IllegalStateException("Board must contain exactly 32 tiles");
    }
}
