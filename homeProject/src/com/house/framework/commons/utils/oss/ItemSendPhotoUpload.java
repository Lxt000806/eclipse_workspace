package com.house.framework.commons.utils.oss;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.FtpUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.driver.ItemAppSendService;

public class ItemSendPhotoUpload {
	
	protected static Logger logger = LoggerFactory.getLogger(ItemSendPhotoUpload.class);
	
	@SuppressWarnings("deprecation")
	public static void doUploadtoOss() throws Exception{
		ItemAppSendService itemAppSendService=(ItemAppSendService) SpringContextHolder.getBean("itemAppSendServiceImpl");	
		XtdmService xtdmService = (XtdmService) SpringContextHolder.getBean("xtdmServiceImpl");
		Xtcs xtcs = itemAppSendService.get(Xtcs.class, "syncPath");
		
		Map<String,Object> ftpMap = xtdmService.getFtpData();
		FtpUtils ftp = null;
		if (ftpMap!=null && ftpMap.size()>0 && xtcs != null && StringUtils.isNotBlank(xtcs.getQz()) && !"0".equals(xtcs.getQz())){
			String ftpaddr = String.valueOf(ftpMap.get("ftpaddr"));
			String ftpuser = String.valueOf(ftpMap.get("ftpuser"));
			String ftppwd = String.valueOf(ftpMap.get("ftppwd"));
			String ftpport = String.valueOf(ftpMap.get("ftpport"));
			ftp = new FtpUtils(ftpaddr, ftpport, ftpuser, ftppwd);
		}
		try {
			do{
				List<Map<String , Object>> list = itemAppSendService.findNoSendYunPic();
		
				for(int i = 0; list != null && i < list.size(); i++){
					Map<String, Object> map = list.get(i);
					if(map!=null && StringUtils.isNotBlank(map.get("PK").toString())){
						String url = getItemSendPhotoUploadPath();
						File file=new File(url+map.get("PhotoName").toString());
						if(file.exists()){
							if(xtcs != null && StringUtils.isNotBlank(xtcs.getQz()) && !"0".equals(xtcs.getQz())){
								String remotePath = file.getAbsolutePath().substring(13);
								remotePath = remotePath.substring(0, remotePath.lastIndexOf("\\"));
								logger.info("ftp remotePath:"+remotePath);
								if(ftp != null){
									boolean result = ftp.uploadFile(file, "", xtcs.getQz()+remotePath.replace("\\", "/"));

									if(result){
										ItemSendPhoto itemSendPhoto = itemAppSendService.get(ItemSendPhoto.class, Integer.parseInt(map.get("PK").toString()));
										if(itemSendPhoto != null){
											itemSendPhoto.setIsSendYun("1");
											itemSendPhoto.setSendDate(new Date());
											itemAppSendService.update(itemSendPhoto);
										}
									}
								}
							}
						}else{
							ItemSendPhoto itemSendPhoto = itemAppSendService.get(ItemSendPhoto.class, Integer.parseInt(map.get("PK").toString()));
							if(itemSendPhoto != null){
								itemSendPhoto.setIsSendYun("2");
								itemSendPhoto.setSendDate(new Date());
								itemAppSendService.update(itemSendPhoto);
							}
						}
					}
				}
				if(list == null || list.size() < 100){
					break;
				}
			}while(true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ftp != null){
				ftp.closeConnect();
			}
		}
		
	}

	protected static String getItemSendPhotoUploadPath(){
		return SystemConfig.getProperty("itemAppSend", "", "photo");
	}
	
}
