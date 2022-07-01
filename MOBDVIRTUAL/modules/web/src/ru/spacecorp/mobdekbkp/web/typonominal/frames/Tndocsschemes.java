package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.google.common.collect.ImmutableMap;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.documents.web.document.DocumentInitKey;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tndocsschemes extends AbstractFrame implements TyponominalFrame {
    @Inject
    private TextField tfTechnicalCondition;

    private Typonominal tn;
    @Inject
    private DataManager dataManager;

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in extra parameters frame is null");
        this.tn = tn;
        if (tn.getTechnicalCondition() != null) {
            tfTechnicalCondition.setValue(tn.getTechnicalCondition());
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        tfTechnicalCondition.setValue(false);
    }
}