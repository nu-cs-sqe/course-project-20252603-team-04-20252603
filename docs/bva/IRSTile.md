# IRSTile BVA Analysis

## Method under test: `getName()`

1. Input: Nothing, Output: the tile type for the IRS tile
2. Output type: `TileType`
3. Output value: `TileType.IRS`

- **TC1: getName returns IRS tile type** ( :white_check_mark: )
  - **State of the system**: IRS tile exists
  - **Expected output**: returns `TileType.IRS`

- **TC2: getName repeated calls are consistent** ( :white_check_mark: )
  - **State of the system**: IRS tile exists, `getName()` is called more than once
  - **Expected output**: every call returns `TileType.IRS`


## Method under test: `landOn(Player player, GameEngine game)`

1. Input: player landing on the IRS tile and the current game, Output: tax payment succeeds or player is eliminated
2. Input type: `Player` object reference and `GameEngine` object reference
3. Boundary values: the result of `player.remove(taxAmount)` and whether `game` is needed for elimination

- **TC3: Land on IRS when tax payment succeeds** ( :x: )
  - **State of the system**: `player.remove(taxAmount)` returns `true`, valid `game` is provided
  - **Expected output**: tax payment is accepted and `game.removeBankruptPlayer(player)` is not called

- **TC4: Land on IRS when tax payment fails** ( :x: )
  - **State of the system**: `player.remove(taxAmount)` returns `false`, valid `game` is provided
  - **Expected output**: `game.removeBankruptPlayer(player)` is called once

- **TC5: Land on IRS with null player** ( :white_check_mark: )
  - **State of the system**: `player = null`, `game` is valid
  - **Expected output**: `NullPointerException` thrown (fail-fast)

- **TC6: Land on IRS with null game when tax payment succeeds** ( :x: )
  - **State of the system**: `player.remove(taxAmount)` returns `true`, `game = null`
  - **Expected output**: method executes normally because no elimination is needed

- **TC7: Land on IRS with null game when tax payment fails** ( :x: )
  - **State of the system**: `player.remove(taxAmount)` returns `false`, `game = null`
  - **Expected output**: `NullPointerException` thrown because elimination requires the game engine
