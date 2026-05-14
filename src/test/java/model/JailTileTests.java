package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JailTileTests {

    @Test
    public void Test_JailTile_Name(){
        JailTile jailTile = new JailTile();
        assert(jailTile.getName() == TileType.JAIL);
    }

    @Test
    public void Test_JailTile_LandOn_NotInJail(){
        JailTile jailTile = new JailTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(player.isActive()).andStubReturn(true);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.replay(player, game);
        jailTile.landOn(player, game);
        assertFalse(player.inJail());
        EasyMock.verify(player, game);
    }
}
