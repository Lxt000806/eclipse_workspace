package com.house.framework.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.excel.DataToExcel;
import com.house.framework.commons.utils.CommonUtils;
import com.house.framework.commons.utils.SpringContextHolder;

@Controller
@RequestMapping("/admin/outExl")
public class DataToExlController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(DataToExlController.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/gridSQLToExl")
	public void gridSQLToExl(HttpServletRequest request,
			HttpServletResponse response, String outExcelSql) throws ServletException, IOException{
		
		if(!CommonUtils.isNullOrEmpty(outExcelSql)){
			/**Excel文件存放路径**/
			String path = request.getSession().getServletContext().getRealPath("/");
			String filepath = path+"temp/excel/";
			if(!new File(filepath).exists()){
				new File(filepath).mkdirs();
			}
	        /**获取数据源**/
			PreparedStatement ps=null;
			ResultSet rs=null;
			List<Map<String, Object>> colsList = new ArrayList<Map<String, Object>>();
	        List<Map<String, Object>> rowsList = new ArrayList<Map<String, Object>>();
			try {
				JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
				ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(outExcelSql);
		        rs = ps.executeQuery();
		        ResultSetMetaData rsmd=rs.getMetaData();
		        /**excel表头**/
		        for (int num = 1; num < rsmd.getColumnCount()+1;num ++) {
		        	Map<String, Object> map = new HashMap();
		        	map.put("hide", "false");/**是否隐藏**/
		        	map.put("titleName", rsmd.getColumnName(num));/**列名**/
		        	colsList.add(map);
				}
		        /**excel体**/
		        while(rs.next()){
		        	Map<String, Object> map = new HashMap();
		        	for (int num =1; num < rsmd.getColumnCount()+1;num ++) {
			        	map.put(String.valueOf(num), rs.getString(num));/**列名**/
					}
		        	rowsList.add(map);
		        }
		        
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}finally{
				try {
					if(rs != null)rs.close();
					if(ps != null)ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			/**建表下载**/
			DataToExcel data2exl = new DataToExcel();
			String fileName = data2exl.dataToExcel(colsList,rowsList,filepath);
			data2exl.DownloadFiles(request,response,fileName,filepath);
			
		}
	}
	
	
}
