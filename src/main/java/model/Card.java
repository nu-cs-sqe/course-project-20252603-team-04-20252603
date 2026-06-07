package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Card {

    private final String title;
    private final String description;
    private final CardEffect effect;

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    public Card(String title, String description, CardEffect effect) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("title must not be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description must not be null or empty");
        }
        if (effect == null) {
            throw new IllegalArgumentException("effect must not be null");
        }
        this.title = title;
        this.description = description;
        this.effect = effect;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public CardEffect getCardEffect() {
        return effect;
    }
}
