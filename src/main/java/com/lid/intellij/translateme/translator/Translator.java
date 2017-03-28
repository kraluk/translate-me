package com.lid.intellij.translateme.translator;

import java.util.List;

@FunctionalInterface
public interface Translator {

    List<String> translate(String text, String[] langPair, boolean autoDetect);
}
