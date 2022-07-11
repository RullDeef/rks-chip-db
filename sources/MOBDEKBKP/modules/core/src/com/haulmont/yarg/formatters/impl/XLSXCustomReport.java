package com.haulmont.yarg.formatters.impl;

import com.haulmont.yarg.formatters.CustomReport;
import com.haulmont.yarg.formatters.factory.FormatterFactoryInput;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportTemplate;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.Map;


/**
 * @author golovin
 */
public class XLSXCustomReport implements CustomReport {

    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {
        ReportTemplate reportTemplate = report.getReportTemplates().get("DEFAULT");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XlsxFormatterV2 formatter = new XlsxFormatterV2(
                new FormatterFactoryInput("", rootBand, reportTemplate, outputStream));
        formatter.renderDocument();
        return outputStream.toByteArray();
    }
}
