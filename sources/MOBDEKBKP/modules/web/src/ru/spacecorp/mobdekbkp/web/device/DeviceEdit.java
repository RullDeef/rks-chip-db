package ru.spacecorp.mobdekbkp.web.device;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.service.AddDeviceComplectService;
import ru.spacecorp.mobdekbkp.web.devicelistproject.DeviceListProjectEdit;

import java.util.UUID;

import static com.haulmont.cuba.gui.components.Frame.MessageType.WARNING;

public class DeviceEdit extends AbstractEditor<Device> {
    //buttons
    @Inject
    private ButtonsPanel partsBtnPanel;
    @Inject
    private Button openDeviceListProject;
    @Inject
    private Button numberDeviceBtn;
    @Inject
    private Button isApprovedBtn;

    @Inject
    private GroupDatasource<DeviceFilterConditions, UUID> filterConditionsDs;

    // tables
    @Inject
    private Table<DevicePartsEntry> partsTable;
    @Inject
    private Table<DeviceComplect> complectsTable;

    // box layouts
    @Inject
    private HBoxLayout numberDeviceHbox;
    @Inject
    private GroupBoxLayout complectsBox;

    // other
    @Inject
    private TextField numberDeviceTextField;
    @Named("documentTable.create")
    private CreateAction createActionDoc;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private PickerField deviceDeveloperPickerField;

    //services
    @Inject
    private AddDeviceComplectService deviceComplectService;

    @Inject
    private UserSession userSession;

    @Inject
    private DataManager dataManager;

    private Integer numberDevice = 0;

    public Component progressCell(DeviceComplect dc) {
        Label l = componentsFactory.createComponent(Label.class);
        String current = ((int) (dc.getProgress() * 100) % 101) + "%";
        l.setValue(current);
        return l;
    }

    @Override
    protected void initNewItem(Device item) {
        super.initNewItem(item);
        UUID id = ((ExtUser) userSession.getUser()).getCompanyId();
        if (id != null) {
            Company company = dataManager.load(Company.class).id(id).one();
            item.setDeveloper(company);
        }
    }

    // если состав изделия утверждён, то нельзя провалиться глубже в составную часть изделия и на форме планируемого перечня
    @Override
    public void ready() {
        filterConditionsDs.addCollectionChangeListener(e -> {
            DataManager dataManager = AppBeans.get(DataManager.class);
            e.getItems().forEach(x -> {
                if (!x.getDevices().contains(getItem())) {
                    x.getDevices().add(getItem());
                    try {
                        dataManager.commit(x);
                    } catch (Exception e1) {
                        /*тут может происходить двойной коммит,
                         данный код необходим так как при добавлении условий к существующемиу девайсу
                         не происходит автоматической привязки условия к девайсу
                          */
                    }
                }
            });
        });

        if (getItem().getIsApproved()) { //утверждение составных частей
            partsBtnPanel.setEnabled(false);
            partsTable.removeAllActions(); //добавить
            isApprovedBtn.setEnabled(false);
            isApprovedBtn.setCaption("Состав изделия зафиксирован");
        }
        if (getItem().getDeviceProjectList() != null) { //если создан проектный перечень
            openDeviceListProject.setVisible(true);
            if (getItem().getDeviceProjectList().getStatus() == DeviceListProjectStatus.readyForUse) {  //если проектный перечень готов к работе
                numberDeviceHbox.setVisible(true);
            }
        }
        if (getItem().getComplects() != null && getItem().getComplects().size() > 0) { //если создан перечень для комплектования и он не пустой
            numberDeviceTextField.setValue(getItem().getComplects().size());
            numberDeviceTextField.setEnabled(false);
            numberDeviceBtn.setEnabled(false);
            complectsBox.setEnabled(true);
        }
        if (getItem().getParts() != null && getItem().getParts().size() > 0 && !getItem().getIsApproved()) { //утвердить состав изделия при условии что есть хоть одна СЧ
            isApprovedBtn.setEnabled(true);
        }
        partsTable.getDatasource().addCollectionChangeListener(e -> { //утвердить состав изделия при условии что есть хоть одна СЧ
            if (e.getItems().size() > 0) {
                isApprovedBtn.setEnabled(true);
            } else if (e.getItems().size() < 1) {
                isApprovedBtn.setEnabled(false);
            }
        });
        complectsTable.setStyleProvider(new Table.StyleProvider<DeviceComplect>() {
            @Nullable
            @Override
            public String getStyleName(DeviceComplect entity, @Nullable String property) {
                if (property == null) {
                    return null;
                } else if (property.equals("progress")) {
                    if (entity.getProgress() == null) {
                        return null;
                    } else {
                        return "some-color-" + precentRounder((int) (entity.getProgress() * 100) % 101);
                    }
                }
                return null;
            }
        });
    }

    // Метод, приводящий текущее значение процентов готовности перечня к
    private static int precentRounder(int i) {
        if (i == 1 || i == 2) return 5;
        return (i > 94 && i < 100) ? 95 : ((i + 2) / 5) * 5;
    }

    public void onButtonNumberDeviceClick() {
        try {
            numberDevice = Integer.parseInt(numberDeviceTextField.getValue());
        } catch (Exception e) {
            showMessageDialog("ВНИМАНИЕ!", "", WARNING);
        }
        if (numberDevice > 0) {
            getItem().setComplects(deviceComplectService.addDeviceComplect(getItem(), numberDevice));
            numberDeviceTextField.setEnabled(false);
            numberDeviceBtn.setEnabled(false);
            complectsBox.setEnabled(true);
        }
    }

    public void onIsApprovedBtnClick() {
        getItem().setIsApproved(true);
        partsBtnPanel.setEnabled(false);
        partsTable.removeAction("edit"); //добавить
        isApprovedBtn.setEnabled(false);
        isApprovedBtn.setCaption("Состав изделия зафиксирован");
    }

    public void onOpenDeviceListProjectClick() {
        DeviceListProjectEdit listProjectEdit = (DeviceListProjectEdit) openEditor(getItem().getDeviceProjectList(), WindowManager.OpenType.THIS_TAB);
        listProjectEdit.addCloseListener(new CloseListener() {
            @Override
            public void windowClosed(String actionId) {
                if (listProjectEdit.getItem().getStatus() == DeviceListProjectStatus.readyForUse) {  //если проектный перечень готов к работе
                    numberDeviceHbox.setVisible(true);
                }
            }
        });
    }
}