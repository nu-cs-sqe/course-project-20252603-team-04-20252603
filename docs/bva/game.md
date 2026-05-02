# BVA: `Game`

Represents full game state: `players`, `board`, `dice`, `chanceDeck`, `currentPlayerIndex`, `status`. Align player count and money rules with `docs/requirements/game-rules.md` (2–4 players, $1000 start, etc.) wherever `startGame` or related setup applies.

---

## Method under test: `startGame()`

- **TC1: Start with minimum player count (2)** ( :x: )
  - **State of the system**: valid configuration with exactly 2 players; game not yet started (or equivalent initial `status`)
  - **Expected output**: game enters playable state; `players` size 2; board/dice/chance deck initialized per design; `currentPlayerIndex` set consistently (e.g. `0`); each player has starting cash if `Game` assigns it

- **TC2: Start with maximum player count (4)** ( :x: )
  - **State of the system**: valid configuration with exactly 4 players
  - **Expected output**: same as TC1 pattern with 4 players

- **TC3: Start rejected or errored with one player** ( :x: )
  - **State of the system**: only 1 player registered before `startGame()` (below rules minimum)
  - **Expected output**: game does not enter normal play; documented exception, error flag, or `status` remains invalid (team must pick one)

- **TC4: Start rejected or errored with five players** ( :x: )
  - **State of the system**: 5 players before `startGame()` (above rules maximum)
  - **Expected output**: same style of failure as TC3; no valid in-progress state

- **TC5: Second call to `startGame` when already started** ( :x: )
  - **State of the system**: `startGame()` already succeeded once; `status` is in-progress (or equivalent)
  - **Expected output**: idempotent no-op, documented exception, or explicit “already started” outcome (team must pick one and test it)

---

## Method under test: `getCurrentPlayer()`

- **TC6: Current player is first in list** ( :x: )
  - **State of the system**: game started; `currentPlayerIndex == 0`; `players` non-empty
  - **Expected output**: returns `players.get(0)`

- **TC7: Current player is last in list** ( :x: )
  - **State of the system**: game started; `currentPlayerIndex == players.size() - 1` (e.g. 4 players, index `3`)
  - **Expected output**: returns `players.get(3)`

- **TC8: Current player after `nextTurn` from last back to first** ( :x: )
  - **State of the system**: `players.size() == 3`; `currentPlayerIndex == 2`; `nextTurn()` just advanced to wrap to `0`
  - **Expected output**: `getCurrentPlayer()` returns `players.get(0)`

- **TC9: No players — precondition** ( :x: )
  - **State of the system**: `players` is empty (or `null` if allowed — avoid if possible)
  - **Expected output**: documented exception or clear failure; no valid player returned

---

## Method under test: `nextTurn()`

- **TC10: Advance from first to second player (two players)** ( :x: )
  - **State of the system**: 2 players; `currentPlayerIndex == 0`
  - **Expected output**: `currentPlayerIndex == 1`

- **TC11: Wrap from last player to first (two players)** ( :x: )
  - **State of the system**: 2 players; `currentPlayerIndex == 1`
  - **Expected output**: `currentPlayerIndex == 0`

- **TC12: Advance in the middle of turn order (four players)** ( :x: )
  - **State of the system**: 4 players; `currentPlayerIndex == 1`
  - **Expected output**: `currentPlayerIndex == 2`

- **TC13: Wrap from last to first (four players)** ( :x: )
  - **State of the system**: 4 players; `currentPlayerIndex == 3`
  - **Expected output**: `currentPlayerIndex == 0`

---

## Method under test: `removeBankruptPlayer(Player player)`

- **TC14: Remove a player listed before the current player** ( :x: )
  - **State of the system**: e.g. 4 players, indices A B C D; `currentPlayerIndex == 2` (C); remove B (`index 1`)
  - **Expected output**: B removed from `players`; `currentPlayerIndex` adjusted so the same logical “current” player (C) is still current (typically index becomes `1` after removal)

- **TC15: Remove a player listed after the current player** ( :x: )
  - **State of the system**: 4 players; `currentPlayerIndex == 1`; remove player at index `3`
  - **Expected output**: removed player gone from list; `currentPlayerIndex` still `1` (same `Player` still at turn)

- **TC16: Remove the current player** ( :x: )
  - **State of the system**: `currentPlayerIndex == 1`; `removeBankruptPlayer(players.get(1))`
  - **Expected output**: that player removed; `currentPlayerIndex` stays in range `[0, size-1]` and indicates the documented “next” player (e.g. same index now points to old index 2, or turn passes per team rule)

- **TC17: Remove player not in the game** ( :x: )
  - **State of the system**: `player` is non-null but not in `players` (or already removed)
  - **Expected output**: no change to list and indices, or documented exception (team must pick one)

- **TC18: Remove when two players remain — boundary before win** ( :x: )
  - **State of the system**: exactly 2 players; remove one bankrupt player
  - **Expected output**: one player left; `isGameOver()` becomes `true` if rules say one survivor wins; `currentPlayerIndex` valid for sole remaining player

---

## Method under test: `isGameOver()`

- **TC19: Game in progress with multiple active players** ( :x: )
  - **State of the system**: `status` indicates playing; at least 2 active players
  - **Expected output**: `false`

- **TC20: Only one player remains** ( :x: )
  - **State of the system**: `players.size() == 1` after eliminations (or equivalent “one not bankrupt”)
  - **Expected output**: `true`

- **TC21: Status explicitly finished** ( :x: )
  - **State of the system**: `status == GameStatus` value meaning finished (if enum used), even if `players` still has multiple references (only if your model allows — otherwise tie to real win condition)
  - **Expected output**: `true`

- **TC22: Game never started** ( :x: )
  - **State of the system**: `startGame()` not called; default construction state
  - **Expected output**: `true` or `false` per team rule (document; e.g. “not started” is not “over” → `false`)

---

## Method under test: `getWinner()`

- **TC23: Single remaining player after game over** ( :x: )
  - **State of the system**: `isGameOver()` is `true`; exactly one player left in `players` (or one non-bankrupt winner)
  - **Expected output**: returns that `Player`

- **TC24: Game not over — precondition** ( :x: )
  - **State of the system**: `isGameOver()` is `false`; multiple players still in contention
  - **Expected output**: documented exception, `null`, or `Optional.empty()` (team must pick one)
