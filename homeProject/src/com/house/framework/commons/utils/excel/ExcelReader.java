package com.house.framework.commons.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel 读取工具
 * @author xfb
 *
 */
public class ExcelReader {

	/**
	 * excel 工作部
	 */
	private Workbook wb = null;

	//private InputStream input = null;
	
	private Sheet sheet = null;

	private int dataRowNum = 1;
	
	private int sheetIndex = 0;

	private List<List<Object>> dataRows = new ArrayList<List<Object>>();

	public ExcelReader(File excel) {
		this(convertToStream(excel), FilenameUtils.getExtension(excel.getName()));
	}

	public ExcelReader(InputStream input) {
		this(input, "xls");
	}

	public ExcelReader(InputStream input, String ext) {
		this(input,ext,1,0);
	}
	
	public ExcelReader(InputStream input, String ext,int dataRowNum,int sheetIndex) {
		try {
			this.dataRowNum = dataRowNum;
			this.sheetIndex = sheetIndex;
			
			this.read(input, ext,dataRowNum,sheetIndex);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void read(InputStream input, String ext,int dataRowNum,int sheetIndex) throws Exception { 
		
		if (("xls").equalsIgnoreCase(ext)) {
			wb = new HSSFWorkbook(input);
		} else if ("xlsx".equalsIgnoreCase(ext)) {
			wb = new XSSFWorkbook(input);
		} else {
			throw new Exception("格式错误.");
		}

		if (this.wb.getNumberOfSheets() <= sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.wb.getSheetAt(sheetIndex);

		Iterator<Row> it = this.sheet.iterator();
		int i = 0;
		while (it.hasNext()) {
			Row row = it.next();
			Iterator<Cell> cells = row.iterator();
			List<Object> dataRow = new ArrayList<Object>();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				int index = cell.getColumnIndex();

				switch (cell.getCellType()) {
				case 0: //数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						//dataRow.set(index, date);
						setData(dataRow,index,date);
					} else {
						setData(dataRow,index,cell.getNumericCellValue());
					}
					break;
				case 1: //字符串
					setData(dataRow,index,cell.getStringCellValue());
					break;
				case 2:
					dataRow.add(index, cell.getCellFormula());
					break;
				case 3:
					dataRow.add(index, "");
					break;
				case 4:
					dataRow.add(index, cell.getBooleanCellValue());
					break;
				default:
					dataRow.add(index, "");
					break;
				}
			}
			if (i >= dataRowNum) {
				this.dataRows.add(dataRow);
			}
			i++;
		}
	}
	
	public List<List<Object>> getDataRows(){
		return this.dataRows;
	}
	
	private void setData(List<Object> dataRow,int index,Object obj){
		if(dataRow.size() < index){
			for(int i = dataRow.size();i< index;i++ ){
				dataRow.add(null);
			}
		}
		dataRow.add(obj);
	}

	public static FileInputStream convertToStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("file not ");
		}
	}
	
	
}
