## User Story: Start and Play a Custom Monopoly Game

As a player of our custom Monopoly game, I want the game to automatically set up a 9x9 board, initialize 2–4 players, handle turns, property purchases, taxes, chance cards, jail, and player elimination, so that players can play a complete Monopoly-style game without manually managing the rules or board state.

## Acceptance Criteria

### Game Setup

- [ ] The game must allow 2 to 4 players.
- [ ] Each player must enter a name.
- [ ] Each player must select an icon.
- [ ] Each player must start with $1000.
- [ ] Each player must start on the GO tile.
- [ ] The board must be initialized as a 1D array of tiles representing the 9x9 board path.
- [ ] The board must contain property tiles, IRS/tax tiles, chance tiles, jail, free parking, GO, and go-to-jail tiles.
- [ ] All properties must begin with no owner.
- [ ] Chance cards must be initialized and shuffled before the first turn.
- [ ] The first player’s turn must begin after setup is complete.

---

## Use Case 1: Start New Game

### Actor
Player

### Preconditions
- The application is launched.
- No game is currently active.

### Main Flow
1. Player opens the main menu.
2. Player selects “Start Game.”
3. System asks for the number of players.
4. Player enters a number between 2 and 4.
5. System asks each player to enter a name and choose an icon.
6. System creates each player.
7. System gives each player $1000.
8. System places every player on the GO tile.
9. System initializes the board.
10. System initializes all property, IRS, chance, jail, free, GO, and go-to-jail tiles.
11. System sets all properties to unowned.
12. System initializes and shuffles the chance deck.
13. System starts the first player’s turn.

### Alternate Flow: Invalid Player Count
4a. Player enters fewer than 2 or more than 4 players.  
4b. System displays an error message.  
4c. System asks the player to enter the number of players again.

### Postconditions
- The board is ready.
- All players are initialized.
- All players are on GO.
- All players have $1000.
- Chance cards are shuffled.
- The first turn can begin.

---

## Use Case 2: Take a Turn

### Actor
Current Player

### Preconditions
- The game has started.
- It is the current player’s turn.
- The player has not been eliminated.

### Main Flow
1. System displays the current player’s turn.
2. Player clicks the “Roll Dice” button.
3. System rolls two dice.
4. System moves the player forward by the dice total.
5. System updates the player’s position on the board.
6. System checks the tile the player landed on.
7. System triggers the tile’s effect.
8. System updates the GUI to show the new board state, player information, and any card or property popups.
9. System advances to the next active player.

### Alternate Flow: Player Is in Jail
1a. If the player is in jail, the system asks whether the player wants to pay a fee or attempt to roll doubles.  
1b. If the player pays the fee, they leave jail and continue their turn.  
1c. If the player rolls doubles, they leave jail and continue their turn.  
1d. If the player fails to roll doubles and does not pay, their turn ends.

### Postconditions
- The player has moved or handled jail logic.
- The tile effect has been resolved.
- The game state is updated.
- The next player’s turn begins.

---

## Use Case 3: Land on an Unowned Property

### Actor
Current Player

### Preconditions
- The player has landed on a property tile.
- The property has no owner.
- The player has enough money to buy the property.

### Main Flow
1. System displays the property name, price, and rent.
2. System asks the player whether they want to buy the property.
3. Player chooses to buy the property.
4. System subtracts the property price from the player’s balance.
5. System sets the property owner to the player.
6. System adds the property to the player’s property set.
7. System updates the GUI.

### Alternate Flow: Player Declines Purchase
3a. Player chooses not to buy the property.  
3b. System leaves the property unowned.  
3c. Turn continues or ends normally.

### Postconditions
- The property is either owned by the player or remains unowned.
- The player’s balance and property list are updated if they purchased it.

---

## Use Case 4: Land on Another Player’s Property

### Actor
Current Player

### Preconditions
- The player has landed on a property tile.
- The property is owned by another player.

### Main Flow
1. System identifies the owner of the property.
2. System calculates the rent owed.
3. System subtracts rent from the current player’s balance.
4. System adds rent to the property owner’s balance.
5. System updates both players’ balances in the GUI.

### Alternate Flow: Player Cannot Afford Rent
3a. System checks whether the player owns any properties.  
3b. If the player owns properties, the player must sell one or more properties for 80% of the original purchase price.  
3c. The money from the sale is used to help pay rent.  
3d. If the player still cannot pay and has no more properties to sell, the player loses and is removed from the game.

### Postconditions
- Rent is paid if possible.
- Player balances are updated.
- The player is eliminated if they cannot pay and cannot sell property.

---

## Use Case 5: Land on IRS / Tax Tile

### Actor
Current Player

### Preconditions
- The player lands on an IRS/tax tile.

### Main Flow
1. System displays the tax amount owed.
2. System subtracts the tax amount from the player’s balance.
3. System updates the player’s balance in the GUI.

### Alternate Flow: Player Cannot Afford Tax
3a. System checks whether the player can sell properties.  
3b. If possible, the player sells property for 80% of the original purchase price.  
3c. If the player cannot pay and has no properties to sell, the player loses.

### Postconditions
- The player pays the tax or is eliminated.

---

## Use Case 6: Land on Chance Tile

### Actor
Current Player

### Preconditions
- The player lands on a chance tile.
- The chance deck has been initialized.

### Main Flow
1. System draws a chance card from the unused chance card deck.
2. System displays the card description in the ChanceCard GUI.
3. Player clicks “Proceed.”
4. System applies the card effect.
5. System moves the used card to the used card deck.
6. System updates the board and player information.

### Example Chance Card Effects
- Advance to GO.
- Go to jail.
- Pay $100 for a subscription service.
- Go back three spaces.
- Stock market crashes: everyone loses $200.
- Choose another player to go to jail.
- AI bubble pops: lose $500.

### Alternate Flow: Chance Deck Is Empty
1a. System reshuffles the used chance card deck.  
1b. System moves reshuffled cards back into the unused deck.  
1c. System draws a card.

### Postconditions
- The chance card effect is applied.
- The game state is updated.
- The used card is tracked.

---

## Use Case 7: Go to Jail

### Actor
Current Player

### Preconditions
- The player lands on a go-to-jail tile or draws a chance card that sends them to jail.

### Main Flow
1. System moves the player to the jail tile.
2. System sets the player’s `inJail` status to `true`.
3. System updates the GUI.
4. Player’s turn ends.

### Postconditions
- Player is in jail.
- Player must roll doubles or pay a fee to leave jail on a future turn.

---

## Use Case 8: Pass or Land on GO

### Actor
Current Player

### Preconditions
- The player moves around the board.
- The player either passes GO or lands on GO.

### Main Flow
1. System detects that the player passed or landed on GO.
2. System adds $200 to the player’s balance.
3. System updates the player’s balance in the GUI.

### Postconditions
- Player receives $200.
- Player continues or ends their turn normally.

---

## Use Case 9: Player Elimination and Game End

### Actor
System

### Preconditions
- A player cannot pay a required amount.
- The player has no available properties to sell.

### Main Flow
1. System detects that the player cannot pay.
2. System checks the player’s property set.
3. If the player has no properties left to sell, the system eliminates the player.
4. System removes the player from the active turn order.
5. System checks how many players remain.
6. If more than one player remains, the game continues.
7. If only one player remains, the system declares that player the winner.

### Postconditions
- Eliminated players no longer take turns.
- The game ends when one player remains.
- The winner is displayed.

---

## Implementation Tasks

### Model / Logic
- [ ] Create `Player` class with name, balance, icon, properties, position, and `inJail`.
- [ ] Create `Dice` class with random two-dice rolling.
- [ ] Create abstract `Tile` class or `Tile` interface.
- [ ] Create `PropertyTile`.
- [ ] Create `ChanceTile`.
- [ ] Create `TaxTile` / `IRSTile`.
- [ ] Create `JailTile`.
- [ ] Create `GoTile`.
- [ ] Create `FreeTile`.
- [ ] Create `GoToJailTile`.
- [ ] Create `Property` class with name, price, rent, and owner.
- [ ] Create `ChanceCard` class.
- [ ] Create `Board` class with a 1D array of tiles.
- [ ] Create `GameEngine` or `GameController` class.
- [ ] Add player movement logic.
- [ ] Add property purchase logic.
- [ ] Add rent payment logic.
- [ ] Add tax payment logic.
- [ ] Add chance card effect logic.
- [ ] Add jail logic.
- [ ] Add player elimination logic.
- [ ] Add game-over detection.

### GUI
- [ ] Create `MainMenuView`.
- [ ] Create `BoardView`.
- [ ] Create `PlayerInfoView`.
- [ ] Create `DiceView`.
- [ ] Create `CardView`.
- [ ] Create property purchase popup/modal.
- [ ] Create chance card popup/modal.
- [ ] Update GUI after player movement.
- [ ] Update GUI after balance changes.
- [ ] Update GUI after property ownership changes.