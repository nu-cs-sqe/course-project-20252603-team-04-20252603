package controller;

import model.GameEngine;
import model.GameStatus;
import model.Player;
import util.Constants;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;

import java.util.List;
import java.util.Objects;

public class GameController {

    private GameEngine gameEngine;
    private BoardView boardView;
    private PlayerInfoView playerInfoView;
    private DiceView diceView;
    private CardView cardView;

    GameController (GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView, CardView cardView) {
        Objects.requireNonNull(gameEngine, "GameEngine cannot be null");
        this.gameEngine = gameEngine;
        this.boardView = boardView;
        this.playerInfoView = playerInfoView;
        this.diceView = diceView;
        this.cardView = cardView;

    }

    public void startGame(List<Player> players) {
        Objects.requireNonNull(players, "Players cannot be null");

        if (players.size() < Constants.MIN_NUM_PLAYERS || players.size() > Constants.MAX_NUM_PLAYERS) {
            throw new IllegalArgumentException("Cannot start a game with less than two players");
        }
        if (gameEngine.getStatus() == GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game is already in progress");
        }
        gameEngine.startGame();
    }

    public GameStatus getStatus() {
        return gameEngine.getStatus();
    }

    public Player getCurrentPlayer() {
        return gameEngine.getCurrentPlayer();
    }

    public List<Player> getActivePlayers() {
        return gameEngine.getActivePlayers();
    }

}
