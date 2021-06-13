package com.house.home.client.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.CustTagEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustTagResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.design.CustTagService;

@RequestMapping("/client/custTag")
@Controller
public class ClientCustTagController extends ClientBaseController {

	@Autowired
	CustTagService custTagService;
	
	@RequestMapping("/getCustTags")
	public void getShowBuilds(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		BasePageQueryResp<CustTagResp> respon=new BasePageQueryResp<CustTagResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List <Map<String, Object>> tagGroupList = custTagService.getCustTagGroup();
			List<CustTagResp> listBean=BeanConvertUtil.mapToBeanList(tagGroupList, CustTagResp.class);
			for (int i = 0; i < listBean.size(); i++) {
				List<Map<String, Object>> tagList = custTagService.getCustTagByGroupPk(listBean.get(i).getTagGroupPk());
				listBean.get(i).setCustTags(tagList);
			}
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveCustTag")
	public void doCustTeamChangeBusiness(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustTagEvt evt=new CustTagEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (CustTagEvt)JSONObject.toBean(json,CustTagEvt.class);
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
			
			Result result = custTagService.doSaveCustTagMapper(evt.getCode(), evt.getCzybh(), evt.getTagPks());
			if(!result.isSuccess()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result.getInfo());
			}else{
				respon.setReturnInfo(result.getInfo());
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
