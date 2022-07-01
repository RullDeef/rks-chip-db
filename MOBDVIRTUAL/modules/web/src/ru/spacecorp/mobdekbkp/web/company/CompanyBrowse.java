package ru.spacecorp.mobdekbkp.web.company;

import com.company.reviews.web.rating.Ratingframe;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.mobdekbkp.entity.Company;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

public class CompanyBrowse extends AbstractLookup {

    @Inject
    private GroupDatasource<Company, UUID> companiesDs;
    @Inject
    private CommentBrowse comments;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;
    @Inject
    private Ratingframe ratingFrame;

    @Override
    public void ready() {
        companiesDs.addItemChangeListener(e -> {
            if (e.getItem() == null)
                return;
        });

        comments.initialize(false)
                .setFrameVisible(false);
        companiesDs.addItemChangeListener(e -> {
            Company item = e.getItem();
            if (item == null) return;
            String dbName = metadata.getSession().getClassNN(item.getClass()).getName();
            comments.initialize(false)
                    .setFrameVisible(true)
                    .setCurrentUser(userSession.getUser())
                    .setCurrentEntityId(item.getId())
                    .setCurrentEntityName(dbName)
                    .applyAndShow();
        });
    }
}