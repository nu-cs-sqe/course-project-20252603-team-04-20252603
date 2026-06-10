package util;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class LocalizationManager {

    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale SPANISH = new Locale("es");

    private static Locale currentLocale = ENGLISH;

    private LocalizationManager() {
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static List<Locale> getSupportedLocales() {
        return List.of(ENGLISH, SPANISH);
    }

    public static void setLocale(Locale locale) {
        Objects.requireNonNull(locale, "Locale cannot be null");

        if (locale.getLanguage().equals(SPANISH.getLanguage())) {
            currentLocale = SPANISH;
            return;
        }

        currentLocale = ENGLISH;
    }

}
