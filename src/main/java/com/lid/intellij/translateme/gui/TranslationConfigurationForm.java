package com.lid.intellij.translateme.gui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.lid.intellij.translateme.configuration.ConfigurationState;
import com.lid.intellij.translateme.rest.RestServiceInvoker;
import com.lid.intellij.translateme.yandex.YandexService;
import com.lid.intellij.translateme.yandex.response.AvailableLanguages;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import static com.lid.intellij.translateme.common.ApplicationConstant.DEFAULT_UI_LANGUAGE;

public class TranslationConfigurationForm {
    private static final Logger log = Logger.getInstance(TranslationConfigurationForm.class);

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;

    private final YandexService yandexService;

    private JPanel rootPanel;
    private ComboBox<String> comboBoxFrom;
    private ComboBox<String> comboBoxTo;
    private Checkbox autoDetectCheckbox;
    private JLabel label;

    private AvailableLanguages availableLanguages;

    public TranslationConfigurationForm() {
        initializeRootPanel();
        initializeAutoDetectCheckbox();
        initializeConfigurationComponents();

        yandexService = new YandexService(new RestServiceInvoker());
    }

    private void initializeRootPanel() {
        rootPanel = new JPanel();
        rootPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        rootPanel.setLayout(new GridBagLayout());
    }

    private void initializeAutoDetectCheckbox() {
        autoDetectCheckbox = new Checkbox("Auto-detect");
        //autoDetect.setState(true);
    }

    private void initializeConfigurationComponents() {
        comboBoxFrom = new ComboBox<>();
        comboBoxTo = new ComboBox<>();
        label = new JLabel("Select translation:");

        initializeComboBox(comboBoxFrom);
        initializeComboBox(comboBoxTo);

        JPanel languagesPanel = createLanguagesPanel();

        GridBagConstraints languageConstraints = new GridBagConstraints();

        languageConstraints.anchor = GridBagConstraints.EAST;
        languageConstraints.fill = GridBagConstraints.EAST;
        languageConstraints.gridx = 0;
        languageConstraints.gridy = 0;

        GridBagConstraints autoDetectConstraints = new GridBagConstraints();

        languageConstraints.anchor = GridBagConstraints.EAST;
        autoDetectConstraints.fill = GridBagConstraints.EAST;
        autoDetectConstraints.gridx = 0;
        autoDetectConstraints.gridy = 1;

        rootPanel.add(languagesPanel, languageConstraints);
        rootPanel.add(autoDetectCheckbox, autoDetectConstraints);
    }

    private void initializeComboBox(ComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.setModel(createModel());

        if (comboBox.getModel().getSize() > 0) {
            comboBox.setSelectedIndex(0);
        }
    }

    @NotNull
    private JPanel createLanguagesPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.add(label);
        panel.add(comboBoxFrom);
        panel.add(comboBoxTo);

        return panel;
    }

    private ComboBoxModel<String> createModel() {
        if (availableLanguages == null) {
            try {
                availableLanguages = yandexService.getLanguages(DEFAULT_UI_LANGUAGE);
            } catch (Exception e) {
                log.warn(e);

                availableLanguages = new AvailableLanguages(); // "null-object"
            }
        }

        List<String> items = new ArrayList<>();
        items.addAll(availableLanguages.getLangs().keySet());

        return new DefaultComboBoxModel<>(items.toArray(new String[]{}));
    }

    /**
     * Gets the root component of the form.
     *
     * @return root component of the form
     */
    public JComponent getRootPanel() {
        return rootPanel;
    }

    public void save(ConfigurationState state) {
        String selectedItemFrom = (String) comboBoxFrom.getSelectedItem();
        String selectedItemTo = (String) comboBoxTo.getSelectedItem();

        if (selectedItemFrom != null && selectedItemTo != null) {
            state.setLangPair(selectedItemFrom, selectedItemTo);
        }

        state.setAutoDetect(autoDetectCheckbox.getState());
    }

    public void load(ConfigurationState state) {
        comboBoxFrom.setSelectedItem(state.getFrom());
        comboBoxTo.setSelectedItem(state.getTo());
        autoDetectCheckbox.setState(state.isAutoDetect());
    }

    public boolean isModified(ConfigurationState state) {
        Object selectedFrom = comboBoxFrom.getSelectedItem();
        Object selectedTo = comboBoxTo.getSelectedItem();

        boolean autoChecked = autoDetectCheckbox.getState();

        final boolean fromChanged = selectedFrom != null && !selectedFrom.equals(state.getFrom());
        final boolean toChanged = selectedTo != null && !selectedTo.equals(state.getTo());
        final boolean detectChanged = autoChecked != state.isAutoDetect();

        return fromChanged || toChanged || detectChanged;
    }
}
