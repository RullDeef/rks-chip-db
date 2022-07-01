package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FullHierarchyInfo extends AbstractFrame implements TyponominalFrame {
    @Inject
    private DataManager dataManager;
    @Inject
    private TextField tftn;
    @Inject
    private TextField tfTechCond;
    @Inject
    private ResizableTextArea textClass;

    @Override
    public void initFrame(Typonominal tn) {
        Typonominal typonominal = null;
        LoadContext<Typonominal> ctx1 = LoadContext.create(Typonominal.class);
        ctx1.setView("typonominal-card-view");
        ctx1.setQuery(LoadContext.createQuery("select tc from mobdekbkp$Typonominal tc where tc.id = :tnid")
                .setParameter("tnid", tn.getId()));
        typonominal = dataManager.load(ctx1);
        String nameTn = typonominal.getName();
        tftn.setValue(nameTn);

        if (typonominal.getTechnicalCondition() != null) {
            tfTechCond.setValue(typonominal.getTechnicalCondition());
        }
        /*
        String nameType = typonominal.getType().getDesignation();
        tftype.setValue(nameType);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        TypeClass current = typonominal.getType().getTypeClass();
        while (current != null) {

            sb1.insert(0, current.getName());
            if (current.getParent() == null) break;

            LoadContext<TypeClass> ctx = LoadContext.create(TypeClass.class);
            ctx.setView("typeClass-view");
            ctx.setQuery(LoadContext.createQuery("select tc from mobdekbkp$TypeClass tc where tc.id = :tnid")
                    .setParameter("tnid", current.getParent().getId()));
            current = dataManager.load(ctx);

            if (current != null)
                sb1.insert(0, " -> ");
        }
        textClass.setStyleName("textFieldSize");
        textClass.setValue(sb1);
        textClass.setDescription(sb1.toString());

         */
    }

    @Override
    public void clearFrame(String labelValue) {
        tfTechCond.setValue(null);
        tftn.setValue(null);
    }

}