package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoBackThreeSpacesCardEffectTests {


    @Test
    public void apply_OnNullPlayer_ThrowsIllegalArgumentException() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void apply_OnNullGame_ThrowsIllegalArgumentException() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void apply_OnBothNull_ThrowsIllegalArgumentException() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, null));
    }


    @Test
    public void apply_OnPlayerAtPosition5_MovesBackToPosition2() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getPlayerPosition(player)).andReturn(5);
        game.setPlayerPosition(player, 2);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition3_MovesBackToGoWithoutBonus() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getPlayerPosition(player)).andReturn(3);
        game.setPlayerPosition(player, 0);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition2_WrapsToPosition31() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getPlayerPosition(player)).andReturn(2);
        game.setPlayerPosition(player, 31);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition1_WrapsToPosition30() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getPlayerPosition(player)).andReturn(1);
        game.setPlayerPosition(player, 30);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerAtPosition0_WrapsToPosition29() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(game.getPlayerPosition(player)).andReturn(0);
        game.setPlayerPosition(player, 29);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }


    @Test
    public void apply_OnEliminatedPlayer_ThrowsIllegalArgumentException() {
        GoBackThreeSpacesCardEffect effect = new GoBackThreeSpacesCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(player, game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, game));

        EasyMock.verify(player, game);
    }
}
