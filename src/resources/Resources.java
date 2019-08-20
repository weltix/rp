/*
 * Copyright (c) RESONANCE JSC, 20.08.2019
 */

package resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Class for application localization. Provides specified strings for specified language.
 * Strings for different languages are stored in *.properties files.
 * Always need to be one *.properties default file without language code (e.g. {@code strings.properties}).
 * Class - Singleton, because it is enough one object of it.
 */
// TODO: 29.05.2019 Save lang to file as preferences
// TODO: 29.05.2019 Get saved lang from file
public class Resources {
    private final static Resources INSTANCE = new Resources();
    private ResourceBundle resourceBundle;
    private final static String RU_LANG = "ru";
    private final static String UA_LANG = "uk";

    private Resources() {
        setLocale(RU_LANG);
    }

    public static Resources getInstance(){
        return INSTANCE;
    }

    /**
     * Returns a string by specified key from file *.properties for selected for {@code resourceBundle} language.
     *
     * @param key key, by which specified string returned.
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Sets default localization for current instance of JRE, using specified language.
     * Creates object of {@link ResourceBundle} using specified language.
     *
     * @param language language of application
     */
    private void setLocale(String language) {
        Locale currentLocale = new Locale(language);
        Locale.setDefault(currentLocale);
        resourceBundle = ResourceBundle.getBundle("resources/strings", currentLocale, new UTF8Control());
    }
}

/**
 * Class solves problem with cyrillic symbols for ResourceBundle.
 * ATTENTION: all *.properties files must have UTF-8 codetable.
 * Code should be executed at the very beginning of the program to allow *.form files get right strings from *.properties files.
 */
class UTF8Control extends ResourceBundle.Control {
    public ResourceBundle newBundle
            (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                // Only this line is changed to make it to read properties files as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
}
