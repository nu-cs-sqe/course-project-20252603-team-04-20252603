# BVA analysis for `GoToJailTile`

### Determine test cases

### Method under test: `getName()`

- **TC1: GoToJailTile reports its tile type** ( :white_check_mark: )
  - **State of the system**: A `GoToJailTile` is constructed.
  - **Expected output**: `getName()` returns `TileType.GOTOJAIL`.

### Method under test: `landOn(Player player, GameEngine game)`

- **TC2: Active player lands on GoToJailTile** ( :white_check_mark: )
  - **State of the system**: `player` is an active player, `game` is a valid `GameEngine`.
  - **Expected output**: `player.goToJail(Constants.JAIL_POSITION)` is called exactly once; player is moved to position 10 with `inJail = true`.

- **TC3: Null player input** ( :white_check_mark: )
  - **State of the system**: `player = null`, `game` is valid.
  - **Expected output**: Method rejects the invalid input (throws `IllegalArgumentException`); no state is mutated.

- **TC4: Null game input** ( :white_check_mark: )
  - **State of the system**: `player` is a valid active player, `game = null`.
  - **Expected output**: Method rejects the invalid input (throws `IllegalArgumentException`); no state is mutated.

- **TC5: Both player and game null** ( :white_check_mark: )
  - **State of the system**: `player = null`, `game = null`.
  - **Expected output**: Method rejects the invalid inputs and applies no effect.

- **TC6: Eliminated / inactive player lands on GoToJailTile** ( :white_check_mark: )
  - **State of the system**: `player.isActive() == false` (already eliminated), `game` is valid.
  - **Expected output**: No jail effect is applied; `player.goToJail` is not called.

- **TC7: `landOn` does not modify player's balance** ( :white_check_mark: )
  - **State of the system**: `player` is an active player, `game` is valid.
  - **Expected output**: After `landOn`, player's balance is unchanged; only `goToJail` is called, no `receive`/`buy`/`sell`.
