# Overall Rule
This folder should contain the BVA analysis. There should be one .md for each class, and there should be BVA analysis for each public method.

# What to Include in each BVA Analysis File (like `MyVector.md`)

You are encouraged to document your intermediate analysis results for Steps 1-3.
However, you are only required to document Step 4.

For each test case, you may choose **any easy-to-read format** you like. Regardless of the format, you are required to specify the following for each test case:
1. A unique ID
2. The method(s) under test
3. The state of the system under test
4. The expected output
5. Whether it has been implemented

# Player BVA Analysis

## Method under test: `remove(double amount)`

- **TC1: Buy with zero amount** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 0  
  - **Expected output**: balance remains 100  

- **TC2: Buy with negative amount (invalid)** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = -10  
  - **Expected output**: operation rejected OR no change  

- **TC3: Buy with exact balance** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 100  
  - **Expected output**: balance becomes 0  

- **TC4: Buy with slightly less than balance** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 99.00
  - **Expected output**: balance becomes 1.00 

- **TC5: Buy with more than balance** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 101  
  - **Expected output**: purchase denied OR no change  

- **TC5b: Buy with Double.MAX_VALUE (extreme boundary)** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = Double.MAX_VALUE  
  - **Expected output**: purchase denied OR no change

## Method under test: `receive(double amount)`

- **TC6: Sell zero amount** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 0  
  - **Expected output**: balance remains 100  

- **TC7: Sell negative amount (invalid)** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = -20  
  - **Expected output**: operation rejected  

- **TC8: Sell positive amount** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 50  
  - **Expected output**: balance becomes 150

- **TC9: Sell maximum double value** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = Double.MAX_VALUE  
  - **Expected output**: operation rejected OR no change  

## Method under test: `canAfford(double amount)`

- **TC10: Amount = 0** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 0  
  - **Expected output**: true  

- **TC11: Amount slightly less than balance** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 99.99  
  - **Expected output**: true  

- **TC12: Amount equal to balance** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 100  
  - **Expected output**: true  

- **TC13: Amount slightly greater than balance** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = 100.01  
  - **Expected output**: false  

- **TC14: Negative amount** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = -10  
  - **Expected output**: false  

- **TC14b: Amount = Double.MAX_VALUE** ( :white_check_mark: )  
  - **State of the system**: balance = 100, amount = Double.MAX_VALUE
  - **Expected output**: false  

## Method under test: `addProperty(Property property)`

- **TC15: Add to empty property set** ( :white_check_mark: )  
  - **State of the system**: properties = empty  
  - **Expected output**: property added  

- **TC16: Add duplicate property** ( :white_check_mark: )  
  - **State of the system**: property already exists  
  - **Expected output**: no duplicate added  

- **TC17: Add null property** ( :white_check_mark: )  
  - **State of the system**: property = null  
  - **Expected output**: rejected / no change  


## Method under test: `removeProperty(Property property)`

- **TC18: Remove existing property** ( :white_check_mark: )  
  - **State of the system**: property exists in set  
  - **Expected output**: property removed  

- **TC19: Remove non-existing property** ( :white_check_mark: )  
  - **State of the system**: property not in set  
  - **Expected output**: no change  

- **TC20: Remove from empty set** ( :white_check_mark: )  
  - **State of the system**: properties = empty  
  - **Expected output**: no change  

- **TC20a: Remove null property** ( :white_check_mark: )  
  - **State of the system**: property = null  
  - **Expected output**: rejected / no change  


## Method under test: `sellProperty(Property property)`

- **TC21: Sell owned property** ( :white_check_mark: )  
  - **State of the system**: property in set  
  - **Expected output**: property removed + balance increases  

- **TC22: Sell unowned property** ( :white_check_mark: )  
  - **State of the system**: property not in set  
  - **Expected output**: no change  

- **TC22a: Sell null property** ( :white_check_mark: )  
  - **State of the system**: property = null  
  - **Expected output**: rejected / no change  

- **TC22b: Sell owned property with invalid price** ( :white_check_mark: )  
  - **State of the system**: property in set, price = Double.MAX_VALUE  
  - **Expected output**: rejected / no change  


## Method under test: `goToJail(int jailPosition)`

- **TC23: Valid jail position** ( :white_check_mark: )  
  - **State of the system**: position = any valid  
  - **Expected output**: inJail = true, position = 10  

- **TC24: Negative jail position** (  :white_check_mark:  )  
  - **State of the system**: jailPosition = -1  
  - **Expected output**: rejected OR handled safely  

- **TC24b: Maximum integer jail position (invalid)** (  :white_check_mark:  )
  - **State of the system**: jailPosition = Integer.MAX_VALUE
  - **Expected output**: rejected OR handled safely

- **TC24c: Minimum integer jail position (invalid)** (  :white_check_mark:  )
  - **State of the system**: jailPosition = Integer.MIN_VALUE
  - **Expected output**: rejected OR handled safely


## Method under test: `leaveJail()`

- **TC25: Player is in jail** ( :white_check_mark: )  
  - **State of the system**: inJail = true  
  - **Expected output**: inJail = false, jailTurnCount reset, position increases by 1  

- **TC26: Player not in jail** ( :white_check_mark: )  
  - **State of the system**: inJail = false  
  - **Expected output**: no change  


## Method under test: `isBankrupt()`

- **TC27: Balance = 0** ( :white_check_mark: )  
  - **State of the system**: balance = 0  
  - **Expected output**: true AND active = false  

- **TC28: Balance slightly above 0** ( :white_check_mark: )  
  - **State of the system**: balance = 0.01  
  - **Expected output**: false AND active remains true  

- **TC29: Balance negative** ( :white_check_mark: )  
  - **State of the system**: balance = -10  
  - **Expected output**: true AND active = false

## Method under test: `getActive()`

- **TC30: Get active status when true** ( :white_check_mark: )  
  - **State of the system**: active = true  
  - **Expected output**: returns true  

- **TC31: Get active status when false** ( :white_check_mark: )  
  - **State of the system**: active = false  
  - **Expected output**: returns false  

## Method under test: `getName()`

- **TC32: Get name for player "Alice"** ( :white_check_mark: )  
  - **State of the system**: name = "Alice"  
  - **Expected output**: returns "Alice"  

- **TC33: Get name for player "Bob"** ( :white_check_mark: )  
  - **State of the system**: name = "Bob"  
  - **Expected output**: returns "Bob"  

## Method under test: `getJailTurnCount()`

- **TC34: Get jailTurnCount when initially created** ( :white_check_mark: )  
  - **State of the system**: jailTurnCount = 0 (initial state)  
  - **Expected output**: returns 0  

- **TC35: Get jailTurnCount after going to jail** ( :white_check_mark: )  
  - **State of the system**: player just called goToJail()  
  - **Expected output**: returns 1  

- **TC36: Get jailTurnCount after leaving jail** ( :white_check_mark: )  
  - **State of the system**: player called goToJail() then leaveJail()  
  - **Expected output**: returns 0