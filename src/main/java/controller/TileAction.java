package controller;

import java.util.Optional;

import model.Card;
import model.Player;
import model.Tile;

public class TileAction {

    private final TileActionType type;
    private final Player player;
    private final Tile tile;
    private final Optional<Card> card;
    private final double amount;

    private TileAction(TileActionType type,
                       Player player,
                       Tile tile,
                       Optional<Card> card,
                       double amount) {
        this.type = type;
        this.player = player;
        this.tile = tile;
        this.card = card;
        this.amount = amount;
    }

    public static TileAction none(Player player, Tile tile) {
        return new TileAction(TileActionType.NONE, player, tile, Optional.empty(), 0.0);
    }

    public static TileAction offerPurchase(Player player, Tile tile) {
        return new TileAction(TileActionType.OFFER_PURCHASE, player, tile, Optional.empty(), 0.0);
    }

    public static TileAction payRent(Player player, Tile tile, double rent) {
        return new TileAction(TileActionType.PAY_RENT, player, tile, Optional.empty(), rent);
    }

    public static TileAction drawCard(Player player, Tile tile, Card card) {
        return new TileAction(TileActionType.DRAW_CARD, player, tile, Optional.of(card), 0.0);
    }

    public static TileAction payTax(Player player, Tile tile, double amount) {
        return new TileAction(TileActionType.PAY_TAX, player, tile, Optional.empty(), amount);
    }

    public static TileAction goToJail(Player player, Tile tile) {
        return new TileAction(TileActionType.GO_TO_JAIL, player, tile, Optional.empty(), 0.0);
    }

    public static TileAction bankruptcyCheck(Player player, Tile tile) {
        return new TileAction(TileActionType.BANKRUPTCY_CHECK, player, tile, Optional.empty(), 0.0);
    }


    public TileActionType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getTile() {
        return tile;
    }

    public Optional<Card> getCard() {
        return card;
    }

    public double getAmount() {
        return amount;
    }
}