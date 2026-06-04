package model;

import model.*;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CardControllerTests {

	// TC1: drawChanceCard - Null player
	@Test
	public void TC1_drawChanceCard_nullPlayer_throwsIllegalArgumentException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		EasyMock.replay(deck, game);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalArgumentException.class, () -> controller.drawChanceCard(null));

		EasyMock.verify(deck, game);
	}

	// TC2: drawChanceCard - Inactive player
	@Test
	public void TC2_drawChanceCard_inactivePlayer_throwsIllegalArgumentException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		Player player = EasyMock.createMock(Player.class);
		EasyMock.expect(player.getActive()).andReturn(false);
		EasyMock.replay(deck, game, player);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalArgumentException.class, () -> controller.drawChanceCard(player));

		EasyMock.verify(deck, game, player);
	}

	// TC3: drawChanceCard - Active player draws the top card
	@Test
	public void TC3_drawChanceCard_activePlayer_returnsCardFromDeck() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		Player player = EasyMock.createMock(Player.class);
		CardEffect effect = EasyMock.createMock(CardEffect.class);
		Card card = new Card("Test Card", "Test Description", effect);

		EasyMock.expect(player.getActive()).andReturn(true);
		EasyMock.expect(deck.draw()).andReturn(card);
		EasyMock.replay(deck, game, player, effect);

		CardController controller = new CardController(deck, game);
		Card result = controller.drawChanceCard(player);

		assertEquals(card, result);
		EasyMock.verify(deck, game, player, effect);
	}

	// TC4: drawChanceCard - Deck is exhausted
	@Test
	public void TC4_drawChanceCard_deckExhausted_propagatesIllegalStateException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		Player player = EasyMock.createMock(Player.class);

		EasyMock.expect(player.getActive()).andReturn(true);
		EasyMock.expect(deck.draw()).andThrow(new IllegalStateException("Both unused and used piles are empty"));
		EasyMock.replay(deck, game, player);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalStateException.class, () -> controller.drawChanceCard(player));

		EasyMock.verify(deck, game, player);
	}

	// TC5: applyCard - Null card
	@Test
	public void TC5_applyCard_nullCard_throwsIllegalArgumentException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		Player player = EasyMock.createMock(Player.class);

		EasyMock.replay(deck, game, player);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalArgumentException.class, () -> controller.applyCard(null, player));

		EasyMock.verify(deck, game, player);
	}

	// TC6: applyCard - Null player
	@Test
	public void TC6_applyCard_nullPlayer_throwsIllegalArgumentException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		CardEffect effect = EasyMock.createMock(CardEffect.class);
		Card card = new Card("Test Card", "Test Description", effect);

		EasyMock.replay(deck, game, effect);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalArgumentException.class, () -> controller.applyCard(card, null));

		EasyMock.verify(deck, game, effect);
	}

	// TC7: applyCard - Both null
	@Test
	public void TC7_applyCard_bothNull_throwsIllegalArgumentException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);

		EasyMock.replay(deck, game);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalArgumentException.class, () -> controller.applyCard(null, null));

		EasyMock.verify(deck, game);
	}

	// TC8: applyCard - Inactive player
	@Test
	public void TC8_applyCard_inactivePlayer_throwsIllegalArgumentException() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		CardEffect effect = EasyMock.createMock(CardEffect.class);
		Card card = new Card("Test Card", "Test Description", effect);
		Player player = EasyMock.createMock(Player.class);

		EasyMock.expect(player.getActive()).andReturn(false);
		EasyMock.replay(deck, game, effect, player);

		CardController controller = new CardController(deck, game);
		assertThrows(IllegalArgumentException.class, () -> controller.applyCard(card, player));

		EasyMock.verify(deck, game, effect, player);
	}

	// TC9: applyCard - Valid card applied to active player
	@Test
	public void TC9_applyCard_validCardActivePlayer_invokesCardEffect() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		CardEffect effect = EasyMock.createMock(CardEffect.class);
		Card card = new Card("Test Card", "Test Description", effect);
		Player player = EasyMock.createMock(Player.class);

		EasyMock.expect(player.getActive()).andReturn(true);
		effect.apply(player, game);
		EasyMock.expectLastCall().once();
		deck.discard(card);
		EasyMock.expectLastCall().once();
		EasyMock.replay(deck, game, effect, player);

		CardController controller = new CardController(deck, game);
		controller.applyCard(card, player);

		EasyMock.verify(deck, game, effect, player);
	}

	// TC10: applyCard - Applied card is discarded back to the deck
	@Test
	public void TC10_applyCard_afterEffectApplied_discardCardToDeck() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		CardEffect effect = EasyMock.createMock(CardEffect.class);
		Card card = new Card("Test Card", "Test Description", effect);
		Player player = EasyMock.createMock(Player.class);

		EasyMock.expect(player.getActive()).andReturn(true);
		effect.apply(player, game);
		EasyMock.expectLastCall().once();
		deck.discard(card);
		EasyMock.expectLastCall().once();
		EasyMock.replay(deck, game, effect, player);

		CardController controller = new CardController(deck, game);
		controller.applyCard(card, player);

		EasyMock.verify(deck, game, effect, player);
	}

	// TC11: applyCard - Effect throws while being applied
	@Test
	public void TC11_applyCard_effectThrows_propagatesExceptionWithoutDiscarding() {
		Deck deck = EasyMock.createMock(Deck.class);
		GameEngine game = EasyMock.createMock(GameEngine.class);
		CardEffect effect = EasyMock.createMock(CardEffect.class);
		Card card = new Card("Test Card", "Test Description", effect);
		Player player = EasyMock.createMock(Player.class);

		EasyMock.expect(player.getActive()).andReturn(true);
		effect.apply(player, game);
		EasyMock.expectLastCall().andThrow(new RuntimeException("Effect failed"));
		EasyMock.replay(deck, game, effect, player);

		CardController controller = new CardController(deck, game);
		assertThrows(RuntimeException.class, () -> controller.applyCard(card, player));

		EasyMock.verify(deck, game, effect, player);
	}

}
