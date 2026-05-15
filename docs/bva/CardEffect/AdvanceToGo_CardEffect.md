# BVA Analysis: `AdvanceToGoCardEffect`

The `AdvanceToGoCardEffect` implements the `CardEffect` interface. When applied, it moves the player directly to GO (position `0`) and grants the player the $200 GO bonus.

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

- **TC4: Player not on GO advances to GO and collects bonus** ( :x: )
  - **State of the system**: `player.position = 10`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `0`; `player.balance` becomes `700.0` ($200 GO bonus collected)

- **TC5: Player at position 1 (boundary: just past GO) advances to GO** ( :x: )
  - **State of the system**: `player.position = 1`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `0`; `player.balance` becomes `700.0`

- **TC6: Player at position 31 (boundary: last tile before GO) advances to GO** ( :x: )
  - **State of the system**: `player.position = 31`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` becomes `0`; `player.balance` becomes `700.0`

- **TC7: Player already on GO draws Advance to GO** ( :x: )
  - **State of the system**: `player.position = 0`, `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.position` remains `0`; `player.balance` becomes `700.0` (bonus still collected)

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC8: Eliminated player is given Advance to GO** ( :x: )
  - **State of the system**: `player.isActive() == false`, `game` is valid
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC9: Player balance near Double.MAX_VALUE** ( :x: )
  - **State of the system**: `player.position = 5`, `player.balance = Double.MAX_VALUE - 100`, `game` is valid
  - **Expected output**: Bonus is safely handled without overflow to `Infinity`; `player.position` becomes `0`
