package ru.spacecorp.mobdekbkp.core.reports.customtemplates;

import com.haulmont.yarg.formatters.CustomReport;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextReport implements CustomReport {
    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {

        StringBuilder answer = new StringBuilder();
        // report name
        answer.append((String) rootBand.getChildByName("reportname").getData().get("name")).append("\n\n");

        // typonominal header
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hgroup")).append(": ");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("componentType")).append("\n");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("htyponom")).append(": ");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("typonominal")).append("\n");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hlevel")).append(": ");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("qualityLevel")).append("\n");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hprod")).append(": ");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("producerCompany")).append("\n");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hcountry")).append(": ");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("country")).append("\n\n");

        //table header
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hyr")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hnum")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hdev")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("qty")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hcommneed")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hdone")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hleft")).append("\n");


        List rows = rootBand.getChildrenByName("componentListProject");
        for (int i = 0; i < rows.size(); i++) {
            BandData bd = (BandData)rows.get(i);
            answer.append(bd.getData().get("planYear")+"\t");
            answer.append(bd.getData().get("enum")+"\t");
            answer.append(bd.getData().get("deviceDesignation")+"\t");
            answer.append(bd.getData().get("deviceKitsCount")+"\t");
            answer.append(bd.getData().get("prjEntriesAmount")+"\t");
            answer.append(bd.getData().get("complEntriesAmount")+"\t");
            answer.append(bd.getData().get("deltaProjectComplect")+"\n");
        }

        return answer.toString().getBytes();
    }
}
