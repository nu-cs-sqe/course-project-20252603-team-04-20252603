package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainMenuControllerTests {

    @Test
    public void validatePlayerCount_WithOneLessThanMinimum_ReturnsFalse() {
        MainMenuController controller = new MainMenuController();

        boolean actual = controller.validatePlayerCount(1);
        assertFalse(actual);
    }

}