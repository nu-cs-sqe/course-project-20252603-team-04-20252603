# BVA Analysis: `PickPersonToJailCardEffect`

The `PickPersonToJailCardEffect` implements the `CardEffect` interface. When applied, the current player (the card drawer) selects another active player to send directly to jail. The target is retrieved from the `GameEngine` via a pre-set selection. The card drawer themselves is not affected.

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

- **TC4: No target selected (game returns null for selected player)** ( :x: )
  - **State of the system**: `player` is valid; `game.getSelectedPlayer()` returns `null`
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

---

### Method under test: `apply(Object player, Object game)` — normal operation

- **TC5: Card drawer picks a different active player to go to jail** ( :x: )
  - **State of the system**: `player` (drawer) is active; `target` is a different active player with `inJail() == false` and `position = 10`; `game` is valid
  - **Expected output**: `target.inJail()` becomes `true`; `target.position` becomes `8` (jail tile); drawer's state is unchanged

- **TC6: Target player is already in jail** ( :x: )
  - **State of the system**: `target.inJail() == true`, `target.position = 8`; `game` is valid
  - **Expected output**: `target.inJail()` remains `true`; `target.position` remains `8`; jail turn counter is reset to `1`

- **TC7: Target player is on GO (position 0)** ( :x: )
  - **State of the system**: `target.position = 0`, `target.inJail() == false`; `game` is valid
  - **Expected output**: `target.position` becomes `8`; `target.inJail()` becomes `true`; no GO bonus applied to target

---

### Method under test: `apply(Object player, Object game)` — edge cases

- **TC8: Drawer attempts to pick themselves** ( :x: )
  - **State of the system**: `game.getSelectedPlayer()` returns the same object as `player`
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated (a player cannot send themselves to jail via this card)

- **TC9: Drawer picks an eliminated (inactive) player** ( :x: )
  - **State of the system**: `game.getSelectedPlayer()` returns a player with `isActive() == false`
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC10: Eliminated drawer draws Pick Person to Jail** ( :x: )
  - **State of the system**: `player.isActive() == false`, `game` is valid
  - **Expected output**: `IllegalArgumentException` thrown; no state is mutated

- **TC11: Only one player remains in the game (no valid target)** ( :x: )
  - **State of the system**: `game` has exactly 1 active player (the drawer); no other active player to pick
  - **Expected output**: `IllegalArgumentException` thrown or effect is skipped; no state is mutated
