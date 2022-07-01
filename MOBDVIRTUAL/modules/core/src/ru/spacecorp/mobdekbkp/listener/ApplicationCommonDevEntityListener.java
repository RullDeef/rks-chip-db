package ru.spacecorp.mobdekbkp.listener;

import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.core.EntityManager;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonDev;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonDevStatus;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonEntry;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonEntryStatus;

@Component("mobdekbkp_ApplicationCommonDevEntityListener")
public class ApplicationCommonDevEntityListener implements BeforeUpdateEntityListener<ApplicationCommonDev> {


    @Override
    public void onBeforeUpdate(ApplicationCommonDev entity, EntityManager entityManager) {
//        if (entity.getStatus() == ApplicationCommonDevStatus.workMpt) {
//            for (ApplicationCommonEntry inner : entity.getEntries()) {
//                inner.setStatus(ApplicationCommonEntryStatus.inOkr);
//            }
//        }
    }


}