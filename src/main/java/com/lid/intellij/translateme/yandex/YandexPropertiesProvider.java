package com.lid.intellij.translateme.yandex;

import com.google.common.collect.Maps;
import com.intellij.openapi.diagnostic.Logger;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Simple Yandex Client properties provider
 *
 * @author lukasz
 */
final class YandexPropertiesProvider {
    private static final Logger log = Logger.getInstance(YandexPropertiesProvider.class);

    private static final String FILE_NAME = "yandex.properties";

    private static Map<YandexProperty, String> propertiesMap;

    public static Map<YandexProperty, String> getProperties() {

        if (propertiesMap == null) {
            Properties properties = new Properties();
            propertiesMap = Maps.newHashMap();

            try (final InputStream stream = YandexPropertiesProvider.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
                properties.load(stream);

                log.debug("Loaded '{}' properties.", properties.size());

                for (String name : properties.stringPropertyNames()) {
                    YandexProperty key = YandexProperty.getByKey(name);
                    String value = properties.getProperty(name);

                    propertiesMap.put(key, value);
                }
            } catch (Exception e) {
                log.error("Unable to load '{}' file!", e, FILE_NAME);
            }
        }

        return propertiesMap;
    }

    /**
     * Provides keys required in the {@link YandexPropertiesProvider#FILE_NAME} file
     */
    enum YandexProperty {
        HOST("host"),
        PATH("path"),
        API_KEY("api.key");

        private String key;

        YandexProperty(String key) {
            this.key = key;
        }

        public static YandexProperty getByKey(String key) {
            return Stream.of(YandexProperty.values())
                    .filter(e -> e.getKey().equals(key))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Illegal key value!"));
        }

        public String getKey() {
            return key;
        }
    }
}
