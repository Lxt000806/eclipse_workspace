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

import com.house.framework.bean.Result;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseItemChgEvt;
import com.house.home.client.service.evt.BaseItemTypeEvt;
import com.house.home.client.service.evt.GetCustomerEvt;
import com.house.home.client.service.resp.BaseItemChgDetailResp;
import com.house.home.client.service.resp.BaseItemChgResp;
import com.house.home.client.service.resp.BaseItemResp;
import com.house.home.client.service.resp.BaseItemTypeResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.GetCustomerResp;
import com.house.home.dao.design.ItemPlanDao;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.insales.BaseItemReq;
import com.house.home.entity.project.BaseItemChg;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.BaseItemReqService;
import com.house.home.service.insales.BaseItemService;
import com.house.home.service.insales.BaseItemType1Service;
import com.house.home.service.insales.BaseItemType2Service;
import com.house.home.service.project.BaseItemChgDetailService;
import com.house.home.service.project.BaseItemChgService;

/**
 * 项目经理APP_基装增减
 * @author created by zb
 * @date   2019-2-22--下午4:29:36
 */
@RequestMapping("/client/baseItemChg")
@Controller
public class ClientPrjBaseItemChgController extends ClientBaseController {

	@Autowired
	private BaseItemChgService baseItemChgService;
	@Autowired
	private BaseItemType1Service baseItemType1Service;
	@Autowired
	private BaseItemType2Service baseItemType2Service;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private BaseItemService baseItemService;
	@Autowired
	private BaseItemReqService baseItemReqService;
	@Autowired
	private BaseItemChgDetailService baseItemChgDetailService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private ItemPlanDao itemPlanDao;
	
	/**
	 * APP基装增减首页
	 * @author	created by zb
	 * @date	2019-2-28--下午5:45:02
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemChgList")
	public void getBaseItemChgList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		BaseItemChgEvt evt=new BaseItemChgEvt();
		BasePageQueryResp<BaseItemChgResp> respon=new BasePageQueryResp<BaseItemChgResp>();

		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			防止中文乱码
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseItemChgEvt) JSONObject.toBean(json, BaseItemChgEvt.class);
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
			page.setPageSize(evt.getPageSize());
			baseItemChgService.getBaseItemChgList(page, evt);
			List<BaseItemChgResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemChgResp.class);
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
	 * 基装类型
	 * @author	created by zb
	 * @date	2019-3-4--下午4:10:29
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemType")
	public void getBaseItemType(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseItemTypeEvt evt = new BaseItemTypeEvt();
		BasePageQueryResp<BaseItemTypeResp> respon = new BasePageQueryResp<BaseItemTypeResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseItemTypeEvt) JSONObject.toBean(json, BaseItemTypeEvt.class);
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
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(-1);
			//如果有类型1的编号，就去搜索类型2
			if (StringUtils.isNotBlank(evt.getCode())) {
				baseItemType2Service.getBaseItemType2(page,evt.getCode());
			} else {
				baseItemType1Service.getBaseItemType1(page);
			}
			List<BaseItemTypeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemTypeResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 获取用户信息
	 * @author	created by zb
	 * @date	2019-3-1--下午2:54:43
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerByConditions")
	public void getCustomerByConditions(HttpServletRequest request, HttpServletResponse response){
		GetCustomerEvt evt=new GetCustomerEvt();
		BasePageQueryResp<GetCustomerResp> respon=new BasePageQueryResp<GetCustomerResp>();
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustomerEvt)JSONObject.toBean(json,GetCustomerEvt.class);
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
			Customer customer = new Customer();
			customer.setStatus(evt.getStatus());
			customer.setAddress(evt.getAddress());
			UserContext uc = getUserContext(request);
			//项目经理相关楼盘就可显示 modify by zzr 2019/12/20
			customer.setIgnoreCustRight("1");
			customer.setProjectMan(uc.getCzybh());
			customerService.findPageBySql(page, customer, uc);
			List<GetCustomerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), GetCustomerResp.class);
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
	 * 获取更多用户信息
	 * @author	created by zb
	 * @date	2019-3-7--下午2:16:34
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getBICCustMoreDetail")
	public void getBICCustMoreDetail(HttpServletRequest request, HttpServletResponse response){
		GetCustomerResp evt=new GetCustomerResp();
		BaseItemChgResp respon=new BaseItemChgResp();
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustomerResp)JSONObject.toBean(json,GetCustomerResp.class);
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
			UserContext uc = getUserContext(request);
			String longFeeCode = xtcsService.getQzById("LONGFEECODE");
			boolean haveBaseChgRight = true;//false;//app对其权限不管控
			/*CzyGnqx czyGnqx = czyGnqxService.getCzyGnqx("0302", "基装增减", uc.getCzybh());
			if (czyGnqx!=null){
				haveBaseChgRight = true;
			}*/
			Customer customer = null;
			CustType custType = null;
			if (StringUtils.isNotBlank(evt.getCode())) {
				customer=customerService.get(Customer.class, evt.getCode());
				custType= custTypeService.get(CustType.class, evt.getCustType());
				if (null == custType) {
					respon.setReturnCode("400001");
					respon.setReturnInfo("客户类型表无对应客户编号");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				respon.setCustCode(evt.getCode());
				respon.setAddress(customer.getAddress());
				respon.setCustType(evt.getCustType());
				respon.setCustTypeType(evt.getCustTypeType());
				respon.setCanUseComBaseItem(evt.getCanUseComBaseItem());
				respon.setDocumentNo(customer.getDocumentNo());
			} else {
				respon.setReturnCode("400001");
				respon.setReturnInfo("无客户编号");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if("1".equals(custType.getType().trim())&&"0".equals(custType.getIsPartDecorate())){
				respon.setBaseDiscPer(baseItemChgService.getLastBaseDiscPer(evt.getCode()));
				respon.setBaseFeeDirct(baseItemChgService.getBaseFeeDirct(evt.getCode()));
			}
			respon.setLastUpdatedBy(uc.getCzybh());
			respon.setDate(new Date());
			respon.setBaseFeeDirct(customer.getBaseFeeDirct());
			respon.setCustType(evt.getCustType().trim());
			respon.setManageFeeBasePer(custType.getManageFeeBasePer());
			respon.setChgManageFeePer(custType.getChgManageFeePer());
			respon.setLongFeePer(custType.getLongFeePer());
			respon.setLongFeeCode(longFeeCode);
			respon.setHaveBaseChgRight(haveBaseChgRight);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取基装项目
	 * @author	created by zb
	 * @date	2019-3-1--下午3:36:39
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItem")
	public void getBaseItem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseItem evt = new BaseItem();
		BasePageQueryResp<BaseItemResp> respon=new BasePageQueryResp<BaseItemResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			防止中文乱码
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseItem) JSONObject.toBean(json, BaseItem.class);
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
			page.setPageSize(evt.getPageSize());
			baseItemService.getItemBaseType(page, evt);
			List<BaseItemResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemResp.class);
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
	 * 已有项List
	 * @author	created by zb
	 * @date	2019-3-8--下午2:20:31
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemHadList")
	public void getBaseItemHadList(HttpServletRequest request,HttpServletResponse response){
		BaseItemChgEvt evt=new BaseItemChgEvt();
		BasePageQueryResp<BaseItemResp> respon=new BasePageQueryResp<BaseItemResp>();

		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			防止中文乱码
			json = this.getJson(request,msg,json,respon);
			evt=(BaseItemChgEvt) JSONObject.toBean(json, BaseItemChgEvt.class);
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
			page.setPageOrderBy("fixareadescr2");
			page.setPageOrder("desc");
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			BaseItemReq baseItemReq = new BaseItemReq();
			baseItemReq.setCustCode(evt.getCustCode());
			baseItemReq.setUnSelected(evt.getUnSelected());
			baseItemReq.setShowOutSet(evt.getShowOutSet());
			baseItemReq.setFixAreaPk(evt.getFixAreaPk());
			baseItemReq.setBaseItemDescr(evt.getBaseItemDescr());
			baseItemReqService.findBaseItemReqList(page, baseItemReq);
			List<BaseItemResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemResp.class);
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
	 * 根据增减no获取详细信息
	 * @author	created by zb
	 * @date	2019-2-26--下午2:26:23
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getBaseItemChgDetail")
	public void getBaseItemChgDetail(HttpServletRequest request,HttpServletResponse response){
		BaseItemChgEvt evt = new BaseItemChgEvt();
		BaseItemChgResp respon = new BaseItemChgResp();
		
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseItemChgEvt) JSONObject.toBean(json, BaseItemChgEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			BaseItemChg baseItemChg = null;
			if (StringUtils.isNotBlank(evt.getNo())) {
				baseItemChg = baseItemChgService.get(BaseItemChg.class, evt.getNo());
//				将一个类放到另一个类里面
				BeanUtilsEx.copyProperties(respon, baseItemChg);
			} else {
				respon.setReturnCode("400001");
				respon.setReturnInfo("无基装增减编号");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			UserContext uc = getUserContext(request);
			CustType custType = null;
			String longFeeCode = xtcsService.getQzById("LONGFEECODE");
			boolean haveBaseChgRight = true;//false;//app不管权限
			/*CzyGnqx czyGnqx = czyGnqxService.getCzyGnqx("0302", "基装增减", uc.getCzybh());
			if (czyGnqx!=null){
				haveBaseChgRight = true;
			}*/
			if(baseItemChg!=null){
				Customer customer = customerService.get(Customer.class,respon.getCustCode());
				custType = custTypeService.get(CustType.class, customer.getCustType());
				if (null == custType) {
					respon.setReturnCode("400001");
					respon.setReturnInfo("客户类型表无对应客户编号");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				respon.setAddress(customer.getAddress());
				respon.setCustTypeType(custType.getType());
				respon.setCustType(customer.getCustType());
				respon.setDocumentNo(customer.getDocumentNo());
				respon.setLastUpdatedBy(uc.getCzybh());
				respon.setCanUseComBaseItem(custType.getCanUseComBaseItem());
				respon.setBaseFeeDirct(customer.getBaseFeeDirct());
				Xtdm xtdm = xtdmService.getByIdAndCbm("ITEMCHGSTATUS", baseItemChg.getStatus());
				if (null != xtdm) {
					respon.setStatusDescr(xtdm.getNote());
				}
			}
			if("1".equals(respon.getCustType().trim())){
				respon.setBaseFeeDirct(baseItemChgService.getBaseFeeDirct(respon.getCustCode()));
			}
			respon.setCustType(respon.getCustType().trim());
			respon.setManageFeeBasePer(custType.getManageFeeBasePer());
			respon.setChgManageFeePer(custType.getChgManageFeePer());
			respon.setLongFeePer(custType.getLongFeePer());
			respon.setLongFeeCode(longFeeCode);
			respon.setHaveBaseChgRight(haveBaseChgRight);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 编辑、查看获取基装项目明细
	 * @author	created by zb
	 * @date	2019-3-12--下午6:24:31
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemList")
	public void getBaseItemList(HttpServletRequest request,HttpServletResponse response){
		BaseItemChgEvt evt=new BaseItemChgEvt();
		BasePageQueryResp<BaseItemChgDetailResp> respon=new BasePageQueryResp<BaseItemChgDetailResp>();
		
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
//			防止中文乱码
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseItemChgEvt) JSONObject.toBean(json, BaseItemChgEvt.class);
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
			page.setPageSize(-1);
			baseItemChgDetailService.findPageByNo(page, evt.getNo());
			List<BaseItemChgDetailResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemChgDetailResp.class);
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
	 * 保存过程
	 * @author	created by zb
	 * @date	2019-3-7--下午4:59:32
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response) {
		BaseItemChgResp evt = new BaseItemChgResp();
		BaseItemChgResp respon=new BaseItemChgResp();

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
			evt=(BaseItemChgResp) JSONObject.toBean(json, BaseItemChgResp.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BaseItemChg baseItemChg = new BaseItemChg();
			BeanUtilsEx.copyProperties(baseItemChg, evt);
			if (baseItemChg.getTax() == null) {
				baseItemChg.setTax(0d);
			}
			Customer customer = new Customer();
			customer.setCode(evt.getCustCode());
			if("2".equals(evt.getCustTypeType())){
				customer.setContractFee(evt.getAmount()+evt.getManageFee());
			}else{
				customer.setContractFee(evt.getAmount());
			}
			customer.setBaseFeeComp(evt.getBaseCompAmount()== null?0:evt.getBaseCompAmount());
			customer.setBaseFeeDirct(evt.getAmount() - customer.getBaseFeeComp());
			
			customer.setDesignFee(0.0);
			customer.setTaxCallType("2");
			customer.setDiscAmount(baseItemChg.getBefAmount() < 0.0 ? -baseItemChg.getDiscAmount():baseItemChg.getDiscAmount());
			customer.setSetAdd(baseItemChg.getAmount()-baseItemChg.getSetMinus());
			baseItemChg.setTax(Double.parseDouble(itemPlanDao.getTax(customer)));
			Result result = baseItemChgService.saveForProc(baseItemChg, evt.getDetailJson());
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
}
