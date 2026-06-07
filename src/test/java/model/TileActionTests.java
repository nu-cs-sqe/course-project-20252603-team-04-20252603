package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TileActionTests {

    @Test
    public void constructor_WithValidFields_StoresAllFields() {
        Player player = EasyMock.createMock(Player.class);
        Tile tile = EasyMock.createMock(Tile.class);
        CardEffect effect = EasyMock.createMock(CardEffect.class);
        Card card = new Card("Chance", "No operation", effect);

        EasyMock.replay(player, tile, effect);

        TileAction action = new TileAction(
                TileActionType.DRAW_CARD,
                player,
                tile,
                card,
                100.0);

        assertEquals(TileActionType.DRAW_CARD, action.getType());
        assertSame(player, action.getPlayer());
        assertSame(tile, action.getTile());
        assertSame(card, action.getCard());
        assertEquals(100.0, action.getAmount(), 0.001);

        EasyMock.verify(player, tile, effect);
    }
}
