package controller;

import model.GameEngine;
import model.GameStatus;
import model.Player;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTests {

    @Test
    public void TC1_getStatus_WhenGameEngineIsNull_ThrowsNullPointerException() {
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);

        assertThrows(NullPointerException.class, () -> new GameController(null, boardView, playerInfoView, diceView, cardView));
    }

    @Test
    public void TC2_getStatus_WhenGameEngineStatusIsNotStarted_ReturnsNotStarted() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(gameEngine.getStatus()).andStubReturn(GameStatus.NOT_STARTED);
        EasyMock.replay(gameEngine);

        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(GameStatus.NOT_STARTED, gameController.getStatus());
    }

    @Test
    public void TC3_getStatus_WhenGameEngineStatusIsInProgress_ReturnsInProgress() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(gameEngine.getStatus()).andStubReturn(GameStatus.IN_PROGRESS);
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(GameStatus.IN_PROGRESS, gameController.getStatus());
    }

    @Test
    public void TC4_getStatus_WhenGameEngineStatusIsGameOver_ReturnsGameOver() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(gameEngine.getStatus()).andStubReturn(GameStatus.GAME_OVER);
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(GameStatus.GAME_OVER, gameController.getStatus());
    }

    @Test
    public void TC5_getCurrentPlayer_WhenGameEngineCurrentPlayerIsFirstPlayer_ReturnsFirstPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Player firstPlayer = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getCurrentPlayer()).andStubReturn(firstPlayer);
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(firstPlayer, gameController.getCurrentPlayer());
    }

    @Test
    public void TC6_getActivePlayers_WhenGameEngineActivePlayersIsEmpty_ReturnsEmptyList() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        EasyMock.expect(gameEngine.getActivePlayers()).andStubReturn(List.of());
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(List.of(), gameController.getActivePlayers());
    }

    @Test
    public void TC7_getActivePlayers_WhenGameEngineHasMinimumPlayers_ReturnsBothPlayers() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getActivePlayers()).andStubReturn(List.of(player1, player2));
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(List.of(player1, player2), gameController.getActivePlayers());
    }
}
