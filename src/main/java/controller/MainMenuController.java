package controller;

import util.Constants;

import javax.swing.*;
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

    public void createPlayerConfigs() {
        throw new IllegalArgumentException("Player count must be between 2 and 4");
    }

}