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
    @Inject private Container deliveryDocVbox;
    @Inject private Container standardAppSchemVbox;
    @Inject private GroupBoxLayout deliveryDocBox;
    @Inject private GroupBoxLayout standardAppSchemBox;
    @Inject private ComponentsFactory componentsFactory;
    @Inject private HBoxLayout docAndSchemHbox;

    private Typonominal tn;
    private Type type;
    private String emptyLable;
    @Inject
    private DataManager dataManager;

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in extra parameters frame is null");
        this.tn = tn;

        this.type = getTypeTyponominal(tn);

        screenDocAndSchemes();

    }

    private Type getTypeTyponominal(Typonominal tn) {
        LoadContext<Type> loadContext = LoadContext.create(Type.class)
                .setQuery(LoadContext.createQuery(
                        "select ty " +
                                "from mobdekbkp$Typonominal t " +
                                "join t.type ty " +
                                "where t.id = :TyponominalId")
                        .setParameter("TyponominalId", tn.getId()))
                .setView("type-view");
        return dataManager.load(loadContext);
    }

    @Override
    public void clearFrame(String labelValue) {
        this.emptyLable = labelValue;
        docAndSchemHbox.removeAll();
        Label noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        docAndSchemHbox.add(noDsLabel);
    }

    private void screenDocAndSchemes() {
        Type type = this.type;

        Boolean shot1 = false;
        Boolean shot2 = false;
        docAndSchemHbox.removeAll();

        if (type != null && type.getDocumnetsDelivery() != null) {
            List<Document> docs = type.getDocumnetsDelivery();
            if (docs.size() > 0) {
                deliveryDocVbox.removeAll();
                screenDocAndSchemesDisplay(docs, deliveryDocVbox);
                deliveryDocBox.setVisible(true);
                docAndSchemHbox.add(deliveryDocBox);
                shot1 = true;
            }

        }
        if (type != null && type.getDocumentsStandartAppScheme() != null) {
            List<Document> docs = type.getDocumentsStandartAppScheme();
            if (docs.size() > 0) {
                standardAppSchemVbox.removeAll();
                screenDocAndSchemesDisplay(docs, standardAppSchemVbox);
                standardAppSchemBox.setVisible(true);
                docAndSchemHbox.add(standardAppSchemBox);
                shot2 = true;
            }
        }

        if (!(shot1 || shot2))
            clearFrame(emptyLable);
    }

    private void screenDocAndSchemesDisplay(List<Document> documents, Container container) { //добавление кнопок ссылок с документами на форму
        for (Document document : documents) {
            Action actionDocBtn = new AbstractAction("openDocBtn" + document.getId()) {
                @Override
                public void actionPerform(Component component) {
                    onOpenDocBtn(document);
                }
            };
            LinkButton docBtn = componentsFactory.createComponent(LinkButton.class);
            docBtn.setId("docBtnID" + document.getId());
            docBtn.setCaption(document.getName());

            docBtn.setAction(actionDocBtn);
            container.add(docBtn);
        }
    } //добавление кнопок ссылок с документами на форму

    private void onOpenDocBtn(Document document) { //открытие формы с документами
        openEditor(
                "documents$Document.edit",
                document,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        DocumentInitKey.SET_CAPTION, document.getName(),
                        DocumentInitKey.SET_ATTR_ENABLE, new ArrayList<>(
                                Arrays.asList(
                                        "name",
                                        "designation",
                                        "documentType",
                                        "files"
                                )
                        )

                        )
                );
    } //открытие формы с документами


}