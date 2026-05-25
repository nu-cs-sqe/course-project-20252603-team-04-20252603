# BVA: `Deck`

Represents the shuffled chance deck. Manages drawing from `unusedCards`, discarding to `usedCards`, and reshuffling when the unused pile is empty.

---

## Method under test: `shuffle()`

- **TC1: Shuffle empty unused pile** ( :x: )
  - **State of the system**: `Deck` with mock `Random`; `unusedCards = []`, `usedCards = []`
  - **Expected output**: No exception; both piles empty; mock receives no `nextInt` calls

- **TC2: Shuffle single card in unused pile** ( :x: )
  - **State of the system**: `unusedCards = [C1]`, `usedCards = []`
  - **Expected output**: Deque `[C1]`; `usedCards` unchanged; mock receives no `nextInt` calls

- **TC3: Shuffle two cards reverses deque order** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = []`; mock `Random`: `nextInt(2)` returns `0`
  - **Expected output**: Deque `[C2, C1]`; `usedCards` unchanged; `nextInt(2)` called once

- **TC4: Shuffle two cards with identity swap** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`; mock `Random`: `nextInt(2)` returns `1`
  - **Expected output**: Deque `[C1, C2]`; `nextInt(2)` called once

- **TC5: Shuffle three cards to deterministic order** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2, C3]`, `usedCards = []`; mock `Random`: `nextInt(3)` returns `0`, `nextInt(2)` returns `0`
  - **Expected output**: Deque `[C2, C3, C1]`; same three cards; `usedCards` empty

- **TC6: Shuffle does not modify used pile** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = [C3]`; mock `Random`: `nextInt(2)` returns `0`
  - **Expected output**: Deque `[C2, C1]`; `usedCards` still `[C3]`

- **TC7: Shuffle four cards** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2, C3, C4]`, `usedCards = []`; mock `Random`: `nextInt(4)` → `0`, `nextInt(3)` → `0`, `nextInt(2)` → `0`
  - **Expected output**: Deque `[C2, C3, C4, C1]`; each card exactly once; `usedCards` empty

- **TC8: reshuffleIfEmpty shuffles moved cards** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3]`; mock `Random` as in TC5; call `reshuffleIfEmpty()`
  - **Expected output**: `usedCards = []`; deque `[C2, C3, C1]`

---

## Method under test: `draw()`

- **TC9: Draw when unused pile has multiple cards** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2, C3]`, `usedCards = []`
  - **Expected output**: Returns `C1` (top/front of deque); `unusedCards = [C2, C3]`; `usedCards` unchanged

- **TC10: Draw last card from unused pile** ( :x: )
  - **State of the system**: `unusedCards = [C1]`, `usedCards = []`
  - **Expected output**: Returns `C1`; `unusedCards = []`; `usedCards` unchanged

- **TC11: Draw when unused empty triggers reshuffle from used** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3]`; `Deck` with mock `Random` stubbed for reshuffle shuffle (see TC8)
  - **Expected output**: `reshuffleIfEmpty()` runs and shuffles; returns front card of shuffled deque; `usedCards = []`; total card count still 3

- **TC12: Draw when both unused and used are empty** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = []`
  - **Expected output**: Throws `IllegalStateException` (or equivalent); no card returned

- **TC13: Consecutive draws exhaust unused then reshuffle** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = []`; draw twice, discard both, draw again with empty unused; mock `Random` for reshuffle shuffle
  - **Expected output**: Third draw succeeds after reshuffle; all cards accounted for across `unusedCards` and `usedCards`

---

## Method under test: `discard(Card card)`

- **TC14: Discard null card** ( :x: )
  - **State of the system**: `card = null`, `usedCards = []`
  - **Expected output**: Throws `IllegalArgumentException`; `usedCards` unchanged

- **TC15: Discard valid card after draw** ( :x: )
  - **State of the system**: Card `C1` was just drawn; `usedCards = []`
  - **Expected output**: `C1` appended to `usedCards`; `C1` not present in `unusedCards`

- **TC16: Discard does not change unused pile size when card already removed** ( :x: )
  - **State of the system**: `unusedCards = [C2, C3]`, drawn card `C1` not in unused
  - **Expected output**: `unusedCards` still `[C2, C3]`; `usedCards` contains `C1`

- **TC17: Discard same card twice** ( :x: )
  - **State of the system**: `C1` already in `usedCards`
  - **Expected output**: Second discard rejected (e.g. `IllegalArgumentException`); `usedCards` has at most one copy of `C1`

- **TC18: Discard card not currently in either pile** ( :x: )
  - **State of the system**: `unusedCards = [C2]`, `usedCards = [C1]`; discard `C3` that was never drawn from this deck
  - **Expected output**: Rejected (e.g. `IllegalArgumentException`); `usedCards` unchanged

---

## Method under test: `reshuffleIfEmpty()`

- **TC19: Unused pile not empty is a no-op** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = [C3]`
  - **Expected output**: `unusedCards` and `usedCards` unchanged; mock `Random` not called

- **TC20: Unused empty and used has one card** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1]`
  - **Expected output**: `unusedCards` deque `[C1]`; `usedCards = []`; no `nextInt` on mock (single-card shuffle)

- **TC21: Unused empty and used has multiple cards** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3]`; mock `Random` for shuffle of size 3 (see TC5)
  - **Expected output**: Deque `[C2, C3, C1]`; `usedCards = []`; total count remains 3

- **TC22: Both unused and used empty** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = []`
  - **Expected output**: No-op; both piles remain empty; mock `Random` not called

- **TC23: Reshuffle preserves total card count** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3, C4]`; mock `Random` for shuffle of size 4 (see TC7)
  - **Expected output**: `unusedCards.size() + usedCards.size() == 4` after reshuffle; `usedCards` empty

---

## Integration notes (Chance tile flow)

Per **Use Case 6** (`game-rules.md`): landing on a chance tile calls `draw()`, applies the card, then `discard(card)`. If `unusedCards` is empty before draw, `reshuffleIfEmpty()` (or equivalent logic inside `draw()`) moves and shuffles `usedCards` first.

- **TC24: Full chance-tile cycle** ( :x: )
  - **State of the system**: `Deck` with mock `Random`; `unusedCards` pre-shuffled to known deque order; at least one card in unused
  - **Expected output**: After `draw()` → apply effect → `discard(drawnCard)`, drawn card is only in `usedCards` until reshuffle
