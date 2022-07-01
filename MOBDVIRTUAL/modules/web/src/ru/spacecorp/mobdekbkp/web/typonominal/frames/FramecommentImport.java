package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.util.Map;

public class FramecommentImport extends AbstractFrame implements ImportFrame {

    @Inject
    private Metadata metadata;
    @Inject
    private CommentBrowse comments;
    @Inject
    private UserSession userSession;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private VBoxLayout vbMain;
    private ImportDevice importDevice;
    private FramecommentImport thisframe;
    private Label noDsLabel;
    private String labelValue;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
    }

    @Override
    public void initFrame(ImportDevice importDevice) {
        if (importDevice == null)
            throw new RuntimeException("Unexpectedly importDevice in TnBasicInfo frame is null");
        this.importDevice = importDevice;
        String dbName = metadata.getSession().getClassNN(importDevice.getClass()).getName();
        comments.initialize(false)
                .setCurrentUser(userSession.getUser())
                .setCurrentEntityId(importDevice.getId())
                .setCurrentEntityName(dbName)
                .applyAndShow();
        noDsLabel.setVisible(false);
        comments.setVisible(true);
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        comments.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }
}