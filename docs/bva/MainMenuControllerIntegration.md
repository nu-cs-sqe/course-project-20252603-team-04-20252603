### Method under test: `startNewGame()`
1. Input: player setup information from the main menu, Output: a new game begins or invalid setup is rejected
2. Input: player config collection size / count, Output: side effect on game state
3. Input: valid player config count range [2, 4], values: 1, 2, 4, 5

- **TC1: startNewGame_WithValidSetup_SetsGameStatusToInProgress** ( :white_check_mark: )
    - **State of the system**: player config count = 2, all player configs are valid
    - **Expected output**: game status becomes `GameStatus.IN_PROGRESS`

- **TC2: startNewGame_WithValidSetup_SetsFirstPlayerAsCurrentPlayer** ( :white_check_mark: )
    - **State of the system**: player config count = 2, all player configs are valid
    - **Expected output**: current player is the first configured player