package com.lid.intellij.translateme.yandex;

import com.google.common.base.Preconditions;
import com.lid.intellij.translateme.yandex.YandexPropertiesProvider.YandexProperty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.lid.intellij.translateme.rest.RestServiceInvoker.DEFAULT_ENCODING;

/**
 * Yandex REST service *raw* request builder
 */
class YandexRequestBuilder {

    private final String host;
    private final String path;
    private final String apiKey;

    private final YandexMethod method;

    private String text;
    private String langFrom;
    private String langTo;

    private String ui;

    private YandexRequestBuilder(YandexMethod method) {
        Map<YandexProperty, String> yandexProperties = YandexPropertiesProvider.getProperties();

        this.host = yandexProperties.get(YandexProperty.HOST);
        this.path = yandexProperties.get(YandexProperty.PATH);
        this.apiKey = yandexProperties.get(YandexProperty.API_KEY);

        this.method = method;
    }

    public static YandexRequestBuilder forMethod(YandexMethod method) {
        Preconditions.checkArgument(method != null, "Method cannot be null!");

        return new YandexRequestBuilder(method);
    }

    private static String encodeText(String text) {
        try {
            return URLEncoder.encode(text, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to encode given text!", e);
        }
    }

    public YandexRequestBuilder withText(String text) {
        Preconditions.checkArgument(text != null, "Text cannot be null!");

        this.text = text;
        return this;
    }

    public YandexRequestBuilder withLangFrom(String langFrom) {
        Preconditions.checkArgument(langFrom != null, "LangFrom cannot be null!");

        this.langFrom = langFrom;
        return this;
    }

    public YandexRequestBuilder withLangTo(String langTo) {
        Preconditions.checkArgument(langTo != null, "LangTo cannot be null!");

        this.langTo = langTo;
        return this;
    }

    public YandexRequestBuilder withUi(String ui) {
        Preconditions.checkArgument(ui != null, "Ui cannot be null!");

        this.ui = ui;
        return this;
    }

    public String build() {
        StringBuilder baseRequest = new StringBuilder(getBaseRequest(method));

        switch (method) {
            case DETECT:
                return detect(baseRequest);

            case TRANSLATE:
                return translate(baseRequest);

            case GET_LANGUAGES:
                return getLanguages(baseRequest);

            default:
                throw new IllegalArgumentException("Illegal method type has been chosen!");
        }
    }

    private String translate(StringBuilder request) {
        request.append("&lang=");
        request.append(langFrom);
        request.append("-");
        request.append(langTo);
        request.append("&text=");
        request.append(encodeText(text));

        return request.toString();
    }

    private String detect(StringBuilder request) {
        request.append("&text=");
        request.append(encodeText(text));

        return request.toString();
    }

    private String getLanguages(StringBuilder request) {
        request.append("&ui=");
        request.append(ui);

        return request.toString();
    }

    private String getBaseRequest(YandexMethod method) {
        StringBuilder request = new StringBuilder();

        request.append("https://")
                .append(host)
                .append(path)
                .append(method.getName())
                .append("?key=")
                .append(apiKey);

        return request.toString();
    }

    /**
     * Enumerates available Yandex methods
     */
    enum YandexMethod {
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
