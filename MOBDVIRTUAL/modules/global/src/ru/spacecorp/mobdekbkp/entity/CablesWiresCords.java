package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_CABLES_WIRES_CORDS")
@Entity(name = "mobdekbkp$CablesWiresCords")
public class CablesWiresCords extends StandardEntity {
    private static final long serialVersionUID = -3155877396263767118L;

    @Column(name = "TEST_VOLTAGE", length = 100)
    protected String testVoltage;

    @Column(name = "ATTENUATION_COEFFICIENT", length = 100)
    protected String attenuationCoefficient;

    @Column(name = "OVERALL_DIMENSIONS", length = 100)
    protected String overallDimensions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setTestVoltage(String testVoltage) {
        this.testVoltage = testVoltage;
    }

    public String getTestVoltage() {
        return testVoltage;
    }

    public void setAttenuationCoefficient(String attenuationCoefficient) {
        this.attenuationCoefficient = attenuationCoefficient;
    }

    public String getAttenuationCoefficient() {
        return attenuationCoefficient;
    }

    public void setOverallDimensions(String overallDimensions) {
        this.overallDimensions = overallDimensions;
    }

    public String getOverallDimensions() {
        return overallDimensions;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}