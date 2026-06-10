package util;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public final class LocalizationManager {
    private static final String BUNDLE_NAME = "i18n.messages";

    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale SPANISH = new Locale("es");

    private static Locale currentLocale = ENGLISH;
    private static ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);

    private LocalizationManager() {
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static List<Locale> getSupportedLocales() {
        return List.of(ENGLISH, SPANISH);
    }

    public static String getMessage(String key) {
        return messages.getString(key);
    }

    public static void setLocale(Locale locale) {
        Objects.requireNonNull(locale, "Locale cannot be null");

        if (locale.getLanguage().equals(SPANISH.getLanguage())) {
            currentLocale = SPANISH;
            return;
        }

        currentLocale = ENGLISH;
        messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
    }

}
