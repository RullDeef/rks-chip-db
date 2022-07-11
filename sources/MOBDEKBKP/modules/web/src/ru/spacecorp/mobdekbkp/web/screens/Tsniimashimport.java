package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import org.w3c.dom.Document;
import ru.spacecorp.mobdekbkp.service.TsniimashImportService;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Map;

public class Tsniimashimport extends AbstractWindow {

    @Inject
    private TextArea reportArea;

    @Inject
    private FileUploadingAPI fileUploadingAPI;

    @Inject
    private FileUploadField xmlUploadField;

    @Inject
    private DataSupplier dataSupplier;

    @Inject
    private FileLoader fileLoader;

    @Inject
    private TsniimashImportService tsniimashImportService;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        xmlUploadField.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = xmlUploadField.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(xmlUploadField.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving to FileStorage", e);
            }
            dataSupplier.commit(fd);

            try {
                InputStream in = fileLoader.openStream(fd);
                Document document;
                try {
                    document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
                } catch (Exception e) {
                    reportArea.setValue(getMessage("wrongFile"));
                    return;
                }

                String report = tsniimashImportService.importXml(document);
                reportArea.setValue(report);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving to FileStorage", e);
            }

        });
    }
}