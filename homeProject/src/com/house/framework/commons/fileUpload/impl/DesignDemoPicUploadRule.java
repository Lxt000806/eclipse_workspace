package com.house.framework.commons.fileUpload.impl;

import org.apache.commons.lang3.StringUtils;

public class DesignDemoPicUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "designDemoPic/";
	
	public DesignDemoPicUploadRule(String originalName, String custCode ,String no) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH;
		if (StringUtils.isNotBlank(custCode)) {
			this.filePath += custCode + "/";
		}
		this.filePath += no + "/";
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
}
