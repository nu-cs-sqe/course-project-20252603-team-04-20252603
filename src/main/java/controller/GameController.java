package controller;

import model.*;
import util.Constants;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;

import java.util.List;
import java.util.Objects;

public class GameController {

    private GameEngine gameEngine;
    private BoardView boardView;
    private PlayerInfoView playerInfoView;
    private DiceView diceView;
    private CardView cardView;
    private Dice dice;
    private Card activeCard;

    GameController(GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView,
                    CardView cardView) {
        Objects.requireNonNull(gameEngine, "GameEngine cannot be null");
        this.gameEngine = gameEngine;
        this.boardView = Objects.requireNonNull(boardView, "BoardView cannot be null");
        this.playerInfoView = Objects.requireNonNull(playerInfoView, "PlayerInfoView cannot be null");
        this.diceView = Objects.requireNonNull(diceView, "DiceView cannot be null");
        this.cardView = Objects.requireNonNull(cardView, "CardView cannot be null");

    }

    GameController(GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView,
                    CardView cardView, Dice dice) {
        this(gameEngine, boardView, playerInfoView, diceView, cardView);
        this.dice = Objects.requireNonNull(dice, "Dice cannot be null");
    }

    public void startGame(List<Player> players) {
        Objects.requireNonNull(players, "Players cannot be null");

        if (players.size() < Constants.MIN_NUM_PLAYERS || players.size() > Constants.MAX_NUM_PLAYERS) {
            throw new IllegalArgumentException("Cannot start a game with less than two players");
        }
        if (gameEngine.getStatus() == GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game is already in progress");
        }
        gameEngine.startGame();
    }

    public GameStatus getStatus() {
        return gameEngine.getStatus();
    }

    public Player getCurrentPlayer() {
        return gameEngine.getCurrentPlayer();
    }

    public List<Player> getActivePlayers() {
        return gameEngine.getActivePlayers();
    }

    public void handleBankruptcy(Player player) {
        Objects.requireNonNull(player, "Player cannot be null");
        gameEngine.removeBankruptPlayer(player);
        refreshViews();
    }

    public void handleRollDice() {
        Player currentPlayer = gameEngine.getCurrentPlayer();
        if (currentPlayer.isBankrupt()) {
            return;
        }
        dice.roll();
        diceView.showRollResult(dice.getDieOne(), dice.getDieTwo());
        gameEngine.movePlayer(currentPlayer, dice.getTotal());
        if (currentPlayer.isBankrupt()) {
            handleBankruptcy(currentPlayer);
            return;
        }
        refreshViews();
    }

    public void handleTileAction(TileAction action) {
        Objects.requireNonNull(action, "TileAction cannot be null");
        TileActionType actionType = action.getType();
        if (actionType == TileActionType.NONE) {
            activeCard = null;
            refreshViews();
            return;
        }
        if (actionType == TileActionType.DRAW_CARD) {
            activeCard = action.getCard();
            refreshViews();
            return;
        }
        if (actionType == TileActionType.OFFER_PURCHASE) {
            activeCard = null;
            Tile tile = action.getTile();
            if (!(tile instanceof Property)) {
                refreshViews();
                return;
            }
            ((Property) tile).purchase(action.getPlayer());
            refreshViews();
            return;
        }
        if (actionType == TileActionType.PAY_BANK || actionType == TileActionType.PAY_TAX) {
            activeCard = null;
            Player player = action.getPlayer();
            boolean paid = player.remove(action.getAmount());
            if (!paid) {
                handleBankruptcy(player);
                return;
            }
            refreshViews();
        }
    }

    public void refreshViews() {
        List<Player> activePlayers = gameEngine.getActivePlayers();

        boardView.refresh();
        playerInfoView.renderPlayers(activePlayers);
        for (Player player : activePlayers) {
            boardView.updatePlayerPosition(player, gameEngine.getPlayerPosition(player));
            playerInfoView.updateBalance(player);
            playerInfoView.updateProperties(player);
        }
        if (!activePlayers.isEmpty()) {
            playerInfoView.showCurrentTurn(gameEngine.getCurrentPlayer());
        }
        diceView.enableRollButton();
        if (activeCard == null) {
            cardView.close();
        } else {
            cardView.showCard(activeCard);
        }
    }

    public void handleEndTurn() {
        if (gameEngine.isGameOver()) {
            refreshViews();
            return;
        }
        gameEngine.nextTurn();
        refreshViews();
    }

}
