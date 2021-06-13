package com.house.framework.commons.fileUpload.impl;

import org.springframework.util.Assert;

public class PrjProgNewUploadRule extends AbstractFileUploadRule{

	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "prjProgNew/";
	
	public PrjProgNewUploadRule(String originalName, String infoNum) {
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH + infoNum + "/";
		this.fileName = originalName;
	}
	
	public String getOriginalPath(){
		return this.filePath + this.originalName;
	}
	
	/**
	 * 从已上传到服务器的文件名生成上传规则
	 * 
	 * @param filename 已上传到服务器的文件名
	 * @return
	 */
	public static PrjProgNewUploadRule fromUploadedFile(String filename) {
	    Assert.notNull(filename);
	    return new PrjProgNewUploadRule(filename, filename.substring(0, 5));
	}
	
}
