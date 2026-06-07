package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTests {

    @Test
    public void getCardEffect_OnValidCard_ReturnsEffectFromConstructor() {
        CardEffect effect = EasyMock.createMock(CardEffect.class);
        EasyMock.replay(effect);

        Card card = new Card("Go to Jail", "Go directly to jail.", effect);

        assertSame(effect, card.getCardEffect());
        EasyMock.verify(effect);
    }

    @Test
    public void getTitle_OnNormalTitle_ReturnsExactString() {
        CardEffect effect = (player, game) -> {};
        Card card = new Card("Go to Jail", "Go directly to jail.", effect);

        assertEquals("Go to Jail", card.getTitle());
    }

    @Test
    public void getTitle_OnTitleWithSpecialCharacters_ReturnsUnchanged() {
        CardEffect effect = (player, game) -> {};
        Card card = new Card("Pay $100!", "Pay $100 for a subscription service!", effect);

        assertEquals("Pay $100!", card.getTitle());
    }

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

    @Test
    public void constructor_OnEmptyDescription_ThrowsIllegalArgumentException() {
        String title = "Go to Jail";
        String description = "";
        CardEffect effect = (player, game) -> {};

        assertThrows(IllegalArgumentException.class,
                () -> new Card(title, description, effect));
    }

    @Test
    public void constructor_OnNullEffect_ThrowsIllegalArgumentException() {
        String title = "Go to Jail";
        String description = "Go directly to jail.";
        CardEffect effect = null;

        assertThrows(IllegalArgumentException.class,
                () -> new Card(title, description, effect));
    }

    @Test
    public void getDescription_OnNormalDescription_ReturnsExactString() {
        CardEffect effect = (player, game) -> {};
        Card card = new Card("AI Bubble", "AI bubble pops: lose $500", effect);

        assertEquals("AI bubble pops: lose $500", card.getDescription());
    }

    @Test
    public void getDescription_OnDescriptionWithSpecialCharacters_ReturnsUnchanged() {
        CardEffect effect = (player, game) -> {};
        Card card = new Card("Subscription",
                "Pay $100 for a subscription service!", effect);

        assertEquals("Pay $100 for a subscription service!",
                card.getDescription());
    }
}
