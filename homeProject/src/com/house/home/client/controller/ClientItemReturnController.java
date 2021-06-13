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
import com.house.framework.bean.Result;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.BaseStatusEvt;
import com.house.home.client.service.evt.ItemReturnAddEvt;
import com.house.home.client.service.evt.ItemReturnDetailSelectQueryEvt;
import com.house.home.client.service.evt.ItemReturnQueryEvt;
import com.house.home.client.service.evt.ItemReturnUpdateEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ItemReturnDetailResp;
import com.house.home.client.service.resp.ItemReturnDetailSelectResp;
import com.house.home.client.service.resp.ItemReturnQueryResp;
import com.house.home.client.service.resp.ItemReturnResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.driver.ItemReturn;
import com.house.home.entity.insales.ItemApp;
import com.house.home.service.driver.ItemReturnDetailService;
import com.house.home.service.driver.ItemReturnService;
import com.house.home.service.insales.ItemAppService;

/**
 * 退货申请相关的接口
 * @author 
 *
 */
@RequestMapping("/client/itemReturn")
@Controller
public class ClientItemReturnController extends ClientBaseController{
	@Autowired
	private ItemReturnDetailService itemReturnDetailService;
	@Autowired
	private ItemReturnService itemReturnService;
	@Autowired
	private ItemAppService itemAppService;
	
	/**
	 * 查看退货申请列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemReturnList")
	public void getItemReturnList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReturnQueryEvt evt=new ItemReturnQueryEvt();
		BasePageQueryResp<ItemReturnQueryResp> respon=new BasePageQueryResp<ItemReturnQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemReturnQueryEvt)JSONObject.toBean(json,ItemReturnQueryEvt.class);
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
			page.setPageOrderBy("lastUpdate");
			page.setPageOrder("desc");
			ItemReturn itemReturn = new ItemReturn();
			itemReturn.setProjectMan(evt.getProjectMan());
			itemReturn.setAddress(evt.getAddress());
			itemReturn.setStatus(evt.getStatus());
			itemReturnService.findPageBySql_forClient(page, itemReturn);
			List<ItemReturnQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReturnQueryResp.class);
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
	 * 查看退货申请详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemReturn")
	public void getItemReturn(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemReturnResp respon=new ItemReturnResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			
			Map<String,Object> itemReturn = itemReturnService.getByNo(evt.getId());
			if (itemReturn==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(itemReturn, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看退货明细列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemReturnDetailList")
	public void getItemReturnDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<ItemReturnDetailSelectResp> respon=new BasePageQueryResp<ItemReturnDetailSelectResp>();
		
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
			itemReturnDetailService.findPageByNo(page, evt.getId());
			List<ItemReturnDetailSelectResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReturnDetailSelectResp.class);
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
	 * 查看退货申请明细接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemReturnDetail")
	public void getItemReturnDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemReturnDetailResp respon=new ItemReturnDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			
			Map<String,Object> itemReturnDetail = itemReturnDetailService.getByPk(Integer.parseInt(evt.getId()));
			if (itemReturnDetail==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(itemReturnDetail, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 修改退货申请状态接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateItemReturnStatus")
	public void updateItemReturnStatus(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseStatusEvt evt=new BaseStatusEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseStatusEvt)JSONObject.toBean(json,BaseStatusEvt.class);
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
			ItemReturn itemReturn = itemReturnService.get(ItemReturn.class, evt.getId());
			if (itemReturn==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if ("1".equals(evt.getStatus())){//收回状态修改为草稿
				if (itemReturn.getStatus().trim().equals("2")){
					itemReturn.setStatus(evt.getStatus());
					itemReturn.setLastUpdate(new Date());
					itemReturn.setLastUpdatedBy(getUserContext(request).getCzybh());
					itemReturnService.update(itemReturn);
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非提交状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}else{
				if (itemReturn.getStatus().trim().equals("1")){//申请状态的才能修改
					itemReturn.setStatus(evt.getStatus());
					itemReturn.setLastUpdate(new Date());
					itemReturn.setLastUpdatedBy(getUserContext(request).getCzybh());
					itemReturnService.update(itemReturn);
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非申请状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 新增预退货申请接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addItemReturn")
	public void addItemReturn(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReturnAddEvt evt=new ItemReturnAddEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (ItemReturnAddEvt)JSONObject.toBean(json,ItemReturnAddEvt.class);
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
			ItemReturn itemReturn = new ItemReturn();
			BeanUtilsEx.copyProperties(itemReturn, evt);
			itemReturn.setM_umState("A");
			if (StringUtils.isBlank(evt.getStatus())){
				itemReturn.setStatus("1");//1 草稿
			}else{
				itemReturn.setStatus(evt.getStatus());
			}
			itemReturn.setDate(new Date());
			String xmlData = evt.getXmlData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
			
			Result result = itemReturnService.saveForProc(itemReturn, xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 修改退货申请接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateItemReturn")
	public void updateItemReturn(HttpServletRequest request, HttpServletResponse response){
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReturnUpdateEvt evt=new ItemReturnUpdateEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (ItemReturnUpdateEvt)JSONObject.toBean(json,ItemReturnUpdateEvt.class);
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
			ItemReturn itemReturn = new ItemReturn();
			BeanUtilsEx.copyProperties(itemReturn, evt);
			itemReturn.setM_umState("M");
			itemReturn.setOldStatus("1");//申请状态
			itemReturn.setStatus(evt.getStatus());
			String xmlData = evt.getXmlData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
			
			Result result = itemReturnService.saveForProc(itemReturn, xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 可选取退货明细列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemReturnDetailSelectList")
	public void getItemReturnDetailSelectList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReturnDetailSelectQueryEvt evt=new ItemReturnDetailSelectQueryEvt();
		BasePageQueryResp<ItemReturnDetailSelectResp> respon=new BasePageQueryResp<ItemReturnDetailSelectResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemReturnDetailSelectQueryEvt)JSONObject.toBean(json,ItemReturnDetailSelectQueryEvt.class);
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
			ItemApp itemApp = new ItemApp();
			itemApp.setProjectMan(evt.getProjectMan());
			itemApp.setCustCode(evt.getCustCode());
			itemApp.setItemType1(evt.getItemType1());
			itemApp.setItemType2(evt.getItemType2());
			itemApp.setItemCodeDescr(evt.getItemCodeDescr());
			itemAppService.findPageBySql_itemReturn(page, itemApp);
			List<ItemReturnDetailSelectResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReturnDetailSelectResp.class);
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
