package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * InterfaceLog信息
 */
@Entity
@Table(name = "tInterfaceLog")
public class InterfaceLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer id;
	@Column(name = "Clazz")
	private String clazz;
	@Column(name = "JavaFun")
	private String javaFun;
	@Column(name = "RequestTime")
	private Date requestTime;
	@Column(name = "ReturnTime")
	private Date returnTime;
	@Column(name = "Content")
	private String content;
	@Column(name = "ReturnCode")
	private String returnCode;
	@Column(name = "ReturnInfo")
	private String returnInfo;
	@Column(name = "ResponseContent")
	private String responseContent;
	@Column(name = "RequestContent")
	private String requestContent;
	@Column(name = "RequestIp")
	private String requestIp;
	
	public InterfaceLog(){
	}
	
	public InterfaceLog(String clazz, String javaFun){
		this.clazz = clazz;
		this.javaFun = javaFun;
	}

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
