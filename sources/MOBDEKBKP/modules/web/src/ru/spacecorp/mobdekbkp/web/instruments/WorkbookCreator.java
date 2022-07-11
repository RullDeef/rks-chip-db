package ru.spacecorp.mobdekbkp.web.instruments;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by Stepanov_ME on 26.11.2018.
 */
public class WorkbookCreator {
    private HSSFWorkbook wb;
    private Sheet sheet;
    private Row row;
    private int curRow;

    public WorkbookCreator() {
        wb = new HSSFWorkbook();
        sheet = wb.createSheet();
        curRow=-1;
    }

    public void createRow() {
        ++curRow;
        row = sheet.createRow(curRow);
    }

    public Cell cell(int i) {
        return row.createCell(i);
    }

    public void setAutosize(int columnNum){
        sheet.autoSizeColumn(columnNum);
    }

    public HSSFWorkbook getWorkbook(){
        return wb;
    }
}
