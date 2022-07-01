package ru.spacecorp.mobdekbkp.web.devicelistproject;

import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.gui.components.renderers.WebComponentRenderer;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;
import ru.iovchinnikov.notificationsusers.service.MessageService;
import ru.iovchinnikov.talks.entity.Comment;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.web.instruments.WorkbookCreator;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardFrame;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardScreen;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class DeviceListProjectEdit extends AbstractEditor<DeviceListProject> {
    private static final String PROCESS_CODE = "devlistprjapproval";                             //bpm

    @Inject
    private TextField textField;
    @Inject
    private GroupBoxLayout procActionsBox;
    @Inject
    private Datasource deviceListProjectDs;
    @Inject
    private Datasource additionsDs;
    @Inject
    private DataGrid<DeviceListProjectEntry> entriesDataGrid;
    @Inject
    private GroupBoxLayout groupBoxApproved;
    @Inject
    private ProcActionsFrame procActionsFrame;
    @Inject
    private GroupBoxLayout groupBoxTyponominal;
    @Inject
    private VBoxLayout vboxTableDevice;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;
    @Inject
    private CommentBrowse comments;
    @Inject
    private TnCardFrame cardFrame;
    @Inject
    private DataManager dataManager;
    @Inject
    private Messages messages;
    @Inject
    private CollectionDatasource procInstancesDs;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private ViewRepository viewRepository;
    @Inject
    private MessageService messageService;

    @Inject
    private GroupDatasource<DeviceListProjectEntry, UUID> entriesDs;

    @Inject
    private Button btAdd;
    @Inject
    private Button importBtn;
    @Inject
    private Button deleteBtn;
    @Inject
    private Button buttonEditor;
    @Inject
    private Button buttonApproved;
    @Inject
    private Button buttonAnnulled;
    @Inject
    private Button createBtn;
    @Inject
    private Button editBtn;
    @Inject
    private Button deleteAdditionBtn;
    @Inject
    private Button addButtonDocument;
    @Inject
    private Button excludeButtonDocument;
    @Inject
    private Button openProcBtn;
    @Inject
    private ExportDisplay exportDisplay;

    @Named("documentTableProject.create")
    private CreateAction createActionProject;
    @Inject
    private CommentBrowse comments_typonominal;

    private boolean readOnly = false;

    private final static int COLUMN_COUNT = 4;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        FunctionLib functionLib = new FunctionLib();
        entriesDataGrid.setCellDescriptionProvider((entity, columnId) -> functionLib.getAdditionMsg(entity));
        entriesDataGrid.addCellStyleProvider(functionLib::getStyleName);
        DataGrid.Column column = entriesDataGrid.addGeneratedColumn("msg", new DataGrid.ColumnGenerator<DeviceListProjectEntry, Component>() {
            @Override
            public Component getValue(DataGrid.ColumnGeneratorEvent<DeviceListProjectEntry> event) {
                return functionLib.getMsgField(event.getItem());
            }

            @Override
            public Class<Component> getType() {
                return Component.class;
            }
        });
        column.setRenderer(new WebComponentRenderer());

        Map<String, Object> frameparams = new HashMap<>();
        frameparams.put(TnCardFrame.NO_INIT_TN_LABEL, getMessage("noDsLabel"));
        frameparams.put(TnCardFrame.OPTION_PARAM, TnCardFrame.DEV_LIST);
        cardFrame.reinit(frameparams);
        if (params.get("readOnly") != null) {
            if ((boolean) params.get("readOnly")) {
                readOnly = true;
            }
        }
    }

    @Override
    protected void postInit() {

        com.haulmont.cuba.web.toolkit.ui.CubaGrid dataGrid1 = entriesDataGrid.unwrap(com.haulmont.cuba.web.toolkit.ui.CubaGrid.class);
        groupBoxTyponominal.setEnabled(false);

        dataGrid1.setSizeFull();
        entriesDataGrid.addItemClickListener(event -> {
            entriesDataGrid.setSelected(DeviceListProjectEntry.class.cast(entriesDataGrid.getDatasource().getItem(event.getItemId())));
            cardFrame.initFrame(DeviceListProjectEntry.class.cast(entriesDataGrid.getDatasource().getItem(event.getItemId())).getTyponominal());
            groupBoxTyponominal.setEnabled(true);

            String dbName1 = metadata.getSession().getClassNN(entriesDataGrid.getDatasource().getItem().getClass()).getName();
            comments_typonominal.initialize(false)
                    .setFrameVisible(true)
                    .setCurrentUser(userSession.getUser())
                    .setCurrentEntityId((UUID) entriesDataGrid.getDatasource().getItem().getId())
                    .setCurrentEntityName(dbName1)
                    .applyAndShow();
        });

        entriesDataGrid.addAction(new BaseAction("edit") { //Просмотр данных одного типономинала
            @Override
            public void actionPerform(Component component) {
                typonominalKardBrowse(entriesDataGrid.getSingleSelected().getValue("typonominal"));
            }
        }); //Просмотр данных одного типономинала
        initProcActionsFrame();             // bpm

        if (readOnly) {
            ArrayList<Button> buttonsList = new ArrayList<>();
            buttonsList.add(btAdd);
            buttonsList.add(importBtn);
            buttonsList.add(deleteBtn);
            buttonsList.add(buttonEditor);
            buttonsList.add(buttonApproved);
            buttonsList.add(buttonAnnulled);
            buttonsList.add(createBtn);
            buttonsList.add(editBtn);
            buttonsList.add(deleteAdditionBtn);
            buttonsList.add(addButtonDocument);
            buttonsList.add(excludeButtonDocument);
            buttonsList.add(openProcBtn);
            buttonsList.forEach(button -> button.setEnabled(false));
        }
    }

    @Override
    public void ready() {
        String dbName = metadata.getSession().getClassNN(getItem().getClass()).getName();
        comments.initialize(false)
                .setFrameVisible(true)
                .setCurrentUser(userSession.getUser())
                .setCurrentEntityId(getItem().getId())
                .setCurrentEntityName(dbName)
                .applyAndShow();
        DeviceListProject current = getItem();
        DeviceListProjectStatus itemStatus = current.getStatus();
        // bpm
        if (itemStatus == DeviceListProjectStatus.partsApproved || itemStatus == DeviceListProjectStatus.onApproval || itemStatus == DeviceListProjectStatus.approved)
            procActionsBox.setVisible(true);
        if (itemStatus == DeviceListProjectStatus.partsApproved || itemStatus == DeviceListProjectStatus.onApproval) {
            groupBoxApproved.setEnabled(true);
        }
        current.addPropertyChangeListener(e -> {
            if (Objects.equals(e.getProperty(), "status") && (e.getValue() == DeviceListProjectStatus.approved || e.getValue() == DeviceListProjectStatus.annulled)) {
                groupBoxApproved.setEnabled(false);
            } else {
                groupBoxApproved.setEnabled(true);
            }
        });
    }

    private void initProcActionsFrame() {                                           // bpm
        procActionsFrame.initializer()
                .setBeforeStartProcessPredicate(this::commit)
                .setAfterStartProcessListener(() -> {
                    showNotification(getMessage("processStarted"),
                            NotificationType.HUMANIZED);
                    close(COMMIT_ACTION_ID);
                })
                .setBeforeCompleteTaskPredicate(this::commit)
                .setAfterCompleteTaskListener(() -> {
                    showNotification(getMessage("taskCompleted"),
                            NotificationType.HUMANIZED);
                    close(COMMIT_ACTION_ID);
                })
                .init(PROCESS_CODE, getItem());
    }


    public void onButtonApprovedClick() {
        if (entriesDataGrid.getSingleSelected() != null) {
            entriesDataGrid.getSingleSelected().setValue("status", DeviceListEntryStatus.approved);
        }
    }

    public void onButtonAnnulledClick() {
        if (entriesDataGrid.getSingleSelected() != null) {
            entriesDataGrid.getSingleSelected().setValue("status", DeviceListEntryStatus.annulled);
        }
    }

    public void onButtonEditorClick() {
        ArrayList<String[]> receivers = new ArrayList<>();
        LoadContext<DevicePartListPlanned> dPLPLoadContext = LoadContext.create(DevicePartListPlanned.class);
        dPLPLoadContext.setQuery(dPLPLoadContext.createQuery("select e.devicePartListPlannedInverse from mobdekbkp$DevicePartListPlannedEntry e where " +
                "e.devicePartListPlannedInverse.device.id=:deviceId " +
                "and e.typonominal.id=:typonominalId")
                .setParameter("deviceId", entriesDataGrid.getSingleSelected().getDeviceListProject().getDevice().getId())
                .setParameter("typonominalId", entriesDataGrid.getSingleSelected().getTyponominal().getId()));
        ArrayList<DevicePartListPlanned> dPLPList = new ArrayList<>(dataManager.loadList(dPLPLoadContext));
        for (DevicePartListPlanned listPlanned : dPLPList) {
            listPlanned.setStatus(DevicePartListPlannedStatus.inDev);
        }
        CommitContext commitContext1 = new CommitContext(dPLPList);

        LoadContext<DevicePartListPlannedEntry> dPLPELoadContext = LoadContext.create(DevicePartListPlannedEntry.class);
        dPLPELoadContext.setQuery(dPLPELoadContext.createQuery("select e from mobdekbkp$DevicePartListPlannedEntry e where " +
                "e.devicePartListPlannedInverse.device.id=:deviceId " +
                "and e.typonominal.id=:typonominalId")
                .setParameter("deviceId", entriesDataGrid.getSingleSelected().getDeviceListProject().getDevice().getId())
                .setParameter("typonominalId", entriesDataGrid.getSingleSelected().getTyponominal().getId()));
        dPLPELoadContext.setView(viewRepository.getView(DevicePartListPlannedEntry.class, "devicePartListPlannedEntry-view_small"));
        ArrayList<DevicePartListPlannedEntry> dPLPEList = new ArrayList<>(dataManager.loadList(dPLPELoadContext));

        for (DevicePartListPlannedEntry entry : dPLPEList) {
            if (entry.getTyponominal().getId().equals(entriesDataGrid.getSingleSelected().getTyponominal().getId())) {
                String[] strings = new String[2];
                strings[0] = entry.getCreatedBy();
                strings[1] = entry.getDevicePartListPlannedInverse().getName();
                receivers.add(strings);
                entry.setStatus(DeviceListEntryStatus.annulled);
            }
        }
        CommitContext commitContext2 = new CommitContext(dPLPEList);

        String dbName1 = metadata.getSession().getClassNN(entriesDataGrid.getDatasource().getItem().getClass()).getName();
        Comment comment = metadata.create(Comment.class);
        comment.setAuthor(userSession.getUser());
        comment.setEntity((UUID) entriesDataGrid.getDatasource().getItem().getId());
        comment.setEntityName(dbName1);
        openEditor(comment, WindowManager.OpenType.NEW_WINDOW).addCloseListener(new CloseListener() {
            @Override
            public void windowClosed(String actionId) {
                if (actionId.contentEquals("commit")) {
                    comments_typonominal.initialize(false).setFrameVisible(true)
                            .setFrameVisible(true)
                            .setCurrentUser(userSession.getUser())
                            .setCurrentEntityId((UUID) entriesDataGrid.getDatasource().getItem().getId())
                            .setCurrentEntityName(dbName1)
                            .applyAndShow();
                    dataManager.commit(commitContext1);
                    dataManager.commit(commitContext2);
                    for (String[] strings : receivers) {
                        messageService.send(null, strings[0], "Отправлен на доработку", strings[1] + " отправлен на доработку", true);
                    }
                }
            }
        });

        if (entriesDataGrid.getSingleSelected() != null) {
            entriesDataGrid.getSingleSelected().setValue("status", DeviceListEntryStatus.edit);
        }

    }

    private void typonominalKardBrowse(Typonominal typonominal) {
        Map<String, Object> valueTyponominal = new HashMap<>();
        valueTyponominal.put(TnCardScreen.TYPONOMINAL, typonominal);
        valueTyponominal.put(TnCardFrame.OPTION_PARAM, TnCardFrame.ALL_TABS);
        openWindow("tn-card-screen", WindowManager.OpenType.DIALOG, valueTyponominal);
    }

    public void onImportBtnClick() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("entity", metadata.getClass("mobdekbkp$DeviceListProjectEntry"));
        params.put("parameter", "WHERE o.deviceListProject.id='" + getItem().getId().toString() + "'");
        HashMap<String, String> defaultValue = new HashMap<>();
        defaultValue.put("status", messages.getMessage(DeviceListEntryStatus.inDev));
        defaultValue.put("deviceListProject", getItem().getInstanceName());
        defaultValue.put("adminParameters", "");

        HashMap<String, String> allowedValue = new HashMap<>();
        allowedValue.put("status", DeviceListEntryStatus.inDev.name());

        params.put("defaultValue", defaultValue);
        params.put("allowedValue", allowedValue);
        params.put("message", messages.getMessage("ru.spacecorp.mobdekbkp.web.devicelistproject", "importInfo"));
        Window window = openWindow("smallImportScreen", WindowManager.OpenType.DIALOG, params);
        window.addCloseListener(actionId -> {
            deviceListProjectDs.refresh();
        });

    }

    public void onDeleteBtnClick() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("entity", metadata.getClass("mobdekbkp$DeviceListProjectEntry"));
        params.put("parameter", "WHERE o.deviceListProject.id='" + getItem().getId().toString() + "'");
        HashMap<String, String> defaultValue = new HashMap<>();
        defaultValue.put("status", "В разработке");
        defaultValue.put("deviceListProject", getItem().getInstanceName());

        HashMap<String, String> allowedValue = new HashMap<>();
        allowedValue.put("status", "inDev");

        params.put("defaultValue", defaultValue);
        params.put("allowedValue", allowedValue);
        params.put("message", messages.getMessage("ru.spacecorp.mobdekbkp.web.devicelistproject", "deleteInfo"));
        Window window = openWindow("smallDeleteScreen", WindowManager.OpenType.DIALOG, params);
        window.addCloseListener(actionId -> {
            deviceListProjectDs.refresh();
            //deviceListProjectFieldGroup.getDatasource().refresh();
        });
    }

    public void onOpenProcBtnClick() {
        procInstancesDs.refresh();
        ArrayList<Entity> procInstance = new ArrayList<>(procInstancesDs.getItems());
        if (procInstance.size() != 0) {
            openEditor(procInstance.get(0), WindowManager.OpenType.NEW_WINDOW);
        }
    }

    //выгрузка данных в Excel
    public void onExportBtnClick() throws IOException {
        WorkbookCreator typonominalsWb = new WorkbookCreator();
        typonominalsWb.createRow();
        typonominalsWb.cell(0).setCellValue(getMessage("typonominal"));
        typonominalsWb.cell(1).setCellValue(getMessage("country"));
        typonominalsWb.cell(2).setCellValue(getMessage("levelQuality"));
        typonominalsWb.cell(3).setCellValue(getMessage("tu"));
        ArrayList<DeviceListProjectEntry> entriesList = new ArrayList<>(entriesDs.getItems());

        entriesList.forEach(deviceListProjectEntry -> {
            Typonominal typonominal = deviceListProjectEntry.getTyponominal();
            if (typonominal != null) {
                typonominal = dataManager.reload(typonominal, "typonominal-view");
                typonominalsWb.createRow();
                typonominalsWb.cell(0).setCellValue(typonominal.getName());
                List<TypeManufacturerEntry> manufacturerEntryList = typonominal.getType().getManufacturers();
                if (manufacturerEntryList.size() == 0) {
                    throw new RuntimeException("Cannot get `Manufacturers`, data is corrupted, contact a system administrator!");
                } else {
                    Company company = manufacturerEntryList.get(0).getName();
                    typonominalsWb.cell(1).setCellValue(company.getCountry().getName());
                }
                typonominalsWb.cell(2).setCellValue(typonominal.getQualityCaption());
                List<Document> documentsList = typonominal.getType().getDocumnetsDelivery();
                StringBuilder documentsStr = new StringBuilder();
                documentsList.forEach(document -> {
                    if (document.getDocumentType().getName().contentEquals("Обозначение ТУ исполнения")) {
                        documentsStr.append(document.getName()).append("; ");
                    }
                });
                typonominalsWb.cell(3).setCellValue(documentsStr.toString());
            }
        });

        for (int i = 0; i < COLUMN_COUNT; ++i) {
            typonominalsWb.setAutosize(i);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            typonominalsWb.getWorkbook().write(bos);
        } finally {
            bos.close();
        }

        ByteArrayDataProvider byteArrayDataProvider = new ByteArrayDataProvider(bos.toByteArray());
        exportDisplay.show(byteArrayDataProvider, "export.xls", ExportFormat.XLS);
    }
}
