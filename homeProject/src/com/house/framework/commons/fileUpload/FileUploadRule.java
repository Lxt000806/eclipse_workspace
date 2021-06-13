package com.house.framework.commons.fileUpload;

/**
 * 文件上传规则，主要用于生成保存的路径
 * @author xzp
 */
public interface FileUploadRule {
	
	/**
	 * 获取原始文件名
	 * @return
	 */
	String getOriginalName();
	
	/**
	 * 获取文件全名（路径+文件名），示例：contractTemp/12345678.pdf
	 * @return
	 */
	String getFullName();
}
