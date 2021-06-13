package com.house.framework.commons.fileUpload.impl;

public class InfoAttachUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "infoAttach/";
	
	public InfoAttachUploadRule(String originalName, String infoNum) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + infoNum + "/";
		this.fileName = originalName;
	}
}
