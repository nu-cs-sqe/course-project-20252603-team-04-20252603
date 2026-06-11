# BVA: `ChanceDeckFactory`

Factory that assembles the standard, shuffled chance deck used to start a game. Lives in the `model`
package so it can use the package-private `Deck` constructor that accepts an initial card collection.

## Assumptions

- The standard deck contains exactly one card for each of the six implemented `CardEffect`s.
- The deck is shuffled before it is returned.

---

## Method under test: `standardDeck()`

1. Input: none, Output: a populated, shuffled `Deck`
2. Input type: none (factory)
3. Output boundary values: deck card count (the six chance cards)

- **TC1: standardDeck_ContainsTheSixChanceCards** ( :white_check_mark: )
  - **State of the system**: factory invoked with no arguments
  - **Expected output**: returns a non-null `Deck` whose unused pile holds exactly six cards, each
    with a non-empty title

- **TC2: standardDeck_WithDeterministicRandom_ShufflesCardsBeforeReturning** ( :white_check_mark: )
  - **State of the system**: package-private `standardDeck(Random)` invoked with a mocked `Random`
  - **Expected output**: returns a shuffled `Deck` with six cards in the order dictated by the mock

---

## Private constructor: `ChanceDeckFactory()`

Utility class; constructor exists only to block direct instantiation.

- **TC3: privateConstructor_CannotBeUsedOutsideReflection** ( :white_check_mark: )
  - **State of the system**: `ChanceDeckFactory.class.getDeclaredConstructor()` accessed via reflection
  - **Expected output**: constructor is private; reflective invocation succeeds and returns a non-null instance
