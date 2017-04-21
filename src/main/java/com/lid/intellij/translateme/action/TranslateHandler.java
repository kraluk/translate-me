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
    private static final String UNDERSCORE_SPLIT_PATTERN = "_";
    private static final String CAMEL_CASE_SPLIT_PATTERN = "(?<=[a-z])(?=[A-Z])";

    private static final String WORD_CONJUNCTION = " ";

    private final ActionHandler handler;

    TranslateHandler(ActionHandler handler) {
        this.handler = handler;
    }

    @Override
    protected final void doExecute(final Editor editor, @Nullable final Caret caret,
                                   final DataContext dataContext) {
        if (editor == null) {
            return;
        }

        Project project = editor.getProject();
        String selectedText = editor.getSelectionModel().getSelectedText();

        if (selectedText != null && selectedText.length() > 0) {
            String preparedText = prepareTextToTranslation(selectedText);

            Pair<String, String> langPair = TranslateAction.getLangPair(project);
            boolean autoDetect = TranslateAction.isAutoDetect(project);

            List<String>
                translated =
                new YandexTranslator().translate(preparedText, langPair, autoDetect);

            if (translated != null) {
                handler.handleResult(editor, translated);
            } else {
                handler.handleError(editor);
            }
        }
    }

    private static String prepareTextToTranslation(String text) {
        String preparedText = arrayToString(text.split(CAMEL_CASE_SPLIT_PATTERN));
        preparedText = arrayToString(preparedText.split(UNDERSCORE_SPLIT_PATTERN));

        return preparedText;
    }

    private static String arrayToString(String[] splitted) {
        if (splitted.length == 1) {
            return splitted[0];
        }

        StringBuilder builder = new StringBuilder();

        for (String word : splitted) {
            builder.append(word).append(WORD_CONJUNCTION);
        }
        return builder.toString();
    }
}
