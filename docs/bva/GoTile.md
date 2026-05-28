### Method under test: `getName()`
1. Input: nothing, Output: the tile type for a GO tile
2. Input: None, Output: Enum
3. Output value: `TileType.GoTile`

- **TC1: getName_WhenTileIsGoTile_ReturnsGoTile** ( :white_check_mark: )
    - **State of the system**: a `GoTile` object exists
    - **Expected output**: returns `TileType.GoTile`

### Method under test: `landOn(Player player, GameEngine game)`
1. Input: a player and game state when the player lands on GO, Output: the player receives GO money or invalid input is rejected
2. Input: `Player` object reference and `GameEngine` object reference, Output: side effect on player balance or exception
3. Input: valid `Player`, null `Player`, valid `GameEngine`, null `GameEngine`; player balance values [0, 1000]

- **TC2: landOn_WhenPlayerWithZeroBalanceLandsOnGo_IncreasesBalanceByGoReward** ( :white_check_mark: )
    - **State of the system**: valid player, valid game, player balance = 0
    - **Expected output**: player balance becomes 200

- **TC3: landOn_WhenPlayerWithStartingBalanceLandsOnGo_IncreasesBalanceByGoReward** ( :white_check_mark: )
    - **State of the system**: valid player, valid game, player balance = 1000
    - **Expected output**: player balance becomes 1200

- **TC4: landOn_WithNullPlayer_ThrowsException** ( :white_check_mark: )
    - **State of the system**: player = null, game = valid game
    - **Expected output**: throws `NullPointerException`

- **TC5: landOn_WithNullGame_ThrowsException** ( :white_check_mark: )
    - **State of the system**: player = valid player, game = null
    - **Expected output**: throws `NullPointerException`

- **TC6: landOn_WithNullPlayerAndNullGame_ThrowsException** ( :white_check_mark: )
    - **State of the system**: player = null, game = null
    - **Expected output**: throws `IllegalArgumentException`