package ru.spacecorp.documents.web.document;

public class DocumentInitKey {

    public static final String SET_CAPTION = "SET_CAPTION";
    public static final String FILTER_VISIBLE = "FILTER_VISIBLE";
    public static final String USE_PRESET = "USE_PRESET";
    public static final String TYPE_RESTRICTIONS = "TYPE_RESTRICTIONS";
    public static final String GLOBAL_TYPE_RESTRICTIONS = "GLOBAL_TYPE_RESTRICTIONS";
    public static final String SET_ATTR_ENABLE = "SET_ATTR_ENABLE";


    public class Preset {
        public static final String EDIT_ACTION = "EDIT_ACTION";
        public static final String WITHOUT_ACTION = "WITHOUT_ACTION";
    }

    public class TableColumns {
        public static final String NAME = "name";
        public static final String DESIGNATION = "designation";
        public static final String DOCUMENT_TYPE = "documentType";
    }

    public class Actions {
        public static final String CREATE = "create";
        public static final String EDIT = "edit";
        public static final String ADD = "add";
        public static final String REMOVE = "remove";
        public static final String EXCLUDE = "exclude";
        public static final String REFRESH = "refresh";
        public static final String EXCEL = "excel";
        public static final String OPEN = "open";
    }
}
