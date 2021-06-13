package com.house.framework.commons.utils;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook; 

public class SXSSFWorkBookUtil {

    public void testWorkBook() {

       try{
           long curr_time=System.currentTimeMillis();
           int rowaccess=100;//内存中缓存记录行数
           /*keep 100 rowsin memory,exceeding rows will be flushed to disk*/
           SXSSFWorkbook wb = new SXSSFWorkbook(rowaccess); 
           int sheet_num=3;//生成3个SHEET

           for(int i=0;i<sheet_num;i++){
              Sheet sh = wb.createSheet();
              //每个SHEET有60000ROW
              for(int rownum = 0; rownum < 60000; rownum++) {
                  Row row = sh.createRow(rownum);
                  //每行有10个CELL
                  for(int cellnum = 0; cellnum < 10; cellnum++) {
                     Cell cell = row.createCell(cellnum);
                     String address = new CellReference(cell).formatAsString();
                     cell.setCellValue(address);
                  }
                  //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                  if(rownum%rowaccess==0){
                     ((SXSSFSheet)sh).flushRows();
                  }
              }
           }

           /*写数据到文件中*/
           FileOutputStream os = new FileOutputStream("d:/data/poi/biggrid.xlsx");    
           wb.write(os);
           os.close();
           /*计算耗时*/
           System.out.println("耗时:"+(System.currentTimeMillis()-curr_time)/1000);
       } catch(Exception e) {
           e.printStackTrace();
       }
    }
}
