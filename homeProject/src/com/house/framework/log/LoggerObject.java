package com.house.framework.log;

import java.util.Date;

public class LoggerObject {
	
	public String modelCode;
	
	public String clazz;
	
	public String javaFun;
	
	public String ip;
	
	public String logcontent;
	
	public Date time;
	
	public String czybh;
	
	public long modelType = 0;
	
	public String loginMode;
	
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}


	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getJavaFun() {
		return javaFun;
	}

	public void setJavaFun(String javaFun) {
		this.javaFun = javaFun;
	}

	public long getModelType() {
		return modelType;
	}

	public void setModelType(long modelType) {
		this.modelType = modelType;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
}
