package com.lid.intellij.translateme.action;

import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.lid.intellij.translateme.action.handler.PopupActionHandler;
import com.lid.intellij.translateme.configuration.ConfigurationState;

import static com.lid.intellij.translateme.common.ApplicationConstant.DEFAULT_SOURCE_LANGUAGE;
import static com.lid.intellij.translateme.common.ApplicationConstant.DEFAULT_TARGET_LANGUAGE;

public class TranslateEditorAction extends EditorAction {
    private static final Pair<String, String> DEFAULT_LANGUAGE_PAIR = new Pair<>(
        DEFAULT_SOURCE_LANGUAGE, DEFAULT_TARGET_LANGUAGE);

    public TranslateEditorAction() {
        super(new TranslateEditorActionHandler(new PopupActionHandler()));
    }

    public static Pair<String, String> getLangPair(Project project) {

        if (project != null) {
            ConfigurationState state = ConfigurationState.getInstance();

            String from = state.getFrom();
            String to = state.getTo();

            return new Pair<>(from, to);
        }

        return DEFAULT_LANGUAGE_PAIR;
    }

    public static boolean isAutoDetect(Project project) {
        if (project != null) {
            ConfigurationState state = ConfigurationState.getInstance();
            return state.isAutoDetect();
        }

        return false;
    }
}
