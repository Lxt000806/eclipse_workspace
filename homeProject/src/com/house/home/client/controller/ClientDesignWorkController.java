package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.DesignWorkEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.DesignWorkResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DesignPlanResultAnalyService;

@RequestMapping("/client/designWork")
@Controller
public class ClientDesignWorkController extends ClientBaseController{
	@Autowired
	private DesignPlanResultAnalyService designPlanResultAnalyService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDesignWorkList")
	public void getFixDutyList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DesignWorkEvt evt=new DesignWorkEvt();
		BasePageQueryResp<DesignWorkResp> respon=new BasePageQueryResp<DesignWorkResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("beginDate")&&!"null".equals(json.get("beginDate").toString())) 
				json.put("beginDate", sdf.parse(json.get("beginDate").toString()));
			if(json.containsKey("endDate")&&!"null".equals(json.get("endDate").toString()))
				json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt = (DesignWorkEvt)JSONObject.toBean(json,DesignWorkEvt.class);
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
			page.setPageSize(10);
			Customer customer = new Customer();
			customer.setDateFrom(evt.getBeginDate());
			customer.setDateTo(evt.getEndDate());
			customer.setEmpCode(evt.getCzybh());
			customer.setPeriod("2");
			customer.setStatistcsMethod("1");
			designPlanResultAnalyService.findPageBySql(page, customer);
			List<DesignWorkResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DesignWorkResp.class);
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
	
}
