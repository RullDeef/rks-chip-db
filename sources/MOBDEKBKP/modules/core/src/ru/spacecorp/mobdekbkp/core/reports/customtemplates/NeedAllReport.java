package ru.spacecorp.mobdekbkp.core.reports.customtemplates;

import com.haulmont.yarg.formatters.CustomReport;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;

import java.util.List;
import java.util.Map;

public class NeedAllReport  implements CustomReport{
    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {
        StringBuilder answer = new StringBuilder();

        answer.append((String) rootBand.getChildByName("reportname").getData().get("name")).append("\n\n");

        //table header
        answer.append((String) rootBand.getChildByName("hmain").getData().get("henum")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hdesignation")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hqlevel")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hamount")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hcompanies")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hdevelopers")).append("\n");

        List rows = rootBand.getChildrenByName("main");
        for (int i = 0; i < rows.size(); i++) {
            BandData bd = (BandData)rows.get(i);
            answer.append(bd.getData().get("enum")+"\t");
            answer.append(bd.getData().get("designation")+"\t");
            answer.append(bd.getData().get("qlevel")+"\t");
            answer.append(bd.getData().get("amount")+"\t");
            answer.append(bd.getData().get("companies")+"\t");
            answer.append(bd.getData().get("developers")+"\n");
        }

        return answer.toString().getBytes();
    }
}
