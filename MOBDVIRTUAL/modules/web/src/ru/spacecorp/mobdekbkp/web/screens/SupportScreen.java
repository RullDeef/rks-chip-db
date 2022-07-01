package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.Message;
import ru.iovchinnikov.notificationsusers.entity.MessageText;
import ru.iovchinnikov.notificationsusers.service.MessageService;
import ru.spacecorp.mobdekbkp.entity.SupportInfo;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SupportScreen extends AbstractWindow {
    @Inject
    private Datasource<SupportInfo> supportInfoDs;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;
    @Inject
    private RichTextArea rtaInfo;
    @Inject
    private TextField tfMail;
    @Inject
    private TextField tfPhone;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private GroupBoxLayout gbButtons;
    @Inject
    private DataManager dataManager;
    @Inject
    private GroupBoxLayout sendMessage;
    @Inject
    private Button btnChat;
    @Inject
    private Button btnSendMsg;
    @Inject
    private LookupField lfThemes;
    @Inject
    private TextField tfTheme;
    @Inject
    private HBoxLayout hbThemePick;
    @Inject
    private RichTextArea rtaMessage;
    @Inject
    private MessageService messageService;
    @Inject
    protected FileUploadField fileUpload;
    @Inject
    protected Label fileName;
    @Inject
    protected HBoxLayout dropZone;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;

    private boolean isSupport = false;
    private String Q_SYSTEM;
    private String Q_WORKSPLACE;
    private String Q_APPLIC_ADD;
    private String Q_APPLIC_DEV;
    private String Q_PROJECT_LIST;
    private String Q_PROJECT_ADD;
    private String Q_COMMENT;
    private String Q_ERROR;
    private String Q_AUTH;
    private String Q_OTHER;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        rtaMessage.addValidator(value -> {
            if (((String) value).contentEquals("")) {
                throw new ValidationException(getMessage("enterMessage"));
            }
        });
        if (supportInfoDs.getItem() == null) {
            LoadContext<SupportInfo> ctx = LoadContext.create(SupportInfo.class);
            ctx.setQuery(LoadContext.createQuery("select si from mobdekbkp$SupportInfo si"))
                    .setView("supportInfo-view");
            supportInfoDs.setItem(dataManager.load(ctx));
        }

        if (supportInfoDs.getItem() == null) {
            SupportInfo item = metadata.create(SupportInfo.class);
            supportInfoDs.setItem(item);
        }

        List<UserRole> ur = userSession.getUser().getUserRoles();
        for (int i = 0; i < ur.size(); i++) {
            if (ur.get(i).getRole().getName().equals(PublicConstants.SYS_ROLE_SUPPORT))
                isSupport = true;
        }

        if (!isSupport) {
            tfMail.setEditable(false);
            tfPhone.setEditable(false);
            rtaInfo.setEditable(false);
        } else {
            Button btnSave = componentsFactory.createComponent(Button.class);
            btnSave.setVisible(true);
            btnSave.setCaption(getMessage("saveClose"));
            btnSave.setAction(new BaseAction("onBtnClick") {
                @Override
                public void actionPerform(Component component) {
                    saveAndClose();
                }
            });
            gbButtons.add(btnSave);
            btnChat.setVisible(false);
            btnSendMsg.setVisible(false);
        }
        Q_SYSTEM = getMessage("qSystem"); // "Технические вопросы по работе с системой"
        Q_WORKSPLACE = getMessage("qWorkplace"); // "Технические вопросы по рабочему месту"
        Q_APPLIC_ADD = getMessage("qApplicAdd"); // "Согласование заявки на добавление нового элемента ЭКБ в МОБД ЭКБ КП"
        Q_APPLIC_DEV = getMessage("qApplicDev"); // "Согласование заявки на разработку нового элемента ЭКБ"
        Q_PROJECT_LIST = getMessage("qProjectList"); // "Согласование перечня проектного для изделий РКТ"
        Q_PROJECT_ADD = getMessage("qProjectAdd"); // "Согласование дополнений к перечню проектному"
        Q_COMMENT = getMessage("qComment"); // "Отзыв, пожелание, идея по работе системы"
        Q_ERROR = getMessage("qError"); // "Ошибка при работе системы"
        Q_AUTH = getMessage("qAuth"); // "Проблемы с авторизацией"
        Q_OTHER = getMessage("qOther"); // "Другое"

        ArrayList<String> LIST_SUPPORT_THEMES = new ArrayList<>(Arrays.asList(
                Q_SYSTEM, Q_WORKSPLACE, Q_APPLIC_ADD, Q_APPLIC_DEV, Q_PROJECT_LIST, Q_PROJECT_ADD, Q_COMMENT, Q_ERROR, Q_AUTH, Q_OTHER
        ));

        lfThemes.setOptionsList(LIST_SUPPORT_THEMES);
        lfThemes.setPageLength(15);
        lfThemes.addValueChangeListener(e -> {
            if (lfThemes.getValue().toString().equals(Q_OTHER)) {
                hbThemePick.expand(tfTheme);
                tfTheme.setVisible(true);
                tfTheme.requestFocus();
            } else {
                hbThemePick.expand(lfThemes);
                tfTheme.setVisible(false);
            }
        });

        // File Upload Part vvv
        fileUpload.addFileUploadSucceedListener(e -> {
            fileName.setValue(fileUpload.getFileName());
        });

        if (AppConfig.getClientType() != ClientType.WEB) {
            dropZone.setVisible(false);
        }
        // File Upload Part ^^^
    }

    private void saveAndClose() {
        supportInfoDs.getItem().setMail(tfMail.getRawValue());
        supportInfoDs.getItem().setPhone(tfPhone.getRawValue());
        supportInfoDs.getItem().setMessage(rtaInfo.getValue());
        supportInfoDs.commit();
        this.close("closed", true);
    }

    public void btnSendMsgClick() {
        gbButtons.setVisible(false);
        sendMessage.setVisible(true);
    }

    public void btnOkClick() throws ValidationException {
        validateAll();
        if (rtaMessage.isValid() && lfThemes.isValid()) {
            LoadContext<User> ctx = LoadContext.create(User.class);
            ctx.setQuery(LoadContext.createQuery("SELECT u FROM sec$User u JOIN u.userRoles ur JOIN u.userRoles r WHERE ur.role.name = :roleName")
                    .setParameter("roleName", PublicConstants.SYS_ROLE_SUPPORT));
            User support = dataManager.load(ctx);
            if (support == null) {
                showNotification("No users with role 'Support' found in the system");
                return;
            }
            // File Upload Part vvv
            FileDescriptor fd = fileUpload.getFileDescriptor();
            if (fileUpload.getFileId() != null) {
                try {
                    fileUploadingAPI.putFileIntoStorage(fileUpload.getFileId(), fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to storage" + e);
                }
                dataSupplier.commit(fd);
            }
            // File Upload Part ^^^
            messageService.send(
                    userSession.getUser().getLogin(),
                    support.getLogin(),
                    (lfThemes.getValue().toString().equals(Q_OTHER)) ? tfTheme.getRawValue() : lfThemes.getValue().toString(),
                    rtaMessage.getValue(),
                    true,
                    fd
            );
            sendMessage.setVisible(false);
            gbButtons.setVisible(true);
        }
    }

    public void btnCancelClick() {
        gbButtons.setVisible(true);
        sendMessage.setVisible(false);
    }

    @Inject
    private ExportDisplay exportDisplay;

    public void btnInstrClick() {
        exportDisplay.show(supportInfoDs.getItem().getInstructions(), ExportFormat.PDF);
    }
}