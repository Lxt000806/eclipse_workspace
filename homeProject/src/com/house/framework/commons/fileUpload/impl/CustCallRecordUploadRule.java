package com.house.framework.commons.fileUpload.impl;

public class CustCallRecordUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "custCallRecord/";
	
	public CustCallRecordUploadRule(String originalName, String czybh) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH;
		this.fileName = System.currentTimeMillis() + czybh + "." + this.getExtension();
	}
	
}
