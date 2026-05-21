package model;

import java.util.ArrayList;
import java.util.List;

import util.Constants;

public class StockMarketCrashCardEffect implements CardEffect {

    @Override
    public void apply(Object player, Object game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        GameEngine g = (GameEngine) game;
        List<Player> snapshot = new ArrayList<>(g.getActivePlayers());
        for (Player p : snapshot) {
            if (p.canAfford(Constants.STOCK_MARKET_CRASH_LOSS)) {
                p.remove(Constants.STOCK_MARKET_CRASH_LOSS);
            } else if (p.getOwnedProperties().isEmpty()) {
                g.removeBankruptPlayer(p);
            }
        }
    }
}
