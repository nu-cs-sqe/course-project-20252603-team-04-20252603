package model;

import java.util.ArrayList;
import java.util.List;

public class StockMarketCrashCardEffect implements CardEffect {

    private static final double LOSS = 200.0;

    @Override
    public void apply(Object player, Object game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        GameEngine g = (GameEngine) game;
        List<Player> snapshot = new ArrayList<>(g.getActivePlayers());
        for (Player p : snapshot) {
            if (p.canAfford(LOSS)) {
                p.remove(LOSS);
            } else if (p.getOwnedProperties().isEmpty()) {
                g.removeBankruptPlayer(p);
            }
        }
    }
}
