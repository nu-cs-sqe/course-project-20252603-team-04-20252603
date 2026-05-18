package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class ChanceTileTests {

    @Test
    public void Tests_ChanceTile_Reports_Its_Tile_Type() {
        ChanceTile chanceTile = new ChanceTile();
        assertEquals(TileType.CHANCE, chanceTile.getName());
    }

    // TC2: Active player lands on ChanceTile

    @Test
    public void Active_Player_Lands_On_ChanceTile() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Deck deck = EasyMock.createMock(Deck.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getChanceDeck()).andReturn(deck);
        deck.draw();
        EasyMock.expectLastCall();

        EasyMock.replay(player, game, deck);

        ChanceTile chanceTile = new ChanceTile();
        chanceTile.landOn(player, game);

        EasyMock.verify(player, game, deck);
    }

    // TC3: Null player input

    @Test
    public void Null_Player_Input_Throws() {
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        ChanceTile chanceTile = new ChanceTile();

        assertThrows(IllegalArgumentException.class, () -> chanceTile.landOn(null, game));

        EasyMock.verify(game);
    }

    // TC4: Null game input

    @Test
    public void Null_Game_Input_Throws() {
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        ChanceTile chanceTile = new ChanceTile();

        assertThrows(IllegalArgumentException.class, () -> chanceTile.landOn(player, null));

        EasyMock.verify(player);
    }

    // TC5: Both player and game null

    @Test
    public void Null_Player_And_Game_Input_Throws() {
        ChanceTile chanceTile = new ChanceTile();

        assertThrows(IllegalArgumentException.class, () -> chanceTile.landOn(null, null));
    }

    // TC6: Inactive player lands on ChanceTile

    @Test
    public void Inactive_Player_Lands_On_ChanceTile_Does_Nothing() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(false);

        EasyMock.replay(player, game);

        ChanceTile chanceTile = new ChanceTile();
        chanceTile.landOn(player, game);

        EasyMock.verify(player, game);
    }
}
