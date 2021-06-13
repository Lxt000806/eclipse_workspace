package com.house.framework.commons.fileUpload.impl;

public class WorkerPicUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "workerPic/";
	
	public WorkerPicUploadRule(String originalName, String infoNum) {
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + infoNum + "/";
		this.fileName = originalName;
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
	
}
