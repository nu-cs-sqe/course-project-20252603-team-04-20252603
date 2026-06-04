package model;

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
		return null;
	}

	public void applyCard(Card card, Player player) {
	}

	public Map<String, String> showCard(Card card) {
		return null;
	}
}
