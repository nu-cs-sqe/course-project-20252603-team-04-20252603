package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class FreeParkingTests {

    @Test
    public void TC1_GetName_Returns_Free() {
        FreeParking tile = new FreeParking();

        assertEquals(TileType.FREE, tile.getName(), "FreeParking should report TileType.FREE");
    }


    @Test 
    public void TC2_GetName_Is_Not_Null() {
        FreeParking tile = new FreeParking();
        assertEquals(false, tile.getName() == null, "FreeParking name should never be null");
    }

    @Test
    public void TC3_LandOn_With_Valid_Player_No_Effect() {
        Player player = new Player("Alice", 200.0);
        Player other = new Player("Bob", 200.0);
        GameEngine game = new GameEngine(Arrays.asList(player, other));
        FreeParking tile = new FreeParking();
        double startingBalance = player.getBalance();

        tile.landOn(player, game);

        assertEquals(startingBalance, player.getBalance(), 0.001, "FreeParking should not change balance");
    }

    @Test
    public void TC4_LandOn_With_Null_Player_Throws() {
        Player player = new Player("Alice", 200.0);
        Player other = new Player("Bob", 200.0);
        GameEngine game = new GameEngine(Arrays.asList(player, other));
        FreeParking tile = new FreeParking();

        assertThrows(NullPointerException.class, () -> tile.landOn(null, game));
    }

    @Test
    public void TC5_LandOn_With_Null_Game_Throws() {
        Player player = new Player("Alice", 200.0);
        FreeParking tile = new FreeParking();

        assertThrows(NullPointerException.class, () -> tile.landOn(player, null));
    }


    


    


    
}
