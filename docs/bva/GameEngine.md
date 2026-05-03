# BVA: `GameEngine`

---

## Method under test: `startGame()`

- **TC1: Start game with 1 player throws exception** ( :x: )
  - **State of the system**: `players = [P1]`
  - **Expected output**: `IllegalArgumentException`

- **TC2: Start game with 2 players succeeds** ( :x: )
  - **State of the system**: `players = [P1, P2]`
  - **Expected output**: `status = IN_PROGRESS`, `current player = P1`

- **TC3: Start game with 4 players succeeds** ( :x: )
  - **State of the system**: `players = [P1, P2, P3, P4]`
  - **Expected output**: `status = IN_PROGRESS`, `current player = P1`

- **TC4: Start game with 5 players throws exception** ( :x: )
  - **State of the system**: `players = [P1, P2, P3, P4, P5]`
  - **Expected output**: `IllegalArgumentException`

---

## Method under test: `getCurrentPlayer()`

- **TC5: Before any turns, current player is first player** ( :x: )
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

- **TC8: Advance from first to second player** ( :x: )
  - **State of the system**: `[P1, P2]`, `current = P1`
  - **Expected output**: `current = P2`

- **TC9: Wrap from last player back to first** ( :x: )
  - **State of the system**: `[P1, P2]`, `current = P2`
  - **Expected output**: `current = P1`

- **TC10: Advance in larger game (middle case)** ( :x: )
  - **State of the system**: `[P1, P2, P3, P4]`, `current = P2`
  - **Expected output**: `current = P3`

- **TC11: Wrap in larger game** ( :x: )
  - **State of the system**: `[P1, P2, P3, P4]`, `current = P4`
  - **Expected output**: `current = P1`

---

## Method under test: `removeBankruptPlayer(Player player)`

- **TC12: Remove player from 2-player game ends game** ( :x: )
  - **State of the system**: `[P1, P2]`, remove `P2`
  - **Expected output**: `[P1]`, game over

- **TC13: Remove player from 3-player game continues game** ( :x: )
  - **State of the system**: `[P1, P2, P3]`, remove `P2`
  - **Expected output**: `[P1, P3]`, game not over

- **TC14: Remove current player updates turn correctly** ( :x: )
  - **State of the system**: `[P1, P2, P3]`, `current = P1`, remove `P1`
  - **Expected output**: `current = P2`

- **TC15: Remove last player in turn order wraps correctly** ( :x: )
  - **State of the system**: `[P1, P2, P3]`, `current = P3`, remove `P3`
  - **Expected output**: `current = P1`

---

## Method under test: `isGameOver()`

- **TC16: No players means game is over** ( :x: )
  - **State of the system**: `[]`
  - **Expected output**: `true`

- **TC17: One player means game is over** ( :x: )
  - **State of the system**: `[P1]`
  - **Expected output**: `true`

- **TC18: Two players means game is not over** ( :x: )
  - **State of the system**: `[P1, P2]`
  - **Expected output**: `false`

- **TC19: Four players means game is not over** ( :x: )
  - **State of the system**: `[P1, P2, P3, P4]`
  - **Expected output**: `false`

---

## Method under test: `getWinner()`

- **TC20: No winner when multiple players remain** ( :x: )
  - **State of the system**: `[P1, P2]`
  - **Expected output**: `null` or `Optional.empty()`

- **TC21: Single remaining player is winner** ( :x: )
  - **State of the system**: `[P1]`
  - **Expected output**: `P1`

- **TC22: No players means no winner** ( :x: )
  - **State of the system**: `[]`
  - **Expected output**: `null` or `Optional.empty()`
