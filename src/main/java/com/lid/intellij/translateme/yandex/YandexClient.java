package com.lid.intellij.translateme.yandex;

import com.intellij.openapi.diagnostic.Logger;
import com.lid.intellij.translateme.yandex.YandexPropertiesProvider.YandexProperty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class YandexClient {
    private static final Logger log = Logger.getInstance(YandexClient.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    private YandexServiceInvoker yandexServiceInvoker;

    private String host;
    private String path;
    private String apiKey;

    public YandexClient() {
        Map<YandexProperty, String> yandexProperties = YandexPropertiesProvider.getProperties();

        this.host = yandexProperties.get(YandexProperty.HOST);
        this.path = yandexProperties.get(YandexProperty.PATH);
        this.apiKey = yandexProperties.get(YandexProperty.API_KEY);

        this.yandexServiceInvoker = new YandexServiceInvoker();
    }

    public String translate(String text, String langFrom, String langTo) {
        StringBuilder request = new StringBuilder(buildRequest(YandexMethod.TRANSLATE));

        request.append("&lang=");
        request.append(langFrom);
        request.append("-");
        request.append(langTo);
        request.append("&text=");
        request.append(encodeText(text));

        return yandexServiceInvoker.invoke(request.toString());
    }

    public String detect(String text) {
        StringBuilder request = new StringBuilder(buildRequest(YandexMethod.DETECT));

        request.append("&text=");
        request.append(encodeText(text));

        return yandexServiceInvoker.invoke(request.toString());
    }

    public String getLanguages(String ui) {
        StringBuilder request = new StringBuilder(buildRequest(YandexMethod.GET_LANGUAGES));

        request.append("&ui=");
        request.append(ui);

        return yandexServiceInvoker.invoke(request.toString());
    }

    private String buildRequest(YandexMethod method) {
        StringBuilder request = new StringBuilder();

        request.append("https://")
                .append(host)
                .append(path)
                .append(method.getName())
                .append("?key=")
                .append(apiKey);

        return request.toString();
    }

    private static String encodeText(String text) {
        try {
            return URLEncoder.encode(text, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to encode given text!", e);
        }
    }

    /**
     * Enumerates available Yandex methods
     */
    private enum YandexMethod {
        TRANSLATE("translate"),
        DETECT("detect"),
        GET_LANGUAGES("getLangs");

        private String name;

        YandexMethod(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
