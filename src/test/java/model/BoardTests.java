package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class BoardTests {

    private List<Tile> createTiles(int numberOfTiles) {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < numberOfTiles; i++) {
            Tile tile = EasyMock.createMock(Tile.class);
            EasyMock.replay(tile);
            tiles.add(tile);
        }

        return tiles;
    }

}
