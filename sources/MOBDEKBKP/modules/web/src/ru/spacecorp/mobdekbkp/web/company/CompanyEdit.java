package ru.spacecorp.mobdekbkp.web.company;

import com.company.reviews.service.ReviewService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;

import javax.inject.Inject;
import java.util.Map;

public class CompanyEdit extends AbstractEditor<Company> {

    @Inject private CommentBrowse comments;
    @Inject private Metadata metadata;
    @Inject private UserSession userSession;
    @Inject private FieldGroup companyFieldGroup;
    @Inject private ButtonsPanel licensesButtonsPanel;
    @Inject private ButtonsPanel typesButtonPanel;
    @Inject private ReviewService reviewService;
    @Inject private Image cmpnLogo;
    @Inject private Datasource<Company> companyDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        if (params.get("noedit") != null) {
            if (params.get("noedit").equals(true)) {
                companyFieldGroup.setEditable(false);
                licensesButtonsPanel.setEnabled(false);
                typesButtonPanel.setEnabled(false);
            }
        }

        companyDs.addItemPropertyChangeListener(event -> {
            if ("logo".equals(event.getProperty()))
                displayImage();
        });

    }

    @Override
    protected void initNewItem(Company item) {
        super.initNewItem(item);
        item.setIsMilitary(ExtBoolean.notSet);
    }

    @Override
    public void ready() {
        super.ready();
        String dbName = metadata.getSession().getClassNN(getItem().getClass()).getName();
        comments.initialize(false)
                .setFrameVisible(true)
                .setCurrentUser(userSession.getUser())
                .setCurrentEntityId(getItem().getId())
                .setCurrentEntityName(dbName)
                .applyAndShow();
        super.ready();
        displayImage();
    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        if ((getItem().getTypes() == null) || (getItem().getTypes().size() < 1)) {
            errors.add(getMessage("noType"));
        }
    }

    private void displayImage() {
        if (getItem().getLogo() != null) {
            cmpnLogo.setSource(FileDescriptorResource.class).setFileDescriptor(getItem().getLogo());
            cmpnLogo.setVisible(true);
        } else {
            cmpnLogo.setVisible(false);
        }
    }
}