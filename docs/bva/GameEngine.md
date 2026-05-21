# BVA: `GameEngine`

---

## Method under test: `startGame()`

- **TC1: Start game with 1 player throws exception** ( :white_check_mark: )
  - **State of the system**: `players = [P1]`
  - **Expected output**: `IllegalArgumentException`

- **TC2: Start game with 2 players succeeds** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2]`
  - **Expected output**: `status = IN_PROGRESS`, `current player = P1`

- **TC3: Start game with 4 players succeeds** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2, P3, P4]`
  - **Expected output**: `status = IN_PROGRESS`, `current player = P1`

- **TC4: Start game with 5 players throws exception** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2, P3, P4, P5]`
  - **Expected output**: `IllegalArgumentException`

---

## Method under test: `getCurrentPlayer()`

- **TC5: Before any turns, current player is first player** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2]`, `currentPlayerIndex = 0`
  - **Expected output**: `P1`

- **TC6: After one nextTurn, current player is second player** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2]`, after `nextTurn()`
  - **Expected output**: `P2`

- **TC7: After wrapping, current player returns to first player** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2]`, after `nextTurn()` twice
  - **Expected output**: `P1`

---

## Method under test: `nextTurn()`

- **TC8: Advance from first to second player** ( :white_check_mark: )
  - **State of the system**: `[P1, P2]`, `current = P1`
  - **Expected output**: `current = P2`

- **TC10: Advance in larger game (middle case)** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3, P4]`, `current = P2`
  - **Expected output**: `current = P3`

- **TC11: Wrap in larger game** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3, P4]`, `current = P4`
  - **Expected output**: `current = P1`

---

## Method under test: `removeBankruptPlayer(Player player)`

- **TC12: Remove player from 2-player game ends game** ( :white_check_mark: )
  - **State of the system**: `[P1, P2]`, remove `P2`
  - **Expected output**: `[P1]`, game over

- **TC13: Remove player from 3-player game continues game** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3]`, remove `P2`
  - **Expected output**: `[P1, P3]`, game not over

- **TC14: Remove current player updates turn correctly** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3]`, `current = P1`, remove `P1`
  - **Expected output**: `current = P2`

- **TC15: Remove last player in turn order wraps correctly** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3]`, `current = P3`, remove `P3`
  - **Expected output**: `current = P1`

- **TC15a: Remove player not in game is a no-op** ( :white_check_mark: )
  - **State of the system**: `[P1, P2]`, `current = P1`, remove `Pnotingame`
  - **Expected output**: `[P1, P2]` unchanged, `status = IN_PROGRESS`, `current = P1`

- **TC15b: Remove player with index before current decrements current index** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3]`, `current = P2` (index 1), remove `P1`
  - **Expected output**: `[P2, P3]`, `current = P2` (index 0)

---

## Method under test: `isGameOver()`

- **TC16: No players means game is over** ( :white_check_mark: )
  - **State of the system**: `[]`
  - **Expected output**: `true`

- **TC17: One player means game is over** ( :white_check_mark: )
  - **State of the system**: `[P1]`
  - **Expected output**: `true`

- **TC18: Two players means game is not over** ( :white_check_mark: )
  - **State of the system**: `[P1, P2]`
  - **Expected output**: `false`

- **TC19: Four players means game is not over** ( :white_check_mark: )
  - **State of the system**: `[P1, P2, P3, P4]`
  - **Expected output**: `false`

- **TC19a: GAME_OVER status means game is over** ( :white_check_mark: )
  - **State of the system**: `[P1, P2]` started, then remove `P2` (status becomes `GAME_OVER`)
  - **Expected output**: `true`

---

## Method under test: `getWinner()`

- **TC20: No winner when multiple players remain** ( :white_check_mark: )
  - **State of the system**: `[P1, P2]`
  - **Expected output**: `null` or `Optional.empty()`

- **TC21: Single remaining player is winner** ( :white_check_mark: )
  - **State of the system**: `[P1]`
  - **Expected output**: `P1`

- **TC22: No players means no winner** ( :white_check_mark: )
  - **State of the system**: `[]`
  - **Expected output**: `null` or `Optional.empty()`

- **TC22a: One player but game not over has no winner** ( :white_check_mark: )
  - **State of the system**: `[P1]`, `status = NOT_STARTED`
  - **Expected output**: `Optional.empty()`

---

## Method under test: `getTile(int index)`

- **TC23: Get tile at index below 0 throws exception** ( )
  - **State of the system**: initialized board, `index` = -1
  - **Expected output**: `IndexOutOfBoundsException`

- **TC24: Get tile at first index returns first tile** ( )
  - **State of the system**: initialized board, `index` = 0
  - **Expected output**: returns tile at index 0

- **TC25: Get tile at last index returns last tile** ( )
  - **State of the system**: initialized board, `index` = 31
  - **Expected output**: returns tile at index 31

- **TC26: Get tile at index equal to board size throws exception** ( )
  - **State of the system**: initialized board, `index` = 32
  - **Expected output**: `IndexOutOfBoundsException`

---

## Method under test: `getPlayerPosition(Player player)`

- **TC27: Get position of player not on board throws exception** ( )
  - **State of the system**: player has no stored board position
  - **Expected output**: `IllegalArgumentException`

- **TC28: Get position of player at first index returns 0** ( )
  - **State of the system**: player is at board index 0
  - **Expected output**: returns 0

- **TC29: Get position of player at last index returns 31** ( )
  - **State of the system**: player is at board index 31
  - **Expected output**: returns 31

---

## Method under test: `setPlayerPosition(Player player, int index)`

- **TC30: Set position to index below 0 throws exception** ( )
  - **State of the system**: valid player, `index` = -1
  - **Expected output**: `IndexOutOfBoundsException`

- **TC31: Set position to first index stores position** ( )
  - **State of the system**: valid player, `index` = 0
  - **Expected output**: `getPlayerPosition(player)` returns 0

- **TC32: Set position to last index stores position** ( )
  - **State of the system**: valid player, `index` = 31
  - **Expected output**: `getPlayerPosition(player)` returns 31

- **TC33: Set position to index equal to board size throws exception** ( )
  - **State of the system**: valid player, `index` = 32
  - **Expected output**: `IndexOutOfBoundsException`

---

## Method under test: `movePlayer(Player player, int spaces)`

- **TC34: Move player not on board throws exception** ( )
  - **State of the system**: player not stored on board, `spaces` = 2
  - **Expected output**: `IllegalArgumentException`

- **TC35: Move player with one less than minimum spaces throws exception** ( )
  - **State of the system**: player at board index 0, `spaces` = 1
  - **Expected output**: `IllegalArgumentException`

- **TC36: Move player with minimum spaces succeeds** ( )
  - **State of the system**: player at board index 30, `spaces` = 2
  - **Expected output**: player moves to index 0

- **TC37: Move player with maximum spaces succeeds** ( )
  - **State of the system**: player at board index 0, `spaces` = 12
  - **Expected output**: player moves to index 12

- **TC38: Move player with one more than maximum spaces throws exception** ( )
  - **State of the system**: player at board index 0, `spaces` = 13
  - **Expected output**: `IllegalArgumentException`

---

## Method under test: `didPassGo(int oldPosition, int newPosition)`

- **TC39: Old position below 0 throws exception** ( )
  - **State of the system**: `oldPosition` = -1, `newPosition` = 0
  - **Expected output**: `IndexOutOfBoundsException`

- **TC40: New position equal to board size throws exception** ( )
  - **State of the system**: `oldPosition` = 0, `newPosition` = 32
  - **Expected output**: `IndexOutOfBoundsException`

- **TC41: Old and new positions both at GO returns false** ( )
  - **State of the system**: `oldPosition` = 0, `newPosition` = 0
  - **Expected output**: `false`

- **TC42: Moving forward from GO returns false** ( )
  - **State of the system**: `oldPosition` = 0, `newPosition` = 1
  - **Expected output**: `false`

- **TC43: Moving near end without wrapping returns false** ( )
  - **State of the system**: `oldPosition` = 30, `newPosition` = 31
  - **Expected output**: `false`

- **TC44: Moving from last index to GO returns true** ( )
  - **State of the system**: `oldPosition` = 31, `newPosition` = 0
  - **Expected output**: `true`

- **TC45: Movement wrapping past GO returns true** ( )
  - **State of the system**: `oldPosition` = 30, `newPosition` = 1
  - **Expected output**: `true`

---

## Method under test: `getBoardSize()`

- **TC46: Board size returns 32** ( )
  - **State of the system**: initialized board with 32 tiles
  - **Expected output**: returns 32
