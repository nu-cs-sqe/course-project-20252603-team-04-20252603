package model;

public class CardController {

    private final Deck deck;
    private final GameEngine game;

    public CardController(Deck deck, GameEngine game) {
        this.deck = deck;
        this.game = game;
    }

    public Card drawChanceCard(Player player) {
        if (player == null || !player.getActive()) {
            throw new IllegalArgumentException("player must not be null or inactive");
        }
        return deck.draw();
    }
}
