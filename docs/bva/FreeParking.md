# Free Parking BVA Analysis

## Method under test: `getName()`

- **TC1: Free parking tile name** ( :white_check_mark: )
  - **State of the system**: A FreeParking tile is created.
  - **Expected output**: `getName()` returns `TileType.FREE`.

- **TC2: Name is never null** ( :white_check_mark: )
  - **State of the system**: A FreeParking tile is created.
  - **Expected output**: `getName()` never returns `null`.


## Method under test: `landOn(Player player, GameEngine game)`

- **TC3: Valid player and game** ( :white_check_mark: )
  - **State of the system**: player is valid, game is valid.
  - **Expected output**: No state change; method completes without error.

- **TC4: Null player** ( :white_check_mark: )
  - **State of the system**: player = null, game is valid.
  - **Expected output**: Method rejects input (throws exception).

- **TC5: Null game** ( :white_check_mark: )
  - **State of the system**: player is valid, game = null.
  - **Expected output**: Method rejects input (throws exception).

- **TC6: Null player and null game** ( :white_check_mark: )
  - **State of the system**: player = null, game = null.
  - **Expected output**: Method rejects inputs (throws exception).
