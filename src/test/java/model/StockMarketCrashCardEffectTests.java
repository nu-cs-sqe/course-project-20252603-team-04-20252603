package model;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Constants;

public class StockMarketCrashCardEffectTests {

    // ==================================================================================================
    // Input validation tests (TC1 - TC3)
    // ==================================================================================================

    @Test
    public void apply_OnNullPlayer_ThrowsIllegalArgumentException() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void apply_OnNullGame_ThrowsIllegalArgumentException() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(player);

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(player, null));

        EasyMock.verify(player);
    }

    @Test
    public void apply_OnBothNull_ThrowsIllegalArgumentException() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();

        assertThrows(IllegalArgumentException.class,
                () -> effect.apply(null, null));
    }

    // ==================================================================================================
    // Normal operation tests (TC4 - TC7)
    // ==================================================================================================

    @Test
    public void apply_OnAllFourPlayersCanAfford_EachLoses200() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(p1, p2, p3, p4));
        EasyMock.expect(p1.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p1.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p4.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p4.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.replay(p1, p2, p3, p4, game);

        effect.apply(p1, game);

        EasyMock.verify(p1, p2, p3, p4, game);
    }

    @Test
    public void apply_OnAllFourPlayersWithExactly200_AllPayAndRemainActive() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(p1, p2, p3, p4));
        EasyMock.expect(p1.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p1.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p4.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p4.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.replay(p1, p2, p3, p4, game);

        effect.apply(p1, game);

        EasyMock.verify(p1, p2, p3, p4, game);
    }

    @Test
    public void apply_OnOneInsolventPlayer199WithNoProperties_RemovesThatPlayer() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player insolvent = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(p1, p2, p3, insolvent));
        EasyMock.expect(p1.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p1.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(insolvent.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(insolvent.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(insolvent);
        EasyMock.replay(p1, p2, p3, insolvent, game);

        effect.apply(p1, game);

        EasyMock.verify(p1, p2, p3, insolvent, game);
    }

    @Test
    public void apply_OnOneInsolventPlayerZeroWithNoProperties_RemovesThatPlayer() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player insolvent = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(p1, p2, p3, insolvent));
        EasyMock.expect(p1.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p1.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p2.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(p3.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(insolvent.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(insolvent.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(insolvent);
        EasyMock.replay(p1, p2, p3, insolvent, game);

        effect.apply(p1, game);

        EasyMock.verify(p1, p2, p3, insolvent, game);
    }

    // ==================================================================================================
    // Edge case tests (TC8 - TC10)
    // ==================================================================================================

    @Test
    public void apply_OnOnlyOneActivePlayerWhoCanAfford_LosesPaymentAndGameContinues() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player solo = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(solo));
        EasyMock.expect(solo.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(solo.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.replay(solo, game);

        effect.apply(solo, game);

        EasyMock.verify(solo, game);
    }

    @Test
    public void apply_OnCrashEliminatesAllButOne_RemovesBankruptPlayer() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player playerA = EasyMock.createMock(Player.class);
        Player playerB = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(playerA, playerB));
        EasyMock.expect(playerA.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(playerA.remove(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(true);
        EasyMock.expect(playerB.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(playerB.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(playerB);
        EasyMock.replay(playerA, playerB, game);

        effect.apply(playerA, game);

        EasyMock.verify(playerA, playerB, game);
    }

    @Test
    public void apply_OnPlayerCannotAffordButHasProperties_DoesNotRemoveFromGame() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player p1 = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Property property = EasyMock.createMock(Property.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(p1));
        EasyMock.expect(p1.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(p1.getOwnedProperties()).andReturn(Collections.singleton(property));
        EasyMock.replay(p1, game, property);

        effect.apply(p1, game);

        EasyMock.verify(p1, game, property);
    }

    @Test
    public void apply_OnCrashEliminatesAllPlayers_RemovesEachOne() {
        StockMarketCrashCardEffect effect = new StockMarketCrashCardEffect();
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);

        EasyMock.expect(game.getActivePlayers()).andReturn(List.of(p1, p2, p3, p4));
        EasyMock.expect(p1.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(p1.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(p1);
        EasyMock.expect(p2.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(p2.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(p2);
        EasyMock.expect(p3.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(p3.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(p3);
        EasyMock.expect(p4.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)).andReturn(false);
        EasyMock.expect(p4.getOwnedProperties()).andReturn(Collections.emptySet());
        game.removeBankruptPlayer(p4);
        EasyMock.replay(p1, p2, p3, p4, game);

        effect.apply(p1, game);

        EasyMock.verify(p1, p2, p3, p4, game);
    }
}
