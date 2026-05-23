# BVA Analysis: `AIBubblePopCardEffect`

The `AIBubblePopCardEffect` implements the `CardEffect` interface. When applied, it deducts $500 from the current player's balance. If the player cannot afford it and has no properties to sell, the player is marked bankrupt and removed from the game.

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

- **TC4: Player balance strictly greater than $500** ( :x: )
  - **State of the system**: `player.balance = 1000.0`, `game` is valid
  - **Expected output**: `player.balance` becomes `500.0`; player remains active

- **TC5: Player balance exactly equals $500 (boundary)** ( :x: )
  - **State of the system**: `player.balance = 500.0`, `game` is valid
  - **Expected output**: `player.balance` becomes `0.0`; `player.isBankrupt()` returns `false` (debt was fully payable)

- **TC6: Player balance one unit below $500, no properties (boundary)** ( :x: )
  - **State of the system**: `player.balance = 499.0`, `player.ownedProperties` is empty, `game` is valid
  - **Expected output**: `player.isBankrupt()` returns `true`; player is marked inactive and removed from the game

- **TC7: Player balance is $0 before payment, no properties** ( :x: )
  - **State of the system**: `player.balance = 0.0`, `player.ownedProperties` is empty, `game` is valid
  - **Expected output**: `player.isBankrupt()` returns `true`; player is marked inactive and removed from the game

- **TC8: Player starts with exactly $1000 (initial balance) loses $500** ( :x: )
  - **State of the system**: `player.balance = 1000.0` (standard starting balance), `game` is valid
  - **Expected output**: `player.balance` becomes `500.0`; player remains active

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC9: Eliminated player draws AI Bubble Pop** ( :x: )
  - **State of the system**: `player.isActive() == false`, `game` is valid
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC10: Payment causes game over (only one player remains)** ( :x: )
  - **State of the system**: `game` has 2 active players; current `player.balance = 200.0`, no properties; other player has `balance = 1000.0`
  - **Expected output**: current player goes bankrupt and is removed; `game.isGameOver()` returns `true` (only one player remains)
