package com.house.framework.commons.fileUpload.impl;

public class IntReplenishDocUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "intReplenish/";
	
	public IntReplenishDocUploadRule(String originalName, String custCode) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + custCode + "/";
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
	}
}
