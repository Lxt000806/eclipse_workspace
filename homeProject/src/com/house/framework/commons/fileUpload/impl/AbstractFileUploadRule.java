package com.house.framework.commons.fileUpload.impl;

import org.apache.commons.lang3.StringUtils;

import com.house.framework.commons.fileUpload.FileUploadRule;

public abstract class AbstractFileUploadRule implements FileUploadRule {

	/**
	 * 原始文件名
	 */
	protected String originalName;
	
	/**
	 * 路径
	 */
	protected String filePath;
	
	/**
	 * 文件名（保存到文件服务器中的文件名，一般是System.currentTimeMillis()+扩展名）
	 */
	protected String fileName;
	
	@Override
	public String getOriginalName() {
		return this.originalName;
	}
	
	@Override
	public String getFullName() {
		if (!StringUtils.endsWith(this.filePath, "/")) {
			return this.filePath + "/" + this.fileName;
		}
		return this.filePath + this.fileName;
	}

	public String getExtension(){
		return StringUtils.substringAfterLast(this.originalName, ".");
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
