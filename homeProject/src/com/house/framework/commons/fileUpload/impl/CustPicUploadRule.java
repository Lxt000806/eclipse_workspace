package com.house.framework.commons.fileUpload.impl;

public class CustPicUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "custPic/";
	
	public CustPicUploadRule(String originalName) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH ;
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
	}
}
