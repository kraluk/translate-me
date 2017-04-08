package com.lid.intellij.translateme.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.lid.intellij.translateme.action.handler.ActionHandler;
import com.lid.intellij.translateme.translator.YandexTranslator;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * TODO: !!!
 *
 * @author v.ivanov
 * @author lukasz
 */
class TranslateHandler extends EditorActionHandler {

    private final ActionHandler handler;

    public TranslateHandler(ActionHandler handler) {
        this.handler = handler;
    }

    @Override
    protected final void doExecute(final Editor editor, @Nullable final Caret caret, final DataContext dataContext) {
        if (editor == null) {
            return;
        }

        Project project = editor.getProject();

        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText != null && selectedText.length() > 0) {
            String splittedText = splitCamelCase(selectedText);
            splittedText = splitUnderscore(splittedText);

            Pair<String, String> langPair = TranslateAction.getLangPair(project);
            boolean autoDetect = TranslateAction.isAutoDetect(project);
            List<String> translated = new YandexTranslator().translate(splittedText, langPair, autoDetect);
            if (translated != null) {
                handler.handleResult(editor, translated);
            } else {
                handler.handleError(editor);
            }
        }
    }

    private static String splitUnderscore(String splittedText) {
        String[] splitted = splittedText.split("_");
        return arrayToString(splitted);
    }

    private static String splitCamelCase(String selectedText) {
        String[] splitted = selectedText.split("(?<=[a-z])(?=[A-Z])");
        return arrayToString(splitted);
    }

    private static String arrayToString(String[] splitted) {
        if (splitted.length == 1) {
            return splitted[0];
        }
        StringBuilder builder = new StringBuilder();
        for (String word : splitted) {
            builder.append(word).append(" ");
        }
        return builder.toString();
    }
}
