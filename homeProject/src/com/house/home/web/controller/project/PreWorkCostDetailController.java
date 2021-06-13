package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.JobType;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.SalaryType;
import com.house.home.entity.project.WorkType12;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.WorkType2Service;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PreWorkCostDetailService;
import com.house.home.service.project.WorkerService;

@Controller
@RequestMapping("/admin/preWorkCostDetail")
public class PreWorkCostDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PreWorkCostDetailController.class);

	@Autowired
	private PreWorkCostDetailService preWorkCostDetailService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerService employeeService;
	
	@Autowired
	private WorkType2Service workType2Service;
	
	@Autowired
	private XtcsService xtcsService;
	
	@Autowired
	private CustTypeService custTypeService;
	
	@Autowired
	private WorkerService workerService;
	
	@Autowired
	private CzybmService czybmService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,PreWorkCostDetail pWorkCostDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		pWorkCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		UserContext uc=this.getUserContext(request);
		preWorkCostDetailService.findPageBySql(page, pWorkCostDetail,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description:  定额工资分页查询
	 * @author	created by zb
	 * @date	2018-11-7--下午3:53:26
	 */
	@RequestMapping("/goQuotaSalaryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getQuotaSalaryJqGrid(HttpServletRequest request,
			HttpServletResponse response,PreWorkCostDetail pWorkCostDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		preWorkCostDetailService.getQuotaSalaryJqGrid(page,pWorkCostDetail);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 已审核工资明细分页查询
	 * @param request
	 * @param response
	 * @param pWorkCostDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWorkCostDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWorkCostDetailJqGrid(HttpServletRequest request, HttpServletResponse response,
															PreWorkCostDetail pWorkCostDetail, String hasBaseCheckItemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		preWorkCostDetailService.goWorkCostDetailJqGrid(page,pWorkCostDetail, hasBaseCheckItemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * PreWorkCostDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, PreWorkCostDetail preWorkCostDetail) throws Exception {
		preWorkCostDetail.setExpired("T");		
		return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_list").addObject("preWorkCostDetail", preWorkCostDetail);
	}

	/**
	 * PreWorkCostDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/preWorkCostDetail/preWorkCostDetail_code");
	}

	/**
	 * 跳转到修改PreWorkCostDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PreWorkCostDetail页面");
		PreWorkCostDetail preWorkCostDetail = null;
		if (StringUtils.isNotBlank(id)){
			preWorkCostDetail = preWorkCostDetailService.get(PreWorkCostDetail.class, Integer.parseInt(id));
		}else{
			preWorkCostDetail = new PreWorkCostDetail();
		}
		
		return new ModelAndView("admin/project/preWorkCostDetail/preWorkCostDetail_update")
			.addObject("preWorkCostDetail", preWorkCostDetail);
	}
	
	/**
	 * 跳转到新增PreWorkCostDetail页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增PreWorkCostDetail页面");
		
		return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_add");
	}
	
	/**
	 * @Description:  跳转到定额工资详细页面
	 * @author	created by zb
	 * @date	2018-11-7--下午3:46:56
	 */
	@RequestMapping("/goQuotaSalaryDetail")
	public ModelAndView goQuotaSalaryDetail(HttpServletRequest request, HttpServletResponse response, 
			PreWorkCostDetail preWorkCostDetail){
		logger.debug("跳转到查看定额工资详细页面");
		
		return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_quotaSalary")
				.addObject("preWorkCostDetail", preWorkCostDetail);
	}

	/**
	 * 跳转到已审核工资明细页面
	 * @param request
	 * @param response
	 * @param preWorkCostDetail
	 * @return
	 */
	@RequestMapping("/goWorkCostView")
	public ModelAndView goWorkCostView(HttpServletRequest request, HttpServletResponse response, PreWorkCostDetail preWorkCostDetail, String hasBaseCheckItemPlan){
		logger.debug("跳转到已审核工资明细页面");
		
		return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_workCostView")
				.addObject("preWorkCostDetail", preWorkCostDetail).addObject("hasBaseCheckItemPlan", hasBaseCheckItemPlan);
	}
	
	/**
	 * 添加PreWorkCostDetail
	 * @param request
	 * @param response
	 * @param preWorkCostDetail
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PreWorkCostDetail preWorkCostDetail){
		logger.debug("添加PreWorkCostDetail开始");
		try{
			//如果客户已结算，不允许保存、提交工人工资申请，并且今天-CustCheckDate>=3
			Customer customer = customerService.get(Customer.class, preWorkCostDetail.getCustCode());
			if ("0".equals(preWorkCostDetail.getIsWithHold())){
				if ("3".equals(customer.getCheckStatus().trim()) || "4".equals(customer.getCheckStatus().trim())){
					ServletUtils.outPrintFail(request, response, "正在进行或已完成项目经理结算，不允许申请工资");
					return;
				}
			
				/*if ("2".equals(customer.getCheckStatus().trim()) && DateUtil.dateDiff(new Date(), customer.getCustCheckDate())>=3){
					ServletUtils.outPrintFail(request, response, "客户已结算，不允许保存、提交工人工资申请");
					return;
				}*/ //delete by xzy date：20200403
			}
			
			if (StringUtils.isBlank(preWorkCostDetail.getStatus())){
				preWorkCostDetail.setStatus("1");//1 草稿
			}
			else{
				preWorkCostDetail.setStatus(preWorkCostDetail.getStatus());
				// 20201117 mark by lp 不控制需要填写进度
//				if ("2".equals(preWorkCostDetail.getStatus())){
//					boolean flag = preWorkCostDetailService.canCommit(preWorkCostDetail.getCustCode(),preWorkCostDetail.getWorkType2());
//					if (flag==false){
//						ServletUtils.outPrintFail(request, response, "请先填写好施工进度，才能提交工资申请，当前申请单可以先保存为草稿。");
//						return;
//					}
//				}
			}
			preWorkCostDetail.setWithHoldNo(0);
			preWorkCostDetail.setApplyMan(getUserContext(request).getCzybh());
			preWorkCostDetail.setApplyDate(new Date());
			preWorkCostDetail.setLastUpdate(new Date());
			preWorkCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			preWorkCostDetail.setActionLog("ADD");
			preWorkCostDetail.setExpired("F");
			preWorkCostDetail.setCfmAmount(preWorkCostDetail.getAppAmount());
			preWorkCostDetailService.save(preWorkCostDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改PreWorkCostDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PreWorkCostDetail preWorkCostDetail){
		logger.debug("修改PreWorkCostDetail开始");
		try{
			preWorkCostDetail.setLastUpdate(new Date());
			preWorkCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.preWorkCostDetailService.update(preWorkCostDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PreWorkCostDetail失败");
		}
	}
	
	/**
	 * 删除PreWorkCostDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PreWorkCostDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PreWorkCostDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PreWorkCostDetail preWorkCostDetail = preWorkCostDetailService.get(PreWorkCostDetail.class, Integer.parseInt(deleteId));
				if(preWorkCostDetail == null)
					continue;
				preWorkCostDetail.setExpired("T");
				preWorkCostDetailService.update(preWorkCostDetail);
			}
		}
		logger.debug("删除PreWorkCostDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PreWorkCostDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PreWorkCostDetail preWorkCostDetail){
		
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=this.getUserContext(request);
		preWorkCostDetailService.findPageBySql(page, preWorkCostDetail,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人工资审批_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/gojingli")
	public void jingli(HttpServletRequest request,HttpServletResponse response,PreWorkCostDetail pWorkCostDetail){
		logger.debug("经理审核权限检测开始");
		pWorkCostDetail.setXtcsid("SalAssCfm");
		Map<String, Object>  listxtcs= preWorkCostDetailService.getXTCS(pWorkCostDetail.getXtcsid());
		if ((listxtcs!=null)&&(listxtcs.size()>0)) {				
			pWorkCostDetail.setXtcsid(listxtcs.get("QZ").toString()); //系统参数	工资助理审批，1：必须审批，0：可不审批
		}
		try {           //工资助理审批  1：必须审批     
			if ("1".equals(pWorkCostDetail.getXtcsid())){
				if(!"3".equals(pWorkCostDetail.getStatus())){
					ServletUtils.outPrintFail(request, response, "只能对一级已审核状态的工人工资进行审核操作!");
					return ;
				}
			}else{   	//工资助理审批  0：可不审批    status=2   是提交状态，  3 是助理已 
				if ((!"2".equals(pWorkCostDetail.getStatus())) && (!"3".equals(pWorkCostDetail.getStatus()))) {
					ServletUtils.outPrintFail(request, response, "只能对一级已审核状态的工人工资进行审核操作!");
					return ;
				}
			}	
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "二级审核权限检测异常");
		}
		ServletUtils.outPrintFail(request, response, "success");
	}
	
	
	@SuppressWarnings("null")
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,PreWorkCostDetail pw){
		logger.debug("跳转到工人工资审批页面");
		UserContext uc=this.getUserContext(request);
		PreWorkCostDetail pWorkCostDetail = null;
		Customer customer = null;			
		pWorkCostDetail = preWorkCostDetailService.get(PreWorkCostDetail.class, pw.getPk());
		customer=customerService.get(Customer.class, pWorkCostDetail.getCustCode());
		Xtcs xtcs_BaseOneCtrl=xtcsService.get(Xtcs.class, "BaseOneCtrl");
		Xtcs xtcs_BaseAllCtrl=xtcsService.get(Xtcs.class, "BaseAllCtrl");
		Xtcs xtcs_IsUpWater=xtcsService.get(Xtcs.class, "IsUpWater");
		CustType custType=custTypeService.get(CustType.class, customer.getCustType());
		String salayType=StringUtils.deleteWhitespace(pWorkCostDetail.getSalaryType());
		pWorkCostDetail.setSalaryType(salayType);
		String workType2=StringUtils.deleteWhitespace(pWorkCostDetail.getWorkType2());
		pWorkCostDetail.setWorkType2(workType2);
		pWorkCostDetail.setWorkLoadDescr(pw.getWorkLoad());
		// 通过code获取WorkType2，进而得到isConfirmTwo --add by zb
		WorkType2 wType2 = workType2Service.get(WorkType2.class, workType2);
		if (pWorkCostDetail.getApplyMan()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, pWorkCostDetail.getApplyMan());
			pWorkCostDetail.setApplyManDescr(employee==null?"":employee.getNameChi());
		}
		// 设置质检安排工人信息：根据工种分类2表中的worktype12检查工地工人信息表中是否存在对应数据——存在显示“是” --add by zb
		boolean hasCustWork = preWorkCostDetailService.hasCustWork(workType2);
		if (hasCustWork) {
			pWorkCostDetail.setHasCustWork("1");
		} else {
			pWorkCostDetail.setHasCustWork("0");
		}
		
		//工人申请金额为空时，显示为0
		if(pWorkCostDetail.getWorkAppAmount()==null){
			pWorkCostDetail.setWorkAppAmount((double) 0);
		}
		 
		//工人工资审批页面，能够显示对应工地、工种、工人的定额工资及明细。--add by zb
		String quotaSalary = preWorkCostDetailService.getQuotaSalary(pWorkCostDetail);
		pWorkCostDetail.setQuotaSalary(Double.parseDouble(quotaSalary));
		
		//判断是否上传水电定位图
		List<Map<String,Object>>  listmsg= preWorkCostDetailService.getMsg(pw.getPk());			
		if ((listmsg!=null)&&(listmsg.size()>0)){
			Map<String,Object> mapmsg=listmsg.get(0);
			if( ("1").equals(StringUtils.deleteWhitespace((String) mapmsg.get("IsUpPosiPic")))){ 
				pWorkCostDetail.setMsg("1"); //设置 pWorkCostDetail.setMsg    1代表已经上传水电定位图到售后，0代表未上传			
			}else {
				 if(("012").equals(StringUtils.deleteWhitespace((String) mapmsg.get("WorkType2")))
					|| ("019").equals(StringUtils.deleteWhitespace((String) mapmsg.get("WorkType2")))	
					|| ("021").equals(StringUtils.deleteWhitespace((String) mapmsg.get("WorkType2")))	
					|| ("008").equals(StringUtils.deleteWhitespace((String) mapmsg.get("WorkType2")))){
					pWorkCostDetail.setMsg("0");
				 }else{
					 pWorkCostDetail.setMsg("1");
				 }	
			}	
		}else{
			pWorkCostDetail.setWorkType1("1");	
		}
		//通过worktype2取到workType1
		Map<String, Object>  list= preWorkCostDetailService.getWorkType1(pWorkCostDetail.getWorkType2());			
		if ((list!=null)&&(list.size()>0)) {
			pWorkCostDetail.setWorkType1(list.get("WorkType1").toString());	
		}
		
		//WorkCon:合同总价
		Map<String,Object>  list0= preWorkCostDetailService.getWorkCon(customer.getCode(),pWorkCostDetail.getWorkType2());			
		if ((list0!=null)&&(list0.size()>0)){
			
			if (("").equals(list0.get("Amount").toString())){
				pWorkCostDetail.setWorkCon("0");
			}else {
				pWorkCostDetail.setWorkCon(list0.get("Amount").toString());
			}		
		}else{
			pWorkCostDetail.setWorkCon("0");	
		}
		//Amount:预扣金额
		if (pWorkCostDetail.getWithHoldNo()!=null){
			Map<String,Object>  list1= preWorkCostDetailService.getAmount(pWorkCostDetail.getWithHoldNo().toString());										
			if((list1!=null)&&(list1.size()>0)){					
				pWorkCostDetail.setYukou((Double)list1.get("Amount"));	
			}else {	
				pWorkCostDetail.setYukou((double) 0);
			}
		}
		if (("1").equals((pWorkCostDetail.getIsWithHold())) && (pWorkCostDetail.getWithHoldNo()!=null) &&(pWorkCostDetail.getWithHoldNo()!=0)){
			//ret1:领取金额    WithHoldNo:预扣单号
			List<Map<String,Object>>  list2= preWorkCostDetailService.getret(pWorkCostDetail.getWithHoldNo().toString());		
			Map<String,Object> map2=list2.get(0);
			if (map2.get("ret1")!=null){
				pWorkCostDetail.setRet1((Double)map2.get("ret1"));				
			}else {
				pWorkCostDetail.setRet1((double) 0);	
			}				
		}else {
			pWorkCostDetail.setRet1((double) 0);	
		}	
		
		//AllCustCtrl:总支出,AllCustCost:总支出,CustCtrl:工种发包
		//CustCost:工种支出,AllLeaveCustCost:总发包余额,LeaveCustCost:工种余额
		//ConfirmRemark:审批说明,ConfirmAmount:审批金额
		List<Map<String,Object>>  list3= preWorkCostDetailService.getPk(pw.getPk());
		if((list3!=null)&&(list3.size()>0)){
			Map<String,Object> map3=list3.get(0);		
			if (map3.get("AllCustCtrl")!=null){
				pWorkCostDetail.setAllCustCtrl((Double)map3.get("AllCustCtrl"));	
			}else {
				pWorkCostDetail.setAllCustCtrl((double) 0);	
			}
			if (map3.get("AllCustCost")!=null){
				pWorkCostDetail.setAllCustCost((Double)map3.get("AllCustCost"));	
			}else {
				pWorkCostDetail.setAllCustCost((double) 0);	
			}
			if (map3.get("CustCtrl")!=null){
				pWorkCostDetail.setCustCtrl((Double)map3.get("CustCtrl"));	
			}else {
				pWorkCostDetail.setCustCtrl((double) 0);	
			}
			if (map3.get("CustCost")!=null){
				pWorkCostDetail.setCustCost((Double)map3.get("CustCost"));	
			}else {
				pWorkCostDetail.setCustCost((double) 0);	
			}
			if (map3.get("AllLeaveCustCost")!=null){
				pWorkCostDetail.setAllLeaveCustCost((Double)map3.get("AllLeaveCustCost"));	
			}else {
				pWorkCostDetail.setAllLeaveCustCost((double) 0);	
			}
			if (map3.get("LeaveCustCost")!=null){
				pWorkCostDetail.setLeaveCustCost((Double)map3.get("LeaveCustCost"));	
			}else {
				pWorkCostDetail.setLeaveCustCost((double) 0);	
			}
			if (map3.get("LeaveCustCost")!=null){
				pWorkCostDetail.setLeaveCustCost((Double)map3.get("LeaveCustCost"));	
			}else {
				pWorkCostDetail.setLeaveCustCost((double) 0);	
			}
			pWorkCostDetail.setConfirmRemark((String)map3.get("ConfirmRemark"));
			if (map3.get("ConfirmAmount")!=null){
				pWorkCostDetail.setConfirmAmount((Double)map3.get("ConfirmAmount"));	
			}else {
				pWorkCostDetail.setConfirmAmount((double) 0);	
			}		
		}else {
			pWorkCostDetail.setAllCustCtrl((double) 0);
			pWorkCostDetail.setAllCustCost((double) 0);
			pWorkCostDetail.setCustCtrl((double) 0);	
			pWorkCostDetail.setCustCost((double) 0);	
			pWorkCostDetail.setAllLeaveCustCost((double) 0);
			pWorkCostDetail.setLeaveCustCost((double) 0);
			pWorkCostDetail.setLeaveCustCost((double) 0);
			pWorkCostDetail.setConfirmAmount((double) 0);
		}
		
		
		//PrjItem:施工项目,EndDate:完成日期,ConfirmCZY:验收人,ConfirmDate:验收日期
		List<Map<String,Object>>  list4= preWorkCostDetailService.getCodeType(pWorkCostDetail.getCustCode(),pWorkCostDetail.getWorkType2());							
		if((list4!=null)&&(list4.size()>0)){
			Map<String,Object> map4=list4.get(0);				
			pWorkCostDetail.setPrjItem((String)map4.get("PrjItem"));
			pWorkCostDetail.setEndDate((Date)map4.get("EndDate"));
			pWorkCostDetail.setConfirmCZY((String)map4.get("ConfirmCZY"));
			pWorkCostDetail.setConfirmDate((Date)map4.get("ConfirmDate"));
			if (map4.get("ConfirmCZY")!=null) {
				Employee employee1= null;		
				employee1 = employeeService.get(Employee.class, pWorkCostDetail.getConfirmCZY());
				pWorkCostDetail.setConfirmCZYDescr(employee1==null?"":employee1.getNameChi());
			}	
		}
		//当客户编号不为空的时候
		//AllCustCtrl:总发包,AllCustCost:总发包,AllLeaveCustCost:总发包金额
		if(pWorkCostDetail.getCustCode()!=null){
			List<Map<String,Object>>  list5= preWorkCostDetailService.getNotNullCustCode(pWorkCostDetail.getCustCode());
			if((list5!=null)&&(list5.size()>0)){
				Map<String,Object> map5=list5.get(0);
				if (map5.get("AllCustCtrl")!=null){
					pWorkCostDetail.setAllCustCtrl((Double)map5.get("AllCustCtrl"));	
				}else {
					pWorkCostDetail.setAllCustCtrl((double) 0);	
				}	
				if (map5.get("AllCustCost")!=null){
					pWorkCostDetail.setAllCustCost((Double)map5.get("AllCustCost"));	
				}else {
					pWorkCostDetail.setAllCustCost((double) 0);	
				}
				if (map5.get("AllLeaveCustCost")!=null){
					pWorkCostDetail.setAllLeaveCustCost((Double)map5.get("AllLeaveCustCost"));	
				}else {
					pWorkCostDetail.setAllLeaveCustCost((double) 0);	
				}			
				}else {
					pWorkCostDetail.setAllCustCtrl((double) 0);	
					pWorkCostDetail.setAllCustCost((double) 0);
					pWorkCostDetail.setAllLeaveCustCost((double) 0);
			}
		}
		//当客户编号WorkType2工种类型2不为空的时候
		if	(pWorkCostDetail.getWorkType2()!=null){
			List<Map<String,Object>>  list6= preWorkCostDetailService.getNotNullWorkType2(pWorkCostDetail.getCustCode(),
					pWorkCostDetail.getWorkType2());
			if ((list6!=null)&&(list6.size()>0)) {
				Map<String,Object> map6=list6.get(0);	
				if (map6.get("CustCtrl")!=null){
					pWorkCostDetail.setCtrlCost((Double)map6.get("CustCtrl"));						
					pWorkCostDetail.setCtrlCost1((Double)map6.get("CustCtrl")*1.1);
					java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
					Double a=Double.valueOf(df.format(pWorkCostDetail.getCtrlCost1()));
					pWorkCostDetail.setCtrlCost1(a);
				}else {
					pWorkCostDetail.setCtrlCost((double) 0);	
				}
				if (map6.get("LeaveCustCost")!=null){
					pWorkCostDetail.setLeaveCustCost((Double)map6.get("LeaveCustCost"));	
				}else {
					pWorkCostDetail.setLeaveCustCost((double) 0);	
				}
			}else {
				pWorkCostDetail.setCtrlCost((double)0);
				pWorkCostDetail.setLeaveCustCost((double) 0);	
				
			}
		}else {//如果工种类型2为0的话       工种发包、工种支出、工种余额、人工控制项      四项为0
			pWorkCostDetail.setCustCtrl((double) 0);
			pWorkCostDetail.setCustCost((double) 0);
			pWorkCostDetail.setLeaveCustCost((double) 0);
			pWorkCostDetail.setCtrlCost1((double) 0);  
      
		}
		//是否面向工人解单
		String hasBaseCheckItemPlan="0";
		Map<String,Object>  list9= preWorkCostDetailService.hasBaseCheckItemPlan(customer.getCode());
		if ((list9!=null)&&(list9.size()>0)){		
			hasBaseCheckItemPlan="1";
		}
		//////     CfmAmount:已审核工资
		Map<String,Object>  list7= "1".equals(hasBaseCheckItemPlan)?preWorkCostDetailService.getCfmAmount(customer.getCode(),pWorkCostDetail.getWorkType2())
								:preWorkCostDetailService.getCfmAmountByWorkType1(customer.getCode(), pWorkCostDetail.getWorkType1());			
		if ((list7!=null)&&(list7.size()>0)){		
			if (list7.get("CfmAmount")==null){
				pWorkCostDetail.setWorkCost((double) 0);	
			}else {
				pWorkCostDetail.setWorkCost("1".equals(hasBaseCheckItemPlan)?(Double)list7.get("CfmAmount"):(Double)list7.get("CfmAmount")+pWorkCostDetail.getCustCost());				
			}	
		}else{
			pWorkCostDetail.setWorkCon("0");		
		}
		//  CfmAmountWt1:同工种1已审核工资
		Double cfmAmountWt1=0.0;
		Map<String,Object>  list8= preWorkCostDetailService.getCfmAmountByWorkType1(customer.getCode(),pWorkCostDetail.getWorkType1());			
		if ((list8!=null)&&(list8.size()>0)){		
			cfmAmountWt1=(Double)list8.get("CfmAmount");
		}
		
		double salaryCtrl = 0.0;
		if(StringUtils.isNotBlank(wType2.getWorkType12())){
			WorkType12 workType12 = this.preWorkCostDetailService.get(WorkType12.class, wType2.getWorkType12());
			if(workType12 != null && workType12.getSalaryCtrl() > 0){
				salaryCtrl = workType12.getSalaryCtrl();
			}
		}
		
		////   Z表示助理审核，M表示经理审核，ZF表示助理反审核，MF表示经理反审核
		if ("Z".equals(pw.getM_s())){       //Z表示助理审核
			boolean hasGNQXByCzybh = czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0320","审批管理");
			return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_zhuli")
			.addObject("pWorkCostDetail", pWorkCostDetail).addObject("customer", customer).addObject("czy", this.getUserContext(request).getCzybh())
			.addObject("workType2", wType2).addObject("BaseOneCtrl", xtcs_BaseOneCtrl.getQz()).addObject("BaseAllCtrl", xtcs_BaseAllCtrl.getQz())
			.addObject("custTypeType", custType.getType()).addObject("cfmAmountWt1", cfmAmountWt1).addObject("hasBaseCheckItemPlan", hasBaseCheckItemPlan)
			.addObject("salaryCtrl", salaryCtrl).addObject("IsUpWater",xtcs_IsUpWater.getQz()).addObject("baseSpec", custType.getBaseSpec()).addObject("hasGNQXByCzybh", hasGNQXByCzybh);		
		} 
		else if ("M".equals(pw.getM_s())){  //M表示经理审核
			boolean hasGNQXByCzybh = czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0320","审批管理");
			return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_jingli")
			.addObject("pWorkCostDetail", pWorkCostDetail).addObject("customer", customer).addObject("czy", this.getUserContext(request).getCzybh())
			.addObject("BaseOneCtrl", xtcs_BaseOneCtrl.getQz()).addObject("BaseAllCtrl", xtcs_BaseAllCtrl.getQz()).addObject("custTypeType", custType.getType())
			.addObject("cfmAmountWt1", cfmAmountWt1).addObject("hasBaseCheckItemPlan", hasBaseCheckItemPlan).addObject("salaryCtrl", salaryCtrl)
			.addObject("IsUpWater",xtcs_IsUpWater.getQz()).addObject("baseSpec", custType.getBaseSpec()).addObject("hasGNQXByCzybh", hasGNQXByCzybh);			
		}
		else if ("ZF".equals(pw.getM_s())){  //ZF表示助理反审核
			return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_zhulifan")
			.addObject("pWorkCostDetail", pWorkCostDetail).addObject("customer", customer).addObject("czy", this.getUserContext(request).getCzybh())
			.addObject("workType2", wType2).addObject("salaryCtrl", salaryCtrl).addObject("hasBaseCheckItemPlan", hasBaseCheckItemPlan);				
		}
		else if("MF".equals(pw.getM_s())){    //MF表示经理反审核
			return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_jinglifan")
			.addObject("pWorkCostDetail", pWorkCostDetail).addObject("customer", customer).addObject("czy", this.getUserContext(request).getCzybh())
			.addObject("salaryCtrl", salaryCtrl).addObject("hasBaseCheckItemPlan", hasBaseCheckItemPlan);				
		}
		else if("C".equals(pw.getM_s())){	//C表示查看
			return new ModelAndView("admin/project/preWorkCostDetail/PreWorkCostDetail_View")
			.addObject("pWorkCostDetail", pWorkCostDetail).addObject("customer", customer).addObject("czy", this.getUserContext(request).getCzybh())
			.addObject("salaryCtrl", salaryCtrl).addObject("hasBaseCheckItemPlan", hasBaseCheckItemPlan);				
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * 工种类型1，2 二级联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workTypeByAuthority/{type}/{pCode}") //获取商品类型1,2,3
	@ResponseBody
	public JSONObject getWorkTypeByAuthority(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = this.preWorkCostDetailService.findWorkTypeByAuthority(type, pCode,uc);
		return this.out(regionList, true);
	}
	
	/**
	 * 工种类型1，2 二级联动,类似项目经理app的
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWorkTypeByAuthorityForPrj/{type}/{pCode}") //获取商品类型1,2,3
	@ResponseBody
	public JSONObject getWorkTypeByAuthorityForPrj(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = this.preWorkCostDetailService.findWorkTypeByAuthorityForPrj(type, pCode,uc);
		return this.out(regionList, true);
	}
	
	
	/**
	 *助理审核
	 *
	 *操作类型 :
	 *Z助理，ZJ助理进行经理审核， J经理，F，助理反审核，ZE助理进行经理反审核，E，经理反审核，Q，取消审核
	 */
	@RequestMapping("/doZhuLiSave")
	public void doZhuLiSave(HttpServletRequest request,HttpServletResponse response,PreWorkCostDetail pWorkCostDetail){
		logger.debug("工人工资审批—审核开始");		
		try {
			if ("Z".equals(pWorkCostDetail.getM_s())){
				// 如果是tWorkType2中字段“isconfirmtwo”为0（不需要二级审核），审核后自动将状态改为“二级已审核” --add by zb
				if ("0".equals(pWorkCostDetail.getIsConfirmTwo())) {
					pWorkCostDetail.setM_umState("ZJ");	
				} else {
					pWorkCostDetail.setM_umState("Z");		
				}
				pWorkCostDetail.setConfirmAssist(this.getUserContext(request).getCzybh());
				Map<String, Object>  listzlqx= preWorkCostDetailService.getzlqx(pWorkCostDetail.getConfirmAssist()
						,pWorkCostDetail.getWorkerCode());//助理是否有审核该工种的权限
				if ("0".equals(listzlqx.get("Count").toString())){
					Map<String, Object>  listwork= preWorkCostDetailService.getworkType12(pWorkCostDetail.getWorkerCode());
					ServletUtils.outPrintFail(request, response, "该助理未拥有审核【"+listwork.get("Descr").toString()+"】工种的权限！");
					return;
				}	
			}
			else if ("M".equals(pWorkCostDetail.getM_s())) {
				pWorkCostDetail.setM_umState("J");	
			}
			else if ("ZF".equals(pWorkCostDetail.getM_s())) {
				//  如果是tWorkType2中字段“isconfirmtwo”为0（不需要二级审核），允许对“二级已审核”状态进行反审核。 --add by zb
				if ("0".equals(pWorkCostDetail.getIsConfirmTwo())) {
					pWorkCostDetail.setM_umState("ZE");		
				} else {
					pWorkCostDetail.setM_umState("F");		
				}
			}
			else if ("MF".equals(pWorkCostDetail.getM_s())) {
				pWorkCostDetail.setM_umState("E");		
			}
			pWorkCostDetail.setAssistConfirmDate(new Date());	
			pWorkCostDetail.setExpired("F"); 
			pWorkCostDetail.setLastUpdate(new Date());		
			pWorkCostDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());			
			Result result = this.preWorkCostDetailService.doZhuLiSave(pWorkCostDetail);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"操作成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	//取消审核
		@RequestMapping("/doCancel")
		public void doCancel(HttpServletRequest request,HttpServletResponse response,PreWorkCostDetail pWorkCostDetail){
			logger.debug("工人工资审批—取消审核");		
			try {
				pWorkCostDetail.setM_umState("Q");	
				pWorkCostDetail.setExpired("F"); 		
				pWorkCostDetail.setConfirmAssist(this.getUserContext(request).getCzybh());
				pWorkCostDetail.setAssistConfirmDate(new Date());
				pWorkCostDetail.setLastUpdate(new Date());		
				pWorkCostDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = this.preWorkCostDetailService.doZhuLiSave(pWorkCostDetail);
					if (result.isSuccess()){
						ServletUtils.outPrintSuccess(request, response,"取消审核");
					}else{
						ServletUtils.outPrintFail(request, response,result.getInfo());
					}
			} catch (Exception e) {
				ServletUtils.outPrintFail(request, response, "审核失败");
			}
		}
		
		/**
		 * 查询JqGrid2表格数据
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */

		
		@RequestMapping("/goJqGrid2")
		@ResponseBody
		public WebPage<Map<String,Object>> getJqGrid2(HttpServletRequest request,
				HttpServletResponse response,PreWorkCostDetail pWorkCostDetail) throws Exception {
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			preWorkCostDetailService.findPageBySql2(page, pWorkCostDetail);
			return new WebPage<Map<String,Object>>(page);
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @param pWorkCostDetail
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/getCfmAmountByWt1")
		@ResponseBody
		public String getCfmAmountByWt1(HttpServletRequest request,
				HttpServletResponse response,PreWorkCostDetail pWorkCostDetail) throws Exception {
			Map<String, Object> map=preWorkCostDetailService.getCfmAmountByWorkType1(pWorkCostDetail.getCustCode(), pWorkCostDetail.getWorkType1());
			if ((map!=null)&&(map.size()>0)){		
				return map.get("CfmAmount").toString();
			}
			return "0";
		}
		
		/**
		 * @param request
		 * @param response
		 * @param pWorkCostDetail
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/isExistsWt2")
		@ResponseBody
		public String isExistsWt2(HttpServletRequest request,
				HttpServletResponse response,PreWorkCostDetail pWorkCostDetail) throws Exception {
			List<Map<String, Object>> list = this.workerService.getWokerCostApply(pWorkCostDetail.getCustCode(), null, null, null, null, pWorkCostDetail.getWorkType2(), null);
			if(list != null && list.size() > 0){
				return "1";
			}
			return "0";
		}
		
		/**
		 * 
		 * @param request
		 * @param response
		 * @param pWorkCostDetail
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/getQuotaSalary")
		@ResponseBody
		public String getQuotaSalary(HttpServletRequest request,
				HttpServletResponse response,PreWorkCostDetail preWorkCostDetail) throws Exception {
			return preWorkCostDetailService.getQuotaSalary(preWorkCostDetail);
		}
		
		/**
		 * 已申请金额
		 * @param request
		 * @param response
		 * @param AppAmount
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/getAppAmount")
		@ResponseBody
		public String getAppAmount(HttpServletRequest request,
				HttpServletResponse response,CustWorker custWorker) throws Exception {
			return workerService.getAppAmount(custWorker).get("AppAmount").toString();
		}
}
