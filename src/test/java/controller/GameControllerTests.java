package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameControllerTests {

    @Test
    public void TC1_startGame_WithNullConfigList_ThrowsException() {
        GameController gameController = new GameController();
        assertThrows(NullPointerException.class, () -> gameController.startGame(null));
    }
}
