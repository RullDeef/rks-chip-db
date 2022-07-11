package ru.spacecorp.mobdekbkp.entity.tc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.util.UUID;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Lob;

@NamePattern("%s|id")
@Table(name = "MOBDEKBKP_MAPING_ENTITY")
@Entity(name = "mobdekbkp$MappingEntity")
public class MappingEntity extends StandardEntity {
    private static final long serialVersionUID = -1224030092211007975L;

    @Lob
    @Column(name = "IDTC")
    protected String idtc;

    @Column(name = "IDCUBA")
    protected UUID idcuba;

    public void setIdcuba(UUID idcuba) {
        this.idcuba = idcuba;
    }

    public UUID getIdcuba() {
        return idcuba;
    }


    public void setIdtc(String idtc) {
        this.idtc = idtc;
    }

    public String getIdtc() {
        return idtc;
    }


}