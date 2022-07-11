package ru.iovchinnikov.notificationsusers.entity;

import javax.persistence.Entity;
import com.haulmont.cuba.core.entity.annotation.Extends;
import javax.persistence.Column;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.core.entity.FileDescriptor;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Extends(User.class)
@Entity(name = "notificationsusers$ExtUser")
public class ExtUser extends User {
    private static final long serialVersionUID = -1971213726917876951L;

    @Pattern(message = "Логин может содержать только буквы, цифры и символ подчеркивания",
            regexp = "^[a-zA-Zа-яА-Я0-9_]*$")
    @Column(name = "LOGIN", length = 50, nullable = false)
    protected String login;

    @Column(name = "COMPANY_REF")
    protected String companyRef;

    @Column(name = "COMPANY_ID")
    protected UUID companyId;

    @Column(name = "PHONE", length = 50)
    protected String phone;

    @Column(name = "PH_ADD")
    protected String phAdd;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    public void setPhAdd(String phAdd) {
        this.phAdd = phAdd;
    }

    public String getPhAdd() {
        return phAdd;
    }


    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public UUID getCompanyId() {
        return companyId;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }

    public FileDescriptor getFile() {
        return file;
    }


    public void setCompanyRef(String companyRef) {
        this.companyRef = companyRef;
    }

    public String getCompanyRef() {
        return companyRef;
    }


}