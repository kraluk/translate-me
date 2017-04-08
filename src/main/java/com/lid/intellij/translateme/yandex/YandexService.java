package com.lid.intellij.translateme.yandex;

import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.lid.intellij.translateme.rest.RestServiceInvoker;
import com.lid.intellij.translateme.yandex.response.AvailableLanguages;
import com.lid.intellij.translateme.yandex.response.DetectedLanguage;
import com.lid.intellij.translateme.yandex.response.Translation;

import static com.lid.intellij.translateme.yandex.YandexRequestBuilder.YandexMethod.*;

/**
 * Yandex translation service
 *
 * @author lukasz
 * @see YandexRequestBuilder
 */
public class YandexService {
    private static final Logger log = Logger.getInstance(YandexService.class);

    private static final Gson JSON_PARSER = new Gson();

    private final RestServiceInvoker restServiceInvoker;

    public YandexService(RestServiceInvoker restServiceInvoker) {
        this.restServiceInvoker = restServiceInvoker;
    }

    /**
     * Translates given text from one language to another
     *
     * @param text     a text to be translated
     * @param langFrom a source language
     * @param langTo   a target language
     * @return a {@link Translation} response object
     */
    public Translation translate(String text, String langFrom, String langTo) {
        String request = YandexRequestBuilder.forMethod(TRANSLATE)
                .withLangFrom(langFrom)
                .withLangTo(langTo)
                .withText(text)
                .build();

        log.debug("Attempting to invoke request '{}'...", request);

        String response = restServiceInvoker.get(request);

        log.debug("Got response '{}'", response);

        return JSON_PARSER.fromJson(response, Translation.class);
    }

    /**
     * Detects language of given text
     *
     * @param text a text to be processed
     * @return a {@link DetectedLanguage} response object
     */
    public DetectedLanguage detect(String text) {
        String request = YandexRequestBuilder.forMethod(DETECT)
                .withText(text)
                .build();

        log.debug("Attempting to invoke request '{}'...", request);

        String response = restServiceInvoker.get(request);

        log.debug("Got response '{}'", response);

        return JSON_PARSER.fromJson(response, DetectedLanguage.class);
    }

    /**
     * Gets available languages for given UI language
     *
     * @param ui a UI language
     * @return a {@link AvailableLanguages} response language
     */
    public AvailableLanguages getLanguages(String ui) {
        String request = YandexRequestBuilder.forMethod(GET_LANGUAGES)
                .withUi(ui)
                .build();

        log.debug("Attempting to invoke request '{}'...", request);

        String response = restServiceInvoker.get(request);

        log.debug("Got response '{}'", response);

        return JSON_PARSER.fromJson(response, AvailableLanguages.class);
    }
}
