package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Assembles the standard, shuffled chance deck (one card per implemented {@link CardEffect}). */
public final class ChanceDeckFactory {

    private ChanceDeckFactory() {
    }

    public static Deck standardDeck() {
        return standardDeck(new Random());
    }

    static Deck standardDeck(Random random) {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Advance to GO", "Advance to GO. Collect $200.",
                new AdvanceToGoCardEffect()));
        cards.add(new Card("Go to Jail", "Go directly to Jail.",
                new GoToJailCardEffect()));
        cards.add(new Card("Go Back 3 Spaces", "Move back three spaces.",
                new GoBackThreeSpacesCardEffect()));
        cards.add(new Card("AI Bubble Pops", "The AI bubble pops: you lose $500.",
                new AIBubblePopCardEffect()));
        cards.add(new Card("Subscription Service", "Pay $100 for a subscription service.",
                new SubscriptionServiceCardEffect()));
        cards.add(new Card("Stock Market Crash", "Stock market crashes: every player loses $200.",
                new StockMarketCrashCardEffect()));

        Deck deck = new Deck(random, cards);
        deck.shuffle();
        return deck;
    }
}
