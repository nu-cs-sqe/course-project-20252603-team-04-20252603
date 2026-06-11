package controller;

import model.Dice;
import model.GameEngine;
import model.Player;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import util.Constants;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JailControllerTests {

    @Test
    public void TC1_SendToJail_NullPlayer_ThrowsNullPointerException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(gameEngine, dice);

        JailController controller = new JailController(gameEngine, dice);

        assertThrows(NullPointerException.class,
                () -> controller.sendToJail(null),
                "sendToJail must reject a null player");

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC2_SendToJail_InactivePlayer_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.sendToJail(player),
                "sendToJail must return false for an inactive player");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC3_SendToJail_ActivePlayerNotInJail_ReturnsTrue() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        gameEngine.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expectLastCall();
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.sendToJail(player),
                "sendToJail must return true for an active player not in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC4_SendToJail_ActivePlayerAlreadyInJail_ReturnsTrue() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        gameEngine.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expectLastCall();
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(true);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.sendToJail(player),
                "sendToJail must return true for an active player already in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC5_ReleaseFromJail_NullPlayer_ThrowsNullPointerException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(gameEngine, dice);

        JailController controller = new JailController(gameEngine, dice);

        assertThrows(NullPointerException.class,
                () -> controller.releaseFromJail(null),
                "releaseFromJail must reject a null player");

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC6_ReleaseFromJail_PlayerInJail_ReturnsTrue() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.leaveJail()).andReturn(true);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.releaseFromJail(player),
                "releaseFromJail must return true when the player leaves jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC7_ReleaseFromJail_PlayerNotInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.leaveJail()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.releaseFromJail(player),
                "releaseFromJail must return false when the player is not in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC8_PayJailFee_NullPlayer_ThrowsNullPointerException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(gameEngine, dice);

        JailController controller = new JailController(gameEngine, dice);

        assertThrows(NullPointerException.class,
                () -> controller.payJailFee(null),
                "payJailFee must reject a null player");

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC9_PayJailFee_PlayerNotInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.payJailFee(player),
                "payJailFee must return false when the player is not in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC10_PayJailFee_PlayerInJailCannotAfford_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.JAIL_FEE)).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.payJailFee(player),
                "payJailFee must return false when the player cannot afford the fee");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC11_PayJailFee_PlayerInJailExactBalance_ReturnsTrue() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.leaveJail()).andReturn(true);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.payJailFee(player),
                "payJailFee must return true when the player pays the exact jail fee");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC12_PayJailFee_PlayerInJailSurplusBalance_ReturnsTrue() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.leaveJail()).andReturn(true);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.payJailFee(player),
                "payJailFee must return true when the player has more than the jail fee");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC13_PayJailFee_InactivePlayerInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.payJailFee(player),
                "payJailFee must return false for an inactive player in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC14_AttemptRollDoubles_NullPlayer_ThrowsNullPointerException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(gameEngine, dice);

        JailController controller = new JailController(gameEngine, dice);

        assertThrows(NullPointerException.class,
                () -> controller.attemptRollDoubles(null),
                "attemptRollDoubles must reject a null player");

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC15_AttemptRollDoubles_PlayerNotInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.attemptRollDoubles(player),
                "attemptRollDoubles must return false when the player is not in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC16_AttemptRollDoubles_PlayerInJailRollsDoubles_ReturnsTrue() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        dice.roll();
        EasyMock.expectLastCall();
        EasyMock.expect(dice.isDoubles()).andReturn(true);
        EasyMock.expect(player.leaveJail()).andReturn(true);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.attemptRollDoubles(player),
                "attemptRollDoubles must return true when the player rolls doubles");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC17_AttemptRollDoubles_PlayerInJailNoDoubles_IncrementsTurnCount() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        dice.roll();
        EasyMock.expectLastCall();
        EasyMock.expect(dice.isDoubles()).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(1);
        player.incrementJailTurnCount();
        EasyMock.expectLastCall();
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.attemptRollDoubles(player),
                "attemptRollDoubles must return false when the player does not roll doubles");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC18_AttemptRollDoubles_PlayerInJailNoDoublesAtMaxTurns_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        dice.roll();
        EasyMock.expectLastCall();
        EasyMock.expect(dice.isDoubles()).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(Constants.MAX_JAIL_TURNS);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.attemptRollDoubles(player),
                "attemptRollDoubles must return false when the player is at max jail turns");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC19_AttemptRollDoubles_InactivePlayerInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.attemptRollDoubles(player),
                "attemptRollDoubles must return false for an inactive player in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC20_HandleJailTurn_NullPlayer_ThrowsNullPointerException() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        EasyMock.replay(gameEngine, dice);

        JailController controller = new JailController(gameEngine, dice);

        assertThrows(NullPointerException.class,
                () -> controller.handleJailTurn(null),
                "handleJailTurn must reject a null player");

        EasyMock.verify(gameEngine, dice);
    }

    @Test
    public void TC21_HandleJailTurn_PlayerNotInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.handleJailTurn(player),
                "handleJailTurn must return false when the player is not in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC22_HandleJailTurn_PlayerInJailFirstTurn_IncrementsTurnCount() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(1);
        player.incrementJailTurnCount();
        EasyMock.expectLastCall();
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.handleJailTurn(player),
                "handleJailTurn must return true when the first jail turn is recorded");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC23_HandleJailTurn_PlayerInJailSecondTurn_IncrementsTurnCount() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(2);
        player.incrementJailTurnCount();
        EasyMock.expectLastCall();
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.handleJailTurn(player),
                "handleJailTurn must return true when the second jail turn is recorded");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC24_HandleJailTurn_PlayerInJailThirdTurn_DoesNotIncrementTurnCount() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(Constants.MAX_JAIL_TURNS);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertTrue(controller.handleJailTurn(player),
                "handleJailTurn must return true when the player is at max jail turns");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC25_HandleJailTurn_InactivePlayerInJail_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.handleJailTurn(player),
                "handleJailTurn must return false for an inactive player in jail");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC_GAP1_SendToJail_GoToJailFails_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        gameEngine.setPlayerPosition(player, Constants.JAIL_POSITION);
        EasyMock.expectLastCall();
        EasyMock.expect(player.goToJail(Constants.JAIL_POSITION)).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.sendToJail(player),
                "sendToJail must return false when goToJail fails");

        EasyMock.verify(gameEngine, dice, player);
    }
    @Test
    public void TC_GAP2_PayJailFee_RemoveFails_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.JAIL_FEE)).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.payJailFee(player),
                "payJailFee must return false when remove fails");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC_GAP3_PayJailFee_LeaveJailFails_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.canAfford(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.remove(Constants.JAIL_FEE)).andReturn(true);
        EasyMock.expect(player.leaveJail()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.payJailFee(player),
                "payJailFee must return false when leaveJail fails");

        EasyMock.verify(gameEngine, dice, player);
    }

    @Test
    public void TC_GAP4_AttemptRollDoubles_DoublesButLeaveJailFails_ReturnsFalse() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(player.inJail()).andReturn(true);
        dice.roll();
        EasyMock.expectLastCall();
        EasyMock.expect(dice.isDoubles()).andReturn(true);
        EasyMock.expect(player.leaveJail()).andReturn(false);
        EasyMock.replay(gameEngine, dice, player);

        JailController controller = new JailController(gameEngine, dice);

        assertFalse(controller.attemptRollDoubles(player),
                "attemptRollDoubles must return false when leaveJail fails after rolling doubles");

        EasyMock.verify(gameEngine, dice, player);
    }
}
