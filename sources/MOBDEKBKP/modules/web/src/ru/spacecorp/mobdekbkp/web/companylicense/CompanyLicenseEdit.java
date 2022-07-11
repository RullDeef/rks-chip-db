package ru.spacecorp.mobdekbkp.web.companylicense;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.web.gui.components.WebTextField;
import ru.spacecorp.mobdekbkp.entity.CompanyLicense;

import javax.inject.Inject;

public class CompanyLicenseEdit extends AbstractEditor<CompanyLicense> {
    @Inject private FieldGroup fieldGroup;

    private boolean isNew = false;

    private static final int charSize = 12;

    @Override
    protected void initNewItem(CompanyLicense item) {
        super.initNewItem(item);
        isNew = true;
    }

    @Override
    public void ready() {
        fieldGroup.setEditable(isNew);
        //setFieldSizes();
        super.ready();
    }

    @Override
    protected void postInit() {
        /*fieldGroup.getDatasource().addItemPropertyChangeListener(e -> {
            setFieldSizes();
        });*/
        if(getItem().getCompany()!=null){
            FieldGroup.FieldConfig fieldCompany = fieldGroup.getField("company");
            fieldCompany.setEditable(false);
        }
        super.postInit();
    }

    private void setFieldSizes(){
        FieldGroup.FieldConfig fieldType = fieldGroup.getField("type");
        FieldGroup.FieldConfig fieldCompany = fieldGroup.getField("company");
        FieldGroup.FieldConfig fieldNumber = fieldGroup.getField("number");
        FieldGroup.FieldConfig fieldDocument = fieldGroup.getField("document");

        String type = null;
        if ((fieldGroup.getDatasource().getItem().getValue("type")) != null) {
            type = ((Entity) fieldGroup.getDatasource().getItem().getValue("type")).getValue("name");
        }
        setSize(fieldType,type);

        String company = null;
        if ((fieldGroup.getDatasource().getItem().getValue("company")) != null) {
            company = ((Entity) fieldGroup.getDatasource().getItem().getValue("company")).getValue("smartName");
        }
        setSize(fieldCompany,company);

        String number = null;
        number = fieldGroup.getDatasource().getItem().getValue("number");
        setSize(fieldNumber,number);

        String document = null;
        if ((fieldGroup.getDatasource().getItem().getValue("document")) != null) {
            document = ((Entity)fieldGroup.getDatasource().getItem().getValue("document")).getValue("name");
        }
        setSize(fieldDocument,document);
    }

    private void setSize(FieldGroup.FieldConfig fieldConfig,String value){
        int additConst=4;
        if (value != null) {
            if (fieldConfig != null) {
                if(!fieldConfig.getComponent().getClass().getName().contains(WebTextField.class.getName())){
                    additConst+=3;
                }
                if(value.length()<20){
                    fieldConfig.setWidth((value.length()+additConst) * charSize + "px");
                }
                else {
                    fieldConfig.setWidth(value.length() * charSize + "px");
                }
            }
        }
    }

}