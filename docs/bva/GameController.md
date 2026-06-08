# BVA: `GameController`

Main controller for game flow. Coordinates turns, player movement, tile effects, bankruptcy handling, and GUI updates through:

```java
Game game;
BoardView boardView;
PlayerInfoView playerInfoView;
DiceView diceView;
CardView cardView;
```

## Assumptions

- A valid game starts with 2 to 4 players.
- The board has 32 tiles, indexed from `0` to `31`.
- A normal dice roll total is from `2` to `12`.
- `refreshViews()` should run after successful game-state changes so the GUI reflects the current model state.
- These are specification-level BVA cases for the controller contract. Implementation status is marked as not implemented until matching tests/code exist.

---

## Method under test: `GameController(GameEngine gameEngine, BoardView boardView, PlayerInfoView playerInfoView, DiceView diceView, CardView cardView)`

1. Input: controller dependencies, Output: initialized controller or rejected construction
2. Input type: object references
3. Input boundary values: `gameEngine = null`, valid `GameEngine`

- **TC1: constructor_WithNullGameEngine_ThrowsNullPointerException** ( :white_check_mark: )
  - **State of the system**: `gameEngine = null`; all view dependencies are valid mocks or objects
  - **Expected output**: throws `NullPointerException`

---

## Method under test: `getStatus()`

1. Input: current `GameEngine` state, Output: current game status
2. Input type: none
3. Output boundary values: `NOT_STARTED`, `IN_PROGRESS`, `GAME_OVER`

- **TC2: getStatus_WhenGameEngineStatusIsNotStarted_ReturnsNotStarted** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getStatus()` returns `GameStatus.NOT_STARTED`
  - **Expected output**: controller returns `GameStatus.NOT_STARTED`

- **TC3: getStatus_WhenGameEngineStatusIsInProgress_ReturnsInProgress** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getStatus()` returns `GameStatus.IN_PROGRESS`
  - **Expected output**: controller returns `GameStatus.IN_PROGRESS`

- **TC4: getStatus_WhenGameEngineStatusIsGameOver_ReturnsGameOver** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getStatus()` returns `GameStatus.GAME_OVER`
  - **Expected output**: controller returns `GameStatus.GAME_OVER`

---

## Method under test: `getCurrentPlayer()`

1. Input: current `GameEngine` turn state, Output: player whose turn it is
2. Input type: none
3. Output boundary values: current player reference returned by `GameEngine`

- **TC5: getCurrentPlayer_WhenGameEngineReturnsCurrentPlayer_ReturnsSamePlayer** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getCurrentPlayer()` returns `P1`
  - **Expected output**: controller returns the same `P1` reference

---

## Method under test: `getActivePlayers()`

1. Input: current `GameEngine` roster, Output: active players in turn order
2. Input type: none
3. Output boundary values: empty list, minimum players, maximum players, one remaining player

- **TC6: getActivePlayers_WhenGameEngineActivePlayersIsEmpty_ReturnsEmptyList** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getActivePlayers()` returns `[]`
  - **Expected output**: controller returns `[]`

- **TC7: getActivePlayers_WhenGameEngineHasMinimumPlayers_ReturnsBothPlayers** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getActivePlayers()` returns `[P1, P2]`
  - **Expected output**: controller returns `[P1, P2]` in turn order

- **TC8: getActivePlayers_WhenGameEngineHasMaximumPlayers_ReturnsAllPlayers** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getActivePlayers()` returns `[P1, P2, P3, P4]`
  - **Expected output**: controller returns `[P1, P2, P3, P4]` in turn order

- **TC9: getActivePlayers_WhenGameEngineHasOneRemainingPlayer_ReturnsWinnerOnly** ( :white_check_mark: )
  - **State of the system**: mocked or stubbed `GameEngine.getActivePlayers()` returns `[P1]`
  - **Expected output**: controller returns `[P1]`

- **TC10: getActivePlayers_ReturnedListCannotMutateControllerState** ( :x: )
  - **State of the system**: mocked or stubbed `GameEngine.getActivePlayers()` returns an unmodifiable list `[P1, P2]`
  - **Expected output**: controller returns an unmodifiable list, or mutating the returned list does not change the controller's active player list

---

## Method under test: `startGame(List<Player> players)`

1. Input: player list, Output: initialized game and refreshed views
2. Input type: list size / object references
3. Input boundary values: `null`, `0`, `1`, `2`, `4`, `5`

- **TC11: startGame_WithNullPlayerList_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `players = null`
  - **Expected output**: throws `NullPointerException`; no game is started and no views are refreshed

- **TC12: startGame_WithZeroPlayers_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `players = []`
  - **Expected output**: throws `IllegalArgumentException`; `game` remains not started

- **TC13: startGame_WithOnePlayer_ThrowsException** ( :x: )
  - **State of the system**: `players = [P1]`
  - **Expected output**: throws `IllegalArgumentException`; `game` remains not started

- **TC14: startGame_WithMinimumPlayers_StartsGame** ( :x: )
  - **State of the system**: `players = [P1, P2]`
  - **Expected output**: `game` is created or reset, `getStatus()` returns `GameStatus.IN_PROGRESS`, `getActivePlayers()` returns `[P1, P2]`, `getCurrentPlayer()` returns `P1`, all players start at board index `0`, and views are refreshed once

- **TC15: startGame_WithMaximumPlayers_StartsGame** ( :x: )
  - **State of the system**: `players = [P1, P2, P3, P4]`
  - **Expected output**: `game` is created or reset, active players are `[P1, P2, P3, P4]`, current player is `P1`, all players start at board index `0`, and views are refreshed once

- **TC16: startGame_WithMoreThanMaximumPlayers_ThrowsException** ( :x: )
  - **State of the system**: `players = [P1, P2, P3, P4, P5]`
  - **Expected output**: throws `IllegalArgumentException`; no new game is started

- **TC17: startGame_WithNullPlayer_ThrowsException** ( :x: )
  - **State of the system**: `players = [P1, null]`
  - **Expected output**: throws `NullPointerException`; no partial game is started

- **TC18: startGame_WhenGameAlreadyInProgress_ThrowsException** ( :x: )
  - **State of the system**: `gameEngine.getStatus()` returns `GameStatus.IN_PROGRESS`;
  - **Expected output**: throws `IllegalStateException`; `gameEngine.startGame()` is not called; existing game state remains unchanged

---

## Method under test: `handleRollDice()`

1. Input: current game state, Output: dice roll, player movement, tile action trigger, and refreshed views
2. Input type: game status, current player position, dice total interval
3. Input boundary values: game not started, dice total `2`, dice total `12`, board positions `0`, `30`, `31`

- **TC19: handleRollDice_BeforeGameStart_DoesNotMovePlayer** ( :x: )
  - **State of the system**: `game = null` or game status is not started
  - **Expected output**: operation is rejected or ignored; no dice result is shown and no player position changes

- **TC20: handleRollDice_WithMinimumRoll_MovesTwoSpaces** ( :x: )
  - **State of the system**: current player `P1` is at index `0`; dice total is controlled to `2`
  - **Expected output**: `P1` moves to index `2`, the tile at index `2` is processed, and views are refreshed

- **TC21: handleRollDice_WithMaximumRoll_MovesTwelveSpaces** ( :x: )
  - **State of the system**: current player `P1` is at index `0`; dice total is controlled to `12`
  - **Expected output**: `P1` moves to index `12`, the tile at index `12` is processed, and views are refreshed

- **TC22: handleRollDice_WhenMinimumRollWrapsToGo_MovesToZero** ( :x: )
  - **State of the system**: current player `P1` is at index `30`; dice total is controlled to `2`
  - **Expected output**: `P1` moves to index `0`, GO tile behavior is applied, and views are refreshed

- **TC23: handleRollDice_WhenMaximumRollWrapsAroundBoard_MovesToExpectedIndex** ( :x: )
  - **State of the system**: current player `P1` is at index `31`; dice total is controlled to `12`
  - **Expected output**: `P1` moves to index `11`, pass-GO behavior is applied if required by the model, and views are refreshed

- **TC24: handleRollDice_WhenCurrentPlayerIsBankrupt_DoesNotMovePlayer** ( :x: )
  - **State of the system**: current player has been removed from active players
  - **Expected output**: controller skips or rejects the roll for that player; no removed player position changes

- **TC25: handleRollDice_WhenTileEffectCausesBankruptcy_HandlesBankruptcy** ( :x: )
  - **State of the system**: current player lands on a tile that requires a payment greater than the player's available balance and assets
  - **Expected output**: `handleBankruptcy(currentPlayer)` is triggered, the player is removed from active turn order, and views are refreshed

---

## Method under test: `handleTileAction(TileAction action)`

1. Input: selected tile action, Output: tile effect is applied or rejected
2. Input type: `TileAction` object reference and action-specific values
3. Input boundary values: `null`, valid action, action requiring exact balance, action requiring more than balance

- **TC26: handleTileAction_WithNullAction_ThrowsException** ( :x: )
  - **State of the system**: game is in progress, `action = null`
  - **Expected output**: throws `NullPointerException`; no game state changes

- **TC27: handleTileAction_WithValidNoOpAction_RefreshesViewsOnly** ( :x: )
  - **State of the system**: current player is on a tile with no required effect, `action = NO_OP` or equivalent
  - **Expected output**: player balance, ownership, and position remain unchanged; views are refreshed

- **TC28: handleTileAction_WithPurchaseAtExactBalance_AllowsPurchase** ( :x: )
  - **State of the system**: current player lands on an unowned property; player balance equals property price
  - **Expected output**: property becomes owned by current player, player balance becomes `0`, and views are refreshed

- **TC29: handleTileAction_WithOptionalPurchaseAboveBalance_DoesNotPurchase** ( :x: )
  - **State of the system**: current player lands on an unowned property; property price is greater than player balance
  - **Expected output**: property remains unowned, player balance is unchanged, and views are refreshed or purchase is rejected

- **TC30: handleTileAction_WithMandatoryPaymentAtExactBalance_PaysSuccessfully** ( :x: )
  - **State of the system**: current player owes rent, tax, or card payment exactly equal to current balance
  - **Expected output**: payment is applied, player balance becomes `0`, player remains active unless the model treats zero balance as bankrupt, and views are refreshed

- **TC31: handleTileAction_WithMandatoryPaymentAboveBalance_TriggersBankruptcy** ( :x: )
  - **State of the system**: current player owes rent, tax, or card payment greater than available balance and assets
  - **Expected output**: `handleBankruptcy(currentPlayer)` is triggered, player is removed from active players, and views are refreshed

- **TC32: handleTileAction_WithActionForWrongTile_RejectsAction** ( :x: )
  - **State of the system**: current player is not on a property tile, but `action = BUY_PROPERTY` or equivalent
  - **Expected output**: action is rejected; player balance, ownership, and position remain unchanged

---

## Method under test: `handleEndTurn()`

1. Input: current game and turn state, Output: current player advances or game ends
2. Input type: active player count and current player index
3. Input boundary values: game not started, active player count `1`, `2`, `4`, last player index

- **TC33: handleEndTurn_BeforeGameStart_DoesNotAdvanceTurn** ( :x: )
  - **State of the system**: `game = null` or game status is not started
  - **Expected output**: operation is rejected or ignored; no current player is selected

- **TC34: handleEndTurn_WithTwoPlayers_AdvancesToSecondPlayer** ( :x: )
  - **State of the system**: active players are `[P1, P2]`, current player is `P1`
  - **Expected output**: current player becomes `P2`, turn controls update, and views are refreshed

- **TC35: handleEndTurn_WithMaximumPlayersMiddleTurn_AdvancesToNextPlayer** ( :x: )
  - **State of the system**: active players are `[P1, P2, P3, P4]`, current player is `P2`
  - **Expected output**: current player becomes `P3`, turn controls update, and views are refreshed

- **TC36: handleEndTurn_WithMaximumPlayersAtLastTurn_WrapsToFirstPlayer** ( :x: )
  - **State of the system**: active players are `[P1, P2, P3, P4]`, current player is `P4`
  - **Expected output**: current player becomes `P1`, turn controls update, and views are refreshed

- **TC37: handleEndTurn_WithOneActivePlayer_EndsGame** ( :x: )
  - **State of the system**: active players are `[P1]`
  - **Expected output**: game status becomes game over, `P1` is declared winner, and views show the final state

- **TC38: handleEndTurn_WhenNextPlayerIsBankrupt_SkipsRemovedPlayer** ( :x: )
  - **State of the system**: original turn order was `[P1, P2, P3]`, `P2` has been removed, current player is `P1`
  - **Expected output**: current player becomes `P3`; removed player does not receive a turn

---

## Method under test: `handleBankruptcy(Player player)`

1. Input: bankrupt player, Output: player removed or game ends
2. Input type: `Player` object reference and active player count
3. Input boundary values: `null`, player not in game, active player count `2`, `3`, current player at first or last index

- **TC39: handleBankruptcy_WithNullPlayer_ThrowsException** ( :x: )
  - **State of the system**: game is in progress, `player = null`
  - **Expected output**: throws `NullPointerException`; active players remain unchanged

- **TC40: handleBankruptcy_WithPlayerNotInGame_DoesNotChangeGame** ( :x: )
  - **State of the system**: active players are `[P1, P2]`, `player = P3`
  - **Expected output**: operation is rejected or ignored; active players remain `[P1, P2]`

- **TC41: handleBankruptcy_WithThreePlayers_RemovesPlayerAndContinuesGame** ( :x: )
  - **State of the system**: active players are `[P1, P2, P3]`, bankrupt player is `P2`
  - **Expected output**: active players become `[P1, P3]`, game remains in progress, and views are refreshed

- **TC42: handleBankruptcy_WithTwoPlayers_RemovesPlayerAndEndsGame** ( :x: )
  - **State of the system**: active players are `[P1, P2]`, bankrupt player is `P2`
  - **Expected output**: active players become `[P1]`, game status becomes game over, `P1` is declared winner, and views are refreshed

- **TC43: handleBankruptcy_WhenCurrentPlayerIsRemoved_AdvancesToNextActivePlayer** ( :x: )
  - **State of the system**: active players are `[P1, P2, P3]`, current player is `P1`, bankrupt player is `P1`
  - **Expected output**: active players become `[P2, P3]`, current player becomes `P2`, and views are refreshed

- **TC44: handleBankruptcy_WhenLastPlayerInTurnOrderIsRemoved_WrapsCurrentPlayer** ( :x: )
  - **State of the system**: active players are `[P1, P2, P3]`, current player is `P3`, bankrupt player is `P3`
  - **Expected output**: active players become `[P1, P2]`, current player becomes `P1`, and views are refreshed

---

## Method under test: `refreshViews()`

1. Input: current model state and view dependencies, Output: GUI components reflect model state
2. Input type: object references and current model state
3. Input boundary values: game not started, all dependencies present, one dependency null, first board index, last board index, no card, visible card

- **TC45: refreshViews_BeforeGameStart_ShowsEmptyOrInitialState** ( :x: )
  - **State of the system**: `game = null` or game status is not started; all view dependencies are non-null
  - **Expected output**: views show initial or empty state without throwing, or the method rejects refresh before game start consistently

- **TC46: refreshViews_WithAllViewsPresent_UpdatesEveryView** ( :x: )
  - **State of the system**: game is in progress, all view dependencies are non-null
  - **Expected output**: `boardView`, `playerInfoView`, `diceView`, and `cardView` receive the current model state exactly once

- **TC47: refreshViews_WithNullBoardView_ThrowsExceptionBeforePartialUpdate** ( :x: )
  - **State of the system**: `boardView = null`; other views and game are valid
  - **Expected output**: throws `NullPointerException`; avoids silently skipping the board update

- **TC48: refreshViews_WhenPlayerAtFirstBoardIndex_RendersPositionZero** ( :x: )
  - **State of the system**: current player is at board index `0`
  - **Expected output**: board view displays the player on GO / first tile and player info view shows the same position

- **TC49: refreshViews_WhenPlayerAtLastBoardIndex_RendersPositionThirtyOne** ( :x: )
  - **State of the system**: current player is at board index `31`
  - **Expected output**: board view displays the player on the last tile and player info view shows the same position

- **TC50: refreshViews_WhenNoCardIsActive_ClearsCardView** ( :x: )
  - **State of the system**: game is in progress and no chance/card action is active
  - **Expected output**: card view is cleared or hidden

- **TC51: refreshViews_WhenCardIsActive_DisplaysCurrentCard** ( :x: )
  - **State of the system**: current tile action produced a visible card effect
  - **Expected output**: card view displays the current card details and other views remain synchronized with the model

- **TC52: refreshViews_AfterBankruptcy_RemovesPlayerFromVisibleTurnOrder** ( :x: )
  - **State of the system**: `handleBankruptcy(P2)` has removed `P2` from active players
  - **Expected output**: player info view and board view no longer show `P2` as active; current player display points to the next valid player or winner
