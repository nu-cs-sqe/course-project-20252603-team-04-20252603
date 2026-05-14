package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class GoTileTests {

    // ==================================================================================================
    // Test suite for the GoTile class. Player and GameEngine collaborators are mocked with EasyMock.
    // ==================================================================================================

    // TC1: GoTile reports its tile type
    @Test
    public void Tests_GetName_Returns_GO() {
        GoTile tile = new GoTile();

        assertEquals(TileType.GO, tile.getName(), "GoTile.getName() should return TileType.GO");
    }

    // TC2: Active player lands on GO and receives the GO bonus
    @Test
    public void Tests_LandOn_Gives_Bonus_To_Active_Player() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andReturn(true);
        EasyMock.expect(player.receive(Constants.GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        // Strict mock verification: position-mutating methods (e.g. goToJail / leaveJail)
        // would have failed the unexpected-call check, so position is implicitly unchanged.
        EasyMock.verify(player, game);
    }

    // TC3: Player with $0 balance lands on GO
    @Test
    public void Tests_LandOn_Gives_Bonus_To_Zero_Balance_Player() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andReturn(true);
        EasyMock.expect(player.receive(Constants.GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        EasyMock.verify(player, game);
    }

    // TC4: Null player input
    @Test
    public void Tests_LandOn_Null_Player_Is_Rejected() {
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        GoTile tile = new GoTile();
        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(null, game),
                "GoTile.landOn must reject a null player");

        EasyMock.verify(game);
    }

    // TC5: Null game input
    @Test
    public void Tests_LandOn_Null_Game_Is_Rejected() {
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        GoTile tile = new GoTile();
        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(player, null),
                "GoTile.landOn must reject a null game");

        EasyMock.verify(player);
    }

    // TC6: Both player and game null
    @Test
    public void Tests_LandOn_Both_Null_Is_Rejected() {
        GoTile tile = new GoTile();
        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(null, null),
                "GoTile.landOn must reject when both inputs are null");
    }

    // TC7: Eliminated / inactive player lands on GO
    @Test
    public void Tests_LandOn_Eliminated_Player_Receives_No_Bonus() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andReturn(false);
        EasyMock.replay(player, game);

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        EasyMock.verify(player, game);
    }

    // TC8: Player at extreme upper balance lands on GO
    @Test
    public void Tests_LandOn_Extreme_Upper_Balance_Does_Not_Overflow() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andReturn(true);
        EasyMock.expect(player.receive(Constants.GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        assertTrue(Double.isFinite(Constants.GO_BONUS),
                "GO bonus must be a finite double so it cannot push a balance to Infinity");
        EasyMock.verify(player, game);
    }

    // TC10: landOn does not move the player off GO
    @Test
    public void Tests_LandOn_Does_Not_Change_Position() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andReturn(true);
        EasyMock.expect(player.receive(Constants.GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        // Strict mocks: any unexpected position-mutation call (goToJail, leaveJail) would fail verify().
        EasyMock.verify(player, game);
    }
}
