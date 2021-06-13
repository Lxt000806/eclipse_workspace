package com.house.framework.commons.fileUpload.impl;

public class WorkerAvatarPicUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "workerAvatarPic/";
	
	public WorkerAvatarPicUploadRule(String originalName, String workerCode) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + workerCode + "/";
		this.fileName = System.currentTimeMillis() + "." + this.getExtension();
	}
	
	public String getFileName(){
		return this.fileName;
	}
}
