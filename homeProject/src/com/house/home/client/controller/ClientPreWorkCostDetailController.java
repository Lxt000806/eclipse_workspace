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
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.IsCommitEvt;
import com.house.home.client.service.evt.PreWorkCostDetailAddEvt;
import com.house.home.client.service.evt.PreWorkCostDetailEvt;
import com.house.home.client.service.evt.PreWorkCostDetailQueryEvt;
import com.house.home.client.service.evt.PreWorkCostDetailStatusEvt;
import com.house.home.client.service.evt.PreWorkCostDetailUpdateEvt;
import com.house.home.client.service.evt.PrjWithHoldQueryEvt;
import com.house.home.client.service.evt.WorkType2QueryEvt;
import com.house.home.client.service.evt.YkCustomerEvt;
import com.house.home.client.service.resp.AddPreWorkCostDetailResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustomerResp;
import com.house.home.client.service.resp.IsCommitResp;
import com.house.home.client.service.resp.ItemTypeQueryResp;
import com.house.home.client.service.resp.PreWorkCostDetailQueryResp;
import com.house.home.client.service.resp.PreWorkCostDetailResp;
import com.house.home.client.service.resp.PrjWithHoldDetailResp;
import com.house.home.client.service.resp.PrjWithHoldResp;
import com.house.home.client.service.resp.UpdatePreWorkCostDetailResp;
import com.house.home.client.service.resp.UpdatePreWorkCostDetailStatusResp;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.entity.project.PrjWithHold;
import com.house.home.entity.project.SalaryType;
import com.house.home.service.basic.WorkType2Service;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PreWorkCostDetailService;
import com.house.home.service.project.PrjWithHoldService;
import com.house.home.service.project.SalaryTypeService;
import com.house.home.service.project.WorkType12Service;
import com.house.home.service.project.WorkerService;

/**
 * 工人工资预申请相关的接口
 * @author 
 *
 */
@RequestMapping("/client/preWorkCostDetail")
@Controller
public class ClientPreWorkCostDetailController extends ClientBaseController{
	@Autowired
	private PreWorkCostDetailService preWorkCostDetailService;
	@Autowired
	private SalaryTypeService salaryTypeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PrjWithHoldService prjWithHoldService;
	@Autowired
	private PersonMessageDao personMessageDao;
	@Autowired
	private WorkType12Service workType12Service;
	@Autowired
	private WorkType2Service workType2Service;
	@Autowired
	private WorkerService workerService;
	
	private String message = "请先填写好施工进度，才能提交工资申请，当前申请单可以先保存为草稿。";
	
	/**
	 * 查看工人工资预申请列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPreWorkCostDetailList")
	public void getPreWorkCostDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PreWorkCostDetailQueryEvt evt=new PreWorkCostDetailQueryEvt();
		BasePageQueryResp<PreWorkCostDetailQueryResp> respon=new BasePageQueryResp<PreWorkCostDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PreWorkCostDetailQueryEvt)JSONObject.toBean(json,PreWorkCostDetailQueryEvt.class);
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
			PreWorkCostDetail preWorkCostDetail = new PreWorkCostDetail();
			preWorkCostDetail.setApplyMan(evt.getApplyMan());
			preWorkCostDetail.setAddress(evt.getAddress());
			preWorkCostDetail.setStatus(evt.getStatus());
			preWorkCostDetailService.findPageBySql_forClient(page, preWorkCostDetail);
			List<PreWorkCostDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PreWorkCostDetailQueryResp.class);
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
	 * 查看工人工资预申请详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPreWorkCostDetail")
	public void getPreWorkCostDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PreWorkCostDetailEvt evt=new PreWorkCostDetailEvt();
		PreWorkCostDetailResp respon=new PreWorkCostDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PreWorkCostDetailEvt)JSONObject.toBean(json,PreWorkCostDetailEvt.class);
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
			
			Map<String,Object> preWorkCostDetail = preWorkCostDetailService.getByPk(evt.getPk());
			if (preWorkCostDetail==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(preWorkCostDetail, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 修改工人工资预申请状态接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePreWorkCostDetailStatus")
	public void updatePreWorkCostDetailStatus(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PreWorkCostDetailStatusEvt evt=new PreWorkCostDetailStatusEvt();
		UpdatePreWorkCostDetailStatusResp respon=new UpdatePreWorkCostDetailStatusResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PreWorkCostDetailStatusEvt)JSONObject.toBean(json,PreWorkCostDetailStatusEvt.class);
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
			PreWorkCostDetail preWorkCostDetail = preWorkCostDetailService.get(PreWorkCostDetail.class, evt.getPk());
			if (preWorkCostDetail==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if("1".equals(preWorkCostDetail.getIsWorkApp())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("无法修改工人申请的记录");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if ("1".equals(evt.getStatus())){//收回状态修改为草稿
				if (preWorkCostDetail.getStatus().trim().equals("2")){
					preWorkCostDetail.setStatus(evt.getStatus());
					preWorkCostDetail.setLastUpdate(new Date());
					preWorkCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
					if("1".equals(preWorkCostDetail.getIsWorkApp())){
						preWorkCostDetail.setPrjConfirmDate(null);
					}
					preWorkCostDetailService.update(preWorkCostDetail);
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非提交状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}else{
				if (preWorkCostDetail.getStatus().trim().equals("1")){//申请状态的才能修改
					if ("2".equals(evt.getStatus()) && "0".equals(preWorkCostDetail.getIsWithHold())){
						boolean flag = preWorkCostDetailService.canCommit(preWorkCostDetail.getCustCode(),preWorkCostDetail.getWorkType2());
						if (flag==false){
							respon.setReturnCode("100000");
							respon.setReturnInfo(message);
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}
					}
					
					preWorkCostDetail.setStatus(evt.getStatus());
					preWorkCostDetail.setLastUpdate(new Date());
					preWorkCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
					if("1".equals(preWorkCostDetail.getIsWorkApp())){
						preWorkCostDetail.setPrjConfirmDate(null);
					}
					preWorkCostDetailService.update(preWorkCostDetail);
					if("1".equals(preWorkCostDetail.getIsWorkApp())){
						PersonMessage personMessage = new PersonMessage();
						personMessage.setMsgRelNo(preWorkCostDetail.getPk().toString());
						personMessage.setMsgType("12");
						personMessage.setMsgRelCustCode(preWorkCostDetail.getCustCode());
						Page page=new Page();
						page.setPageSize(-1);
						page.setPageNo(1);
						personMessageDao.findPageBySql(page,personMessage);
						List<PersonMessage> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), PersonMessage.class);
						for(PersonMessage message:listBean){
							message.setRcvStatus("1");
							message.setRcvDate(new Date());
							preWorkCostDetailService.update(message);
						}
					}
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
	 * 新增工人工资预申请接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addPreWorkCostDetail")
	public void addPreWorkCostDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PreWorkCostDetailAddEvt evt=new PreWorkCostDetailAddEvt();
		AddPreWorkCostDetailResp respon=new AddPreWorkCostDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PreWorkCostDetailAddEvt)JSONObject.toBean(json,PreWorkCostDetailAddEvt.class);
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
			//如果未上传水电定位图，除拆除（WorkType1='01'）和土工砌墙的工资（WorkType2='011'），其它不允许申请。
//			if ("01".equals(evt.getWorkType1().trim()) || "011".equals(evt.getWorkType2().trim())){
//				
//			}else{
//				Customer customer = customerService.get(Customer.class, evt.getCustCode());
//				if (!"1".equals(customer.getIsUpPosiPic())){
//					respon.setReturnCode("100000");
//					respon.setReturnInfo("未上传水电定位图，不允许申请工资！");
//					returnJson(respon,response,msg,respon,request,interfaceLog);
//					return;
//				}
//			}
			PreWorkCostDetail preWorkCostDetail = new PreWorkCostDetail();
			BeanUtilsEx.copyProperties(preWorkCostDetail, evt);

			//做流程判断控制(针对是否选择框),以后采用此模式进行控制,400002统一为confirm对话框的返回代码,其他为alert提示框返回代码 add by zzr 2018/06/14
			if(evt.getCheckStatus() != null && evt.getCheckStatus() > 0){
				switch(evt.getCheckStatus()){
					case 1:{
						//case 1 检测工人是否安排到该工地
/*						Map<String, Object> custWorkerMap = preWorkCostDetailService.getCustWorkerInfo(preWorkCostDetail.getCustCode(), preWorkCostDetail.getWorkerCode());
						
						if(custWorkerMap == null){
							respon.setReturnCode("400002");
							respon.setReturnInfo("申请工资工人非本工地施工工人,是否继续提交?");
							respon.setCheckStatus(evt.getCheckStatus()+1);
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}*/
						evt.setCheckStatus(evt.getCheckStatus()+1);
					};
					case 2:{
						List<Map<String, Object>> list = this.workerService.getWokerCostApply(evt.getCustCode(), null, null, null, null, evt.getWorkType2(), null);
						if(list != null && list.size() > 0){
							respon.setReturnCode("400002");
							respon.setReturnInfo("该客户存在该工种分类2的申请，是否确认再次申请？");
							respon.setCheckStatus(evt.getCheckStatus()+1);
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}
					}
				}
			}
			
			preWorkCostDetail.setM_umState("A");
			if (StringUtils.isBlank(preWorkCostDetail.getIsWithHold())){
				preWorkCostDetail.setIsWithHold("0");
			}
			//如果客户已结算，不允许保存、提交工人工资申请，并且今天-CustCheckDate>=3
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if ("0".equals(preWorkCostDetail.getIsWithHold())){
				if ("3".equals(customer.getCheckStatus().trim()) || "4".equals(customer.getCheckStatus().trim())){
					respon.setReturnCode("100000");
					respon.setReturnInfo("正在进行或已完成项目经理结算，不允许申请工资");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				if ("2".equals(customer.getCheckStatus().trim()) && DateUtil.dateDiff(new Date(), customer.getCustCheckDate())>=3){
					respon.setReturnCode("100000");
					respon.setReturnInfo("客户已结算，不允许保存、提交工人工资申请");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			
			if (StringUtils.isBlank(evt.getStatus())){
				preWorkCostDetail.setStatus("1");//1 草稿
			}else{
				preWorkCostDetail.setStatus(evt.getStatus());
				if ("2".equals(evt.getStatus()) && "0".equals(preWorkCostDetail.getIsWithHold())){
					boolean flag = preWorkCostDetailService.canCommit(preWorkCostDetail.getCustCode(),preWorkCostDetail.getWorkType2());
					if (flag==false){
						respon.setReturnCode("100000");
						respon.setReturnInfo(message);
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
				}
			}
			preWorkCostDetail.setApplyDate(new Date());
			preWorkCostDetail.setLastUpdate(new Date());
			preWorkCostDetail.setLastUpdatedBy(evt.getApplyMan());
			preWorkCostDetail.setActionLog("ADD");
			preWorkCostDetail.setExpired("F");
			if (StringUtils.isNotBlank(evt.getSalaryType())){
				SalaryType salaryType = salaryTypeService.get(SalaryType.class, evt.getSalaryType());
				if (salaryType!=null && StringUtils.isNotBlank(salaryType.getWorkType2())){
					preWorkCostDetail.setWorkType2(salaryType.getWorkType2());
				}
			}
			preWorkCostDetail.setCfmAmount(preWorkCostDetail.getAppAmount());
			preWorkCostDetail.setIsWorkApp("0");
			preWorkCostDetail.setCardId2(evt.getCardId2());
			preWorkCostDetailService.save(preWorkCostDetail);
			
			if("1".equals(evt.getFromInfoAdd())){
				PersonMessage pm = preWorkCostDetailService.get(PersonMessage.class, evt.getInfoPk());
				pm.setRcvDate(new Date());
				pm.setRcvStatus("1");
				pm.setDealNo(preWorkCostDetail.getPk().toString());
				preWorkCostDetailService.update(pm);
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 修改工人工资预申请接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePreWorkCostDetail")
	public void updatePreWorkCostDetail(HttpServletRequest request, HttpServletResponse response){
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PreWorkCostDetailUpdateEvt evt=new PreWorkCostDetailUpdateEvt();
		UpdatePreWorkCostDetailResp respon=new UpdatePreWorkCostDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PreWorkCostDetailUpdateEvt)JSONObject.toBean(json,PreWorkCostDetailUpdateEvt.class);
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
			//如果未上传水电定位图，除拆除（WorkType1='01'）和土工砌墙的工资（WorkType2='011'），其它不允许申请。
//			if ("01".equals(evt.getWorkType1().trim()) || "011".equals(evt.getWorkType2().trim())){
//				
//			}else{
//				Customer customer = customerService.get(Customer.class, evt.getCustCode());
//				if (!"1".equals(customer.getIsUpPosiPic())){
//					respon.setReturnCode("100000");
//					respon.setReturnInfo("未上传水电定位图，不允许申请工资！");
//					returnJson(respon,response,msg,respon,request,interfaceLog);
//					return;
//				}
//			}
			PreWorkCostDetail preWorkCostDetail = preWorkCostDetailService.get(PreWorkCostDetail.class, evt.getPk());
			String oldWorkerCode = (preWorkCostDetail!= null && preWorkCostDetail.getWorkerCode() != null) ? preWorkCostDetail.getWorkerCode().trim():""; 
			BeanUtilsEx.copyProperties(preWorkCostDetail, evt);

			//做流程判断控制(针对是否选择框),以后采用此模式进行控制,400002统一为confirm对话框的返回代码,其他为alert提示框返回代码 add by zzr 2018/06/14
			if(evt.getCheckStatus() != null && evt.getCheckStatus() > 0){
				switch(evt.getCheckStatus()){
					case 1:{
						//case 1 检测工人是否安排到该工地
						Map<String, Object> custWorkerMap = preWorkCostDetailService.getCustWorkerInfo(preWorkCostDetail.getCustCode(), preWorkCostDetail.getWorkerCode());
						
						if(custWorkerMap == null && !oldWorkerCode.equals(preWorkCostDetail.getWorkerCode().trim())){
							respon.setReturnCode("400002");
							respon.setReturnInfo("申请工资工人非本工地施工工人,是否继续提交?");
							respon.setCheckStatus(evt.getCheckStatus()+1);
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}
					};
				}
			}
			
			if (StringUtils.isBlank(preWorkCostDetail.getIsWithHold())){
				preWorkCostDetail.setIsWithHold("0");
			}
			//如果客户已结算，不允许保存、提交工人工资申请，并且今天-CustCheckDate>=3
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if ("0".equals(preWorkCostDetail.getIsWithHold())){
				if ("3".equals(customer.getCheckStatus().trim()) || "4".equals(customer.getCheckStatus().trim())){
					respon.setReturnCode("100000");
					respon.setReturnInfo("正在进行或已完成项目经理结算，不允许申请工资");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				if ("2".equals(customer.getCheckStatus().trim()) && DateUtil.dateDiff(new Date(), customer.getCustCheckDate())>=3){
					respon.setReturnCode("100000");
					respon.setReturnInfo("客户已结算，不允许保存、提交工人工资申请");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			
			if ("2".equals(evt.getStatus()) && "0".equals(preWorkCostDetail.getIsWithHold())){
				boolean flag = preWorkCostDetailService.canCommit(preWorkCostDetail.getCustCode(),preWorkCostDetail.getWorkType2());
				if (flag==false){
					respon.setReturnCode("100000");
					respon.setReturnInfo(message);
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			if("1".equals(preWorkCostDetail.getIsWorkApp())){
				preWorkCostDetail.setPrjConfirmDate(new Date());	
			}
			preWorkCostDetail.setM_umState("M");
			preWorkCostDetail.setOldStatus("1");//申请状态
			preWorkCostDetail.setStatus(evt.getStatus());
			preWorkCostDetail.setLastUpdate(new Date());
			preWorkCostDetail.setLastUpdatedBy(evt.getApplyMan());
			preWorkCostDetail.setActionLog("EDIT");
			if (StringUtils.isNotBlank(evt.getSalaryType())){
				SalaryType salaryType = salaryTypeService.get(SalaryType.class, evt.getSalaryType());
				if (salaryType!=null && StringUtils.isNotBlank(salaryType.getWorkType2())){
					preWorkCostDetail.setWorkType2(salaryType.getWorkType2());
				}
			}
			preWorkCostDetail.setCfmAmount(preWorkCostDetail.getAppAmount());
			preWorkCostDetail.setCardId2(evt.getCardId2());
			preWorkCostDetailService.update(preWorkCostDetail);
			if("1".equals(preWorkCostDetail.getIsWorkApp())){
				PersonMessage personMessage = new PersonMessage();
				personMessage.setMsgRelNo(preWorkCostDetail.getPk().toString());
				personMessage.setMsgType("12");
				personMessage.setMsgRelCustCode(preWorkCostDetail.getCustCode());
				Page page=new Page();
				page.setPageSize(-1);
				page.setPageNo(1);
				personMessageDao.findPageBySql(page,personMessage);
				List<PersonMessage> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), PersonMessage.class);
				for(PersonMessage message:listBean){
					message.setRcvStatus("1");
					message.setRcvDate(new Date());
					preWorkCostDetailService.update(message);
				}
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 是否可以提交工资申请接口
	 * 相应阶段未填写结束时间，不允许提交，只允许保存为草稿。提示“请先填写好施工进度，才能提交工资申请，当前申请单可以先保存为草稿。”
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getIsCommit")
	public void getIsCommit(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IsCommitEvt evt=new IsCommitEvt();
		IsCommitResp respon=new IsCommitResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IsCommitEvt)JSONObject.toBean(json,IsCommitEvt.class);
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
			
			boolean flag = preWorkCostDetailService.canCommit(evt.getCustCode(),evt.getWorkType2());
			if (flag==true){
				respon.setCanCommit(flag);
			}else{
				respon.setCanCommit(flag);
				respon.setMessage(message);
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取预扣客户列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getYkCustomerList")
	public void getYkCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		YkCustomerEvt evt=new YkCustomerEvt();
		BasePageQueryResp<CustomerResp> respon=new BasePageQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (YkCustomerEvt)JSONObject.toBean(json,YkCustomerEvt.class);
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
			if (StringUtils.isBlank(evt.getId())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customer.setAddress(evt.getAddress());
			customerService.findPageBySql_ykCustomer(page,customer);
			List<CustomerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerResp.class);
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
	 * 获取预扣客户列表接口new
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getYkCustomerList_new")
	public void getYkCustomerList_new(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		YkCustomerEvt evt=new YkCustomerEvt();
		BasePageQueryResp<CustomerResp> respon=new BasePageQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (YkCustomerEvt)JSONObject.toBean(json,YkCustomerEvt.class);
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
			if (StringUtils.isBlank(evt.getId())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customer.setAddress(evt.getAddress());
			customerService.findPageBySql_ykCustomer_new(page,customer);
			List<CustomerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerResp.class);
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
	 * 获取项目经理预扣单列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjWithHoldList")
	public void getPrjWithHoldList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjWithHoldQueryEvt evt=new PrjWithHoldQueryEvt();
		BasePageQueryResp<PrjWithHoldResp> respon=new BasePageQueryResp<PrjWithHoldResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjWithHoldQueryEvt)JSONObject.toBean(json,PrjWithHoldQueryEvt.class);
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
			PrjWithHold prjWithHold = new PrjWithHold();
			prjWithHold.setCustCode(evt.getCustCode());
			prjWithHold.setWorkType2(evt.getWorkType2());
			prjWithHold.setWorkType2Descr(evt.getWorkType2Descr());
			prjWithHoldService.findPageBySql_ykd(page,prjWithHold);
			List<PrjWithHoldResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjWithHoldResp.class);
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
	 * 获取项目经理预扣单接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjWithHold")
	public void getPrjWithHold(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		PrjWithHoldDetailResp respon=new PrjWithHoldDetailResp();
		
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
			Map<String,Object> map = prjWithHoldService.findByPk(Integer.parseInt(evt.getId()));
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getWorkType12Info")
	public void getWorkType12Info(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		YkCustomerEvt evt=new YkCustomerEvt();
		CustomerResp respon=new CustomerResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (YkCustomerEvt)JSONObject.toBean(json,YkCustomerEvt.class);
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
			Map<String,Object> map = workType12Service.getWorkType12InfoByCode(evt.getCode());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkType2List")
	public void getWorkType2List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkType2QueryEvt evt = new WorkType2QueryEvt();
		BasePageQueryResp<ItemTypeQueryResp> respon = new BasePageQueryResp<ItemTypeQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkType2QueryEvt) JSONObject.toBean(json, WorkType2QueryEvt.class);
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
			page.setPageOrderBy("dispSeq");
			page.setPageOrder("asc");
			WorkType2 workType2 = new WorkType2();
			if(StringUtil.notEmpty(evt.getWorkType2())){
				workType2 = workType2Service.get(WorkType2.class, evt.getWorkType2());
			}
			workType2.setWorkType1(evt.getWorkType1());
			workType2Service.getPreWorkCostWorkType2(page, workType2);
			List<ItemTypeQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemTypeQueryResp.class);
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
}
