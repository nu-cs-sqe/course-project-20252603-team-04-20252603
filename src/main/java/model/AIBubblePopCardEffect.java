package model;

public class AIBubblePopCardEffect implements CardEffect {

    private static final double FEE = 500.0;

    @Override
    public void apply(Object player, Object game) {
        if (player == null || game == null) {
            throw new IllegalArgumentException("player and game must not be null");
        }
        Player p = (Player) player;
        GameEngine g = (GameEngine) game;
        if (!p.getActive()) {
            throw new IllegalArgumentException("player must be active");
        }
        if (p.canAfford(FEE)) {
            p.remove(FEE);
        } else if (p.getOwnedProperties().isEmpty()) {
            g.removeBankruptPlayer(p);
        }
    }
}
