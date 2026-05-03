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

    @Test
    public void Advance_In_Larger_Game_Middle_Case(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3, player4);

        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3, player4));
        gameEngine.startGame();
        gameEngine.nextTurn();
        gameEngine.nextTurn();
        assertEquals(player3, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2, player3, player4);
        
    }

    @Test
    public void Wrap_In_Larger_Game(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3, player4);

        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3, player4));
        gameEngine.startGame();
        gameEngine.nextTurn();
        gameEngine.nextTurn();
        gameEngine.nextTurn();
        gameEngine.nextTurn();
        assertEquals(player1, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2, player3, player4);
    }

    // remove bankrupt player tests

    @Test
    public void Remove_Player_From_Two_Player_Game_Ends_Game(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        assertEquals(GameStatus.IN_PROGRESS, gameEngine.getStatus());
        gameEngine.removeBankruptPlayer(player2);
        assertEquals(player1, gameEngine.getCurrentPlayer());
        assertEquals(GameStatus.GAME_OVER, gameEngine.getStatus());

        EasyMock.verify(player1, player2);
    }

    @Test
    public void Remove_Player_From_Three_Player_Game_Continues_Game(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3);

        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3));
        gameEngine.startGame();
        gameEngine.removeBankruptPlayer(player2);
        assertEquals(GameStatus.IN_PROGRESS, gameEngine.getStatus());

        EasyMock.verify(player1, player2, player3);
    }

    @Test
    public void Remove_Current_Player_Updates_Turn_Correctly(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3));
        gameEngine.startGame();
        gameEngine.removeBankruptPlayer(player1);
        assertEquals(player2, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2, player3);
    }
}
