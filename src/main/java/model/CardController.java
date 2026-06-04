package model;

import java.util.HashMap;
import java.util.Map;

public class CardController {
	private final Deck deck;
	private final GameEngine game;

	public CardController(Deck deck, GameEngine game) {
		this.deck = deck;
		this.game = game;
	}

	public Card drawChanceCard(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		}
		if (!player.getActive()) {
			throw new IllegalArgumentException("Player must be active");
		}
		return deck.draw();
	}

	public void applyCard(Card card, Player player) {
		if (card == null) {
			throw new IllegalArgumentException("Card cannot be null");
		}
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		}
		if (!player.getActive()) {
			throw new IllegalArgumentException("Player must be active");
		}
		card.getCardEffect().apply(player, game);
		deck.discard(card);
	}

	public Map<String, String> showCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Card cannot be null");
		}
		Map<String, String> result = new HashMap<>();
		result.put("title", card.getTitle());
		result.put("description", card.getDescription());
		return result;
	}
}
