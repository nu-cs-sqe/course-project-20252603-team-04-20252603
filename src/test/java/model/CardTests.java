package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTests {

    @Test
    public void getDescription_OnValidCard_ReturnsDescription() {
        String title = "Go to Jail";
        String description = "Go directly to jail.";
        CardEffect effect = (player, game) -> {};

        Card card = new Card(title, description, effect);

        assertEquals("Go directly to jail.", card.getDescription());
    }
}
