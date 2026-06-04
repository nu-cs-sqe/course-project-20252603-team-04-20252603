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

}
