# Property BVA Analysis

## Constructor: `Property(string name, double price, double rent)`

### Price Parameter Boundaries
- **TC1: Valid price** ( :x: )
  - **State of the system**: price = 100.0, rent = 50.0
  - **Expected output**: Property created successfully

- **TC2: Zero price (free property)** ( :x: )
  - **State of the system**: price = 0.0, rent = 0.0
  - **Expected output**: Property created (edge case)

- **TC3: Negative price (invalid)** ( :x: )
  - **State of the system**: price = -50.0
  - **Expected output**: Constructor rejects OR throws exception

- **TC4: Maximum double price** ( :x: )
  - **State of the system**: price = Double.MAX_VALUE
  - **Expected output**: Handled safely OR rejected

### Rent Parameter Boundaries
- **TC5: Valid rent** ( :x: )
  - **State of the system**: price = 100.0, rent = 50.0
  - **Expected output**: Property created successfully

- **TC6: Zero rent** ( :x: )
  - **State of the system**: price = 100.0, rent = 0.0
  - **Expected output**: Property created (no rent due)

- **TC7: Negative rent (invalid)** ( :x: )
  - **State of the system**: price = 100.0, rent = -25.0
  - **Expected output**: Constructor rejects OR throws exception

- **TC8: Rent greater than price** ( :x: )
  - **State of the system**: price = 50.0, rent = 100.0
  - **Expected output**: Logical inconsistency check (allow or reject?)


## Method under test: `isOwned()`

- **TC11: Property unowned** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED
  - **Expected output**: false

- **TC12: Property owned** ( :x: )
  - **State of the system**: ownershipStatus = OWNED
  - **Expected output**: true


## Method under test: `isOwnedBy(Player player)`

- **TC13: Check ownership with null player** ( :x: )
  - **State of the system**: player = null
  - **Expected output**: NullPointerException thrown (fail-fast)

- **TC14: Check ownership - player is owner** ( :x: )
  - **State of the system**: ownershipStatus = OWNED, player is owner
  - **Expected output**: true

- **TC15: Check ownership - player is not owner** ( :x: )
  - **State of the system**: ownershipStatus = OWNED, player is different owner
  - **Expected output**: false

- **TC16: Check ownership - property unowned** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED, player = valid Player
  - **Expected output**: false


## Method under test: `purchase(Player player)`

- **TC17: Valid purchase by player with enough balance** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED, player.balance >= price
  - **Expected output**: ownershipStatus = OWNED, player.balance decreases by price

- **TC18: Purchase with exact balance** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED, player.balance == price
  - **Expected output**: ownershipStatus = OWNED, player.balance = 0

- **TC19: Purchase with insufficient balance** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED, player.balance < price
  - **Expected output**: Purchase rejected OR failed

- **TC20: Purchase null player** ( :x: )
  - **State of the system**: player = null
  - **Expected output**: NullPointerException thrown (fail-fast)

- **TC21: Purchase already owned property** ( :x: )
  - **State of the system**: ownershipStatus = OWNED
  - **Expected output**: Purchase rejected / no change

- **TC22: Purchase zero-price property** ( :x: )
  - **State of the system**: price = 0.0, ownershipStatus = UNOWNED
  - **Expected output**: Property acquired for free, ownershipStatus = OWNED


## Method under test: `chargeRent(Player renter)`

- **TC23: Charge rent with player having exact amount** ( :x: )
  - **State of the system**: renter.balance == rent, ownershipStatus = OWNED
  - **Expected output**: renter.balance -= rent, owner.balance += rent

- **TC24: Charge rent with player having more than rent** ( :x: )
  - **State of the system**: renter.balance > rent, ownershipStatus = OWNED
  - **Expected output**: Rent paid successfully

- **TC25: Charge rent with insufficient balance** ( :x: )
  - **State of the system**: renter.balance < rent, ownershipStatus = OWNED
  - **Expected output**: Player must sell property OR lose game

- **TC26: Charge rent to null player** ( :x: )
  - **State of the system**: renter = null
  - **Expected output**: NullPointerException thrown (fail-fast)

- **TC27: Charge rent on unowned property** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED
  - **Expected output**: No rent charged / rejected

- **TC28: Owner pays rent to self (edge case)** ( :x: )
  - **State of the system**: renter == owner, ownershipStatus = OWNED
  - **Expected output**: No rent transfer OR handled safely

- **TC29: Charge zero rent** ( :x: )
  - **State of the system**: rent = 0.0, ownershipStatus = OWNED
  - **Expected output**: No money transferred


## Method under test: `getResaleValue()`

- **TC30: Standard resale (80% of price)** ( :x: )
  - **State of the system**: price = 100.0
  - **Expected output**: 80.0

- **TC31: Zero price resale** ( :x: )
  - **State of the system**: price = 0.0
  - **Expected output**: 0.0

- **TC32: Small price resale** ( :x: )
  - **State of the system**: price = 1.0
  - **Expected output**: 0.8

- **TC33: Large price resale** ( :x: )
  - **State of the system**: price = Double.MAX_VALUE
  - **Expected output**: Overflow handling / safe calculation


## Method under test: `resetOwner()`

- **TC34: Reset when property owned** ( :x: )
  - **State of the system**: ownershipStatus = OWNED
  - **Expected output**: ownershipStatus = UNOWNED

- **TC35: Reset when property already unowned** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED
  - **Expected output**: No change / idempotent


## Method under test: `landOn(Player player, GameEngine game)`

- **TC36: Land on owned property (not yours) with sufficient balance** ( :x: )
  - **State of the system**: ownershipStatus = OWNED, player != owner, renter.balance >= rent
  - **Expected output**: Rent charged to current player, owner receives rent

- **TC37: Land on owned property (not yours) with insufficient balance** ( :x: )
  - **State of the system**: ownershipStatus = OWNED, player != owner, renter.balance < rent
  - **Expected output**: chargeRent returns false, no money transferred

- **TC38: Land on own property** ( :x: )
  - **State of the system**: ownershipStatus = OWNED, owner == player
  - **Expected output**: Safe zone / no rent charged

- **TC39: Land on unowned property** ( :x: )
  - **State of the system**: ownershipStatus = UNOWNED
  - **Expected output**: No action / GameEngine handles purchase prompt

- **TC40: Land with null player throws exception** ( :x: )
  - **State of the system**: player = null
  - **Expected output**: NullPointerException thrown (fail-fast)

- **TC41: Land with null game (no validation)** ( :x: )
  - **State of the system**: game = null
  - **Expected output**: Method executes normally (game param not used)