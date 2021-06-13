package com.house.framework.commons.utils.oss;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.home.entity.project.PrjProgPhoto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.FtpUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.project.PrjJobPhoto;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.project.PrjJobService;

public class PrjJobPhotoUpload {
	
	protected static Logger logger = LoggerFactory.getLogger(PrjJobPhotoUpload.class);
	
	@SuppressWarnings("deprecation")
	public static void doUploadtoOss() throws Exception{
		PrjJobService prjJobService=(PrjJobService) SpringContextHolder.getBean("prjJobServiceImpl");	
		XtdmService xtdmService = (XtdmService) SpringContextHolder.getBean("xtdmServiceImpl");
		Xtcs xtcs = prjJobService.get(Xtcs.class, "syncPath");
		
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
				List<Map<String , Object>> list = prjJobService.findNoSendYunPic();
		
				for(int i = 0; list != null && i < list.size(); i++){
					Map<String, Object> map = list.get(i);
					if(map!=null && StringUtils.isNotBlank(map.get("PK").toString())){
						String url = getPrjProgPhotoUploadPath(map.get("PhotoName").toString());
						File file=new File(url+map.get("PhotoName").toString());
						if(file.exists()){
							String remotePath = file.getAbsolutePath().substring(13);
							remotePath = remotePath.substring(0, remotePath.lastIndexOf("\\"));
							String returnUrl = OssManager.uploadFile(file, remotePath.replace("\\", "/"), "");
							if(StringUtils.isNotBlank(returnUrl)){
								PrjJobPhoto prjJobPhoto = prjJobService.get(PrjJobPhoto.class, Integer.parseInt(map.get("PK").toString()));
								if(prjJobPhoto != null){
									prjJobPhoto.setIsSendYun("1");
									prjJobPhoto.setSendDate(new Date());
									prjJobService.update(prjJobPhoto);
								}
							}
						}else{
							PrjJobPhoto prjJobPhoto = prjJobService.get(PrjJobPhoto.class, Integer.parseInt(map.get("PK").toString()));
							if(prjJobPhoto != null){
								prjJobPhoto.setIsSendYun("2");
								prjJobPhoto.setSendDate(new Date());
								prjJobService.update(prjJobPhoto);
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

	protected static String getPrjProgPhotoUploadPath(String fileName){
		String prjProgNew = SystemConfig.getProperty("prjProgNew", "", "photo");
		if (StringUtils.isBlank(prjProgNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return prjProgNew + fileName.substring(0,5) + "/";
		}else{
			return SystemConfig.getProperty("prjProg", "", "photo");
		}
	}
	
}
