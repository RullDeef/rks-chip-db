package ru.spacecorp.mobdekbkp.web.devicelistprojectaddition;

import com.company.importmodule.service.ImportService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ViewRepository;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectAddition;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectAdditionEntry;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DeviceListProjectAdditionEdit extends AbstractEditor<DeviceListProjectAddition> {

    @Named("documentTable.create")
    private CreateAction documentCreateBtn;

    @Inject
    ViewRepository viewRepository;

    @Inject
    DataManager dataManager;

    @Inject
    CollectionDatasource entriesDs;


    @Override
    protected boolean preCommit() {
        if(getItem().getEntries()!=null) {
            ArrayList<DeviceListProjectAdditionEntry> addEntryList = new ArrayList<>(getItem().getEntries());
            for (Entity o : addEntryList) {
                o.setValue("deviceListProjectAddition", null);
                dataManager.commit(o);
            }
        }
        entriesDs.setAllowCommit(false);
        return super.preCommit();
    }

    @Override
    protected boolean postCommit(boolean committed, boolean close) {
        if(getItem().getEntries()!=null) {
            ArrayList<DeviceListProjectAdditionEntry> addEntryList = new ArrayList<>(getItem().getEntries());
            for (int i = 0; i < addEntryList.size(); ++i) {
                DeviceListProjectAdditionEntry deviceListProjectAdditionEntry=dataManager.reload(addEntryList.get(i),
                        viewRepository.getView(DeviceListProjectAdditionEntry.class, "deviceListProjectAdditionEntry-view"));
                deviceListProjectAdditionEntry.setValue("deviceListProjectAddition", getItem());
                addEntryList.set(i, deviceListProjectAdditionEntry);
            }
            CommitContext commitContext = new CommitContext(addEntryList);
            dataManager.commit(commitContext);
        }
        return super.postCommit(true,true);

    }
}