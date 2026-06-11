package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class ChanceTileTests {

    private static final CardEffect NO_OP_EFFECT = (player, game) -> { };

    @Test
    public void Tests_ChanceTile_Reports_Its_Tile_Type() {
        ChanceTile chanceTile = new ChanceTile();
        assertEquals(TileType.CHANCE, chanceTile.getName());
    }


    @Test
    public void Active_Player_Lands_On_ChanceTile() {
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Deck deck = EasyMock.createMock(Deck.class);
        Card card = new Card("Chance", "Test chance card", NO_OP_EFFECT);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getChanceDeck()).andReturn(deck);
        EasyMock.expect(deck.draw()).andReturn(card);

        EasyMock.replay(player, game, deck);

        ChanceTile chanceTile = new ChanceTile();
        chanceTile.landOn(player, game);

        EasyMock.verify(player, game, deck);
    }


    @Test
    public void Null_Player_Input_Throws() {
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        ChanceTile chanceTile = new ChanceTile();

        assertThrows(NullPointerException.class, () -> chanceTile.landOn(null, game));

        EasyMock.verify(game);
    }


    @Test
    public void Null_Game_Input_Throws() {
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        ChanceTile chanceTile = new ChanceTile();

        assertThrows(NullPointerException.class, () -> chanceTile.landOn(player, null));

        EasyMock.verify(player);
    }


    @Test
    public void Null_Player_And_Game_Input_Throws() {
        ChanceTile chanceTile = new ChanceTile();

        assertThrows(NullPointerException.class, () -> chanceTile.landOn(null, null));
    }


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
