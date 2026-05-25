package model;

@FunctionalInterface
public interface CardEffect {
    void apply(Player player, GameEngine game);
}
