package model;

import org.easymock.EasyMock;
import org.easymock.internal.matchers.Null;
import org.junit.jupiter.api.Test;

import util.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JailTileTests {

    @Test
    public void TC1_JailTile_Name(){
        JailTile jailTile = new JailTile();
        assertEquals(jailTile.getName(),TileType.JAIL);
    }

    @Test
    public void TC2_JailTile_LandOn_NotInJail(){
        JailTile jailTile = new JailTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andStubReturn(true);
        EasyMock.expect(player.inJail()).andStubReturn(false);
        EasyMock.expect(player.getPosition()).andStubReturn(Constants.JAIL_POSITION);
        EasyMock.expect(player.getBalance()).andStubReturn(1000.0);

        EasyMock.replay(player, game);

        jailTile.landOn(player, game);
        assertTrue(player.getActive(), "Player should remain active");
        assertFalse(player.inJail(), "Landing on JailTile should not send player to jail");
        assertEquals(Constants.JAIL_POSITION, player.getPosition(), "JailTile should not move the player");
        assertEquals(1000.0, player.getBalance(), "JailTile should not change balance");

        EasyMock.verify(player, game);

    }

    @Test
    public void TC3_JailTile_LandOn_InJail(){
        // Active player already in jail lands/remains on jail
        JailTile jailTile = new JailTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andStubReturn(true);
        EasyMock.expect(player.inJail()).andStubReturn(true);
        EasyMock.expect(player.getPosition()).andStubReturn(Constants.JAIL_POSITION);
        EasyMock.expect(player.getBalance()).andStubReturn(1000.0);

        EasyMock.replay(player, game);

        jailTile.landOn(player, game);

        assertTrue(player.getActive(), "Player should remain active");
        assertTrue(player.inJail(), "JailTile should not release a jailed player");
        assertEquals(Constants.JAIL_POSITION, player.getPosition(), "JailTile should not move the player");
        assertEquals(1000.0, player.getBalance(), "JailTile should not change balance");

        EasyMock.verify(player, game);
    }

    @Test
    public void TC4_JailTile_LandOn_NullPlayer_Invalid(){
        // rejects invalid input with IllegalArgumentException
        JailTile jailTile = new JailTile();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(NullPointerException.class,
                () -> jailTile.landOn(null, game),
                "Player cannot be null");

        EasyMock.verify(game);
    }

    @Test
    public void TC5_JailTile_LandOn_NullGame_Invalid() {
        JailTile jailTile = new JailTile();
        Player player = EasyMock.createMock(Player.class);

        EasyMock.replay(player);

        assertThrows(NullPointerException.class,
                () -> jailTile.landOn(player, null),
                "GameEngine cannot be null");

        EasyMock.verify(player);
    }



    @Test
    public void TC7_JailTile_LandOn_InactivePlayer_NoEffect() {
        JailTile jailTile = new JailTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andStubReturn(false);
        EasyMock.expect(player.inJail()).andStubReturn(false);
        EasyMock.expect(player.getPosition()).andStubReturn(Constants.JAIL_POSITION);
        EasyMock.expect(player.getBalance()).andStubReturn(0.0);

        EasyMock.replay(player, game);

        jailTile.landOn(player, game);

        assertFalse(player.getActive(), "Setup check: player should be inactive");
        assertFalse(player.inJail(), "JailTile should not change jail state");
        assertEquals(Constants.JAIL_POSITION, player.getPosition(), "JailTile should not move inactive player");
        assertEquals(0.0, player.getBalance(), "JailTile should not change inactive player's balance");

        EasyMock.verify(player, game);
    }

    @Test
    public void TC8_JailTile_DoesNotMovePlayer() {
        JailTile jailTile = new JailTile();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getPosition())
                .andReturn(Constants.JAIL_POSITION)
                .times(2);

        EasyMock.replay(player, game);

        int startingPosition = player.getPosition();

        jailTile.landOn(player, game);

        assertEquals(startingPosition, player.getPosition(),
                "JailTile should not move the player");

        EasyMock.verify(player, game);

    }

}
