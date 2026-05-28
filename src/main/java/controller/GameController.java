package controller;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import model.Card;
import model.Deck;
import model.Dice;
import model.GameEngine;
import model.GameStatus;
import model.Player;
import model.Property;
import model.Tile;
import model.TileType;
import util.Constants;

public final class GameController {

    private final GameEngine game;
    private final Dice dice;
    private final Deck chanceDeck;
    private final double taxAmount;

    private final BiFunction<Player, Tile, TileAction> noOp =
            (player, tile) -> TileAction.none(player, tile);

    private final Map<TileType, BiFunction<Player, Tile, TileAction>> handlers;

    public GameController(GameEngine game, Dice dice, Deck chanceDeck) {
        this(game, dice, chanceDeck, Constants.IRS_TAX);
    }

    public GameController(GameEngine game, Dice dice, Deck chanceDeck, double taxAmount) {
        this.game = Objects.requireNonNull(game, "GameEngine cannot be null");
        this.dice = Objects.requireNonNull(dice, "Dice cannot be null");
        this.chanceDeck = Objects.requireNonNull(chanceDeck, "Chance Deck cannot be null");
        if (taxAmount < 0) {
            throw new IllegalArgumentException("Tax amount cannot be negative");
        }
        this.taxAmount = taxAmount;
        this.handlers = buildHandlers();
    }

    private Map<TileType, BiFunction<Player, Tile, TileAction>> buildHandlers() {
        return Map.ofEntries(
                Map.entry(TileType.PROPERTY, this::handleProperty),
                Map.entry(TileType.CHANCE, this::handleChance),
                Map.entry(TileType.IRS, this::handleIrs),
                Map.entry(TileType.GOTOJAIL, this::handleGoToJail),
                Map.entry(TileType.GO, noOp),
                Map.entry(TileType.JAIL, noOp),
                Map.entry(TileType.FREE, noOp)
        );
    }

    private TileAction handleProperty(Player player, Tile tile) {
        Property property = (Property) tile;
        if (!property.isOwned()) {
            return TileAction.offerPurchase(player, tile);
        }
        if (property.isOwnedBy(player)) {
            return TileAction.none(player, tile);
        }
        if (property.chargeRent(player)) {
            return TileAction.payRent(player, tile, property.getRent());
        }
        return TileAction.bankruptcyCheck(player, tile);
    }

    private TileAction handleChance(Player player, Tile tile) {
        Card card = chanceDeck.draw();
        return TileAction.drawCard(player, tile, card);
    }

    private TileAction handleIrs(Player player, Tile tile) {
        if (player.remove(taxAmount)) {
            return TileAction.payTax(player, tile, taxAmount);
        }
        return TileAction.bankruptcyCheck(player, tile);
    }

    private TileAction handleGoToJail(Player player, Tile tile) {
        game.setPlayerPosition(player, Constants.JAIL_POSITION);
        player.goToJail(Constants.JAIL_POSITION);
        return TileAction.goToJail(player, tile);
    }

    public void startGame() {
        game.startGame();
    }

    public GameStatus getGameStatus() {
        return game.getStatus();
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public TileAction handleRollDice() {
        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game not in progress");
        }
        Player player = game.getCurrentPlayer();
        int oldPosition = game.getPlayerPosition(player);

        dice.roll();
        game.movePlayer(player, dice.getTotal());

        int newPosition = game.getPlayerPosition(player);
        if (game.didPassGo(oldPosition, newPosition)) {
            player.receive(Constants.GO_BONUS);
        }

        Tile tile = game.getTile(newPosition);
        return resolveTile(player, tile);
    }

    public TileAction resolveTile(Player player, Tile tile) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(tile, "tile");
        return handlers
                .getOrDefault(tile.getName(), noOp)
                .apply(player, tile);
    }

    public void discardChanceCard(Card card) {
        chanceDeck.discard(card);
    }

    public void endTurn() {
        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game not in progress");
        }
        game.nextTurn();
    }
}