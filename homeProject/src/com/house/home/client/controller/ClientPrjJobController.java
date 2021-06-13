package com.house.home.client.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseStatusEvt;
import com.house.home.client.service.evt.DealPrjJobEvt;
import com.house.home.client.service.evt.DealPrjJobQueryEvt;
import com.house.home.client.service.evt.GetPrjJobPhotoListEvt;
import com.house.home.client.service.evt.JobTypeQueryEvt;
import com.house.home.client.service.evt.PrjJobAddEvt;
import com.house.home.client.service.evt.PrjJobEmployeeQueryEvt;
import com.house.home.client.service.evt.PrjJobQueryEvt;
import com.house.home.client.service.evt.PrjJobUpdateEvt;
import com.house.home.client.service.evt.UploadPhotoEvt;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.JobTypeQueryResp;
import com.house.home.client.service.resp.JobTypeResp;
import com.house.home.client.service.resp.PrjJobEmployeeQueryResp;
import com.house.home.client.service.resp.PrjJobQueryResp;
import com.house.home.client.service.resp.PrjJobResp;
import com.house.home.client.service.resp.PrjJobPhotoDetailResp;
import com.house.home.client.service.resp.PrjJobPhotoResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.JobType;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.PrjJobPhoto;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.JobTypeService;
import com.house.home.service.project.PrjJobPhotoService;
import com.house.home.service.project.PrjJobService;

/**
 * 项目任务相关的接口
 * @author 
 *
 */
@RequestMapping("/client/prjJob")
@Controller
public class ClientPrjJobController extends ClientBaseController{
	@Autowired
	private PrjJobService prjJobService;
	@Autowired
	private PrjJobPhotoService prjJobPhotoService;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	
	/**
	 * 查看项目任务列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjJobList")
	public void getPrjJobList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjJobQueryEvt evt=new PrjJobQueryEvt();
		BasePageQueryResp<PrjJobQueryResp> respon=new BasePageQueryResp<PrjJobQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjJobQueryEvt)JSONObject.toBean(json,PrjJobQueryEvt.class);
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
			PrjJob prjJob = new PrjJob();
			prjJob.setAppCzy(evt.getProjectMan());
			prjJob.setAddress(evt.getAddress());
			prjJob.setStatus(evt.getStatus());
			prjJobService.findPageBySql_forClient(page, prjJob);
			List<PrjJobQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjJobQueryResp.class);
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
	 * 查看项目任务详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjJob")
	public void getPrjJob(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		PrjJobResp respon=new PrjJobResp();
		
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
			
			Map<String,Object> prjJob = prjJobService.getByNo(evt.getId());
			if (prjJob==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(prjJob, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 待处理项目任务列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDealPrjJobList")
	public  void getDealPrjJobList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DealPrjJobQueryEvt evt=new DealPrjJobQueryEvt();
		BasePageQueryResp<PrjJobQueryResp> respon=new BasePageQueryResp<PrjJobQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DealPrjJobQueryEvt)JSONObject.toBean(json,DealPrjJobQueryEvt.class);
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
			PrjJob prjJob = new PrjJob();
			prjJob.setDealCzy(evt.getDealCzy());
			prjJob.setAddress(evt.getAddress());
			prjJob.setStatus(evt.getStatus());
			prjJobService.getDealPrjJobList(page, prjJob);
			List<PrjJobQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjJobQueryResp.class);
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
	 * 修改项目任务状态接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePrjJobStatus")
	public void updatePrjJobStatus(HttpServletRequest request, HttpServletResponse response){
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
			PrjJob prjJob = prjJobService.get(PrjJob.class, evt.getId());
			if (prjJob==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			JobType jobType = prjJobPhotoService.get(JobType.class, prjJob.getJobType());
			if (jobType==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			/**
			 * 增加提交状态和指派状态任务单判断
			 */
			if(!"1".equals(jobType.getAllowManySubmit())&&"1".equals(prjJob.getStatus().trim()) && "2".equals(evt.getStatus()) && prjJobService.existPrjJob(prjJob.getCustCode(), prjJob.getJobType(), "2,3") != null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("存在提交、指派的任务单,无法重复提交");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			if ("1".equals(evt.getStatus())){//收回状态修改为草稿
				if (prjJob.getStatus().trim().equals("2")){
					prjJob.setStatus(evt.getStatus());
					prjJob.setLastUpdate(new Date());
					prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
					prjJobService.update(prjJob);
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非提交状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}else{
				if (prjJob.getStatus().trim().equals("1")){//申请状态的才能修改
					prjJob.setStatus(evt.getStatus());
					prjJob.setLastUpdate(new Date());
					prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
					//申请集成测量时，如果无集成设计师干系人不允许提交。提示：该楼盘无集成设计师干系人，请先做集成设计师申请。
/*					if ("01".equals(prjJob.getJobType().trim()) && "2".equals(prjJob.getStatus().trim())){
						int i = custStakeholderService.getCount(prjJob.getCustCode(), "11");
						if (i==0){
							respon.setReturnCode("400001");
							respon.setReturnInfo("该楼盘无集成设计师干系人，请先做集成设计师申请。");
							returnJson(respon, response, msg, respon, request,interfaceLog);
							return;
						}
					}*/
					prjJobService.update(prjJob);
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
	 * 新增项目任务接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addPrjJob")
	public void addPrjJob(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjJobAddEvt evt=new PrjJobAddEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjJobAddEvt)JSONObject.toBean(json,PrjJobAddEvt.class);
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
			JobType jobType = jobTypeService.get(JobType.class, evt.getJobType());
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if ("0".equals(jobType.getCanEndCust()) && "5".equals(customer.getStatus().trim())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("完工工地不允许保存");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			PrjJob prjJob = new PrjJob();
			BeanUtilsEx.copyProperties(prjJob, evt);

			/**
			 * 增加提交状态和指派状态任务单判断
			 */
			if(!"1".equals(jobType.getAllowManySubmit())&&prjJobService.existPrjJob(prjJob.getCustCode(), prjJob.getJobType(), "2,3") != null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("存在提交、指派的任务单,无法重复提交");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			//申请集成测量时，如果无集成设计师干系人不允许提交。提示：该楼盘无集成设计师干系人，请先做集成设计师申请。
/*			if ("01".equals(prjJob.getJobType().trim()) && "2".equals(prjJob.getStatus().trim())){
				int i = custStakeholderService.getCount(prjJob.getCustCode(), "11");
				if (i==0){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该楼盘无集成设计师干系人，请先做集成设计师申请。");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
			}*/
			String empCode = prjJobService.getDefaultDealMan(prjJob.getCustCode(),jobType.getRole(),jobType.getCode());
			if (StringUtils.isNotBlank(empCode)){
				prjJob.setDealCzy(empCode);
			}
			if("1".equals(evt.getIsNeedReq())){
				if(!prjJobService.isNeedReq(evt.getCustCode(),evt.getItemType1())){
					ItemType1 itemType1 = this.prjJobService.get(ItemType1.class, evt.getItemType1());
					String str = itemType1 != null && StringUtils.isNotBlank(itemType1.getDescr()) ? itemType1.getDescr() : "软装"; 
					respon.setReturnCode("400001");
					respon.setReturnInfo("该工地没有" + str + "预算,不允许申请");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
			}
			prjJob.setFromInfoAdd(evt.getFromInfoAdd());
			prjJob.setInfoPk(evt.getInfoPk());
			if (!prjJobService.addPrjJob(prjJob)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("新增项目任务失败");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 修改项目任务接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePrjJob")
	public void updatePrjJob(HttpServletRequest request, HttpServletResponse response){
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjJobUpdateEvt evt=new PrjJobUpdateEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjJobUpdateEvt)JSONObject.toBean(json,PrjJobUpdateEvt.class);
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
			JobType jobType = jobTypeService.get(JobType.class, evt.getJobType());
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if ("0".equals(jobType.getCanEndCust()) && "5".equals(customer.getStatus().trim())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("完工工地不允许保存");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			PrjJob prjJob = prjJobService.get(PrjJob.class, evt.getNo());
			if (prjJob==null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("信息不存在");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if (!"1".equals(prjJob.getStatus().trim())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("只有草稿状态，才能编辑");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			/**
			 * 增加提交状态和指派状态任务单判断
			 */
			if(!"1".equals(jobType.getAllowManySubmit())&&"2".equals(evt.getStatus()) && prjJobService.existPrjJob(prjJob.getCustCode(), prjJob.getJobType(), "2,3") != null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("存在提交、指派的任务单,无法重复提交");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			prjJob.setCustCode(evt.getCustCode());
			prjJob.setItemType1(evt.getItemType1());
			prjJob.setJobType(evt.getJobType());
			prjJob.setDealCzy(evt.getDealCzy());
			prjJob.setRemarks(evt.getRemarks());
			prjJob.setStatus(evt.getStatus());
			prjJob.setDate(new Date());
			prjJob.setAppCzy(evt.getAppCzy());
			prjJob.setPhotoString(evt.getPhotoString());
			prjJob.setLastUpdate(new Date());
			prjJob.setLastUpdatedBy(evt.getAppCzy());
			prjJob.setActionLog("EDIT");
			prjJob.setWarBrand(evt.getWarBrand());
			prjJob.setCupBrand(evt.getCupBrand());
			//申请集成测量时，如果无集成设计师干系人不允许提交。提示：该楼盘无集成设计师干系人，请先做集成设计师申请。
/*			if ("01".equals(prjJob.getJobType().trim()) && "2".equals(prjJob.getStatus().trim())){
				int i = custStakeholderService.getCount(prjJob.getCustCode(), "11");
				if (i==0){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该楼盘无集成设计师干系人，请先做集成设计师申请。");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
			}*/
			String empCode = prjJobService.getDefaultDealMan(prjJob.getCustCode(),jobType.getRole(),jobType.getCode());
			if (StringUtils.isNotBlank(empCode)){
				prjJob.setDealCzy(empCode);
			}
			//需要预算控制时,如果没有预算不能申请
			System.out.println(evt.getCustCode()+" "+("1".equals(evt.getIsNeedReq())?"是":"否")+evt.getItemType1());
			if("1".equals(evt.getIsNeedReq())){
				if(!prjJobService.isNeedReq(evt.getCustCode(),evt.getItemType1())){
					ItemType1 itemType1 = this.prjJobService.get(ItemType1.class, evt.getItemType1());
					String str = itemType1 != null && StringUtils.isNotBlank(itemType1.getDescr()) ? itemType1.getDescr() : "软装"; 
					respon.setReturnCode("400001");
					respon.setReturnInfo("该工地没有" + str + "预算,不允许申请");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
			}
			if (!prjJobService.updatePrjJob(prjJob)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("修改项目任务失败");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 项目处理接口
	 */
	@RequestMapping("/dealPrjJob")
	public void dealPrjJob(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DealPrjJobEvt evt=new DealPrjJobEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DealPrjJobEvt) JSONObject.toBean(json, DealPrjJobEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			PrjJob prjJob = prjJobService.get(PrjJob.class, evt.getNo());
			if (prjJob==null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("信息不存在");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			prjJob.setErrPosi(evt.getErrPosi());
			prjJob.setStatus("4");
			prjJob.setDealDate(new Date());
			prjJob.setDealCzy(evt.getDealCzy());
			prjJob.setLastUpdate(new Date());
			prjJob.setLastUpdatedBy(evt.getDealCzy());
			prjJob.setDealRemark(evt.getDealRemark());
			prjJob.setEndCode(evt.getEndCode());
			prjJob.setAddress(evt.getAddress());
			prjJobService.dealPrjJob(prjJob, evt.getPhotoNameList());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 项目任务图片上传(app)
	 * 
	 * @param request
	 * @param response
	 */
	/*@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadPrjJob")
	public void uploadPrjJob(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			String fileRealPath = "";//文件存放真实地址
			String photoPath = "";
			String firstFileName = ""; 
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 设置允许上传的最大文件大小 500k
//			upload.setSizeMax(500 * 1024);
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);
			String lastUpdatedBy="",prjJobNo="";
			List<String> fileRealPathList = new ArrayList<String>();
			List<String> fileNameList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				if (obit.isFormField()) { //普通域,获取页面参数
					String fieldName = obit.getFieldName();
					String fieldValue = obit.getString();
					if ("prjJobNo".equals(fieldName)){
						prjJobNo = fieldValue;
					}
				}
				// 如果是多媒体
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					// 如果item是文件上传表单域
					// 获得文件名及路径
					String fileName = item.getName();
					if (fileName != null) {
						firstFileName = item.getName().substring(
								item.getName().lastIndexOf("\\") + 1);
						String formatName = firstFileName
								.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
						String fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
						String filePath = getPrjProgPhotoUploadPath(fileNameNew);
						FileUploadServerMgr.makeDir(filePath);
						fileRealPath = filePath + fileNameNew;// 文件存放真实地址
						photoPath = getPrjProgPhotoDownloadPath(request,fileNameNew) + fileNameNew;
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());// 获得文件输入流
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
						Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
						in.close();
						outStream.close();
						fileRealPathList.add(photoPath);
						fileNameList.add(fileNameNew);
						
					}
				}
			}
			if (StringUtils.isNotBlank(prjJobNo)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						PrjJobPhoto photo = new PrjJobPhoto();
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						if (StringUtils.isNotBlank(lastUpdatedBy)){//delphi调用接口用
							photo.setLastUpdatedBy(lastUpdatedBy);
						}else{
							photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						}
						photo.setActionLog("Add");
						photo.setExpired("F");
						photo.setType("1");
						photo.setPrjJobNo(prjJobNo);
						prjJobPhotoService.save(photo);
					}
				}
			}
			respon.setPhotoPathList(fileRealPathList);
			respon.setPhotoNameList(fileNameList);
			
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}*/
	
	@RequestMapping("/uploadPrjJob")
	public void uploadPrjJob(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		UploadPhotoResp respon = new UploadPhotoResp();
		
		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String prjJobNo = multipartFormData.getParameter("no");
			String photoPath = "";
			String fileNameNew = "";
			String firstFileName = "";
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> fileRealPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
            	firstFileName = attatchment.getName().substring(
            			attatchment.getName().lastIndexOf("\\") + 1);
				String formatName = firstFileName
						.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
				fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
                PrjProgNewUploadRule rule =
                        new PrjProgNewUploadRule(fileNameNew, fileNameNew.substring(0,5));
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                		rule.getFileName(),rule.getFilePath());
                fileNameList.add(fileNameNew);
                fileRealPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
            }
			if (StringUtils.isNotBlank(prjJobNo)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						PrjJobPhoto photo = new PrjJobPhoto();
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						photo.setActionLog("Add");
						photo.setExpired("F");
						photo.setType("1");
						photo.setPrjJobNo(prjJobNo);
						photo.setIsSendYun("1");
						photo.setSendDate(new Date());
						prjJobPhotoService.save(photo);
					}
				}
			}
			respon.setPhotoPathList(fileRealPathList);
			respon.setPhotoNameList(fileNameList);
			
			returnJson(respon,response,msg,respon,request,null);
			ServletUtils.outPrintSuccess(request, response,fileNameNew);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
	}
	
	/**
	 * 获取工地巡检图片列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjJobPhotoList")
	public void getPrjJobPhotoList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetPrjJobPhotoListEvt evt = new GetPrjJobPhotoListEvt(); // BaseGetEvt 改为 GetPrjJobPhotoListEvt(增加类型参数) update by zzr 2018/04/09
		BaseListQueryResp<PrjJobPhotoResp> respon = new BaseListQueryResp<PrjJobPhotoResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetPrjJobPhotoListEvt) JSONObject.toBean(json,GetPrjJobPhotoListEvt.class);
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
			List<String> list = prjJobPhotoService.getPhotoListByPrjJobNo(evt.getId(), evt.getType());
			List<PrjJobPhotoResp> listBean = new ArrayList<PrjJobPhotoResp>();
			if (list!=null && list.size()>0){
				for (String str : list){
					if (StringUtils.isNotBlank(str)){
						FileUploadRule rule = PrjProgNewUploadRule.fromUploadedFile(str);
						String path = FileUploadUtils.getFileUrl(rule.getFullName());
						PrjJobPhotoResp prjJobPhotoResp = new PrjJobPhotoResp();
						prjJobPhotoResp.setPhotoPath(path);
						prjJobPhotoResp.setSrc(str);
						prjJobPhotoResp.setPhotoName(str);
						listBean.add(prjJobPhotoResp);
						prjJobPhotoResp = null;
					}
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 获取工地巡检单个图片接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjJobPhoto")
	public void getPrjJobPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		PrjJobPhotoDetailResp respon = new PrjJobPhotoDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json,
					BaseGetEvt.class);
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
			PrjJobPhoto prjJobPhoto = prjJobPhotoService.get(PrjJobPhoto.class, Integer.parseInt(evt.getId()));
			if (prjJobPhoto!=null && StringUtils.isNotBlank(prjJobPhoto.getPhotoName())){
				PrjProgNewUploadRule rule = new PrjProgNewUploadRule(prjJobPhoto.getPhotoName(), prjJobPhoto.getPhotoName().substring(0, 5));
				String path = FileUploadUtils.getFileUrl(rule.getFullName());
				respon.setPhotoName(path);
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 查看项目任务处理人列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjJobEmployeeList")
	public void getPrjJobEmployeeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjJobEmployeeQueryEvt evt=new PrjJobEmployeeQueryEvt();
		BasePageQueryResp<PrjJobEmployeeQueryResp> respon=new BasePageQueryResp<PrjJobEmployeeQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjJobEmployeeQueryEvt)JSONObject.toBean(json,PrjJobEmployeeQueryEvt.class);
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
			Employee employee = new Employee();
			employee.setCode(evt.getCode());
			employee.setNameChi(evt.getNameChi());
			employee.setProjectMan(evt.getProjectMan());
			employeeService.findPageBySql_forClient(page, employee);
			List<PrjJobEmployeeQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjJobEmployeeQueryResp.class);
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
	 * 查看任务类型详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getJobType")
	public void getJobType(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		JobTypeResp respon=new JobTypeResp();
		
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
			JobType jobType = jobTypeService.get(JobType.class, evt.getId());
			if (jobType==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanUtilsEx.copyProperties(respon, jobType);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看任务类型列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getJobTypeList")
	public void getJobTypeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		JobTypeQueryEvt evt=new JobTypeQueryEvt();
		BasePageQueryResp<JobTypeQueryResp> respon=new BasePageQueryResp<JobTypeQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (JobTypeQueryEvt)JSONObject.toBean(json,JobTypeQueryEvt.class);
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
			JobType jobType = new JobType();
			jobType.setItemType1(evt.getItemType1());
			jobTypeService.findPageBySql(page, jobType);
			List<JobTypeQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), JobTypeQueryResp.class);
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

	@RequestMapping("/getPrjJobTypeList")
	public void getPrjJobTypeList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		JobTypeQueryEvt evt=new JobTypeQueryEvt();
		BasePageQueryResp<JobTypeQueryResp> respon=new BasePageQueryResp<JobTypeQueryResp>();

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		UserContext uc=getUserContext(request);//(UserContext) request.getSession().getAttribute(CommonConstant.USER_APP_KEY);
		String itemRight = uc.getItemRight().trim();
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (JobTypeQueryEvt)JSONObject.toBean(json,JobTypeQueryEvt.class);
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
			List<Map<String,Object>> taskTypeList = jobTypeService.getPrjJobTypeList(itemRight);
			List<JobTypeQueryResp> listBean = BeanConvertUtil.mapToBeanList(taskTypeList, JobTypeQueryResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjJobReceiveList")
	public  void getPrjJobReceiveList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DealPrjJobQueryEvt evt=new DealPrjJobQueryEvt();
		BasePageQueryResp<PrjJobQueryResp> respon=new BasePageQueryResp<PrjJobQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		UserContext uc= getUserContext(request);//(UserContext) request.getSession().getAttribute(CommonConstant.USER_APP_KEY);
		String itemRight = uc.getItemRight().trim();
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DealPrjJobQueryEvt)JSONObject.toBean(json,DealPrjJobQueryEvt.class);
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
			PrjJob prjJob = new PrjJob();
			prjJob.setDealCzy(evt.getDealCzy());
			prjJob.setAddress(evt.getAddress());
			prjJob.setStatus(evt.getStatus());
			prjJob.setJobType(evt.getJobType());
			prjJobService.getPrjJobReceiveList(page, prjJob,itemRight);
			List<PrjJobQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjJobQueryResp.class);
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
	@RequestMapping("/updatePrjJobReceive")
	public void updatePrjJobReceive(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DealPrjJobEvt evt=new DealPrjJobEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DealPrjJobEvt) JSONObject.toBean(json, DealPrjJobEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			PrjJob prjJob = prjJobService.get(PrjJob.class, evt.getNo());
			if (prjJob==null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("信息不存在");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			prjJob.setStatus("3");
			prjJob.setDealCzy(evt.getDealCzy());
			prjJobService.receivePrjJob(prjJob);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 图片删除接口
	 */
	@RequestMapping("/deletePrjJobPhoto")
	public void deletePrjJobPhoto(HttpServletRequest request,HttpServletResponse response){
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
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
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
			for(String str:arr){
				PrjProgNewUploadRule rule = new PrjProgNewUploadRule(str,str.substring(0, 5));
				FileUploadUtils.deleteFile(rule.getOriginalPath());
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

	@RequestMapping("/getErrPosi")
	public void getErrPosi(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DealPrjJobEvt evt=new DealPrjJobEvt();
		PrjJobResp respon=new PrjJobResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DealPrjJobEvt) JSONObject.toBean(json, DealPrjJobEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			
			PrjJob prjJob = prjJobService.get(PrjJob.class, evt.getNo());
			if (prjJob==null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("信息不存在");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
		
			Customer customer = customerService.get(Customer.class,prjJob.getCustCode());
			if(customer.getBuilderCode()!=null){
				Builder builder = prjJobService.get(Builder.class, customer.getBuilderCode());
				if(builder.getLongitude()!=null&&builder.getLongitude().longValue()!=0){
					int limitDistance = builder.getOffset()>0?builder.getOffset():PosiBean.LIMIT_DISTANCE;
					System.out.println(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude()));
					if(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude())>limitDistance){
						respon.setErrPosi("1");
					}else{
						respon.setErrPosi("0");
					}
				}else{
					respon.setErrPosi("1");
				}
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getSupplJob")
	public void getSupplJob(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BasePageQueryResp<PrjJobResp> respon=new BasePageQueryResp<PrjJobResp>();
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
			PrjJob prjJob = new PrjJob();
			prjJob.setNo(evt.getId());
			
			Page<Map<String,Object>> page = new Page<Map<String,Object>>();
			prjJobService.findSupplPageBySql(page, prjJob);
			List<PrjJobResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjJobResp.class);
			
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
