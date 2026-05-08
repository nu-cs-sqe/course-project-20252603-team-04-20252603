package model;

import java.util.Arrays;

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

    // TC2: Active player lands on GO and receives the GO bonus
    @Test
    public void Tests_LandOn_Gives_Bonus_To_Active_Player() {
        Player player = new Player("John", 1000.0);
        Player other = new Player("Jane", 1000.0);
        GameEngine game = new GameEngine(Arrays.asList(player, other));
        int positionBefore = player.getPosition();

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        assertEquals(1200.0, player.getBalance(), 0.001,
                "Active player should receive the $200 GO bonus exactly once");
        assertEquals(positionBefore, player.getPosition(),
                "GoTile.landOn must not change the player's position");
    }

    // TC3: Player with $0 balance lands on GO
    @Test
    public void Tests_LandOn_Gives_Bonus_To_Zero_Balance_Player() {
        Player player = new Player("John", 0.0);
        Player other = new Player("Jane", 1000.0);
        GameEngine game = new GameEngine(Arrays.asList(player, other));

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        assertEquals(200.0, player.getBalance(), 0.001,
                "Player with $0 balance should have $200 after landing on GO");
        assertFalse(player.isBankrupt(),
                "Player should not be bankrupt after receiving the GO bonus");
    }

    // TC4: Null player input
    @Test
    public void Tests_LandOn_Null_Player_Is_Rejected() {
        Player other = new Player("Jane", 1000.0);
        Player another = new Player("Bob", 1000.0);
        GameEngine game = new GameEngine(Arrays.asList(other, another));
        double balanceBefore = other.getBalance();

        GoTile tile = new GoTile();

        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(null, game),
                "GoTile.landOn must reject a null player");
        assertEquals(balanceBefore, other.getBalance(), 0.001,
                "Other players' balances must not change when input is rejected");
    }

    // TC5: Null game input
    @Test
    public void Tests_LandOn_Null_Game_Is_Rejected() {
        Player player = new Player("John", 1000.0);
        GoTile tile = new GoTile();

        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(player, null),
                "GoTile.landOn must reject a null game");
        assertEquals(1000.0, player.getBalance(), 0.001,
                "Player balance must not change when game is null");
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
        Player player = new Player("John", 0.0);
        Player other = new Player("Jane", 1000.0);
        GameEngine game = new GameEngine(Arrays.asList(player, other));

        assertTrue(player.isBankrupt(),
                "Pre-condition: $0-balance player is bankrupt and inactive");
        assertFalse(player.isActive(),
                "Pre-condition: bankrupt player must be inactive");

        GoTile tile = new GoTile();
        tile.landOn(player, game);

        assertEquals(0.0, player.getBalance(), 0.001,
                "Eliminated player's balance must not increase from the GO bonus");
    }
}
