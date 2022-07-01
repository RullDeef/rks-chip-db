package ru.spacecorp.mobdekbkp.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import ru.spacecorp.mobdekbkp.core.tsniimashImport.ImportTsniimashWorker;

import javax.inject.Inject;

@Service(TsniimashImportService.NAME)
public class TsniimashImportServiceBean implements TsniimashImportService {

    @Inject
    private ImportTsniimashWorker importTsniimashWorker;

    @Override
    public String importXml(Document document) {
        return importTsniimashWorker.doWork(document).getReport();
    }
}