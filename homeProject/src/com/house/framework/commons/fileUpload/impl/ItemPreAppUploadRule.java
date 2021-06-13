package com.house.framework.commons.fileUpload.impl;

public class ItemPreAppUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "itemPreApp/";
	
	public ItemPreAppUploadRule(String originalName, String czybh) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH ;
		this.fileName = System.currentTimeMillis() + czybh + "." + this.getExtension();
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
}
