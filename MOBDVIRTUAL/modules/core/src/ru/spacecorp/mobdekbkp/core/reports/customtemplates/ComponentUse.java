package ru.spacecorp.mobdekbkp.core.reports.customtemplates;

import com.haulmont.yarg.formatters.CustomReport;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;

import java.util.List;
import java.util.Map;

public class ComponentUse implements CustomReport {
    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {
        StringBuilder answer = new StringBuilder();

        answer.append((String) rootBand.getChildByName("reportname").getData().get("name")).append("\n\n");

        answer.append((String) rootBand.getChildByName("headerup").getData().get("hsp1")).append("\t");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hclass")).append("\t");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("htype")).append("\t");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hlevel")).append("\t");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hmans")).append("\t");
        answer.append((String) rootBand.getChildByName("headerup").getData().get("hcountry")).append("\n");

        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("sp1")).append("\t");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("rowcl")).append("\t");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("type")).append("\t");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("qualityLevel")).append("\t");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("mans")).append("\t");
        answer.append((String) rootBand.getChildByName("componentCommons").getData().get("country")).append("\n");

        answer.append((String) rootBand.getChildByName("headerdown").getData().get("henum")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hdevpt")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hdevptdev")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hdev")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hdevprod")).append("\t");
        answer.append((String) rootBand.getChildByName("headerdown").getData().get("hyr")).append("\n");

        List rows = rootBand.getChildrenByName("mainRows");
        for (int i = 0; i < rows.size(); i++) {
            BandData bd = (BandData) rows.get(i);
            answer.append(bd.getData().get("enum1") + "\t");
            answer.append(bd.getData().get("devpt") + "\t");
            answer.append(bd.getData().get("devptdev") + "\t");
            answer.append(bd.getData().get("dev") + "\t");
            answer.append(bd.getData().get("devprod") + "\t");
            answer.append(bd.getData().get("yr") + "\n");
        }

        return answer.toString().getBytes();
    }
}
