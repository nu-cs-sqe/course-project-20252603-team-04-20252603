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

    @Test
    public void constructor_OnNullTitle_ThrowsIllegalArgumentException() {
        String title = null;
        String description = "Go directly to jail.";
        CardEffect effect = (player, game) -> {};

        assertThrows(IllegalArgumentException.class,
                () -> new Card(title, description, effect));
    }

    @Test
    public void constructor_OnEmptyTitle_ThrowsIllegalArgumentException() {
        String title = "";
        String description = "Go directly to jail.";
        CardEffect effect = (player, game) -> {};

        assertThrows(IllegalArgumentException.class,
                () -> new Card(title, description, effect));
    }

    @Test
    public void constructor_OnNullDescription_ThrowsIllegalArgumentException() {
        String title = "Go to Jail";
        String description = null;
        CardEffect effect = (player, game) -> {};

        assertThrows(IllegalArgumentException.class,
                () -> new Card(title, description, effect));
    }
}
