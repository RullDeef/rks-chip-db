package ru.spacecorp.mobdekbkp.core.tsniimashImport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stepanov_ME on 28.01.2019.
 */
public class DataConverter {

    private OuterDbFailData outerDbFailData;
    private AdditData additData;

    public DataConverter() {
        outerDbFailData = new OuterDbFailData();
        additData = new AdditData();
    }

    public void convertData(String attrValue, String value) {
        switch (attrValue) {
            case StringHolder.DATE_MANUFACTURE: {
                outerDbFailData.setManufactureDate(parseDate(value));
                break;
            }
            case StringHolder.TYPONOMINAL: {
                outerDbFailData.setTyponominal(value);
                break;
            }
            case StringHolder.DESCRIPTION: {
                outerDbFailData.setDescription(value);
                break;
            }
            case StringHolder.COMISSION_RESUME: {
                outerDbFailData.setComissionResume(value);
                break;
            }
            case StringHolder.CLAIMED_COMPANY: {
                outerDbFailData.setClaimedCompany(value);
                break;
            }
            case StringHolder.MANUFACTURER: {
                outerDbFailData.setManufacturer(value);
                break;
            }
            case StringHolder.DATE_DETECT: {
                outerDbFailData.setFailDate(parseDate(value));
                break;
            }
            case StringHolder.DOC_DATE: {
                outerDbFailData.setDocDate(parseDate(value));
                break;
            }
            case StringHolder.DOC_NUM: {
                outerDbFailData.setClaimDocs(value);
                break;
            }
            case StringHolder.INDEX: {
                outerDbFailData.setIndex(value);
                break;
            }
            case StringHolder.PART: {
                outerDbFailData.setPart(value);
                break;
            }
            case StringHolder.VISIBLE_FAIL: {
                outerDbFailData.setVisibleFail(value);
                break;
            }
            case StringHolder.WORK_FACT: {
                outerDbFailData.setWorkFact(value);
                break;
            }
            case StringHolder.WORK_GUARANTEE: {
                outerDbFailData.setWorkGuarantee(value);
                break;
            }
            case StringHolder.PARENT_ID: {
                additData.setParentId(value);
                break;
            }
            case StringHolder.FAIL_TYPE: {
                additData.setFailType(value);
                break;
            }
            case StringHolder.FAIL_TYPE_COMMENT: {
                additData.setFailComment(value);
                break;
            }
        }
    }

    public OuterDbFailData getOuterDbFailData() {
        return outerDbFailData;
    }

    public AdditData getAdditData() {
        return additData;
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            return parser.parse(dateString);
        } catch (Exception e) {
            //wrong format
            return null;
        }
    }
}
