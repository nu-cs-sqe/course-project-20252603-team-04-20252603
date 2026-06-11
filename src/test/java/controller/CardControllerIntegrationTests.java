package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Board;
import model.Card;
import model.ChanceDeckFactory;
import model.Deck;
import model.FreeParking;
import model.GameEngine;
import model.Player;
import model.Tile;

public class CardControllerIntegrationTests {

    private static final int BOARD_SIZE = 32;
    private static final int START_POSITION = 0;
    private static final double STARTING_BALANCE = 1000.0;

    @Test
    public void drawChanceCard_WithActivePlayerAndStandardDeck_ReturnsCardFromDeck() {
        Player player = new Player("John", STARTING_BALANCE);
        Player otherPlayer = new Player("Jane", STARTING_BALANCE);
        Deck deck = ChanceDeckFactory.standardDeck();
        GameEngine game = createGame(player, otherPlayer, deck);
        CardController controller = new CardController(deck, game);

        int unusedBeforeDraw = deck.getUnusedCards().size();

        Card drawnCard = controller.drawChanceCard(player);

        assertNotNull(drawnCard);
        assertEquals(unusedBeforeDraw - 1, deck.getUnusedCards().size());
        assertTrue(player.getActive());
    }

    private GameEngine createGame(Player player, Player otherPlayer, Deck deck) {
        Board board = new Board(createTiles());
        board.initializeBoard();
        board.setPlayerPosition(player, START_POSITION);
        board.setPlayerPosition(otherPlayer, START_POSITION);
        return new GameEngine(List.of(player, otherPlayer), board, deck);
    }

    private List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            tiles.add(new FreeParking());
        }
        return tiles;
    }
}
