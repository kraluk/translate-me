package com.lid.intellij.translateme.action.handler;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import com.lid.intellij.translateme.gui.ResultDialog;

import java.awt.*;
import java.util.List;

/**
 * TODO: please, comment me!
 *
 * @author lukasz
 */
public class PopupActionHandler implements ActionHandler {

    @Override
    public void handleResult(Editor editor, List<String> translated) {
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            ResultDialog resultDialog = new ResultDialog("Translated", translated);
            resultDialog.setVisible(true);
        });
    }

    @Override
    public void handleError(Editor editor) {
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> showErrorMessage(editor, "Failed to translate"));
    }

    private void showErrorMessage(Editor editor, String message) {
        BalloonBuilder builder =
            JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("hello", MessageType.ERROR, null);

        Balloon balloon = builder.createBalloon();
        balloon.setTitle(message);

        CaretModel caretModel = editor.getCaretModel();
        Point point = editor.visualPositionToXY(caretModel.getVisualPosition());
        RelativePoint where = new RelativePoint(point);

        balloon.show(where, Balloon.Position.below);
    }
}
