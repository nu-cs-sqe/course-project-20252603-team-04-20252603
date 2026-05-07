package model;

public class Card {

    private final String description;

    public Card(String title, String description, CardEffect effect) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
