package model;

import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class AIBubblePopCardEffectTests {

    // ==================================================================================================
    // Input validation tests
    // ==================================================================================================

    @Test
    public void apply_OnNullPlayer_ThrowsIllegalArgumentException() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void apply_OnNullGame_ThrowsIllegalArgumentException() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void apply_OnBothNull_ThrowsIllegalArgumentException() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, null));
    }

    // ==================================================================================================
    // Normal operation tests
    // ==================================================================================================

    @Test
    public void apply_OnPlayerBalanceGreaterThan500_DeductsFee() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.AI_BUBBLE_POP_FEE)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerBalanceExactly500_DeductsFeeAndRemainsActive() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.AI_BUBBLE_POP_FEE)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerBalance499WithNoProperties_RemovesFromGame() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerBalanceZeroWithNoProperties_RemovesFromGame() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerWithStartingBalance1000_DeductsFee() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.AI_BUBBLE_POP_FEE)).andReturn(true);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    // ==================================================================================================
    // Edge case tests
    // ==================================================================================================

    @Test
    public void apply_OnEliminatedPlayer_ThrowsIllegalArgumentException() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
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
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(player);
        EasyMock.replay(player, game);

        effect.apply(player, game);

        EasyMock.verify(player, game);
    }

    @Test
    public void apply_OnPlayerCannotAffordButHasProperties_DoesNotRemoveFromGame() {
        AIBubblePopCardEffect effect = new AIBubblePopCardEffect();
        Player player = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Property property = EasyMock.createMock(Property.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.AI_BUBBLE_POP_FEE)).andReturn(false);
        EasyMock.expect(player.getOwnedProperties()).andReturn(Collections.singleton(property));
        EasyMock.replay(player, game, property);

        effect.apply(player, game);

        EasyMock.verify(player, game, property);
    }
}
