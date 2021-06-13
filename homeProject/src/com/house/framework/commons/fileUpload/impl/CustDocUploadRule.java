package com.house.framework.commons.fileUpload.impl;

public class CustDocUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "custDoc/";
	
	public CustDocUploadRule(String originalName, String custCode) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + custCode;
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
	}
	
}
