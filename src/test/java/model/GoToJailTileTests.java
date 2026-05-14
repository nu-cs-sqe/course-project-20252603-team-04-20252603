package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

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

    // TC2: Active player lands on GoToJailTile
    @Test
    public void Tests_LandOn_Sends_Active_Player_To_Jail() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andReturn(true);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        GoToJailTile tile = new GoToJailTile();
        tile.landOn(player, game);

        EasyMock.verify(player, game);
    }

    // TC3: Null player input
    @Test
    public void Tests_LandOn_Null_Player_Is_Rejected() {
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        GoToJailTile tile = new GoToJailTile();
        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(null, game),
                "GoToJailTile.landOn must reject a null player");

        EasyMock.verify(game);
    }
}
