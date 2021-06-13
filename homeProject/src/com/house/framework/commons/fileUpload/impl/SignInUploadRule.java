package com.house.framework.commons.fileUpload.impl;

public class SignInUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "signIn/";
	
	public SignInUploadRule(String originalName) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH;
		this.fileName = originalName;
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
}
