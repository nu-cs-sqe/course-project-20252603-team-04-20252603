package controller;

import model.Player;

import java.util.List;
import java.util.Objects;

public class GameController {

    public void startGame(List<Player> players) {
        Objects.requireNonNull(players, "Player cannot be null");
        if (players.size() < 2) {
            throw new IllegalArgumentException("At least two players are required to start the game");
        }
    }
}
