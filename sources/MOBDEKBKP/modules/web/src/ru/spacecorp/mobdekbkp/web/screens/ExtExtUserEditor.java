package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.data.Datasource;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.iovchinnikov.notificationsusers.gui.extuser.ExtUserEditor;
import ru.spacecorp.mobdekbkp.entity.Company;

import javax.inject.Inject;

public class ExtExtUserEditor extends ExtUserEditor {

    @Inject private LookupPickerField pfCompany;
    @Inject private DataManager dataManager;

    private Datasource<ExtUser> d;

    @Override
    public void ready() {
        super.ready();
        d = (Datasource<ExtUser>) getDsContext().get("userDs");
        if (d == null) return;

        LoadContext<Company> ctx = LoadContext.create(Company.class);
        ctx.setQuery(LoadContext.createQuery("select e from mobdekbkp$Company e where e.id = :param").setParameter("param", d.getItem().getCompanyId()));
        Company c = dataManager.load(ctx);
        pfCompany.setValue(c);
    }

    @Override
    protected boolean preClose(String actionId) {
        if (d == null) return super.preClose(actionId);
        if (pfCompany.getValue() == null) return super.preClose(actionId);
        LoadContext<Company> ctx = LoadContext.create(Company.class);
        ctx.setQuery(LoadContext.createQuery("select e from mobdekbkp$Company e where e.id = :param").setParameter("param", ((Company) pfCompany.getValue()).getId()));
        Company c = dataManager.load(ctx);

        if (c == null)return super.preClose(actionId);
        d.getItem().setCompanyId(c.getId());
        d.getItem().setCompanyRef(c.getSmartName());
        return super.preClose(actionId);
    }
}