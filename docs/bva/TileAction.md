# BVA: `TileAction`

---

## Factory methods: `none`, `offerPurchase`, `payRent`, `drawCard`, `payTax`, `goToJail`, `bankruptcyCheck`

- **TC1: Create NONE action with valid player and tile** ( :x: )
  - **State of the system**: valid `player`, valid `tile`
  - **Expected output**: `type = NONE`, `card` is empty, `amount = 0.0`

- **TC2: Create OFFER_PURCHASE action** ( :x: )
  - **State of the system**: valid `player`, valid `tile`
  - **Expected output**: `type = OFFER_PURCHASE`, `card` is empty, `amount = 0.0`

- **TC3: Create PAY_RENT action with positive amount** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `rent = 50.0`
  - **Expected output**: `type = PAY_RENT`, `amount = 50.0`

- **TC4: Create PAY_RENT action with zero amount** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `rent = 0.0`
  - **Expected output**: `type = PAY_RENT`, `amount = 0.0`

- **TC5: Create PAY_RENT action with negative amount** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `rent = -1.0`
  - **Expected output**: `type = PAY_RENT`, `amount = -1.0` (no validation)

- **TC6: Create DRAW_CARD action with valid card** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, valid `card`
  - **Expected output**: `type = DRAW_CARD`, `card` is present

- **TC7: Create DRAW_CARD action with null card** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `card = null`
  - **Expected output**: throws `NullPointerException`

- **TC8: Create PAY_TAX action with positive amount** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `amount = 100.0`
  - **Expected output**: `type = PAY_TAX`, `amount = 100.0`

- **TC9: Create PAY_TAX action with zero amount** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `amount = 0.0`
  - **Expected output**: `type = PAY_TAX`, `amount = 0.0`

- **TC10: Create PAY_TAX action with negative amount** ( :x: )
  - **State of the system**: valid `player`, valid `tile`, `amount = -10.0`
  - **Expected output**: `type = PAY_TAX`, `amount = -10.0` (no validation)

- **TC11: Create GO_TO_JAIL action** ( :x: )
  - **State of the system**: valid `player`, valid `tile`
  - **Expected output**: `type = GO_TO_JAIL`, `card` is empty, `amount = 0.0`

- **TC12: Create BANKRUPTCY_CHECK action** ( :x: )
  - **State of the system**: valid `player`, valid `tile`
  - **Expected output**: `type = BANKRUPTCY_CHECK`, `card` is empty, `amount = 0.0`

---

## Method under test: getters (`getType`, `getPlayer`, `getTile`, `getCard`, `getAmount`)

- **TC13: Getters return values from NONE action** ( :x: )
  - **State of the system**: action created via `none(player, tile)`
  - **Expected output**: type `NONE`, same `player` and `tile`, empty `card`, `amount = 0.0`

- **TC14: Getters return values from DRAW_CARD action** ( :x: )
  - **State of the system**: action created via `drawCard(player, tile, card)`
  - **Expected output**: type `DRAW_CARD`, same `player` and `tile`, present `card`, `amount = 0.0`

- **TC15: Getters return values from PAY_RENT action** ( :x: )
  - **State of the system**: action created via `payRent(player, tile, 75.0)`
  - **Expected output**: type `PAY_RENT`, same `player` and `tile`, empty `card`, `amount = 75.0`

- **TC16: Getters return values from PAY_TAX action** ( :x: )
  - **State of the system**: action created via `payTax(player, tile, 25.0)`
  - **Expected output**: type `PAY_TAX`, same `player` and `tile`, empty `card`, `amount = 25.0`

---

## Null handling (no validation in factory methods except drawCard)

- **TC17: Create NONE action with null player** ( :x: )
  - **State of the system**: `player = null`, valid `tile`
  - **Expected output**: action created, `getPlayer()` returns `null`

- **TC18: Create NONE action with null tile** ( :x: )
  - **State of the system**: valid `player`, `tile = null`
  - **Expected output**: action created, `getTile()` returns `null`

- **TC19: Create PAY_TAX action with null player and null tile** ( :x: )
  - **State of the system**: `player = null`, `tile = null`, `amount = 10.0`
  - **Expected output**: action created, getters return nulls
