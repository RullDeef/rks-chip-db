package ru.spacecorp.documents.web.document;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.AddAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.documents.entity.DocumentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DocumentEdit extends AbstractEditor<Document> {
    @Inject private Messages messages;
    @Inject private FileMultiUploadField filesUpload;
    @Inject private FileUploadingAPI fileUploadingAPI;
    @Inject private BrowserFrame browserPreview;
    @Inject private CollectionDatasource<FileDescriptor, UUID> filesDs;
    @Inject private FieldGroup fieldGroup;
    @Inject private ButtonsPanel buttonsPanel;

    @Named("filesTable.add")
    private AddAction filesTableAdd;
    @Named("filesTable.remove")
    private RemoveAction filesTableRemove;

    private boolean isValidateType = false;

    @Override
    public void init(Map<String, Object> params) {
        initParams(params);
        initFilesUploadButton();
        initPreviewBox();
    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        if (isValidateType && getItem().getDocumentType() == null)
            errors.add(messages.getMessage(getClass(), "typeIsEmpty"));
    }

    //region Init Map
    private void initParams(Map<String, Object> params) {
        if (params.containsKey(DocumentInitKey.TYPE_RESTRICTIONS))
            initRestrictions(params);

        if (params.containsKey(DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS))
            initRestrictions(params);

        if (params.containsKey(DocumentInitKey.SET_ATTR_ENABLE))
            initFieldEnable(params.get(DocumentInitKey.SET_ATTR_ENABLE));

        if (params.containsKey(DocumentInitKey.SET_CAPTION))
            setCaptionFromParams(params.get(DocumentInitKey.SET_CAPTION));
    }

    private void setCaptionFromParams(Object caption) {
        if (caption.getClass() != String.class)
            throw new RuntimeException("Init parameter \"SET_CAPTION\" has an incorrect format. Expected String.");

        setCaption((String) caption);
    }

    private void initFieldEnable(Object attr_list) {
        List<String> listAttr;
        try {
            @SuppressWarnings("unchecked")
            List<String> castList = (List<String>) attr_list;
            listAttr = castList;
        } catch (ClassCastException e) {
            throw new RuntimeException("Init parameter \"SET_ATTR_ENABLE\" has an incorrect value format. " +
                    "Expected List<String>.");
        }

        for (String attrKey : listAttr) {
            Component attr = fieldGroup.getComponent(attrKey);
            if (attr != null)
                attr.setEnabled(false);
        }

        if (listAttr.contains("files")) {
            buttonsPanel.setEnabled(false);
            filesTableAdd.setEnabled(false);
            filesTableRemove.setEnabled(false);
        }
    }

    private void initRestrictions(Object restrictions) {
        Map<String, Object> map;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> castMap = (Map<String, Object>) restrictions;
            map = castMap;
        } catch (ClassCastException e) {
            throw new RuntimeException("Init parameter has an incorrect value format. Expected Map<String, Object>.");
        }
        PickerField docType = (PickerField) fieldGroup.getComponent("documentType");
        if (docType != null) {
            docType.addAction(new PickerField.LookupAction(docType) {
                @Override
                public void actionPerform(Component component) {
                    openLookup(
                            DocumentType.class,
                            items -> {
                                if (!items.isEmpty())
                                    getItem().setDocumentType((DocumentType) items.iterator().next());
                            },
                            WindowManager.OpenType.DIALOG,
                            map
                    );
                }
            });
        }
        isValidateType = true;
    }
    //endregion

    /**
     * Инициализация экрана предпросмотра
     */
    private void initPreviewBox() {
        filesDs.addItemChangeListener(e -> {
            if (e.getItem() != null)
                browserPreview.setSource(FileDescriptorResource.class).setFileDescriptor(e.getItem());
        });
    }

    /**
     * Инициализация логики работы кнопки загрузки файлов
     */
    private void initFilesUploadButton() {
        filesUpload.addQueueUploadCompleteListener(() -> {
            for (Map.Entry<UUID, String> fileEntry : filesUpload.getUploadsMap().entrySet()) {
                UUID fileId = fileEntry.getKey();
                String fileName = fileEntry.getValue();
                FileDescriptor fileDescriptor = fileUploadingAPI.getFileDescriptor(fileId, fileName);
                try {
                    fileUploadingAPI.putFileIntoStorage(fileId, fileDescriptor);
                    filesDs.addItem(fileDescriptor);
                } catch (FileStorageException e) {
                    throw new RuntimeException(messages.getMessage(getClass(), "uploadError"), e);
                }
            }
            showNotification(messages.getMessage(getClass(), "uploadSuccessful")
                            + filesUpload.getUploadsMap().values(),
                    NotificationType.TRAY);
            filesUpload.clearUploads();
        });
    }


}