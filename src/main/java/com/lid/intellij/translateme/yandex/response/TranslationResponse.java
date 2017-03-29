package com.lid.intellij.translateme.yandex.response;

import java.util.List;

/**
 * Simple POJO for {@code translate} method response
 */
public class TranslationResponse {

    private int code;
    private String lang;
    private List<String> text;

    public int getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getText() {
        return text;
    }
}