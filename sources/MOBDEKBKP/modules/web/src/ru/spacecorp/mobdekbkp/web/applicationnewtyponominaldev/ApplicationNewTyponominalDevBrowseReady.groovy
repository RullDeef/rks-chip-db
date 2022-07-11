package ru.spacecorp.mobdekbkp.web.applicationnewtyponominaldev

import com.haulmont.cuba.gui.components.AbstractLookup
import com.haulmont.cuba.gui.components.GroupTable
import com.haulmont.cuba.gui.components.actions.RemoveAction
import com.haulmont.cuba.security.entity.UserRole
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalDev
import ru.spacecorp.mobdekbkp.web.PublicConstants

import javax.inject.Inject
import java.util.function.Predicate

class ApplicationNewTyponominalDevBrowseReady extends AbstractLookup {

    @Inject
    private GroupTable<ApplicationNewTyponominalDev> applicationNewTyponominalDevsTable

    @Override
    void init(Map<String, Object> params) {
        super.init(params)
        applicationNewTyponominalDevsTable.addAction(new RemoveAction(applicationNewTyponominalDevsTable){
            @Override
            protected boolean isApplicable() {
                super.isApplicable()
                return userSession.getUser().getUserRoles().stream()
                .anyMatch(new Predicate<UserRole>() {
                    @Override
                    boolean test(UserRole userRole) {
                        return userRole.getRole().getName().contentEquals(PublicConstants.SYS_ROLE_GNIO)
                    }
                })
            }
        })
    }
}