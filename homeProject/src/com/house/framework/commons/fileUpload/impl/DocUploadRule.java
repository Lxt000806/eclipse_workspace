package com.house.framework.commons.fileUpload.impl;

public class DocUploadRule extends AbstractFileUploadRule {

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "doc/";

	public DocUploadRule (String folderCode, String originalName){
		this.originalName = originalName;
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
		this.filePath = FIRST_LEVEL_PATH + folderCode + "/";
	}
	
}
