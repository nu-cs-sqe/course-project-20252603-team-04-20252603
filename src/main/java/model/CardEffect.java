package model;

@FunctionalInterface
public interface CardEffect {
    void apply(Object player, Object game);
}
