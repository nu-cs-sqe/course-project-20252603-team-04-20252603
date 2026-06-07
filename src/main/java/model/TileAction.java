package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Objects;

import static java.lang.Double.isNaN;


public class TileAction {

    private final TileActionType type;
    private final Player player;
    private final Tile tile;
    private final Card card;
    private final double amount;

    @SuppressFBWarnings(
            value = {"CT_CONSTRUCTOR_THROW", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
            justification = "Constructor validates invariants and intentionally stores model references for controller context.")
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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "TileAction intentionally exposes the player reference as controller context.")
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
