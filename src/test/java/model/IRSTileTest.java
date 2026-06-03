package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IRSTileTest {

    @Test
    public void TC1_getName_Returns_IRSTile_Type() {
        IRSTile irsTile = new IRSTile();
        assertEquals(irsTile.getName(), TileType.IRS);
    }

    @Test
    public void TC2_getName_consistentlyReturns_IRSTile_Type() {
        IRSTile irsTile = new IRSTile();
        assertEquals(irsTile.getName(), TileType.IRS);
        assertEquals(irsTile.getName(), TileType.IRS);
    }

    @Test
    public void TC3_onLand_paymentSucceeds() {
        IRSTile irsTile = new IRSTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.remove(200)).andReturn(true);
        EasyMock.replay(player, game);
        irsTile.landOn(player, game);
        EasyMock.verify(player, game);
    }

    @Test
    public void TC4_onLand_paymentFails() {
        IRSTile irsTile = new IRSTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.remove(200)).andReturn(false);
        game.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
        EasyMock.replay(player, game);
        irsTile.landOn(player, game);
        EasyMock.verify(player, game);
    }

    @Test
    public void TC5_nullPlayer_ThrowsNullPointerException() {
        IRSTile irsTile = new IRSTile();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        assertThrows(NullPointerException.class, () -> irsTile.landOn(null, game));
    }

    @Test
    public void TC6_nullGame_PaymentSucceeds() {
        IRSTile irsTile = new IRSTile();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);
        assertThrows(NullPointerException.class, () -> irsTile.landOn(player, null));
    }

    @Test
    public void TC7_nullGame_PaymentFails_ThrowsNullPointerException() {
        IRSTile irsTile = new IRSTile();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);
        assertThrows(NullPointerException.class, () -> irsTile.landOn(player, null), "GameEngine cannot be null");
    }



}
