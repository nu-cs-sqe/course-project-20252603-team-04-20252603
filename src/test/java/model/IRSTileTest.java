package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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


}
