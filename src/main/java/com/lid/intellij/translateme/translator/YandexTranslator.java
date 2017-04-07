package com.lid.intellij.translateme.translator;

import com.intellij.openapi.diagnostic.Logger;
import com.lid.intellij.translateme.rest.RestServiceInvoker;
import com.lid.intellij.translateme.yandex.YandexService;
import com.lid.intellij.translateme.yandex.response.DetectedLanguage;
import com.lid.intellij.translateme.yandex.response.Translation;

import java.util.Collections;
import java.util.List;

/**
 * TODO: !!!
 */
public class YandexTranslator implements Translator {
    private static final Logger log = Logger.getInstance(YandexTranslator.class);

    private static final int HTTP_CODE_OK = 200;

    private YandexService service;

    public YandexTranslator() {
        this.service = new YandexService(new RestServiceInvoker());
    }

    @Override
    public List<String> translate(String text, String[] languagePair, boolean autoDetect) {
        Translation translation;

        if (autoDetect) {
            DetectedLanguage response = service.detect(text);
            int code = response.getCode();

            if (code == HTTP_CODE_OK) {
                String language = response.getLang();
                translation = service.translate(text, language, languagePair[1]);
            } else {
                log.debug("Failed to detect language. Received code '{}'", code);
                translation = service.translate(text, languagePair[0], languagePair[1]);
            }
        } else {
            translation = service.translate(text, languagePair[0], languagePair[1]);
        }

        if (translation != null && translation.getText() != null) {
            return translation.getText();
        }

        return Collections.emptyList();
    }
}
