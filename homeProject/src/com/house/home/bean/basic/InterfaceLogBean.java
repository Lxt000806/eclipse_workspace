package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * InterfaceLog信息bean
 */
public class InterfaceLogBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="id", order=1)
	private Integer id;
	@ExcelAnnotation(exportName="clazz", order=2)
	private String clazz;
	@ExcelAnnotation(exportName="javaFun", order=3)
	private String javaFun;
    	@ExcelAnnotation(exportName="requestTime", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date requestTime;
    	@ExcelAnnotation(exportName="returnTime", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date returnTime;
	@ExcelAnnotation(exportName="content", order=6)
	private String content;
	@ExcelAnnotation(exportName="returnCode", order=7)
	private String returnCode;
	@ExcelAnnotation(exportName="returnInfo", order=8)
	private String returnInfo;
	@ExcelAnnotation(exportName="responseContent", order=9)
	private String responseContent;
	@ExcelAnnotation(exportName="requestContent", order=10)
	private String requestContent;
	@ExcelAnnotation(exportName="requestIp", order=11)
	private String requestIp;

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public String getClazz() {
		return this.clazz;
	}
	public void setJavaFun(String javaFun) {
		this.javaFun = javaFun;
	}
	
	public String getJavaFun() {
		return this.javaFun;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	public Date getRequestTime() {
		return this.requestTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	public Date getReturnTime() {
		return this.returnTime;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	public String getReturnCode() {
		return this.returnCode;
	}
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
	
	public String getReturnInfo() {
		return this.returnInfo;
	}
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	
	public String getResponseContent() {
		return this.responseContent;
	}
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
	
	public String getRequestContent() {
		return this.requestContent;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	
	public String getRequestIp() {
		return this.requestIp;
	}

}
