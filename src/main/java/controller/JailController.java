package controller;

import model.GameEngine;

import java.util.Objects;

import model.Dice;
import model.Player;
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
        return true;
    }
}
