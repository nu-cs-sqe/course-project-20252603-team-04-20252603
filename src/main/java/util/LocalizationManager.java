package util;

import java.util.Locale;

public final class LocalizationManager {

    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale SPANISH = new Locale("es");

    private static Locale currentLocale = ENGLISH;

    private LocalizationManager() {
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
    }

}
