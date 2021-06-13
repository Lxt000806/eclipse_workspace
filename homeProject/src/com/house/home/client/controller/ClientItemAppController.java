package com.house.home.client.controller;

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
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.ItemAppQueryEvt;
import com.house.home.client.service.evt.ItemReqEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.ItemAppDetailQueryResp;
import com.house.home.client.service.resp.ItemAppQueryResp;
import com.house.home.client.service.resp.ItemReqDetailResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.ItemReq;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.ItemReqService;

/**
 * 领料相关的接口
 * @author 
 *
 */
@RequestMapping("/client/itemApp")
@Controller
public class ClientItemAppController extends ClientBaseController{
	@Autowired
	private ItemReqService itemReqService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;
	@Autowired
	private ItemAppService itemAppService;
	/**
	 * 查看领料列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemAppList")
	public void getItemAppList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemAppQueryEvt evt=new ItemAppQueryEvt();
		BasePageQueryResp<ItemAppQueryResp> respon=new BasePageQueryResp<ItemAppQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemAppQueryEvt)JSONObject.toBean(json,ItemAppQueryEvt.class);
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
			page.setPageOrder("c.address");
			ItemApp itemApp = new ItemApp();
			itemApp.setCustCode(evt.getCustCode());
			itemAppService.findPageBySql(page, itemApp);
			List<ItemAppQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemAppQueryResp.class);
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
	/**
	 * 查看领料详情列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemAppDetailList")
	public void getItemAppDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<ItemAppDetailQueryResp> respon=new BasePageQueryResp<ItemAppDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			itemAppDetailService.findPageByNo(page, evt.getId());
			List<ItemAppDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemAppDetailQueryResp.class);
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
	/**
	 * 查看领料需求列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getItemReqList")
	public void getItemReqList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReqEvt evt=new ItemReqEvt();
		BasePageQueryResp<ItemReqDetailResp> respon=new BasePageQueryResp<ItemReqDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemReqEvt)JSONObject.toBean(json,ItemReqEvt.class);
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
			ItemReq itemReq = new ItemReq();
			itemReq.setCustCode(evt.getCustCode());
			itemReq.setItemType1(evt.getItemType1());
			itemReq.setIsService(evt.getIsService());
			itemReqService.findPageBySql(page, itemReq);
			List<ItemReqDetailResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqDetailResp.class);
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
