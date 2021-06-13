package com.house.framework.commons.utils.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FtpUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.query.SignInPic;
import com.house.home.service.basic.SignInService;
import com.house.home.service.basic.XtdmService;

public class SignInPhotoUpload {

	protected static Logger logger = LoggerFactory.getLogger(SignInPhotoUpload.class);
	
	@SuppressWarnings("deprecation")
	public static void doUploadtoOss() throws Exception{
		SignInService signInService=(SignInService) SpringContextHolder.getBean("signInServiceImpl");
		XtdmService xtdmService = (XtdmService) SpringContextHolder.getBean("xtdmServiceImpl");
		Xtcs xtcs = signInService.get(Xtcs.class, "syncPath");
		
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
				List<Map<String , Object>> list = signInService.findNoSendYunPic();
				
				for(int i = 0; list != null && i < list.size(); i++){
					Map<String, Object> map = list.get(i);
					if(map!=null && StringUtils.isNotBlank(map.get("PK").toString())){
						String url = getSignInPhotoUploadPath();
						File file=new File(url+map.get("PhotoName").toString());
						if(file.exists()){
							if(file.length()/1024.0 > 300.0){
								InputStream inputStream = new FileInputStream(file);
								ServletUtils.compressImage(inputStream, url+"/"+map.get("PhotoName").toString(), 300, 1.0f);
							}
							String returnUrl = OssManager.uploadFile(file, "signIn", "");
							if(StringUtils.isNotBlank(returnUrl)){
								SignInPic signInPic = signInService.get(SignInPic.class, Integer.parseInt(map.get("PK").toString()));
								if(signInPic != null){
									signInPic.setIsSendYun("1");
									signInPic.setSendDate(new Date());
									signInService.update(signInPic);
								}
							}
							if(xtcs != null && StringUtils.isNotBlank(xtcs.getQz()) && !"0".equals(xtcs.getQz())){
								String remotePath = file.getAbsolutePath().substring(13);
								remotePath = remotePath.substring(0, remotePath.lastIndexOf("\\"));
								logger.info("ftp remotePath:"+remotePath);
								if(ftp != null){
									ftp.uploadFile(file, "", xtcs.getQz()+remotePath.replace("\\", "/"));
								}
							}
						}else{
							SignInPic signInPic = signInService.get(SignInPic.class, Integer.parseInt(map.get("PK").toString()));
							if(signInPic != null){
								signInPic.setIsSendYun("2");
								signInPic.setSendDate(new Date());
								signInService.update(signInPic);
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

	protected static String getSignInPhotoUploadPath(){
		return SystemConfig.getProperty("signIn", "", "photo");
	}
	
}
