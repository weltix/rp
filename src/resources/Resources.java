/*
 * Copyright (c) RESONANCE JSC, 29.05.2019
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
 * Класс служит для локализации приложения. Обеспечивает получение строк для указанного языка.
 * Строки для разных языков хранятся в *.properties файлах.
 * Всегда должен быть один *.properties файл по умолчанию, без кода языка (например, {@code strings.properties}).
 * Класс - Singleton, так как нам достаточно единственного его объекта.
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
     * Возвращает по ключу строку из файла *.properties на установленном для {@code resourceBundle} языке
     *
     * @param key ключ, по которому возвращается соответствующая строка
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Устанавливает локализацию по умолчанию для данного экземпляра JRE, используя заданный язык.
     * Создаёт объект класса ResourceBundle с заданным языком.
     *
     * @param language желаемый язык интерфейса программы
     */
    private void setLocale(String language) {
        Locale currentLocale = new Locale(language);
        Locale.setDefault(currentLocale);
        resourceBundle = ResourceBundle.getBundle("resources/strings", currentLocale, new UTF8Control());
    }
}

/**
 * Класс, исправляющий проблему с русской кодировкой для ResourceBundle.
 * При этом, файлы *.properties должны иметь кодировку UTF-8.
 * Код должен выполниться в самом начале программы, чтобы *.form файлы могли обращаться к *.properties файлам,
 * и получать читабельные русскоязычные строки.
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
