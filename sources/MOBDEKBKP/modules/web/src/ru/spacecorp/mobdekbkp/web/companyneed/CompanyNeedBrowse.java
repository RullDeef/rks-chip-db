package ru.spacecorp.mobdekbkp.web.companyneed;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.CompanyNeed;
import ru.spacecorp.mobdekbkp.entity.CompanyNeedStatus;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CompanyNeedBrowse extends AbstractLookup {
    @Inject
    private GroupDatasource<CompanyNeed, UUID> companyNeedsDs;
    @Inject
    private UserSession userSession;

    @Inject
    private GroupTable<CompanyNeed> companyNeedsTable;

    @Inject
    private Button approveBtn;

    @Inject
    private Button rejectBtn;

    @Inject
    private Button rejectGnioBtn;

    @Inject
    private DataManager dataManager;

    @Inject
    private ComponentsFactory componentsFactory;

    @Inject
    private Messages messages;

    private Action editAction;
    private Action removeAction;
    private final static String EDIT = "edit";
    private final static String REMOVE = "remove";

    private final static int MAX_STATUS_LENGTH = 39;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        companyNeedsTable.addGeneratedColumn("status", entity -> {
            CompanyNeedStatus status = entity.getStatus();
            if (status != null) {
                String statusStr = messages.getMessage(status);
                if (statusStr.length() < MAX_STATUS_LENGTH) {
                    return new Table.PlainTextCell(statusStr);
                } else {
                    PopupView popupView = componentsFactory.createComponent(PopupView.class);
                    popupView.setMinimizedValue(statusStr.substring(0, MAX_STATUS_LENGTH));

                    TextArea textArea = componentsFactory.createComponent(TextArea.class);
                    textArea.setEditable(false);
                    textArea.setWidth("300px");
                    textArea.setHeight("80px");
                    textArea.setValue(statusStr);

                    popupView.setPopupContent(textArea);
                    return popupView;
                }
            } else {
                return null;
            }
        });

        editAction = companyNeedsTable.getAction(EDIT);
        removeAction = companyNeedsTable.getAction(REMOVE);

        boolean isAccessing = false;
        ExtUser currentUser = (ExtUser) userSession.getUser();
        List<UserRole> currentUserRoles = currentUser.getUserRoles();
        for (UserRole r : currentUserRoles) {
            if (r.getRole().getName().equals(PublicConstants.SYS_ROLE_GKRK) ||
                    r.getRole().getName().equals(PublicConstants.SYS_ROLE_GNIO))
                isAccessing = true;
        }

        boolean finalIsAccessing = isAccessing;
        companyNeedsDs.addItemChangeListener(e -> {
            hideButtons();
            CompanyNeed companyNeed = e.getItem();
            if (companyNeed != null) {
                //???????? ???????????????????????? ???? ?????????????????? ????????????
                if (!companyNeed.getCreatedBy().contentEquals(userSession.getUser().getLogin())
                        && (!companyNeed.getCompany().getId().equals(((ExtUser) userSession.getUser()).getCompanyId()))) {
                    editAction.setEnabled(false);
                    //???????? ???????????????????????? ???? ???????? ?????? ????????
                    if (!finalIsAccessing) {
                        removeAction.setEnabled(false);
                    }
                } else {
                    editAction.setEnabled(true);
                    removeAction.setEnabled(true);
                }

                //???????? ???? ????????????????, ???? ???????????????? ???????????? ????????????
                if (isBpComplited(companyNeed)) {
                    editAction.setEnabled(false);
                    if (finalIsAccessing) {
                        //???????? ?????????? ?????????????????? ?????????? ???????????? ?????????? ?????? ??????????????????????
                        if (companyNeed.getStatus() != CompanyNeedStatus.rejected) {
                            rejectGnioBtn.setVisible(true);
                        }
                    }
                } else {
                    //???????? ???? ???? ????????????????, ???? ???????????????????? ???????????? ????????????????????
                    Typonominal typonominal = companyNeed.getTyponominal();
                    if (typonominal != null) {
                        if (isManufacturer(typonominal)) {
                            //?????????????????????????? ?????????? ?????????????????????? ?????? ?????????????????? ????????????
                            approveBtn.setVisible(true);
                            rejectBtn.setVisible(true);
                        }
                    }
                    if (finalIsAccessing) {
                        //???????? ?????????? ?????????????????? ?????????? ????????????
                        rejectGnioBtn.setVisible(true);
                    }
                }
            }
        });

        companyNeedsTable.setStyleProvider((entity, property) -> {
            if (property == null) {
                return null;
            }
            CompanyNeedStatus status = entity.getStatus();
            if (status != null) {
                switch (status) {
                    case approved: {
                        return "green";
                    }
                    case rejected: {
                        return "red";
                    }
                    case rejectedByGnio: {
                        return "red";
                    }
                    case rejectedAfterApproveByGnio: {
                        return "red";
                    }
                    default: {
                        return null;
                    }
                }
            } else {
                return null;
            }
        });

        if (!isAccessing) {
            String login = userSession.getUser().getLogin();
            //?????????????? ???????????????????????? ???????????????? ???????????????????? ???????????? ?????? ???????????????????????????? ???????????????????????????????? ???????????????? ??????
            String queryDefault = String.format("SELECT e FROM mobdekbkp$CompanyNeed e WHERE e.company.id IN " +
                            "(SELECT a.companyId FROM notificationsusers$ExtUser a WHERE a.login = '%s') " +
                            "or e.createdBy IN (SELECT a.login FROM notificationsusers$ExtUser a WHERE a.login = '%s') " +
                            "or e.typonominal IN (SELECT e FROM mobdekbkp$Typonominal e join e.type.manufacturers m " +
                            "WHERE m.name.id IN " +
                            "(SELECT a.companyId FROM notificationsusers$ExtUser a WHERE a.login = '%s')) " +
                            "and ((e.status <> '%s') or (e.status IS NULL))",
                    login, login, login, CompanyNeedStatus.rejectedByGnio.getId());
            companyNeedsDs.setQuery(queryDefault);
        }

        companyNeedsDs.refresh();
    }

    private boolean isManufacturer(Typonominal typonominal) {
        LoadContext<Typonominal> loadContext = LoadContext.create(Typonominal.class);
        LoadContext.Query query = LoadContext.createQuery("SELECT e FROM mobdekbkp$Typonominal e " +
                "join e.type.manufacturers m " +
                "WHERE m.name.id IN (SELECT a.companyId FROM notificationsusers$ExtUser a WHERE a.login = :login) " +
                "and e.id = :typonominalId");
        query.setParameter("login", userSession.getUser().getLogin());
        query.setParameter("typonominalId", typonominal.getId());
        loadContext.setQuery(query);
        loadContext.setView("typonominal-view");
        List<Typonominal> typonominalList = dataManager.loadList(loadContext);
        return typonominalList.size() != 0;
    }

    private boolean isBpComplited(@NotNull CompanyNeed companyNeed) {
        return (companyNeed.getStatus() != CompanyNeedStatus.created) && (companyNeed.getStatus() != null);
    }

    private void hideButtons() {
        approveBtn.setVisible(false);
        rejectBtn.setVisible(false);
        rejectGnioBtn.setVisible(false);
    }

    public void onApproveBtnClick() {
        refreshStatus(CompanyNeedStatus.approved);
    }

    public void onRejectBtnClick() {
        refreshStatus(CompanyNeedStatus.rejected);
    }

    public void onRejectGnioBtnClick() {
        CompanyNeed companyNeed = companyNeedsDs.getItem();
        if (companyNeed != null) {
            //?????? ???????????????????? ?????? ???????????????????????????? ???????????? ???????????????????????? ???????????????????????????? ????????????
            if (companyNeed.getStatus() == CompanyNeedStatus.approved) {
                refreshStatus(CompanyNeedStatus.rejectedAfterApproveByGnio);
            } else {
                refreshStatus(CompanyNeedStatus.rejectedByGnio);
            }
        }
    }

    private void refreshStatus(CompanyNeedStatus status) {
        CompanyNeed companyNeed = companyNeedsDs.getItem();
        if (companyNeed != null) {
            companyNeed.setStatus(status);
        }
        dataManager.commit(companyNeed);
        companyNeedsDs.refresh();
    }
}