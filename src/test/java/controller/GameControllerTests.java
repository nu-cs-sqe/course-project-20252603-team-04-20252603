package controller;

import model.*;
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

    private void expectRefreshViews(GameEngine gameEngine, BoardView boardView,
                                    PlayerInfoView playerInfoView, DiceView diceView,
                                    CardView cardView) {
        List<Player> activePlayers = List.of();

        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(activePlayers);

        boardView.refresh();
        EasyMock.expectLastCall().once();

        playerInfoView.renderPlayers(activePlayers);
        EasyMock.expectLastCall().once();

        diceView.enableRollButton();
        EasyMock.expectLastCall().once();

        cardView.close();
        EasyMock.expectLastCall().once();
    }

    private void expectRefreshViewsWithPlayerPosition(GameEngine gameEngine, BoardView boardView,
                                                      PlayerInfoView playerInfoView, DiceView diceView,
                                                      CardView cardView, Player player, int position) {
        List<Player> activePlayers = List.of(player);

        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(activePlayers);

        boardView.refresh();
        EasyMock.expectLastCall().once();

        playerInfoView.renderPlayers(activePlayers);
        EasyMock.expectLastCall().once();

        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(position);
        boardView.updatePlayerPosition(player, position);
        EasyMock.expectLastCall().once();

        playerInfoView.updateBalance(player);
        EasyMock.expectLastCall().once();

        playerInfoView.updateProperties(player);
        EasyMock.expectLastCall().once();

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        playerInfoView.showCurrentTurn(player);
        EasyMock.expectLastCall().once();

        diceView.enableRollButton();
        EasyMock.expectLastCall().once();

        cardView.close();
        EasyMock.expectLastCall().once();
    }

    private void expectRefreshViewsWithCard(GameEngine gameEngine, BoardView boardView,
                                            PlayerInfoView playerInfoView, DiceView diceView,
                                            CardView cardView, Card card) {
        List<Player> activePlayers = List.of();

        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(activePlayers);

        boardView.refresh();
        EasyMock.expectLastCall().once();

        playerInfoView.renderPlayers(activePlayers);
        EasyMock.expectLastCall().once();

        diceView.disableRollButton();
        EasyMock.expectLastCall().once();

        cardView.showCard(card);
        EasyMock.expectLastCall().once();
    }

    @Test
    public void TC1_getStatus_WhenGameEngineIsNull_ThrowsNullPointerException() {
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        assertThrows(NullPointerException.class, () -> new GameController(
                null, boardView, playerInfoView, diceView, cardView, dice
        ));
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertEquals(List.of(player1), gameController.getActivePlayers());
    }

    @Test
    public void TC10_getActivePlayers_ReturnedListCannotMutateControllerState() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        List<Player> activePlayers = List.of(player1, player2);

        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(activePlayers);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView,
                dice, player1, player2, player3);

        GameController controller = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        List<Player> returnedPlayers = controller.getActivePlayers();

        assertThrows(UnsupportedOperationException.class,
                () -> returnedPlayers.add(player3));

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView,
                dice, player1, player2, player3);
    }

    @Test
    public void TC11_startGame_WithNullPlayerList_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertThrows(NullPointerException.class, () -> gameController.startGame(null));
        EasyMock.verify(dice);
    }

    @Test
    public void TC12_startGame_WithEmptyPlayerList_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);
        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(List.of()));
        assertEquals(GameStatus.NOT_STARTED, gameController.getStatus());
        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC13_startGame_WithOnePlayer_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);
        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(List.of(EasyMock.createMock(Player.class))));
        assertEquals(GameStatus.NOT_STARTED, gameController.getStatus());
        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC14_startGame_WithMinimumPlayers_StartsGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);

        gameEngine.startGame();
        EasyMock.expectLastCall().once();
        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        gameController.startGame(List.of(player1, player2));

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC15_startGame_WithMaximumPlayers_StartsGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.NOT_STARTED);

        gameEngine.startGame();
        EasyMock.expectLastCall().once();
        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        gameController.startGame(List.of(player1, player2, player3, player4));
        EasyMock.verify(gameEngine, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertThrows(IllegalArgumentException.class, () -> gameController.startGame(List.of(player1, player2, player3, player4, player5)));

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC17_startGame_WithNullPlayer_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Player player1 = EasyMock.createMock(Player.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertThrows(NullPointerException.class, () -> gameController.startGame(List.of(player1, null)));
        EasyMock.verify(gameEngine, dice);
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
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.expect(gameEngine.getStatus()).andReturn(GameStatus.IN_PROGRESS);
        EasyMock.replay(gameEngine, dice);
        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        assertThrows(IllegalStateException.class, () -> gameController.startGame(List.of(player1, player2)));
        EasyMock.verify(gameEngine, dice);
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

        EasyMock.expect(dice.getDieOne()).andReturn(1);
        EasyMock.expect(dice.getDieTwo()).andReturn(1);
        diceView.showRollResult(1, 1);
        EasyMock.expectLastCall().once();

        EasyMock.expect(dice.getTotal()).andReturn(Constants.MIN_DICE_ROLL);

        gameEngine.movePlayer(player, Constants.MIN_DICE_ROLL);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

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

        EasyMock.expect(dice.getDieOne()).andReturn(6);
        EasyMock.expect(dice.getDieTwo()).andReturn(6);
        diceView.showRollResult(6, 6);
        EasyMock.expectLastCall().once();

        EasyMock.expect(dice.getTotal()).andReturn(Constants.MAX_DICE_ROLL);

        gameEngine.movePlayer(player, Constants.MAX_DICE_ROLL);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

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

        EasyMock.expect(dice.getDieOne()).andReturn(1);
        EasyMock.expect(dice.getDieTwo()).andReturn(1);
        diceView.showRollResult(1, 1);
        EasyMock.expectLastCall().once();

        EasyMock.expect(dice.getTotal()).andReturn(Constants.MIN_DICE_ROLL);

        gameEngine.movePlayer(player, Constants.MIN_DICE_ROLL);
        EasyMock.expectLastCall().once();

        EasyMock.expect(player.isBankrupt()).andReturn(true);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, dice, boardView, playerInfoView, diceView, cardView, player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleRollDice();

        EasyMock.verify(gameEngine, dice,boardView, playerInfoView, diceView, cardView, player);
    }

    @Test
    public void TC23_handleTileAction_WithNullAction_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        assertThrows(NullPointerException.class, () -> gameController.handleTileAction(null));

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC24_handleTileAction_WithValidNoOpAction_RefreshesViewsOnly() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        EasyMock.expect(action.getType()).andReturn(TileActionType.NONE);


        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, action, cardView, dice);
        gameController.handleTileAction(action);
        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, action, cardView, dice);
    }

    @Test
    public void TC25_handleTileAction_WithPurchaseAtExactBalance_AllowsPurchase() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);

        EasyMock.expect(action.getType()).andReturn(TileActionType.OFFER_PURCHASE);
        EasyMock.expect(action.getTile()).andReturn(property);
        EasyMock.expect(action.getPlayer()).andReturn(player);
        EasyMock.expect(property.purchase(player)).andReturn(true);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player, property);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player, property);
    }

    @Test
    public void TC26_handleTileAction_WithOptionalPurchaseAboveBalance_DoesNotPurchase() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Player player = EasyMock.createMock(Player.class);
        Property property = EasyMock.createMock(Property.class);

        EasyMock.expect(action.getType()).andReturn(TileActionType.OFFER_PURCHASE);
        EasyMock.expect(action.getTile()).andReturn(property);
        EasyMock.expect(action.getPlayer()).andReturn(player);
        EasyMock.expect(property.purchase(player)).andReturn(false);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player, property);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player, property);
    }

    @Test
    public void TC27_handleTileAction_WithMandatoryPaymentAtExactBalance_PaysSuccessfully() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Player player = EasyMock.createMock(Player.class);
        double paymentAmount = 100.0;

        EasyMock.expect(action.getType()).andReturn(TileActionType.PAY_BANK);
        EasyMock.expect(action.getPlayer()).andReturn(player);
        EasyMock.expect(action.getAmount()).andReturn(paymentAmount);
        EasyMock.expect(player.remove(paymentAmount)).andReturn(true);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player);
    }

    @Test
    public void TC28_handleTileAction_WithMandatoryPaymentAboveBalance_TriggersBankruptcy() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Player player = EasyMock.createMock(Player.class);
        double paymentAmount = 100.0;

        EasyMock.expect(action.getType()).andReturn(TileActionType.PAY_BANK);
        EasyMock.expect(action.getPlayer()).andReturn(player);
        EasyMock.expect(action.getAmount()).andReturn(paymentAmount);
        EasyMock.expect(player.remove(paymentAmount)).andReturn(false);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player);
    }

    @Test
    public void TC29_handleTileAction_WithTaxPaymentAboveBalance_TriggersBankruptcy() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Player player = EasyMock.createMock(Player.class);
        double taxAmount = 100.0;

        EasyMock.expect(action.getType()).andReturn(TileActionType.PAY_TAX);
        EasyMock.expect(action.getPlayer()).andReturn(player);
        EasyMock.expect(action.getAmount()).andReturn(taxAmount);
        EasyMock.expect(player.remove(taxAmount)).andReturn(false);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                player);
    }

    @Test
    public void TC30_handleTileAction_WithActionForWrongTile_RejectsAction() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Tile tile = EasyMock.createMock(Tile.class);

        EasyMock.expect(action.getType()).andReturn(TileActionType.OFFER_PURCHASE);
        EasyMock.expect(action.getTile()).andReturn(tile);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                tile);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action,
                tile);
    }

    @Test
    public void TC31_handleEndTurn_WithTwoPlayers_AdvancesToSecondPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);

        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleEndTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC32_handleEndTurn_WithMaximumPlayersMiddleTurn_AdvancesToNextPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);

        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleEndTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC33_handleEndTurn_WithMaximumPlayersAtLastTurn_WrapsToFirstPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);

        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleEndTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC34_handleEndTurn_WithOneActivePlayer_EndsGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.expect(gameEngine.isGameOver()).andReturn(true);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleEndTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC35_handleEndTurn_WhenNextPlayerIsBankrupt_SkipsRemovedPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);

        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleEndTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC36_handleBankruptcy_WithNullPlayer_ThrowsException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        assertThrows(NullPointerException.class, () -> gameController.handleBankruptcy(null));

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC37_handleBankruptcy_WithPlayerNotInGame_DoesNotChangeGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleBankruptcy(player);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);
    }

    @Test
    public void TC38_handleBankruptcy_WithThreePlayers_RemovesPlayerAndContinuesGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleBankruptcy(player);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);
    }

    @Test
    public void TC39_handleBankruptcy_WithTwoPlayers_RemovesPlayerAndEndsGame() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleBankruptcy(player);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);
    }



    @Test
    public void TC40_refreshViews_WithAllViewsPresent_UpdatesEveryView() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        gameController.refreshViews();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC41_refreshViews_WithNullBoardView_ThrowsExceptionBeforePartialUpdate() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        EasyMock.replay(gameEngine, playerInfoView, diceView, cardView, dice);

        assertThrows(NullPointerException.class, () -> new GameController(
                gameEngine, null, playerInfoView, diceView, cardView, dice
        ));

        EasyMock.verify(gameEngine, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC42_refreshViews_WhenPlayerAtFirstBoardIndex_RendersPositionZero() {
        Player player = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        expectRefreshViewsWithPlayerPosition(
                gameEngine, boardView, playerInfoView, diceView, cardView, player, 0
        );

        EasyMock.replay(player, board, gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.refreshViews();

        EasyMock.verify(player, board, gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC43_refreshViews_WhenPlayerAtLastBoardIndex_RendersPositionThirtyOne() {
        Player player = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        int lastBoardIndex = Constants.BOARD_SIZE - 1;

        expectRefreshViewsWithPlayerPosition(
                gameEngine, boardView, playerInfoView, diceView, cardView, player, lastBoardIndex
        );

        EasyMock.replay(player, board, gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.refreshViews();

        EasyMock.verify(player, board, gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC44_refreshViews_WhenNoCardIsActive_ClearsCardView() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.refreshViews();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC45_refreshViews_WhenCardIsActive_DisplaysCurrentCard() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = EasyMock.createMock(TileAction.class);
        Card card = EasyMock.createMock(Card.class);

        EasyMock.expect(action.getType()).andReturn(TileActionType.DRAW_CARD);
        EasyMock.expect(action.getCard()).andReturn(card);

        expectRefreshViewsWithCard(gameEngine, boardView, playerInfoView, diceView, cardView, card);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action, card);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, action, card);
    }

    @Test
    public void TC46_refreshViews_AfterBankruptcy_RemovesPlayerFromVisibleTurnOrder() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player remainingPlayer = EasyMock.createMock(Player.class);
        Player bankruptPlayer = EasyMock.createMock(Player.class);

        gameEngine.removeBankruptPlayer(bankruptPlayer);
        EasyMock.expectLastCall().once();

        expectRefreshViewsWithPlayerPosition(
                gameEngine, boardView, playerInfoView, diceView, cardView, remainingPlayer, 0
        );

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                remainingPlayer, bankruptPlayer);

        GameController gameController = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice
        );

        gameController.handleBankruptcy(bankruptPlayer);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                remainingPlayer, bankruptPlayer);
    }



}
