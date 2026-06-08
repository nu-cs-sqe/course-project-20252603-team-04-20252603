package controller;

import model.GameEngine;
import model.GameStatus;
import model.Dice;
import model.Player;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import util.Constants;
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

    @Test
    public void TC8_getActivePlayers_WhenGameEngineHasMaximumPlayers_ReturnsAllPlayers() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getActivePlayers()).andStubReturn(List.of(player1, player2, player3, player4));
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(List.of(player1, player2, player3, player4), gameController.getActivePlayers());
    }

    @Test
    public void TC9_getActivePlayers_WhenGameEngineHasOneRemainingPlayer_ReturnsWinnerOnly() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Player player1 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getActivePlayers()).andStubReturn(List.of(player1));
        EasyMock.replay(gameEngine);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertEquals(List.of(player1), gameController.getActivePlayers());
    }

    @Test
    public void TC10_getActivePlayers_ReturnedListCannotMutateControllerState() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        List<Player> activePlayers = List.of(player1, player2);

        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(activePlayers);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView,
                player1, player2, player3);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView
        );
        List<Player> returnedPlayers = controller.getActivePlayers();

        assertThrows(UnsupportedOperationException.class,
                () -> returnedPlayers.add(player3));

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView,
                player1, player2, player3);
    }

    @Test
    public void TC11_startGame_WithNullPlayerList_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertThrows(NullPointerException.class, () -> gameController.startGame(null));
    }

    @Test
    public void TC12_startGame_WithEmptyPlayerList_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);
        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(List.of()));
        assertEquals(GameStatus.NOT_STARTED, gameController.getStatus());
        EasyMock.verify(gameEngine);
    }

    @Test
    public void TC13_startGame_WithOnePlayer_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);
        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(List.of(EasyMock.createMock(Player.class))));
        assertEquals(GameStatus.NOT_STARTED, gameController.getStatus());
        EasyMock.verify(gameEngine);
    }

    @Test
    public void TC14_startGame_WithMinimumPlayers_StartsGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);

        gameEngine.startGame();
        EasyMock.expectLastCall().once();
        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        gameController.startGame(List.of(player1, player2));

        EasyMock.verify(gameEngine);
    }

    @Test
    public void TC15_startGame_WithMaximumPlayers_StartsGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);

        gameEngine.startGame();
        EasyMock.expectLastCall().once();
        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        gameController.startGame(List.of(player1, player2, player3, player4));
        EasyMock.verify(gameEngine);
    }


    @Test
    public void TC16_startGame_WithMoreThanMaximumPlayers_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player player5 = EasyMock.createMock(Player.class);

        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(List.of(player1, player2, player3, player4, player5)));

        EasyMock.verify(gameEngine);
    }

    @Test
    public void TC17_startGame_WithOneRemainingPlayer_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player1 = EasyMock.createMock(Player.class);
        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertThrows(NullPointerException.class, () -> gameController.startGame(List.of(player1, null)));
        EasyMock.verify(gameEngine);
    }

    @Test
    public void TC18_startGame_WhenGameAlreadyInProgress_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.IN_PROGRESS);
        EasyMock.replay(gameEngine);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView);
        assertThrows(IllegalStateException.class, () -> gameController.startGame(List.of(player1, player2)));
    }

    @Test
    public void TC19_handleRollDice_WithMinimumRoll_MovesTwoSpaces() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.isBankrupt()).andReturn(false);


        dice.roll();
        EasyMock.expectLastCall().once();

        EasyMock.expect(dice.getTotal()).andReturn(Constants.MIN_DICE_ROLL);

        gameEngine.movePlayer(player, Constants.MIN_DICE_ROLL);
        EasyMock.expectLastCall().once();

        boardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        playerInfoView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        diceView.refresh(dice);
        EasyMock.expectLastCall().once();

        cardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        EasyMock.replay(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);

        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        gameController.handleRollDice();

        EasyMock.verify(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);
    }

    @Test
    public void TC20_handleRollDice_WithMaximumRoll_MovesTwelveSpaces() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.isBankrupt()).andReturn(false);


        dice.roll();
        EasyMock.expectLastCall().once();

        EasyMock.expect(dice.getTotal()).andReturn(Constants.MAX_DICE_ROLL);

        gameEngine.movePlayer(player, Constants.MAX_DICE_ROLL);
        EasyMock.expectLastCall().once();

        boardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        playerInfoView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        diceView.refresh(dice);
        EasyMock.expectLastCall().once();

        cardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        EasyMock.replay(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);

        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        gameController.handleRollDice();

        EasyMock.verify(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);
    }

    @Test
    public void TC21_handleRollDice_WhenCurrentPlayerIsBankrupt_DoesNotMovePlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(true);

        EasyMock.replay(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleRollDice();

        EasyMock.verify(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);
    }

    @Test
    public void TC22_handleRollDice_WhenTileEffectCausesBankruptcy_HandlesBankruptcy() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);

        dice.roll();
        EasyMock.expectLastCall().once();

        EasyMock.expect(dice.getTotal()).andReturn(Constants.MIN_DICE_ROLL);

        gameEngine.movePlayer(player, Constants.MIN_DICE_ROLL);
        EasyMock.expectLastCall().once();

        EasyMock.expect(player.isBankrupt()).andReturn(true);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        boardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        playerInfoView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        diceView.refresh(dice);
        EasyMock.expectLastCall().once();

        cardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        EasyMock.replay(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleRollDice();

        EasyMock.verify(gameEngine, dice,boardView, playerInfoView, diceView, cardView, player);
    }


    @Test
    public void TC43_refreshViews_WithAllViewsPresent_UpdatesEveryView() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        boardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        playerInfoView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        diceView.refresh(dice);
        EasyMock.expectLastCall().once();

        cardView.refresh(gameEngine);
        EasyMock.expectLastCall().once();

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        gameController.refreshViews();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }
}
