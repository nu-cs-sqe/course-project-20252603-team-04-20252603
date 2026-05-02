package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TileTest {

    @Test
    void getName_ReturnsValidTileNameGO() {
        Tile tile = new TestTile(TileType.GO);

        assertEquals(TileType.GO, tile.getName());
    }



}
