### Method under test: `roll()`
1. Input: Nothing, Output: updates `dieOne` and `dieTwo`
2. Output type: Two intervals
3. Output ranges: `dieOne` = [1,6], `dieTwo` = [1,6]

- **TC1: roll_OnLowestValues_SetDiceTo1And1** ( :white_check_mark: )
  - **State of the system**: Mock Random returns 0, then 0
  - **Expected output**: `getDieOne()` returns 1, `getDieTwo()` returns 1

- **TC2: roll_OnHighestValues_SetDiceTo6And6** ( :white_check_mark: )
  - **State of the system**: Mock Random returns 5, then 5
  - **Expected output**: `getDieOne()` returns 6, `getDieTwo()` returns 6

### Method under test: `getDieOne()`
1. Input: Nothing, Output: stored `dieOne`
2. Output type: Interval
3. Output range: [1,6]

- **TC1: getDieOne_OnLowestRoll_Return1** ( :white_check_mark: )
  - **State of the system**: `dieOne = 1`
  - **Expected output**: 1

- **TC2: getDieOne_OnHighestRoll_Return6** ( :white_check_mark: )
  - **State of the system**: `dieOne = 6`
  - **Expected output**: 6

### Method under test: `getDieTwo()`
1. Input: Nothing, Output: stored `dieTwo`
2. Output type: Interval
3. Output range: [1,6]

- **TC1: getDieTwo_OnLowestRoll_Return1** ( :white_check_mark: )
  - **State of the system**: `dieTwo = 1`
  - **Expected output**: 1

- **TC2: getDieTwo_OnHighestRoll_Return6** ( :white_check_mark: )
  - **State of the system**: `dieTwo = 6`
  - **Expected output**: 6

### Method under test: `getTotal()`
1. Input: Nothing, Output: `dieOne + dieTwo`
2. Output type: Interval
3. Output range: [2,12]

- **TC1: getTotal_WhenDiceAre1And1_Return2** ( :x: )
  - **State of the system**: `dieOne = 1`, `dieTwo = 1`
  - **Expected output**: 2

- **TC2: getTotal_WhenDiceAre6And6_Return12** ( :x: )
  - **State of the system**: `dieOne = 6`, `dieTwo = 6`
  - **Expected output**: 12

### Method under test: `isDoubles()`
1. Input: Nothing, Output: true or false
2. Output type: Boolean
3. Output values: true, false

- **TC1: isDoubles_WhenDiceAreEqual_ReturnTrue** ( :x: )
  - **State of the system**: `dieOne = 1`, `dieTwo = 1`
  - **Expected output**: true

- **TC2: isDoubles_WhenDiceAreDifferent_ReturnFalse** ( :x: )
  - **State of the system**: `dieOne = 1`, `dieTwo = 2`
  - **Expected output**: false