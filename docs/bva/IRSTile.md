# IRSTile BVA Analysis

## Method under test: `getName()`

1. Input: Nothing, Output: the tile type for the IRS tile
2. Output type: `TileType`
3. Output value: `TileType.IRS`

- **TC1: getName returns IRS tile type** ( :white_check_mark: )
  - **State of the system**: IRS tile exists
  - **Expected output**: returns `TileType.IRS`

- **TC2: getName repeated calls are consistent** ( :x: )
  - **State of the system**: IRS tile exists, `getName()` is called more than once
  - **Expected output**: every call returns `TileType.IRS`


## Method under test: `landOn(Player player, GameEngine game)`

1. Input: player landing on the IRS tile and the current game, Output: side effect on player balance
2. Input type: `Player` object reference and `GameEngine` object reference
3. Boundary values: player balance compared to the IRS tax amount

- **TC3: Land on IRS with balance greater than tax amount** ( :x: )
  - **State of the system**: `player.balance > taxAmount`, player lands on IRS tile
  - **Expected output**: player's balance decreases by exactly `taxAmount`

- **TC4: Land on IRS with balance equal to tax amount** ( :x: )
  - **State of the system**: `player.balance == taxAmount`, player lands on IRS tile
  - **Expected output**: player's balance becomes `0.0`

- **TC5: Land on IRS with balance slightly less than tax amount** ( :x: )
  - **State of the system**: `player.balance < taxAmount`, player lands on IRS tile
  - **Expected output**: player cannot pay the tax and is eliminated from the game

- **TC6: Land on IRS with zero balance** ( :x: )
  - **State of the system**: `player.balance = 0.0`, player lands on IRS tile
  - **Expected output**: player cannot pay the tax and is eliminated from the game

- **TC7: Land on IRS with negative balance** ( :x: )
  - **State of the system**: `player.balance < 0.0`, player lands on IRS tile
  - **Expected output**: player is already below zero and is eliminated from the game

- **TC8: Land on IRS with null player** ( :x: )
  - **State of the system**: `player = null`, `game` is valid
  - **Expected output**: `NullPointerException` thrown (fail-fast)

- **TC9: Land on IRS with null game** ( :x: )
  - **State of the system**: `player` is valid, `game = null`
  - **Expected output**: method executes normally if `game` is not used by IRS tile logic

- **TC10: Land on IRS does not transfer money to another player** ( :x: )
  - **State of the system**: multiple players exist, one player lands on IRS tile with enough balance
  - **Expected output**: landing player's balance decreases by `taxAmount`; other players' balances do not change
