package ru.spacecorp.documents.web.document;

import com.haulmont.cuba.gui.components.AbstractLookup;

import javax.inject.Inject;
import java.util.*;

public class DocumentBrowse extends AbstractLookup {
    @Inject
    private DocumentsFrame docsFrame;

    @Override
    public void init(Map<String, Object> params) {
        docsFrame.initializer()
                .usePreset(DocumentInitKey.Preset.EDIT_ACTION)
                .init();
        docsFrame.init(params);
        initParams(params);
    }

    private void initParams(Map<String, Object> params) {
        if (params.containsKey(DocumentInitKey.SET_CAPTION)) {
            Object caption = params.get(DocumentInitKey.SET_CAPTION);
            if (caption.getClass() != String.class)
                throw new RuntimeException("Init parameter \"SET_CAPTION\" has an incorrect format. Expected String.");

            setCaption((String) caption);
        }
    }



}