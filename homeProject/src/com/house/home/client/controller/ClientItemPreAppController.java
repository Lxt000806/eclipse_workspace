package com.house.home.client.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.ItemPreAppUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.MathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.IsSetItemEvt;
import com.house.home.client.service.evt.ItemAppListQueryResp;
import com.house.home.client.service.evt.ItemAppTempAreaDetailQueryEvt;
import com.house.home.client.service.evt.ItemAppTempQueryEvt;
import com.house.home.client.service.evt.ItemBatchDetailQueryEvt;
import com.house.home.client.service.evt.ItemBatchHeaderQueryEvt;
import com.house.home.client.service.evt.ItemPreAppAddEvt;
import com.house.home.client.service.evt.ItemPreAppCheckEvt;
import com.house.home.client.service.evt.ItemPreAppQueryEvt;
import com.house.home.client.service.evt.ItemPreAppUpdateEvt;
import com.house.home.client.service.evt.ItemPreAppUpdateRemarksEvt;
import com.house.home.client.service.evt.ItemReqQueryEvt;
import com.house.home.client.service.evt.UpdateItemPreAppStatusEvt;
import com.house.home.client.service.evt.WaterItemAppEvt;
import com.house.home.client.service.evt.WorkerItemAppEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.IsSetItemNeedResp;
import com.house.home.client.service.resp.IsSetItemResp;
import com.house.home.client.service.resp.ItemAppTempAreaDetailQueryResp;
import com.house.home.client.service.resp.ItemAppTempQueryResp;
import com.house.home.client.service.resp.ItemBatchDetailQueryResp;
import com.house.home.client.service.resp.ItemBatchHeaderQueryResp;
import com.house.home.client.service.resp.ItemPreAppAddResp;
import com.house.home.client.service.resp.ItemPreAppDetailQueryResp;
import com.house.home.client.service.resp.ItemPreAppDetailResp;
import com.house.home.client.service.resp.ItemPreAppPhotoResp;
import com.house.home.client.service.resp.ItemPreAppQueryResp;
import com.house.home.client.service.resp.ItemPreAppResp;
import com.house.home.client.service.resp.ItemReqResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.client.service.resp.WaterItemAppResp;
import com.house.home.client.service.resp.WorkerItemAppResp;
import com.house.home.entity.basic.AppItemTypeBatch;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.ItemAppCtrl;
import com.house.home.entity.insales.ItemAppTemp;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.insales.ItemPreAppPhoto;
import com.house.home.entity.insales.ItemReq;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.ItemType1Service;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.ItemAppCtrlService;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.ItemAppTempAreaDetailService;
import com.house.home.service.insales.ItemAppTempService;
import com.house.home.service.insales.ItemBatchHeaderService;
import com.house.home.service.insales.ItemPreAppDetailService;
import com.house.home.service.insales.ItemPreAppPhotoService;
import com.house.home.service.insales.ItemPreAppService;
import com.house.home.service.insales.ItemReqService;

/**
 * 预领料相关的接口
 * @author 
 *
 */
@RequestMapping("/client/itemPreApp")
@Controller
public class ClientItemPreAppController extends ClientBaseController{
	@Autowired
	private ItemPreAppService itemPreAppService;
	@Autowired
	private ItemPreAppDetailService itemPreAppDetailService;
	@Autowired
	private ItemPreAppPhotoService itemPreAppPhotoService;
	@Autowired
	private ItemBatchHeaderService itemBatchHeaderService;
	@Autowired
	private ItemReqService itemReqService;
	@Autowired
	private CustomerService customerService;
	@Resource(name = "xtdmCacheManager")
	private ICacheManager xtdmCacheManager;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private ItemType1Service itemType1Service;
	@Autowired
	private ItemAppCtrlService itemAppCtrlService;
	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private ItemAppTempService itemAppTempService;
	@Autowired
	private ItemAppTempAreaDetailService itemAppTempAreaDetailService;
	/**
	 * 查看预领料列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemPreAppList")
	public void getItemPreAppList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemPreAppQueryEvt evt=new ItemPreAppQueryEvt();
		BasePageQueryResp<ItemPreAppQueryResp> respon=new BasePageQueryResp<ItemPreAppQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("address", request.getParameter("address"));
			evt = (ItemPreAppQueryEvt)JSONObject.toBean(json,ItemPreAppQueryEvt.class);
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
			page.setPageOrderBy("date");
			page.setPageOrder("desc");
			ItemPreApp itemPreApp = new ItemPreApp();
			itemPreApp.setAppCzy(evt.getAppCzy());
			itemPreApp.setAddress(evt.getAddress());
			itemPreApp.setItemType1(evt.getItemType1());
			itemPreApp.setSaleIndependence(evt.getSaleIndependence());
			itemPreAppService.findPageBySql(page, itemPreApp);
			List<ItemPreAppQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemPreAppQueryResp.class);
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
	 * 查看预领料详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemPreApp")
	public void getItemPreApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemPreAppResp respon=new ItemPreAppResp();
		
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
			ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, evt.getId());
			if (itemPreApp==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanUtilsEx.copyProperties(respon, itemPreApp);
			Xtdm xtdm = (Xtdm) xtdmCacheManager.get("PREAPPSTATUS_"+itemPreApp.getStatus().trim());
			if (xtdm!=null){
				respon.setStatusDescr(xtdm.getNote());
			}
			xtdm = (Xtdm) xtdmCacheManager.get("YESNO_"+respon.getIsSetItem());
			if (xtdm!=null){
				respon.setIsSetItemDesc(xtdm.getNote());
			}
			xtdm = (Xtdm) xtdmCacheManager.get("PREAPPENDCODE_"+itemPreApp.getEndCode());
			if (xtdm!=null){
				respon.setEndCodeDescr(xtdm.getNote());
			}
			if (StringUtils.isNotBlank(respon.getCustCode())){
				Customer customer = customerService.get(Customer.class,respon.getCustCode());
				if (customer!=null){
					respon.setAddress(customer.getAddress());
				}
			}
			if (StringUtils.isNotBlank(respon.getConfirmCzy())){
				Czybm czybm = czybmService.get(Czybm.class, respon.getConfirmCzy());
				if (czybm!=null){
					respon.setConfirmCzyDescr(czybm.getZwxm());
				}
			}
			if (StringUtils.isNotBlank(respon.getItemType1())){
				ItemType1 itemType1 = itemType1Service.get(ItemType1.class, respon.getItemType1());
				if (itemType1!=null){
					respon.setItemType1Desc(itemType1.getDescr());
				}
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看预领料图片接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemPreAppPhotoList")
	public void getItemPreAppPhotoList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemPreAppPhotoResp respon=new ItemPreAppPhotoResp();
		
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
			List<Map<String,Object>> list = itemPreAppPhotoService.getPhotoList(evt.getId());
			List<String> reList = new ArrayList<String>();
			List<String> reNameList = new ArrayList<String>();
			if (list!=null && list.size()>0){
				for (Map<String,Object> m : list){
					String str = String.valueOf(m.get("photoName"));
					if (StringUtils.isNotBlank(str)){
						ItemPreAppUploadRule rule = new ItemPreAppUploadRule(str, getUserContext(request).getCzybh());
						reList.add(FileUploadUtils.getFileUrl(rule.getOriginalPath()));
						reNameList.add(str);
					}
				}
			}
			respon.setPhotoList(reList);
			respon.setPhotoNameList(reNameList);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看预领料明细列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemPreAppDetailList")
	public void getItemPreAppDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<ItemPreAppDetailQueryResp> respon=new BasePageQueryResp<ItemPreAppDetailQueryResp>();
		
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
			itemPreAppDetailService.findPageByNo(page, evt.getId(), null);
			List<ItemPreAppDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemPreAppDetailQueryResp.class);
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
	 * 查看预领料明细接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemPreAppDetail")
	public void getItemPreAppDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemPreAppDetailResp respon=new ItemPreAppDetailResp();
		
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
			
			Map<String,Object> itemPreAppDetail = itemPreAppDetailService.getByPk(Integer.parseInt(evt.getId()));
			if (itemPreAppDetail==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(itemPreAppDetail, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 修改预领料状态接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateItemPreAppStatus")
	public void updateItemPreAppStatus(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UpdateItemPreAppStatusEvt evt=new UpdateItemPreAppStatusEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (UpdateItemPreAppStatusEvt)JSONObject.toBean(json,UpdateItemPreAppStatusEvt.class);
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
			ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, evt.getId());
			if (itemPreApp==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			itemPreApp.setConfirmRemark(evt.getConfirmRemark());
			if ("1".equals(evt.getStatus())){//收回状态修改为草稿
				if (itemPreApp.getStatus().trim().equals("2")){
					itemPreApp.setStatus(evt.getStatus());
					itemPreAppService.update(itemPreApp);
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非提交状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}else{
				if (itemPreApp.getStatus().trim().equals("1")){//申请状态的才能修改
					String str = itemPreAppDetailService.existsChange(evt.getId());
					if ("2".equals(evt.getStatus()) && StringUtils.isNotBlank(str)){
						//领料，有需求，添加材料时，同区域相同材料存在申请状态的材料增减项单，进行提示，不允许添加该材料
						respon.setReturnCode("100000");
						respon.setReturnInfo(str+",同区域相同材料存在申请状态的材料增减项单");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
					itemPreApp.setStatus(evt.getStatus());
					itemPreApp.setDate(new Date());
					itemPreAppService.update(itemPreApp);
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
	 * 修改预领料说明接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateItemPreAppRemarks")
	public void updateItemPreAppRemarks(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemPreAppUpdateRemarksEvt evt=new ItemPreAppUpdateRemarksEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemPreAppUpdateRemarksEvt)JSONObject.toBean(json,ItemPreAppUpdateRemarksEvt.class);
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
			ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, evt.getId());
			if (itemPreApp==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if (itemPreApp.getStatus().trim().equals("1")){//申请状态的才能修改
				itemPreApp.setRemarks(evt.getRemarks());
				itemPreAppService.update(itemPreApp);
			}else{
				respon.setReturnCode("100000");
				respon.setReturnInfo("非申请状态的不能修改");
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
	 * 新增预领料接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addItemPreApp")
	public void addItemPreApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemPreAppAddEvt evt=new ItemPreAppAddEvt();
		ItemPreAppAddResp respon=new ItemPreAppAddResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			json = StringUtil.queryStringToJSONObject(request);
			json = this.getJson(request,msg,json,respon);
			//json.put("xmlData", "'"+request.getParameter("xmlData")+"'");
			evt = (ItemPreAppAddEvt)JSONObject.toBean(json,ItemPreAppAddEvt.class);
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
			String conStatAndIsAddAll = customerService.getConStatAndIsAddAll(evt.getCustCode());
			if(conStatAndIsAddAll != null && !"1".equals(conStatAndIsAddAll)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("非正在施工工地不允许申请领料");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if("JZ".equals(evt.getItemType1())){
				Customer customer = customerService.get(Customer.class, evt.getCustCode());
				if(customer != null){
					CustType custType = customerService.get(CustType.class, customer.getCustType());
					if(custType!=null&&"0".equals(customer.getMainItemOk())&&"1".equals(custType.getCtrlMainItemOK())){
						respon.setReturnCode("400001");
						respon.setReturnInfo("主材未备齐不允许申请基础材料");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
				}
			}
			
			if("ZC".equals(evt.getItemType1()) || "JC".equals(evt.getItemType1()) || "RZ".equals(evt.getItemType1())){
				Customer customer = customerService.get(Customer.class, evt.getCustCode());
				if(customer != null ){
					if("1".equals(customer.getIsInitSign())){
						respon.setReturnCode("400001");
						respon.setReturnInfo("草签客户不允许新增主材、集成、软装领料单");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
				}
			}
			
			
			ItemPreApp itemPreApp = new ItemPreApp();
			BeanUtilsEx.copyProperties(itemPreApp, evt);
			
//			Customer customer = customerService.get(Customer.class, itemPreApp.getCustCode());
//			if(customer != null && !"1".equals(customer.getConstructStatus().trim())){
//				respon.setReturnCode("400001");
//				respon.setReturnInfo("非正在施工工地不允许领料");
//				returnJson(respon,response,msg,respon,request,interfaceLog);
//				return;
//			}
			
			itemPreApp.setM_umState("A");
			if (StringUtils.isBlank(evt.getStatus())){
				itemPreApp.setStatus("1");//1 草稿
			}else{
				itemPreApp.setStatus(evt.getStatus());
			}
			itemPreApp.setDate(new Date());
			String xmlData = evt.getXmlData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
//			String xmlData = XmlConverUtil.jsonToXmlNoHead(evt.getXmlData().substring(1, evt.getXmlData().length()-1));
			
//			String str = "[{'expired':'F','reqPk':'2400','actionLog':'ADD','lastUpdate':'','no':'IA00000006','lastUpdatedBy':'00163','remarks':'备注','qty':'1','itemCode':'015920'}]";
//			String xmlData = XmlConverUtil.jsonToXmlNoHead(str);
			
//			List<ItemPreAppDetail> list = itemPreAppDetailService.findByNo("IA00000005");
//			String jsonString = JSONArray.toJSONString(list);
//			String xmlData = XmlConverUtil.jsonToXmlNoHead(jsonString);
			
			itemPreApp.setFromInfoAdd(evt.getFromInfoAdd());
			itemPreApp.setInfoPk(evt.getInfoPk());
			itemPreApp.setItemConfCheck(evt.getItemConfCheck());
			itemPreApp.setConfirmRemark(evt.getConfirmRemark());
			if(StringUtils.isNotBlank(evt.getWorkerApp())){
				itemPreApp.setWorkerApp("1");
			}else{
				itemPreApp.setWorkerApp("0");
			}
			
			Result result = itemPreAppService.saveForProc(itemPreApp, xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
				respon.setItemConfCheck("0");
			}
			
			respon.setIsExistsSaleIndependence("0");
			
			if(itemPreAppService.isExistsSaleIndependence(evt.getCustCode())){
				respon.setIsExistsSaleIndependence("1");
				respon.setReturnInfo("存在独立销售主材，请及时下单");
			}			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 修改预领料接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateItemPreApp")
	public void updateItemPreApp(HttpServletRequest request, HttpServletResponse response){
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemPreAppUpdateEvt evt=new ItemPreAppUpdateEvt();
		ItemPreAppAddResp respon=new ItemPreAppAddResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			json = StringUtil.queryStringToJSONObject(request);
			json = this.getJson(request,msg,json,respon);
//			json.put("xmlData", "'"+request.getParameter("xmlData")+"'");
			evt = (ItemPreAppUpdateEvt)JSONObject.toBean(json,ItemPreAppUpdateEvt.class);
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
			ItemPreApp itemPreApp = new ItemPreApp();
			BeanUtilsEx.copyProperties(itemPreApp, evt);
			itemPreApp.setM_umState("M");
			itemPreApp.setOldStatus("1");//申请状态
			String xmlData = evt.getXmlData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
//			String xmlData = XmlConverUtil.jsonToXmlNoHead(evt.getXmlData().substring(1, evt.getXmlData().length()-1));
			
			Result result = itemPreAppService.saveForProc(itemPreApp, xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}

			ItemPreApp tItemPreApp = itemPreAppService.get(ItemPreApp.class, evt.getNo());
			
			respon.setIsExistsSaleIndependence("0");
			
			if(itemPreAppService.isExistsSaleIndependence(tItemPreApp.getCustCode())){
				respon.setIsExistsSaleIndependence("1");
				respon.setReturnInfo("存在独立销售主材，请及时下单");
			}		
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * check预领料接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkItemPreApp")
	public void checkItemPreApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemPreAppCheckEvt evt=new ItemPreAppCheckEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (ItemPreAppCheckEvt)JSONObject.toBean(json,ItemPreAppCheckEvt.class);
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
			ItemPreApp itemPreApp = new ItemPreApp();
			BeanUtilsEx.copyProperties(itemPreApp, evt);
			String xmlData = evt.getXmlData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
			
			Result result = itemPreAppService.checkForProc(itemPreApp, xmlData);
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
	 * 查看材料批次列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemBatchHeaderList")
	public void getItemBatchHeaderList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemBatchHeaderQueryEvt evt=new ItemBatchHeaderQueryEvt();
		BasePageQueryResp<ItemBatchHeaderQueryResp> respon=new BasePageQueryResp<ItemBatchHeaderQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemBatchHeaderQueryEvt)JSONObject.toBean(json,ItemBatchHeaderQueryEvt.class);
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
			page.setPageOrder("asc");
			page.setPageOrderBy("DispSeq");
			ItemBatchHeader itemBatchHeader = new ItemBatchHeader();
			itemBatchHeader.setItemType1(evt.getItemType1());
			itemBatchHeader.setBatchType("3");//3领料批次
			itemBatchHeader.setCustCode(evt.getCustCode());
			//新版本工人app直传worktype12限制材料，旧版本仍然匹配工人工种分类12
			if(StringUtils.isBlank(evt.getWorkType12())){
				if(StringUtils.isNotBlank(evt.getWorkerCode())){
					itemBatchHeader.setWorkerCode(evt.getWorkerCode());
				}
			}else{
				if("32".equals(evt.getWorkType12().trim())){
					evt.setWorkType12("02");
				}
				itemBatchHeader.setWorkType12(evt.getWorkType12());
			}

			if(StringUtils.isNotBlank(itemBatchHeader.getCustCode())){
				Customer customer = itemBatchHeaderService.get(Customer.class, itemBatchHeader.getCustCode());
				if(customer != null){
					itemBatchHeader.setCustType(customer.getCustType());
				}
			}
			itemBatchHeaderService.findPageBySql(page, itemBatchHeader);
			List<ItemBatchHeaderQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemBatchHeaderQueryResp.class);
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
	 * 查看材料批次明细列表接口(无需求)
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemBatchDetailList")
	public void getItemBatchDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemBatchDetailQueryEvt evt=new ItemBatchDetailQueryEvt();
		BasePageQueryResp<ItemBatchDetailQueryResp> respon=new BasePageQueryResp<ItemBatchDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemBatchDetailQueryEvt)JSONObject.toBean(json,ItemBatchDetailQueryEvt.class);
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
			Item item = new Item();
			item.setIbdno(evt.getIbdno());
			item.setItemType1(evt.getItemType1());
			item.setDescr(evt.getDescr());
			item.setIsSetItem(evt.getIsSetItem());
			item.setWorkType12(evt.getWorkType12());
			if("5".equals(evt.getAppType()) && StringUtils.isBlank(item.getIbdno())){
				
			} else {
				itemService.findPageBySqlForClient(page, item);
			}
			List<ItemBatchDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemBatchDetailQueryResp.class);
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
	 * 查看材料需求列表接口(有需求)
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemReqList")
	public void getItemReqList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReqQueryEvt evt=new ItemReqQueryEvt();
		BasePageQueryResp<ItemReqResp> respon=new BasePageQueryResp<ItemReqResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemReqQueryEvt)JSONObject.toBean(json,ItemReqQueryEvt.class);
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
			itemReq.setItemType1(evt.getItemType1());
			itemReq.setItemType2(evt.getItemType2());
			itemReq.setItemCodeDescr(evt.getItemCodeDescr());
			itemReq.setCustCode(evt.getCustCode());
			//itemReq.setIsSetItem(evt.getIsSetItem());
			itemReqService.findPageBySqlForClient(page, itemReq);
			List<ItemReqResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqResp.class);
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
	 * 是否套餐材料接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getIsSetItem")
	public void getIsSetItem(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IsSetItemEvt evt=new IsSetItemEvt();
		IsSetItemResp respon=new IsSetItemResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IsSetItemEvt)JSONObject.toBean(json,IsSetItemEvt.class);
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
			ItemAppCtrl itemAppCtrl = itemAppCtrlService.getByCustTypeAndItemType1(evt.getCustType(),evt.getItemType1());
			BeanUtilsEx.copyProperties(respon, itemAppCtrl);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 预领料图片上传
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadItemPreAppPhoto")
	public void uploadItemPreAppPhoto(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoResp respon = new UploadPhotoResp();
		
		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String no = multipartFormData.getParameter("no");
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> filePathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
                ItemPreAppUploadRule rule = new ItemPreAppUploadRule(attatchment.getName(), 
                        		getUserContext(request).getCzybh());
                
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                        		rule.getFileName(),rule.getFilePath());
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
                fileNameList.add(rule.getFileName());
                filePathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
            }
			
			if (StringUtils.isNotBlank(no)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						ItemPreAppPhoto photo = new ItemPreAppPhoto();
						photo.setNo(no);
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						photo.setActionLog("Add");
						photo.setExpired("F");
						photo.setIsSendYun("1");
						photo.setSendDate(new Date());
						itemPreAppPhotoService.save(photo);
					}
				}
			}
			respon.setPhotoPathList(filePathList);
			respon.setPhotoNameList(fileNameList);
			
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}
	/**
	 * 下单明细列表接口
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getItemAppList")
	public  void getItemAppList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<ItemAppListQueryResp> respon=new BasePageQueryResp<ItemAppListQueryResp>();
		
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
			itemAppService.findPageByPreAppNo(page, evt.getId());
			List<ItemAppListQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemAppListQueryResp.class);
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
	 * 删除预领料图片接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteItemPreAppPhoto")
	public void deleteItemPreAppPhoto(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseResponse respon=new BaseResponse();
		
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
			String[] arr = evt.getId().split(",");
			int i = 0;
			for (String str: arr){
				ItemPreAppPhoto itemPreAppPhoto = itemPreAppPhotoService.getByPhotoName(str);
				//删除服务器上的图片
//				String path = getItemPreAppPhotoDownloadPath(request);
				ItemPreAppUploadRule rule = new ItemPreAppUploadRule(str, "");
				
				if (itemPreAppPhoto!=null){
					itemPreAppPhotoService.delete(itemPreAppPhoto);
					OssManager.deleteFile(rule.getOriginalPath());
					i++;
				}
			}
			
			if (i==0){
				respon.setReturnCode("300102");
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
	 * 领料模板列表接口
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemAppTempList")
	public void getItemAppTempList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppTempQueryEvt evt=new ItemAppTempQueryEvt();
		BasePageQueryResp<ItemAppTempQueryResp> respon=new BasePageQueryResp<ItemAppTempQueryResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemAppTempQueryEvt)JSONObject.toBean(json,ItemAppTempQueryEvt.class);
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
			ItemAppTemp itemAppTemp=new ItemAppTemp();
			itemAppTemp.setItemType1(evt.getItemType1());
			// 原本限制为工人的工种类型12 改为按工人安排的工种类型12
			//新版本工人app直传worktype12限制材料，旧版本仍然匹配工人工种分类12
			if(StringUtils.isBlank(evt.getWorkType12())){
				if(StringUtils.isNotBlank(evt.getWorkerCode())){
					itemAppTemp.setWorkerCode(evt.getWorkerCode());
				}
			}else{
				if("32".equals(evt.getWorkType12().trim())){
					evt.setWorkType12("02");
				}
				itemAppTemp.setWorkType12(evt.getWorkType12());
			}
			itemAppTempService.findPageBySql(page,itemAppTemp);
			List<ItemAppTempQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppTempQueryResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAppItemTypeBatch")
	public void getAppItemTypeBatch(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppTempQueryEvt evt=new ItemAppTempQueryEvt();
		BasePageQueryResp<ItemAppTempQueryResp> respon=new BasePageQueryResp<ItemAppTempQueryResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemAppTempQueryEvt)JSONObject.toBean(json,ItemAppTempQueryEvt.class);
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
			ItemAppTemp itemAppTemp=new ItemAppTemp();
			itemAppTemp.setCustCode(evt.getCustCode());
			itemAppTemp.setIsItemSet(evt.getIsItemSet());
			itemAppTempService.getAppItemTypeBatch(page,itemAppTemp);
			List<ItemAppTempQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppTempQueryResp.class);
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
	
	@RequestMapping("/getItemAppTempAreaDetail")
	public  void getItemAppTempAreaDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppTempAreaDetailQueryEvt evt=new ItemAppTempAreaDetailQueryEvt();
		BasePageQueryResp<ItemAppTempAreaDetailQueryResp> respon=new BasePageQueryResp<ItemAppTempAreaDetailQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppTempAreaDetailQueryEvt) JSONObject.toBean(json, ItemAppTempAreaDetailQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
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
			itemAppTempAreaDetailService.findPageBySql(page, evt.getNo(), evt.getCustCode());
			List<ItemAppTempAreaDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppTempAreaDetailQueryResp.class);
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
	
	@RequestMapping("/getAppItemTypeBatchDetial")
	public  void getAppItemTypeBatchDetial(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppTempAreaDetailQueryEvt evt=new ItemAppTempAreaDetailQueryEvt();
		BasePageQueryResp<ItemAppTempAreaDetailQueryResp> respon=new BasePageQueryResp<ItemAppTempAreaDetailQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppTempAreaDetailQueryEvt) JSONObject.toBean(json, ItemAppTempAreaDetailQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
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
			itemAppTempAreaDetailService.findConfItemTypePageBySql(page, evt.getBatchCode(), evt.getCustCode());
			List<ItemAppTempAreaDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppTempAreaDetailQueryResp.class);
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
	
	@RequestMapping("/getConfItemCode")
	public  void getConfItemCode(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppTempAreaDetailQueryEvt evt=new ItemAppTempAreaDetailQueryEvt();
		BasePageQueryResp<ItemAppTempAreaDetailQueryResp> respon=new BasePageQueryResp<ItemAppTempAreaDetailQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppTempAreaDetailQueryEvt) JSONObject.toBean(json, ItemAppTempAreaDetailQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
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
			AppItemTypeBatch appItemTypeBatch = new AppItemTypeBatch();
			
			if(StringUtils.isNotBlank(evt.getBatchCode())){
				appItemTypeBatch = itemAppCtrlService.get(AppItemTypeBatch.class, evt.getBatchCode());
				if(appItemTypeBatch != null){
					evt.setFlag(appItemTypeBatch.getIsSetItem());
				}
			}
			
			itemAppTempAreaDetailService.findConfItemCodePageBySql(page, evt.getConfCode(), evt.getCustCode(),evt.getFlag(),evt.getM_umStatus());
			List<ItemAppTempAreaDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppTempAreaDetailQueryResp.class);
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
	
	@RequestMapping("/checkInfoAll")
	public  void checkInfoAll(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppTempAreaDetailQueryEvt evt=new ItemAppTempAreaDetailQueryEvt();
		BasePageQueryResp<ItemAppTempAreaDetailQueryResp> respon=new BasePageQueryResp<ItemAppTempAreaDetailQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppTempAreaDetailQueryEvt) JSONObject.toBean(json, ItemAppTempAreaDetailQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
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
			itemAppTempAreaDetailService.findCheckAppItemAllPageBySql(page, evt.getAppItemCodes(),evt.getItemCode() ,evt.getCustCode(),evt.getIsSetItem());
			List<ItemAppTempAreaDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppTempAreaDetailQueryResp.class);
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
	 * 是否套餐材料接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/isSetItemNeed")
	public void isSetItemNeed(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemPreAppCheckEvt evt=new ItemPreAppCheckEvt();
		IsSetItemNeedResp respon=new IsSetItemNeedResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemPreAppCheckEvt)JSONObject.toBean(json,ItemPreAppCheckEvt.class);
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
			List<ItemReq> list =itemReqService.getReqList(evt.getCustCode(),evt.getItemType1());
			if(list!=null&&list.size()>0){
				respon.setIsSetItemNeed("1");
			}else{
				respon.setIsSetItemNeed("0");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerAppItemList")
	public void getWorkerAppItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		BasePageQueryResp<WorkerItemAppResp> respon=new BasePageQueryResp<WorkerItemAppResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			if(evt.getPageSize()!=0){
				page.setPageSize(evt.getPageSize());
			}else{
				page.setPageSize(20);
			}
			itemPreAppService.getWorkerAppItemList(page, evt.getCustWkPk(),evt.getCustCode());
			List<WorkerItemAppResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerItemAppResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerItemApp")
	public void getWorkerItemApp(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		BasePageQueryResp<WorkerItemAppResp> respon=new BasePageQueryResp<WorkerItemAppResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(20);
			itemPreAppService.getWorkerItemApp(page, evt.getNo());
			List<WorkerItemAppResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerItemAppResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerItemPreAppDetail")
	public void getWorkerItemPreAppDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		BasePageQueryResp<WorkerItemAppResp> respon=new BasePageQueryResp<WorkerItemAppResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(10000);
			itemPreAppService.getItemAppDetail(page, evt.getNo());
			List<WorkerItemAppResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerItemAppResp.class);
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
	
	@RequestMapping("/getRatedMatrial")
	public void getRatedMatrial(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		WorkerItemAppResp respon=new WorkerItemAppResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}	
			Map<String,Object> ratedMatrial= itemPreAppService.getRatedMatrial(evt.getCustCode(),evt.getWorkType12());
			if (ratedMatrial==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(ratedMatrial, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getAppliedMoney")
	public void getAppliedMoney(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		WorkerItemAppResp respon=new WorkerItemAppResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}	
			Map<String,Object> appliedMoney= itemPreAppService.getAppliedMoney(evt.getCustCode(),evt.getWorkType12());
			if (appliedMoney==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(appliedMoney, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWaterItemList")
	public void getWaterItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WaterItemAppEvt evt=new WaterItemAppEvt();
		BasePageQueryResp<WaterItemAppResp> respon=new BasePageQueryResp<WaterItemAppResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WaterItemAppEvt) JSONObject.toBean(json, WaterItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			if(evt.getPageSize()!=0){
				page.setPageSize(evt.getPageSize());
			}else{
				page.setPageSize(20);
			}
			itemPreAppService.getWaterItemList(page,evt.getCustCode());
			List<WaterItemAppResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WaterItemAppResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWaterItemDetail")
	public void getWaterItemDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WaterItemAppEvt evt=new WaterItemAppEvt();
		BasePageQueryResp<WaterItemAppResp> respon=new BasePageQueryResp<WaterItemAppResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WaterItemAppEvt) JSONObject.toBean(json, WaterItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			if(evt.getPageSize()!=0){
				page.setPageSize(evt.getPageSize());
			}else{
				page.setPageSize(20);
			}
			itemPreAppService.getWaterItemDetail(page,evt.getNo());
			List<WaterItemAppResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WaterItemAppResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSubmitCheck")
	public void doSubmitCheck(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterItemAppEvt evt=new WaterItemAppEvt();
		ItemPreAppResp respon=new ItemPreAppResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		interfaceLog.setRequestContent(json.toString());
		json=StringUtil.queryStringToJSONObject(request);
		evt=(WaterItemAppEvt) JSONObject.toBean(json, WaterItemAppEvt.class);
		StringBuilder alarmStr = new StringBuilder();
		ItemApp itemApp=itemPreAppService.get(ItemApp.class, evt.getNo());
		
		Map<String,Object> custPayInfoMap = itemPreAppService.getCustPayInfo(itemApp.getCustCode());
		Double firstPay=Double.valueOf(custPayInfoMap.get("firstPay").toString());
		Double secondPay=Double.valueOf(custPayInfoMap.get("secondPay").toString());
		Double thirdPay=Double.valueOf(custPayInfoMap.get("thirdPay").toString());
		Double fourPay=Double.valueOf(custPayInfoMap.get("fourPay").toString());
		Double designFee=Double.valueOf(custPayInfoMap.get("designFee").toString());
		Double chgFee=Double.valueOf(custPayInfoMap.get("zjje").toString());
		Double custPay=Double.valueOf(custPayInfoMap.get("payFee").toString());
		String custType="";
		String payNumStr = "";
		String payNum = "";
		Double payPer = 0.0;
		Double diffAmount = 0.0;
		Double stdDesignFee = 0.0;
		Double resultDesignFee = 0.0;
		Double chgPer = 0.0;
		Double isRcvDesignFee = 0.0;
		
		Map<String,Object> map = itemPreAppService.checkCustPay(itemApp.getNo());
		if(map != null){
			custType=map.get("Custtype").toString();
			payNum = map.get("PayNum").toString();
			payPer = Double.valueOf(map.get("PayPer").toString());
			diffAmount = Double.valueOf(map.get("DiffAmount").toString());
			stdDesignFee = Double.valueOf(map.get("StdDesignFee").toString());
			chgPer = Double.valueOf(map.get("ChgPer").toString());
			
			if("1".equals(map.get("IsRcvDesignFee").toString())){
				isRcvDesignFee = 1.0;
			}
			
			if("2".equals(map.get("DesignFeeType").toString())){//"1".equals(map.get("Custtype"))  && "2".equals(payType) 根据规则配置
				resultDesignFee = stdDesignFee;
			}else{
				resultDesignFee = designFee;
			}

			if("1".equals(payNum)){
				if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay*payPer-custPay, 4) > diffAmount){
					alarmStr.append("一期:");
					if(resultDesignFee * isRcvDesignFee > 0){
						alarmStr.append(MathUtil.stripTrailingZeros(resultDesignFee));
					}
					
					if(chgFee * chgPer > 0){
						alarmStr.append("+"+MathUtil.stripTrailingZeros(chgFee*chgPer));
					}else if(chgFee * chgPer < 0){
						alarmStr.append(MathUtil.stripTrailingZeros(chgFee*chgPer));
					}
					
					if(resultDesignFee * isRcvDesignFee > 0 || chgFee * chgPer != 0){
						alarmStr.append("+");
					}
					alarmStr.append(MathUtil.stripTrailingZeros(firstPay));
					if(payPer == 1){
						alarmStr.append("-");
					}else{
						alarmStr.append("*"+MathUtil.stripTrailingZeros(payPer*100)+"%-");
					}
					alarmStr.append(MathUtil.stripTrailingZeros(custPay)+"="
									+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay*payPer-custPay, 4)));
				}
			}else if("2".equals(payNum)){
				if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay*payPer-custPay, 4) > diffAmount){
					alarmStr.append("二期:"+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay*payPer-custPay, 4)));
				}
			}else if("3".equals(payNum)){
				if(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay > diffAmount){
					if(fourPay == 0){//没有四期款，三期款即尾款
						payNumStr = "尾款:";
					}else{
						payNumStr = "三期:";
					}
		        	if(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay > thirdPay){
		    	            alarmStr.append(payNumStr+MathUtil.stripTrailingZeros(thirdPay)+ " 二期:"
		    	            			    +MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay-thirdPay, 4)));
		        	}else{
		        		alarmStr.append(payNumStr+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay, 4)));
		        	}
				}
			}else if("4".equals(payNum)){
		        if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay+fourPay*payPer-custPay,4) > diffAmount){
		        	alarmStr.append("尾款:"+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay+fourPay*payPer-custPay, 4)));
		        }
			}
			
		}
		try {
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Result result = this.itemAppService.doSubmitCheck(itemApp.getCustCode(), itemApp.getNo(), "TC",getUserContext(request).getCzybh(), alarmStr.toString(), itemApp.getIsCupboard(), itemApp.getItemType1(),itemApp.getItemType2(),custType,"");
			respon.setReturnCode(result.getCode());
			respon.setReturnInfo(result.getInfo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
}
