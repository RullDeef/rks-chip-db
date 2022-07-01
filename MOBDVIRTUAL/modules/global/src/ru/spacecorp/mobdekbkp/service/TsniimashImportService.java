package ru.spacecorp.mobdekbkp.service;


import org.w3c.dom.Document;

public interface TsniimashImportService {
    String NAME = "mobdekbkp_TsniimashImportService";

    String importXml(Document document);
}