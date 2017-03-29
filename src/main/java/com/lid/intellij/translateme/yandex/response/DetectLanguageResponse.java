package com.lid.intellij.translateme.yandex.response;


/**
 * Simple POJO for {@code detect} method response
 * <p/>
 * Example response:
 * <br/>
 * <code>
 * {
 * "code": 200,
 * "lang": "en"
 * }
 * </code>
 */
public class DetectLanguageResponse {

    private int code;
    private String lang;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
