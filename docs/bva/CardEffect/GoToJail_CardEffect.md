# BVA Analysis: `GoToJailCardEffect`

The `GoToJailCardEffect` implements the `CardEffect` interface. When applied, it sends the current player directly to jail: sets `inJail = true`, places the player on the jail tile (position `8`), and resets the jail turn counter.

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

- **TC4: Active player not in jail is sent to jail** ( :x: )
  - **State of the system**: `player.inJail() == false`, `player.position = 5`, `game` is valid
  - **Expected output**: `player.inJail()` becomes `true`; `player.position` becomes `8` (jail tile); no $200 GO bonus collected

- **TC5: Player already in jail draws Go to Jail** ( :x: )
  - **State of the system**: `player.inJail() == true`, `player.position = 8`, `game` is valid
  - **Expected output**: `player.inJail()` remains `true`; `player.position` remains `8`; jail turn counter is reset to `1`

- **TC6: Player on GO (position 0) is sent to jail** ( :x: )
  - **State of the system**: `player.position = 0`, `player.inJail() == false`, `game` is valid
  - **Expected output**: `player.position` becomes `8`; `player.inJail()` becomes `true`; no GO bonus collected despite passing position `0`

- **TC7: Player at position 31 (boundary: last tile) is sent to jail** ( :x: )
  - **State of the system**: `player.position = 31`, `player.inJail() == false`, `game` is valid
  - **Expected output**: `player.position` becomes `8`; `player.inJail()` becomes `true`

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC8: Eliminated player draws Go to Jail** ( :x: )
  - **State of the system**: `player.isActive() == false`, `game` is valid
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC9: Player balance is not affected by going to jail** ( :x: )
  - **State of the system**: `player.balance = 300.0`, `player.position = 10`, `game` is valid
  - **Expected output**: `player.balance` remains `300.0`; only position and jail status change
