package controller;

import model.Dice;
import model.GameEngine;
import model.IRSTile;
import model.Player;
import model.Property;
import model.Tile;
import model.TileAction;
import model.TileActionType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;
import view.PropertyPromptView;

import java.util.List;

public class GameControllerTurnTests {

    /** Expects the minimal refreshViews() interaction (no active players, no active card). */
    private void expectRefreshViews(GameEngine gameEngine, BoardView boardView,
                                    PlayerInfoView playerInfoView, DiceView diceView, CardView cardView) {
        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(List.of());
        boardView.refresh();
        EasyMock.expectLastCall().once();
        playerInfoView.renderPlayers(List.of());
        EasyMock.expectLastCall().once();
        diceView.enableRollButton();
        EasyMock.expectLastCall().once();
        cardView.close();
        EasyMock.expectLastCall().once();
    }

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

    @Test
    public void TC47_resolveLanding_OnUnownedUnaffordableProperty_RefreshesWithoutPrompt() {
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
        EasyMock.expect(player.canAfford(100.0)).andReturn(false);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyPromptView(prompt);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);
    }

    @Test
    public void TC57_handleTileAction_WithPayRentAffordable_TransfersRent() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);
        TileAction action = new TileAction(TileActionType.PAY_RENT, player, property, null, 0);

        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, property, player);
    }

    @Test
    public void TC48_resolveLanding_OnPropertyOwnedByAnother_ChargesRent() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(7);
        EasyMock.expect(gameEngine.getTile(7)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.expect(property.isOwnedBy(player)).andReturn(false);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, property, player);
    }

    @Test
    public void TC49_resolveLanding_OnPropertyOwnedBySelf_RefreshesOnly() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(7);
        EasyMock.expect(gameEngine.getTile(7)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.expect(property.isOwnedBy(player)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                property, player);
    }

    @Test
    public void TC53_resolveLanding_OnNeutralTile_RefreshesOnly() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Tile tile = EasyMock.createMock(Tile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(20);
        EasyMock.expect(gameEngine.getTile(20)).andReturn(tile);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                tile, player);
    }

    @Test
    public void TC50_resolveLanding_OnIrsTile_PaysTax() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        IRSTile tile = EasyMock.createMock(IRSTile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(11);
        EasyMock.expect(gameEngine.getTile(11)).andReturn(tile);
        EasyMock.expect(player.remove(util.Constants.GO_BONUS)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                tile, player);
    }

    @Test
    public void TC58_handleTileAction_WithCollectMoney_CreditsPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);
        TileAction action = new TileAction(
                TileActionType.COLLECT_MONEY, player, null, null, util.Constants.GO_BONUS);

        EasyMock.expect(player.receive(util.Constants.GO_BONUS)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);
    }

    @Test
    public void TC59_handleTileAction_WithGoToJail_SendsToJail() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        Player player = EasyMock.createMock(Player.class);
        TileAction action = new TileAction(TileActionType.GO_TO_JAIL, player, null, null, 0);

        EasyMock.expect(jailController.sendToJail(player)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);
    }
}
