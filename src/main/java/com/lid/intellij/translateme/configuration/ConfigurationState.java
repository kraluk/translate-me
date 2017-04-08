package com.lid.intellij.translateme.configuration;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import static com.lid.intellij.translateme.configuration.ConfigurationState.COMPONENT_NAME;

@State(
        name = COMPONENT_NAME,
        storages = {@Storage("tme-configuration-state.xml")}
)
public class ConfigurationState implements PersistentStateComponent<ConfigurationState> {
    public static final String COMPONENT_NAME = "Translate.ConfigurationState";

    private String langFrom = "en";
    private String langTo = "pl";

    private boolean autoDetect = false;

    private ConfigurationState() {
    }

    public String getFrom() {
        return langFrom;
    }

    public String getTo() {
        return langTo;
    }

    public boolean isAutoDetect() {
        return autoDetect;
    }

    public void setAutoDetect(boolean autoDetect) {
        this.autoDetect = autoDetect;
    }

    public void setLangPair(final String from, final String to) {
        langFrom = from;
        langTo = to;
    }

    @Override
    public ConfigurationState getState() {
        return this;
    }

    @Override
    public void loadState(ConfigurationState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static ConfigurationState getInstance() {
        return ServiceManager.getService(ConfigurationState.class);
    }
}
