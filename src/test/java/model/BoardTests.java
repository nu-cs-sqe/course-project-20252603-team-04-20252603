package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTests {

    private List<Tile> createTiles(int numberOfTiles) {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < numberOfTiles; i++) {
            Tile tile = EasyMock.createMock(Tile.class);
            EasyMock.replay(tile);
            tiles.add(tile);
        }

        return tiles;
    }

    @Test
    public void initializeBoard_With31Tiles_ThrowsException() {
        List<Tile> tiles = createTiles(31);
        Board board = new Board(tiles);

        assertThrows(IllegalStateException.class, board::initializeBoard);
    }

    @Test
    public void initializeBoard_With32Tiles_InitializesBoard() {
        List<Tile> tiles = createTiles(32);
        Board board = new Board(tiles);

        assertDoesNotThrow(board::initializeBoard);
    }

    @Test
    public void initializeBoard_With33Tiles_ThrowsException() {
        List<Tile> tiles = createTiles(33);
        Board board = new Board(tiles);

        assertThrows(IllegalStateException.class, board::initializeBoard);
    }

    @Test
    public void getTile_WithNegativeIndex_ThrowsException() {
        List<Tile> tiles = createTiles(32);
        Board board = new Board(tiles);
        board.initializeBoard();

        assertThrows(IndexOutOfBoundsException.class, () -> board.getTile(-1));
    }
}
