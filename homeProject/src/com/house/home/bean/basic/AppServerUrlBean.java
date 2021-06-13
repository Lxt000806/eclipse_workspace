package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * AppServerUrl信息bean
 */
public class AppServerUrlBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="descr", order=1)
	private String descr;
	@ExcelAnnotation(exportName="url", order=2)
	private String url;
	@ExcelAnnotation(exportName="port", order=3)
	private String port;

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getPort() {
		return this.port;
	}

}
