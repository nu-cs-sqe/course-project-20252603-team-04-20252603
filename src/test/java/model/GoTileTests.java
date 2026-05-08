package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoTileTests {

    // ==================================================================================================
    // Test suite for the GoTile class.
    // ==================================================================================================

    // TC1: GoTile reports its tile type
    @Test
    public void Tests_GetName_Returns_GO() {
        GoTile tile = new GoTile();

        assertEquals(TileType.GO, tile.getName(), "GoTile.getName() should return TileType.GO");
    }
}
