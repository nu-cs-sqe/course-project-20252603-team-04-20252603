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

    public boolean payJailFee(Player player){
        Objects.requireNonNull(player, "Player cannot be null");
        if (!player.getActive()) {
            return false;
        }
        if (!player.inJail()) {
            return false;
        }
        if (!player.canAfford(Constants.JAIL_FEE)) {
            return false;
        }
        player.remove(Constants.JAIL_FEE);
        return player.leaveJail();
    }

    public boolean attemptRollDoubles(Player player){
        Objects.requireNonNull(player, "Player cannot be null");
        if (!player.getActive()) {
            return false;
        }
        if (!player.inJail()) {
            return false;
        }
        dice.roll();
        if (dice.isDoubles()) {
            return releaseFromJail(player);
        }
        if (player.getJailTurnCount() < Constants.MAX_JAIL_TURNS) {
            player.incrementJailTurnCount();
        }
        return false;
    }

    public boolean handleJailTurn(Player player){
        Objects.requireNonNull(player, "Player cannot be null");
        if (!player.inJail()) {
            return false;
        }
        return false;
    }

}
