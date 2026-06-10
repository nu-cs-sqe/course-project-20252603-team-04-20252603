package util;

import java.util.Locale;

public final class LocalizationManager {

    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale SPANISH = new Locale("es");

    private LocalizationManager() {
    }

    public static Locale getLocale() {
        return ENGLISH;
    }

}
