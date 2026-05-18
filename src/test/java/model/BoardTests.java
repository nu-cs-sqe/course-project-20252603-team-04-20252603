package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private final int NORMAL_TILE_SIZE = 32;

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
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE - 1);
        Board board = new Board(tiles);

        assertThrows(IllegalStateException.class, board::initializeBoard);
    }

    @Test
    public void initializeBoard_With32Tiles_InitializesBoard() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);

        assertDoesNotThrow(board::initializeBoard);
    }

    @Test
    public void initializeBoard_With33Tiles_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE + 1);
        Board board = new Board(tiles);

        assertThrows(IllegalStateException.class, board::initializeBoard);
    }

    @Test
    public void getTile_WithNegativeIndex_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        assertThrows(IndexOutOfBoundsException.class, () -> board.getTile(-1));
    }

    @Test
    public void getTile_WithFirstIndex_ReturnsFirstTile() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Tile expected = tiles.get(0);
        Tile actual = board.getTile(0);

        assertSame(expected, actual);
    }

    @Test
    public void getTile_WithLastIndex_ReturnsLastTile() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Tile expected = tiles.get(31);
        Tile actual = board.getTile(31);

        assertSame(expected, actual);
    }

    @Test
    public void getTile_WithIndexEqualToBoardSize_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        assertThrows(IndexOutOfBoundsException.class, () -> board.getTile(32));
    }

    @Test
    public void getPlayerPosition_WhenPlayerNotOnBoard_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);

        assertThrows(IllegalArgumentException.class, () -> board.getPlayerPosition(player));
    }

    @Test
    public void getPlayerPosition_WhenPlayerAtFirstIndex_ReturnsZero() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 0);

        int expected = 0;
        int actual = board.getPlayerPosition(player);

        assertEquals(expected, actual);
    }

    @Test
    public void getPlayerPosition_WhenPlayerAtLastIndex_Returns31() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 31);

        int expected = 31;
        int actual = board.getPlayerPosition(player);

        assertEquals(expected, actual);
    }

    @Test
    public void movePlayer_WhenPlayerNotOnBoard_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);

        assertThrows(IllegalArgumentException.class, () -> board.movePlayer(player, 2));
    }

    @Test
    public void movePlayer_WithOneLessThanMinimumDiceRoll_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 0);

        assertThrows(IllegalArgumentException.class, () -> board.movePlayer(player, 1));
    }

    @Test
    public void movePlayer_WithMinimumDiceRoll_MovesTwoSpaces() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 30);

        board.movePlayer(player, 2);

        int expected = 0;
        int actual = board.getPlayerPosition(player);

        assertEquals(expected, actual);
    }

    @Test
    public void movePlayer_WithMaximumDiceRoll_MovesTwelveSpaces() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 0);

        board.movePlayer(player, 12);

        int expected = 12;
        int actual = board.getPlayerPosition(player);

        assertEquals(expected, actual);
    }

    @Test
    public void movePlayer_WithOneMoreThanMaximumDiceRoll_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 0);

        assertThrows(IllegalArgumentException.class, () -> board.movePlayer(player, 13));
    }

    @Test
    public void movePlayer_WithMaximumDiceRollNearEnd_WrapsAroundBoard() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);
        board.setPlayerPosition(player, 31);

        board.movePlayer(player, 12);

        int expected = 11;
        int actual = board.getPlayerPosition(player);

        assertEquals(expected, actual);
    }

    @Test
    public void setPlayerPosition_WithOneLessThanFirstIndex_ThrowsException() {
        List<Tile> tiles = createTiles(NORMAL_TILE_SIZE);
        Board board = new Board(tiles);
        board.initializeBoard();

        Player player = new Player("John", 1000.0);

        assertThrows(IndexOutOfBoundsException.class, () -> board.setPlayerPosition(player, -1));
    }

}
