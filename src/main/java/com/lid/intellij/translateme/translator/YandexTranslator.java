package com.lid.intellij.translateme.translator;

import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.lid.intellij.translateme.yandex.response.DetectLanguageResponse;
import com.lid.intellij.translateme.yandex.response.TranslationResponse;
import com.lid.intellij.translateme.yandex.YandexClient;

import java.util.Collections;
import java.util.List;

public class YandexTranslator implements Translator {
    private static final Logger log = Logger.getInstance(YandexTranslator.class);

    private static final int HTTP_CODE_OK = 200;

    private static final Gson GSON_PARSER = new Gson();

    @Override
    public List<String> translate(String text, String[] languagePair, boolean autoDetect) {
        return translate0(text, languagePair, autoDetect);
    }

    private List<String> translate0(String splittedText, String[] languagePairs, boolean autoDetect) {
        String translated;
        YandexClient yandexClient = new YandexClient();

        if (autoDetect) {
            String detect = yandexClient.detect(splittedText);
            DetectLanguageResponse response = GSON_PARSER.fromJson(detect, DetectLanguageResponse.class);
            int code = response.getCode();

            if (code == HTTP_CODE_OK) {
                String language = response.getLang();
                translated = yandexClient.translate(splittedText, language, languagePairs[1]);
            } else {
                log.debug("Failed to detect language. Received code '{}'", code);
                translated = yandexClient.translate(splittedText, languagePairs[0], languagePairs[1]);
            }
        } else {
            translated = yandexClient.translate(splittedText, languagePairs[0], languagePairs[1]);
        }

        if (translated != null) {
            TranslationResponse response = new Gson().fromJson(translated, TranslationResponse.class);
            return response.getText();
        }

        return Collections.emptyList();
    }

}
