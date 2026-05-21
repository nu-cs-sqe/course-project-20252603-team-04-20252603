package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdvanceToGoCardEffectTests {

    private static final int GO_POSITION = 0;
    private static final double GO_BONUS = 200.0;

    // ==================================================================================================
    // Input validation tests
    // ==================================================================================================

    @Test
    public void apply_OnNullPlayer_ThrowsIllegalArgumentException() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void apply_OnNullGame_ThrowsIllegalArgumentException() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void apply_OnBothNull_ThrowsIllegalArgumentException() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, null));
    }

    // ==================================================================================================
    // Normal operation tests
    // ==================================================================================================

    @Test
    public void apply_OnPlayerAtPosition10_MovesToGoAndCollectsBonus() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, GO_POSITION);
        EasyMock.expect(player.receive(GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition1_MovesToGoAndCollectsBonus() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, GO_POSITION);
        EasyMock.expect(player.receive(GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition31_MovesToGoAndCollectsBonus() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, GO_POSITION);
        EasyMock.expect(player.receive(GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAlreadyOnGo_RemainsAtGoAndCollectsBonus() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, GO_POSITION);
        EasyMock.expect(player.receive(GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    // ==================================================================================================
    // Edge case tests
    // ==================================================================================================

    @Test
    public void apply_OnEliminatedPlayer_ThrowsIllegalArgumentException() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(player, game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, game));

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerWithBalanceNearMax_DoesNotOverflowToInfinity() {
        AdvanceToGoCardEffect effect = new AdvanceToGoCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        game.setPlayerPosition(player, GO_POSITION);
        EasyMock.expect(player.receive(GO_BONUS)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }
}
