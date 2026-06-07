package controller;

import model.GameEngine;

import java.util.Objects;

import model.Dice;
import model.Player;
import util.Constants;
public class JailController {
    private final GameEngine gameEngine;
    private final Dice dice;
    public JailController(GameEngine gameEngine, Dice dice){
        this.gameEngine = gameEngine;
        this.dice = dice;

    }

    public boolean sendToJail(Player player){
        Objects.requireNonNull(player, "Player cannot be null");
        if (!player.getActive()) {
            return false;
        }
        gameEngine.setPlayerPosition(player, Constants.JAIL_POSITION);
        return player.goToJail(Constants.JAIL_POSITION);
    }

    public boolean releaseFromJail(Player player){
        Objects.requireNonNull(player, "Player cannot be null");
        return player.leaveJail();
    }
}
