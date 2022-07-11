package com.spacecorp.rulesmodule.web.rulescript;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.SourceCodeEditor;
import com.spacecorp.rulesmodule.entity.RuleScript;

import java.util.Map;

public class RuleScriptEdit extends AbstractEditor<RuleScript> {
    private SourceCodeEditor sourceCodeEditor=null;

    @Override
    public void init(Map<String, Object> params) {
        sourceCodeEditor = (SourceCodeEditor) getComponent("sourceCodeEditor");
        sourceCodeEditor.setHandleTabKey(true);
        sourceCodeEditor.setHighlightActiveLine(true);
        sourceCodeEditor.setMode(SourceCodeEditor.Mode.Groovy);
        sourceCodeEditor.addValueChangeListener(e -> getItem().setScript(sourceCodeEditor.getValue()));
    }

    @Override
    public void postInit() {
        sourceCodeEditor.setValue(getItem().getScript());
    }
}