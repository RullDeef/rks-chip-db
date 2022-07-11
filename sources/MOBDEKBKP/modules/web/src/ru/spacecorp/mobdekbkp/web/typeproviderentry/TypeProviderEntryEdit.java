package ru.spacecorp.mobdekbkp.web.typeproviderentry;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.TypeProviderEntry;

import javax.inject.Inject;
import java.util.Map;

public class TypeProviderEntryEdit extends AbstractEditor<TypeProviderEntry> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if(params.get("noedit")!=null){
            if (params.get("noedit").equals(true)){
                fieldGroup.setEditable(false);
            }}
    }

}