package com.lid.intellij.translateme.action.handler;

import com.intellij.openapi.editor.Editor;

import java.util.List;

public interface ActionHandler {

    void handleResult(Editor editor, List<String> translated);

    void handleError(Editor editor);
}
