# BVA Analysis: `StockMarketCrashCardEffect`

The `StockMarketCrashCardEffect` implements the `CardEffect` interface. When applied, every active player in the game loses $200. Players who cannot afford the payment and have no properties to sell are marked bankrupt and removed from the game.

**Method signature**: `void apply(Object player, Object game)`

---

### Method under test: `apply(Object player, Object game)` — input validation

- **TC1: Null player** ( :x: )
  - **State of the system**: `player = null`, `game` is a valid in-progress `GameEngine`
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC2: Null game** ( :x: )
  - **State of the system**: `player` is a valid active `Player`, `game = null`
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC3: Both null** ( :x: )
  - **State of the system**: `player = null`, `game = null`
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

---

### Method under test: `apply(Object player, Object game)` — normal operation

- **TC4: All players can afford the $200 payment** ( :x: )
  - **State of the system**: 4 active players, all with `balance = 500.0`, `game` is valid
  - **Expected output**: every player's `balance` decreases by `200.0` to `300.0`; no player is marked inactive

- **TC5: All players have exactly $200 (boundary)** ( :x: )
  - **State of the system**: 4 active players, all with `balance = 200.0`, `game` is valid
  - **Expected output**: every player's `balance` becomes `0.0`; `isBankrupt()` returns `false` for all (debt was fully payable)

- **TC6: One player has $199 and no properties (boundary: just below payment)** ( :x: )
  - **State of the system**: 3 players with `balance = 500.0`; 1 player with `balance = 199.0` and empty properties; `game` is valid
  - **Expected output**: the 3 solvent players each lose `200.0`; the insolvent player's `isBankrupt()` returns `true` and they are removed from the game

- **TC7: One player has $0 before payment, no properties** ( :x: )
  - **State of the system**: 3 players with `balance = 500.0`; 1 player with `balance = 0.0` and empty properties; `game` is valid
  - **Expected output**: the 3 solvent players each lose `200.0`; the insolvent player is marked bankrupt and removed

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC8: Only one active player remains and they can afford payment** ( :x: )
  - **State of the system**: `game` has exactly 1 active player with `balance = 500.0`
  - **Expected output**: that player's `balance` becomes `300.0`; game continues (single-player state is not itself a crash trigger)

- **TC9: Crash eliminates all but one player, triggering game over** ( :x: )
  - **State of the system**: `game` has 2 active players; player A has `balance = 500.0`; player B has `balance = 100.0` and no properties
  - **Expected output**: player A loses `200.0` (balance → `300.0`); player B goes bankrupt and is removed; `game.isGameOver()` returns `true`

- **TC10: Crash eliminates all players simultaneously** ( :x: )
  - **State of the system**: all active players have `balance < 200.0` and no properties
  - **Expected output**: all players are marked bankrupt; game-over condition is triggered
