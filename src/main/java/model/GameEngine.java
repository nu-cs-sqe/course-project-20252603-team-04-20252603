package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameEngine {

    private final List<Player> players;
    private GameStatus status;
    private int currentPlayerIndex;

    public GameEngine(List<Player> players) {
        this.players = players;
        this.status = GameStatus.NOT_STARTED;
        this.currentPlayerIndex = 0;
    }

    public void startGame() {
        if (players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required to start the game");
        }
        status = GameStatus.IN_PROGRESS;
    }

    public GameStatus getStatus(){
        return status; 
    }
    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }
}
