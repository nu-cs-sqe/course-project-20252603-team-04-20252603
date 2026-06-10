package controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import model.Card;
import model.Deck;
import model.GameEngine;
import model.Player;

public class CardController {

    private final Deck deck;
    private final GameEngine game;

    @SuppressFBWarnings({"EI_EXPOSE_REP2", "CT_CONSTRUCTOR_THROW"})
    public CardController(Deck deck, GameEngine game) {
        this.deck = Objects.requireNonNull(deck, "deck must not be null");
        this.game = Objects.requireNonNull(game, "game must not be null");
    }

    public Card drawChanceCard(Player player) {
        Objects.requireNonNull(player, "player must not be null");
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        return deck.draw();
    }

    public void applyCard(Card card, Player player) {
        Objects.requireNonNull(card, "card must not be null");
        Objects.requireNonNull(player, "player must not be null");
        if (!player.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        card.getCardEffect().apply(player, game);
        deck.discard(card);
    }

    public Map<String, String> showCard(Card card) {
        Objects.requireNonNull(card, "card must not be null");
        Map<String, String> result = new LinkedHashMap<>();
        result.put("title", card.getTitle());
        result.put("description", card.getDescription());
        return result;
    }
}
