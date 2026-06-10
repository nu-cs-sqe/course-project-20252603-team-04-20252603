package util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalizationManagerTests {

    @Test
    public void getLocale_WithDefaultSystemLocale_ReturnsSupportedLocale() {
        Locale actual = LocalizationManager.getLocale();

        boolean isSupportedLocale = actual.equals(LocalizationManager.ENGLISH)
                || actual.equals(LocalizationManager.SPANISH);

        assertTrue(isSupportedLocale);
    }

    @Test
    public void setLocale_WithEnglishLocale_SetsLocaleToEnglish() {
        LocalizationManager.setLocale(Locale.ENGLISH);

        Locale actual = LocalizationManager.getLocale();

        assertEquals(Locale.ENGLISH, actual);
    }

    @Test
    public void setLocale_WithSpanishLocale_SetsLocaleToSpanish() {
        LocalizationManager.setLocale(LocalizationManager.SPANISH);

        Locale actual = LocalizationManager.getLocale();

        assertEquals(LocalizationManager.SPANISH, actual);
    }

    @Test
    public void setLocale_WithUnsupportedLocale_FallsBackToEnglish() {
        LocalizationManager.setLocale(Locale.FRENCH);

        Locale actual = LocalizationManager.getLocale();

        assertEquals(Locale.ENGLISH, actual);
    }

    @Test
    public void setLocale_WithNullLocale_ThrowsException() {
        assertThrows(NullPointerException.class, () -> LocalizationManager.setLocale(null));
    }

    @Test
    public void getSupportedLocales_ReturnsEnglishAndSpanish() {
        List<Locale> supportedLocales = LocalizationManager.getSupportedLocales();

        assertTrue(supportedLocales.contains(LocalizationManager.ENGLISH));
        assertTrue(supportedLocales.contains(LocalizationManager.SPANISH));
    }

    @Test
    public void getSupportedLocales_WhenCallerModifiesReturnedList_ThrowsException() {
        List<Locale> supportedLocales = LocalizationManager.getSupportedLocales();

        assertThrows(UnsupportedOperationException.class, () -> supportedLocales.add(Locale.FRENCH));
    }

    @Test
    public void getMessage_WithEnglishLocaleAndTitleKey_ReturnsEnglishTitle() {
        LocalizationManager.setLocale(Locale.ENGLISH);

        String actual = LocalizationManager.getMessage("mainMenu.title");

        assertEquals("Monopoly", actual);
    }

}
