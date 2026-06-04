package model;

public final class Card {

    private final String title;
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
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
