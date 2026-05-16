# BVA: `ChanceTile`

Chancetile is only for landing on chance. The actual deal of the chance card logic happens in the deck class. When a player lands on the chance tile, it should trigger the deck to draw a chance card and apply the effect to the player(s).

### Method under test: `getName()`

- **TC1: ChanceTile reports its tile type** ( :x: )
  - **State of the system**: A **`ChanceTile`** is constructed.
  - **Expected output**: **`getName()`** returns **`TileType.CHANCE`.

### Method under test: `landOn(Player player, GameEngine game)`

- **TC2: Active player lands on ChanceTile** ( :x: )
  - **State of the system**: **`player`** is active; **`game`** is a valid **`GameEngine`** (mocked); the engine exposes exactly one sensible entry point for “draw/play next Chance card” (whatever your design calls it, ultimately leading to **`Deck.draw()`** etc.).
  - **Expected output**: That entry point is invoked **exactly once** during **`landOn`**. **`ChanceTile`** does not implement **`shuffle`** / **`discard`** / **`reshuffleIfEmpty`** (**`Deck`** tests cover those).

- **TC3: Null player input** ( :x: )
  - **State of the system**: **`player = null`**; **`game`** is valid.
  - **Expected output**: **`IllegalArgumentException`**; **`game`**/`Deck` is not invoked for a Chance draw.

- **TC4: Null game input** ( :x: )
  - **State of the system**: **`player`** is a valid active **`Player`**; **`game = null`**.
  - **Expected output**: **`IllegalArgumentException`**; no Chance effect runs.

- **TC5: Both player and game null** ( :x: )
  - **State of the system**: **`player = null`**, **`game = null`**.
  - **Expected output**: Inputs are rejected (e.g. **`IllegalArgumentException`**); no effect.

- **TC6: Inactive player lands on ChanceTile** ( :x: )
  - **State of the system**: **`player`** is inactive (**`isActive() == false`** or **`getActive() == false`**, whichever your **`Player`** API uses); **`game`** is valid and mocked so Chance-draw hooks are observable.
  - **Expected output**: No Chance effect runs—**no** **`draw`** / **`handleChanceLanding`** (or equivalent) on **`game`**/`Deck`; **`landOn`** returns normally (no exception for inactive **`player`** if that matches your **`Tile`** contract).
