package com.house.home.client.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.WxAppletUtils;
import com.house.home.client.service.evt.CustRecommendEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.CustRecommendResp;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustRecommend;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.CustAccountService;
import com.house.home.service.basic.CustRecommendService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.ResrCustService;

@RequestMapping({"/client/custRecommend","/client/custByWorker"})
@Controller
public class ClientCustRecommendController extends ClientBaseController{
	
	@Autowired
	private CustRecommendService custRecommendService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private CustAccountService custAccountService;
	@Autowired
	private ResrCustService resrCustService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping({"/getCustRecommendList","/getCustByWorkerList"})
	public void getCustRecommendList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustRecommendEvt evt=new CustRecommendEvt();
		BasePageQueryResp<CustRecommendResp> respon=new BasePageQueryResp<CustRecommendResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustRecommendEvt)JSONObject.toBean(json,CustRecommendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			page.setPageOrderBy("LastUpdate");
			page.setPageOrder("Desc");
			CustRecommend custRecommend = new CustRecommend();
			custRecommend.setAddress(evt.getAddress());
			custRecommend.setStatus(evt.getStatus());
			if(StringUtils.isNotEmpty(evt.getWorkerCode())){
				custRecommend.setRecommender(evt.getWorkerCode());
			}else{
				custRecommend.setRecommender(evt.getRecommender());
			}

			custRecommend.setPortalAccount(evt.getPortalAccount());
			custRecommend.setIsSelf(evt.getIsSelf());	
			custRecommend.setSearchInfo(evt.getSearchInfo());
			custRecommendService.getCustRecommendList(page,custRecommend);
			List<CustRecommendResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustRecommendResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping({"/getCustRecommendDetail","/getCustByWorkerDetail"})
	public void getCustRecommendDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustRecommendEvt evt=new CustRecommendEvt();
		CustRecommendResp respon=new CustRecommendResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustRecommendEvt)JSONObject.toBean(json,CustRecommendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map = custRecommendService.getCustRecommendDetail(evt.getPk());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response){
		//这个接口目前只有工人App会调用 add by cjm 2020-01-08
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustRecommendEvt evt=new CustRecommendEvt();
		BasePageQueryResp<CustRecommendResp> respon=new BasePageQueryResp<CustRecommendResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json = StringUtil.queryStringToJSONObject(request);
			}
			evt = (CustRecommendEvt)JSONObject.toBean(json,CustRecommendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String resrCustCode = resrCustService.getSeqNo("tResrCust");
			CustRecommend custRecommend = new CustRecommend();
			CustRecommend oldCustRecommend = new CustRecommend();
			CustAccount ca = new CustAccount();
			if("6".equals(evt.getRecommendSource())){
				ca = custAccountService.getCustAccountByPhone(evt.getRecommender(),"");
				oldCustRecommend = custRecommendService.getCustRecommendByCustPhone(evt.getRecommender());
			}
			CustRecommend cr = custRecommendService.getCustRecommendByCustPhone(evt.getCustPhone());
			if(null!=cr&&DateUtil.dateDiff(cr.getRecommendDate(),new Date())<90){
				respon.setReturnCode("400001");
				respon.setReturnInfo("该客户已有推荐人");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			custRecommend.setResrCustCode(resrCustCode);
			custRecommend.setAddress(evt.getAddress());
			custRecommend.setCustName(evt.getCustName());
			custRecommend.setCustPhone(evt.getCustPhone());
			
			// 兼容旧版本工人APP
			if(StringUtils.isNotEmpty(evt.getWorkerCode())){
				custRecommend.setRecommender(evt.getWorkerCode());
				custRecommend.setRecommendSource("1");
				custRecommend.setRecommenderType("1");
			}else{
				if("6".equals(evt.getRecommendSource())){
					custRecommend.setRecommender(ca.getPk()+"");
				}else{
					custRecommend.setRecommender(evt.getRecommender());
				}
				custRecommend.setRecommendSource(evt.getRecommendSource());
				custRecommend.setRecommenderType(evt.getRecommenderType());
			}
			
			if("6".equals(evt.getRecommendSource())&&null!=oldCustRecommend){
				custRecommend.setManager(oldCustRecommend.getManager());
			}
			
			custRecommend.setStatus("0");
			custRecommend.setRemarks(evt.getRemarks());
			custRecommend.setRecommendDate(new Date());
			custRecommend.setLastUpdate(new Date());
			custRecommend.setLastUpdatedBy("1");
			custRecommend.setExpired("F");
			custRecommend.setActionLog("ADD");
			custRecommendService.save(custRecommend);
			
			ResrCust resrCust = new ResrCust();
			resrCust.setCode(resrCustCode);
			resrCust.setBuilderCode("");
			resrCust.setStatus("1"); 
			resrCust.setCustResStat("0");
			resrCust.setCustKind("0");
			resrCust.setCrtDate(new Date());
			resrCust.setAddress(custRecommend.getAddress());
			resrCust.setDescr(custRecommend.getCustName());
			resrCust.setMobile1(custRecommend.getCustPhone());
			resrCust.setRemark(custRecommend.getRemarks());
			resrCust.setResrCustPoolNo("RCP0000001"); // 推荐客户默认归为默认线索池
			resrCust.setValidDispatchCount(0);
			resrCust.setNoValidCount(0);
			if("3".equals(evt.getRecommenderType())&&null!=oldCustRecommend){
				resrCust.setBusinessMan(oldCustRecommend.getManager());
				resrCust.setDispatchDate(new Date());
				resrCust.setLastUpdatedBy(oldCustRecommend.getManager());
			}else{
				resrCust.setBusinessMan("");
				resrCust.setLastUpdatedBy("1");
			}
			resrCust.setCrtCzy("1");
			resrCust.setSource("10");
			resrCust.setLastUpdate(new Date());
			resrCust.setExpired("F");
			resrCust.setActionLog("ADD");
			this.resrCustService.save(resrCust);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustRecommendEvt evt=new CustRecommendEvt();
		BasePageQueryResp<CustRecommendResp> respon=new BasePageQueryResp<CustRecommendResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json = StringUtil.queryStringToJSONObject(request);
			}
			evt = (CustRecommendEvt)JSONObject.toBean(json,CustRecommendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			CustRecommend custRecommend = custRecommendService.get(CustRecommend.class, evt.getPk());
			custRecommend.setAddress(evt.getAddress());
			custRecommend.setCustName(evt.getCustName());
			if(StringUtils.isNotEmpty(evt.getCustPhone())){
				custRecommend.setCustPhone(evt.getCustPhone());
			}
			custRecommend.setRemarks(evt.getRemarks());
			custRecommend.setLastUpdate(new Date());
			custRecommend.setLastUpdatedBy(evt.getCzybh());
			custRecommend.setExpired("F");
			custRecommend.setActionLog("EDIT");
			this.custRecommendService.update(custRecommend);
			ResrCust resrCust = resrCustService.get(ResrCust.class, custRecommend.getResrCustCode());
			resrCust.setAddress(evt.getAddress());
			resrCust.setDescr(evt.getCustName());
			if(StringUtils.isNotEmpty(evt.getCustPhone())){
				resrCust.setMobile1(evt.getCustPhone());
			}
			resrCust.setRemark(evt.getRemarks());
			if("3".equals(evt.getAppType())){
				resrCust.setLastUpdatedBy(evt.getCzybh());
			}
			resrCust.setLastUpdate(new Date());
			resrCust.setExpired("F");
			resrCust.setActionLog("EDIT");
			this.resrCustService.update(resrCust);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getQrCode")
	public void getQrCode(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustRecommendEvt evt=new CustRecommendEvt();
		CustRecommendResp respon=new CustRecommendResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustRecommendEvt) JSONObject.toBean(json, CustRecommendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
//			WxAppletUtils.getAccessToken();
			String appCityValue = xtcsService.getQzById("AppCityValue");
			String sence = "";
			if("2".equals(evt.getRecommendSource())||"4".equals(evt.getRecommendSource())){
				sence="r="+evt.getCzybh().trim()+"&s="+evt.getRecommendSource()+"&ct="+appCityValue;
			}else{
				//客户邀请码参数设定  账号表pk+推荐来源+城市地址
				CustAccount custAccount = custAccountService.getCustAccountByPhone(evt.getCustPhone(),null);
				sence="r="+custAccount.getPk()+"&s="+evt.getRecommendSource()+"&ct="+appCityValue;
			}
			String accessTokenUrl = xtcsService.getQzById("AcceTokenUrl");
			respon.setBase64String(WxAppletUtils.getWXQrcode(evt.getPage(),sence,evt.getWidth(),accessTokenUrl));
			respon.setSence(sence);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
