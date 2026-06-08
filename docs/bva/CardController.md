# BVA Analysis: `CardController`

The `CardController` connects the chance `Deck` to the card popup behaviour. It draws a
chance card for a player, applies that card's effect to the game state, and shows the card
to the user via a popup/view.

**Assumed design** (collaborators, all mocked with EasyMock since `CardController` is the SUT):
- `Deck deck` — source of chance cards (`draw()` / `discard(Card)`).
- `GameEngine game` — game state the card effect mutates.
- `Card` — read-only via `getTitle()` / `getDescription()` (the popup renders the returned strings).

**Method signatures under analysis**:
- `Card drawChanceCard(Player player)`
- `void applyCard(Card card, Player player)`
- `Map<String, String> showCard(Card card)` — returns a dictionary with keys `"title"` and `"description"` for the popup

> `showCard` reads the card via `card.getTitle()` and `card.getDescription()` and returns a
> dictionary `{ "title" -> <title>, "description" -> <description> }` so the popup can be
> rendered.

> Effects reach the game through `CardEffect.apply(Player, GameEngine)`. `applyCard` is the
> seam that invokes the drawn card's effect against `game` and the given `player`.

---

## Method under test: `CardController(Deck deck, GameEngine game)`

The constructor must reject null collaborators up front. If a null `deck` or `game` were stored,
the failure would surface later (e.g. `deck.draw()` in `drawChanceCard`, or `effect.apply(player, game)`
in `applyCard`) as an opaque `NullPointerException` far from the real cause. Validating in the
constructor fails fast at the construction site.

### Argument validation

- **TC16: Null deck** ( :white_check_mark: )
    - **State of the system**: `deck = null`; `game` is a valid mock
    - **Expected output**: `NullPointerException` thrown; no `CardController` instance is created

- **TC17: Null game** ( :white_check_mark: )
    - **State of the system**: `deck` is a valid mock; `game = null`
    - **Expected output**: `NullPointerException` thrown; no `CardController` instance is created

- **TC18: Both null** ( :white_check_mark: )
    - **State of the system**: `deck = null`, `game = null`
    - **Expected output**: `NullPointerException` thrown; no `CardController` instance is created

### Normal operation

- **TC19: Both valid** ( :white_check_mark: )
    - **State of the system**: `deck` and `game` are valid mocks
    - **Expected output**: a `CardController` instance is constructed successfully (no exception)

---

## Method under test: `drawChanceCard(Player player)`

### Argument validation

- **TC1: Null player** ( :white_check_mark: )
    - **State of the system**: `player = null`; `deck` is a valid mock
    - **Expected output**: `NullPointerException` thrown; `deck.draw()` is **not** called

- **TC2: Inactive (eliminated) player** ( :white_check_mark: )
    - **State of the system**: `player.getActive() == false`; `deck` valid
    - **Expected output**: `IllegalArgumentException` thrown; `deck.draw()` is **not** called

### Normal operation

- **TC3: Active player draws the top card** ( :white_check_mark: )
    - **State of the system**: `player` active; mock `deck.draw()` returns card `C1`
    - **Expected output**: returns `C1`; `deck.draw()` called exactly once

### Edge cases

- **TC4: Deck is exhausted** ( :white_check_mark: )
    - **State of the system**: `player` active; mock `deck.draw()` throws `IllegalStateException` (both piles empty)
    - **Expected output**: `IllegalStateException` propagates to the caller; no card returned

---

## Method under test: `applyCard(Card card, Player player)`

### Argument validation

- **TC5: Null card** ( :white_check_mark: )
    - **State of the system**: `card = null`, `player` active
    - **Expected output**: `NullPointerException` thrown; no effect is applied; `game` untouched

- **TC6: Null player** ( :white_check_mark: )
    - **State of the system**: `card` valid mock, `player = null`
    - **Expected output**: `NullPointerException` thrown; no effect is applied; `game` untouched

- **TC7: Both null** ( :white_check_mark: )
    - **State of the system**: `card = null`, `player = null`
    - **Expected output**: `NullPointerException` thrown; no effect is applied

- **TC8: Inactive player** ( :white_check_mark: )
    - **State of the system**: `card` valid, `player.getActive() == false`
    - **Expected output**: `IllegalArgumentException` thrown; no effect is applied

### Normal operation

- **TC9: Valid card applied to active player** ( :white_check_mark: )
    - **State of the system**: `card` valid mock holding a mock `CardEffect`; `player` active; `game` valid mock
    - **Expected output**: the card's effect is invoked exactly once with `(player, game)`; method returns normally

- **TC10: Applied card is discarded back to the deck** ( :white_check_mark: )
    - **State of the system**: `card` is the card most recently returned by `deck.draw()`; `player` active
    - **Expected output**: after the effect is applied, `deck.discard(card)` is called exactly once with the same card instance (per Use Case 6 draw → apply → discard cycle)

### Edge cases

- **TC11: Effect throws while being applied** ( :white_check_mark: )
    - **State of the system**: `card`'s mock `CardEffect.apply(player, game)` throws a `RuntimeException`
    - **Expected output**: exception propagates; `deck.discard(card)` is **not** called (card is not consumed on failure)

---

## Method under test: `Map<String, String> showCard(Card card)`

### Argument validation

- **TC12: Null card** ( :white_check_mark: )
    - **State of the system**: `card = null`
    - **Expected output**: `NullPointerException` thrown; nothing returned; `card.getTitle()` / `card.getDescription()` not called

### Normal operation

- **TC13: Valid card returns a dictionary with title and description** ( :white_check_mark: )
    - **State of the system**: `card` mock; `card.getTitle()` returns `"Advance to GO"`, `card.getDescription()` returns `"Advance to GO. Collect $200."`
    - **Expected output**: returns a `Map` with exactly two entries — `"title" -> "Advance to GO"` and `"description" -> "Advance to GO. Collect $200."`; `getTitle()` and `getDescription()` each called exactly once; no game state mutated

### Edge cases

- **TC14: Title and description differ (mapped to the correct keys, not swapped)** ( :white_check_mark: )
    - **State of the system**: `card` mock; `card.getTitle()` returns `"Stock Market Crash"`, `card.getDescription()` returns `"Every player pays $200."`
    - **Expected output**: returned map has `"title" -> "Stock Market Crash"` and `"description" -> "Every player pays $200."` (values land under the correct keys, not swapped or merged)

- **TC15: Special characters in title/description are preserved** ( :white_check_mark: )
    - **State of the system**: `card` mock; `card.getTitle()` returns `"Pay $100!"`, `card.getDescription()` returns `"Pay $100 for a subscription service!"`
    - **Expected output**: returned map's `"title"` and `"description"` values match the source strings exactly (special characters preserved)

---

## Integration note

The full chance-tile flow combines all three methods: `drawChanceCard(player)` →
`showCard(card)` (popup renders the returned title + description) → `applyCard(card, player)`,
ending with `deck.discard(card)`. Each method is unit-tested in isolation above with the deck,
game, card, and effect replaced by EasyMock mocks; the ordered combination is covered at the
integration level.
