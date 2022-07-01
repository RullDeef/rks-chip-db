package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|application,id")
@Table(name = "MOBDEKBKP_APPLICATION_NEW_DEV_ENTRY")
@Entity(name = "mobdekbkp$ApplicationNewDevEntry")
public class ApplicationNewDevEntry extends StandardEntity {
    private static final long serialVersionUID = 2167087324357049717L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPLICATION_ID")
    protected ApplicationNewTyponominalDev application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_COMMON_ENTRY_ID")
    protected ApplicationCommonEntry applicationCommonEntry;

    public void setApplicationCommonEntry(ApplicationCommonEntry applicationCommonEntry) {
        this.applicationCommonEntry = applicationCommonEntry;
    }

    public ApplicationCommonEntry getApplicationCommonEntry() {
        return applicationCommonEntry;
    }


    public void setApplication(ApplicationNewTyponominalDev application) {
        this.application = application;
    }

    public ApplicationNewTyponominalDev getApplication() {
        return application;
    }


}