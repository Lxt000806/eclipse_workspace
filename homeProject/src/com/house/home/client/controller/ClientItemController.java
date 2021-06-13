package com.house.home.client.controller;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

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
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.fileUpload.impl.ItemPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.CustWorkInfoEvt;
import com.house.home.client.service.evt.ItemBatchDetailQueryEvt;
import com.house.home.client.service.evt.ItemBatchHeaderQueryEvt;
import com.house.home.client.service.evt.ItemDetailEvt;
import com.house.home.client.service.evt.ItemSaveEvt;
import com.house.home.client.service.evt.MaterialSearchEvt;
import com.house.home.client.service.evt.SaveCustSelectionEvt;
import com.house.home.client.service.evt.UpdateItemEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustWorkInfoResp;
import com.house.home.client.service.resp.ItemBatchDetailResp;
import com.house.home.client.service.resp.ItemBatchHeaderResp;
import com.house.home.client.service.resp.ItemDetailResp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemPic;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemBatchDetail;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.project.CustWorker;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.ItemBatchDetailService;

@RequestMapping("/client/item")
@Controller
public class ClientItemController extends ClientBaseController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemBatchDetailService itemBatchDetailService;
	@Autowired
	private CzybmService czybmService;
	/**
	 * 查询材料批次
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemBatchHeader")
	public  void getItemBatchHeader(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			itemService.findPageByCzy(page, evt.getId(),evt.getBatchType()==null?"1":evt.getBatchType(),evt.getItemBatchHeaderRemarks());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true
					: false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 查询材料批次明细
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemBatchDetail")
	public void getItemBatchDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchDetailQueryEvt evt=new ItemBatchDetailQueryEvt();
		BasePageQueryResp<ItemBatchDetailResp> respon=new BasePageQueryResp<ItemBatchDetailResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchDetailQueryEvt) JSONObject.toBean(json, ItemBatchDetailQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page=new Page();
			page.setPageNo(1);
			page.setPageSize(-1);
			itemBatchDetailService.findPageByIbdNo(page, evt.getId());
			List<ItemBatchDetailResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), ItemBatchDetailResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true
					: false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getItemDetail")
	public void getItemDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemDetailEvt evt=new ItemDetailEvt();
		ItemDetailResp respon=new ItemDetailResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemDetailEvt) JSONObject.toBean(json,ItemDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map=null;
			if(StringUtils.isNotBlank(evt.getCustCode())){
				Customer customer = itemService.get(Customer.class, evt.getCustCode());
				CustType custType = itemService.get(CustType.class, customer.getCustType());
				Boolean canScanItem = itemService.canScanItem(evt.getId(),custType.getCode(),custType.getCanUseComItem());
				if(!canScanItem){
					respon.setReturnCode("300102");
					respon.setReturnInfo("该材料不可选择");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
				map=itemService.findItemById(evt.getId(),customer.getCustType());
			}else{
				map=itemService.findItemById(evt.getId(),null);
			}
			if(map==null){
				respon.setReturnCode("300102");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	/**
	 * 保存材料项接口
	 */
	@RequestMapping("/saveItem")
	public void saveItem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemSaveEvt evt=new  ItemSaveEvt();
		BaseResponse respon=new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			//JSonArrayString Post
			json = this.getJson(request,msg,json,respon);
			evt = (ItemSaveEvt) JSONObject.toBean(json, ItemSaveEvt.class);
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
			itemService.saveItem(evt, itemService.getSeqNo("tItemBatchHeader"));
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 删除材料项
	 */
	@RequestMapping("/deleteItem")
	public  void deleteItem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=this.getJson(request, msg, json, respon);
			evt=(BaseGetEvt) JSONObject.toBean(json,BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			itemService.deleteItem(evt.getId());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 更新材料项
	 */
	@RequestMapping("/updateItem")
	public  void updateItem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UpdateItemEvt evt=new UpdateItemEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=this.getJson(request, msg, json, respon);
			evt=(UpdateItemEvt) JSONObject.toBean(json, UpdateItemEvt.class);
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
			itemService.updateItem(evt);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustCodeList")
	public  void getCustCodeList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			UserContext uc = new UserContext();
			Czybm czybm = czybmService.get(Czybm.class, evt.getCzybh());
			uc.setCzybh(evt.getCzybh());
			uc.setCustRight(czybm.getCustRight());
			uc.setEmnum(czybm.getEmnum());
			itemService.getCustCodeList(page, uc, evt.getSearchAddress());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setPageNo(page.getPageNo());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrePlanAreaList")
	public  void getPrePlanAreaList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(1000);
			itemService.getPrePlanAreaList(page, evt.getCustCode());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getFixAreaTypeItemType12AttrList")
	public  void getFixAreaTypeItemType12AttrList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(1000);
			itemService.getFixAreaTypeItemType12AttrList(page,evt.getFixAreaType(),evt.getItemType12());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			for(int i=0;i<listBean.size();i++){//由于前端页面ng-option 的key value 是一样的会导致ng-model绑定数据不能按预期显示  所以给value加空格 --cjm
				listBean.get(i).setAttrString(listBean.get(i).getAttrString()+" ");
			}
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMaterialOldItemList")
	public  void getMaterialOldItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			itemService.getMaterialOldItemList(page, evt);
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getSameItemNum")
	public void getSameItemNum(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt=new ItemBatchHeaderQueryEvt();
		ItemDetailResp respon=new ItemDetailResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map=itemService.getSameItemNum(evt.getCustCode(),evt.getId(), evt.getAlreadyReplaceStr());
			if(map==null){
				respon.setReturnCode("300102");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemType12List")
	public  void getItemType12List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(1000);
			if(StringUtils.isNotBlank(evt.getCustCode())){
				itemService.getItemType12List(page, evt.getCustCode(),evt.getItemType1());
			}else{
				itemService.getItemType12List(page, evt.getItemType1());
			}
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/saveCustSelection")
	public void saveCustSelection(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		SaveCustSelectionEvt evt=new  SaveCustSelectionEvt();
		BaseResponse respon=new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			//JSonArrayString Post
			json = this.getJson(request,msg,json,respon);
			evt = (SaveCustSelectionEvt) JSONObject.toBean(json, SaveCustSelectionEvt.class);
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
			ItemBatchHeader itemBatchHeader = new ItemBatchHeader();
			BeanUtilsEx.copyProperties(itemBatchHeader, evt);
			if(!"D".equals(itemBatchHeader.getM_umState())){
				Customer customer = itemService.get(Customer.class, evt.getCustCode());
				ItemType1 itemType1 = itemService.get(ItemType1.class, evt.getItemType1());
				itemBatchHeader.setRemarks(customer.getAddress()+"-"+itemType1.getDescr());
			}
			Result result = itemService.saveCustSelection(itemBatchHeader,evt.getXmlData());
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
	
	@RequestMapping("/getItemMainPic")
	public void getItemMainPic(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt=new ItemBatchHeaderQueryEvt();
		ItemDetailResp respon=new ItemDetailResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map=itemService.getItemMainPicPhotoUrl(evt.getItemCode());
			BeanConvertUtil.mapToBean(map, respon);
			String photoPath = "";
			if(respon.getPhotoUrl()!=null){
				String fileName = respon.getPhotoUrl().substring(respon.getPhotoUrl().lastIndexOf("\\")+1);;
				Item item=itemService.get(Item.class,evt.getItemCode());
//				photoPath= getItemPhotoDownloadPath(request, fileName,item.getItemType1(),item.getItemType2(),item.getItemType3());
//				photoPath=PathUtil.getWebRootAddress(request)+photoPath.substring(photoPath.indexOf("/")+1);
				ItemPicUploadRule rule = new ItemPicUploadRule(fileName,item.getItemType1(),item.getItemType2(),
						item.getItemType3(), evt.getItemCode(), 0);
				photoPath=rule.getFilePath();
			}
			if(photoPath.equals("")){
				respon.setPhotoUrl(null);
			}else{
				respon.setPhotoUrl(photoPath);
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemType2List")
	public  void getItemType2List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(1000);
			itemService.getItemType2List(page, evt.getItemType1());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMaterialNewItemList")
	public  void getMaterialNewItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			Customer customer = itemService.get(Customer.class, evt.getCustCode());
			CustType custType = itemService.get(CustType.class, customer.getCustType());
			itemService.getMaterialNewItemList(page, evt,custType.getCode(), custType.getCanUseComItem());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getSameItemList")
	public  void getSameItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemBatchHeaderQueryEvt evt = new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemBatchHeaderQueryEvt) JSONObject.toBean(json, ItemBatchHeaderQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(1000);
			itemService.getSameItemList(page, evt);
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustTypeItemList")
	public void getCustTypeItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MaterialSearchEvt evt=new MaterialSearchEvt();
		BasePageQueryResp<ItemDetailResp> respon=new BasePageQueryResp<ItemDetailResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(MaterialSearchEvt) JSONObject.toBean(json, MaterialSearchEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = itemService.getCustTypeItemList(evt.getId());
			List<ItemDetailResp> listBean = BeanConvertUtil.mapToBeanList(list, ItemDetailResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMaterialList")
	public  void getMaterialList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MaterialSearchEvt evt = new MaterialSearchEvt();
		BasePageQueryResp<ItemBatchHeaderResp> respon=new BasePageQueryResp<ItemBatchHeaderResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(MaterialSearchEvt) JSONObject.toBean(json, MaterialSearchEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			itemService.getMaterialList(page, evt.getSearchText(),evt.getItemType1());
			List<ItemBatchHeaderResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemBatchHeaderResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true
					: false);
			respon.setRecordSum(page.getTotalCount());
			respon.setPageNo(page.getPageNo());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
