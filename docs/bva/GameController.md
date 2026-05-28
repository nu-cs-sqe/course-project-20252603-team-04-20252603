# BVA: `GameController`

---

## Constructor: `GameController(GameEngineWrapper game, Dice dice, Deck chanceDeck, double taxAmount)`

- **TC1: Valid dependencies and positive tax amount** ( :x: )
  - **State of the system**: non-null `game`, `dice`, `chanceDeck`, `taxAmount = 50.0`
  - **Expected output**: controller created successfully

- **TC2: Zero tax amount** ( :x: )
  - **State of the system**: non-null dependencies, `taxAmount = 0.0`
  - **Expected output**: controller created successfully

- **TC3: Negative tax amount** ( :x: )
  - **State of the system**: non-null dependencies, `taxAmount = -0.01`
  - **Expected output**: throws `IllegalArgumentException`

- **TC4: Null game** ( :x: )
  - **State of the system**: `game = null`, valid `dice`, valid `chanceDeck`, `taxAmount = 50.0`
  - **Expected output**: throws `NullPointerException`

- **TC5: Null dice** ( :x: )
  - **State of the system**: valid `game`, `dice = null`, valid `chanceDeck`, `taxAmount = 50.0`
  - **Expected output**: throws `NullPointerException`

- **TC6: Null chance deck** ( :x: )
  - **State of the system**: valid `game`, valid `dice`, `chanceDeck = null`, `taxAmount = 50.0`
  - **Expected output**: throws `NullPointerException`

---

## Method under test: `handleRollDice()`

- **TC7: Game not started** ( :x: )
  - **State of the system**: `status = NOT_STARTED`
  - **Expected output**: throws `IllegalStateException`

- **TC8: Game already over** ( :x: )
  - **State of the system**: `status = GAME_OVER`
  - **Expected output**: throws `IllegalStateException`

- **TC9: In progress, player passes GO** ( :x: )
  - **State of the system**: `status = IN_PROGRESS`, `oldPosition = 30`, dice total moves to `newPosition = 1`
  - **Expected output**: player receives GO bonus, then tile at `newPosition` is resolved

- **TC10: In progress, player does not pass GO** ( :x: )
  - **State of the system**: `status = IN_PROGRESS`, `oldPosition = 5`, dice total moves to `newPosition = 8`
  - **Expected output**: no GO bonus, tile at `newPosition` is resolved

---

## Method under test: `resolveTile(Player player, Tile tile)`

- **TC11: Null player** ( :x: )
  - **State of the system**: `player = null`, valid tile
  - **Expected output**: throws `NullPointerException`

- **TC12: Null tile** ( :x: )
  - **State of the system**: valid player, `tile = null`
  - **Expected output**: throws `NullPointerException`

- **TC13: Property tile unowned** ( :x: )
  - **State of the system**: `tile.getName() = PROPERTY`, `property.isOwned() = false`
  - **Expected output**: `TileActionType.OFFER_PURCHASE`

- **TC14: Property tile owned by current player** ( :x: )
  - **State of the system**: `tile.getName() = PROPERTY`, `property.isOwnedBy(player) = true`
  - **Expected output**: `TileActionType.NONE`

- **TC15: Property tile owned by another player, rent paid** ( :x: )
  - **State of the system**: `tile.getName() = PROPERTY`, `property.isOwnedBy(player) = false`, `property.chargeRent(player) = true`
  - **Expected output**: `TileActionType.PAY_RENT` with `amount = property.getRent()`

- **TC16: Property tile owned by another player, rent not paid** ( :x: )
  - **State of the system**: `tile.getName() = PROPERTY`, `property.isOwnedBy(player) = false`, `property.chargeRent(player) = false`
  - **Expected output**: `TileActionType.BANKRUPTCY_CHECK`

- **TC17: Chance tile with available cards** ( :x: )
  - **State of the system**: `tile.getName() = CHANCE`, deck has at least one card
  - **Expected output**: `TileActionType.DRAW_CARD` with the drawn card

- **TC18: Chance tile with empty deck** ( :x: )
  - **State of the system**: `tile.getName() = CHANCE`, unused and used piles are empty
  - **Expected output**: throws `IllegalStateException`

- **TC19: IRS tile, player can pay tax** ( :x: )
  - **State of the system**: `tile.getName() = IRS`, `player.remove(taxAmount) = true`, `taxAmount > 0`
  - **Expected output**: `TileActionType.PAY_TAX` with `amount = taxAmount`

- **TC20: IRS tile, player cannot pay tax** ( :x: )
  - **State of the system**: `tile.getName() = IRS`, `player.remove(taxAmount) = false`, `taxAmount > 0`
  - **Expected output**: `TileActionType.BANKRUPTCY_CHECK`

- **TC21: IRS tile, zero tax amount** ( :x: )
  - **State of the system**: `tile.getName() = IRS`, `taxAmount = 0.0`
  - **Expected output**: `TileActionType.PAY_TAX` with `amount = 0.0`

- **TC22: Go-to-jail tile** ( :x: )
  - **State of the system**: `tile.getName() = GOTOJAIL`
  - **Expected output**: `TileActionType.GO_TO_JAIL`, player position set to jail

- **TC23: Neutral tiles (GO, JAIL, FREE)** ( :x: )
  - **State of the system**: `tile.getName()` is one of `GO`, `JAIL`, `FREE`
  - **Expected output**: `TileActionType.NONE`

- **TC24: Unknown or null tile type** ( :x: )
  - **State of the system**: `tile.getName() = null`
  - **Expected output**: `TileActionType.NONE`

---

## Method under test: `discardChanceCard(Card card)`

- **TC25: Discard the last drawn card** ( :x: )
  - **State of the system**: `card` equals the most recently drawn card
  - **Expected output**: card moved to used pile, no exception

- **TC26: Discard null card** ( :x: )
  - **State of the system**: `card = null`
  - **Expected output**: throws `IllegalArgumentException`

- **TC27: Discard card that is not the last drawn** ( :x: )
  - **State of the system**: `card` is not equal to the most recently drawn card
  - **Expected output**: throws `IllegalArgumentException`

- **TC28: Discard an already discarded card** ( :x: )
  - **State of the system**: `card` is already in used pile
  - **Expected output**: throws `IllegalArgumentException`

---

## Method under test: `endTurn()`

- **TC29: In-progress game ends turn** ( :x: )
  - **State of the system**: `status = IN_PROGRESS`
  - **Expected output**: `game.nextTurn()` is called

- **TC30: Game not started** ( :x: )
  - **State of the system**: `status = NOT_STARTED`
  - **Expected output**: throws `IllegalStateException`

- **TC31: Game already over** ( :x: )
  - **State of the system**: `status = GAME_OVER`
  - **Expected output**: throws `IllegalStateException`
