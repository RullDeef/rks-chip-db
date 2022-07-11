package com.company.importmodule.service;


import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;

import java.io.IOException;
import java.util.ArrayList;

public interface ExportService {
    String NAME = "importmodule_ExportService";

    FileDescriptor getExportFileWithParam(MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, int findType,
                                         boolean keyAndName, boolean withDict,String parameter,
                                          String additParam) throws IOException;
    FileDescriptor getExportFileWithParam(MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, int findType,
                                          boolean keyAndName, boolean withDict, String parameter) throws IOException;
}