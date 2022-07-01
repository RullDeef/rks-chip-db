package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_SAPR_DATA")
@Entity(name = "mobdekbkp$SaprData")
public class SaprData extends StandardEntity {
    private static final long serialVersionUID = 3782179379815575342L;

    @Column(name = "LIBRARY_GRAPHICAL_SYMBOLS")
    protected String libraryGraphicalSymbols;

    @Column(name = "ELECTRONIC_GEOMETRIC_LIBRARY")
    protected String electronicGeometricLibrary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setLibraryGraphicalSymbols(String libraryGraphicalSymbols) {
        this.libraryGraphicalSymbols = libraryGraphicalSymbols;
    }

    public String getLibraryGraphicalSymbols() {
        return libraryGraphicalSymbols;
    }

    public void setElectronicGeometricLibrary(String electronicGeometricLibrary) {
        this.electronicGeometricLibrary = electronicGeometricLibrary;
    }

    public String getElectronicGeometricLibrary() {
        return electronicGeometricLibrary;
    }

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


}