package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void TC3_player_landOn_withBalance_GreaterThanTaxAmount() {
        IRSTile irsTile = new IRSTile();
        Player player = new Player("Alice", 201.0);
        // previous balance
        double previousBalance = player.getBalance();
        GameEngine game = new GameEngine(java.util.List.of(player));
        irsTile.landOn(player, game);
        assertEquals(previousBalance - 200, player.getBalance());

    }





}
