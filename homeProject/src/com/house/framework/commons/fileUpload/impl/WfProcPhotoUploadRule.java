package com.house.framework.commons.fileUpload.impl;

public class WfProcPhotoUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "wfProcPic/";
	
	public WfProcPhotoUploadRule(String originalName) {
		
		this.originalName = originalName;
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
		this.filePath = FIRST_LEVEL_PATH + this.fileName.substring(0, 5) + "/";
	}
}
