package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class GoToJailTileTests {


    @Test
    public void Tests_GetName_Returns_GOTOJAIL() {
        GoToJailTile tile = new GoToJailTile();

        assertEquals(TileType.GOTOJAIL, tile.getName(),
                "GoToJailTile.getName() should return TileType.GOTOJAIL");
    }

    @Test
    public void Tests_LandOn_Sends_Active_Player_To_Jail() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        GoToJailTile tile = new GoToJailTile();
        tile.landOn(player, game);

        EasyMock.verify(player, game);
    }

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

    @Test
    public void Tests_LandOn_Null_Game_Is_Rejected() {
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        GoToJailTile tile = new GoToJailTile();
        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(player, null),
                "GoToJailTile.landOn must reject a null game");

        EasyMock.verify(player);
    }

    @Test
    public void Tests_LandOn_Both_Null_Is_Rejected() {
        GoToJailTile tile = new GoToJailTile();
        assertThrows(IllegalArgumentException.class,
                () -> tile.landOn(null, null),
                "GoToJailTile.landOn must reject when both inputs are null");
    }
    @Test
    public void Tests_LandOn_Eliminated_Player_Is_Not_Sent_To_Jail() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(player, game);

        GoToJailTile tile = new GoToJailTile();
        tile.landOn(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void Tests_LandOn_Does_Not_Modify_Balance() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        GoToJailTile tile = new GoToJailTile();
        tile.landOn(player, game);

        // Strict mocks: any unexpected call to receive/buy/sell would fail verify().
        EasyMock.verify(player, game);
    }
}
