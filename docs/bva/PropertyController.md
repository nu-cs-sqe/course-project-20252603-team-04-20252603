# PropertyController BVA Analysis

## Method under test: `promptPurchase(Player player, Property property)`

- **TC1: Null player** ( :white_check_mark: )
  - **State of the system**: player = null, property = valid
  - **Expected output**: NullPointerException thrown

- **TC2: Null property** ( :white_check_mark: )
  - **State of the system**: player = valid, property = null
  - **Expected output**: NullPointerException thrown

- **TC3: Property already owned** ( :white_check_mark: )
  - **State of the system**: property.isOwned() = true
  - **Expected output**: false

- **TC4: Unowned property, player cannot afford** ( :white_check_mark: )
  - **State of the system**: property.isOwned() = false, player.canAfford(price) = false
  - **Expected output**: false

- **TC5: Unowned property, player has exactly the price** ( :white_check_mark: )
  - **State of the system**: property.isOwned() = false, player.canAfford(price) = true (exact)
  - **Expected output**: true

- **TC6: Unowned property, player has more than the price** ( :white_check_mark: )
  - **State of the system**: property.isOwned() = false, player.canAfford(price) = true (surplus)
  - **Expected output**: true

---

## Method under test: `buyProperty(Player player, Property property)`

- **TC7: Null player** ( :white_check_mark: )
  - **State of the system**: player = null, property = valid
  - **Expected output**: NullPointerException thrown

- **TC8: Null property** ( :white_check_mark: )
  - **State of the system**: player = valid, property = null
  - **Expected output**: NullPointerException thrown

- **TC9: Successful purchase** ( :white_check_mark: )
  - **State of the system**: property.purchase(player) = true
  - **Expected output**: true

- **TC10: Failed purchase (insufficient funds)** ( :white_check_mark: )
  - **State of the system**: property.purchase(player) = false
  - **Expected output**: false

---

## Method under test: `declineProperty(Player player, Property property)`

- **TC11: Null player** ( :white_check_mark: )
  - **State of the system**: player = null, property = valid
  - **Expected output**: NullPointerException thrown

- **TC12: Null property** ( :white_check_mark: )
  - **State of the system**: player = valid, property = null
  - **Expected output**: NullPointerException thrown

- **TC13: Valid inputs** ( :white_check_mark: )
  - **State of the system**: player = valid, property = valid (unowned)
  - **Expected output**: completes without exception, no model mutation

---

## Method under test: `handleRentPayment(Player renter, Property property)`

- **TC14: Null renter** ( :white_check_mark: )
  - **State of the system**: renter = null, property = valid
  - **Expected output**: NullPointerException thrown

- **TC15: Null property** ( :white_check_mark: )
  - **State of the system**: renter = valid, property = null
  - **Expected output**: NullPointerException thrown

- **TC16: Successful rent charge** ( :white_check_mark: )
  - **State of the system**: property.chargeRent(renter) = true
  - **Expected output**: true

- **TC17: Failed rent charge (insufficient balance)** ( :white_check_mark: )
  - **State of the system**: property.chargeRent(renter) = false
  - **Expected output**: false

---

## Method under test: `handleForcedSale(Player player, double requiredAmount)`

- **TC18: Null player** ( :white_check_mark: )
  - **State of the system**: player = null
  - **Expected output**: NullPointerException thrown

- **TC19: requiredAmount = 0, player balance = 0** ( :white_check_mark: )
  - **State of the system**: requiredAmount = 0, player.canAfford(0) = true
  - **Expected output**: true (already affordable)

- **TC20: Player already has enough balance** ( :white_check_mark: )
  - **State of the system**: player.canAfford(requiredAmount) = true (no selling needed)
  - **Expected output**: true

- **TC21: No properties, insufficient balance** ( :white_check_mark: )
  - **State of the system**: player.getOwnedProperties() = empty, player.canAfford() = false
  - **Expected output**: false

- **TC22: One property sold covers required amount** ( :white_check_mark: )
  - **State of the system**: one property with resale value >= (requiredAmount - balance)
  - **Expected output**: true

- **TC23: Sell all properties, still insufficient** ( :white_check_mark: )
  - **State of the system**: all properties sold, balance still < requiredAmount
  - **Expected output**: false

- **TC24: First property sale makes player affordable, loop breaks before later properties** ( :white_check_mark: )
  - **State of the system**: player is initially unaffordable, first owned property sale raises balance to requiredAmount or above, later properties remain untouched
  - **Expected output**: true
