# BVA Analysis: `GoBackThreeSpacesCardEffect`

The `GoBackThreeSpacesCardEffect` implements the `CardEffect` interface. When applied, it moves the current player back 3 positions on the board (wrapping around if needed). Passing or landing on GO by moving backward does **not** grant the $200 bonus.

**Method signature**: `void apply(Object player, Object game)`

Board size is `32` tiles (positions `0`–`31`).

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

- **TC4: Player moves back without underflowing** ( :x: )
  - **State of the system**: `player.position = 5`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `2`; `player.balance` unchanged at `500.0`

- **TC5: Player at position 3 moves back 3 (boundary: lands exactly on GO)** ( :x: )
  - **State of the system**: `player.position = 3`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `0`; `player.balance` unchanged at `500.0` (no GO bonus for backward movement)

- **TC6: Player at position 2 moves back 3 (boundary: wraps around board)** ( :x: )
  - **State of the system**: `player.position = 2`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `31`; `player.balance` unchanged at `500.0` (no GO bonus)

- **TC7: Player at position 1 moves back 3 (boundary: wraps one past GO)** ( :x: )
  - **State of the system**: `player.position = 1`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `30`; `player.balance` unchanged at `500.0` (no GO bonus)

- **TC8: Player at position 0 (GO) moves back 3 (boundary: starts on GO and wraps)** ( :x: )
  - **State of the system**: `player.position = 0`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `29`; `player.balance` unchanged at `500.0` (no GO bonus)

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC9: Eliminated player draws Go Back Three Spaces** ( :x: )
  - **State of the system**: `player.isActive() == false`, `game` is valid
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated
