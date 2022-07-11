package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|databaseName")
@Table(name = "MOBDEKBKP_OUTER_INFORMATION_SOURCE")
@Entity(name = "mobdekbkp$OuterInformationSource")
public class OuterInformationSource extends StandardEntity {
    private static final long serialVersionUID = 4294712492822925053L;

    @Column(name = "DATABASE_NAME", nullable = false)
    protected String databaseName;

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }



}