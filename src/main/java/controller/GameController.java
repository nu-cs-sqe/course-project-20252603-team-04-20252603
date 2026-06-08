package controller;

import model.Player;

import java.util.List;
import java.util.Objects;

public class GameController {

    public void startGame(List<Player> players) {
        Objects.requireNonNull(players, "Player cannot be null");
        if (players.isEmpty()) {
            throw new IllegalArgumentException("At least one player is required to start the game");
        }
    }
}
