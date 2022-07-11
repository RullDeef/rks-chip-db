package com.company.importmodule.core;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Stepanov_ME on 23.01.2018.
 */
public class ExelWork {
    private Row row;
    private Stack<String>[] data;
    public Iterator<Row> it;

    //количество строк
    public int row_count;
    //количество столбцов
    public int column_count;
    private HashMap<String,Integer> hashMap;
    private FileLoader fileLoader= AppBeans.get(FileLoader.class);

    public ExelWork(FileDescriptor fileDescriptor) throws IOException,FileStorageException
    {
        this.getExelData(fileDescriptor);
    }

    private void getExelData(FileDescriptor fileDescriptor) throws IOException,FileStorageException
    {
        //InputStream in=new FileInputStream(path);
        InputStream in=fileLoader.openStream(fileDescriptor);
        HSSFWorkbook wb = new HSSFWorkbook(in);
        Sheet sheet = wb.getSheetAt(0);
        hashMap=new HashMap();
        row_count=0;
        //row_count=sheet.getLastRowNum();
        it=sheet.iterator();
        while(it.hasNext()){
            row=it.next();
            ++row_count;
        }
        it=sheet.iterator();
        row=it.next();

        column_count=row.getLastCellNum();

        for(int i=0;i<column_count;++i)
        {
            hashMap.put(row.getCell(i).toString(),i);
        }

        data=new Stack[column_count];
        for(int i=0;i<column_count;++i)
        {
            data[i]= new Stack<>();
        }
    }

    public void getData(String name,ArrayList<String> errorList)
    {
        boolean empty=true;
        row = it.next();
        Cell cell;
        for (int g = 0; g < column_count; ++g) {
            String value;
            //empty=true;
            cell = row.getCell(g);

            if (cell == null) {
                value = "";
            } else {
                int check = cell.getCellType();
                switch (check) {
                    case Cell.CELL_TYPE_STRING: {
                        value = cell.getStringCellValue();
                        break;
                    }
                    case Cell.CELL_TYPE_NUMERIC: {
                        value = cell.toString();
                        break;
                    }
                    case Cell.CELL_TYPE_BOOLEAN: {
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    }
                    default: {
                        errorList.add("Ошибка данных "+name+" column "+g+" row "+row.getRowNum());
                        value = "";
                    }
                }
            }
            if(!value.contentEquals("")){
                empty=false;
            }
            data[g].push(value);
        }
        if(empty){
            for (int g = 0; g < column_count; ++g) {
                data[g].pop();
            }
        }

    }

    public Boolean checkData(List<String> column_list, String name)
    {
        String temp=data[hashMap.get(name)].peek();
        if(column_list.contains(temp))
        {
            for(int i=0;i<column_count;++i)
            {
                data[i].pop();
            }
            return true;
        }
        return false;
    }

    public String getProperty(String name)
    {
        Integer i=hashMap.get(name);
        if(i!=null)
        {
            return data[i].pop();
        }
        else {
            return "";
        }
    }

    public void setCorrectSize(){
        row=it.next();
        row=it.next();
        row_count-=3;
    }

    public int getDataCount(){
        if(data==null){
            return 0;
        }
        if(data.length==0){
            return 0;
        }
        return data[0].size();
    }
}
