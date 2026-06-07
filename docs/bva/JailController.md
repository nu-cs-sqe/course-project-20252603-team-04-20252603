# JailController BVA Analysis

## Method under test: `sendToJail(Player player)`

Moves an active player to jail via `GameEngine.setPlayerPosition` and `Player.goToJail(Constants.JAIL_POSITION)`.

- **TC1: Null player** ( )
  - **State of the system**: `player = null`
  - **Expected output**: `NullPointerException` thrown

- **TC2: Inactive player** ( )
  - **State of the system**: `player.getActive() = false`, `player.inJail() = false`
  - **Expected output**: `false`; no change to jail status, position, or `jailTurnCount`

- **TC3: Active player not in jail** ( )
  - **State of the system**: `player.getActive() = true`, `player.inJail() = false`, `player.position ≠ JAIL_POSITION`
  - **Expected output**: `true`; `player.inJail() = true`, `player.position = Constants.JAIL_POSITION`, `player.getJailTurnCount() = 1`; board position updated via `GameEngine`

- **TC4: Active player already in jail** ( )
  - **State of the system**: `player.getActive() = true`, `player.inJail() = true`, `player.getJailTurnCount() = 2`
  - **Expected output**: `true`; `player.inJail()` remains `true`, `player.position = Constants.JAIL_POSITION`, `player.getJailTurnCount()` reset to `1` (per `Player.goToJail()` behavior)

---

## Method under test: `releaseFromJail(Player player)`

Releases a jailed player by delegating to `Player.leaveJail()`.

- **TC5: Null player** ( )
  - **State of the system**: `player = null`
  - **Expected output**: `NullPointerException` thrown

- **TC6: Player is in jail** ( )
  - **State of the system**: `player.inJail() = true`, `player.getJailTurnCount() = 2`, `player.position = Constants.JAIL_POSITION`
  - **Expected output**: `true`; `player.inJail() = false`, `player.getJailTurnCount() = 0`, `player.position` increases by 1 (per `Player.leaveJail()`)

- **TC7: Player not in jail** ( )
  - **State of the system**: `player.inJail() = false`
  - **Expected output**: `false`; no change to jail status, position, or `jailTurnCount`

---

## Method under test: `payJailFee(Player player)`

Deducts the jail fee and releases the player. Corresponds to game-rules Use Case 2 alternate flow step 1b.

- **TC8: Null player** ( )
  - **State of the system**: `player = null`
  - **Expected output**: `NullPointerException` thrown

- **TC9: Player not in jail** ( )
  - **State of the system**: `player.inJail() = false`
  - **Expected output**: `false`; balance unchanged

- **TC10: Player in jail, cannot afford fee** ( )
  - **State of the system**: `player.inJail() = true`, `player.canAfford(Constants.JAIL_FEE) = false`
  - **Expected output**: `false`; `player.inJail()` remains `true`, balance unchanged

- **TC11: Player in jail, balance exactly equals fee** ( )
  - **State of the system**: `player.inJail() = true`, `player.balance = Constants.JAIL_FEE`
  - **Expected output**: `true`; balance becomes `0`, player released (`inJail = false`, `jailTurnCount = 0`)

- **TC12: Player in jail, balance greater than fee** ( )
  - **State of the system**: `player.inJail() = true`, `player.balance > Constants.JAIL_FEE`
  - **Expected output**: `true`; balance reduced by `Constants.JAIL_FEE`, player released

- **TC13: Inactive player in jail** ( )
  - **State of the system**: `player.inJail() = true`, `player.getActive() = false`
  - **Expected output**: `false`; no balance change, player remains in jail

---

## Method under test: `attemptRollDoubles(Player player)`

Rolls dice for a jailed player. Corresponds to game-rules Use Case 2 alternate flow steps 1c / 1d. Uses injected `Dice`.

- **TC14: Null player** ( )
  - **State of the system**: `player = null`
  - **Expected output**: `NullPointerException` thrown

- **TC15: Player not in jail** ( )
  - **State of the system**: `player.inJail() = false`
  - **Expected output**: `false`; dice not rolled (or roll ignored), no jail state change

- **TC16: Player in jail, dice roll is doubles** ( )
  - **State of the system**: `player.inJail() = true`; after `dice.roll()`, `dice.isDoubles() = true`
  - **Expected output**: `true`; player released via `releaseFromJail` (`inJail = false`, `jailTurnCount = 0`)

- **TC17: Player in jail, dice roll is not doubles, turn count below max** ( )
  - **State of the system**: `player.inJail() = true`, `player.getJailTurnCount() = 1`; after `dice.roll()`, `dice.isDoubles() = false`
  - **Expected output**: `false`; player remains in jail; `jailTurnCount` incremented to `2`

- **TC18: Player in jail, dice roll is not doubles, turn count at max** ( )
  - **State of the system**: `player.inJail() = true`, `player.getJailTurnCount() = Constants.MAX_JAIL_TURNS`; after `dice.roll()`, `dice.isDoubles() = false`
  - **Expected output**: `false`; player remains in jail; turn ends (player must pay fee on a future turn per game rules)

- **TC19: Inactive player in jail** ( )
  - **State of the system**: `player.inJail() = true`, `player.getActive() = false`
  - **Expected output**: `false`; no dice roll, no state change

---

## Method under test: `handleJailTurn(Player player)`

Called when a jailed player's turn ends without release (failed doubles and fee not paid). Records the elapsed jail turn so the 3-turn boundary can be enforced. Corresponds to game-rules Use Case 2 alternate flow step 1d.

- **TC20: Null player** ( )
  - **State of the system**: `player = null`
  - **Expected output**: `NullPointerException` thrown

- **TC21: Player not in jail** ( )
  - **State of the system**: `player.inJail() = false`
  - **Expected output**: `false`; no state change

- **TC22: Player in jail, first turn in jail (`jailTurnCount = 1`)** ( )
  - **State of the system**: `player.inJail() = true`, `player.getJailTurnCount() = 1`, player did not pay or roll doubles this turn
  - **Expected output**: completes without exception; `jailTurnCount` incremented to `2`; player remains in jail; turn ends

- **TC23: Player in jail, second turn in jail (`jailTurnCount = 2`)** ( )
  - **State of the system**: `player.inJail() = true`, `player.getJailTurnCount() = 2`, player did not pay or roll doubles this turn
  - **Expected output**: completes without exception; `jailTurnCount` incremented to `3`; player remains in jail; turn ends

- **TC24: Player in jail, third turn in jail (`jailTurnCount = 3`)** ( )
  - **State of the system**: `player.inJail() = true`, `player.getJailTurnCount() = 3`, player did not pay or roll doubles this turn
  - **Expected output**: completes without exception; `jailTurnCount` remains `3`; player remains in jail; player must pay fee before rolling on the next turn (or implementation forces payment—document chosen behavior in controller Javadoc)

- **TC25: Inactive player in jail** ( )
  - **State of the system**: `player.inJail() = true`, `player.getActive() = false`
  - **Expected output**: `false`; no state change
