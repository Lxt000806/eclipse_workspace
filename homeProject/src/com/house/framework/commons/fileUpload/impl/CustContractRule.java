package com.house.framework.commons.fileUpload.impl;

public class CustContractRule extends AbstractFileUploadRule {

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "custContract/";

	public CustContractRule (String originalName){
		this.originalName = originalName;
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
		this.filePath = FIRST_LEVEL_PATH;
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
}
