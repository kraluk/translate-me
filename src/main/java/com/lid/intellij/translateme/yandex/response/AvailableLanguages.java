package com.lid.intellij.translateme.yandex.response;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Simple POJO for {@code getLangs} method response
 */
public class AvailableLanguages {

    @SerializedName("dirs")
    private List<String> pairs;

    @SerializedName("langs")
    private Map<String, String> langs;

    public List<String> getPairs() {
        return pairs;
    }

    public void setPairs(List<String> pairs) {
        this.pairs = pairs;
    }

    public Map<String, String> getLangs() {
        if (langs == null) {
            return Collections.emptyMap();
        }

        return langs;
    }

    public void setLangs(Map<String, String> langs) {
        this.langs = langs;
    }
}
