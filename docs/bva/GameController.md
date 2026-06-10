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

- **TC10: getActivePlayers_ReturnedListCannotMutateControllerState** ( :white_check_mark: )
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

- **TC13: startGame_WithOnePlayer_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `players = [P1]`
  - **Expected output**: throws `IllegalArgumentException`; `game` remains not started

- **TC14: startGame_WithMinimumPlayers_StartsGame** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2]`
  - **Expected output**: `game` is created or reset, `getStatus()` returns `GameStatus.IN_PROGRESS`, `getActivePlayers()` returns `[P1, P2]`, `getCurrentPlayer()` returns `P1`, all players start at board index `0`, and views are refreshed once

- **TC15: startGame_WithMaximumPlayers_StartsGame** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2, P3, P4]`
  - **Expected output**: `game` is created or reset, active players are `[P1, P2, P3, P4]`, current player is `P1`, all players start at board index `0`, and views are refreshed once

- **TC16: startGame_WithMoreThanMaximumPlayers_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `players = [P1, P2, P3, P4, P5]`
  - **Expected output**: throws `IllegalArgumentException`; no new game is started

- **TC17: startGame_WithNullPlayer_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `players = [P1, null]`
  - **Expected output**: throws `NullPointerException`; no partial game is started

- **TC18: startGame_WhenGameAlreadyInProgress_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `gameEngine.getStatus()` returns `GameStatus.IN_PROGRESS`;
  - **Expected output**: throws `IllegalStateException`; `gameEngine.startGame()` is not called; existing game state remains unchanged

---

## Method under test: `handleRollDice()`

1. Input: current game state, Output: dice roll, player movement, tile action trigger, and refreshed views
2. Input type: game status, current player position, dice total interval
3. Input boundary values: game not started, dice total `2`, dice total `12`, board positions `0`, `30`, `31`

- **TC19: handleRollDice_WithMinimumRoll_MovesTwoSpaces** ( :white_check_mark: )
  - **State of the system**: current player `P1` is at index `0`; dice total is controlled to `2`
  - **Expected output**: `P1` moves to index `2`, the tile at index `2` is processed, and views are refreshed

- **TC20: handleRollDice_WithMaximumRoll_MovesTwelveSpaces** ( :white_check_mark: )
  - **State of the system**: current player `P1` is at index `0`; dice total is controlled to `12`
  - **Expected output**: `P1` moves to index `12`, the tile at index `12` is processed, and views are refreshed

- **TC21: handleRollDice_WhenCurrentPlayerIsBankrupt_DoesNotMovePlayer** ( :white_check_mark: )
  - **State of the system**: current player has been removed from active players
  - **Expected output**: controller skips or rejects the roll for that player; no removed player position changes

- **TC22: handleRollDice_WhenTileEffectCausesBankruptcy_HandlesBankruptcy** ( :white_check_mark: )
  - **State of the system**: current player lands on a tile that requires a payment greater than the player's available balance and assets
  - **Expected output**: `handleBankruptcy(currentPlayer)` is triggered, the player is removed from active turn order, and views are refreshed

---

## Method under test: `handleTileAction(TileAction action)`

1. Input: selected tile action, Output: tile effect is applied or rejected
2. Input type: `TileAction` object reference and action-specific values
3. Input boundary values: `null`, valid action, action requiring exact balance, action requiring more than balance

- **TC23: handleTileAction_WithNullAction_ThrowsException** ( :white_check_mark: )
  - **State of the system**: game is in progress, `action = null`
  - **Expected output**: throws `NullPointerException`; no game state changes

- **TC24: handleTileAction_WithValidNoOpAction_RefreshesViewsOnly** ( :white_check_mark: )
  - **State of the system**: current player is on a tile with no required effect, `action = NO_OP` or equivalent
  - **Expected output**: player balance, ownership, and position remain unchanged; views are refreshed

- **TC25: handleTileAction_WithPurchaseAtExactBalance_AllowsPurchase** ( :white_check_mark: )
  - **State of the system**: current player lands on an unowned property; player balance equals property price
  - **Expected output**: property becomes owned by current player, player balance becomes `0`, and views are refreshed

- **TC26: handleTileAction_WithOptionalPurchaseAboveBalance_DoesNotPurchase** ( :white_check_mark: )
  - **State of the system**: current player lands on an unowned property; property price is greater than player balance
  - **Expected output**: property remains unowned, player balance is unchanged, and views are refreshed or purchase is rejected

- **TC27: handleTileAction_WithMandatoryPaymentAtExactBalance_PaysSuccessfully** ( :white_check_mark: )
  - **State of the system**: current player owes rent, tax, or card payment exactly equal to current balance
  - **Expected output**: payment is applied, player balance becomes `0`, player remains active unless the model treats zero balance as bankrupt, and views are refreshed

- **TC28: handleTileAction_WithMandatoryPaymentAboveBalance_TriggersBankruptcy** ( :white_check_mark: )
  - **State of the system**: current player owes rent, tax, or card payment greater than available balance and assets
  - **Expected output**: `handleBankruptcy(currentPlayer)` is triggered, player is removed from active players, and views are refreshed

- **TC29: handleTileAction_WithActionForWrongTile_RejectsAction** ( :white_check_mark: )
  - **State of the system**: current player is not on a property tile, but `action = BUY_PROPERTY` or equivalent
  - **Expected output**: action is rejected; player balance, ownership, and position remain unchanged

---

## Method under test: `handleEndTurn()`

1. Input: current game and turn state, Output: current player advances or game ends
2. Input type: active player count and current player index
3. Input boundary values: game not started, active player count `1`, `2`, `4`, last player index

- **TC30: handleEndTurn_WithTwoPlayers_AdvancesToSecondPlayer** ( :white_check_mark: )
  - **State of the system**: active players are `[P1, P2]`, current player is `P1`
  - **Expected output**: current player becomes `P2`, turn controls update, and views are refreshed

- **TC31: handleEndTurn_WithMaximumPlayersMiddleTurn_AdvancesToNextPlayer** ( :white_check_mark: )
  - **State of the system**: active players are `[P1, P2, P3, P4]`, current player is `P2`
  - **Expected output**: current player becomes `P3`, turn controls update, and views are refreshed

- **TC32: handleEndTurn_WithMaximumPlayersAtLastTurn_WrapsToFirstPlayer** ( :white_check_mark: )
  - **State of the system**: active players are `[P1, P2, P3, P4]`, current player is `P4`
  - **Expected output**: current player becomes `P1`, turn controls update, and views are refreshed

- **TC33: handleEndTurn_WithOneActivePlayer_EndsGame** ( :white_check_mark: )
  - **State of the system**: active players are `[P1]`
  - **Expected output**: game status becomes game over, `P1` is declared winner, and views show the final state

- **TC34: handleEndTurn_WhenNextPlayerIsBankrupt_SkipsRemovedPlayer** ( :white_check_mark: )
  - **State of the system**: original turn order was `[P1, P2, P3]`, `P2` has been removed, current player is `P1`
  - **Expected output**: current player becomes `P3`; removed player does not receive a turn

---

## Method under test: `handleBankruptcy(Player player)`

1. Input: bankrupt player, Output: player removed or game ends
2. Input type: `Player` object reference and active player count
3. Input boundary values: `null`, player not in game, active player count `2`, `3`, current player at first or last index

- **TC35: handleBankruptcy_WithNullPlayer_ThrowsException** ( :white_check_mark: )
  - **State of the system**: game is in progress, `player = null`
  - **Expected output**: throws `NullPointerException`; active players remain unchanged

- **TC36: handleBankruptcy_WithPlayerNotInGame_DoesNotChangeGame** ( :white_check_mark: )
  - **State of the system**: active players are `[P1, P2]`, `player = P3`
  - **Expected output**: operation is rejected or ignored; active players remain `[P1, P2]`

- **TC37: handleBankruptcy_WithThreePlayers_RemovesPlayerAndContinuesGame** ( :white_check_mark: )
  - **State of the system**: active players are `[P1, P2, P3]`, bankrupt player is `P2`
  - **Expected output**: active players become `[P1, P3]`, game remains in progress, and views are refreshed

- **TC38: handleBankruptcy_WithTwoPlayers_RemovesPlayerAndEndsGame** ( :white_check_mark: )
  - **State of the system**: active players are `[P1, P2]`, bankrupt player is `P2`
  - **Expected output**: active players become `[P1]`, game status becomes game over, `P1` is declared winner, and views are refreshed

---

## Method under test: `refreshViews()`

1. Input: current model state and view dependencies, Output: GUI components reflect model state
2. Input type: object references and current model state
3. Input boundary values: game not started, all dependencies present, one dependency null, first board index, last board index, no card, visible card

- **TC39: refreshViews_WithAllViewsPresent_UpdatesEveryView** ( :white_check_mark: )
  - **State of the system**: game is in progress, all view dependencies are non-null
  - **Expected output**: `boardView`, `playerInfoView`, `diceView`, and `cardView` receive the current model state exactly once

- **TC40: refreshViews_WithNullBoardView_ThrowsExceptionBeforePartialUpdate** ( :white_check_mark: )
  - **State of the system**: `boardView = null`; other views and game are valid
  - **Expected output**: throws `NullPointerException`; avoids silently skipping the board update

- **TC41: refreshViews_WhenPlayerAtFirstBoardIndex_RendersPositionZero** ( :white_check_mark: )
  - **State of the system**: current player is at board index `0`
  - **Expected output**: board view displays the player on GO / first tile and player info view shows the same position

- **TC42: refreshViews_WhenPlayerAtLastBoardIndex_RendersPositionThirtyOne** ( :white_check_mark: )
  - **State of the system**: current player is at board index `31`
  - **Expected output**: board view displays the player on the last tile and player info view shows the same position

- **TC43: refreshViews_WhenNoCardIsActive_ClearsCardView** ( :white_check_mark: )
  - **State of the system**: game is in progress and no chance/card action is active
  - **Expected output**: card view is cleared or hidden

- **TC44: refreshViews_WhenCardIsActive_DisplaysCurrentCard** ( :white_check_mark: )
  - **State of the system**: current tile action produced a visible card effect
  - **Expected output**: card view displays the current card details and other views remain synchronized with the model

- **TC45: refreshViews_AfterBankruptcy_RemovesPlayerFromVisibleTurnOrder** ( :white_check_mark: )
  - **State of the system**: `handleBankruptcy(P2)` has removed `P2` from active players
  - **Expected output**: player info view and board view no longer show `P2` as active; current player display points to the next valid player or winner

---

## Turn-flow extension (end-to-end wiring)

These methods orchestrate a complete turn. Optional collaborators (`PropertyController`,
`CardController`, `JailController`, `PropertyPromptView`, `BankruptcyView`) are setter-injected
(default `null`) so the frozen 6-arg constructor and TC1–TC45 are untouched. Every collaborator that
is not under test is an EasyMock mock.

### Method under test: `resolveLanding()`

Resolves the effect of the tile the current player is standing on. Pure dispatch — one small handler
per tile category.

1. Input: current player + their board tile, Output: the correct tile effect / prompt is triggered
2. Input type: `Tile` subtype + property ownership + affordability
3. Input boundary values: unowned-affordable property, unowned-unaffordable property, property owned by
   another, property owned by self, IRS tile, chance tile, go-to-jail tile, GO tile, neutral tile

- **TC46: resolveLanding_OnUnownedAffordableProperty_ShowsPurchasePrompt** ( :construction: )
  - **State**: current tile is an unowned `Property`, `player.canAfford(price)` is `true`
  - **Expected**: `propertyPromptView.showProperty(property, player)` is shown and buy/decline
    listeners are registered; no money moves yet
- **TC47: resolveLanding_OnUnownedUnaffordableProperty_RefreshesWithoutPrompt** ( :construction: )
  - **State**: current tile is an unowned `Property`, `player.canAfford(price)` is `false`
  - **Expected**: no prompt is shown; views are refreshed; ownership/balance unchanged
- **TC48: resolveLanding_OnPropertyOwnedByAnother_ChargesRent** ( :construction: )
  - **State**: current tile is a `Property` owned by another active player; renter can afford rent
  - **Expected**: rent is charged via `PropertyController.handleRentPayment`; views refreshed
- **TC49: resolveLanding_OnPropertyOwnedBySelf_RefreshesOnly** ( :construction: )
  - **State**: current tile is a `Property` owned by the current player
  - **Expected**: no payment/prompt; views refreshed
- **TC50: resolveLanding_OnIrsTile_PaysTax** ( :construction: )
  - **State**: current tile is an `IRSTile`; player can afford the tax
  - **Expected**: `PAY_TAX` of `Constants.GO_BONUS` is applied; views refreshed
- **TC51: resolveLanding_OnChanceTile_DrawsAndShowsCard** ( :construction: )
  - **State**: current tile is a `ChanceTile`
  - **Expected**: `CardController.drawChanceCard(player)` is drawn and `cardView` displays it; effect
    is not applied until Proceed
- **TC52: resolveLanding_OnGoToJailTile_SendsToJail** ( :construction: )
  - **State**: current tile is a `GoToJailTile`
  - **Expected**: `JailController.sendToJail(player)` is called; views refreshed
- **TC53: resolveLanding_OnNeutralTile_RefreshesOnly** ( :construction: )
  - **State**: current tile is `JailTile` (just visiting) or `FreeParking`
  - **Expected**: no payment/prompt; views refreshed

### Method under test: `playTurn()`

Owns one full turn: jail handling, roll, move, GO-pass bonus, then `resolveLanding()`.

1. Input: current game state, Output: a fully resolved turn
2. Input type: bankrupt/jailed flags, dice total, pass-GO boundary
3. Input boundary values: bankrupt current player, jailed current player, passes GO, lands on GO,
   lands elsewhere

- **TC54: playTurn_WhenCurrentPlayerBankrupt_DoesNothing** ( :construction: )
  - **State**: `currentPlayer.isBankrupt()` is `true`
  - **Expected**: no roll/move; returns without changing state
- **TC55: playTurn_WhenNotInJail_RollsMovesAndResolves** ( :construction: )
  - **State**: active, not in jail; controlled dice total
  - **Expected**: `dice.roll()`, `diceView.showRollResult`, `gameEngine.movePlayer`, then landing is
    resolved
- **TC56: playTurn_WhenPassesGoWithoutLanding_GrantsGoBonus** ( :construction: )
  - **State**: move wraps past GO and lands on a non-GO tile (`didPassGo` true)
  - **Expected**: `COLLECT_MONEY` of `Constants.GO_BONUS` is granted exactly once (no double with a
    GO landing)

#### Jail turn (a jailed player's `playTurn`)

When the current player starts their turn in jail, the controller attempts to roll doubles; if that
fails and the player has reached `MAX_JAIL_TURNS`, the jail fee is auto-paid so play can continue.

- **TC61: playTurn_WhenInJailAndRollsDoubles_LeavesJail** ( :construction: )
  - **State**: current player is in jail; `JailController.attemptRollDoubles` returns `true`
  - **Expected**: no board move; views refreshed
- **TC62: playTurn_WhenInJailNoDoublesBelowMaxTurns_StaysInJail** ( :construction: )
  - **State**: in jail, `attemptRollDoubles` false, `getJailTurnCount` below `MAX_JAIL_TURNS`
  - **Expected**: no fee paid; views refreshed (player remains jailed)
- **TC63: playTurn_WhenInJailNoDoublesAtMaxTurns_PaysFee** ( :construction: )
  - **State**: in jail, `attemptRollDoubles` false, `getJailTurnCount` equals `MAX_JAIL_TURNS`
  - **Expected**: `JailController.payJailFee` is invoked; views refreshed

### Method under test: `handleTileAction(...)` — additive cases

Existing `NONE`/`DRAW_CARD`/`OFFER_PURCHASE`/`PAY_BANK`/`PAY_TAX` cases unchanged (TC23–TC29). Adds:

- **TC57: handleTileAction_WithPayRentAffordable_TransfersRent** ( :construction: )
  - **State**: `PAY_RENT` action, renter can afford rent
  - **Expected**: rent moves renter→owner via `PropertyController.handleRentPayment`; views refreshed
- **TC58: handleTileAction_WithCollectMoney_CreditsPlayer** ( :construction: )
  - **State**: `COLLECT_MONEY` action with amount
  - **Expected**: `player.receive(amount)`; views refreshed
- **TC59: handleTileAction_WithGoToJail_SendsToJail** ( :construction: )
  - **State**: `GO_TO_JAIL` action
  - **Expected**: `JailController.sendToJail(player)`; views refreshed

### Forced sale + elimination (rent / tax shortfall)

When a required payment exceeds the player's cash, the controller first forces property sales
(`PropertyController.handleForcedSale`, 80% resale) and only eliminates the player if that still
isn't enough.

- **TC64: resolveLanding_OnRentShortfallForcedSaleCovers_PaysRent** ( :construction: )
  - **State**: renter can't afford rent, but `handleForcedSale` raises enough; second
    `handleRentPayment` succeeds
  - **Expected**: rent is paid after the sale; views refreshed; player not eliminated
- **TC65: resolveLanding_OnRentShortfallForcedSaleFails_EliminatesPlayer** ( :construction: )
  - **State**: renter can't afford rent and `handleForcedSale` can't raise enough
  - **Expected**: `handleBankruptcy` runs and `BankruptcyView.showPlayerEliminated` is shown
- **TC66: resolveLanding_OnTaxShortfallForcedSaleCovers_PaysTax** ( :construction: )
  - **State**: player can't afford IRS tax, but `handleForcedSale` raises enough; `remove` then succeeds
  - **Expected**: tax is paid after the sale; views refreshed
- **TC67: resolveLanding_OnTaxShortfallForcedSaleFails_EliminatesPlayer** ( :construction: )
  - **State**: player can't afford IRS tax and `handleForcedSale` can't raise enough
  - **Expected**: `handleBankruptcy` runs and `BankruptcyView.showPlayerEliminated` is shown

### Method under test: `applyDrawnCard()`

Applies the currently displayed chance card when the player clicks Proceed.

1. Input: the active drawn card, Output: card effect applied and card cleared
2. Input type: presence/absence of an active card
3. Input boundary values: a card is active, no card is active

- **TC60: applyDrawnCard_WithActiveCard_AppliesEffectAndClears** ( :construction: )
  - **State**: a chance card has been drawn and is active
  - **Expected**: `CardController.applyCard(card, currentPlayer)` is invoked, the active card is
    cleared, and views are refreshed (card view closed)
