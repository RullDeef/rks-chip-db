package ru.spacecorp.mobdekbkp.web.typonominalpurchaseparameters;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalPurchaseParameters;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Purchaseparameters extends AbstractFrame implements TyponominalFrame {
    @Inject
    private CollectionDatasource<TyponominalPurchaseParameters, UUID> typonominalPurchaseParametersesDs;
    @Inject
    private Table<TyponominalPurchaseParameters> tableParameters;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private VBoxLayout vbox;
    private Label noDsLabel;
    private String labelValue;
    @Inject
    private Button buttonCreate;

    @Inject
    private Button removeBtn;

    @Inject
    private Metadata metadata;
    Typonominal tn;

    @Inject
    private UserSession userSession;

    private boolean manufacturer = false;

    @Override
    public void init(Map<String, Object> params) {
        tableParameters.setVisible(false);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbox.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn != null) {
            this.tn = tn;
            tableParameters.setVisible(true);
            typonominalPurchaseParametersesDs.setQuery("select e from mobdekbkp$TyponominalPurchaseParameters e where e.typonominal.id = '" + tn.getId() + "'");
            typonominalPurchaseParametersesDs.refresh();

            RemoveAction removeAction = (RemoveAction) tableParameters.getAction("remove");
            if (removeAction != null) {

                removeAction.setEnabled(false);
                removeAction.setVisible(false);
                removeBtn.setVisible(false);

                typonominalPurchaseParametersesDs.addItemChangeListener(e -> {
                    //по-умолчанию у пользователя отсутствует возможность удаления записи
                    removeAction.setEnabled(false);
                    removeAction.setVisible(false);
                    removeBtn.setVisible(false);
                    //при смене итема в датасорсе сбрасывается статус производителя
                    manufacturer = false;
                    TyponominalPurchaseParameters purchaseParameters = e.getItem();
                    if (purchaseParameters != null) {
                        UUID companyId = purchaseParameters.getCompany().getId();
                        UUID userCompanyId = ((ExtUser) userSession.getUser()).getCompanyId();

                        if ((userCompanyId != null) && (companyId != null)) {
                            if (companyId.equals(userCompanyId)) {
                                removeAction.setEnabled(true);
                                removeAction.setVisible(true);
                                removeBtn.setVisible(true);
                                manufacturer = true;
                            }
                        }
                    }
                });
            }

            clearFrame(labelValue);
            buttonCreate.setVisible(false);
            if (tn.getType().getManufacturers().stream().anyMatch(
                    typeManufacturerEntry -> typeManufacturerEntry.getName().getId().equals(((ExtUser) userSession.getUser()).getCompanyId()))) {
                buttonCreate.setVisible(true);
            }
            if (tn.getType().getProviders().stream().anyMatch(
                    typeProviderEntry -> typeProviderEntry.getName().getId().equals(((ExtUser) userSession.getUser()).getCompanyId()))) {
                buttonCreate.setVisible(true);
            }

            if (typonominalPurchaseParametersesDs.size() != 0) {
                tableParameters.setVisible(true);
                noDsLabel.setVisible(false);
            }
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tableParameters.setVisible(false);
        buttonCreate.setVisible(false);
        removeBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onEdit(Component source) {
        Map<String, Object> paramOpen = new HashMap<>();
        //если пользователь не является производителем, то не может редактировать записи
        if (!manufacturer) {
            paramOpen.put("noedit", true);
        }
        openEditor("mobdekbkp$TyponominalPurchaseParameters.edit", typonominalPurchaseParametersesDs.getItem(), WindowManager.OpenType.THIS_TAB, paramOpen);
    }

    public void onButtonCreateClick() {
        TyponominalPurchaseParameters newItem = metadata.create(TyponominalPurchaseParameters.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$TyponominalPurchaseParameters.edit", newItem, WindowManager.OpenType.THIS_TAB);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }
}