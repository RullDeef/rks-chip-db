package ru.spacecorp.mobdekbkp.core.reports.customtemplates;

import com.haulmont.yarg.formatters.CustomReport;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;

import java.util.List;
import java.util.Map;

public class NeedOne implements CustomReport {
    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {

        StringBuilder answer = new StringBuilder();
        // report name
        answer.append((String) rootBand.getChildByName("reportname").getData().get("name")).append("\n\n");

        // typonominal header
        answer.append((String) rootBand.getChildByName("chdes").getData().get("hdesignation")).append(": ");
        answer.append((String) rootBand.getChildByName("cdes").getData().get("designation")).append("\n");
        answer.append((String) rootBand.getChildByName("chtype").getData().get("htype")).append(": ");
        answer.append((String) rootBand.getChildByName("ctype").getData().get("type")).append("\n");
        answer.append((String) rootBand.getChildByName("chqlevel").getData().get("hqlevel")).append(": ");
        answer.append((String) rootBand.getChildByName("cqlevel").getData().get("qlevel")).append("\n");
        answer.append((String) rootBand.getChildByName("chyr").getData().get("hyr")).append(": ");
        answer.append((String) rootBand.getChildByName("cyr").getData().get("yr")).append("\n");

        //table header
        answer.append((String) rootBand.getChildByName("hmain").getData().get("henum")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hcompany")).append("\t");
        answer.append((String) rootBand.getChildByName("hmain").getData().get("hamount")).append("\n");

        List rows = rootBand.getChildrenByName("main");
        for (int i = 0; i < rows.size(); i++) {
            BandData bd = (BandData) rows.get(i);
            answer.append(bd.getData().get("enum1") + "\t");
            answer.append(bd.getData().get("company") + "\t");
            answer.append(bd.getData().get("amount") + "\n");
        }
        return answer.toString().getBytes();
    }
}
