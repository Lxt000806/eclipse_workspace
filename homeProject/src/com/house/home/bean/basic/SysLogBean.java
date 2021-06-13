package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SysLog信息bean
 */
public class SysLogBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="id", order=1)
	private Integer id;
	@ExcelAnnotation(exportName="title", order=2)
	private String title;
	@ExcelAnnotation(exportName="type", order=3)
	private String type;
	@ExcelAnnotation(exportName="appType", order=4)
	private String appType;
	@ExcelAnnotation(exportName="requestUrl", order=5)
	private String requestUrl;
	@ExcelAnnotation(exportName="userAgent", order=6)
	private String userAgent;
	@ExcelAnnotation(exportName="method", order=7)
	private String method;
	@ExcelAnnotation(exportName="params", order=8)
	private String params;
	@ExcelAnnotation(exportName="description", order=9)
	private String description;
	@ExcelAnnotation(exportName="remoteAddr", order=10)
	private String remoteAddr;
	@ExcelAnnotation(exportName="operId", order=11)
	private String operId;
    	@ExcelAnnotation(exportName="operDate", pattern="yyyy-MM-dd HH:mm:ss", order=12)
	private Date operDate;
	@ExcelAnnotation(exportName="clazz", order=13)
	private String clazz;
	@ExcelAnnotation(exportName="responseContent", order=14)
	private String responseContent;

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	public String getAppType() {
		return this.appType;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public String getRequestUrl() {
		return this.requestUrl;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public String getUserAgent() {
		return this.userAgent;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getMethod() {
		return this.method;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getParams() {
		return this.params;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	public String getRemoteAddr() {
		return this.remoteAddr;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	
	public String getOperId() {
		return this.operId;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	
	public Date getOperDate() {
		return this.operDate;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public String getClazz() {
		return this.clazz;
	}
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	
	public String getResponseContent() {
		return this.responseContent;
	}

}
