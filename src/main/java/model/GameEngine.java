package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class GameEngine {

    private final List<Player> players;
    private final Board board;
    private GameStatus status;
    private int currentPlayerIndex;

    public GameEngine(List<Player> players) {
        this(players, null);
    }

    public GameEngine(List<Player> players, Board board) {
        this.players = new ArrayList<>(players);
        this.board = board;
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

    public List<Player> getActivePlayers() {
        return Collections.unmodifiableList(players);
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

    public boolean isGameOver() {
        if (status == GameStatus.GAME_OVER) {
            return true;
        }
        return players.size() < 2;
    }

    public Optional<Player> getWinner() {
        if (players.size() != 1 || status != GameStatus.GAME_OVER) {
            return Optional.empty();
        }
        return Optional.of(players.get(0));
    }

    public Tile getTile(int index) {
        return board.getTile(index);
    }

    public int getPlayerPosition(Player player) {
        return board.getPlayerPosition(player);
    }

    public void setPlayerPosition(Player player, int index) {
        board.setPlayerPosition(player, index);
    }

    public void movePlayer(Player player, int spaces) {
        board.movePlayer(player, spaces);
    }

    public boolean didPassGo(int oldPosition, int newPosition) {
        return board.didPassGo(oldPosition, newPosition);
    }

    public int getBoardSize() {
        return board.getBoardSize();
    }

}
