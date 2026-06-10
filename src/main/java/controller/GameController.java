package controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.*;
import util.Constants;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;
import view.PropertyPromptView;

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

    private PropertyController propertyController;
    private PropertyPromptView propertyPromptView;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "GameController intentionally keeps references to model and view collaborators supplied "
                    + "by the application wiring.")
    public GameController(GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView,
                    CardView cardView, Dice dice) {
        this.gameEngine = Objects.requireNonNull(gameEngine, "GameEngine cannot be null");
        this.boardView = Objects.requireNonNull(boardView, "BoardView cannot be null");
        this.playerInfoView = Objects.requireNonNull(playerInfoView, "PlayerInfoView cannot be null");
        this.diceView = Objects.requireNonNull(diceView, "DiceView cannot be null");
        this.cardView = Objects.requireNonNull(cardView, "CardView cannot be null");
        this.dice = Objects.requireNonNull(dice, "Dice cannot be null");
    }

    public void startGame(List<Player> players) {
        Objects.requireNonNull(players, "Players cannot be null");

        if (players.size() < Constants.MIN_NUM_PLAYERS || players.size() > Constants.MAX_NUM_PLAYERS) {
            throw new IllegalArgumentException("Player count must be between " + Constants.MIN_NUM_PLAYERS + " and "
                    + Constants.MAX_NUM_PLAYERS);
        }
        for (Player player : players) {
            Objects.requireNonNull(player, "Player cannot be null");
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
            return;
        }
        if (actionType == TileActionType.PAY_RENT) {
            activeCard = null;
            payRent(action.getPlayer(), action.getTile());
        }
    }

    private void payRent(Player renter, Tile tile) {
        if (!(tile instanceof Property)) {
            refreshViews();
            return;
        }
        boolean paid = propertyController.handleRentPayment(renter, (Property) tile);
        if (!paid) {
            handleBankruptcy(renter);
            return;
        }
        refreshViews();
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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Optional collaborators supplied by the application wiring.")
    public void setPropertyController(PropertyController propertyController) {
        this.propertyController = propertyController;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Optional view collaborator supplied by the application wiring.")
    public void setPropertyPromptView(PropertyPromptView propertyPromptView) {
        this.propertyPromptView = propertyPromptView;
    }

    /** Resolves the effect of the tile the current player has landed on. */
    public void resolveLanding() {
        Player player = gameEngine.getCurrentPlayer();
        Tile tile = gameEngine.getTile(gameEngine.getPlayerPosition(player));
        if (tile instanceof Property) {
            resolveProperty(player, (Property) tile);
        }
    }

    private void resolveProperty(Player player, Property property) {
        if (property.isOwned()) {
            if (property.isOwnedBy(player)) {
                refreshViews();
            } else {
                handleTileAction(new TileAction(TileActionType.PAY_RENT, player, property, null, 0));
            }
            return;
        }
        double price = property.getPrice();
        if (player.canAfford(price)) {
            offerPurchase(player, property, price);
        } else {
            refreshViews();
        }
    }

    private void offerPurchase(Player player, Property property, double price) {
        propertyPromptView.showProperty(property, player);
        propertyPromptView.setBuyListener(event ->
                handleTileAction(new TileAction(TileActionType.OFFER_PURCHASE, player, property, null, price)));
        propertyPromptView.setDeclineListener(event ->
                handleTileAction(new TileAction(TileActionType.NONE, player, property, null, 0)));
    }

}
