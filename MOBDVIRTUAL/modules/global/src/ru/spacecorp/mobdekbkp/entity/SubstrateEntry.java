package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|substrate")
@Table(name = "MOBDEKBKP_SUBSTRATE_ENTRY")
@Entity(name = "mobdekbkp$SubstrateEntry")
public class SubstrateEntry extends StandardEntity {
    private static final long serialVersionUID = 7635972935219805072L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SUBSTRATE_ID")
    protected Substrate substrate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_INSTALL_PARAMETERS_ID")
    protected TyponominalInstallParameters typonominalInstallParameters;

    public void setTyponominalInstallParameters(TyponominalInstallParameters typonominalInstallParameters) {
        this.typonominalInstallParameters = typonominalInstallParameters;
    }

    public TyponominalInstallParameters getTyponominalInstallParameters() {
        return typonominalInstallParameters;
    }


    public void setSubstrate(Substrate substrate) {
        this.substrate = substrate;
    }

    public Substrate getSubstrate() {
        return substrate;
    }


}