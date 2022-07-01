package ru.spacecorp.mobdekbkp.web.applicationnewtyponominaladd;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAdd;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.Map;

public class ApplicationNewTyponominalAddBrowseReady extends AbstractLookup {

    @Inject
    private GroupTable<ApplicationNewTyponominalAdd> applicationNewTyponominalAddsTable;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        applicationNewTyponominalAddsTable.addAction(new RemoveAction(applicationNewTyponominalAddsTable) {
            @Override
            protected boolean isApplicable() {
                super.isApplicable();
                return userSession.getUser().getUserRoles().stream()
                        .anyMatch(userRole -> userRole.getRole().getName().contentEquals(PublicConstants.SYS_ROLE_GNIO));
            }
        });
    }
}