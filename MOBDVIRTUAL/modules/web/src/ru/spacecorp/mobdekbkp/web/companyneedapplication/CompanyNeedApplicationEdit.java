package ru.spacecorp.mobdekbkp.web.companyneedapplication;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Frame;
import ru.spacecorp.mobdekbkp.entity.CompanyNeedApplication;
import ru.spacecorp.mobdekbkp.web.companyneed.CompanyNeedBrowseFrame;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CompanyNeedApplicationEdit extends AbstractEditor<CompanyNeedApplication> {

    @Inject
    private DataManager dataManager;

    @Inject
    private CompanyNeedBrowseFrame companyNeedFrame;

    private final static String MAX_NUMBER = "maxNumber";
    private final static int FIRST_NUMBER = 1;

    @Override
    protected void initNewItem(CompanyNeedApplication item) {
        super.initNewItem(item);
        companyNeedFrame.enableCreate(false);
        ValueLoadContext valueLoadContext = ValueLoadContext.create();
        ValueLoadContext.Query query = ValueLoadContext.createQuery("select MAX(e.number) from mobdekbkp$CompanyNeedApplication e");
        valueLoadContext.setQuery(query);
        valueLoadContext.addProperty(MAX_NUMBER);
        valueLoadContext.setSoftDeletion(false);
        List<KeyValueEntity> intList = dataManager.loadValues(valueLoadContext);
        if (intList.size() != 0) {
            KeyValueEntity valueEntity = intList.get(0);
            if (valueEntity != null) {
                Integer maxNumber = valueEntity.getValue(MAX_NUMBER);
                if (maxNumber != null) {
                    item.setNumber(++maxNumber);
                } else {
                    item.setNumber(FIRST_NUMBER);
                }
            }
        }
    }

    @Override
    protected void postInit() {
        super.postInit();
        companyNeedFrame.initFrame(getItem().getId());
    }
}