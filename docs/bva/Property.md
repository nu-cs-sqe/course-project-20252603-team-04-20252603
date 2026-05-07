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

- **TC9: Property unowned** ( :x: )
  - **State of the system**: owner = null
  - **Expected output**: false

- **TC10: Property owned** ( :x: )
  - **State of the system**: owner = Player object
  - **Expected output**: true


## Method under test: `isOwnedBy(Player player)`

- **TC11: Check ownership with null player** ( :x: )
  - **State of the system**: player = null
  - **Expected output**: false OR rejected

- **TC12: Check ownership - player is owner** ( :x: )
  - **State of the system**: owner = player (same reference)
  - **Expected output**: true

- **TC13: Check ownership - player is not owner** ( :x: )
  - **State of the system**: owner = different Player object
  - **Expected output**: false

- **TC14: Check ownership - property unowned** ( :x: )
  - **State of the system**: owner = null, player = valid Player
  - **Expected output**: false


## Method under test: `purchase(Player player)`

- **TC15: Valid purchase by player with enough balance** ( :x: )
  - **State of the system**: owner = null, player.balance >= price
  - **Expected output**: owner = player, player.balance decreases by price

- **TC16: Purchase with exact balance** ( :x: )
  - **State of the system**: owner = null, player.balance == price
  - **Expected output**: owner = player, player.balance = 0

- **TC17: Purchase with insufficient balance** ( :x: )
  - **State of the system**: owner = null, player.balance < price
  - **Expected output**: Purchase rejected OR failed

- **TC18: Purchase null player** ( :x: )
  - **State of the system**: player = null
  - **Expected output**: Purchase rejected / no change

- **TC19: Purchase already owned property** ( :x: )
  - **State of the system**: owner = existing Player
  - **Expected output**: Purchase rejected / no change

- **TC20: Purchase zero-price property** ( :x: )
  - **State of the system**: price = 0.0, owner = null
  - **Expected output**: Property acquired for free


## Method under test: `chargeRent(Player renter)`

- **TC21: Charge rent with player having exact amount** ( :x: )
  - **State of the system**: renter.balance == rent, owner exists
  - **Expected output**: renter.balance -= rent, owner.balance += rent

- **TC22: Charge rent with player having more than rent** ( :x: )
  - **State of the system**: renter.balance > rent
  - **Expected output**: Rent paid successfully

- **TC23: Charge rent with insufficient balance** ( :x: )
  - **State of the system**: renter.balance < rent, owner exists
  - **Expected output**: Player must sell property OR lose game

- **TC24: Charge rent to null player** ( :x: )
  - **State of the system**: renter = null
  - **Expected output**: Rejected / no change

- **TC25: Charge rent on unowned property** ( :x: )
  - **State of the system**: owner = null
  - **Expected output**: No rent charged / rejected

- **TC26: Owner pays rent to self (edge case)** ( :x: )
  - **State of the system**: renter == owner
  - **Expected output**: No rent transfer OR handled safely

- **TC27: Charge zero rent** ( :x: )
  - **State of the system**: rent = 0.0
  - **Expected output**: No money transferred


## Method under test: `getResaleValue()`

- **TC28: Standard resale (80% of price)** ( :x: )
  - **State of the system**: price = 100.0
  - **Expected output**: 80.0

- **TC29: Zero price resale** ( :x: )
  - **State of the system**: price = 0.0
  - **Expected output**: 0.0

- **TC30: Small price resale** ( :x: )
  - **State of the system**: price = 1.0
  - **Expected output**: 0.8

- **TC31: Large price resale** ( :x: )
  - **State of the system**: price = Double.MAX_VALUE
  - **Expected output**: Overflow handling / safe calculation


## Method under test: `resetOwner()`

- **TC32: Reset when property owned** ( :x: )
  - **State of the system**: owner = Player object
  - **Expected output**: owner = null

- **TC33: Reset when property already unowned** ( :x: )
  - **State of the system**: owner = null
  - **Expected output**: No change / idempotent


## Method under test: `onLand(Player player, Game game)`

- **TC34: Land on unowned property with buying option** ( :x: )
  - **State of the system**: owner = null, player has balance >= price
  - **Expected output**: Player offered option to buy

- **TC35: Land on unowned property without buying option** ( :x: )
  - **State of the system**: owner = null, player has balance < price
  - **Expected output**: Player cannot buy

- **TC36: Land on owned property (not yours)** ( :x: )
  - **State of the system**: owner = different Player, renter has balance >= rent
  - **Expected output**: Rent charged to current player

- **TC37: Land on owned property (not yours) - insufficient balance** ( :x: )
  - **State of the system**: owner exists, renter.balance < rent
  - **Expected output**: Renter must sell property OR bankruptcy triggered

- **TC38: Land on own property** ( :x: )
  - **State of the system**: owner == player
  - **Expected output**: Safe zone / no action required

- **TC39: Land with null player** ( :x: )
  - **State of the system**: player = null
  - **Expected output**: Handled safely / rejected

- **TC40: Land with null game** ( :x: )
  - **State of the system**: game = null
  - **Expected output**: Handled safely / rejected