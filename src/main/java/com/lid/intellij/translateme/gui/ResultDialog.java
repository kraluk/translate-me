package com.lid.intellij.translateme.gui;

import com.intellij.ide.BrowserUtil;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.components.labels.LinkListener;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.*;

public class ResultDialog extends JDialog implements ActionListener {
    private static final String TEXT_PATTERN = "%s. %s";

    public ResultDialog(String message, List<String> detail) {
        super(JOptionPane.getRootFrame(), message, true);

        final JPanel content = new JPanel();

        Box verticalBox = Box.createVerticalBox();

        long lineNumber = 1;

        for (String text : detail) {
            verticalBox.add(new JLabel(String.format(TEXT_PATTERN, lineNumber++, text)));
            verticalBox.add(Box.createVerticalGlue());
        }

        final LinkLabel showMoreLink = createLinkLabel();
        verticalBox.add(showMoreLink);

        content.add(verticalBox);

        getContentPane().add(content, BorderLayout.NORTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());

        addEscapeListener(this);
    }

    private static void addEscapeListener(final JDialog dialog) {
        ActionListener escListener = e -> dialog.setVisible(false);

        dialog.getRootPane().registerKeyboardAction(escListener,
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    @NotNull
    private LinkLabel createLinkLabel() {

        final LinkLabel<String>
            showMoreLink =
            new LinkLabel<>("Powered by Yandex.Translator", null);
        LinkListener<String>
            showMoreListener =
            (aSource, aLinkData) -> BrowserUtil.browse("http://translate.yandex.com/");
        showMoreLink.setListener(showMoreListener, null);
        return showMoreLink;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
    }
}