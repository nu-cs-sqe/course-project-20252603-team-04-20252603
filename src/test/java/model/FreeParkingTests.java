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

    


    


    
}
