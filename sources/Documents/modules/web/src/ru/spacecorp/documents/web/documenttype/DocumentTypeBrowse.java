package ru.spacecorp.documents.web.documenttype;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import ru.spacecorp.documents.entity.DocumentRestriction;
import ru.spacecorp.documents.entity.DocumentType;
import ru.spacecorp.documents.web.document.DocumentInitKey;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DocumentTypeBrowse extends AbstractLookup {
    @Named("documentTreeTable.create")
    private CreateAction documentTreeTableCreate;
    @Named("documentTreeTable.edit")
    private EditAction documentTreeTableEdit;
    @Named("documentTreeTable.remove")
    private RemoveAction documentTreeTableRemove;

    @Inject
    private HierarchicalDatasource<DocumentType, UUID> documentTypesDs;
    @Inject
    private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {
        initParams(params);
    }

    private void initParams(Map<String, Object> params) {
        if (params.containsKey(DocumentInitKey.TYPE_RESTRICTIONS))
            initTypeRestrictions(params.get(DocumentInitKey.TYPE_RESTRICTIONS));

        if (params.containsKey(DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS))
            initGlobalTypeRestrictions(params.get(DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS));
    }

    /**
     * Ограничивает выдачу через глобальные ограничения DocumentsRestriction
     *
     * @param restrictions Ключ экземпляра DocumentsRestriction
     */
    private void initGlobalTypeRestrictions(Object restrictions) {
        deactivationActions();

        if (restrictions.getClass() != String.class)
            throw new RuntimeException("Init parameter \"GLOBAL_TYPE_RESTRICTIONS\" has an incorrect format. " +
                    "Expected String.");

        List<DocumentRestriction> documentRestriction = dataManager
                .load(DocumentRestriction.class)
                .query("" +
                        "select dr " +
                        "from documents$DocumentRestriction dr " +
                        "where dr.key = :dr_key")
                .parameter("dr_key", restrictions)
                .view("documentRestriction-view")
                .list();

        if (documentRestriction == null || documentRestriction.size() == 0)
            throw new RuntimeException("Invalid key. Please, use key from instances of class DocumentRestriction");

        List<String> uuidList = new ArrayList<>();
        for (DocumentType documentType : documentRestriction.get(0).getRestriction()) {
            uuidList.add("'" + documentType.getId().toString() + "'");
        }

        documentTypesDs.setQuery(
                String.format(
                        "select e from documents$DocumentType e where e.id in (%s)",
                        String.join(",", uuidList)
                )
        );
    }

    /**
     * Ограничивает выдачу по наименованиям переданным в restrictions
     *
     * @param restrictions Список наименований типов
     */
    private void initTypeRestrictions(Object restrictions) {
        deactivationActions();

        List<String> listRestrictions;
        try {
            @SuppressWarnings("unchecked")
            List<String> castMap = (List<String>) restrictions;
            listRestrictions = castMap;
        } catch (ClassCastException e) {
            throw new RuntimeException("Init parameter \"TYPE_RESTRICTIONS\" has an incorrect value format. " +
                    "Expected List<String>.");
        }

        List<String> nameList = new ArrayList<>();
        for (String name : listRestrictions) {
            nameList.add("\'" + name + "\'");
        }

        documentTypesDs.setQuery(
                String.format(
                        "select e from documents$DocumentType e where e.name in (%s)",
                        String.join(",", nameList)
                )
        );
    }

    /**
     * Скрывает все действия формы
     */
    private void deactivationActions() {
        documentTreeTableCreate.setVisible(false);
        documentTreeTableCreate.setEnabled(false);
        documentTreeTableEdit.setVisible(false);
        documentTreeTableEdit.setEnabled(false);
        documentTreeTableRemove.setVisible(false);
        documentTreeTableRemove.setEnabled(false);
    }


}