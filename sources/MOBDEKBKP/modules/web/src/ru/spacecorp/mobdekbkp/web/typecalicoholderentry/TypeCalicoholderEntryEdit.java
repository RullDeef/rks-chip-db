package ru.spacecorp.mobdekbkp.web.typecalicoholderentry;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.TypeCalicoholderEntry;

import javax.inject.Inject;
import java.util.Map;

public class TypeCalicoholderEntryEdit extends AbstractEditor<TypeCalicoholderEntry> {

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