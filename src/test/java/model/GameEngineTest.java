package model;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.easymock.EasyMock;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void Remove_Last_Player_In_Turn_Order_Wraps_Correctly(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);


        EasyMock.replay(player1, player2, player3);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3));
        gameEngine.startGame();
        gameEngine.nextTurn();
        gameEngine.nextTurn();
        gameEngine.removeBankruptPlayer(player3);
        assertEquals(player1, gameEngine.getCurrentPlayer());

        EasyMock.verify(player1, player2, player3);
    }

    // isGameOver tests

    @Test
    public void No_Players_Means_Game_Is_Over(){
        GameEngine gameEngine = new GameEngine(List.of());
                
        assertEquals(true, gameEngine.isGameOver());
    }

    @Test
    public void One_Player_Means_Game_Is_Over(){
        Player player1 = EasyMock.createMock(Player.class);
        EasyMock.replay(player1);
        
        GameEngine gameEngine = new GameEngine(List.of(player1));
        assertEquals(true, gameEngine.isGameOver());

        EasyMock.verify(player1);
    }

    @Test
    public void Two_Players_Means_Game_Is_Not_Over(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.replay(player1, player2);
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        assertEquals(false, gameEngine.isGameOver());

        EasyMock.verify(player1, player2);
    }

    @Test
    public void Four_Players_Means_Game_Is_Not_Over(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        Player player3 = EasyMock.createMock(Player.class);
        Player player4 = EasyMock.createMock(Player.class);

        EasyMock.replay(player1, player2, player3, player4);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2, player3, player4));
        gameEngine.startGame();
        assertEquals(false, gameEngine.isGameOver());

        EasyMock.verify(player1, player2, player3, player4);
    }

    // getWinner tests
    @Test
    public void No_Winner_When_Multiple_Players_Remain(){
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.replay(player1, player2);
        
        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        assertEquals(Optional.empty(), gameEngine.getWinner());
    }

    @Test
    public void Single_Remaining_Player_Is_Winner() {
        Player player1 = EasyMock.createMock(Player.class);
        Player player2 = EasyMock.createMock(Player.class);
        EasyMock.replay(player1, player2);

        GameEngine gameEngine = new GameEngine(List.of(player1, player2));
        gameEngine.startGame();
        gameEngine.removeBankruptPlayer(player2);

        assertEquals(Optional.of(player1), gameEngine.getWinner());

        EasyMock.verify(player1, player2);
    }

    @Test
    public void No_Players_Means_No_Winner(){
        GameEngine gameEngine = new GameEngine(List.of());
        assertEquals(Optional.empty(), gameEngine.getWinner());
    }

    // getTile tests (TC23-TC26)

    @Test
    public void getTile_WithNegativeIndex_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.getTile(-1)).andThrow(new IndexOutOfBoundsException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IndexOutOfBoundsException.class, () -> engine.getTile(-1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void getTile_WithFirstIndex_ReturnsFirstTile() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);
        Tile tile = EasyMock.createMock(Tile.class);

        EasyMock.expect(board.getTile(0)).andReturn(tile);
        EasyMock.replay(p1, p2, board, tile);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertSame(tile, engine.getTile(0));

        EasyMock.verify(p1, p2, board, tile);
    }

    @Test
    public void getTile_WithLastIndex_ReturnsLastTile() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);
        Tile tile = EasyMock.createMock(Tile.class);

        EasyMock.expect(board.getTile(31)).andReturn(tile);
        EasyMock.replay(p1, p2, board, tile);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertSame(tile, engine.getTile(31));

        EasyMock.verify(p1, p2, board, tile);
    }

    @Test
    public void getTile_WithIndexEqualToBoardSize_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.getTile(32)).andThrow(new IndexOutOfBoundsException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IndexOutOfBoundsException.class, () -> engine.getTile(32));

        EasyMock.verify(p1, p2, board);
    }

    // getPlayerPosition tests (TC27-TC29)

    @Test
    public void getPlayerPosition_WhenPlayerNotOnBoard_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);
        Player notOnBoard = EasyMock.createMock(Player.class);

        EasyMock.expect(board.getPlayerPosition(notOnBoard)).andThrow(new IllegalArgumentException());
        EasyMock.replay(p1, p2, board, notOnBoard);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IllegalArgumentException.class, () -> engine.getPlayerPosition(notOnBoard));

        EasyMock.verify(p1, p2, board, notOnBoard);
    }

    @Test
    public void getPlayerPosition_WhenPlayerAtFirstIndex_ReturnsZero() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.getPlayerPosition(p1)).andReturn(0);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertEquals(0, engine.getPlayerPosition(p1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void getPlayerPosition_WhenPlayerAtLastIndex_ReturnsLastIndex() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.getPlayerPosition(p1)).andReturn(31);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertEquals(31, engine.getPlayerPosition(p1));

        EasyMock.verify(p1, p2, board);
    }

    // setPlayerPosition tests (TC30-TC33)

    @Test
    public void setPlayerPosition_WithOneLessThanFirstIndex_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.setPlayerPosition(p1, -1);
        EasyMock.expectLastCall().andThrow(new IndexOutOfBoundsException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IndexOutOfBoundsException.class, () -> engine.setPlayerPosition(p1, -1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void setPlayerPosition_WithFirstIndex_StoresPosition() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.setPlayerPosition(p1, 0);
        EasyMock.expectLastCall();
        EasyMock.expect(board.getPlayerPosition(p1)).andReturn(0);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        engine.setPlayerPosition(p1, 0);
        assertEquals(0, engine.getPlayerPosition(p1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void setPlayerPosition_WithLastIndex_StoresPosition() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.setPlayerPosition(p1, 31);
        EasyMock.expectLastCall();
        EasyMock.expect(board.getPlayerPosition(p1)).andReturn(31);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        engine.setPlayerPosition(p1, 31);
        assertEquals(31, engine.getPlayerPosition(p1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void setPlayerPosition_WithOneMoreThanLastIndex_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.setPlayerPosition(p1, 32);
        EasyMock.expectLastCall().andThrow(new IndexOutOfBoundsException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IndexOutOfBoundsException.class, () -> engine.setPlayerPosition(p1, 32));

        EasyMock.verify(p1, p2, board);
    }

    // movePlayer tests (TC34-TC38)

    @Test
    public void movePlayer_WhenPlayerNotOnBoard_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);
        Player notOnBoard = EasyMock.createMock(Player.class);

        board.movePlayer(notOnBoard, 2);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
        EasyMock.replay(p1, p2, board, notOnBoard);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IllegalArgumentException.class, () -> engine.movePlayer(notOnBoard, 2));

        EasyMock.verify(p1, p2, board, notOnBoard);
    }

    @Test
    public void movePlayer_WithOneLessThanMinimumSpaces_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.movePlayer(p1, 1);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IllegalArgumentException.class, () -> engine.movePlayer(p1, 1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void movePlayer_WithMinimumSpaces_MovesPlayer() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.movePlayer(p1, 2);
        EasyMock.expectLastCall();
        EasyMock.expect(board.getPlayerPosition(p1)).andReturn(0);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        engine.movePlayer(p1, 2);
        assertEquals(0, engine.getPlayerPosition(p1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void movePlayer_WithMaximumSpaces_MovesPlayer() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.movePlayer(p1, 12);
        EasyMock.expectLastCall();
        EasyMock.expect(board.getPlayerPosition(p1)).andReturn(12);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        engine.movePlayer(p1, 12);
        assertEquals(12, engine.getPlayerPosition(p1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void movePlayer_WithOneMoreThanMaximumSpaces_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        board.movePlayer(p1, 13);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IllegalArgumentException.class, () -> engine.movePlayer(p1, 13));

        EasyMock.verify(p1, p2, board);
    }

    // didPassGo tests (TC39-TC45)

    @Test
    public void didPassGo_WithOneLessThanFirstOldPosition_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(-1, 0)).andThrow(new IndexOutOfBoundsException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IndexOutOfBoundsException.class, () -> engine.didPassGo(-1, 0));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void didPassGo_WithOneMoreThanLastNewPosition_ThrowsException() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(0, 32)).andThrow(new IndexOutOfBoundsException());
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertThrows(IndexOutOfBoundsException.class, () -> engine.didPassGo(0, 32));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void didPassGo_WhenOldAndNewPositionsAreGo_ReturnsFalse() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(0, 0)).andReturn(false);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertFalse(engine.didPassGo(0, 0));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void didPassGo_WhenMovingForwardFromGo_ReturnsFalse() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(0, 1)).andReturn(false);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertFalse(engine.didPassGo(0, 1));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void didPassGo_WhenMovingNearEndWithoutWrap_ReturnsFalse() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(30, 31)).andReturn(false);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertFalse(engine.didPassGo(30, 31));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void didPassGo_WhenMovingFromLastIndexToGo_ReturnsTrue() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(31, 0)).andReturn(true);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertTrue(engine.didPassGo(31, 0));

        EasyMock.verify(p1, p2, board);
    }

    @Test
    public void didPassGo_WhenMovementWrapsPastGo_ReturnsTrue() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.didPassGo(30, 1)).andReturn(true);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertTrue(engine.didPassGo(30, 1));

        EasyMock.verify(p1, p2, board);
    }

    // getBoardSize test (TC46)

    @Test
    public void getBoardSize_WhenBoardInitialized_Returns32() {
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Board board = EasyMock.createMock(Board.class);

        EasyMock.expect(board.getBoardSize()).andReturn(32);
        EasyMock.replay(p1, p2, board);

        GameEngine engine = new GameEngine(List.of(p1, p2), board);
        assertEquals(32, engine.getBoardSize());

        EasyMock.verify(p1, p2, board);
    }
}
