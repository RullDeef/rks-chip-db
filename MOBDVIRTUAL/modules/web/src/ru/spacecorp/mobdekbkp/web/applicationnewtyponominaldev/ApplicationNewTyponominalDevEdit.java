package ru.spacecorp.mobdekbkp.web.applicationnewtyponominaldev;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.service.AddEntriesService;
import ru.spacecorp.mobdekbkp.web.PublicConstants;
import ru.spacecorp.mobdekbkp.web.applicationcommonentry.ApplicationCommonEntryEdit;

import javax.inject.Inject;
import java.util.*;

public class ApplicationNewTyponominalDevEdit extends AbstractEditor<ApplicationNewTyponominalDev> {

    @Inject
    private TextField classNameField;
    @Inject
    private TextField statusField;
    @Inject
    private TextArea eventArea;
    @Inject
    private TextArea characteristicsArea;
    @Inject
    private TextArea possibilityArea;
    @Inject
    private TextArea prototypeArea;
    @Inject
    private PickerField documentField;
    @Inject
    private PickerField commonApplicationField;
    @Inject
    private Button btnReady;
    @Inject
    private Button btnToCommon;
    @Inject
    private Button btnAddtoComm;
    @Inject
    private UserSession userSession;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private AddEntriesService addEntriesService;

    @Override
    public void init(Map<String, Object> params) {
    }


    private void setAllActive(boolean isEditable) {
        Field[] allFields = {statusField, classNameField, eventArea,
                characteristicsArea, possibilityArea, prototypeArea,
                documentField, commonApplicationField};
        for (Field f : allFields) {
            f.setEditable(isEditable);
        }
        btnReady.setVisible(isEditable);
        btnToCommon.setVisible(isEditable);
        btnAddtoComm.setVisible(isEditable);
    }

    @Override
    public void ready() {
        ApplicationNewTyponominalDevStatus currentStatus = getItem().getStatus();
        boolean isGnio = false;
        for (UserRole r : userSession.getUser().getUserRoles()) {
            if (r.getRole().getName().equals(PublicConstants.SYS_ROLE_GNIO))
                isGnio = true;
        }

        if (currentStatus != ApplicationNewTyponominalDevStatus.inDev) {
            setAllActive(false);
        } else {
            btnToCommon.setVisible(false);
            btnAddtoComm.setVisible(false);
        }

        if (currentStatus == ApplicationNewTyponominalDevStatus.ready && isGnio) {
            btnToCommon.setVisible(true);
            btnAddtoComm.setVisible(true);
        } else {
            btnToCommon.setVisible(false);
            btnAddtoComm.setVisible(false);
        }

        if (getItem().getCommonApplication() != null) {
            commonApplicationField.setVisible(true);
        }
    }

    @Override
    protected void initNewItem(ApplicationNewTyponominalDev item) {
        item.setStatus(ApplicationNewTyponominalDevStatus.inDev);
    }

    public void onBtnReadyClick() {
        //TODO Send message to GNIO with a link to an application
        getItem().setStatus(ApplicationNewTyponominalDevStatus.ready);
        setAllActive(false);
    }

    public void onBtnToCommonClick() {
        //TODO проверить документ 06.02
        ApplicationNewTyponominalDev currentApp = getItem();
        Map<String, Object> map = new HashMap<>();
        map.put("src", this);
        map.put("srcItem", getItem());

        ApplicationCommonEntry newEntry = metadata.create(ApplicationCommonEntry.class);
        newEntry.setClassName(currentApp.getClassName());
        newEntry.setCharacteristics(currentApp.getCharacteristics());
        newEntry.setEvents(currentApp.getEvent());
        newEntry.setPossibility(currentApp.getPossibility());
        newEntry.setPrototype(currentApp.getPrototype());
        if (getItem().getDocument() != null) {
            newEntry.setDocuments(Collections.singletonList(getItem().getDocument()));
        }

        ApplicationNewDevEntry parentEntry;
        if (newEntry.getCreatedBy() == null) {
            parentEntry = metadata.create(ApplicationNewDevEntry.class);
            parentEntry.setApplication(currentApp);
            parentEntry.setApplicationCommonEntry(newEntry);
        } else {
            parentEntry = addEntriesService.addParentEntry(newEntry, currentApp);
        }

        if (newEntry.getParents() != null) {
            newEntry.getParents().add(parentEntry);
        } else {
            List<ApplicationNewDevEntry> parentsEntryList = new LinkedList<>();
            parentsEntryList.add(parentEntry);
            newEntry.setParents(parentsEntryList);
        }

        final ApplicationCommonEntryEdit entryEditor = (ApplicationCommonEntryEdit) frame.openEditor("mobdekbkp$ApplicationCommonEntry.edit",
                newEntry,
                WindowManager.OpenType.NEW_TAB,
                map);

        entryEditor.addCloseListener(actionId -> {
            if (actionId.equals(COMMIT_ACTION_ID)) {
                getItem().setStatus(ApplicationNewTyponominalDevStatus.isInCommon);
                getItem().setCommonApplication(newEntry);
                this.commonApplicationField.setVisible(true);
                this.ready();
            }
        });
    }

    public void onBtnAddtoCommClick() {
        Map<String, Object> map = new HashMap<>();
        map.put("src", this);
        map.put("srcItem", getItem());
        frame.openLookup("mobdekbkp$ApplicationCommonEntry.browse",
                items -> {
                    ApplicationCommonEntry selected = (ApplicationCommonEntry) items.toArray()[0];
                    getItem().setCommonApplication(selected);
                    ApplicationNewDevEntry parentEntry = addEntriesService.addParentEntry(selected, getItem());
                    selected.getParents().add(parentEntry);
                    if (selected.getDocuments() != null)
                        selected.getDocuments().add(getItem().getDocument());
                    else
                        selected.setDocuments(Collections.singletonList(getItem().getDocument()));

                },
                WindowManager.OpenType.THIS_TAB,
                map)
                .addCloseListener(actionId -> {
                    getItem().setStatus(ApplicationNewTyponominalDevStatus.isInCommon);
                    commonApplicationField.setVisible(true);
                });
    }
}