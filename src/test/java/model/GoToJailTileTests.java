package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoToJailTileTests {

    // ==================================================================================================
    // Test suite for the GoToJailTile class. Player and GameEngine collaborators are mocked with EasyMock.
    // ==================================================================================================

    // TC1: GoToJailTile reports its tile type
    @Test
    public void Tests_GetName_Returns_GOTOJAIL() {
        GoToJailTile tile = new GoToJailTile();

        assertEquals(TileType.GOTOJAIL, tile.getName(),
                "GoToJailTile.getName() should return TileType.GOTOJAIL");
    }
}
