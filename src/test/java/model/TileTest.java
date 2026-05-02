package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TileTest {

    @Test
    void getName_ReturnsNormalValidTileName() {
        Tile tile = new TestTile("GO");
        assertEquals("GO", tile.getName());
    }

    @Test
    void getName_ReturnsShortestValidTileName() {
        Tile tile = new TestTile("A");
        assertEquals("A", tile.getName());
    }

    @Test
    void getName_RejectsEmptyTileName() {
        assertThrows(IllegalArgumentException.class, () -> new TestTile(""));
    }
}
