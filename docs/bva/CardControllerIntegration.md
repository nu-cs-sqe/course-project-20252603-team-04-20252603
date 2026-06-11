### Method under test: `drawChanceCard(Player player)`
1. Input: active player and standard chance deck, Output: drawn card is returned from the deck
2. Input: player active state, Output: controller either draws a card or rejects the player
3. Input: standard deck size boundary, values: 6 cards before draw, 5 cards after draw

- **TC1: drawChanceCard_WithActivePlayerAndStandardDeck_ReturnsCardFromDeck** ( :white_check_mark: )
    - **State of the system**: active player is on a valid board; standard chance deck contains all implemented cards
    - **Expected output**: a non-null card is returned; unused deck size decreases by one

### Method under test: `applyCard(Card card, Player player)`
1. Input: drawn card and active player, Output: card effect is applied and card is discarded
2. Input: standard card effects, Output: game/player state remains valid after applying the drawn card
3. Input: deck discard boundary, values: 0 used cards before apply, 1 used card after apply

- **TC2: applyCard_WithDrawnStandardCard_AppliesEffectAndDiscardsCard** ( :white_check_mark: )
    - **State of the system**: active player is on a valid board; card was drawn from the same standard deck
    - **Expected output**: drawn card is added to the used pile; player remains active; game still has active players
