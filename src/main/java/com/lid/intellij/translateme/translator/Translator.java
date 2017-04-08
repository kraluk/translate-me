package com.lid.intellij.translateme.translator;

import com.intellij.openapi.util.Pair;

import java.util.List;

@FunctionalInterface
public interface Translator {

    List<String> translate(String text, Pair<String, String> languagePair, boolean autoDetect);
}
