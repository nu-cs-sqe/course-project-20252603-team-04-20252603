package controller;

import model.Dice;
import model.GameEngine;
import model.Player;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JailControllerTests {

    // TC1: sendToJail - Null player
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

    // TC2: sendToJail - Inactive player
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

}
