package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoTileTests {

    @Test
    public void getName_WhenTileIsGoTile_ReturnsGoTile() {
        GoTile goTile = new GoTile();

        TileType expected = TileType.GO;
        TileType actual = goTile.getName();

        assertEquals(expected, actual);
    }

}
