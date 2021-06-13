package com.house.framework.commons.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.parameters.ParametersExporterInput;

/** 
* @ClassName: JasperReportUtils 
* @Description: JasperReport报表打印工具类
* @author liuxiaobin
* @date 2015年9月22日 上午11:31:23 
*  
*/
@SuppressWarnings("deprecation")
public class JasperReportUtils{

	protected static final Logger logger = LoggerFactory.getLogger(JasperReportUtils.class);
	
	/**
	 * 
	* @Title: getConnection 
	* @Description: 获取数据库连接
	* @param @return
	* @param @throws SQLException    设定文件 
	* @return Connection    返回类型 
	* @throws
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			ComboPooledDataSource dataSource = SpringContextHolder.getBean("dataSource");
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 
	* @Title: getReportPath 
	* @Description: 获取报表路径
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getReportPath(){
		return SystemPath.getSysPath()+"/WEB-INF/jsp/jasper/";
	}

	/**
	 * 
	* @Title: printPDF 
	* @Description: 打印生成pdf文件
	* @param @param reportFile
	* @param @param param
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean printPDF(HttpServletResponse response,String reportFile,Map<String,Object>param){
      Connection con = getConnection();
      try {
    	  	File jasperFile=new File(getReportPath()+reportFile);
            JasperReport jasperReport= (JasperReport)JRLoader.loadObjectFromFile(jasperFile.getPath());
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,param,con);//new JREmptyDataSource()
            response.setContentType("application/pdf;charset=utf-8");
            response.setHeader("Content-Disposition", "inline;filename="+URLEncoder.encode("打印报表.pdf" ,"utf-8"));
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            if(StringUtils.isEmpty((String)param.get("isPreview")) || !"true".equals(param.get("isPreview"))){
                configuration.setPdfJavaScript("this.print();");
            }
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		return true;
	}
	
	/**
	* @Title: printPDF 
	* @Description: 打印生成pdf文件
	* @param @param reportFile
	* @param @param param
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean printMultiPDF(HttpServletResponse response,String reportFile,Map<String,Object>param){
      Connection con = getConnection();
      try {
    	  	List jasperPrintList = new ArrayList();
    	  	String[] arr = reportFile.split(",");
    	  	if (arr!=null && arr.length>0){
    	  		for (String str : arr){
    	  			jasperPrintList.add(JasperFillManager.fillReport((JasperReport)JRLoader
        	  				.loadObjectFromFile(new File(getReportPath()+str).getPath()),param,con));
    	  		}
    	  	}
            response.setContentType("application/pdf;charset=utf-8");
            response.setHeader("Content-Disposition", "inline;filename="+URLEncoder.encode("打印报表.pdf" ,"utf-8"));
            JRPdfExporter exporter = new JRPdfExporter();
            Map<JRExporterParameter, Object> parmetersMap = new HashMap<JRExporterParameter, Object>();
            parmetersMap.put(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            ParametersExporterInput pa = new ParametersExporterInput(parmetersMap);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.setExporterInput(pa);
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            if(StringUtils.isEmpty((String)param.get("isPreview")) || !"true".equals(param.get("isPreview"))){
                configuration.setPdfJavaScript("this.print();");
            }
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		return true;
	}
	
	/**
	 * 
	* @Title: printPDF 
	* @Description: 传入一个List<T>结果集作为数据源
	* @return boolean    返回类型 
	* @throws
	 */
	public static <T> boolean printPDF(HttpServletResponse response,String reportFile,List<T> detailList,Map<String,Object>param){
	      try {
	    	  	File jasperFile=new File(getReportPath()+reportFile);
	            JasperReport jasperReport= (JasperReport)JRLoader.loadObjectFromFile(jasperFile.getPath());
	            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(detailList);
	            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,param,ds);//new JREmptyDataSource()
	            response.setContentType("application/pdf;charset=utf-8");
	            response.setHeader("Content-Disposition", "inline;filename="+URLEncoder.encode("打印报表.pdf" ,"utf-8"));
	            JRPdfExporter exporter = new JRPdfExporter();
	            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
	            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	            exporter.setConfiguration(configuration);	
	            if(StringUtils.isEmpty((String)param.get("isPreview")) || !"true".equals(param.get("isPreview"))){
	                configuration.setPdfJavaScript("this.print();");
	            }
	            exporter.exportReport();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return true;
		}
	
	/**
	 * 
	* @Title: printXLS
	* @Description: 打印生成excel文件
	* @param @param reportFile
	* @param @param param
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean printXLS(HttpServletResponse response,String reportFile,Map<String,Object>param){
        Connection con = getConnection();
		try {
			String title = (String)param.get("title");
			if(title == null){
				title = "打印报表";
			}
			title = title + ".xls";
			
            File jasperFile=new File(getReportPath()+reportFile);
            JasperReport jasperReport= (JasperReport)JRLoader.loadObjectFromFile(jasperFile.getPath());
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,param,con);
            response.setContentType("application/vnd_ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "inline;filename="+URLEncoder.encode(title ,"utf-8"));
            JRXlsExporter exporter=new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setOnePagePerSheet(false);
            configuration.setWhitePageBackground(false);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		return true;
	}
	
	/**
	* @Title: savePDF 
	* @Description: 生成pdf文件保存到本地
	* @param @param reportFile
	* @param @param param
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean saveMultiPDFStream(String reportFile,Map<String,Object>param,String filePath){
      Connection con = getConnection();
      	try {
      		String[] arr = reportFile.split(",");
      		if (arr!=null && arr.length>0){
      			for (String str : arr) {
      				OutputStream outputStream = null;
      				try {
      					Map<String, Object> parameterValues = new HashMap<String, Object>();
      					parameterValues.putAll(param);
      					String jasperName = StringUtils.split(str,".")[0];
      					outputStream = new FileOutputStream(filePath+System.currentTimeMillis()+jasperName+".pdf");
      					JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)JRLoader
      							.loadObjectFromFile(new File(getReportPath()+str).getPath()),parameterValues,con);
      					JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
      					outputStream.flush();
      				} catch (Exception e) {
      					e.printStackTrace();
      				}finally{
      					if (outputStream != null) {
      						try {
      							outputStream.close();
      						} catch (IOException e) {
      							e.printStackTrace();
      						}
      					}
      				}
      			}
      		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
