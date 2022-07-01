package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_PHOTOSENSITIVE_DEVICES")
@Entity(name = "mobdekbkp$PhotosensitiveDevices")
public class PhotosensitiveDevices extends StandardEntity {
    private static final long serialVersionUID = -6942942783935026841L;

    @Column(name = "SPECTRAL_SENSITIVITY_REGION", length = 100)
    protected String spectralSensitivityRegion;

    @Column(name = "NUMBER_PHOTOSENSITIVE_ELEMENTS", length = 100)
    protected String numberPhotosensitiveElements;

    @Column(name = "GEOMETRIC_DIMENSIONS_PHOTOSENSITIVE_ELEMENT", length = 100)
    protected String geometricDimensionsPhotosensitiveElement;

    @Column(name = "SPECIFIC_THRESHOLD_FLUX", length = 100)
    protected String specificThresholdFlux;

    @Column(name = "CURRENT_INTEGRAL_SENSITIVITY", length = 100)
    protected String currentIntegralSensitivity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setSpectralSensitivityRegion(String spectralSensitivityRegion) {
        this.spectralSensitivityRegion = spectralSensitivityRegion;
    }

    public String getSpectralSensitivityRegion() {
        return spectralSensitivityRegion;
    }

    public void setNumberPhotosensitiveElements(String numberPhotosensitiveElements) {
        this.numberPhotosensitiveElements = numberPhotosensitiveElements;
    }

    public String getNumberPhotosensitiveElements() {
        return numberPhotosensitiveElements;
    }

    public void setGeometricDimensionsPhotosensitiveElement(String geometricDimensionsPhotosensitiveElement) {
        this.geometricDimensionsPhotosensitiveElement = geometricDimensionsPhotosensitiveElement;
    }

    public String getGeometricDimensionsPhotosensitiveElement() {
        return geometricDimensionsPhotosensitiveElement;
    }

    public void setSpecificThresholdFlux(String specificThresholdFlux) {
        this.specificThresholdFlux = specificThresholdFlux;
    }

    public String getSpecificThresholdFlux() {
        return specificThresholdFlux;
    }

    public void setCurrentIntegralSensitivity(String currentIntegralSensitivity) {
        this.currentIntegralSensitivity = currentIntegralSensitivity;
    }

    public String getCurrentIntegralSensitivity() {
        return currentIntegralSensitivity;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}