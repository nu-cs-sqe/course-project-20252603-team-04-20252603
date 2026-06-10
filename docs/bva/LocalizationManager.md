### Method under test: `setLocale(Locale locale)` and `getLocale()`
1. Input: requested locale, Output: active locale
2. Input: locale category, Output: selected supported locale
3. Input values: English locale, Spanish locale, unsupported locale, null locale

- **TC1: getLocale_WithDefaultSystemLocale_ReturnsSupportedLocale** ( :white_check_mark: )
    - **State of the system**: `LocalizationManager` has just loaded
    - **Expected output**: returns one of the supported locales

- **TC2: setLocale_WithEnglishLocale_SetsLocaleToEnglish** ( :white_check_mark: )
    - **State of the system**: locale = `Locale.ENGLISH`
    - **Expected output**: `getLocale()` returns `Locale.ENGLISH`

- **TC3: setLocale_WithSpanishLocale_SetsLocaleToSpanish** ( :white_check_mark: )
    - **State of the system**: locale = Spanish locale
    - **Expected output**: `getLocale()` returns Spanish locale

- **TC4: setLocale_WithUnsupportedLocale_FallsBackToEnglish** ( :white_check_mark: )
    - **State of the system**: locale = unsupported locale
    - **Expected output**: `getLocale()` returns `Locale.ENGLISH`

- **TC5: setLocale_WithNullLocale_ThrowsException** ( :white_check_mark: )
    - **State of the system**: locale = `null`
    - **Expected output**: throws `NullPointerException`

### Method under test: `getSupportedLocales()`
1. Input: none, Output: supported locales
2. Input: none, Output: collection size and contents
3. Output values: English and Spanish

- **TC6: getSupportedLocales_ReturnsEnglishAndSpanish** ( :white_check_mark: )
    - **State of the system**: manager is initialized
    - **Expected output**: returns supported locales containing English and Spanish

- **TC7: getSupportedLocales_WhenCallerModifiesReturnedList_ThrowsException** ( :white_check_mark: )
    - **State of the system**: caller attempts to add another locale to the returned list
    - **Expected output**: throws `UnsupportedOperationException`

### Method under test: `getMessage(String key)`
1. Input: message key, Output: localized message
2. Input: key category, Output: localized string or exception
3. Input values: valid title key, missing key, null key

- **TC8: getMessage_WithEnglishLocaleAndTitleKey_ReturnsEnglishTitle** ( :white_check_mark: )
    - **State of the system**: active locale = English, key = `mainMenu.title`
    - **Expected output**: returns `Monopoly`

- **TC9: getMessage_WithSpanishLocaleAndTitleKey_ReturnsSpanishTitle** ( :white_check_mark: )
    - **State of the system**: active locale = Spanish, key = `mainMenu.title`
    - **Expected output**: returns `Monopolio`

- **TC10: getMessage_WithMissingKey_ThrowsException** ( :white_check_mark: )
    - **State of the system**: key is not in the resource bundle
    - **Expected output**: throws `MissingResourceException`

- **TC11: getMessage_WithNullKey_ThrowsException** ( :white_check_mark: )
    - **State of the system**: key = `null`
    - **Expected output**: throws `NullPointerException`

### Method under test: `formatMessage(String key, Object... arguments)`
1. Input: message key and replacement arguments, Output: formatted localized message
2. Input: key and argument category, Output: substituted string
3. Input values: one replacement value, no replacement values

- **TC12: formatMessage_WithEnglishLocaleAndPlayerNumber_ReturnsFormattedEnglishLabel**
    - **State of the system**: active locale = English, key = `mainMenu.playerLabel`, argument = `1`
    - **Expected output**: returns `Player 1`

- **TC13: formatMessage_WithSpanishLocaleAndPlayerNumber_ReturnsFormattedSpanishLabel**
    - **State of the system**: active locale = Spanish, key = `mainMenu.playerLabel`, argument = `1`
    - **Expected output**: returns `Jugador 1`

- **TC14: formatMessage_WithNoArgumentsForParameterizedMessage_ReturnsTemplateText**
    - **State of the system**: active locale = English, key = `mainMenu.playerLabel`, no arguments
    - **Expected output**: returns unresolved template text from `MessageFormat`
