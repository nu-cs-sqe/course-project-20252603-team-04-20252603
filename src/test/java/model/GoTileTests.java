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
}
