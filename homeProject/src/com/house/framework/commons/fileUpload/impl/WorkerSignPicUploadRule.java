package com.house.framework.commons.fileUpload.impl;

public class WorkerSignPicUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "workSignPic/";
	
	public WorkerSignPicUploadRule(String originalName, String custCode, String czybh) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + custCode +"/";
		this.fileName = System.currentTimeMillis() + czybh + "." + this.getExtension();
	}
}
