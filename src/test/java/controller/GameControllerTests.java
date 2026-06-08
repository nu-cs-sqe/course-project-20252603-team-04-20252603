package controller;

import model.GameEngine;
import model.GameStatus;
import model.Player;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import view.BoardView;
import view.CardView;
import view.DiceView;
import view.PlayerInfoView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTests {

    @Test
    public void TC_STATUS1_getStatus_WhenGameEngineIsNull_ThrowsNullPointerException() {
        BoardView boardView = EasyMock.createMock(BoardView.class);
        PlayerInfoView playerInfoView = EasyMock.createMock(PlayerInfoView.class);
        DiceView diceView = EasyMock.createMock(DiceView.class);
        CardView cardView = EasyMock.createMock(CardView.class);

        assertThrows(NullPointerException.class, () -> new GameController(null, boardView, playerInfoView, diceView, cardView));
    }




}
