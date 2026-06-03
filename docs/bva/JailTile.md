# BVA analysis for `JailTile`

### Determine test cases

### Method under test: `getName()`

- **TC1: JailTile reports its tile type** ( :white_check_mark: )
    - **State of the system**: A `JailTile` is constructed.
    - **Expected output**: `getName()` returns `TileType.JAIL`.

### Method under test: `landOn(Player player, GameEngine game)`

- **TC2: Active player lands on jail while not in jail** ( :white_check_mark: )
    - **State of the system**: `player.inJail() == false`, `player.isActive() == true`, `game` is valid.
    - **Expected output**: No jail status is applied. Player remains not in jail, balance is unchanged, and position is unchanged.

- **TC3: Active player already in jail lands/remains on jail** ( :white_check_mark: )
    - **State of the system**: `player.inJail() == true`, player position is the jail position, `game` is valid.
    - **Expected output**: Player remains in jail. `landOn` does not call `leaveJail()` or otherwise change jail state.

- **TC4: Null player input** ( :white_check_mark: )
    - **State of the system**: `player = null`, `game` is valid.
    - **Expected output**: The method rejects the invalid input, e.g. throws `NullPointerException`; no state is mutated.

- **TC5: Null game input** ( :white_check_mark: )
    - **State of the system**: `player` is valid, `game = null`.
    - **Expected output**: The method rejects the invalid input, e.g. throws `NullPointerException`; player state is unchanged.

- **TC6: Both player and game null** ( :white_check_mark: )
    - **State of the system**: `player = null`, `game = null`.
    - **Expected output**: The method rejects the invalid inputs and applies no effect. e.g. throws `NullPointerException`.

- **TC7: Inactive player lands on jail** ( :white_check_mark: )
    - **State of the system**: `player.isActive() == false`, `game` is valid.
    - **Expected output**: No effect is applied. Balance, position, and jail state remain unchanged.

- **TC8: JailTile does not move the player** ( :white_check_mark: )
    - **State of the system**: Player is already positioned on the jail tile.
    - **Expected output**: After `landOn`, player position is unchanged. Movement is the board/game engine’s responsibility, not the tile’s.
