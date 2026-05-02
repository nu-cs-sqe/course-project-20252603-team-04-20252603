# BVA Analysis: `Card`

A `Card` represents a chance card drawn from the deck. It holds a description and an effect applied to the game state when the card is executed.

Card Class:
- `Card(String title, String description, CardEffect effect)`
- `String getDescription()`
- `void apply(Player player, Game game)`

---

### Method under test: `Card(String title, String description, CardEffect effect)`

- **TC1: Valid title, description, and effect** ( :x: )
    - **State of the system**: `title = "Go to Jail"`, `description = "Go directly to jail."`, `effect` is a valid `CardEffect` lambda
    - **Expected output**: Card is created successfully; `getDescription()` returns `"Go directly to jail."`

- **TC2: Null title** ( :x: )
    - **State of the system**: `title = null`, `description = "Go directly to jail."`, valid `effect`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC3: Empty title** ( :x: )
    - **State of the system**: `title = ""`, `description = "Go directly to jail."`, valid `effect`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC4: Null description** ( :x: )
    - **State of the system**: `title = "Go to Jail"`, `description = null`, valid `effect`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC5: Empty description** ( :x: )
    - **State of the system**: `title = "Go to Jail"`, `description = ""`, valid `effect`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC6: Null effect** ( :x: )
    - **State of the system**: `title = "Go to Jail"`, `description = "Go directly to jail."`, `effect = null`
    - **Expected output**: `IllegalArgumentException` thrown

---

### Method under test: `getDescription()`

- **TC7: Normal description** ( :x: )
    - **State of the system**: Card constructed with `description = "AI bubble pops: lose $500"`
    - **Expected output**: Returns `"AI bubble pops: lose $500"` (exact match)

- **TC8: Description with special characters** ( :x: )
    - **State of the system**: Card constructed with `description = "Pay $100 for a subscription service!"`
    - **Expected output**: Returns `"Pay $100 for a subscription service!"` unchanged

---

### Method under test: `apply(Player player, Game game)` — argument validation

- **TC9: Null player** ( :x: )
    - **State of the system**: `player = null`, valid `game`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC10: Null game** ( :x: )
    - **State of the system**: valid `player`, `game = null`
    - **Expected output**: `IllegalArgumentException` thrown

---

### Method under test: `apply(Player player, Game game)` — advance-to-GO effect

- **TC11: Player not on GO advances to GO** ( :x: )
    - **State of the system**: effect advances player to GO; `player.position = 5`; GO is at position 0
    - **Expected output**: `player.position` becomes `0`; `player.balance` increases by `200.0` (GO bonus collected)

---

### Method under test: `apply(Player player, Game game)` — go-to-jail effect

// In the test file, jail position is defined as a constant, with value JAIL_POSITION = 8
- **TC13: Player not in jail is sent to jail** ( :x: )
    - **State of the system**: effect calls `player.goToJail(jailPosition)`; `player.inJail = false`
    - **Expected output**: `player.inJail` becomes `true`; `player.position` is set to the jail tile position; no GO bonus collected

---

### Method under test: `apply(Player player, Game game)` — single-player pay effect

- **TC15: Player balance strictly greater than payment amount** ( :x: )
    - **State of the system**: effect calls `player.debit(100.0)`; `player.balance = 200.0`
    - **Expected output**: `player.balance` becomes `100.0`; player remains active

- **TC16: Player balance equals exact payment amount** ( :x: )
    - **State of the system**: effect calls `player.debit(100.0)`; `player.balance = 100.0`
    - **Expected output**: `player.balance` becomes `0.0`; `player.isBankrupt()` returns `false` (debt was payable)

// TODO: Not sure what happens if player balance is 0 but player has properties. 
// Does player select what properties to sale, or we sale for the player?

- **TC17: Player balance one unit below payment amount, no properties** ( :x: )
    - **State of the system**: effect calls `player.debit(100.0)`; `player.balance = 99.0`; `player.properties` is empty
    - **Expected output**: `player.isBankrupt()` returns `true`; player is marked inactive

- **TC18: Player balance is 0.0 before payment, no properties** ( :x: )
    - **State of the system**: effect calls `player.debit(100.0)`; `player.balance = 0.0`; `player.properties` is empty
    - **Expected output**: `player.isBankrupt()` returns `true`; player is marked inactive

---

### Method under test: `apply(Player player, Game game)` — move-back effect (go back 3 spaces)

- **TC19: Player moves back without underflowing board** ( :x: )
    - **State of the system**: effect moves player back 3; `player.position = 5`; board has 32 tiles
    - **Expected output**: `player.position` becomes `2`; no GO bonus

- **TC20: Player at position 3 moves back 3 (boundary: lands exactly on GO)** ( :x: )
    - **State of the system**: effect moves player back 3; `player.position = 3`; board has 32 tiles
    - **Expected output**: `player.position` becomes `0`; no GO bonus (backward movement does not collect GO bonus)

- **TC21: Player at position 2 moves back 3 (boundary: wraps around board)** ( :x: )
    - **State of the system**: effect moves player back 3; `player.position = 2`; board has 32 tiles
    - **Expected output**: `player.position` becomes `31`; no GO bonus

- **TC22: Player at position 0 moves back 3 (boundary: starts on GO and wraps)** ( :x: )
    - **State of the system**: effect moves player back 3; `player.position = 0`; board has 32 tiles
    - **Expected output**: `player.position` becomes `29`; no GO bonus

---

### Method under test: `apply(Player player, Game game)` — all-players pay effect (e.g., stock market crash)

- **TC23: All players can afford the payment** ( :x: )
    - **State of the system**: effect debits all players `200.0`; all 4 players have `balance >= 200.0`
    - **Expected output**: each player's `balance` decreases by `200.0`; no player is marked inactive

- **TC24: One player cannot afford the payment and has no properties** ( :x: )
    - **State of the system**: effect debits all players `200.0`; 3 players have `balance >= 200.0`; 1 player has `balance = 100.0` and empty `properties`
    - **Expected output**: solvent players each lose `200.0`; the insolvent player's `isBankrupt()` returns `true` and they are marked inactive

- **TC25: All players have exactly the payment amount (boundary)** ( :x: )
    - **State of the system**: effect debits all players `200.0`; all players have `balance = 200.0`
    - **Expected output**: every player's `balance` becomes `0.0`; no player is marked inactive

- **TC26: Only one active player remains in the game** ( :x: )
    - **State of the system**: effect debits all players `200.0`; `game` has exactly 1 active player with `balance = 200.0`
    - **Expected output**: that player's `balance` becomes `0.0`; game-over condition is triggered
