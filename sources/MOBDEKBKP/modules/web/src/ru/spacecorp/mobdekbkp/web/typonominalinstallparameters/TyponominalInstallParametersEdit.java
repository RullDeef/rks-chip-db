package ru.spacecorp.mobdekbkp.web.typonominalinstallparameters;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.actions.AddAction;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.Substrate;
import ru.spacecorp.mobdekbkp.entity.SubstrateEntry;
import ru.spacecorp.mobdekbkp.entity.TyponominalInstallParameters;
import ru.spacecorp.mobdekbkp.service.AddEntriesService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class TyponominalInstallParametersEdit extends AbstractEditor<TyponominalInstallParameters> {

    @Inject
    private Metadata metadata;

    @Inject
    private AddEntriesService addEntriesService;

    @Named("substratesTable.add")
    private AddAction addActionSubstrates;

    @Override
    protected void initNewItem(TyponominalInstallParameters item) {
        item.setNonPbSolderTech(ExtBoolean.notSet);
        item.setGasEnvironmentAvailable(ExtBoolean.notSet);
        item.setAutoInstallation(ExtBoolean.notSet);
        item.setHasGasket(ExtBoolean.notSet);
        item.setHasGlue(ExtBoolean.notSet);
    }

    @Override
    protected void postInit() {
        addSubstrates();
    }

    void addSubstrates(){
        //Начало - добавление Подложек
        addActionSubstrates.setWindowId("mobdekbkp$Substrate.browse");
        addActionSubstrates.setHandler(new Lookup.Handler() {
            @Override
            public void handleLookup(Collection items) {
                List substrateList = new LinkedList(items);
                SubstrateEntry substrateEntry;
                if(getItem().getCreatedBy() == null){
                    substrateEntry = metadata.create(SubstrateEntry.class);
                    substrateEntry.setSubstrate((Substrate) substrateList.get(0));
                    substrateEntry.setTyponominalInstallParameters(getItem());
                } else {
                    substrateEntry = addEntriesService.addSubstrateEntry(getItem(), (Substrate) substrateList.get(0));
                }

                if(getItem().getSubstrates() != null) {
                    getItem().getSubstrates().add(substrateEntry);
                } else {
                    List<SubstrateEntry> substrateEntryList = new LinkedList<>();
                    substrateEntryList.add(substrateEntry);
                    getItem().setSubstrates(substrateEntryList);
                }
            }
        });


        //Конец - добавление Подложек
    }   //добавление Подложек

    @Override
    protected boolean preCommit() {
        if(getItem().getGasketSize() != null || getItem().getGasketMaterial() != null){
            getItem().setHasGasket(ExtBoolean.yes);
        }
        if(getItem().getGlueType() != null){
            getItem().setHasGlue(ExtBoolean.yes);
        }
        return super.preCommit();
    }
}
