package model;

import org.junit.jupiter.api.Test;

public class JailTileTests {

    @Test
    public void Test_JailTile_Name(){
        JailTile jailTile = new JailTile();
        assert(jailTile.getName() == TileType.JAIL);
    }
}
