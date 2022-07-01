package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.HandbookCad;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class Tnsapr extends AbstractFrame implements TyponominalFrame {

    private Label noDsLabel;

    @Override
    public void initFrame(Typonominal tn) {
    }

    @Override
    public void clearFrame(String labelValue) {
    }
}