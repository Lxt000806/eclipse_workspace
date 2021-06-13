package com.house.home.entity.basic;

import java.util.Date;
import java.util.Map;

import javax.management.loading.PrivateClassLoader;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.StringUtils;

/**
 * SysLog信息
 */
@Entity
@Table(name = "tSysLog")
public class SysLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	public static final String TYPE_BS = "1";//来自于BS系统
	public static final String TYPE_PROJECTMANAPP = "2";//来自于项目经理系统
	public static final String TYPE_APP = "3";//来自于手机APP
	public static final String TYPE_ACCESS = "1";//业务日志
	public static final String TYPE_EXCEPTION = "2";//异常日志
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer id;
	@Column(name = "Title")
	private String title;
	@Column(name = "Type")
	private String type;
	@Column(name = "AppType")
	private String appType;
	@Column(name = "RequestUrl")
	private String requestUrl;
	@Column(name = "UserAgent")
	private String userAgent;
	@Column(name = "Method")
	private String method;
	@Column(name = "Params")
	private String params;
	@Column(name = "Description")
	private String description;
	@Column(name = "RemoteAddr")
	private String remoteAddr;
	@Column(name = "OperId")
	private String operId;
	@Column(name = "OperDate")
	private Date operDate;
	@Column(name = "Clazz")
	private String clazz;
	@Column(name = "ResponseContent")
	private String responseContent;
	@Transient
	private String allParams;
	@Transient
	private String department2;
	@Transient
	private String projectMan;
	
	
	
	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap){
		if (paramMap == null){
			return;
		}
		StringBuilder params = new StringBuilder();
		StringBuilder allParamsSb = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 2000));
			allParamsSb.append(param.getKey() + "=" +paramValue);
		}
		this.params = params.toString();
		this.allParams = allParamsSb.toString();
	}
	
	public String getAllParams() {
		return allParams;
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
