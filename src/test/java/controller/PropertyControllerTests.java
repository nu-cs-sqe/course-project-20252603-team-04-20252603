package controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import model.Player;
import model.Property;

public class PropertyControllerTests {

    // =====================================================================
    // promptPurchase
    // =====================================================================

    @Test
    public void TC1_PromptPurchase_NullPlayer_ThrowsIllegalArgumentException() {
        PropertyController controller = new PropertyController();
        Property property = EasyMock.createMock(Property.class);
        EasyMock.replay(property);

        assertThrows(IllegalArgumentException.class,
                () -> controller.promptPurchase(null, property));

        EasyMock.verify(property);
    }
}
