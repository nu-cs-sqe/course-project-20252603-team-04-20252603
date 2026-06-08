package controller;

import model.Player;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameControllerTests {

    @Test
    public void TC1_startGame_WithNullConfigList_ThrowsException() {
        GameController gameController = new GameController();
        assertThrows(NullPointerException.class, () -> gameController.startGame(null));
    }

    @Test
    public void TC2_startGame_WithZeroPlayers_ThrowsException() {
        GameController gameController = new GameController();
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(java.util.List.of()));
    }

    @Test
    public void TC3_startGame_WithOnePlayer_ThrowsException() {
        GameController gameController = new GameController();
        Player onlyPlayer = EasyMock.createMock(Player.class);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(java.util.List.of(onlyPlayer)));
    }
}
