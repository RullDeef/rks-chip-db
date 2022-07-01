package ru.spacecorp.mobdekbkp.web.suppliers;

import com.company.reviews.service.ReviewService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.mobdekbkp.entity.Suppliers;

import javax.inject.Inject;
import java.util.Map;

public class SuppliersEdit extends AbstractEditor<Suppliers> {
    @Inject
    private FieldGroup supplierfieldGroup;
    @Inject
    private Image cmpnLogo;
    @Inject
    private Datasource<Suppliers> suppliersDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        if (params.get("noedit") != null) {
            if (params.get("noedit").equals(true)) {
                supplierfieldGroup.setEditable(false);
            }
        }

        suppliersDs.addItemPropertyChangeListener(event -> {
            if ("logo".equals(event.getProperty()))
                displayImage();
        });

    }

    @Override
    protected void initNewItem(Suppliers item) {
        super.initNewItem(item);
    }

    @Override
    public void ready() {
        super.ready();
        displayImage();
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