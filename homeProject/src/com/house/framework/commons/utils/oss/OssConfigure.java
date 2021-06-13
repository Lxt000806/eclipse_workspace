package com.house.framework.commons.utils.oss;

import com.house.framework.commons.conf.SystemConfig;

public class OssConfigure {
	public static String endpoint = SystemConfig.getProperty("endpoint", "", "ossConfigure");
	// accessKey
	public static String accessKeyId = SystemConfig.getProperty("accessKeyId", "", "ossConfigure");
	
	public static String accessKeySecret = SystemConfig.getProperty("accessKeySecret", "", "ossConfigure");
	// 空间
	public static String bucketName = SystemConfig.getProperty("bucketName", "", "ossConfigure");
	
	public static String accessUrl = SystemConfig.getProperty("accessUrl", "", "ossConfigure");

	public static String cdnAccessUrl = SystemConfig.getProperty("cdnAccessUrl", "", "ossConfigure");
}
