package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_CATHODE_RAY_TUBES")
@Entity(name = "mobdekbkp$CathodeRayTubes")
public class CathodeRayTubes extends StandardEntity {
    private static final long serialVersionUID = -2545519637570133465L;

    @Column(name = "SCREEN_BRIGHTNESS", length = 100)
    protected String screenBrightness;

    @Column(name = "RESOLUTION", length = 100)
    protected String resolution;

    @Column(name = "MODULATION_VOLTAGE", length = 100)
    protected String modulationVoltage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setScreenBrightness(String screenBrightness) {
        this.screenBrightness = screenBrightness;
    }

    public String getScreenBrightness() {
        return screenBrightness;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public void setModulationVoltage(String modulationVoltage) {
        this.modulationVoltage = modulationVoltage;
    }

    public String getModulationVoltage() {
        return modulationVoltage;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}