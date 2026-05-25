package model;

public final class Card {

    private final String description;

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
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
