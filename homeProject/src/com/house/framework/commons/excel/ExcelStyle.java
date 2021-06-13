package com.house.framework.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 设置Excel样式
 *
 */
public class ExcelStyle {
	
	/**
	 * 设置头样式
	 * @param workbook
	 * @param headStyle
	 * @return
	 */
	public static CellStyle setHeadStyle(SXSSFWorkbook workbook, CellStyle headStyle) {

		headStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headStyle.setBorderRight(CellStyle.BORDER_THIN);
		headStyle.setBorderTop(CellStyle.BORDER_THIN);
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font font = workbook.createFont(); // 生成字体   
		//font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		  
		headStyle.setFont(font); // 把字体应用到当前的样样式 
		return headStyle;

	}

	/**
	 * 设置数据体样式
	 * @param workbook
	 * @param bodyStyle
	 * @return
	 */
	public static CellStyle setBodyStyle(SXSSFWorkbook workbook, CellStyle bodyStyle) {
		bodyStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		bodyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyStyle.setAlignment(CellStyle.ALIGN_LEFT);
		bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		Font font = workbook.createFont(); //生成字体   
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		
		bodyStyle.setFont(font); //把字体应用到当前的样样式   
		return bodyStyle;
	}
	
	public static CellStyle setCommonHeadStyle(SXSSFWorkbook workbook, CellStyle style) {

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER); // 所有标题居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true); //头部样式设置强制换行
		return style;

	}
}
