# BVA Analysis: `SubscriptionServiceCardEffect`

The `SubscriptionServiceCardEffect` implements the `CardEffect` interface. When applied, it deducts $100 from the current player's balance. If the player cannot afford it and has no properties to sell, the player is marked bankrupt and removed from the game.

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

- **TC4: Player balance strictly greater than $100** ( :x: )
  - **State of the system**: `player.balance = 200.0`, `game` is valid
  - **Expected output**: `player.balance` becomes `100.0`; player remains active

- **TC5: Player balance exactly equals $100 (boundary)** ( :x: )
  - **State of the system**: `player.balance = 100.0`, `game` is valid
  - **Expected output**: `player.balance` becomes `0.0`; `player.isBankrupt()` returns `false` (debt was fully payable)

- **TC6: Player balance one unit below $100, no properties (boundary)** ( :x: )
  - **State of the system**: `player.balance = 99.0`, `player.ownedProperties` is empty, `game` is valid
  - **Expected output**: `player.isBankrupt()` returns `true`; player is marked inactive and removed from the game

- **TC7: Player balance is $0 before payment, no properties** ( :x: )
  - **State of the system**: `player.balance = 0.0`, `player.ownedProperties` is empty, `game` is valid
  - **Expected output**: `player.isBankrupt()` returns `true`; player is marked inactive and removed from the game

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC8: Eliminated player draws Subscription Service** ( :x: )
  - **State of the system**: `player.isActive() == false`, `game` is valid
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC9: Last payment causes game over (only one player remains)** ( :x: )
  - **State of the system**: `game` has 2 active players; current `player.balance = 50.0`, no properties; other player has `balance = 500.0`
  - **Expected output**: current player goes bankrupt and is removed; `game.isGameOver()` returns `false` (one player still remains as winner is declared only when 1 remains)
