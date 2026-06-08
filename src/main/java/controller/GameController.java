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

    GameController (GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView, CardView cardView) {
        Objects.requireNonNull(gameEngine, "GameEngine cannot be null");
        this.gameEngine = gameEngine;
        this.boardView = boardView;
        this.playerInfoView = playerInfoView;
        this.diceView = diceView;
        this.cardView = cardView;

    }

    GameController (GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView, CardView cardView, Dice dice) {
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
            refreshViews();
            return;
        }
        if (actionType == TileActionType.OFFER_PURCHASE) {
            ((Property) action.getTile()).purchase(action.getPlayer());
            refreshViews();
            return;
        }
        if (actionType == TileActionType.PAY_BANK || actionType == TileActionType.PAY_TAX) {
            action.getPlayer().remove(action.getAmount());
            refreshViews();
        }
    }

    public void refreshViews() {
        boardView.refresh(gameEngine);
        playerInfoView.refresh(gameEngine);
        diceView.refresh(dice);
        cardView.refresh(gameEngine);
    }



}
