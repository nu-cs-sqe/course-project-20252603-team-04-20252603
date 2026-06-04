package model;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class CardController {

    private final Deck deck;
    private final GameEngine game;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public CardController(Deck deck, GameEngine game) {
        if (deck == null) {
            throw new IllegalArgumentException("deck must not be null");
        }
        this.deck = deck;
        this.game = game;
    }

    public Card drawChanceCard(Player player) {
        if (player == null || !player.getActive()) {
            throw new IllegalArgumentException("player must not be null or inactive");
        }
        return deck.draw();
    }

    public void applyCard(Card card, Player player) {
        if (card == null) {
            throw new IllegalArgumentException("card must not be null");
        }
        if (player == null || !player.getActive()) {
            throw new IllegalArgumentException("player must not be null or inactive");
        }
        card.getCardEffect().apply(player, game);
        deck.discard(card);
    }

    public Map<String, String> showCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("card must not be null");
        }
        Map<String, String> result = new LinkedHashMap<>();
        result.put("title", card.getTitle());
        result.put("description", card.getDescription());
        return result;
    }
}
