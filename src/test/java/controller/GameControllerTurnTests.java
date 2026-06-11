package controller;

import model.Card;
import model.ChanceTile;
import model.Dice;
import model.GameEngine;
import model.GoToJailTile;
import model.IRSTile;
import model.Player;
import model.Property;
import model.Tile;
import model.TileAction;
import model.TileActionType;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import view.BankruptcyView;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.JailStatusView;
import view.PlayerInfoView;
import view.PropertyPromptView;
import view.RentConfirmationView;

import java.awt.event.ActionListener;
import java.util.List;

public class GameControllerTurnTests {

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

    private void expectRefreshViewsWithCard(GameEngine gameEngine, BoardView boardView,
                                            PlayerInfoView playerInfoView, DiceView diceView,
                                            CardView cardView, Card card) {
        EasyMock.expect(gameEngine.getActivePlayers()).andReturn(List.of());
        boardView.refresh();
        EasyMock.expectLastCall().once();
        playerInfoView.renderPlayers(List.of());
        EasyMock.expectLastCall().once();
        diceView.disableRollButton();
        EasyMock.expectLastCall().once();
        cardView.showCard(card);
        EasyMock.expectLastCall().once();
    }

    @Test
    public void TC46_resolveLanding_OnUnownedAffordableProperty_ShowsPurchasePrompt() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
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
        diceView.disableRollButton();
        EasyMock.expectLastCall();
        prompt.setBuyListener(EasyMock.anyObject());
        EasyMock.expectLastCall();
        prompt.setDeclineListener(EasyMock.anyObject());
        EasyMock.expectLastCall();
        prompt.showProperty(property, player);
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
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
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
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        RentConfirmationView rentConfirmationView = EasyMock.createMock(RentConfirmationView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);
        TileAction action = new TileAction(TileActionType.PAY_RENT, player, property, null, 0);

        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(true);
        rentConfirmationView.showRentPaid(player, property);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, rentConfirmationView, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setRentConfirmationView(rentConfirmationView);
        controller.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, rentConfirmationView, property, player);
    }

    @Test
    public void TC48_resolveLanding_OnPropertyOwnedByAnother_ChargesRent() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        RentConfirmationView rentConfirmationView = EasyMock.createMock(RentConfirmationView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(7);
        EasyMock.expect(gameEngine.getTile(7)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.expect(property.isOwnedBy(player)).andReturn(false);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(true);
        rentConfirmationView.showRentPaid(player, property);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, rentConfirmationView, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setRentConfirmationView(rentConfirmationView);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, rentConfirmationView, property, player);
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

    @Test
    public void TC52_resolveLanding_OnGoToJailTile_SendsToJail() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        GoToJailTile tile = EasyMock.createMock(GoToJailTile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(24);
        EasyMock.expect(gameEngine.getTile(24)).andReturn(tile);
        EasyMock.expect(jailController.sendToJail(player)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, tile, player);
    }

    @Test
    public void TC51_resolveLanding_OnChanceTile_DrawsAndShowsCard() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        CardController cardController = EasyMock.createMock(CardController.class);
        ChanceTile tile = EasyMock.createMock(ChanceTile.class);
        Card card = EasyMock.createMock(Card.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(14);
        EasyMock.expect(gameEngine.getTile(14)).andReturn(tile);
        EasyMock.expect(cardController.drawChanceCard(player)).andReturn(card);
        expectRefreshViewsWithCard(gameEngine, boardView, playerInfoView, diceView, cardView, card);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                cardController, tile, card, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setCardController(cardController);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                cardController, tile, card, player);
    }

    @Test
    public void TC54_playTurn_WhenCurrentPlayerBankrupt_RemovesPlayerAndRefreshes() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(true);
        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, player);
    }

    @Test
    public void TC55_playTurn_WhenNotInJail_RollsMovesAndResolves() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Tile tile = EasyMock.createMock(Tile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player).times(2);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(0).andReturn(5).andReturn(5);
        dice.roll();
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getDieOne()).andReturn(3);
        EasyMock.expect(dice.getDieTwo()).andReturn(2);
        diceView.showRollResult(3, 2);
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getTotal()).andReturn(5);
        gameEngine.movePlayer(player, 5);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(5);
        boardView.updatePlayerPosition(player, 5);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.didPassGo(0, 5)).andReturn(false);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(tile);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);
    }

    @Test
    public void TC56_playTurn_WhenPassesGoWithoutLanding_GrantsGoBonus() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Tile tile = EasyMock.createMock(Tile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player).times(2);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(30).andReturn(3).andReturn(3).andReturn(3);
        dice.roll();
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getDieOne()).andReturn(2);
        EasyMock.expect(dice.getDieTwo()).andReturn(3);
        diceView.showRollResult(2, 3);
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getTotal()).andReturn(5);
        gameEngine.movePlayer(player, 5);
        EasyMock.expectLastCall().once();
        boardView.updatePlayerPosition(player, 3);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.didPassGo(30, 3)).andReturn(true);
        EasyMock.expect(player.receive(util.Constants.GO_BONUS)).andReturn(true);
        EasyMock.expect(gameEngine.getTile(3)).andReturn(tile);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);
    }

    @Test
    public void TC60_applyDrawnCard_WithActiveCard_AppliesEffectAndClears() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        CardController cardController = EasyMock.createMock(CardController.class);
        ChanceTile tile = EasyMock.createMock(ChanceTile.class);
        Card card = EasyMock.createMock(Card.class);
        Player player = EasyMock.createMock(Player.class);

        // First a chance tile draws and shows the card (sets the active card)...
        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player).times(2);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(14);
        EasyMock.expect(gameEngine.getTile(14)).andReturn(tile);
        EasyMock.expect(cardController.drawChanceCard(player)).andReturn(card);
        expectRefreshViewsWithCard(gameEngine, boardView, playerInfoView, diceView, cardView, card);
        // ...then Proceed applies and clears it.
        cardController.applyCard(card, player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                cardController, tile, card, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setCardController(cardController);
        controller.resolveLanding();
        controller.applyDrawnCard();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                cardController, tile, card, player);
    }

    @Test
    public void TC61_playTurn_WhenInJailAndRollsDoubles_LeavesJail() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(1);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(true);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);
    }

    @Test
    public void TC62_playTurn_WhenInJailNoDoublesBelowMaxTurns_StaysInJail() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        JailStatusView jailStatusView = EasyMock.createMock(JailStatusView.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(1);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(2);
        jailStatusView.showStillInJail(player, 2);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, jailStatusView, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.setJailStatusView(jailStatusView);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, jailStatusView, player);
    }

    @Test
    public void TC63_playTurn_WhenInJailNoDoublesAtSecondTurn_ShowsOneTurnRemaining() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        JailStatusView jailStatusView = EasyMock.createMock(JailStatusView.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(2);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(util.Constants.MAX_JAIL_TURNS);
        jailStatusView.showStillInJail(player, 1);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, jailStatusView, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.setJailStatusView(jailStatusView);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, jailStatusView, player);
    }

    @Test
    public void TC63B_playTurn_WhenInJailNoDoublesAtMaxTurns_PaysFee() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(util.Constants.MAX_JAIL_TURNS);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(util.Constants.MAX_JAIL_TURNS);
        EasyMock.expect(jailController.payJailFee(player)).andReturn(true);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);
    }

    @Test
    public void TC64_resolveLanding_OnRentShortfallForcedSaleCovers_PaysRent() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        RentConfirmationView rentConfirmationView = EasyMock.createMock(RentConfirmationView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(7);
        EasyMock.expect(gameEngine.getTile(7)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.expect(property.isOwnedBy(player)).andReturn(false);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(false);
        EasyMock.expect(property.getRent()).andReturn(75.0);
        EasyMock.expect(propertyController.handleForcedSale(player, 75.0)).andReturn(true);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(true);
        rentConfirmationView.showRentPaid(player, property);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, rentConfirmationView, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setRentConfirmationView(rentConfirmationView);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, rentConfirmationView, property, player);
    }

    @Test
    public void TC65_resolveLanding_OnRentShortfallForcedSaleFails_EliminatesPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        BankruptcyView bankruptcyView = EasyMock.createMock(BankruptcyView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(7);
        EasyMock.expect(gameEngine.getTile(7)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.expect(property.isOwnedBy(player)).andReturn(false);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(false);
        EasyMock.expect(property.getRent()).andReturn(75.0);
        EasyMock.expect(propertyController.handleForcedSale(player, 75.0)).andReturn(false);
        bankruptcyView.showPlayerEliminated(player);
        EasyMock.expectLastCall().once();
        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setBankruptcyView(bankruptcyView);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, property, player);
    }

    @Test
    public void TC66_resolveLanding_OnTaxShortfallForcedSaleCovers_PaysTax() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        IRSTile tile = EasyMock.createMock(IRSTile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(11);
        EasyMock.expect(gameEngine.getTile(11)).andReturn(tile);
        EasyMock.expect(player.remove(util.Constants.GO_BONUS)).andReturn(false).andReturn(true);
        EasyMock.expect(propertyController.handleForcedSale(player, util.Constants.GO_BONUS))
                .andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, tile, player);
    }

    @Test
    public void TC67_resolveLanding_OnTaxShortfallForcedSaleFails_EliminatesPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        BankruptcyView bankruptcyView = EasyMock.createMock(BankruptcyView.class);
        IRSTile tile = EasyMock.createMock(IRSTile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(11);
        EasyMock.expect(gameEngine.getTile(11)).andReturn(tile);
        EasyMock.expect(player.remove(util.Constants.GO_BONUS)).andReturn(false);
        EasyMock.expect(propertyController.handleForcedSale(player, util.Constants.GO_BONUS))
                .andReturn(false);
        bankruptcyView.showPlayerEliminated(player);
        EasyMock.expectLastCall().once();
        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setBankruptcyView(bankruptcyView);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, tile, player);
    }

    @Test
    public void TC68_handleTileAction_WithUnhandledMovePlayerAction_DoesNothing() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        TileAction action = new TileAction(TileActionType.MOVE_PLAYER, null, null, null, 0);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC69_handleTileAction_WithPayRentOnNonProperty_RefreshesOnly() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Tile tile = EasyMock.createMock(Tile.class);
        Player player = EasyMock.createMock(Player.class);
        TileAction action = new TileAction(TileActionType.PAY_RENT, player, tile, null, 0);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.handleTileAction(action);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);
    }

    @Test
    public void TC70_resolveLanding_OnRentShortfallSecondPaymentFails_EliminatesPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        BankruptcyView bankruptcyView = EasyMock.createMock(BankruptcyView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(7);
        EasyMock.expect(gameEngine.getTile(7)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(true);
        EasyMock.expect(property.isOwnedBy(player)).andReturn(false);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(false);
        EasyMock.expect(property.getRent()).andReturn(75.0);
        EasyMock.expect(propertyController.handleForcedSale(player, 75.0)).andReturn(true);
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(false);
        bankruptcyView.showPlayerEliminated(player);
        EasyMock.expectLastCall().once();
        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setBankruptcyView(bankruptcyView);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, property, player);
    }

    @Test
    public void TC71_resolveLanding_OnTaxForcedSaleThenRemoveFails_EliminatesPlayer() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyController propertyController = EasyMock.createMock(PropertyController.class);
        BankruptcyView bankruptcyView = EasyMock.createMock(BankruptcyView.class);
        IRSTile tile = EasyMock.createMock(IRSTile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(11);
        EasyMock.expect(gameEngine.getTile(11)).andReturn(tile);
        EasyMock.expect(player.remove(util.Constants.GO_BONUS)).andReturn(false).andReturn(false);
        EasyMock.expect(propertyController.handleForcedSale(player, util.Constants.GO_BONUS))
                .andReturn(true);
        bankruptcyView.showPlayerEliminated(player);
        EasyMock.expectLastCall().once();
        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyController(propertyController);
        controller.setBankruptcyView(bankruptcyView);
        controller.resolveLanding();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                propertyController, bankruptcyView, tile, player);
    }

    @Test
    public void TC72_resolveLanding_OnRentShortfallWithoutBankruptcyView_EliminatesPlayer() {
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
        EasyMock.expect(propertyController.handleRentPayment(player, property)).andReturn(false);
        EasyMock.expect(property.getRent()).andReturn(75.0);
        EasyMock.expect(propertyController.handleForcedSale(player, 75.0)).andReturn(false);
        gameEngine.removeBankruptPlayer(player);
        EasyMock.expectLastCall().once();
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
    public void TC73_applyDrawnCard_WithNoActiveCard_RefreshesOnly() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);

        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.applyDrawnCard();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice);
    }

    @Test
    public void TC74_resolveLanding_WhenBuyListenerRuns_PurchasesPropertyAndRefreshes() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        final PropertyPromptView prompt = EasyMock.createMock(PropertyPromptView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);
        final Capture<ActionListener> buyListener = new Capture<>();

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(5);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        diceView.disableRollButton();
        EasyMock.expectLastCall().once();
        prompt.setBuyListener(EasyMock.capture(buyListener));
        EasyMock.expectLastCall().once();
        prompt.setDeclineListener(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        prompt.showProperty(property, player);
        EasyMock.expectLastCall().once();
        EasyMock.expect(property.purchase(player)).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyPromptView(prompt);
        controller.resolveLanding();
        buyListener.getValue().actionPerformed(null);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);
    }

    @Test
    public void TC75_resolveLanding_WhenDeclineListenerRuns_RefreshesWithoutPurchase() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        PropertyPromptView prompt = EasyMock.createMock(PropertyPromptView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);
        Capture<ActionListener> declineListener = new Capture<>();

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(5);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        diceView.disableRollButton();
        EasyMock.expectLastCall().once();
        prompt.setBuyListener(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        prompt.setDeclineListener(EasyMock.capture(declineListener));
        EasyMock.expectLastCall().once();
        prompt.showProperty(property, player);
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyPromptView(prompt);
        controller.resolveLanding();
        declineListener.getValue().actionPerformed(null);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);
    }

    @Test
    public void TC76_playTurn_WhenBuyListenerRuns_CompletesTurnAfterPurchase() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        final PropertyPromptView prompt = EasyMock.createMock(PropertyPromptView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);
        final Capture<ActionListener> buyListener = new Capture<>();

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player).times(2);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(0).andReturn(5).andReturn(5).andReturn(5);
        dice.roll();
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getDieOne()).andReturn(3);
        EasyMock.expect(dice.getDieTwo()).andReturn(2);
        diceView.showRollResult(3, 2);
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getTotal()).andReturn(5);
        gameEngine.movePlayer(player, 5);
        EasyMock.expectLastCall().once();
        boardView.updatePlayerPosition(player, 5);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.didPassGo(0, 5)).andReturn(false);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        diceView.disableRollButton();
        EasyMock.expectLastCall().once();
        prompt.setBuyListener(EasyMock.capture(buyListener));
        EasyMock.expectLastCall().once();
        prompt.setDeclineListener(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        prompt.showProperty(property, player);
        EasyMock.expectLastCall().once();
        EasyMock.expect(property.purchase(player)).andReturn(true);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyPromptView(prompt);
        controller.playTurn();
        buyListener.getValue().actionPerformed(null);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);
    }

    @Test
    public void TC77_showRentPaid_WhenRentConfirmationViewNull_PaysRentWithoutAnnouncement() {
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
    public void TC78_showStillInJail_WhenJailStatusViewNull_StaysInJailWithoutAnnouncement() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(1);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(2);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, player);
    }

    @Test
    public void TC79_playTurn_WhenTurnCompletesAndGameOver_DoesNotAdvanceTurn() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        Tile tile = EasyMock.createMock(Tile.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player).times(2);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.expect(gameEngine.getPlayerPosition(player))
                .andReturn(0).andReturn(5).andReturn(5).andReturn(5);
        dice.roll();
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getDieOne()).andReturn(3);
        EasyMock.expect(dice.getDieTwo()).andReturn(2);
        diceView.showRollResult(3, 2);
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getTotal()).andReturn(5);
        gameEngine.movePlayer(player, 5);
        EasyMock.expectLastCall().once();
        boardView.updatePlayerPosition(player, 5);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.didPassGo(0, 5)).andReturn(false);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(tile);
        EasyMock.expect(gameEngine.isGameOver()).andReturn(true);
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice, tile, player);
    }

    @Test
    public void TC80_playJailTurn_WhenBeforeAtMaxButAfterBelowMax_StaysInJail() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        JailStatusView jailStatusView = EasyMock.createMock(JailStatusView.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(util.Constants.MAX_JAIL_TURNS);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(false);
        EasyMock.expect(player.getJailTurnCount()).andReturn(2);
        jailStatusView.showStillInJail(player, 2);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.isGameOver()).andReturn(false);
        gameEngine.nextTurn();
        EasyMock.expectLastCall().once();
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, jailStatusView, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setJailController(jailController);
        controller.setJailStatusView(jailStatusView);
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                jailController, jailStatusView, player);
    }

    @Test
    public void TC81_finishAction_WhenTurnInProgressAndCardActive_RefreshesWithoutCompleting() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        CardController cardController = EasyMock.createMock(CardController.class);
        JailController jailController = EasyMock.createMock(JailController.class);
        ChanceTile tile = EasyMock.createMock(ChanceTile.class);
        Card card = EasyMock.createMock(Card.class);
        Player player = EasyMock.createMock(Player.class);

        // A chance card is drawn and left active (turn not yet in progress)...
        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(gameEngine.getPlayerPosition(player)).andReturn(14);
        EasyMock.expect(gameEngine.getTile(14)).andReturn(tile);
        EasyMock.expect(cardController.drawChanceCard(player)).andReturn(card);
        expectRefreshViewsWithCard(gameEngine, boardView, playerInfoView, diceView, cardView, card);
        // ...then a new turn starts for a jailed player who escapes; finishAction sees the
        // still-active card and must refresh without completing the turn.
        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(true);
        EasyMock.expect(player.getJailTurnCount()).andReturn(1);
        EasyMock.expect(jailController.attemptRollDoubles(player)).andReturn(true);
        expectRefreshViewsWithCard(gameEngine, boardView, playerInfoView, diceView, cardView, card);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                cardController, jailController, tile, card, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setCardController(cardController);
        controller.setJailController(jailController);
        controller.resolveLanding();
        controller.playTurn();

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                cardController, jailController, tile, card, player);
    }

    @Test
    public void TC82_finishAction_WhenTurnInProgressAndAwaitingDecision_RefreshesWithoutCompleting() {
        GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
        BoardView boardView = EasyMock.createMock(BoardView.class);
        final PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        final CardView cardView = EasyMock.createMock(CardView.class);
        Dice dice = EasyMock.createMock(Dice.class);
        final PropertyPromptView prompt = EasyMock.createMock(PropertyPromptView.class);
        Property property = EasyMock.createMock(Property.class);
        Player player = EasyMock.createMock(Player.class);
        final TileAction noOp = new TileAction(TileActionType.NONE, player, property, null, 0);

        // A turn rolls onto an affordable property and the buy/decline prompt is shown, so the
        // turn is left awaiting the player's decision (awaitingPlayerDecision == true).
        EasyMock.expect(gameEngine.getCurrentPlayer()).andReturn(player).times(2);
        EasyMock.expect(player.isBankrupt()).andReturn(false);
        EasyMock.expect(player.inJail()).andReturn(false);
        EasyMock.expect(gameEngine.getPlayerPosition(player))
                .andReturn(0).andReturn(5).andReturn(5).andReturn(5);
        dice.roll();
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getDieOne()).andReturn(3);
        EasyMock.expect(dice.getDieTwo()).andReturn(2);
        diceView.showRollResult(3, 2);
        EasyMock.expectLastCall().once();
        EasyMock.expect(dice.getTotal()).andReturn(5);
        gameEngine.movePlayer(player, 5);
        EasyMock.expectLastCall().once();
        boardView.updatePlayerPosition(player, 5);
        EasyMock.expectLastCall().once();
        EasyMock.expect(gameEngine.didPassGo(0, 5)).andReturn(false);
        EasyMock.expect(gameEngine.getTile(5)).andReturn(property);
        EasyMock.expect(property.isOwned()).andReturn(false);
        EasyMock.expect(property.getPrice()).andReturn(100.0);
        EasyMock.expect(player.canAfford(100.0)).andReturn(true);
        diceView.disableRollButton();
        EasyMock.expectLastCall().once();
        prompt.setBuyListener(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        prompt.setDeclineListener(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        prompt.showProperty(property, player);
        EasyMock.expectLastCall().once();
        // A no-op action then reaches finishAction while the decision is still pending.
        expectRefreshViews(gameEngine, boardView, playerInfoView, diceView, cardView);

        EasyMock.replay(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);

        GameController controller = new GameController(
                gameEngine, boardView, playerInfoView, diceView, cardView, dice);
        controller.setPropertyPromptView(prompt);
        controller.playTurn();
        controller.handleTileAction(noOp);

        EasyMock.verify(gameEngine, boardView, playerInfoView, diceView, cardView, dice,
                prompt, property, player);
    }

}
