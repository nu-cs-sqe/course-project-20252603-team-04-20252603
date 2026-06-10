package controller;

import model.GameEngine;
import model.GameStatus;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainMenuControllerIntegrationTests {

    private ImageIcon createIcon() {
        return new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }

    @Test
    public void startNewGame_WithValidSetup_SetsGameStatusToInProgress() {
        List<String> playerNames = List.of("John", "Jane");
        List<ImageIcon> playerIcons = List.of(createIcon(), createIcon());
        MainMenuController controller = new MainMenuController(playerNames, playerIcons);

        GameEngine game = controller.startNewGame();

        GameStatus expected = GameStatus.IN_PROGRESS;
        GameStatus actual = game.getStatus();

        assertEquals(expected, actual);
    }

    @Test
    public void startNewGame_WithValidSetup_SetsFirstPlayerAsCurrentPlayer() {
        List<String> playerNames = List.of("John", "Jane");
        List<ImageIcon> playerIcons = List.of(createIcon(), createIcon());
        MainMenuController controller = new MainMenuController(playerNames, playerIcons);

        GameEngine game = controller.startNewGame();

        String expected = "John";
        String actual = game.getCurrentPlayer().getName();

        assertEquals(expected, actual);
    }

}
