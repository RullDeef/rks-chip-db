package ru.spacecorp.mobdekbkp.web.devicepartlistplanned;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.AddAction;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlanned;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlannedEntry;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlannedStatus;
import ru.spacecorp.mobdekbkp.entity.Device;
import ru.spacecorp.mobdekbkp.service.AddEntriesService;
import ru.spacecorp.mobdekbkp.web.instruments.WorkbookCreator;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardFrame;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardScreen;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class DevicePartListPlannedEdit extends AbstractEditor<DevicePartListPlanned> {

    @Inject
    private FieldGroup devicePartListPlannedFieldGroup;
    @Inject
    private Metadata metadata;
    @Inject
    private AddEntriesService addEntriesService;
    @Inject
    private Table<DevicePartListPlannedEntry> devicePartListPlannedEntriesTable;
    @Inject
    private Button viewButton;
    @Inject
    private Button removeButton;
    @Inject
    private Button addButton;
    @Inject
    private Button addButtonDocument;
    @Inject
    private Button removeExcludeDocument;
    @Inject
    private CollectionDatasource<DevicePartListPlannedEntry, UUID> entriesDs;
    @Inject
    private Datasource<DevicePart> devicePartDs;
    @Inject
    private GroupDatasource<DeviceFilterConditions, UUID> filterConditionsDs;
    @Inject
    private DataManager dataManager;
    @Inject
    private ExportDisplay exportDisplay;

    @Named("devicePartListPlannedEntriesTable.add")
    private AddAction addActionTyponominal;
    @Named("documentTablePlanned.create")
    private CreateAction createActionPlan;
    @Named("devicePartListPlannedFieldGroup.device")
    private PickerField deviceField;

    private boolean readOnly = false;

    private final static int COLUMN_COUNT = 4;

    @Override
    protected void initNewItem(DevicePartListPlanned item) {
        item.setStatus(DevicePartListPlannedStatus.inDev);
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        FunctionLib functionLib = new FunctionLib();
        devicePartListPlannedEntriesTable.setStyleProvider(new GroupTable.GroupStyleProvider<Entity>() {
            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                return null;
            }

            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                return functionLib.getStyleName(entity, property);
            }
        });

        devicePartListPlannedEntriesTable.addGeneratedColumn(getMessage("recommendations"), new Table.ColumnGenerator<Entity>() {
            @Override
            public Component generateCell(Entity entity) {
                return functionLib.getMsgField(entity);
            }
        });

        if (params.get("readOnly") != null) {
            if ((boolean) params.get("readOnly")) {
                readOnly = true;
            }
        }
    }

    //TODO оптимизировать
    @Override
    protected void postInit() {
        super.postInit();
        if(readOnly){
            ArrayList<Button> buttonsList=new ArrayList<>();
            buttonsList.add(addButtonDocument);
            buttonsList.add(removeExcludeDocument);
            buttonsList.add(addButton);
            buttonsList.add(removeButton);
            buttonsList.add(viewButton);
            buttonsList.forEach(button -> button.setEnabled(false));
        }

        //показывать только те изделия в которых Состав изделия НЕ утверждён + только те, в состав которых входит СЧ
        Map<String, Object> filterParams = new HashMap<>();
        filterParams.put("part", devicePartDs.getItem().getId());
        deviceField.getLookupAction().setLookupScreen("mobdekbkp$DeviceLookup.browse");
        deviceField.getLookupAction().setLookupScreenParams(filterParams);


        devicePartListPlannedEntriesTable.addAction(new BaseAction("edit") { //Просмотр данных одного типономинала
            @Override
            public void actionPerform(Component component) {
                typonominalKardBrowse(devicePartListPlannedEntriesTable.getSingleSelected().getValue("typonominal"));
            }
        });

        //TODO  - оптимизация - добавление записи планируемого перечня СЧ при вызове коммита дата менеджера, а не по кнопке Ок формы проектного перечня
        //Начало - выбор с формы Типономинала и добавление записи планируемого перечня(запись добавляется до выхода с формы!!!)

        addActionTyponominal.setWindowId("mobdekbkp$Typonominal.browse");
        addActionTyponominal.setHandler(new Lookup.Handler() {
            @Override
            public void handleLookup(Collection items) {
                List<Typonominal> listTyponominal = new LinkedList<>(items);
                DevicePartListPlannedEntry plannedEntry;
                if (getItem().getCreatedBy() == null) {
                    plannedEntry = metadata.create(DevicePartListPlannedEntry.class);
                    plannedEntry.setDevicePartListPlannedInverse(getItem());
                    plannedEntry.setTyponominal(listTyponominal.get(0));
                    plannedEntry.setStatus(DeviceListEntryStatus.inDev);
                } else {
                    plannedEntry = addEntriesService.addPlannedEntry(getItem(), listTyponominal.get(0));
                }

                if (getItem().getEntries() != null) {
                    getItem().getEntries().add(plannedEntry);
                } else {
                    List<DevicePartListPlannedEntry> plannedEntryList = new LinkedList<>();
                    plannedEntryList.add(plannedEntry);
                    getItem().setEntries(plannedEntryList);
                }
            }
        });
        //Конец - выбор с формы Типономинала и добавление записи планируемого перечня(запись добавляется до выхода с формы!!!)
    }

    @Override
    public void ready() {
        DevicePartListPlanned item = getItem();

        if (deviceField.getValue() == null) {
            LoadContext<Device> ctx = LoadContext.create(Device.class);
            ctx.setQuery(
                    LoadContext.createQuery("select dev from mobdekbkp$Device dev join dev.parts pts where pts.part.designation = :current")
                            .setParameter("current", item.getDevicePart().getDesignation()))
                    .setView("device-view");
            List<Device> devices = dataManager.loadList(ctx);
            if (devices.size() == 1)
                deviceField.setValue(devices.get(0));
        }


        //передача фильтров в параметра экрана выбора типономинала
        //нельзя выполнять раньше, так как device подставляется в поле только в ready
        ArrayList<DeviceFilterConditions> filterList = new ArrayList<>(filterConditionsDs.getItems());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("filterConditions", filterList);
        if ((devicePartListPlannedEntriesTable.getAction("add")) != null) {
            ((AddAction) devicePartListPlannedEntriesTable.getAction("add")).setWindowParams(hashMap);
        }

        item.addPropertyChangeListener(e -> {
            if (e.getProperty().equals("status") && (e.getValue() == DevicePartListPlannedStatus.approved || e.getValue() == DevicePartListPlannedStatus.annulled)) {
                editEnables();
                for (DevicePartListPlannedEntry plannedEntry : getItem().getEntries()) {
                    if (getItem().getStatus() == DevicePartListPlannedStatus.approved && plannedEntry.getStatus() != DeviceListEntryStatus.annulled) {
                        plannedEntry.setStatus(DeviceListEntryStatus.approved);
                    } else if (getItem().getStatus() == DevicePartListPlannedStatus.annulled) {
                        plannedEntry.setStatus(DeviceListEntryStatus.annulled);
                    }
                }
            }
        });

        DevicePartListPlannedStatus status = item.getStatus();
        Device device = item.getDevice();
        if (device != null) {
            if (device.getIsApproved() &&
                    (status != DevicePartListPlannedStatus.approved &&
                            status != DevicePartListPlannedStatus.annulled)) {  // если состав изделия РКТ утверждён(стоит галка), то можно изменять статус до момента Утверждения или Аннулирования перечня
                devicePartListPlannedFieldGroup.getField("status").setEditable(true);
            } else if (device.getIsApproved() &&
                    (status == DevicePartListPlannedStatus.approved || status == DevicePartListPlannedStatus.annulled)) {
                editEnables();
            }
        }

        entriesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                viewButton.setEnabled(true);
            } else {
                viewButton.setEnabled(false);
            }
        });

    }

    private void editEnables() { // убираем возможность изменять, добавлять и удалять записи
        devicePartListPlannedFieldGroup.getField("status").setEditable(false);
        devicePartListPlannedFieldGroup.getField("device").setEditable(false);
        devicePartListPlannedEntriesTable.removeAction("remove");
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        viewButton.setEnabled(true);
    }

    private void typonominalKardBrowse(Typonominal typonominal) {
        Map<String, Object> valueTyponominal = new HashMap<>();
        valueTyponominal.put(TnCardScreen.TYPONOMINAL, typonominal);
        valueTyponominal.put(TnCardFrame.OPTION_PARAM, TnCardFrame.ALL_TABS);
        openWindow("tn-card-screen", WindowManager.OpenType.DIALOG, valueTyponominal);
    }

    public void onViewButtonClick() {
        typonominalKardBrowse(devicePartListPlannedEntriesTable.getDatasource().getItem().getValue("typonominal"));
    }

    public void onExportBtnClick() throws IOException{
        WorkbookCreator typonominalsWb = new WorkbookCreator();
        typonominalsWb.createRow();
        typonominalsWb.cell(0).setCellValue(getMessage("typonominal"));
        typonominalsWb.cell(1).setCellValue(getMessage("country"));
        typonominalsWb.cell(2).setCellValue(getMessage("levelQuality"));
        typonominalsWb.cell(3).setCellValue(getMessage("tu"));
        ArrayList<DevicePartListPlannedEntry> entriesList = new ArrayList<>(entriesDs.getItems());

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