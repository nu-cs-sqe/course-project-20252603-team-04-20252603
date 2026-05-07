package model;

public class Card {

    private final String description;

    public Card(String title, String description, CardEffect effect) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("title must not be null or empty");
        }
        if (description == null) {
            throw new IllegalArgumentException("description must not be null");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
