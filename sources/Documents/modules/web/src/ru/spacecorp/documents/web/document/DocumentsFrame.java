package ru.spacecorp.documents.web.document;

import com.google.common.collect.ImmutableMap;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.documents.entity.DocumentRestriction;
import ru.spacecorp.documents.entity.DocumentType;

import javax.inject.Inject;
import java.util.*;

public class DocumentsFrame extends AbstractFrame {
    //region Init Params
    private String presetKey;
    private String globalTypeRestrictionKey;
    private List<String> hideColumnList = new ArrayList<>();
    private List<String> actionsKey = new ArrayList<>();
    private List<String> typeRestrictions = new ArrayList<>();
    private Map<String, Object> mapParams = new HashMap<>();
    private CollectionDatasource customDataSource;
    //endregion

    @Inject private GroupDatasource<Document, UUID> documentsDs;
    @Inject private DataManager dataManager;
    @Inject private GroupTable<Document> documentsTable;
    @Inject private Filter filter;
    @Inject private ButtonsPanel buttons;

    @Override
    public void init(Map<String, Object> params) {
        initParams(params);
    }

    private void initParams(Map<String, Object> params) {
        mapParams.putAll(params);

        if (params.containsKey(DocumentInitKey.USE_PRESET)) {
            Object preset = params.get(DocumentInitKey.USE_PRESET);
            if (preset.getClass() != String.class)
                throw new RuntimeException("Init parameter \"USE_PRESET\" has an incorrect format. Expected String.");

            presetKey = (String) preset;
            initAction(false);
            usePreset();
        }

        if (params.containsKey(DocumentInitKey.TYPE_RESTRICTIONS)) {
            Object restrictions = params.get(DocumentInitKey.TYPE_RESTRICTIONS);
            List<String> listRestrictions;
            try {
                @SuppressWarnings("unchecked")
                List<String> castMap = (List<String>) restrictions;
                listRestrictions = castMap;
            } catch (ClassCastException e) {
                throw new RuntimeException("Init parameter \"TYPE_RESTRICTIONS\" has an incorrect value format. " +
                        "Expected List<String>.");
            }
            typeRestrictions = listRestrictions;
            initTypeRestriction();
        }

        if (params.containsKey(DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS)) {
            Object restrictions = params.get(DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS);
            if (restrictions.getClass() != String.class)
                throw new RuntimeException("Init parameter \"GLOBAL_TYPE_RESTRICTIONS\" has an incorrect format. " +
                        "Expected String.");
            globalTypeRestrictionKey = (String) restrictions;
            initGlobalTypeRestrictions();
        }

        if (params.containsKey(DocumentInitKey.FILTER_VISIBLE)) {
            Object visible = params.get(DocumentInitKey.FILTER_VISIBLE);
            if (visible.getClass() != Boolean.class)
                throw new RuntimeException("Init parameter \"FILTER_VISIBLE\" has an incorrect format. Expected Boolean.");

            filter.setVisible((Boolean) visible);
        }


    }

    private void init() {
        initDs();
        hideTableColumn();
        initCustomActions();
        if (initAction(true))
            usePreset();
        initTypeRestriction();
        initGlobalTypeRestrictions();
        initActionRestrictions();
    }

    private void initCustomActions() {
        documentsTable.addAction(new BaseAction(DocumentInitKey.Actions.OPEN) {
            @Override
            public void actionPerform(Component component) {
                openEditor(
                        documentsTable.getSingleSelected(),
                        WindowManager.OpenType.NEW_TAB,
                        ImmutableMap.of(
                                DocumentInitKey.SET_ATTR_ENABLE, Arrays.asList(
                                        "name",
                                        "designation",
                                        "documentType",
                                        "files"
                                ),
                                DocumentInitKey.SET_CAPTION, Objects.
                                        requireNonNull(documentsTable.getSingleSelected()).getName()
                        )
                );
            }

            @Override
            protected boolean isApplicable() {
                return target != null && target.getSelected().size() == 1;
            }
        });
        Action openAction = documentsTable.getAction(DocumentInitKey.Actions.OPEN);
        if (openAction != null) {
            openAction.setVisible(false);
            openAction.setEnabled(false);
        }

    }

    private void initDs() {
        if (customDataSource != null)
            documentsDs.setQuery(customDataSource.getQuery());
    }

    private void initGlobalTypeRestrictions() {
        if (globalTypeRestrictionKey == null)
            return;

        List<DocumentRestriction> documentRestriction = dataManager
                .load(DocumentRestriction.class)
                .query("" +
                        "select dr " +
                        "from documents$DocumentRestriction dr " +
                        "where dr.key = :dr_key")
                .parameter("dr_key", globalTypeRestrictionKey)
                .view("documentRestriction-view")
                .list();

        if (documentRestriction == null || documentRestriction.size() == 0)
            throw new RuntimeException("Invalid key. Please, use key from instances of class DocumentRestriction");

        List<String> uuidList = new ArrayList<>();
        for (DocumentType documentType : documentRestriction.get(0).getRestriction()) {
            uuidList.add("'" + documentType.getId().toString() + "'");
        }

        documentsDs.setQuery(
                String.format(
                        "select e from documents$Document e where e.documentType.id in (%s)",
                        String.join(",", uuidList)
                )
        );

        mapParams.put(DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS, globalTypeRestrictionKey);
    }

    private void initTypeRestriction() {
        if (typeRestrictions.isEmpty())
            return;

        List<String> nameList = new ArrayList<>();
        for (String name : typeRestrictions) {
            nameList.add("\'" + name + "\'");
        }

        documentsDs.setQuery(
                String.format(
                        "select e from documents$Document e where e.documentType.name in (%s)",
                        String.join(",", nameList)
                )
        );

        mapParams.put(DocumentInitKey.TYPE_RESTRICTIONS, typeRestrictions);

    }

    private void initActionRestrictions() {
        CreateAction createAction = (CreateAction) documentsTable.getAction(DocumentInitKey.Actions.CREATE);
        if (createAction != null)
            createAction.setWindowParams(mapParams);
        EditAction editAction = (EditAction) documentsTable.getAction(DocumentInitKey.Actions.EDIT);
        if (editAction != null)
            editAction.setWindowParams(mapParams);
    }

    private boolean initAction(boolean b) {
        List<Action> actions = new ArrayList<>();
        for (String key : actionsKey) {
            Action action = documentsTable.getAction(key);
            if (action != null)
                actions.add(action);
        }

        visibleAction(actions, b);

        return actionsKey.isEmpty();
    }

    private void usePreset() {
        if(presetKey == null)
            return;

        List<Action> actions = new ArrayList<>();
        Boolean visibleMode;
        switch (presetKey) {
            case DocumentInitKey.Preset.EDIT_ACTION:
                actions.add(documentsTable.getAction(DocumentInitKey.Actions.CREATE));
                actions.add(documentsTable.getAction(DocumentInitKey.Actions.EDIT));
                actions.add(documentsTable.getAction(DocumentInitKey.Actions.REMOVE));
                visibleMode = true;
                break;
            case DocumentInitKey.Preset.WITHOUT_ACTION:
                actions.add(documentsTable.getAction(DocumentInitKey.Actions.CREATE));
                actions.add(documentsTable.getAction(DocumentInitKey.Actions.EDIT));
                actions.add(documentsTable.getAction(DocumentInitKey.Actions.REMOVE));
                visibleMode = false;
                break;
            default:
                throw new RuntimeException("Incorrect preset. Please, use DocumentInitKey.Preset.");
        }
        if (!actions.isEmpty()) {
            visibleAction(actions, visibleMode);
        }
    }

    private void visibleAction(List<Action> actions, Boolean aBoolean) {
        buttons.setVisible(aBoolean);
        for (Action action : actions) {
            action.setVisible(aBoolean);
            action.setEnabled(aBoolean);
        }
    }

    private void hideTableColumn() {
        for (String columnName : hideColumnList) {
            documentsTable.removeColumn(documentsTable.getColumn(columnName));
        }
    }

    Initializer initializer() {
        return new Initializer();
    }

    //region Initializer
    public class Initializer {
        private String presetKey;
        private String globalTypeRestrictionKey;
        private CollectionDatasource customDataSource;
        private List<String> hideColumnList = new ArrayList<>();
        private List<String> actionsKey = new ArrayList<>();
        private List<String> typeRestrictions = new ArrayList<>();


        private Initializer() {
        }

        Initializer usePreset(String presetKey) {
            this.presetKey = presetKey;
            return this;
        }

        Initializer setDataSources(CollectionDatasource ds) {
            this.customDataSource = ds;
            return this;
        }

        Initializer hideTableColumns(List<String> hideColumnList) {
            this.hideColumnList = hideColumnList;
            return this;
        }

        Initializer activateAction(String actionKey) {
            this.actionsKey.add(actionKey);
            return this;
        }

        Initializer addTypeRestrictions(String restrictions) {
            this.typeRestrictions.add(restrictions);
            return this;
        }

        Initializer setGlobalTypeRestriction(String key) {
            this.globalTypeRestrictionKey = key;
            return this;
        }

        void init() {
            copyFields();
            DocumentsFrame.this.init();
        }

        private void copyFields() {
            DocumentsFrame.this.presetKey = this.presetKey;
            DocumentsFrame.this.hideColumnList = this.hideColumnList;
            DocumentsFrame.this.actionsKey = this.actionsKey;
            DocumentsFrame.this.typeRestrictions = this.typeRestrictions;
            DocumentsFrame.this.globalTypeRestrictionKey = this.globalTypeRestrictionKey;
            DocumentsFrame.this.customDataSource = this.customDataSource;
        }
    }
    //endregion

}