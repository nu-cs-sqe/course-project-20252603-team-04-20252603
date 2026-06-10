package controller;

import model.Dice;
import model.GameEngine;
import model.Player;
import model.Property;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;
import view.PropertyPromptView;

public class GameControllerTurnTests {

    @Test
    public void TC46_resolveLanding_OnUnownedAffordableProperty_ShowsPurchasePrompt() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyPromptView prompt = EasyMock.createMock(PropertyPromptView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(5);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        prompt.showProperty(property, player);
        EasyMock.expectLastCall();
        prompt.setBuyListener(EasyMock.anyObject());
        EasyMock.expectLastCall();
        prompt.setDeclineListener(EasyMock.anyObject());
        EasyMock.expectLastCall();

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyPromptView(prompt);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);
    }
}
