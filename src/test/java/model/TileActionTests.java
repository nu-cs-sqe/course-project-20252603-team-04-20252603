package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TileActionTests {

    private static final CardEffect NO_OP_EFFECT = (player, game) -> { };

    @Test
    public void constructor_WithValidFields_StoresAllFields() {
        Player player = new Player("Alice", 1000.0);
        Tile tile = new FreeParking();
        Card card = new Card("Chance", "No operation", NO_OP_EFFECT);

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
    }




}
