package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_LOG_FILE_DATA")
@Entity(name = "mobdekbkp$LogFileData")
public class LogFileData extends StandardEntity {
    private static final long serialVersionUID = -7871435337496594397L;

    @Column(name = "FILE_NAME")
    protected String fileName;

    @Column(name = "PRODUCT")
    protected String product;

    @Column(name = "NAME1_OR_TYPE")
    protected String name1OrType;

    @Column(name = "NAME2")
    protected String name2;

    @Column(name = "NAME3")
    protected String name3;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setName1OrType(String name1OrType) {
        this.name1OrType = name1OrType;
    }

    public String getName1OrType() {
        return name1OrType;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName2() {
        return name2;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getName3() {
        return name3;
    }


}