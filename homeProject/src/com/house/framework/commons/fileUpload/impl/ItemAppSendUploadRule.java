package com.house.framework.commons.fileUpload.impl;

public class ItemAppSendUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "itemAppSend/";
	
	public ItemAppSendUploadRule(String originalName, String czybh) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH;
		this.fileName = System.currentTimeMillis() + czybh + "." + this.getExtension();
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
}
