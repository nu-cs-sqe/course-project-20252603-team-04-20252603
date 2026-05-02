package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    @Test
    void getName_ReturnsNormalValidTileName() {
        Tile tile = new TestTile("Test");
        assertEquals("Test", tile.getName());
    }
}
