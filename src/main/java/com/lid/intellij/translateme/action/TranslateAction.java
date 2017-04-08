package com.lid.intellij.translateme.action;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Pair;
import com.intellij.ui.awt.RelativePoint;
import com.lid.intellij.translateme.action.handler.ActionHandler;
import com.lid.intellij.translateme.configuration.ConfigurationState;
import com.lid.intellij.translateme.gui.ResultDialog;

import java.awt.*;
import java.util.List;

public class TranslateAction extends EditorAction {
    private static final Pair<String, String> DEFAULT_LANGUAGE_PAIR = new Pair<>("en", "pl");

    public TranslateAction() {
        super(new TranslateHandler(new PopupActionHandler()));
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

    private static class PopupActionHandler implements ActionHandler {

        @Override
        public void handleResult(Editor editor, List<String> translated) {
            ResultDialog resultDialog = new ResultDialog("Translated", translated);
            resultDialog.setVisible(true);
        }

        @Override
        public void handleError(Editor editor) {
            showErrorMessage(editor, "Failed to translate");
        }

        private void showErrorMessage(Editor editor, String message) {
            BalloonBuilder builder =
                    JBPopupFactory.getInstance().createHtmlTextBalloonBuilder("hello", MessageType.ERROR, null);
            Balloon balloon = builder.createBalloon();
            balloon.setTitle(message);
            CaretModel caretModel = editor.getCaretModel();
            Point point = editor.visualPositionToXY(caretModel.getVisualPosition());
            RelativePoint where = new RelativePoint(point);
            balloon.show(where, Balloon.Position.below);
        }
    }
}
