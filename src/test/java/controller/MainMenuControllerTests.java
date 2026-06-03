package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainMenuControllerTests {

    @Test
    public void validatePlayerCount_WithOneLessThanMinimum_ReturnsFalse() {
        MainMenuController controller = new MainMenuController();

        boolean actual = controller.validatePlayerCount(1);
        assertFalse(actual);
    }

    @Test
    public void validatePlayerCount_WithMinimumPlayerCount_ReturnsTrue() {
        MainMenuController controller = new MainMenuController();

        boolean actual = controller.validatePlayerCount(2);

        assertTrue(actual);
    }

    @Test
    public void validatePlayerCount_WithMaximumPlayerCount_ReturnsTrue() {
        MainMenuController controller = new MainMenuController();

        boolean actual = controller.validatePlayerCount(4);

        assertTrue(actual);
    }

}