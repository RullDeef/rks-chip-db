package ru.spacecorp.mobdekbkp.web.suppliers;

import com.company.reviews.web.rating.Ratingframe;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.mobdekbkp.entity.Suppliers;

import javax.inject.Inject;
import java.util.UUID;

public class SuppliersBrowse extends AbstractLookup {

    @Inject
    private GroupDatasource<Suppliers, UUID> suppliersCollectionDs;

    @Override
    public void ready() {
        suppliersCollectionDs.addItemChangeListener(e -> {
            if (e.getItem() == null)
                return;
        });
    }
}