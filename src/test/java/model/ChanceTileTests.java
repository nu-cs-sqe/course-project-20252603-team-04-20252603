package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ChanceTileTests {

    //TC1: ChanceTile reports its tile type

    @Test
    public void Tests_ChanceTile_Reports_Its_Tile_Type() {
        ChanceTile chanceTile = new ChanceTile();
        assertEquals(TileType.CHANCE, chanceTile.getName());
    }

}