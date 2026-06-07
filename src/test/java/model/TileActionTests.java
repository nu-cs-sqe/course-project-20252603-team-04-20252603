package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileActionTests {

    @Test
    public void TC1_constructor_WithValidFields_StoresAllFields() {
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

    @Test
    public void TC2_constructor_WithNoneAndOptionalNullFields_CreatesAction() {
        TileAction action = new TileAction(TileActionType.NONE, null, null, null, 0.0);

        assertEquals(TileActionType.NONE, action.getType());
        assertNull(action.getPlayer());
        assertNull(action.getTile());
        assertNull(action.getCard());
        assertEquals(0.0, action.getAmount(), 0.001);
    }

    @Test
    public void TC3_constructor_WithCollectMoneyType_StoresTypeAndAmount() {
        Player player = EasyMock.createMock(Player.class);
        Tile tile = EasyMock.createMock(Tile.class);

        EasyMock.replay(player, tile);

        TileAction action = new TileAction(
                TileActionType.COLLECT_MONEY,
                player,
                tile,
                null,
                200.0);

        assertEquals(TileActionType.COLLECT_MONEY, action.getType());
        assertSame(player, action.getPlayer());
        assertSame(tile, action.getTile());
        assertNull(action.getCard());
        assertEquals(200.0, action.getAmount(), 0.001);

        EasyMock.verify(player, tile);
    }

    @Test
    public void TC4_constructor_WithPayBankType_StoresTypeAndAmount() {
        Player player = EasyMock.createMock(Player.class);
        Tile tile = EasyMock.createMock(Tile.class);

        TileAction action = new TileAction(
                TileActionType.PAY_BANK,
                player,
                tile,
                null,
                100.0);

        assertEquals(TileActionType.PAY_BANK, action.getType());
        assertSame(player, action.getPlayer());
        assertSame(tile, action.getTile());
        assertNull(action.getCard());
        assertEquals(100.0, action.getAmount(), 0.001);
    }

    @Test
    public void TC5_constructor_WithMovePlayerType_StoresTypeAndTargetTile() {
        Player player = EasyMock.createMock(Player.class);
        Tile tile = EasyMock.createMock(Tile.class);

        TileAction action = new TileAction(
                TileActionType.MOVE_PLAYER,
                player,
                tile,
                null,
                0.0);

        assertEquals(TileActionType.MOVE_PLAYER, action.getType());
        assertSame(player, action.getPlayer());
        assertSame(tile, action.getTile());
        assertNull(action.getCard());
        assertEquals(0.0, action.getAmount(), 0.001);
    }

    @Test
    public void TC6_constructor_WithNullType_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new TileAction(null, null, null, null, 0.0));
    }

    @Test
    public void TC7_constructor_WithNegativeAmount_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileAction(TileActionType.PAY_TAX, null, null, null, -0.01));
    }

}
