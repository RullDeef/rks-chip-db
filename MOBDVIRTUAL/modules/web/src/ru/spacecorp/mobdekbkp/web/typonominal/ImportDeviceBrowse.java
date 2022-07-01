package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.web.importdevice.ImportCardFrame;

import javax.inject.Inject;
import java.util.*;

import com.haulmont.cuba.gui.components.Component;
import ru.spacecorp.mobdekbkp.web.importdevice.ImportDeviceScreen;

public class ImportDeviceBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<ImportClass, UUID> importClassesDs;
    @Inject
    private CollectionDatasource<ImportDevice, UUID> importDevicesDs;
    @Inject
    private ImportCardFrame importCardFrame;
    private ImportDevice tn;
    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        Map<String, Object> frameparams = new HashMap<>();
        frameparams.put(ImportCardFrame.NO_INIT_TN_LABEL, getMessage("noDsLabel"));
        frameparams.put(ImportCardFrame.OPTION_PARAM, ImportCardFrame.MAIN_TAB);
        importCardFrame.reinit(frameparams);
    }

    @Override
    public void ready() {

        importClassesDs.addItemChangeListener(importClass -> {
            if (importClass.getItem() != null) {
                List<UUID> childrenList = new ArrayList<>();
                childrenList.clear();
                childrenList.add(importClass.getItem().getId());
                StringBuilder queryDevice = new StringBuilder("select e from mobdekbkp$ImportDevice e where e.importClass.id = '");
                boolean isFirstAddition = true;
                for (int j = 0; j < childrenList.size(); j++) {
                    if (isFirstAddition) {
                        queryDevice.append(childrenList.get(j).toString()).append("'");
                        isFirstAddition = false;
                    } else {
                        queryDevice.append(" or e.importClass.id = '").append(childrenList.get(j).toString()).append("'");
                    }

                    if (importDevicesDs != null) {
                        importDevicesDs.setQuery(queryDevice.toString() + "order by e.name");
                        importDevicesDs.refresh();
                        importDevicesDs.setMaxResults(0);
                    }
                }
                importCardFrame.clearFrame(getMessage("noDsLabel"));
            }
        });

        if (importDevicesDs != null) {
            importDevicesDs.addItemChangeListener(tn -> {
                ImportDevice importDevice = importDevicesDs.getItem();
                importCardFrame.clearFrame(getMessage("noDsLabel"));
                if (importDevice == null) return;
                importCardFrame.initFrame(importDevice);
            });
        }
    }

    public void onBtAddClick() {
        tn = metadata.create(ImportDevice.class);
        tn.setImportClass(importClassesDs.getItem());
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$ImportDevice.edit", tn,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.importDevicesDs.refresh());
        importDevicesDs.refresh();
    }

    public void cancel() {
    }

    public void save() {
    }
}