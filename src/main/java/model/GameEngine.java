package model;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private final List<Player> players;
    private GameStatus status;
    private int currentPlayerIndex;

    public GameEngine(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.status = GameStatus.NOT_STARTED;
        this.currentPlayerIndex = 0;
    }

    public void startGame() {
        if (players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required to start the game");
        }
        if (players.size() > 4) {
            throw new IllegalArgumentException("At most 4 players are allowed to start the game");
        }
        status = GameStatus.IN_PROGRESS;
    }

    public GameStatus getStatus(){
        return status; 
    }
    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public void nextTurn(){
        currentPlayerIndex = currentPlayerIndex + 1;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public void removeBankruptPlayer(Player player) {
        int removeIdx = players.indexOf(player);
        if (removeIdx < 0) {
            return;
        }
        players.remove(removeIdx);
        if (removeIdx < currentPlayerIndex) {
            currentPlayerIndex--;
        }
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
        if (players.size() == 1) {
            status = GameStatus.GAME_OVER;
        }
    }

}
