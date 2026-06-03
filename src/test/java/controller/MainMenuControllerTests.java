package controller;

import org.junit.jupiter.api.Test;
import util.PlayerConfig;
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

    @Test
    public void createPlayerConfigs_WithMinimumPlayers_ReturnsTwoPlayerConfigs() {
        List<String> playerNames = List.of("John", "Jane");
        List<ImageIcon> playerIcons = List.of(new ImageIcon(), new ImageIcon());
        MainMenuController controller = new MainMenuController(playerNames, playerIcons);

        List<PlayerConfig> configs = controller.createPlayerConfigs();

        int expected = 2;
        int actual = configs.size();

        assertEquals(expected, actual);
    }

    @Test
    public void createPlayerConfigs_WithMaximumPlayers_ReturnsFourPlayerConfigs() {
        List<String> playerNames = List.of("John", "Jane", "Jack", "Jill");
        List<ImageIcon> playerIcons = List.of(
                new ImageIcon(),
                new ImageIcon(),
                new ImageIcon(),
                new ImageIcon()
        );
        MainMenuController controller = new MainMenuController(playerNames, playerIcons);

        List<PlayerConfig> configs = controller.createPlayerConfigs();

        int expected = 4;
        int actual = configs.size();

        assertEquals(expected, actual);
    }

    @Test
    public void createPlayerConfigs_WithOneMoreThanMaximumPlayers_ThrowsException() {
        List<String> playerNames = List.of("John", "Jane", "Jack", "Jill", "James");
        List<ImageIcon> playerIcons = List.of(
                new ImageIcon(),
                new ImageIcon(),
                new ImageIcon(),
                new ImageIcon(),
                new ImageIcon()
        );
        MainMenuController controller = new MainMenuController(playerNames, playerIcons);

        assertThrows(IllegalArgumentException.class, controller::createPlayerConfigs);
    }

}