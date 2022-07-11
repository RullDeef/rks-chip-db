package ru.spacecorp.mobdekbkp.web.devicelistprojectadditionentry

import com.haulmont.cuba.gui.components.AbstractEditor
import com.haulmont.cuba.gui.components.Window
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectAdditionEntry

class DeviceListProjectAdditionEntryEdit extends AbstractEditor<DeviceListProjectAdditionEntry> {
    @Override
    boolean commit() {
        return super.commit()
    }

    @Override
    void commitAndClose() {
        //super.commitAndClose()
        this.close(Window.COMMIT_ACTION_ID,true);
    }
}