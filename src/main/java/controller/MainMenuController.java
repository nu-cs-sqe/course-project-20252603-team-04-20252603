package controller;

import model.GameEngine;
import model.Player;
import util.Constants;
import util.PlayerConfig;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainMenuController {

    private final List<String> playerNames;
    private final List<ImageIcon> playerIcons;

    public MainMenuController(List<String> playerNames, List<ImageIcon> playerIcons) {
        this.playerNames = new ArrayList<>(Objects.requireNonNull(playerNames, "Player names cannot be null"));
        this.playerIcons = new ArrayList<>(Objects.requireNonNull(playerIcons, "Player icons cannot be null"));
    }

    public boolean validatePlayerCount(int count) {
        return count >= Constants.MIN_NUM_PLAYERS && count <= Constants.MAX_NUM_PLAYERS;
    }

    public List<PlayerConfig> createPlayerConfigs() {
        if (!validatePlayerCount(playerNames.size())) {
            throw new IllegalArgumentException("Player count must be between " + Constants.MIN_NUM_PLAYERS + " and " + Constants.MAX_NUM_PLAYERS);
        }

        List<PlayerConfig> playerConfigs = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            String playerName = playerNames.get(i);

            if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty");
            }

            ImageIcon playerIcon = Objects.requireNonNull(
                    playerIcons.get(i),
                    "Player icon cannot be null"
            );

            playerConfigs.add(new PlayerConfig(playerName, playerIcon));
        }

        return playerConfigs;
    }

    public GameEngine startNewGame() {
        createPlayerConfigs();

        List<Player> players = new ArrayList<>();

        for (String playerName : playerNames) {
            players.add(new Player(playerName, Constants.STARTING_BALANCE));
        }

        GameEngine game = new GameEngine(players);
        game.startGame();

        return game;
    }

}