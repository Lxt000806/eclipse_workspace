package com.house.home.client.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.WarehouseSendEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.ItemSendBatchResp;
import com.house.home.client.service.resp.WareHouseResp;
import com.house.home.client.service.resp.WarehouseSendResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.ItemAppSendDetail;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.insales.ItemAppSendDetailService;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.ItemPreAppService;
import com.house.home.service.insales.ItemSendBatchService;
import com.house.home.service.insales.PreItemAppSendService;
import com.house.home.service.insales.WareHouseService;

/**
 * 工地巡检相关的接口
 * 
 * @author
 * 
 */
@RequestMapping("/client/warehouseSend")
@Controller
public class ClientWarehouseSendController extends ClientBaseController {
	@Autowired
	private PreItemAppSendService preItemAppSendService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ItemSendBatchService itemSendBatchService; 
	@Autowired
	private ItemPreAppService itemPreAppService;
	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private ItemAppSendDetailService itemAppSendDetailService;
	@Autowired
	private XtdmService xtdmService;
	
	/**
	 * 主页面显示
	 * @author	created by zb
	 * @date	2019-9-17--下午6:18:02
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWarehouseSendList")
	public void getWarehouseSendList(HttpServletRequest request,
			HttpServletResponse response) {
		WarehouseSendEvt evt = new WarehouseSendEvt();
		BasePageQueryResp<WarehouseSendResp> respon = new BasePageQueryResp<WarehouseSendResp>();

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);

		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt) JSONObject.toBean(json, WarehouseSendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			UserContext uc = getUserContext(request);
			preItemAppSendService.findWarehouseSendList(page, evt.getId(), evt.getAddress(), evt.getItemType1(), uc);
			List<WarehouseSendResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WarehouseSendResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	/**
	 * 领料发货明细
	 * @author	created by zb
	 * @date	2019-9-21--下午3:34:12
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getIASDetail")
	public void getIASDetail(HttpServletRequest request, HttpServletResponse response){
		WarehouseSendEvt evt=new WarehouseSendEvt();
		BasePageQueryResp<WarehouseSendResp> respon=new BasePageQueryResp<WarehouseSendResp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt)JSONObject.toBean(json,WarehouseSendEvt.class);
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
			ItemAppSendDetail itemAppSendDetail = new ItemAppSendDetail();
			itemAppSendDetail.setNo(evt.getNo());
			itemAppSendDetailService.findDetailBySqlNoAPP(page, itemAppSendDetail);
			List<WarehouseSendResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WarehouseSendResp.class);
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
	 * 领料单明细
	 * @author	created by zb
	 * @date	2019-9-17--下午6:18:52
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemAppDetail")
	public void getItemAppDetail(HttpServletRequest request,
			HttpServletResponse response) {
		WarehouseSendEvt evt = new WarehouseSendEvt();
		BasePageQueryResp<WarehouseSendResp> respon = new BasePageQueryResp<WarehouseSendResp>();

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);

		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt) JSONObject.toBean(json, WarehouseSendEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			ItemApp itemApp = this.itemAppDetailService.get(ItemApp.class, evt.getIaNo());
			if (null == itemApp) {
				respon.setReturnCode("400001");
				respon.setReturnInfo("领料单号错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			// 判断状态
			if (StringUtils.isBlank(itemApp.getStatus()) || !("CONFIRMED".equals(itemApp.getStatus().trim()))) {
				Xtdm xtdm = this.xtdmService.getByIdAndCbm("ITEMAPPSTATUS", itemApp.getStatus().trim());
				respon.setReturnCode("400002");
				respon.setReturnInfo(xtdm.getNote()+"状态，无法进行发货");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			//获取领料单材料数据
			Page page = new Page();
			page.setPageSize(evt.getPageSize());
			itemAppService.getPSendAppDtlJqGrid(page, evt.getIaNo(), itemApp.getCustCode());
			List<WarehouseSendResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WarehouseSendResp.class);
			respon.setDatas(listBean);
			//根据领料单获取仓库材料数据
			Page page2 = new Page();
			wareHouseService.getWHItemDetail(page2, evt.getIaNo());
			List<WarehouseSendResp> listBean2 = BeanConvertUtil.mapToBeanList(page2.getResult(), WarehouseSendResp.class);
			respon.setDatas2(listBean2);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	/**
	 * 获取仓库列表
	 * @author	created by zb
	 * @date	2019-9-19--上午11:39:23
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWHByConditions")
	public void getWHByConditions(HttpServletRequest request, HttpServletResponse response){
		WarehouseSendEvt evt=new WarehouseSendEvt();
		BasePageQueryResp<WareHouseResp> respon=new BasePageQueryResp<WareHouseResp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt)JSONObject.toBean(json,WarehouseSendEvt.class);
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
			WareHouse wareHouse = new WareHouse();
			wareHouse.setDesc1(evt.getDesc1());
			wareHouse.setCzybh(getUserContext(request).getCzybh().trim());
			wareHouseService.findPageBySqlCode(page, wareHouse);
			List<WareHouseResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WareHouseResp.class);
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
	 * 送货批次
	 * @author	created by zb
	 * @date	2019-9-19--下午5:00:00
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getISBByConditions")
	public void getISBByConditions(HttpServletRequest request, HttpServletResponse response){
		WarehouseSendEvt evt=new WarehouseSendEvt();
		BasePageQueryResp<ItemSendBatchResp> respon=new BasePageQueryResp<ItemSendBatchResp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt)JSONObject.toBean(json,WarehouseSendEvt.class);
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
			ItemSendBatch itemSendBatch = new ItemSendBatch();
			itemSendBatch.setM_umState("S");
			itemSendBatch.setNo(evt.getNo());
			itemSendBatch.setDriverCodeDescr(evt.getDriverCodeDescr());
			itemSendBatchService.getJqGrid(page,itemSendBatch);
			List<ItemSendBatchResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemSendBatchResp.class);
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
	 * 获取已发货次数
	 * @author	created by zb
	 * @date	2019-9-19--下午5:50:40
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getSendCount")
	public void getSendCount(HttpServletRequest request, HttpServletResponse response){
		WarehouseSendEvt evt=new WarehouseSendEvt();
		WarehouseSendResp respon=new WarehouseSendResp();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt)JSONObject.toBean(json,WarehouseSendEvt.class);
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
			ItemApp itemApp = this.itemPreAppService.get(ItemApp.class, evt.getIaNo());
			respon.setCustCode(itemApp.getCustCode());
			respon.setItemType1(itemApp.getItemType1());
			respon.setConfirmDate(itemApp.getConfirmDate());
			respon.setSendCount(this.itemPreAppService.getSendCountNum(evt.getIaNo()));
			if(StringUtils.isNotBlank(itemApp.getWhcode())) {
				respon.setWhCode(itemApp.getWhcode());
				respon.setWhDescr(itemAppService.get(WareHouse.class, itemApp.getWhcode()).getDesc1());
			} else { //选中拥有仓库权限的第一个
				Page page = new Page();				
				WareHouse wareHouse = new WareHouse();
				wareHouse.setCzybh(getUserContext(request).getCzybh().trim());
				wareHouseService.findPageBySqlCode(page, wareHouse);
				if (page.getResult().size()>0) {
					JSONArray arrayResult = JSONArray.fromObject(page.getResult());
					JSONObject jsonObject = arrayResult.getJSONObject(0);
					respon.setWhCode(jsonObject.getString("code"));
					respon.setWhDescr(jsonObject.getString("desc1"));
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 检查仓库权限
	 * @author	created by zb
	 * @date	2019-9-20--上午11:11:01
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getWHOperator")
	public void getWHOperator(HttpServletRequest request, HttpServletResponse response){
		WarehouseSendEvt evt=new WarehouseSendEvt();
		WarehouseSendResp respon=new WarehouseSendResp();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt)JSONObject.toBean(json,WarehouseSendEvt.class);
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
			if(itemAppService.getWHOperator(getUserContext(request).getCzybh().trim(), evt.getWhcode()) == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("您没有【" +itemAppService.get(WareHouse.class, evt.getWhcode()).getDesc1().trim()+ "】的操作权限");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 新增送货
	 * @author	created by zb
	 * @date	2019-9-23--下午2:19:42
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response) {
		WarehouseSendResp evt = new WarehouseSendResp();
		WarehouseSendResp respon=new WarehouseSendResp();

		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			Post用获取数据
			json = this.getJson(request,msg,json,respon);
			evt=(WarehouseSendResp) JSONObject.toBean(json, WarehouseSendResp.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ItemApp itemApp = this.itemAppService.get(ItemApp.class, evt.getIaNo());
			Result result = itemAppService.doSendBatchForXml(evt.getIaNo(),evt.getWhCode(),itemApp.getItemType1(),itemApp.getCustCode(),
					new Date(),itemApp.getRemarks(),evt.getItemSendBatchNo(),getUserContext(request).getCzybh().trim(),evt.getDetailJson(), 
					evt.getManySendRsn(),evt.getDelayReson(),evt.getDelayRemark(), "");
			if (!result.isSuccess()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 获取差额
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getBalance")
	public void getBalance(HttpServletRequest request, HttpServletResponse response){
		WarehouseSendEvt evt=new WarehouseSendEvt();
		WarehouseSendResp respon=new WarehouseSendResp();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WarehouseSendEvt)JSONObject.toBean(json,WarehouseSendEvt.class);
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
			ItemApp itemApp = itemAppService.get(ItemApp.class, evt.getNo());
			Map<String,Object> balanceMap = itemAppService.getBalance(evt.getNo(), itemApp.getCustCode());
			if (balanceMap != null) {
				respon.setReturnCode("400003");
				respon.setReturnInfo( "客户"+balanceMap.get("PayNum").toString()+"期款未交齐，差额"
						+balanceMap.get("ShouldBalance").toString()+"元，是否继续发货？");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
