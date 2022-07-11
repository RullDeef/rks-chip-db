package ru.spacecorp.mobdekbkp.core.tsniimashImport;

import com.haulmont.cuba.core.global.*;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.spacecorp.mobdekbkp.core.Logger;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.OuterDbFail;
import ru.spacecorp.mobdekbkp.entity.OuterInformationSource;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stepanov_ME on 26.08.2019.
 */
@Component
public class ImportTsniimashWorker {

    private DataManager dataManager;
    private Metadata metadata;
    private Messages messages;

    public Logger doWork(Document document) {

        Logger logger = new Logger();

        dataManager = AppBeans.get(DataManager.class);
        metadata = AppBeans.get(Metadata.class);
        messages = AppBeans.get(Messages.class);

        //инициализация хранилища
        HashMap<String, OuterDbFailData> outerDbFailDataMap = new HashMap<>();
        ArrayList<AdditData> additDataList = new ArrayList<>();


        try {
            NodeList nodeListDocument = document.getElementsByTagName(StringHolder.DOCUMENT);

            //обработка всех документов
            for (int i = 0; i < nodeListDocument.getLength(); ++i) {
                Node documentNode = nodeListDocument.item(i);
                NodeList nodeList = documentNode.getChildNodes();
                DataConverter dataConverter = new DataConverter();

                //обработка отдельных атрибутов
                for (int g = 0; g < nodeList.getLength(); ++g) {
                    Node node = nodeList.item(g);
                    String name = node.getNodeName();
                    String value = node.getTextContent();
                    dataConverter.convertData(name, value);
                }
                Node node = documentNode.getAttributes().getNamedItem("id");
                outerDbFailDataMap.put(node.getTextContent(), dataConverter.getOuterDbFailData());
            }

            nodeListDocument = document.getElementsByTagName(StringHolder.DOCUMENT_CHILD);

            //обработка дополнительных всех документов
            for (int i = 0; i < nodeListDocument.getLength(); ++i) {
                Node documentNode = nodeListDocument.item(i);
                NodeList nodeList = documentNode.getChildNodes();
                DataConverter dataConverter = new DataConverter();

                //обработка отдельных атрибутов
                for (int g = 0; g < nodeList.getLength(); ++g) {
                    Node node = nodeList.item(g);
                    String name = node.getNodeName();
                    String value = node.getTextContent();
                    dataConverter.convertData(name, value);
                }
                additDataList.add(dataConverter.getAdditData());
            }
        }
        catch (Exception e){
            logger.addFirst(messages.getMessage(this.getClass(),"parsingError"));
            return logger;
        }

        //постобработка
        for (AdditData additData : additDataList) {
            OuterDbFailData outerDbFailData = outerDbFailDataMap.get(additData.getParentId());
            if (outerDbFailData != null) {
                outerDbFailData.setFailType(additData.getFailType());
                outerDbFailData.setFailTypeComment(additData.getFailComment());
            }
        }

        int number = outerDbFailDataMap.size();
        final int[] counter = {0};

        OuterInformationSource outerInformationSource = dataManager.load(OuterInformationSource.class)
                .query("select e from mobdekbkp$OuterInformationSource e where e.databaseName = 'ЦНИИмаш'")
                .optional().orElse(null);
        if (outerInformationSource == null) {
            logger.addLog(messages.getMessage(this.getClass(),"noInformationSource"));
            logger.addFirst(messages.getMessage(this.getClass(),"allData") + number);
            logger.addFirst(messages.getMessage(this.getClass(),"allImport") + counter[0]);
            return logger;
        }

        outerDbFailDataMap.forEach((key, value) -> {
            OuterDbFail outerDbFail = convertToOuterDbFail(value, key, logger, outerInformationSource);
            if (outerDbFail != null) {
                LoadContext<OuterDbFail> loadContext = LoadContext.create(OuterDbFail.class);
                LoadContext.Query query = LoadContext.createQuery("select e from mobdekbkp$OuterDbFail e " +
                        "where e.typonominal.name = :typonominalName " +
                        "and e.index =:index " +
                        "and e.failDate = :failDate " +
                        "and e.failType = :failType");
                query.setParameter("index", outerDbFail.getIndex());
                query.setParameter("typonominalName", outerDbFail.getTyponominal().getName());
                query.setParameter("failDate", outerDbFail.getFailDate());
                query.setParameter("failType", outerDbFail.getFailType());
                loadContext.setQuery(query);
                if (dataManager.loadList(loadContext).size() != 0) {
                    logger.addLog(messages.getMessage(this.getClass(),"alreadyExist") + key);
                } else {
                    ++counter[0];
                    dataManager.commit(outerDbFail);
                }
            }
        });
        logger.addFirst(messages.getMessage(this.getClass(),"allData") + number);
        logger.addFirst(messages.getMessage(this.getClass(),"allImport") + counter[0]);
        return logger;
    }

    private OuterDbFail convertToOuterDbFail(OuterDbFailData outerDbFailData, String id, Logger logger, OuterInformationSource source) {
        OuterDbFail outerDbFail = metadata.create(OuterDbFail.class);

        LoadContext<Typonominal> typonominalLoadContext = LoadContext.create(Typonominal.class);
        LoadContext.Query query = LoadContext.createQuery("select e from mobdekbkp$Typonominal e where e.name = :typonominalName");
        query.setParameter("typonominalName", outerDbFailData.getTyponominal());
        typonominalLoadContext.setQuery(query);
        List<Typonominal> typonominalList = dataManager.loadList(typonominalLoadContext);
        if (typonominalList.size() != 0) {
            outerDbFail.setTyponominal(typonominalList.get(0));
        } else {
            logger.addLog("Типономинал не найден id = " + id);
            return null;
        }

        if (outerDbFailData.getIndex() != null) {
            outerDbFail.setIndex(outerDbFailData.getIndex());
        } else {
            logger.addLog("Индекс не найден id = " + id);
            return null;
        }
        outerDbFail.setMaufactureDate(outerDbFailData.getManufactureDate());
        outerDbFail.setFailDate(outerDbFailData.getFailDate());

        try {
            double workFact = Double.valueOf(outerDbFailData.getWorkFact());
            outerDbFail.setWorkFact(workFact);
        } catch (Exception e) {
            //do nothing
        }

        try {
            double workGarantee = Double.valueOf(outerDbFailData.getWorkGuarantee());
            outerDbFail.setWorkGuarantee(workGarantee);
        } catch (Exception e) {
            //do nothing
        }

        outerDbFail.setClaimDocs(outerDbFailData.getClaimDocs());

        LoadContext<Company> companyLoadContext = LoadContext.create(Company.class);
        query = LoadContext.createQuery("select e from mobdekbkp$Company e where e.name = :companyName");
        query.setParameter("companyName", outerDbFailData.getManufacturer());
        companyLoadContext.setQuery(query);
        List<Company> companyList = dataManager.loadList(companyLoadContext);
        if (companyList.size() != 0) {
            outerDbFail.setManufacturer(companyList.get(0));
        }

        query.setParameter("companyName", outerDbFailData.getClaimedCompany());
        companyList = dataManager.loadList(companyLoadContext);
        if (companyList.size() != 0) {
            outerDbFail.setClaimedCompany(companyList.get(0));
        }

        outerDbFail.setFailType(outerDbFailData.getFailType());
        outerDbFail.setFailTypeComment(outerDbFailData.getFailTypeComment());
        outerDbFail.setRepeating(outerDbFailData.getRepeating());
        outerDbFail.setPart(outerDbFailData.getPart());
        outerDbFail.setVisibleFail(outerDbFailData.getVisibleFail());
        outerDbFail.setDescription(outerDbFailData.getDescription());
        outerDbFail.setComissionResume(outerDbFailData.getComissionResume());
        outerDbFail.setPreviousResume(outerDbFailData.getPreviousResume());
        outerDbFail.setSource(source);

        return outerDbFail;
    }
}
