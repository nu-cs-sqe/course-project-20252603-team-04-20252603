package model;

import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class SubscriptionServiceCardEffectTests {

    // ==================================================================================================
    // Input validation tests (TC1 - TC3)
    // ==================================================================================================

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

    // ==================================================================================================
    // Normal operation tests (TC4 - TC7)
    // ==================================================================================================

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
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
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
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    // ==================================================================================================
    // Edge case tests (TC8 - TC10)
    // ==================================================================================================

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
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerCannotAffordButHasProperties_DoesNotRemoveFromGame() {
        SubscriptionServiceCardEffect effect = new SubscriptionServiceCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Property property = EasyMock.createMock(Property.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.SUBSCRIPTION_SERVICE_FEE)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.singleton(property));
        EasyMock.replay(player, game, property);

        effect.apply(player, game);

        EasyMock.verify(player, game, property);
    }
}
