package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
