# BVA analysis for `GoTile`

### Determine test cases

### Method under test: `getName()`

- **TC1: GoTile reports its tile type** ( :white_check_mark: )
  - **State of the system**: A `GoTile` is constructed.
  - **Expected output**: `getName()` returns `TileType.GO`.

### Method under test: `landOn(Player player, GameEngine game)`

- **TC2: Active player lands on GO and receives the GO bonus** ( :white_check_mark: )
  - **State of the system**: `player.balance = 1000.0`, `game` is a valid in-progress `GameEngine`.
  - **Expected output**: `player.balance` becomes `1200.0` (bonus of $200 applied exactly once); `player.position` is unchanged.

- **TC3: Player with $0 balance lands on GO** ( :white_check_mark: )
  - **State of the system**: `player.balance = 0.0`, `game` is valid.
  - **Expected output**: `player.balance` becomes `200.0`; player is not bankrupt.

- **TC4: Null player input** ( :white_check_mark: )
  - **State of the system**: `player = null`, `game` is valid.
  - **Expected output**: The method rejects the invalid input (e.g. throws `NullPointerException` / `IllegalArgumentException`); no state is mutated.

- **TC5: Null game input** ( :white_check_mark: )
  - **State of the system**: `player` is a valid active player, `game = null`.
  - **Expected output**: The method rejects the invalid input (throws an exception) OR safely no-ops; the player's balance does not change spuriously.

- **TC6: Both player and game null** ( :white_check_mark: )
  - **State of the system**: `player = null`, `game = null`.
  - **Expected output**: The method rejects the invalid inputs and applies no effect.

- **TC7: Eliminated / inactive player lands on GO** ( :x: )
  - **State of the system**: `player.isBankrupt() == true` (already eliminated), `game` is valid.
  - **Expected output**: No bonus is applied to the eliminated player, OR the action is rejected. The eliminated player's balance is not increased.

- **TC8: Player at extreme upper balance lands on GO** ( :x: )
  - **State of the system**: `player.balance = Double.MAX_VALUE - 100`, `game` is valid.
  - **Expected output**: Bonus is rejected / safely capped so the balance does not overflow to `Infinity`; balance remains a finite, valid `double`.

- **TC10: `landOn` does not move the player off GO** ( :x: )
  - **State of the system**: `player.position = 0` (on GO), `game` is valid.
  - **Expected output**: After `landOn`, `player.position == 0` (GoTile must not change position; movement is the Board's responsibility).
