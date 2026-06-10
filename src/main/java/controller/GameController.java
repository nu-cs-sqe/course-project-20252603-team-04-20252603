package controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.*;
import util.Constants;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.BankruptcyView;
import view.PlayerInfoView;
import view.PropertyPromptView;
import view.RentConfirmationView;

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
    private JailController jailController;
    private CardController cardController;
    private PropertyPromptView propertyPromptView;
    private BankruptcyView bankruptcyView;
    private RentConfirmationView rentConfirmationView;

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
            return;
        }
        if (actionType == TileActionType.COLLECT_MONEY) {
            activeCard = null;
            action.getPlayer().receive(action.getAmount());
            refreshViews();
            return;
        }
        if (actionType == TileActionType.GO_TO_JAIL) {
            activeCard = null;
            jailController.sendToJail(action.getPlayer());
            refreshViews();
        }
    }

    private void payRent(Player renter, Tile tile) {
        if (!(tile instanceof Property)) {
            refreshViews();
            return;
        }
        Property property = (Property) tile;
        if (propertyController.handleRentPayment(renter, property)) {
            showRentPaid(renter, property);
            refreshViews();
            return;
        }
        double rent = property.getRent();
        if (propertyController.handleForcedSale(renter, rent)
                && propertyController.handleRentPayment(renter, property)) {
            showRentPaid(renter, property);
            refreshViews();
            return;
        }
        eliminate(renter);
    }

    /** Takes a mandatory payment from the bank, forcing property sales before eliminating the player. */
    private void requirePayment(Player player, double amount) {
        if (player.remove(amount)) {
            refreshViews();
            return;
        }
        if (propertyController.handleForcedSale(player, amount) && player.remove(amount)) {
            refreshViews();
            return;
        }
        eliminate(player);
    }

    /** Removes a player who cannot meet a required payment, announcing the elimination. */
    private void eliminate(Player player) {
        if (bankruptcyView != null) {
            bankruptcyView.showPlayerEliminated(player);
        }
        handleBankruptcy(player);
    }

    private void showRentPaid(Player renter, Property property) {
        if (rentConfirmationView != null) {
            rentConfirmationView.showRentPaid(renter, property);
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
        if (activeCard == null) {
            diceView.enableRollButton();
            cardView.close();
        } else {
            diceView.disableRollButton();
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
            justification = "Optional collaborators supplied by the application wiring.")
    public void setJailController(JailController jailController) {
        this.jailController = jailController;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Optional collaborators supplied by the application wiring.")
    public void setCardController(CardController cardController) {
        this.cardController = cardController;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Optional view collaborator supplied by the application wiring.")
    public void setPropertyPromptView(PropertyPromptView propertyPromptView) {
        this.propertyPromptView = propertyPromptView;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Optional view collaborator supplied by the application wiring.")
    public void setBankruptcyView(BankruptcyView bankruptcyView) {
        this.bankruptcyView = bankruptcyView;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Optional view collaborator supplied by the application wiring.")
    public void setRentConfirmationView(RentConfirmationView rentConfirmationView) {
        this.rentConfirmationView = rentConfirmationView;
    }

    /** Plays one full turn for the current player: roll, move, GO-pass bonus, then tile resolution. */
    public void playTurn() {
        Player current = gameEngine.getCurrentPlayer();
        if (current.isBankrupt()) {
            handleBankruptcy(current);
            return;
        }
        if (current.inJail()) {
            playJailTurn(current);
            return;
        }
        int oldPosition = gameEngine.getPlayerPosition(current);
        rollAndMove(current);
        grantGoBonusIfPassed(current, oldPosition);
        resolveLanding();
    }

    private void playJailTurn(Player current) {
        boolean escaped = jailController.attemptRollDoubles(current);
        if (!escaped && current.getJailTurnCount() >= Constants.MAX_JAIL_TURNS) {
            jailController.payJailFee(current);
        }
        refreshViews();
    }

    private void rollAndMove(Player current) {
        dice.roll();
        diceView.showRollResult(dice.getDieOne(), dice.getDieTwo());
        gameEngine.movePlayer(current, dice.getTotal());
        refreshPlayerPosition(current);
    }

    private void refreshPlayerPosition(Player player) {
        boardView.updatePlayerPosition(player, gameEngine.getPlayerPosition(player));
    }

    private void grantGoBonusIfPassed(Player current, int oldPosition) {
        int newPosition = gameEngine.getPlayerPosition(current);
        if (gameEngine.didPassGo(oldPosition, newPosition)) {
            handleTileAction(new TileAction(
                    TileActionType.COLLECT_MONEY, current, null, null, Constants.GO_BONUS));
        }
    }

    /** Resolves the effect of the tile the current player has landed on. */
    public void resolveLanding() {
        Player player = gameEngine.getCurrentPlayer();
        Tile tile = gameEngine.getTile(gameEngine.getPlayerPosition(player));
        if (tile instanceof Property) {
            resolveProperty(player, (Property) tile);
        } else if (tile instanceof IRSTile) {
            requirePayment(player, Constants.GO_BONUS);
        } else if (tile instanceof GoToJailTile) {
            handleTileAction(new TileAction(TileActionType.GO_TO_JAIL, player, tile, null, 0));
        } else if (tile instanceof ChanceTile) {
            drawChanceCard(player);
        } else {
            refreshViews();
        }
    }

    private void drawChanceCard(Player player) {
        activeCard = cardController.drawChanceCard(player);
        refreshViews();
    }

    /** Applies the chance card currently shown to the player, then clears it. */
    public void applyDrawnCard() {
        if (activeCard == null) {
            refreshViews();
            return;
        }
        Card card = activeCard;
        cardController.applyCard(card, gameEngine.getCurrentPlayer());
        activeCard = null;
        refreshViews();
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
        diceView.disableRollButton();
        propertyPromptView.showProperty(property, player);
        propertyPromptView.setBuyListener(event ->
                handleTileAction(new TileAction(TileActionType.OFFER_PURCHASE, player, property, null, price)));
        propertyPromptView.setDeclineListener(event ->
                handleTileAction(new TileAction(TileActionType.NONE, player, property, null, 0)));
    }

}
