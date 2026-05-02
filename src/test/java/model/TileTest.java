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

    @Test
    void getName_ReturnsValidTileNamePROPERTY() {
        Tile tile = new TestTile(TileType.PROPERTY);

        assertEquals(TileType.PROPERTY, tile.getName());
    }

    @Test
    void getName_ReturnsValidTileNameIRS() {
        Tile tile = new TestTile(TileType.IRS);

        assertEquals(TileType.IRS, tile.getName());
    }

    @Test
    void getName_ReturnsValidTileNameCHANCE() {
        Tile tile = new TestTile(TileType.CHANCE);

        assertEquals(TileType.CHANCE, tile.getName());
    }



}
