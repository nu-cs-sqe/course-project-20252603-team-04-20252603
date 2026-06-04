# BVA analysis for `TileAction`

`TileAction` represents the result of landing on a tile or resolving a card effect caused by landing on a tile. Controllers use it to decide which GUI prompt, animation, or follow-up rule check happens next.

Class under test:

```java
TileAction(
        TileActionType type,
        Player player,
        Tile tile,
        Card card,
        double amount);
TileActionType getType();
Player getPlayer();
Tile getTile();
Card getCard();
double getAmount();
```

Supported action types:

```java
NONE
COLLECT_MONEY
OFFER_PURCHASE
PAY_RENT
PAY_BANK
DRAW_CARD
PAY_TAX
MOVE_PLAYER
GO_TO_JAIL
BANKRUPTCY_CHECK
GAME_OVER
```

### 1. Identify the input and output equivalent classes

#### Constructor

Input equivalent classes:
- Valid required `TileActionType`
- Invalid null `TileActionType`
- Valid optional `Player` reference
- Omitted optional `Player` reference
- Valid optional `Tile` reference
- Omitted optional `Tile` reference
- Valid optional `Card` reference
- Omitted optional `Card` reference
- Valid zero amount
- Valid positive finite amount
- Invalid negative amount
- Invalid non-finite amount: `Double.NaN`, `Double.POSITIVE_INFINITY`, or `Double.NEGATIVE_INFINITY`

Output equivalent classes:
- Creates a `TileAction` storing the supplied type, references, and amount
- Rejects missing required action type
- Rejects invalid amount values

#### Getters

Input equivalent classes:
- `TileAction` created with populated optional references
- `TileAction` created with omitted optional references
- `TileAction` created with zero amount
- `TileAction` created with positive amount

Output equivalent classes:
- Returns the exact `TileActionType`
- Returns the same `Player`, `Tile`, and `Card` references supplied at construction
- Returns `null` for optional references omitted at construction
- Returns the exact stored amount

### 2. Determine the data type

#### Constructor

- Input: `TileActionType type`, required enum reference
- Input: `Player player`, optional object reference
- Input: `Tile tile`, optional object reference
- Input: `Card card`, optional object reference
- Input: `double amount`, finite non-negative number
- Output: `TileAction` object or `IllegalArgumentException`

#### Getters

- Output: `TileActionType`
- Output: `Player` reference or `null`
- Output: `Tile` reference or `null`
- Output: `Card` reference or `null`
- Output: `double`

### 3. Concrete values along the edges

#### `TileActionType type`

| Boundary | Concrete Value |
|---|---|
| No follow-up action | `TileActionType.NONE` |
| Player receives money | `TileActionType.COLLECT_MONEY` |
| Controller should offer property purchase | `TileActionType.OFFER_PURCHASE` |
| Player owes another player rent | `TileActionType.PAY_RENT` |
| Player owes bank or generic fee | `TileActionType.PAY_BANK` |
| Controller should display or resolve a drawn card | `TileActionType.DRAW_CARD` |
| Player owes tax | `TileActionType.PAY_TAX` |
| Controller should move or animate player movement | `TileActionType.MOVE_PLAYER` |
| Player must be sent to jail | `TileActionType.GO_TO_JAIL` |
| Controller should check whether player can recover from debt | `TileActionType.BANKRUPTCY_CHECK` |
| Game has ended | `TileActionType.GAME_OVER` |
| Invalid required type | `null` |

#### Optional references

| Boundary | Concrete Value |
|---|---|
| Player context present | `player = validPlayer` |
| Player context omitted | `player = null` |
| Tile context present | `tile = validTile` |
| Tile context omitted | `tile = null` |
| Card context present | `card = validCard` |
| Card context omitted | `card = null` |

#### `double amount`

| Boundary | Concrete Value |
|---|---|
| Minimum valid amount | `0.0` |
| Positive valid amount | `100.0` |
| Invalid just below minimum | `-0.01` |
| Invalid not-a-number | `Double.NaN` |
| Invalid positive infinity | `Double.POSITIVE_INFINITY` |
| Invalid negative infinity | `Double.NEGATIVE_INFINITY` |

### 4. Determine test cases

### Method under test: `TileAction(...)`

- **TC1: Valid action stores all fields** ( :white_check_mark: )
  - **State of the system**: `type = DRAW_CARD`, valid `player`, valid `tile`, valid `card`, `amount = 100.0`.
  - **Expected output**: `TileAction` is created successfully and all getters return the supplied values.

- **TC2: NONE action with omitted optional fields** ( :white_check_mark: )
  - **State of the system**: `type = NONE`, `player = null`, `tile = null`, `card = null`, `amount = 0.0`.
  - **Expected output**: `TileAction` is created successfully; optional getters return `null`; amount returns `0.0`.

- **TC3: COLLECT_MONEY action stores collection amount** ( :white_check_mark: )
  - **State of the system**: `type = COLLECT_MONEY`, valid `player`, valid GO `tile`, `card = null`, `amount = 200.0`.
  - **Expected output**: `TileAction` is created successfully; getters return `COLLECT_MONEY`, the same player and tile references, `null` card, and `200.0`.

- **TC4: PAY_BANK action stores payment amount** ( :white_check_mark: )
  - **State of the system**: `type = PAY_BANK`, valid `player`, valid `tile`, `card = null`, `amount = 100.0`.
  - **Expected output**: `TileAction` is created successfully; getters return `PAY_BANK`, the same player and tile references, `null` card, and `100.0`.

- **TC5: MOVE_PLAYER action stores destination tile context** ( :white_check_mark: )
  - **State of the system**: `type = MOVE_PLAYER`, valid `player`, valid destination `tile`, `card = null`, `amount = 0.0`.
  - **Expected output**: `TileAction` is created successfully; getters return `MOVE_PLAYER`, the same player and destination tile references, `null` card, and `0.0`.

- **TC6: Null action type** ( :white_check_mark: )
  - **State of the system**: `type = null`, optional fields omitted, `amount = 0.0`.
  - **Expected output**: `IllegalArgumentException` is thrown.

- **TC7: Negative amount** ( :white_check_mark: )
  - **State of the system**: `type = PAY_TAX`, `amount = -0.01`.
  - **Expected output**: `IllegalArgumentException` is thrown.

- **TC8: NaN amount** ( :white_check_mark: )
  - **State of the system**: `type = PAY_TAX`, `amount = Double.NaN`.
  - **Expected output**: `IllegalArgumentException` is thrown.

- **TC9: Infinite amount** ( :white_check_mark: )
  - **State of the system**: `type = PAY_TAX`, `amount = Double.POSITIVE_INFINITY`.
  - **Expected output**: `IllegalArgumentException` is thrown.

- **TC10: All defined action types are accepted** ( :white_check_mark: )
  - **State of the system**: Construct one `TileAction` for every value in `TileActionType.values()`.
  - **Expected output**: Construction succeeds for `NONE`, `COLLECT_MONEY`, `OFFER_PURCHASE`, `PAY_RENT`, `PAY_BANK`, `DRAW_CARD`, `PAY_TAX`, `MOVE_PLAYER`, `GO_TO_JAIL`, `BANKRUPTCY_CHECK`, and `GAME_OVER`.

### Method under test: Getters

- **TC11: Getter references preserve identity** ( :white_check_mark: )
  - **State of the system**: A `TileAction` is constructed with valid object references.
  - **Expected output**: `getPlayer()`, `getTile()`, and `getCard()` return the same object references passed into the constructor.

- **TC12: Getter amount preserves numeric value** ( :white_check_mark: )
  - **State of the system**: A `TileAction` is constructed with `amount = 100.0`.
  - **Expected output**: `getAmount()` returns `100.0`.
