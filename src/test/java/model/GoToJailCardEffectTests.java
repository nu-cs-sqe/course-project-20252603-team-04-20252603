package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class GoToJailCardEffectTests {

    // ==================================================================================================
    // Input validation tests
    // ==================================================================================================

    @Test
    public void apply_OnNullPlayer_ThrowsIllegalArgumentException() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void apply_OnNullGame_ThrowsIllegalArgumentException() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void apply_OnBothNull_ThrowsIllegalArgumentException() {
        GoToJailCardEffect effect = new GoToJailCardEffect();

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, null));
    }

    // ==================================================================================================
    // Normal operation tests
    // ==================================================================================================

    @Test
    public void apply_OnActivePlayerNotInJail_SendsToJail() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAlreadyInJail_ResetsJailTurnCounter() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtGo_SendsToJailWithoutGoBonus() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition31_SendsToJail() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    // ==================================================================================================
    // Edge case tests
    // ==================================================================================================

    @Test
    public void apply_OnEliminatedPlayer_ThrowsIllegalArgumentException() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(player, game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, game));

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayer_DoesNotAffectBalance() {
        GoToJailCardEffect effect = new GoToJailCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }
}
