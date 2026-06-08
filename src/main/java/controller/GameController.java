package controller;

import model.GameEngine;
import model.GameStatus;
import model.Player;
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
        Objects.requireNonNull(players, "Player cannot be null");
        if (players.size() < 2) {
            throw new IllegalArgumentException("At least two players are required to start the game");
        }
    }

    public GameStatus getStatus() {
        return gameEngine.getStatus();
    }

    public Player getCurrentPlayer() {
        return gameEngine.getCurrentPlayer();
    }


}
