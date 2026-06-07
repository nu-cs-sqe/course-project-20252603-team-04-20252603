package model;

import java.util.Objects;

import static java.lang.Double.isNaN;


public final class TileAction {

    private final TileActionType type;
    private final Player player;
    private final Tile tile;
    private final Card card;
    private final double amount;

    public TileAction(TileActionType type, Player player, Tile tile, Card card, double amount) {

        Objects.requireNonNull(type, "type must not be null");
        if (amount < 0 || isNaN(amount) || amount == Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("amount must be non-negative, finite, and not NaN");
        }
        this.type = type;
        this.player = player;
        this.tile = tile;
        this.card = card;
        this.amount = amount;
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

    public Card getCard() {
        return card;
    }

    public double getAmount() {
        return amount;
    }
}
