package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class SubscriptionServiceCardEffectTests {


    @Test
    public void apply_OnNullPlayer_ThrowsIllegalArgumentException() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void apply_OnNullGame_ThrowsIllegalArgumentException() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void apply_OnBothNull_ThrowsIllegalArgumentException() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, null));
    }


    @Test
    public void apply_OnPlayerBalanceGreaterThan100_DeductsFee() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerBalanceExactly100_DeductsFeeAndRemainsActive() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerBalance99WithNoProperties_RemovesFromGame() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(false);
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerBalanceZeroWithNoProperties_RemovesFromGame() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(false);
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }


    @Test
    public void apply_OnEliminatedPlayer_ThrowsIllegalArgumentException() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(player, game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, game));

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPaymentCausingGameOver_RemovesPlayerFromGame() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(false);
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

}
