# BVA analysis for `Tile`

### 1. Identify the input and output equivalent classes

Assumed public methods for the `Tile` interface:

```java
String getName();
void landOn(Player player, GameEngine game);
```

#### `getName()`

Input equivalent classes:
- Valid tile name
- Empty tile name
- Null tile name

Output equivalent classes:
- Returns the tile's name as a non-null `String`
- Rejects an invalid name before `getName()` can return it

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

- Input: `String name`, usually provided when constructing a concrete tile
- Output: `String`

#### `landOn(Player player, GameEngine game)`

- Input: `Player` object reference
- Input: `GameEngine` object reference
- Output: `void`
- Side effects: may update player state, game state, board state, balances, cards, ownership, or turn flow depending on the concrete tile

### 3. Concrete values along the edges

#### `getName()`

| Boundary | Concrete Value |
|---|---|
| Null name | `null` |
| Empty name | `""` |
| Shortest valid name | `"A"` |
| Normal valid name | `"GO"` |

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

- **TC1: Normal valid tile name** ( :x: )
  - **State of the system**: A concrete tile is created with a normal valid name, such as `"GO"`.
  - **Expected output**: `getName()` returns `"GO"`.

- **TC2: Shortest valid tile name** ( :x: )
  - **State of the system**: A concrete tile is created with the shortest valid name, such as `"A"`.
  - **Expected output**: `getName()` returns `"A"`.

- **TC3: Empty tile name** ( :x: )
  - **State of the system**: A concrete tile is created with an empty string `""` as its name.
  - **Expected output**: Construction is rejected, or `getName()` returns `""` if empty names are allowed by design.

- **TC4: Null tile name** ( :x: )
  - **State of the system**: A concrete tile is created with `null` as its name.
  - **Expected output**: Construction is rejected, and `getName()` should never return `null`.

### Method under test: `landOn(Player player, GameEngine game)`

- **TC5: Valid active player lands on tile** ( :x: )
  - **State of the system**: A valid active player lands on a tile while the game state is valid.
  - **Expected output**: The tile's effect is applied exactly once.

- **TC6: Null player input** ( :x: )
  - **State of the system**: `player` is `null` and `game` is valid.
  - **Expected output**: The method rejects the invalid player input, such as by throwing an exception.

- **TC7: Null game input** ( :x: )
  - **State of the system**: `player` is valid and `game` is `null`.
  - **Expected output**: The method rejects the invalid game input, such as by throwing an exception.

- **TC8: Null player and null game input** ( :x: )
  - **State of the system**: Both `player` and `game` are `null`.
  - **Expected output**: The method rejects the invalid inputs and does not apply any tile effect.

- **TC9: Eliminated player lands on tile** ( :x: )
  - **State of the system**: The player has already been eliminated before landing on the tile.
  - **Expected output**: No tile effect is applied to the eliminated player, or the method rejects the action.

