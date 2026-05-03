package model;

import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameEngineTest {
    @Test
    public void Start_Game_With_Minimum_Player_Count(){
        Player player1 = EasyMock.createMock(Player.class); 
        Player player2 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2);

        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();

        assertEquals(GameStatus.IN_PROGRESS, gameEngine.getStatus());
        assertSame(player1, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2);


    }

    @Test 
    public void Start_Game_With_One_Player_Throws_Exception(){
        Player player1 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1);

        GameEngine gameEngine = new GameEngine(List.of(player1));
        assertThrows(IllegalArgumentException.class, () -> gameEngine.startGame());

        EasyMock.verify(player1);
    }

    @Test
    public void Start_Game_With_Four_Players_Succeeds(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3, player4);

        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3, player4));
        gameEngine.startGame();

        assertEquals(GameStatus.IN_PROGRESS, gameEngine.getStatus());
        assertSame(player1, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2, player3, player4);
        
    }

    @Test
    public void Start_Game_With_Five_Players_Throws_Exception(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);
        Player player5 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3, player4, player5);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3, player4, player5));
        assertThrows(IllegalArgumentException.class, () -> gameEngine.startGame());

        EasyMock.verify(player1, player2, player3, player4, player5);
    }

    // getCurrentPlayer tests

    @Test
    public void Before_Any_Turns_Current_Player_Is_First_Player(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();

        assertEquals(player1, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2);
    }

    @Test
    public void After_One_Next_Turn_Current_Player_Is_Second_Player(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        gameEngine.nextTurn();
        assertEquals(player2, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2);
    }

    @Test
    public void After_Wrapping_Current_Player_Returns_To_First_Player(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        gameEngine.nextTurn();
        gameEngine.nextTurn();
        assertEquals(player1, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2);
    }

    // nextTurn tests

    @Test
    public void Advance_From_First_To_Second_Player(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        gameEngine.nextTurn();
        assertEquals(player2, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2);
    }
}
