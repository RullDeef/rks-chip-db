package ru.spacecorp.mobdekbkp.web.optimizationbdservice;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.Company;

import javax.inject.Inject;
import java.util.UUID;

public class Companyscreen extends AbstractEditor {
    @Inject private Table<Company> tableCompany;
    @Inject private CollectionDatasource<Company, UUID> companiesDs;
    private Company company;

    public void onBtOkClick() {
        if(companiesDs.getItem()!=null){
        company = companiesDs.getItem();
            this.close("exit");
        }
        else showNotification("NotCompany");
    }

    public void onBtCancelClick() {
        this.close("exit");
    }

    public Company getCompany(){
        return company;
    }
}