package ru.spacecorp.mobdekbkp.web.applicationnewtyponominaldev;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDev;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDevStatus;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.Map;

public class ApplicationNewTyponominalDevBrowse extends AbstractLookup {
    @Inject
    private GroupTable<ApplicationNewTyponominalDev> applicationNewTyponominalDevsTable;

    @Inject
    private UserSession userSession;

    @Inject
    private GroupDatasource applicationNewTyponominalDevsDs;

    @Override
    public void init(Map<String, Object> params) {
        applicationNewTyponominalDevsDs.setQuery(String.format("select e from mobdekbkp$ApplicationNewTyponominalDev e" +
                " where e.createdBy in (select e.login from notificationsusers$ExtUser e where e.companyRef in " +
                "(select e.companyRef FROM notificationsusers$ExtUser e where e.login= '%s'))", userSession.getUser().getLogin()));
        if (userSession.getUser().getUserRoles().stream().anyMatch(userRole -> userRole.getRole().getName().contentEquals(PublicConstants.SYS_ROLE_GNIO))) {
            applicationNewTyponominalDevsDs.setQuery(String.format("SELECT e FROM mobdekbkp$ApplicationNewTyponominalDev e where " +
                    "((e.status='declined') or (e.status='ready') or (e.status='close')) or (e.createdBy in (select e.login from notificationsusers$ExtUser e where e.companyRef in " +
                    "(select e.companyRef FROM notificationsusers$ExtUser e where e.login= '%s')))", userSession.getUser().getLogin()));
        }
        applicationNewTyponominalDevsDs.refresh();

        ExtUser currentUser = (ExtUser) userSession.getUser();
        if (currentUser != null) {
            if (currentUser.getCompanyRef() == null) {
                showNotification(getMessage("noCompanyWarning"), NotificationType.WARNING);
            }
        } else {
            throw new RuntimeException("Something went wrong loading current Extended user! (AppNewTnDevBr.java:62)");
        }

        applicationNewTyponominalDevsTable.addAction(new RemoveAction(applicationNewTyponominalDevsTable) {
            @Override
            protected boolean isApplicable() {
                boolean applicable = super.isApplicable();
                if (applicable)
                    for (ApplicationNewTyponominalDev item : applicationNewTyponominalDevsTable.getSelected())
                        if (item.getStatus() != ApplicationNewTyponominalDevStatus.inDev)
                            return false;
                return applicable;
            }
        });
        applicationNewTyponominalDevsTable.addAction(new EditAction(applicationNewTyponominalDevsTable) {
            @Override
            protected boolean isApplicable() {
                boolean applicable = super.isApplicable();
                if (applicable)
                    for (ApplicationNewTyponominalDev item : applicationNewTyponominalDevsTable.getSelected())
                        if (item.getStatus() != ApplicationNewTyponominalDevStatus.inDev)
                            this.setCaption(getMessage("ready-edit"));
                        else
                            this.setCaption(getMessage("edit"));
                return applicable;
            }
        });
        applicationNewTyponominalDevsTable.addAction(new CreateAction(applicationNewTyponominalDevsTable) {
            @Override
            protected boolean isPermitted() {
                return currentUser.getCompanyRef() != null;
            }
        });
    }


}