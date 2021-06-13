package com.house.home.client.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.WeiXinCryptUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.service.evt.GetCustomerDetailInfoEvt;
import com.house.home.client.service.evt.GetCustomerInfoEvt;
import com.house.home.client.service.evt.GetShowBuildDetailEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.client.service.evt.MtAddCustInfoEvt;
import com.house.home.client.service.evt.MtEncryptTestEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GetCustomerDetailInfoResp;
import com.house.home.client.service.resp.GetCustomerInfoResp;
import com.house.home.client.service.resp.GetShowBuildDetailAPIResp;
import com.house.home.client.service.resp.GetShowBuildsResp;
import com.house.home.client.service.resp.MtEncryptTestResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.APIService;
import com.house.home.service.basic.MtCustInfoService;
import com.house.home.service.basic.ShowService;
import com.house.home.service.project.PrjProgService;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import java.util.List;
@RequestMapping("/client/api")
@Controller
public class ClientAPIController extends ClientBaseController {
	
	@Autowired
	private ShowService showService;
	@Autowired
	private APIService apiService;
	@Autowired
	private PrjProgService prjProgService;
	@Autowired
	private MtCustInfoService mtCustInfoService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getShowBuilds")
	public void getShowBuilds(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetShowBuildsEvt evt=new GetShowBuildsEvt();
		BasePageQueryResp<GetShowBuildsResp> respon=new BasePageQueryResp<GetShowBuildsResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			if(!checkAPIRequest(json, "zxjzyjzs")){
				respon.setReturnCode("400001");
				respon.setReturnInfo("验签失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			evt=(GetShowBuildsEvt) JSONObject.toBean(json, GetShowBuildsEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			respon.setReturnCode("400001");
			respon.setReturnInfo("此接口已不提供使用");
			returnJson(respon,response,msg,respon,request,interfaceLog);
			return;
/*			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize() <= 20 ? evt.getPageSize() : 20);
			showService.getShowBuilds(page, evt);
			List<GetShowBuildsResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetShowBuildsResp.class);
			if(listBean != null && listBean.size() > 0){
				for(int i = 0;i < listBean.size();i++){
					List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(listBean.get(i).getPrjProgConfirmNo(), 3);
					if(photoList == null){
						photoList = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < photoList.size();j++){
						if("1".equals(photoList.get(j).get("isSendYun").toString())){
							photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
						}else{
							String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
							photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
						}
						photoList.get(j).remove("isSendYun");
					}
					listBean.get(i).setPhotos(photoList);
					listBean.get(i).setPrjProgConfirmNo(null);
					listBean.get(i).setBeginDate(null);
				}
			}
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);*/
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getShowBuildDetail")
	public void getShowBuildDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetShowBuildDetailEvt evt=new GetShowBuildDetailEvt();
		GetShowBuildDetailAPIResp respon=new GetShowBuildDetailAPIResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			if(!checkAPIRequest(json, "zxjzyjzs")){
				respon.setReturnCode("400001");
				respon.setReturnInfo("验签失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			evt=(GetShowBuildDetailEvt) JSONObject.toBean(json, GetShowBuildDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			respon.setReturnCode("400001");
			respon.setReturnInfo("此接口已不提供使用");
			returnJson(respon,response,msg,respon,request,interfaceLog);
			return;
			/*
			Customer customer = this.showService.get(Customer.class, evt.getCustCode());
			respon.setCustCode(evt.getCustCode());
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = this.showService.get(Builder.class, customer.getBuilderCode());
				if(builder != null){
					respon.setAddress(builder.getDescr());
				}
			}
			CustType custType =showService.get(CustType.class, customer.getCustType());
			respon.setArea(customer.getArea());
			if(StringUtils.isNotBlank(evt.getGdbb())){
				respon.setAddress(customer.getAddress());
			}
			respon.setCustDescr(customer.getDescr().substring(0, 1));
			respon.setGender(customer.getGender());
			List<Map<String, Object>> list = this.showService.getPrjProgConfirm(evt.getCustCode());
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			for(int i = 0; i < list.size(); i++){
				List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(list.get(i).get("prjProgConfirmNo").toString(), null);
				if(photoList == null || (photoList != null && photoList.size() < 3)){
					photoList = new ArrayList<Map<String,Object>>();
				}
				for(int j = 0;j < photoList.size();j++){
					if("1".equals(photoList.get(j).get("isSendYun").toString())){
						photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
					}else{
						String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
						photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
					}
					photoList.get(j).remove("isSendYun");
				}
				list.get(i).put("photos", photoList);
				list.get(i).remove("prjProgConfirmNo");
				list.get(i).remove("prjItem");
			}
			respon.setDatas(list);
			returnJson(respon,response,msg,respon,request,interfaceLog);*/
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getCustomerInfo")
	public void getCustomerInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetCustomerInfoEvt evt=new GetCustomerInfoEvt();
		BasePageQueryResp<GetCustomerInfoResp> respon=new BasePageQueryResp<GetCustomerInfoResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			if(!checkAPIRequest(json, "zxjzyjzs")){
				respon.setReturnCode("400001");
				respon.setReturnInfo("验签失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			evt=(GetCustomerInfoEvt) JSONObject.toBean(json, GetCustomerInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize() <= 50 ? evt.getPageSize() : 50);
			
			this.apiService.getCustomerInfo(page, evt);

			List<GetCustomerInfoResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetCustomerInfoResp.class);
			
			for(int i = 0; i < listBean.size(); i++){
				List<Map<String, Object>> photos = this.apiService.getPrjProgPhoto(listBean.get(i).getCustCode());

				if(photos == null){
					photos = new ArrayList<Map<String,Object>>();
				}
				for(int j = 0;j < photos.size();j++){
					if("1".equals(photos.get(j).get("isSendYun").toString())){
						photos.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photos.get(j).get("src").toString().substring(0, 5)+"/"+photos.get(j).get("src").toString());
					}else{
						String src = getPrjProgPhotoDownloadPath(request, photos.get(j).get("src").toString());
						photos.get(j).put("src", src+photos.get(j).get("src").toString());
					}
					photos.get(j).remove("isSendYun");
				}
				listBean.get(i).setPhotos(photos);
				Map<String,Object> currentPrjItemMap = this.prjProgService.getPrjProgCurrentById(listBean.get(i).getCustCode());
				if(currentPrjItemMap != null && currentPrjItemMap.get("note") != null && judgePrjItem(currentPrjItemMap.get("PRJITEM").toString())){
					listBean.get(i).setCurrentPrjItem(currentPrjItemMap.get("note").toString());
				}
			}
			
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustomerDetailInfo")
	public void getCustomerDetailInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetCustomerDetailInfoEvt evt=new GetCustomerDetailInfoEvt();
		GetCustomerDetailInfoResp respon=new GetCustomerDetailInfoResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			if(!checkAPIRequest(json, "zxjzyjzs")){
				respon.setReturnCode("400001");
				respon.setReturnInfo("验签失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			evt=(GetCustomerDetailInfoEvt) JSONObject.toBean(json, GetCustomerDetailInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getCustCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("custCode参数不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String, Object>> list = this.apiService.getCustomerDetailInfo(evt.getCustCode(), evt.getDateFrom(), evt.getDateTo(), evt.getPrjItems());
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			respon.setCustCode(evt.getCustCode());
			for(int i = 0; i < list.size(); i++){
				List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(list.get(i).get("No").toString(), null);
				if(photoList == null || (photoList != null && photoList.size() < 3)){
					photoList = new ArrayList<Map<String,Object>>();
				}
				for(int j = 0;j < photoList.size();j++){
					if("1".equals(photoList.get(j).get("isSendYun").toString())){
						photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
					}else{
						String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
						photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
					}
					photoList.get(j).remove("isSendYun");
				}
				list.get(i).put("photos", photoList);
				list.get(i).remove("No");
			}
			respon.setDatas(list);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	public boolean judgePrjItem(String prjItem){
		boolean result = true;
		try{
			if(StringUtils.isBlank(prjItem)){
				return false;
			}
			Integer prjItemNumber = Integer.parseInt(prjItem);
			switch(prjItemNumber){
				case 3: result = true;break;
				case 5: result = true;break;
				case 8: result = true;break;
				case 9: result = true;break;
				case 10: result = true;break;
				case 16: result = true;break;
				default: result = false;break;
			}
		}catch (Exception e) {
			// TODO: handle exception
			result = false;
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	@RequestMapping("/mtEncryptTest")
	public void mtEncryptTest(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MtEncryptTestEvt evt=new MtEncryptTestEvt();
		MtEncryptTestResp respon=new MtEncryptTestResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=this.getJson(request,msg,json,respon);
			evt=(MtEncryptTestEvt) JSONObject.toBean(json, MtEncryptTestEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String postData = json.getString("PostData");
			if(StringUtils.isNotBlank(evt.getPostData())){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String timestamp = new Date().getTime()/1000+"";
			String nonce = Math.floor((Math.random()*1000000))+"";

			WXBizMsgCrypt pc = new WXBizMsgCrypt(WeiXinCryptUtils.mtToken, WeiXinCryptUtils.mtEncodingAesKey, WeiXinCryptUtils.mtAppId);
			String mingwen = pc.encryptMsg(postData, timestamp, nonce);
			
			respon.setPostData(mingwen);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/mtDecryptTest")
	public void mtDecryptTest(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MtEncryptTestEvt evt=new MtEncryptTestEvt();
		MtEncryptTestResp respon=new MtEncryptTestResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=this.getJson(request,msg,json,respon);
			evt=(MtEncryptTestEvt) JSONObject.toBean(json, MtEncryptTestEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String postData = json.getString("PostData");
			System.out.println(postData);
			if(StringUtils.isNotBlank(evt.getPostData())){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			

			WXBizMsgCrypt pc = new WXBizMsgCrypt(WeiXinCryptUtils.mtToken, WeiXinCryptUtils.mtEncodingAesKey, WeiXinCryptUtils.mtAppId);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			dbf.setXIncludeAware(false);
			dbf.setExpandEntityReferences(false);

			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(postData);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
			NodeList nodelist3 = root.getElementsByTagName("TimeStamp");
			NodeList nodelist4 = root.getElementsByTagName("Nonce");

			String encrypt = nodelist1.item(0).getTextContent();
			String msgSignature = nodelist2.item(0).getTextContent();
			String timestamp = nodelist3.item(0).getTextContent();
			String nonce = nodelist4.item(0).getTextContent();

			String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			String fromXML = String.format(format, encrypt);

			String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
			
			respon.setPostData(result2);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/mtAddCustInfo")
	public void mtAddCustInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MtAddCustInfoEvt evt=new MtAddCustInfoEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			JSONObject analyJson = this.getMtStream(request, json);//this.getPayload(request, json);
			if(analyJson == null || analyJson.get("PostData") == null || StringUtils.isBlank(analyJson.get("PostData").toString())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请求参数PostData不能为空");
				returnString(request, response, msg, respon, interfaceLog);
				return;
			}
			String result = WeiXinCryptUtils.Decrypt(WeiXinCryptUtils.mtToken, WeiXinCryptUtils.mtEncodingAesKey, WeiXinCryptUtils.mtAppId, analyJson.get("PostData").toString());
			json = JSONObject.fromObject(result);
			evt=(MtAddCustInfoEvt) JSONObject.toBean(json, MtAddCustInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnString(request, response, msg, respon, interfaceLog);
				return;
			}

			Iterator iterator = json.keys();
			int count = 0;
			while(iterator.hasNext()){
				count++;
				String key = iterator.next().toString();
				if(!"remark".equals(key) && (json.get(key) == null || json.getString(key).length() <= 0)){
					respon.setReturnCode("400001");
					respon.setReturnInfo("除remark字段外其他信息都不能为空,当前"+key+"字段为空");
					returnString(request, response, msg, respon, interfaceLog);
					return;
				}
			}
			if(count == 0){
				respon.setReturnCode("400001");
				respon.setReturnCode("除remark字段外其他信息都不能为空,当前信息全空");
				returnString(request, response, msg, respon, interfaceLog);
				return;
			}
			
			this.mtCustInfoService.addMtCustInfo(evt, respon);
			returnString(request, response, msg, respon, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandleString(respon,response,msg,request,interfaceLog);
		}
	}
}
