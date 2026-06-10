package util;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalizationManagerTests {

    @Test
    public void getLocale_WithDefaultSystemLocale_ReturnsSupportedLocale() {
        Locale actual = LocalizationManager.getLocale();

        boolean isSupportedLocale = actual.equals(LocalizationManager.ENGLISH)
                || actual.equals(LocalizationManager.SPANISH);

        assertTrue(isSupportedLocale);
    }

}
