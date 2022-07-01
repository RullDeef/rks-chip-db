package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.mobdekbkp.entity.BasicInformation;
import ru.spacecorp.mobdekbkp.entity.Corpus;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalBody;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Tnbody extends AbstractFrame implements TyponominalFrame {

    @Inject
    private TextField tfcorpusType;
    @Inject
    private TextField tfcorpusMaterial;
    @Inject
    private TextField tfcoverCover;
    @Inject
    private TextField tfcontactType;
    @Inject
    private TextField tfcontactCover;
    @Inject
    private TextField tfdesignationOutputFormingOption;
    @Inject
    private DataManager dataManager;

    private Typonominal tn;
    private Corpus corpus;

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBody frame is null");
        this.tn = tn;

        LoadContext<Corpus> ctx = LoadContext.create(Corpus.class);
        ctx.setView("corpus-view");
        ctx.setQuery(LoadContext.createQuery("select c from mobdekbkp$Corpus c join c.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        corpus = dataManager.load(ctx);

        if (corpus != null) {
            getCorpusType();
            getCorpusMaterial();
            getCoverCover();
            getContactType();
            getContactCover();
            getDesignationOutputFormingOption();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        tfcorpusType.setValue(false);
        tfcorpusMaterial.setValue(false);
        tfcoverCover.setValue(false);
        tfcontactType.setValue(false);
        tfcontactCover.setValue(false);
        tfdesignationOutputFormingOption.setValue(false);
    }

    private void getCorpusType() {
        if (corpus.getCorpusType() != null) {
            tfcorpusType.setValue(corpus.getCorpusType());
        }
    }

    private void getCorpusMaterial() {
        if (corpus.getCorpusMaterial() != null) {
            tfcorpusMaterial.setValue(corpus.getCorpusMaterial());
        }
    }

    private void getCoverCover() {
        if (corpus.getCoverCover() != null) {
            tfcoverCover.setValue(corpus.getCoverCover());
        }
    }

    private void getContactType() {
        if (corpus.getContactType() != null) {
            tfcontactType.setValue(corpus.getContactType());
        }
    }

    private void getContactCover() {
        if (corpus.getContactCover() != null) {
            tfcontactCover.setValue(corpus.getContactCover());
        }
    }

    private void getDesignationOutputFormingOption() {
        if (corpus.getDesignationOutputFormingOption() != null) {
            tfdesignationOutputFormingOption.setValue(corpus.getDesignationOutputFormingOption());
        }
    }
}