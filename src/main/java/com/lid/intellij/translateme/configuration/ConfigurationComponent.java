package com.lid.intellij.translateme.configuration;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.lid.intellij.translateme.gui.TranslationConfigurationForm;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class ConfigurationComponent implements ProjectComponent, Configurable {
    private static final Logger log = Logger.getInstance(ConfigurationComponent.class);

    static final String COMPONENT_NAME = "Translate.ConfigurationComponent";

    private TranslationConfigurationForm form;

    @Override
    public boolean isModified() {
        return form != null && form.isModified(ConfigurationState.getInstance());
    }

    @Override
    public void projectOpened() {
        log.debug("Project opened.");
    }

    @Override
    public void projectClosed() {
        log.debug("Project closed.");
    }

    @Override
    @NotNull
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    public String getDisplayName() {
        return "TranslateMe";
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        if (form == null) {
            form = new TranslationConfigurationForm();
        }

        return form.getRootPanel();
    }

    /**
     * Stores settings from form to configuration bean.
     */
    @Override
    public void apply() throws ConfigurationException {
        if (form != null) {
            form.save(ConfigurationState.getInstance());
        }
    }

    /**
     * Restores form values from configuration.
     */
    @Override
    public void reset() {
        if (form != null) {
            form.load(ConfigurationState.getInstance());
        }
    }

    /**
     * Disposes UI resource.
     */
    @Override
    public void disposeUIResources() {
        form = null;
    }
}
