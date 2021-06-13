package com.house.framework.commons.utils.oss;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.DesignDemoPic;
import com.house.home.service.basic.WorkerMessageService;
import com.house.home.service.design.DesignDemoPicService;

public class DesignDemoUpload {

	public static void doUploadtoOss() throws Exception{
		DesignDemoPicService designDemoPicService=(DesignDemoPicService) SpringContextHolder
				.getBean("designDemoPicServiceImpl");
		List<Map<String , Object>> list=designDemoPicService.findNoPushYunPics();
		try {
			if(list!=null){
				for(int i = 0;i < list.size();i++){
					String url= getCustDocPath(list.get(i).get("PhotoName").toString(),list.get(i).get("CustCode").toString());
					File file=new File(url+list.get(i).get("no").toString().trim()+"/"+list.get(i).get("PhotoName").toString());
					if(StringUtils.isNotBlank(list.get(i).get("CustCode").toString())){
						OssManager.uploadFile(file, "designDemoPic/"+list.get(i).get("CustCode").toString()+"/"+list.get(i).get("no").toString().trim(), "");
					}else {
						OssManager.uploadFile(file, "designDemoPic/"+list.get(i).get("no").toString().trim(), "");
					}
					DesignDemoPic designDemoPic = designDemoPicService.get(DesignDemoPic.class, Integer.parseInt(list.get(i).get("PK").toString()));
					if(designDemoPic != null){
						designDemoPic.setSendDate(new Date());
						designDemoPic.setIsSendYun("1");
						designDemoPicService.update(designDemoPic);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getCustDocPath(String fileName,String fileCustCode){
		String custDocNameNew = SystemConfig.getProperty("designDemoPic", "", "photo");
		if (StringUtils.isBlank(custDocNameNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return custDocNameNew + fileCustCode + "/";
		}else{
			return SystemConfig.getProperty("designDemoPic", "", "photo");
		}
	}
}
