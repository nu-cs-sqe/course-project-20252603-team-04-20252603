### Method under test: `initializeBoard()`
1. Input: the board’s current tile collection, Output: whether the board initializes successfully
2. Input: collection size / count, Output: success or exception
3. Input values: 31, 32, 33

- **TC1: initializeBoard_With31Tiles_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `tiles.size()` = 31
  - **Expected output**: throws `IllegalStateException`

- **TC2: initializeBoard_With32Tiles_InitializesBoard** ( :white_check_mark: )
  - **State of the system**: `tiles.size()` = 32
  - **Expected output**: board initializes successfully

- **TC3: initializeBoard_With33Tiles_ThrowsException** ( :white_check_mark: )
  - **State of the system**: `tiles.size()` = 33
  - **Expected output**: throws `IllegalStateException`

### Method under test: `getTile(int index)`
1. Input: Requested board position, Output: The tile stored at that board position
2. Input: Interval, Output: `Tile` Object Reference
3. Input: [0, 31] (-1, 0, 31, 32), Output: The valid `Tile` reference

- **TC4: getTile_WithNegativeIndex_ThrowsException** ( :white_check_mark: )
  - **State of the system**: initialized board, `index` = -1
  - **Expected output**: throws `IndexOutOfBoundsException`

- **TC5: getTile_WithFirstIndex_ReturnsFirstTile** ( :white_check_mark: )
  - **State of the system**: initialized board, `index` = 0
  - **Expected output**: returns tile at index 0

- **TC6: getTile_WithLastIndex_ReturnsLastTile** ( :white_check_mark: )
  - **State of the system**: initialized board, `index` = 31
  - **Expected output**: returns tile at index 31

- **TC7: getTile_WithIndexEqualToBoardSize_ThrowsException** ( :white_check_mark: )
  - **State of the system**: initialized board, `index` = 32
  - **Expected output**: throws `IndexOutOfBoundsException`

### Method under test: `getPlayerPosition(Player player)`
1. Input: a player whose board position is requested, Output: the player’s current board position
2. Input: `Player` object reference, Output: array index / interval
3. Input: Valid `Player`, Invalid `Player`, Output: [0, 31]

- **TC8: getPlayerPosition_WhenPlayerNotOnBoard_ThrowsException** ( :white_check_mark: )
  - **State of the system**: valid player has no stored board position
  - **Expected output**: throws `IllegalArgumentException`

- **TC9: getPlayerPosition_WhenPlayerAtFirstIndex_ReturnsZero** ( :white_check_mark: )
  - **State of the system**: player exists on board at index 0
  - **Expected output**: returns 0

- **TC10: getPlayerPosition_WhenPlayerAtLastIndex_Returns31** ( :white_check_mark: )
  - **State of the system**: player exists on board at index 31
  - **Expected output**: returns 31

### Method under test: `movePlayer(Player player, int spaces)`
1. Input: a player and a number of spaces to move, Output: the player’s board position after movement
2. Input: `Player` object reference and spaces is an interval, Output: interval
3. Input: Valid `Player`, Invalid `Player`, spaces is [2, 12] (1, 2, 12, 13)

- **TC11: movePlayer_WhenPlayerNotOnBoard_ThrowsException** ( :white_check_mark: )
  - **State of the system**: player not stored on board, spaces = 2
  - **Expected output**: throws `IllegalArgumentException`

- **TC12: movePlayer_WithOneLessThanMinimumDiceRoll_ThrowsException** ( :white_check_mark: )
  - **State of the system**: player exists on board at index 0, spaces = 1
  - **Expected output**: throws `IllegalArgumentException`

- **TC13: movePlayer_WithMinimumDiceRoll_MovesTwoSpaces** ( :white_check_mark: )
  - **State of the system**: player exists on board at index 30, spaces = 2
  - **Expected output**: player moves to index 0

- **TC14: movePlayer_WithMaximumDiceRoll_MovesTwelveSpaces** ( :white_check_mark: )
  - **State of the system**: player exists on board at index 0, spaces = 12
  - **Expected output**: player moves to index 12

- **TC15: movePlayer_WithOneMoreThanMaximumDiceRoll_ThrowsException** ( :white_check_mark: )
  - **State of the system**: player exists on board at index 0, spaces = 13
  - **Expected output**: throws `IllegalArgumentException`

- **TC16: movePlayer_WithMaximumDiceRollNearEnd_WrapsAroundBoard** ( :x: )
  - **State of the system**: player exists on board at index 31, spaces = 12
  - **Expected output**: player moves to index 11

### Method under test: `setPlayerPosition(Player player, int index)`
1. Input: a player and a desired board position, Output: the player’s stored position is updated
2. Input `Player` object, array index, Output: side effect on player position map
3. Input: valid player, board position [0, 31] (-1, 0, 31, 32), Output: position stored

- **TC17: setPlayerPosition_WithOneLessThanFirstIndex_ThrowsException** ( :x: )
  - **State of the system**: valid player, index = -1
  - **Expected output**: throws `IndexOutOfBoundsException`

- **TC18: setPlayerPosition_WithFirstIndex_StoresPosition** ( :x: )
  - **State of the system**: valid player, index = 0
  - **Expected output**: `getPlayerPosition(player)` returns 0

- **TC19: setPlayerPosition_WithLastIndex_StoresPosition** ( :x: )
  - **State of the system**: valid player, index = 31
  - **Expected output**: `getPlayerPosition(player)` returns 31

- **TC20: setPlayerPosition_WithOneMoreThanLastIndex_ThrowsException** ( :x: )
  - **State of the system**: valid player, index = 32
  - **Expected output**: throws `IndexOutOfBoundsException`

### Method under test: `didPassGo(int oldPosition, int newPosition)`
1. Input: a previous board position and a new board position, Output: whether the movement passed GO
2. Input: oldPosition: array index, newPosition: array index, Output: boolean
3. Input: 
  - oldPosition = -1, newPosition = 0
  - oldPosition = 0, newPosition = 32
  - oldPosition = 0, newPosition = 0
  - oldPosition = 0, newPosition = 1
  - oldPosition = 30, newPosition = 31
  - oldPosition = 31, newPosition = 0
  - oldPosition = 30, newPosition = 1
Output: true, false, exception

- **TC21: didPassGo_WithOneLessThanFirstOldPosition_ThrowsException** ( :x: )
  - **State of the system**: oldPosition = -1, newPosition = 0
  - **Expected output**: throws `IndexOutOfBoundsException`

- **TC22: didPassGo_WithOneMoreThanLastNewPosition_ThrowsException** ( :x: )
  - **State of the system**: oldPosition = 0, newPosition = 32
  - **Expected output**: throws `IndexOutOfBoundsException`

- **TC23: didPassGo_WhenOldAndNewPositionsAreGo_ReturnsFalse** ( :x: )
  - **State of the system**: oldPosition = 0, newPosition = 0
  - **Expected output**: returns `false`

- **TC24: didPassGo_WhenMovingForwardFromGo_ReturnsFalse** ( :x: )
  - **State of the system**: oldPosition = 0, newPosition = 1
  - **Expected output**: returns `false`

- **TC25: didPassGo_WhenMovingNearEndWithoutWrap_ReturnsFalse** ( :x: )
  - **State of the system**: oldPosition = 30, newPosition = 31
  - **Expected output**: returns `false`

- **TC26: didPassGo_WhenMovingFromLastIndexToGo_ReturnsTrue** ( :x: )
  - **State of the system**: oldPosition = 31, newPosition = 0
  - **Expected output**: returns `true`

- **TC27: didPassGo_WhenMovementWrapsPastGo_ReturnsTrue** ( :x: )
  - **State of the system**: oldPosition = 30, newPosition = 1
  - **Expected output**: returns `true`

### Method under test: `getBoardSize()`
1. Input: nothing, Output: the number of tiles on the board
2. Input: None, Output: Count
3. Output: 32

- **TC28: getBoardSize_WhenBoardInitialized_Returns32** ( :x: )
  - **State of the system**: initialized board with 32 tiles
  - **Expected output**: returns 32