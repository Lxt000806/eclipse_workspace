package com.house.home.quartz;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;


import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.WeiXinCryptUtils;
import com.house.home.client.service.bean.MtPerfResultBean;
import com.house.home.entity.basic.MtCustInfo;
import com.house.home.service.basic.MtCustInfoService;

public class MtPerfSend {

	public static String mtPerfUrl = "http://218.5.27.194:60012/FZYJ/AddCtmAchievement";
	
	protected static Logger logger = LoggerFactory.getLogger(MtPerfSend.class);
	
	@SuppressWarnings("deprecation")
	public static void doSendMt() throws Exception{
		MtCustInfoService mtCustInfoService=(MtCustInfoService) SpringContextHolder.getBean("mtCustInfoServiceImpl");
		
		try {
			do{
				Map<String, Object> map = mtCustInfoService.getMtPerfNoSend();
				if(map == null){
					break;
				}
				Integer pk = Integer.parseInt(map.get("PK").toString());
				JSONObject json = new JSONObject();
				json.put("custCodeMT", map.get("custCodeMT"));
				json.put("achTime", DateUtil.format((Date) map.get("achTime"), "yyyy-MM-dd hh:mm:ss"));
				json.put("achMoney", map.get("achMoney"));
				json.put("signTime", DateUtil.format((Date) map.get("signTime"), "yyyy-MM-dd hh:mm:ss"));
				
				String sendResult = sendInfo("PostData="+URLEncoder.encode(WeiXinCryptUtils.Encrypt(WeiXinCryptUtils.mtToken, WeiXinCryptUtils.mtEncodingAesKey, WeiXinCryptUtils.mtAppId, json.toString())));
				
				String result = WeiXinCryptUtils.Decrypt(WeiXinCryptUtils.mtToken, WeiXinCryptUtils.mtEncodingAesKey, WeiXinCryptUtils.mtAppId, sendResult);
				
				
				MtPerfResultBean resultBean = (MtPerfResultBean) JSONObject.toBean(JSONObject.fromObject(result), MtPerfResultBean.class);

				MtCustInfo mtCustInfo = mtCustInfoService.get(MtCustInfo.class, pk);
				if(mtCustInfo != null){
					if(resultBean.isSuccess() && "200".equals(resultBean.getCode())){
						mtCustInfo.setStatus("4");
						mtCustInfo.setSendDate(new Date());
					}else{
						mtCustInfo.setStatus("6");
						System.out.println(result);
						logger.info(result);
					}
					mtCustInfo.setLastUpdate(new Date());
					mtCustInfo.setActionLog("EDIT");
					mtCustInfoService.update(mtCustInfo);
				}

			}while(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String sendInfo(String send) throws IOException{
		HttpURLConnection httpConn;
	    URL url=new URL(mtPerfUrl);
	    httpConn = (HttpURLConnection)url.openConnection();
	    httpConn.setDoOutput(true);     //需要输出
        httpConn.setDoInput(true);      //需要输入
        httpConn.setUseCaches(false);   //不允许缓存
		httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Charset", "UTF-8");
		DataOutputStream os = new DataOutputStream(httpConn.getOutputStream() );
		if(StringUtils.isBlank(send)){
			send="";
		}
		os.write(send.getBytes("utf-8"));
		os.flush();
		os.close();
		httpConn.connect();  
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
		reader.close();
		httpConn.disconnect();
		return buffer.toString();
	}
	
}
