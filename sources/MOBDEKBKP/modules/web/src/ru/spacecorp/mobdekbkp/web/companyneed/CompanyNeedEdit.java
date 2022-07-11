package ru.spacecorp.mobdekbkp.web.companyneed;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class CompanyNeedEdit extends AbstractEditor<CompanyNeed> {
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;

    @Inject
    private FieldGroup fieldGroup;

    private boolean isNew = false;

    private UUID applicationId = null;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        Object applicationIdObj = params.get("applicationId");
        if (applicationIdObj != null) {
            this.applicationId = (UUID) applicationIdObj;
        }
    }

    @Override
    protected void postInit() {
        super.postInit();
        if (applicationId != null) {
            CompanyNeedApplication companyNeedApplication = dataManager.load(CompanyNeedApplication.class).id(applicationId).one();
            getItem().setCompanyNeedApplication(companyNeedApplication);
        }
    }

    @Override
    public void ready() {
        super.ready();
        if (isNew) {
            LoadContext<Company> ctx = LoadContext.create(Company.class);
            ctx.setQuery(LoadContext.createQuery("select cmp from mobdekbkp$Company cmp where cmp.id = :ref")
                    .setParameter("ref", ((ExtUser) userSession.getUser()).getCompanyId()));
            Company company = dataManager.load(ctx);
            getItem().setCompany(company);
            if (company == null) {
                showNotification(getMessage("NoCompany"), NotificationType.WARNING);
                close(Window.CLOSE_ACTION_ID, true);
            }
            FieldGroup.FieldConfig companyField = fieldGroup.getField("company");
            if (companyField != null) {
                if (userSession.getUser().getUserRoles().stream().anyMatch(userRole -> {
                    String roleName = userRole.getRole().getName();
                    return roleName.contentEquals(PublicConstants.SYS_ROLE_GNIO) || roleName.contentEquals(PublicConstants.SYS_ROLE_GKRK);
                })) {
                    companyField.setEditable(true);
                }
            }
        }
    }

    @Override
    protected void initNewItem(CompanyNeed item) {
        super.initNewItem(item);
        item.setStatus(CompanyNeedStatus.created);
        FieldGroup.FieldConfig statusFieldConfig = fieldGroup.getField("status");
        if (statusFieldConfig != null) {
            statusFieldConfig.setVisible(false);
        }
        isNew = true;
    }

    @Override
    protected boolean preCommit() {
        LoadContext<Typonominal> ctx = LoadContext.create(Typonominal.class);
        ctx.setQuery(LoadContext.createQuery("select tn from mobdekbkp$Typonominal tn where tn.id = :ref")
                .setParameter("ref", getItem().getTyponominal()))
                .setView("typonominal-view");
        getItem().setTyponominal(dataManager.load(ctx));

        return super.preCommit();
    }
}