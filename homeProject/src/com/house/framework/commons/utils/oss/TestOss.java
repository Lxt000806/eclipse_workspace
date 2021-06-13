package com.house.framework.commons.utils.oss;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.utils.DesUtils;

public class TestOss {

	public static void main(String[] args) {
/*		upload();
		download();*/
		getFiles();
		
		
		/*System.out.println(DesUtils.decode("F5E8549EF476E5E6"));*/
	}

	/**
	 * @Des:测试上传本地图片
	 */
	private static void upload() {
		try {
			File file = new File("D:/1.jpg");
			String remotePath = "test/pic";
			String uploadFile = OssManager.uploadFile(file, remotePath, "my.jpg");
			System.err.println("uploadFile:" + uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Des:测试下载图片到本地
	 */
	private static void download() {
		try {
			String fileUrl = "http://lyh-zxc.oss-cn-shenzhen.aliyuncs.com/test/my.jpg";
			String fileName = "my.jpg";
			String downloadPath = "d:/download/";
			OssManager.downloadFile(fileUrl, fileName, downloadPath);
			System.err.println("downloadFile:" + fileUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void getFiles() {
		String marker = "";
		int count = 0;
		do{
			Map<String, Object> map = OssManager.getFilesByDir("sddwt/test2/", marker, 10);
			System.out.println(map);
			if(map != null){
				System.out.println("marker begin:"+marker);
				List list = (List) map.get("fileList");
				count += list.size();
				for(int i = 0;i < list.size();i++){
					System.out.println(list.get(i));
				}
				System.out.println("marker end:"+marker+"\n");
				if(Boolean.parseBoolean(map.get("hasNext").toString())){
					marker = map.get("marker").toString();
				}else{
					break;
				}
			}
		}while(true);
		System.out.println(count);
	}
}
