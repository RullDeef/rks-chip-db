package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.Label;
import ru.spacecorp.mobdekbkp.entity.CountryType;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalLifeCycleStage;
import ru.spacecorp.mobdekbkp.service.CheckDuplicateService;

import javax.inject.Inject;

public class TyponominalEdit extends AbstractEditor<Typonominal> {
    @Inject
    private Label qualitiLevelLbl;
    @Inject
    private HBoxLayout qualityLevelNativeHbox;
    @Inject
    private HBoxLayout qualityLevelImportHbox;
    @Inject
    private CheckDuplicateService checkDuplicateService;
    @Inject
    private String checkDuplicateStr = "";

    @Override
    protected void initNewItem(Typonominal item) {
        item.setNotPerspective(ExtBoolean.notSet);
        item.setHasSubstitute(ExtBoolean.notSet);
        item.setSealNeeded(ExtBoolean.notSet);
        item.setIsAutoAssemble(ExtBoolean.notSet);
        item.setMadeUsingImportItems(ExtBoolean.notSet);
        item.setHasBody(ExtBoolean.notSet);
        item.setLifeCycleStage(TyponominalLifeCycleStage.productionNotPlanStop);
        checkDuplicateStr = "newTyponominal";
    }

    @Override
    public void ready() {
        if(getItem().getType().getManufacturers() == null || getItem().getType().getManufacturers().size() <=0 ){
            qualitiLevelLbl.setVisible(true);
        } else {
                if(getItem().getType().getManufacturers().get(0).getName().getCountry().getCountryType() == CountryType.russian){
                    qualityLevelNativeHbox.setVisible(true);
                } else if(getItem().getType().getManufacturers().get(0).getName().getCountry().getCountryType() == CountryType.foreign){
                    qualityLevelImportHbox.setVisible(true);
                }
        }
    }

    @Override
    protected boolean preCommit() {
        Boolean commited = true;

        if(getItem().getBody() != null){
            getItem().setHasBody(ExtBoolean.yes);
        }
        if(checkDuplicateStr.equals("newTyponominal")) {
            if (checkDuplicateService.duplicateTyponominal(getItem()) == false) {
                if (getItem().getTyponominalQualityLevelNative() == null && getItem().getTyponominalQualityLevelImport() == null) {
                    showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.typonominal","enterQualityLevel"),NotificationType.WARNING);
                    commited = false;
                }
            } else {
                showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.typonominal","typonominalExist"),NotificationType.WARNING);
                commited = false;
            }
        }
        return commited;
    }   //Проверка на ввод изготовителя типа ЭКБ
}