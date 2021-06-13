package com.house.framework.commons.excel;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.CommonUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.CustomerService;

public class ExcelExportUtils<T> {
	private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtils.class);
	private int rowaccess = 100;//内存中缓存记录行数
	/**
	 * 可导出当前excel表格数据，并汇总
	 * @param title
	 * @param dataset
	 * @param hiddenList
	 * @param titleList
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public SXSSFWorkbook exportExcel(String title, List<?> dataset, List<String> columnList, 
			List<String> titleList, List<String> sumList) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(rowaccess); 
		try {
			//首先检查数据看是否是正确的   
			if (dataset == null || title == null) {
				throw new Exception("传入的数据异常！");   
			}
			Iterator<?> its = dataset.iterator();
			if (!its.hasNext()){
				throw new Exception("传入的数据异常！");   
			}
			T ts = (T) its.next();
			Sheet sheet = workbook.createSheet(title);
			sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节 
			CellStyle headStyle = workbook.createCellStyle();
			headStyle = ExcelStyle.setHeadStyle(workbook, headStyle);
			
			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle = ExcelStyle.setBodyStyle(workbook, bodyStyle);
			
			//add by zzr 2017/12/20   数字类型的单元格样式 begin
			CellStyle numberBodyStyle = workbook.createCellStyle();            
			numberBodyStyle = ExcelStyle.setBodyStyle(workbook, numberBodyStyle);
			numberBodyStyle.setAlignment(CellStyle.ALIGN_RIGHT); 
			//add by zzr 2017/12/20   数字类型的单元格样式 end
			
			if (titleList==null || titleList.size()==0){
				throw new Exception("传入的数据异常！");
			}else{
				if (ts instanceof Map){
					// 产生表格标题行
					Row row = sheet.createRow(0);
					int j=0;
					for(String str: titleList){  
						Cell cell = row.createCell(j);
						cell.setCellStyle(headStyle);
	                    cell.setCellValue(str);
	                    j++;
					}
					Double[] hj = new Double[columnList.size()];
					// 循环整个集合   
					for(int i=0; i<dataset.size(); i++){
						row = sheet.createRow(i + 1); //第一行为标题列, 从1开始写excel
						Map<String,Object> map2 = (Map<String, Object>) dataset.get(i);
						int k = 0;
						for (String str : columnList){
							Cell cell = row.createCell(k);
							if (map2.get(str)==null){
								cell.setCellValue("");
							}else{
								String st = String.valueOf(map2.get(str));
								String type = map2.get(str).getClass().getName();
								if("java.sql.Timestamp".equals(type)){
									try {
										cell.setCellValue(st.substring(0, st.indexOf(".")));
									} catch (Exception e) {
										cell.setCellValue(st);
									}
								}else if("java.math.BigDecimal".equals(type) || "java.lang.Double".equals(type) 
										|| "java.lang.Integer".equals(type)){
									try {
										st = Arith.strNumToStr(st);
										cell.setCellValue(Double.parseDouble(st));
									} catch (Exception e) {
										cell.setCellValue(st);
									}
								}else{
									cell.setCellValue(st);
								}
								if (hj[k]==null){
									hj[k] = (double) 0;
								}
								if (sumList!=null && sumList.size()>0 && StringUtils.isNotBlank(sumList.get(k))){
									double da = 0;
									try{
										da = Double.parseDouble(st);
										hj[k] = Arith.add(hj[k], da);
									}catch(Exception e){
										
									}
								}
							}
							if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){ 
								cell.setCellStyle(numberBodyStyle); // add by zzr 2017/12/20 数字类型右对齐
							}else{
								cell.setCellStyle(bodyStyle);
							}
							k++;
						}
						//每当行数达到设置的值就刷新数据到硬盘,以清理内存
		                if(i%rowaccess==0){
		                   ((SXSSFSheet)sheet).flushRows();
		                }
					}
					if (sumList!=null && sumList.size()>0){
						row = sheet.createRow(dataset.size() + 1);
						int m=0;
						for(Double str : hj){  
							Cell cell = row.createCell(m);
							cell.setCellStyle(headStyle);
		                    if (StringUtils.isNotBlank(sumList.get(m))){
								NumberFormat nf = NumberFormat.getInstance();
								nf.setMaximumFractionDigits(2);
								if (str!=null){
									cell.setCellValue(Double.parseDouble(nf.format(str).replace(",", "")));
								}else{
									cell.setCellValue(0);
								}
							}else{
								if (m==0){
									cell.setCellValue("合计");
								}else{
									cell.setCellValue("");
								}
							}
		                    m++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return workbook;
	}
	/**
	 * 可导出当前excel表格数据，不汇总
	 * @param title
	 * @param dataset
	 * @param hiddenList
	 * @param titleList
	 * @return
	 */
	public SXSSFWorkbook exportExcel(String title, List<?> dataset, List<String> columnList, List<String> titleList) {
		return exportExcel(title,dataset,columnList,titleList,null);
	}
	/**  
	 * 导出查询的所有数据，不导出隐藏列
	 * @param title 标题  
	 * @param dataset 集合  
	 * @param out  输出流  
	 */
	public SXSSFWorkbook exportExcel(String title, List<?> dataset, List<String> columnList) {
		return exportExcel(title,dataset,columnList,null,null);
	}
	/**  
	 * 导出查询的所有数据
	 * @param title 标题  
	 * @param dataset 集合  
	 * @param out  输出流  
	 */
	public SXSSFWorkbook exportExcel(String title, List<T> dataset) {
		return exportExcel(title,dataset,null,null,null);
	}
	
	public String getValue(Object value, Object[] meta) {
		String textValue = "";
		if (value == null)
			return textValue;

		if (value instanceof Boolean) {
			boolean bValue = (Boolean) value;
			textValue = "是";
			if (!bValue) {
				textValue = "否";
			}
		} else if (value instanceof Date) {
			String pattern = (String)meta[2];
			if(StringUtils.isBlank(pattern))pattern = "yyyy-MM-dd";
			textValue = CommonUtils.parse2StandardDate((Date)value, pattern);
		} else{
			textValue = value.toString();
		}

		return textValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		
		List list = new ArrayList();
		for (int i = 0; i < 5000; i++) {
			Testpojo testpojo = new Testpojo();
			testpojo.setAge(new Integer(i));
			testpojo.setCreatetime(new Date());
			testpojo.setLoginname("chenld_" + i);
			testpojo.setAge(Integer.valueOf(i));
			testpojo.setUsername("chenliangdeng_" + i);
			list.add(testpojo);
		}
		OutputStream out = new FileOutputStream("C:\\testPojo.xls");
		Long l = System.currentTimeMillis();
		ExcelExportUtils<Testpojo> ex = new ExcelExportUtils<Testpojo>();
		SXSSFWorkbook workbook = ex.exportExcel("测试", list);
		workbook.write(out);
		out.close();
		Long s = System.currentTimeMillis();
		System.out.println("总共耗时：" + (s - l));

	}
	/**
	 * 客户信息查询专用导出excel
	 * @param title
	 * @param dataset
	 * @param hiddenList
	 * @param titleList
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public SXSSFWorkbook exportExcelForCustomer(String title, List<?> dataset, List<String> columnList, 
			List<String> titleList, List<String> sumList,Customer customer,UserContext uc) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(rowaccess); 
		try {
			Sheet sheet = workbook.createSheet(title);
			sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节 
			CellStyle headStyle = workbook.createCellStyle();
			headStyle = ExcelStyle.setHeadStyle(workbook, headStyle);
			
			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle = ExcelStyle.setBodyStyle(workbook, bodyStyle);
			
			//add by zzr 2017/12/20   数字类型的单元格样式 begin
			CellStyle numberBodyStyle = workbook.createCellStyle();            
			numberBodyStyle = ExcelStyle.setBodyStyle(workbook, numberBodyStyle);
			numberBodyStyle.setAlignment(CellStyle.ALIGN_RIGHT); 
			//add by zzr 2017/12/20   数字类型的单元格样式 end
			
			if (titleList==null || titleList.size()==0){
				throw new Exception("传入的数据异常！");
			}else{
					// 产生表格标题行
				Row row = sheet.createRow(0);
				int j=0;
				for(String str: titleList){  
					Cell cell = row.createCell(j);
					cell.setCellStyle(headStyle);
	                cell.setCellValue(str);
	                j++;
				}
				int avgNum=10000;//每批次查询和写入excel的数据量
				int beginNum=0,endNum=avgNum;
				CustomerService customerService=(CustomerService)SpringContextHolder.getBean(CustomerService.class);
				Cell cell=null;
				Map<String,Object> map2=null;
				String st="";
				String type="";
				do{
					Page<Map<String, Object>> page = new Page<Map<String, Object>>();
					page.setPageSize(avgNum);
					page.setPageNo(endNum/avgNum);
					dataset=customerService.findPageBySql_xxcx(page, customer, uc).getResult();
					// 循环整个集合   
					for(int i=0; i<dataset.size(); i++){
						row = sheet.createRow(i + 1+beginNum); //第一行为标题列, 从1开始写excel 再加起始行数
						map2 = (Map<String, Object>) dataset.get(i);
						int k = 0;
						for (String str : columnList){
							cell = row.createCell(k);
							if (map2.get(str)==null){
								cell.setCellValue("");
							}else{
								st = String.valueOf(map2.get(str));
								type = map2.get(str).getClass().getName();
								if("java.sql.Timestamp".equals(type)){
									try {
										cell.setCellValue(st.substring(0, st.indexOf(".")));
									} catch (Exception e) {
										cell.setCellValue(st);
									}
								}else if("java.math.BigDecimal".equals(type) || "java.lang.Double".equals(type) 
										|| "java.lang.Integer".equals(type)){
									try {
										st = Arith.strNumToStr(st);
										cell.setCellValue(Double.parseDouble(st));
									} catch (Exception e) {
										cell.setCellValue(st);
									}
								}else{
									cell.setCellValue(st);
								}
							}
							if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){ 
								cell.setCellStyle(numberBodyStyle); // add by zzr 2017/12/20 数字类型右对齐
							}else{
								cell.setCellStyle(bodyStyle);
							}
							k++;
						}
						//每当行数达到设置的值就刷新数据到硬盘,以清理内存
						if(i%rowaccess==0){
							((SXSSFSheet)sheet).flushRows();
						}
					}
					beginNum+=avgNum;
					endNum+=avgNum;
				}while(dataset.size()==avgNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return workbook;
	}
}
