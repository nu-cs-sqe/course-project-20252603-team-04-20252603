package controller;

import util.Constants;

public class MainMenuController {

    public boolean validatePlayerCount(int count) {
        return count == Constants.MIN_NUM_PLAYERS;
    }

}