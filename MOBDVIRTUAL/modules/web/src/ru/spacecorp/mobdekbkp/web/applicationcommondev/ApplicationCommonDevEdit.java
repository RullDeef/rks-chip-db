package ru.spacecorp.mobdekbkp.web.applicationcommondev;

import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.iovchinnikov.notificationsusers.service.MessageService;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.mobdekbkp.entity.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.spacecorp.mobdekbkp.entity.ApplicationCommonDevStatus.approvalMpt;

public class ApplicationCommonDevEdit extends AbstractEditor<ApplicationCommonDev> {

    @Named("okrInfoTable.create")
    private CreateAction btnAddAppOkrInfo;

    @Named("appCommEntryDocTable.create")
    private CreateAction createAppCommEntryDocBtn;

    @Inject
    private CollectionDatasource appCommEntriesDs;
    @Inject
    private Field appCommEntryClassNameField;
    @Inject
    private TextArea appCommEntryEventArea;
    @Inject
    private TextArea appCommEntryCharacteristicsArea;
    @Inject
    private TextArea appCommEntryPrototypeArea;
    @Inject
    private TextArea appCommEntryPossibilityArea;
    @Inject
    private Table<Document> appCommEntryDocTable;
    @Inject
    private Button entriesSaveBtn;
    @Inject
    private Button entriesEditBtn;
    @Inject
    private Button btnSetAppMpt;
    @Inject
    private Button btnSetWrkMpt;
    @Inject
    private Button btnSetClosed;
    @Inject
    private Button btnSetDeclined;
    @Inject
    private Button btnEntryStatOkr;
    @Inject
    private Button btnEntryStatClosed;
    @Inject
    private Button btnEntryStatDeclined;
    @Inject
    private ButtonsPanel appCommDocBtnPanel;
    @Inject
    private TabSheet okrTabSheet;
    @Inject
    private Button entriesCreateBtn;
    @Inject
    private Table<ApplicationOkrInfo> okrInfoTable;
    @Inject
    private MessageService messageService;

    private ApplicationCommonEntryStatus aceStatus;
    private ApplicationCommonEntry applicationCommonEntry;
    private Action editActionOkrInfoTable;
    private boolean closed = false;

    @Override
    protected void initNewItem(ApplicationCommonDev item) {
        item.setStatus(ApplicationCommonDevStatus.workGnio);
    }

    @Override
    protected void postInit() {
        if (getItem().getStatus() == ApplicationCommonDevStatus.closed) {
            closed = true;
        }

        appCommDocBtnPanel.setVisible(false); //скрыть панель с кнопка у таблицы с документами
        btnSetAppMpt.setVisible(false); //скрыть кнопку На согласование в МинПромТорг
        btnSetWrkMpt.setVisible(false); //скрыть кнопку В работу в МинПромТорг
        btnSetClosed.setVisible(false); //скрыть кнопку Закрыть
        btnSetDeclined.setVisible(false); //скрыть кнопку Отклонить
        btnAddAppOkrInfo.setVisible(false); //скрыть кнопку Добавить Сведения об ОКР
        entriesCreateBtn.setEnabled(false); //сделать неактивной кнопку Создать Записи единой заявки на разработку новых ЭКБ
        okrTabSheet.setVisible(false); //скрыть таб с информацией об окр
        entriesEditBtn.setEnabled(false);  //сделать неактивной кнопку Изменить Записи единой заявки на разработку новых ЭКБ
        appCommEntryDocTable.removeAction("edit"); //запрещаем редактирование записей таблицы Документы записи единой заявки активной
        editActionOkrInfoTable = okrInfoTable.getAction("edit"); //запоминаем действие Edit для таблицы Сведения об окр записи единой заявки
        okrInfoTable.removeAction("edit"); //запрещаем редактрирование данных об ОКР

        btnAddAppOkrInfo.setWindowId("mobdekbkp$ApplicationOkrInfo.edit"); //вызываем экран добавления информации об ОКР
        btnAddAppOkrInfo.setAfterCommitHandler(orkInfo -> { //если добавлена информация об ОКР, то скрываем кнопку Добавить ОКР инфо
            btnAddAppOkrInfo.setVisible(false);
        });

        getItem().addPropertyChangeListener(event -> {
            if (event.getProperty().equals("entries")) {
                btnSetAppMpt.setEnabled(true); //если добавить запись единой заявки, то кнопка Согласовать в МинПромТорг становится активной
            }
        }); //слушатель изменений единой заявки

        ApplicationCommonDevStatus status = getItem().getStatus();
        switch (status) {
            case workGnio:
                btnSetAppMpt.setVisible(true);
                btnSetDeclined.setVisible(true);
                entriesCreateBtn.setEnabled(true);
                if (getItem().getEntries() != null && getItem().getEntries().size() > 0) {
                    btnSetAppMpt.setEnabled(true); //сделать активной кнопку На согласование в МинПромТорг
                }
                break;
            case approvalMpt:
                btnSetWrkMpt.setVisible(true);
                btnSetDeclined.setVisible(true);
                enabledBtnSetWrkMpt(); //проверка все ли записи заявки утверждены или отклонены и тогда возможно добавить заявку в работу в ОКР
                break;
            case workMpt:
                btnSetClosed.setVisible(true);
                btnSetDeclined.setVisible(true);
                okrTabSheet.setVisible(true);
                enabledBtnClose(); //проверка наличия информации об ОКР в записях единой заявки и если она отсутствует то нельяза закрыть единую заявку
                break;
            case closed:
                okrTabSheet.setVisible(true);
                break;
            default:
        } //в зависимости от статуса показываем и скрываем кнопки на форме

        appCommEntriesDs.addItemChangeListener(e -> {
            applicationCommonEntry = (ApplicationCommonEntry) e.getItem(); //выбираем запись единой заявки
            aceStatus = e.getItem().getValue("status"); //статус Записи единой заявки на разработку новых ЭКБ

            if (getItem().getStatus() != ApplicationCommonDevStatus.closed && getItem().getStatus() != ApplicationCommonDevStatus.declined) {
                entriesEditBtn.setEnabled(true); //если выделена Запись единой заявки на разработку новых ЭКБ, то становится активна кнопка Изменить
            }

            if (!entriesEditBtn.isVisible()) {
                enabledBtnEntriesClose(); //отображение кнопок изменения статуса в записи единой заявки
            }
        }); //слушатель выбора Записи единой заявки на разработку новых ЭКБ
    }

    private void enabledBtnEntriesClose() {
        switch (aceStatus) {
            case inDev:
                btnEntryStatOkr.setVisible(false);
                btnEntryStatClosed.setVisible(false);
                btnEntryStatDeclined.setVisible(true);
                break;
            case onApproval:
                btnEntryStatOkr.setVisible(true);
                btnEntryStatClosed.setVisible(false);
                btnEntryStatDeclined.setVisible(true);
                break;
            case inOkr:
                btnEntryStatOkr.setVisible(false);
                btnEntryStatClosed.setVisible(true);
                btnEntryStatDeclined.setVisible(true);
                if ((applicationCommonEntry.getOkrInfo() == null || applicationCommonEntry.getOkrInfo().size() == 0) && getItem().getStatus() == ApplicationCommonDevStatus.workMpt) {
                    btnAddAppOkrInfo.setVisible(true);
                } else {
                    btnAddAppOkrInfo.setVisible(false);
                }
                break;
            case closed:
                btnEntryStatOkr.setVisible(false);
                btnEntryStatClosed.setVisible(false);
                btnEntryStatDeclined.setVisible(false);
                break;
            case declined:
                btnEntryStatOkr.setVisible(false);
                btnEntryStatClosed.setVisible(false);
                btnEntryStatDeclined.setVisible(false);
                btnAddAppOkrInfo.setVisible(false);
                break;
            default:
        }
    } //отображение кнопок изменения статуса в записи единой заявки

    private void enabledBtnClose() {
        btnSetClosed.setEnabled(true);
        for (ApplicationCommonEntry applicationCommonEntry : getItem().getEntries()) {
            if (applicationCommonEntry.getStatus() == ApplicationCommonEntryStatus.inOkr && (applicationCommonEntry.getOkrInfo() == null || applicationCommonEntry.getOkrInfo().size() == 0)) {
                btnSetClosed.setEnabled(false);
                break;
            }
        }
    } //проверка наличия информации об ОКР в записях единой заявки и если она отсутствует то нельяза закрыть единую заявку

    private void enabledBtnSetWrkMpt() {
        for (ApplicationCommonEntry commonEntry : getItem().getEntries()) {
            if ((commonEntry.getStatus() != ApplicationCommonEntryStatus.inOkr) && (commonEntry.getStatus() != ApplicationCommonEntryStatus.declined)) {
                btnSetWrkMpt.setEnabled(false);
                break;
            }
        }
    } //проверка все ли записи заявки утверждены или отклонены и тогда возможно добавить заявку в работу в ОКР

    public void onEntriesEditBtnClick() {
        appCommEntryClassNameField.setEditable(true);       //открываем для редактирования Наименование записи ЕДиной Заявки
        appCommEntryEventArea.setEditable(true);            //открываем для редактирования Мероприятия
        appCommEntryCharacteristicsArea.setEditable(true); //открываем для редактирования Характеристики
        appCommEntryPrototypeArea.setEditable(true);        //открываем для редактирования Функциональный аналог
        appCommEntryPossibilityArea.setEditable(true);      //открываем для редактирования возможность применения
        entriesEditBtn.setVisible(false);   //скрываем кнопку Изменить
        entriesSaveBtn.setVisible(true);    //показываем кнопку Сохранить
        appCommDocBtnPanel.setVisible(true);    //показываем кнопки таблицы Документы записи единой заявки
        okrInfoTable.addAction(editActionOkrInfoTable); //добавляем действие Edit для таблицы Сведения об ОКР записи единой заявки

        enabledBtnEntriesClose(); //отображение кнопок изменения статуса в записи единой заявки
    } //нажатие на кнопку Изменить для записи единой заявки

    public void onEntriesSaveBtnClick() {
        appCommEntryClassNameField.setEditable(false); //закрываем для редактирования Наименование записи ЕДиной Заявки
        appCommEntryEventArea.setEditable(false); //закрываем для редактирования Мероприятия
        appCommEntryCharacteristicsArea.setEditable(false); //закрываем для редактирования Характеристики
        appCommEntryPrototypeArea.setEditable(false); //закрываем для редактирования Функциональный аналог
        appCommEntryPossibilityArea.setEditable(false); //закрываем для редактирования возможность применения
        entriesEditBtn.setVisible(true); //показываем кнопку Изменить
        entriesSaveBtn.setVisible(false); //скрываем кнопку Сохранить
        appCommDocBtnPanel.setVisible(false); //скрываем кнопки таблицы Документы записи единой заявки
        appCommEntryDocTable.removeAction("edit"); //запрещаем редактирование записей таблицы Документы записи единой заявки активной
        okrInfoTable.removeAction("edit"); //запрещаем редактрирование данных об ОКР
        btnAddAppOkrInfo.setVisible(false); //скрываем кнопку Добавить Сведения об ОКР записи единой заявки активной
        btnEntryStatOkr.setVisible(false); //скрываем кнопку В ОКР записи единой заявки активной
        btnEntryStatClosed.setVisible(false); //скрываем кнопку Закрыть записи единой заявки активной
        btnEntryStatDeclined.setVisible(false); //скрываем кнопку Отклонить записи единой заявки активной


        btnSetWrkMpt.setEnabled(true);
        enabledBtnSetWrkMpt(); //проверка все ли запси заявки утверждены или отклонены и тогда возможно добавить заявку в работу в ОКР
        enabledBtnClose(); //проверка наличия информации об ОКР в записях единой заявки и если она отсутствует то нельяза закрыть единую заявку
        //commit(); //коммит изменений
        entriesEditBtn.setEnabled(true); //делаем доступной кнопку Изменить
    }//нажатие на кнопку Сохранить для записи единой заявки

    public void onBtnSetAppMptClick() {
        getItem().setStatus(approvalMpt); //статус единой заявки изменяется
        for (ApplicationCommonEntry commonEntry : getItem().getEntries()) { //если заявка на согласовании в минпромторг то все записи заявки становятся на согласовании
            commonEntry.setStatus(ApplicationCommonEntryStatus.onApproval);
        }
        aceStatus = ApplicationCommonEntryStatus.onApproval;
        btnSetAppMpt.setVisible(false); //кнопка на согласовать в минпромторг становится невидимой
        btnSetWrkMpt.setVisible(true); //кнопка в работу в минпромторг становится видимой
        btnSetWrkMpt.setEnabled(false); //кнопка в работу в минпромторг становится недоступна
        entriesCreateBtn.setEnabled(false); //кнопка создать запись единой заявки становится неактивной
    } //нажатие на кнопку Согласовать в МинПромТорг в единой заявке

    public void onBtnSetWrkMptClick() {
        btnSetWrkMpt.setVisible(false); //кнопка в работу в минпромторг становится невидимой
        btnSetClosed.setVisible(true); //кнопка Закрыть становится видимой
        btnSetClosed.setEnabled(true);
        enabledBtnClose(); //проверка наличия информации об ОКР в записях единой заявки и если информация отсутствует то нельяза закрыть единую заявку
        okrTabSheet.setVisible(true); //открывается таб с информацией об окр
        getItem().setStatus(ApplicationCommonDevStatus.workMpt); //статус изменяется на В работе в минпромторг
    } //нажатие на кнопку В работу в МинПромТорг в единой заявке

    public void onBtnSetClosedClick() {
        getItem().setStatus(ApplicationCommonDevStatus.closed);
        btnSetClosed.setEnabled(false);
        btnSetDeclined.setEnabled(false);
        entriesEditBtn.setEnabled(false);
        entriesSaveBtn.setEnabled(false);

    } //нажатие на кнопку Закрыть в единой заявке

    public void onBtnSetDeclinedClick() {
        getItem().setStatus(ApplicationCommonDevStatus.declined);
        btnSetAppMpt.setVisible(false);
        btnSetWrkMpt.setVisible(false);
        btnSetClosed.setVisible(false);
        btnSetDeclined.setVisible(false);
        entriesEditBtn.setEnabled(false);
        entriesCreateBtn.setEnabled(false);
        entriesSaveBtn.setEnabled(false);
    } //нажатие на кнопку Отклонить в единой заявке

    public void onBtnEntryStatOkrClick() {
        btnEntryStatOkr.setVisible(false);
        btnEntryStatClosed.setVisible(true);
        btnEntryStatDeclined.setVisible(true);
        applicationCommonEntry.setStatus(ApplicationCommonEntryStatus.inOkr);
    } //нажатие на кнопку В ОКР в записи единой заявки

    public void onBtnEntryStatClosedClick() {
        btnEntryStatClosed.setVisible(false);
        btnEntryStatDeclined.setVisible(false);
        applicationCommonEntry.setStatus(ApplicationCommonEntryStatus.closed);
    } //нажатие на кнопку Закрыть в записи единой заявки

    public void onBtnEntryStatDeclinedClick() {
        btnEntryStatOkr.setVisible(false);
        btnEntryStatClosed.setVisible(false);
        btnEntryStatDeclined.setVisible(false);
        btnAddAppOkrInfo.setVisible(false);
        applicationCommonEntry.setStatus(ApplicationCommonEntryStatus.declined);
    } //нажатие на кнопку Отклонить в записи единой заявки

    @Override
    protected boolean postCommit(boolean committed, boolean close) {
        if ((getItem().getStatus() == ApplicationCommonDevStatus.closed) && (!closed)) {
            ApplicationCommonDev applicationCommonDev = getItem();
            List<ApplicationCommonEntry> applicationCommonEntryList = applicationCommonDev.getEntries();
            ArrayList<String[]> receivers = new ArrayList<>();
            if (applicationCommonEntryList != null) {
                for (ApplicationCommonEntry applicationCommonEntry : applicationCommonEntryList) {
                    String[] strings = new String[2];
                    List<ApplicationNewDevEntry> list = applicationCommonEntry.getParents();
                    for (ApplicationNewDevEntry applicationNewDevEntry : list) {
                        strings[0] = applicationNewDevEntry.getApplication().getCreatedBy();
                        strings[1] = applicationCommonEntry.getClassName();
                    }
                    receivers.add(strings);
                }
                for (String[] strings : receivers) {
                    if (strings[0] != null) {
                        messageService.send(null, strings[0], messages.getMessage("ru.spacecorp.mobdekbkp.web.applicationcommondev", "applicationClosed"),
                                messages.getMessage("ru.spacecorp.mobdekbkp.web.applicationcommondev", "applicationClosedMessage")
                                        + strings[1], true);
                    }
                }
            }
        }
        return super.postCommit(committed, close);
    }
}