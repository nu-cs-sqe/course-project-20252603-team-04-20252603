package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoTileTests {

    @Test
    public void getName_WhenTileIsGoTile_ReturnsGoTile() {
        GoTile goTile = new GoTile();

        TileType expected = TileType.GO;
        TileType actual = goTile.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void landOn_WhenPlayerWithZeroBalanceLandsOnGo_IncreasesBalanceByGoReward() {
        GoTile goTile = new GoTile();
        Player player = new Player("John", 0.0);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        goTile.landOn(player, game);

        double expected = 200.0;
        double actual = player.getBalance();

        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void landOn_WhenPlayerWithStartingBalanceLandsOnGo_IncreasesBalanceByGoReward() {
        GoTile goTile = new GoTile();
        Player player = new Player("John", 1000.0);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        goTile.landOn(player, game);

        double expected = 1200.0;
        double actual = player.getBalance();

        assertEquals(expected, actual, 0.001);
    }

}
