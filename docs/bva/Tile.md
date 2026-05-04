# BVA analysis for `Tile`

### 1. Identify the input and output equivalent classes

Assumed public methods for the `Tile` interface:

```java
TileType getName();
void landOn(Player player, GameEngine game);
```

#### `getName()`

Input equivalent classes:
- Valid `TileType` enum value
- Null `TileType` enum value

Output equivalent classes:
- Returns the tile's name as a non-null `TileType`
- Rejects an invalid tile name before `getName()` can return it

#### `landOn(Player player, GameEngine game)`

Input equivalent classes:
- Valid active player and valid game state
- Null player
- Null game state
- Eliminated or inactive player

Output equivalent classes:
- Tile effect is applied once
- Invalid input is rejected
- No tile effect is applied

### 2. Determine the data type 

#### `getName()`

- Input: `TileType name`, usually provided when constructing a concrete tile
- Output: `TileType`, or an exception such as `IllegalArgumentException` or `NullPointerException` if the tile name is invalid

#### `landOn(Player player, GameEngine game)`

- Input: `Player` object reference
- Input: `GameEngine` object reference
- Output: `void`
- Side effects: may update player state, game state, board state, balances, cards, ownership, or turn flow depending on the concrete tile

### 3. Concrete values along the edges

#### `getName()`

| Boundary | Concrete Value |
|---|---|
| Valid property tile name | `TileType.PROPERTY` |
| Valid chance tile name | `TileType.CHANCE` |
| Valid IRS tile name | `TileType.IRS` |
| Valid GO tile name | `TileType.GO` |
| Valid free tile name | `TileType.FREE` |
| Valid jail tile name | `TileType.JAIL` |
| Valid go-to-jail tile name | `TileType.GOTOJAIL` |
| Null tile name | `null` |

#### `landOn(Player player, GameEngine game)`

| Boundary | Concrete Value |
|---|---|
| Null player | `player = null`, `game = validGame` |
| Null game | `player = validPlayer`, `game = null` |
| Both inputs null | `player = null`, `game = null` |
| Valid active player | `player = activePlayer`, `game = validGame` |
| Invalid inactive player | `player = eliminatedPlayer`, `game = validGame` |

### 4. Determine test cases

### Method under test: `getName()`

- **TC1: Property tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.PROPERTY` as its name.
  - **Expected output**: `getName()` returns `TileType.PROPERTY`.

- **TC2: Chance tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.CHANCE` as its name.
  - **Expected output**: `getName()` returns `TileType.CHANCE`.

- **TC3: IRS tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.IRS` as its name.
  - **Expected output**: `getName()` returns `TileType.IRS`.

- **TC4: GO tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.GO` as its name.
  - **Expected output**: `getName()` returns `TileType.GO`.

- **TC5: Free tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.FREE` as its name.
  - **Expected output**: `getName()` returns `TileType.FREE`.

- **TC6: Jail tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.JAIL` as its name.
  - **Expected output**: `getName()` returns `TileType.JAIL`.

- **TC7: Go-to-jail tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `TileType.GOTOJAIL` as its name.
  - **Expected output**: `getName()` returns `TileType.GOTOJAIL`.

- **TC8: Null tile name** ( :white_check_mark: )
  - **State of the system**: A concrete tile is created with `null` as its name.
  - **Expected output**: Construction is rejected, and `getName()` should never return `null`.

### Method under test: `landOn(Player player, GameEngine game)`

- **TC9: Valid active player lands on tile** ( :x: )
  - **State of the system**: A valid active player lands on a tile while the game state is valid.
  - **Expected output**: The tile's effect is applied exactly once.

- **TC10: Null player input** ( :x: )
  - **State of the system**: `player` is `null` and `game` is valid.
  - **Expected output**: The method rejects the invalid player input, such as by throwing an exception.

- **TC11: Null game input** ( :x: )
  - **State of the system**: `player` is valid and `game` is `null`.
  - **Expected output**: The method rejects the invalid game input, such as by throwing an exception.

- **TC12: Null player and null game input** ( :x: )
  - **State of the system**: Both `player` and `game` are `null`.
  - **Expected output**: The method rejects the invalid inputs and does not apply any tile effect.

- **TC13: Eliminated player lands on tile** ( :x: )
  - **State of the system**: The player has already been eliminated before landing on the tile.
  - **Expected output**: No tile effect is applied to the eliminated player, or the method rejects the action.

