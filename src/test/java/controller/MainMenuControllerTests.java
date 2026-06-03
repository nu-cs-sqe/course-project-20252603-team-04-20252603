package controller;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuControllerTests {

    @Test
    public void validatePlayerCount_WithOneLessThanMinimum_ReturnsFalse() {
        MainMenuController controller = new MainMenuController(List.of(), List.of());

        boolean actual = controller.validatePlayerCount(1);
        assertFalse(actual);
    }

    @Test
    public void validatePlayerCount_WithMinimumPlayerCount_ReturnsTrue() {
        MainMenuController controller = new MainMenuController(List.of(), List.of());

        boolean actual = controller.validatePlayerCount(2);

        assertTrue(actual);
    }

    @Test
    public void validatePlayerCount_WithMaximumPlayerCount_ReturnsTrue() {
        MainMenuController controller = new MainMenuController(List.of(), List.of());

        boolean actual = controller.validatePlayerCount(4);

        assertTrue(actual);
    }

    @Test
    public void validatePlayerCount_WithOneMoreThanMaximum_ReturnsFalse() {
        MainMenuController controller = new MainMenuController(List.of(), List.of());

        boolean actual = controller.validatePlayerCount(5);

        assertFalse(actual);
    }

    @Test
    public void createPlayerConfigs_WithOneLessThanMinimumPlayers_ThrowsException() {
        List<String> playerNames = List.of("John");
        List<ImageIcon> playerIcons = List.of(new ImageIcon());
        MainMenuController controller = new MainMenuController(playerNames, playerIcons);

        assertThrows(IllegalArgumentException.class, controller::createPlayerConfigs);
    }

}