package com.house.framework.commons.fileUpload.impl;

public class PrjProblemUploadRule extends AbstractFileUploadRule {
	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "prjProblem/";
	
	public PrjProblemUploadRule(String originalName, String czybh) {
		
		this.originalName = originalName;
		this.filePath = FIRST_LEVEL_PATH;
		this.fileName = System.currentTimeMillis() + czybh + "." + this.getExtension();
	}
	
	public PrjProblemUploadRule() {
		this.filePath = FIRST_LEVEL_PATH;
	}
	
}
