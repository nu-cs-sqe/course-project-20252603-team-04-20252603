### Method under test: `validatePlayerCount(int count)`
1. Input: requested number of players, Output: whether the player count is valid
2. Input: interval, Output: boolean
3. Input: valid player count range [2, 4], values: 1, 2, 4, 5

- **TC1: validatePlayerCount_WithOneLessThanMinimum_ReturnsFalse** ( :white_check_mark: )
    - **State of the system**: count = 1
    - **Expected output**: returns false

- **TC2: validatePlayerCount_WithMinimumPlayerCount_ReturnsTrue** ( :white_check_mark: )
    - **State of the system**: count = 2
    - **Expected output**: returns true

- **TC3: validatePlayerCount_WithMaximumPlayerCount_ReturnsTrue** ( :white_check_mark: )
    - **State of the system**: count = 4
    - **Expected output**: returns true

- **TC4: validatePlayerCount_WithOneMoreThanMaximum_ReturnsFalse** ( :white_check_mark: )
    - **State of the system**: count = 5
    - **Expected output**: returns false

### Method under test: `createPlayerConfigs()`
1. Input: player setup information from the main menu, Output: a list of player configuration objects
2. Input: player count is an interval, player names are strings, player icons are object references, Output: collection size / count
3. Input: valid player count range [2, 4], values: 1, 2, 4, 5; valid names, empty names; valid icons, null icons

- **TC5: createPlayerConfigs_WithOneLessThanMinimumPlayers_ThrowsException** ( :white_check_mark: )
    - **State of the system**: player count = 1, one valid player name, one valid icon
    - **Expected output**: throws `IllegalArgumentException`

- **TC6: createPlayerConfigs_WithMinimumPlayers_ReturnsTwoPlayerConfigs** ( :white_check_mark: )
    - **State of the system**: player count = 2, two valid player names, two valid icons
    - **Expected output**: returns a list with 2 player configs

- **TC7: createPlayerConfigs_WithMaximumPlayers_ReturnsFourPlayerConfigs** ( :white_check_mark: )
    - **State of the system**: player count = 4, four valid player names, four valid icons
    - **Expected output**: returns a list with 4 player configs

- **TC8: createPlayerConfigs_WithOneMoreThanMaximumPlayers_ThrowsException** ( :white_check_mark: )
    - **State of the system**: player count = 5, five valid player names, five valid icons
    - **Expected output**: throws `IllegalArgumentException`

- **TC9: createPlayerConfigs_WithEmptyPlayerName_ThrowsException** ( :white_check_mark: )
    - **State of the system**: player count = 2, one player name is empty, all icons are valid
    - **Expected output**: throws `IllegalArgumentException`

- **TC10: createPlayerConfigs_WithNullPlayerIcon_ThrowsException** ( :x: )
    - **State of the system**: player count = 2, all player names are valid, one icon is null
    - **Expected output**: throws `NullPointerException`

### Method under test: `startNewGame()`
1. Input: player setup information from the main menu, Output: a new game begins or invalid setup is rejected
2. Input: player config collection size / count, Output: side effect on game state
3. Input: valid player config count range [2, 4], values: 1, 2, 4, 5

- **TC11: startNewGame_WithOneLessThanMinimumPlayers_ThrowsException** ( :x: )
    - **State of the system**: player config count = 1
    - **Expected output**: throws `IllegalArgumentException`

- **TC12: startNewGame_WithMinimumPlayers_StartsGame** ( :x: )
    - **State of the system**: player config count = 2, all player configs are valid
    - **Expected output**: game starts successfully

- **TC13: startNewGame_WithMaximumPlayers_StartsGame** ( :x: )
    - **State of the system**: player config count = 4, all player configs are valid
    - **Expected output**: game starts successfully

- **TC14: startNewGame_WithOneMoreThanMaximumPlayers_ThrowsException** ( :x: )
    - **State of the system**: player config count = 5
    - **Expected output**: throws `IllegalArgumentException`

- **TC15: startNewGame_WithValidSetup_SetsGameStatusToInProgress** ( :x: )
    - **State of the system**: player config count = 2, all player configs are valid
    - **Expected output**: game status becomes `GameStatus.IN_PROGRESS`

- **TC16: startNewGame_WithValidSetup_SetsFirstPlayerAsCurrentPlayer** ( :x: )
    - **State of the system**: player config count = 2, all player configs are valid
    - **Expected output**: current player is the first configured player