package com.house.framework.commons.fileUpload.impl;

public class CustContractTempUploadRule extends AbstractFileUploadRule {
	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "custContractTemp/";
	
	public CustContractTempUploadRule(String originalName) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH;
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
	}
}
