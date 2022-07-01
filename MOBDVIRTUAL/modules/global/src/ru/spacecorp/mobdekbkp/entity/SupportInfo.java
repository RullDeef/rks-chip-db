package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@NamePattern("%s|mail")
@Table(name = "MOBDEKBKP_SUPPORT_INFO")
@Entity(name = "mobdekbkp$SupportInfo")
public class SupportInfo extends StandardEntity {
    private static final long serialVersionUID = -9031771338163629494L;

    @Column(name = "PHONE", nullable = false)
    protected String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTRUCTIONS_ID")
    protected FileDescriptor instructions;

    @Column(name = "MAIL", nullable = false)
    protected String mail;

    @Lob
    @Column(name = "MESSAGE")
    protected String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION")
    protected Date expiration;

    public void setInstructions(FileDescriptor instructions) {
        this.instructions = instructions;
    }

    public FileDescriptor getInstructions() {
        return instructions;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Date getExpiration() {
        return expiration;
    }


}