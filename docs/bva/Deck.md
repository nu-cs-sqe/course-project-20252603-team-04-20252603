# BVA: `Deck`

Represents the shuffled chance deck. Manages drawing from `unusedCards`, discarding to `usedCards`, and reshuffling when the unused pile is empty.

---

## Method under test: `shuffle()`

- **TC1: Shuffle empty unused pile** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = []`
  - **Expected output**: `unusedCards` remains empty; no exception

- **TC2: Shuffle single card in unused pile** ( :x: )
  - **State of the system**: `unusedCards = [C1]`, `usedCards = []`
  - **Expected output**: `unusedCards` still contains exactly one card (`C1`); `usedCards` unchanged

- **TC3: Shuffle multiple cards in unused pile** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2, C3]`, `usedCards = []`
  - **Expected output**: `unusedCards` size is 3 and contains the same three cards (order may change); `usedCards` unchanged

- **TC4: Shuffle does not modify used pile** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = [C3]`
  - **Expected output**: `usedCards` still `[C3]`; only `unusedCards` may be reordered

---

## Method under test: `draw()`

- **TC5: Draw when unused pile has multiple cards** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2, C3]`, `usedCards = []`
  - **Expected output**: Returns `C1` (top/front of deque); `unusedCards = [C2, C3]`; `usedCards` unchanged

- **TC6: Draw last card from unused pile** ( :x: )
  - **State of the system**: `unusedCards = [C1]`, `usedCards = []`
  - **Expected output**: Returns `C1`; `unusedCards = []`; `usedCards` unchanged

- **TC7: Draw when unused empty triggers reshuffle from used** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3]`
  - **Expected output**: `reshuffleIfEmpty()` runs; returns one card from reshuffled unused; `usedCards = []`; total card count still 3

- **TC8: Draw when both unused and used are empty** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = []`
  - **Expected output**: Throws `IllegalStateException` (or equivalent); no card returned

- **TC9: Consecutive draws exhaust unused then reshuffle** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = []`; draw twice, discard both, draw again with empty unused
  - **Expected output**: Third draw succeeds after reshuffle; all cards accounted for across `unusedCards` and `usedCards`

---

## Method under test: `discard(Card card)`

- **TC10: Discard null card** ( :x: )
  - **State of the system**: `card = null`, `usedCards = []`
  - **Expected output**: Throws `IllegalArgumentException`; `usedCards` unchanged

- **TC11: Discard valid card after draw** ( :x: )
  - **State of the system**: Card `C1` was just drawn; `usedCards = []`
  - **Expected output**: `C1` appended to `usedCards`; `C1` not present in `unusedCards`

- **TC12: Discard does not change unused pile size when card already removed** ( :x: )
  - **State of the system**: `unusedCards = [C2, C3]`, drawn card `C1` not in unused
  - **Expected output**: `unusedCards` still `[C2, C3]`; `usedCards` contains `C1`

- **TC13: Discard same card twice** ( :x: )
  - **State of the system**: `C1` already in `usedCards`
  - **Expected output**: Second discard rejected (e.g. `IllegalArgumentException`) or ignored per team policy; `usedCards` has at most one copy of `C1`

- **TC14: Discard card not currently in either pile** ( :x: )
  - **State of the system**: `unusedCards = [C2]`, `usedCards = [C1]`; discard `C3` that was never drawn from this deck
  - **Expected output**: Rejected (e.g. `IllegalArgumentException`) OR accepted into `usedCards` only — document chosen behavior in implementation

---

## Method under test: `reshuffleIfEmpty()`

- **TC15: Unused pile not empty is a no-op** ( :x: )
  - **State of the system**: `unusedCards = [C1, C2]`, `usedCards = [C3]`
  - **Expected output**: `unusedCards` and `usedCards` unchanged

- **TC16: Unused empty and used has one card** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1]`
  - **Expected output**: `unusedCards` contains `C1` (shuffled); `usedCards = []`

- **TC17: Unused empty and used has multiple cards** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3]`
  - **Expected output**: All three cards moved to `unusedCards` and shuffled; `usedCards = []`; total count remains 3

- **TC18: Both unused and used empty** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = []`
  - **Expected output**: No-op; both piles remain empty

- **TC19: Reshuffle preserves total card count** ( :x: )
  - **State of the system**: `unusedCards = []`, `usedCards = [C1, C2, C3, C4]`
  - **Expected output**: `unusedCards.size() + usedCards.size() == 4` after reshuffle; `usedCards` empty

---

## Integration notes (Chance tile flow)

Per **Use Case 6** (`game-rules.md`): landing on a chance tile calls `draw()`, applies the card, then `discard(card)`. If `unusedCards` is empty before draw, `reshuffleIfEmpty()` (or equivalent logic inside `draw()`) moves and shuffles `usedCards` first.

- **TC20: Full chance-tile cycle** ( :x: )
  - **State of the system**: Deck initialized and shuffled; `unusedCards` has at least one card
  - **Expected output**: After `draw()` → apply effect → `discard(drawnCard)`, drawn card is only in `usedCards` until reshuffle
