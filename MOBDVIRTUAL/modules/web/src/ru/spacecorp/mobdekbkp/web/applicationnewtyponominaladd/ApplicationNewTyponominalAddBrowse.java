package ru.spacecorp.mobdekbkp.web.applicationnewtyponominaladd;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAdd;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.Map;

public class ApplicationNewTyponominalAddBrowse extends AbstractLookup {

    @Inject
    private GroupTable<ApplicationNewTyponominalAdd> applicationNewTyponominalAddsTable;

    @Inject
    GroupDatasource applicationNewTyponominalAddsDs;

    @Inject
    private UserSession userSession;

    @Override
    public void init(Map<String, Object> params) {
        applicationNewTyponominalAddsDs.setQuery(String.format("select e from mobdekbkp$ApplicationNewTyponominalAdd e" +
                " where e.createdBy in (select o.login from notificationsusers$ExtUser o where o.companyRef in " +
                "(select r.companyRef FROM notificationsusers$ExtUser r where r.login= '%s'))", userSession.getUser().getLogin()));
        if (userSession.getUser().getUserRoles().stream().anyMatch(userRole -> userRole.getRole().getName().contentEquals(PublicConstants.SYS_ROLE_GNIO))) {
            applicationNewTyponominalAddsDs.setQuery(String.format("SELECT e FROM mobdekbkp$ApplicationNewTyponominalAdd e where " +
                    "(e.status<>'created') or (e.createdBy in (select e.login from notificationsusers$ExtUser e where e.companyRef in " +
                    "(select e.companyRef FROM notificationsusers$ExtUser e where e.login= '%s')))", userSession.getUser().getLogin()));
        }
        applicationNewTyponominalAddsDs.refresh();
        applicationNewTyponominalAddsTable.addAction(new RemoveAction(applicationNewTyponominalAddsTable) {
            @Override
            protected boolean isApplicable() {
                boolean applicable = super.isApplicable();
                if (applicable)
                    for (ApplicationNewTyponominalAdd item : applicationNewTyponominalAddsTable.getSelected())
                        if (!item.getStatus().equals(ApplicationNewTyponominalAddStatus.created)) return false;
                return applicable;
            }

        });
        applicationNewTyponominalAddsTable.addAction(new EditAction(applicationNewTyponominalAddsTable) {
            @Override
            protected boolean isApplicable() {
                boolean applicable = super.isApplicable();
                if (applicable)
                    for (ApplicationNewTyponominalAdd item : applicationNewTyponominalAddsTable.getSelected())
                        if (!item.getStatus().equals(ApplicationNewTyponominalAddStatus.created))
                            this.setCaption(getMessage("readOnlyEdit"));
                        else this.setCaption(getMessage("edit"));
                return applicable;
            }

        });
    }

}
