package com.house.home.web.controller.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.finance.SupplierPrepay;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.workflow.Department;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.TaxPayeeService;
import com.house.home.service.finance.SupplierCheckService;
import com.house.home.service.workflow.WfProcInstService;

@Controller
@RequestMapping("/admin/supplierCheck")
public class SupplierCheckController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplierCheckController.class);

	@Autowired
	private SupplierCheckService supplierCheckService;
	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaxPayeeService taxPayeeService;
	/*表格相关*/
	
	/**
	 * 查询主页表格
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		splCheckOut.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		supplierCheckService.goJqGrid(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 供应商结算新增结算-新增页面表格
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridAddPurchase")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridAddPurchase(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierCheckService.goJqGridAddPurchase(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 供应商结算查看-按公司汇总
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridMainItemByCompany")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMainItemByCompany(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierCheckService.goJqGridMainItemByCompany(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 按材料类型3汇总
	 * @author	created by zb
	 * @date	2020-4-22--下午4:46:24
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridMainItemByItemType3")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMainItemByItemType3(HttpServletRequest request, 
			HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierCheckService.goJqGridMainItemByItemType3(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 楼盘部门汇总
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridMainItemByCustDept")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMainItemByCustDept(HttpServletRequest request, 
			HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierCheckService.goJqGridMainItemByCustDept(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 集成安装费表格
	 * @param request
	 * @param response
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridIntInstall")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridIntInstall(HttpServletRequest request, HttpServletResponse response, String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = supplierCheckService.goJqGridIntInstall(page, custCode);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 主项目表格
	 * @param request
	 * @param response
	 * @param checkOutNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridMainItem")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMainItem(HttpServletRequest request, HttpServletResponse response, String checkOutNo) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = supplierCheckService.goJqGridMainItem(page, checkOutNo);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 超出额表格
	 * @param request
	 * @param response
	 * @param nos
	 * @param splCode
	 * @param checkOutNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridExcess")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridExcess(HttpServletRequest request, HttpServletResponse response, String nos, String splCode, String checkOutNo) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = supplierCheckService.goJqGridExcess(page, nos, splCode, checkOutNo);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 预扣单表格
	 * @param request
	 * @param response
	 * @param nos
	 * @param splCode
	 * @param checkOutNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridWithHold")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridWithHold(HttpServletRequest request, HttpServletResponse response, String nos, String splCode, String checkOutNo) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = supplierCheckService.goJqGridWithHold(page, nos, splCode, checkOutNo);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goProcListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goProcListJqGrid(HttpServletRequest request,
			HttpServletResponse response,SplCheckOut splCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierCheckService.findProcListJqGrid(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/*页面跳转相关*/
	
	/**
	 * 跳转到主页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到查询主页");
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_list");
	}
	/**
	 * 跳转到新增页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到新增页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m_umState", "A");
		map.put("date", new Date());
		map.put("no", "保存时生成");
		map.put("status", "1");
		map.put("payType", "0");
		map.put("otherCost", 0);
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_save").addObject("data", map);
	}

	/**
	 * 跳转到新增结算 新增页面
	 * @param request
	 * @param response
	 * @param splCode
	 * @param splName
	 * @param checkOutNo
	 * @param nos
	 * @param maxCheckSeq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddPurchase")
	public ModelAndView goAddPurchase(HttpServletRequest request, HttpServletResponse response, String splCode, String splName, String checkOutNo, String nos, 
									   Integer maxCheckSeq) throws Exception {
		logger.debug("跳转到新增采购单结算页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m_umState", "A");
		map.put("checkOutNo", checkOutNo);
		map.put("splName", splName);
		map.put("splCode", splCode);
		map.put("nos", StringUtils.isBlank(nos)?"":nos);
		map.put("maxCheckSeq", maxCheckSeq);
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_addPurchase").addObject("data", map);
	}

	/**
	 * 跳转到集成安装费页面
	 * @param request
	 * @param response
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIntInstall")
	public ModelAndView goIntInstall(HttpServletRequest request, HttpServletResponse response, String custCode) throws Exception {
		logger.debug("跳转到集成安装费页面");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m_umState", "V");
		map.put("custCode", custCode);
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_intInstall").addObject("data", map);
	}

	/**
	 * 供应商结算编辑页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到编辑页面");
		Map<String, Object> map = supplierCheckService.getSplCheckOutByNo(no);
		map.put("m_umState", "M");
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_save").addObject("data", map);
	}

	/**
	 * 供应商结算审核页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到审核页面");
		Map<String, Object> map = supplierCheckService.getSplCheckOutByNo(no);
		map.put("m_umState", "C");
		map.put("noChecAppReturnNum", supplierCheckService.getNoChecAppReturnNum((String) map.get("splCode")));
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_save").addObject("data", map);
	}

	/**
	 * 供应商结算反审核页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCheckBack")
	public ModelAndView goCheckBack(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到反审核页面");
		Map<String, Object> map = supplierCheckService.getSplCheckOutByNo(no);
		map.put("m_umState", "B");
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_save").addObject("data", map);
	}
	
	/**
	 * 供应商结算录入凭证号页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goInputDocument")
	public ModelAndView goInputDocument(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到录入凭证号页面");
		Map<String, Object> map = supplierCheckService.getSplCheckOutByNo(no);
		map.put("m_umState", "L");
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_save").addObject("data", map);
	}
	/**
	 * 供应商结算查看页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到查看页面");
		Map<String, Object> map = supplierCheckService.getSplCheckOutByNo(no);
		map.put("m_umState", "V");
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_save").addObject("data", map);
	}
	
	@RequestMapping("/goWfProcApply")
	public ModelAndView goWfProcApply(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("no") String no) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		// 根据流程标识获取最后一个版本的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey("purchaseExpense")
			.latestVersion()
			.singleResult();
		
		String url = FileUploadUtils.DOWNLOAD_URL;
		
		// 获取对应的流程
        WfProcess wfProcess = this.wfProcInstService.getWfProcessByProcKey(processDefinition.getKey());
		String wfProcNo = "";
		if(wfProcess != null){
			wfProcNo = wfProcess.getNo();
			wfProcess.setRemarks((wfProcess.getRemarks()==null?"":wfProcess.getRemarks()).replace("\r\n", "<br/>"));
		}
		
		SplCheckOut splCheckOut = supplierCheckService.get(SplCheckOut.class, no);
		Supplier supplier = new Supplier();
		Employee employee = wfProcInstService.get(Employee.class, this.getUserContext(request).getEmnum());
		// 获取明细 根据类型合计
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> detailJson = new HashMap<String, Object>();
		Map<String , Object> detailMap = new HashMap<String, Object>();
		Map<String , Object> amountMap = new HashMap<String, Object>();
		List<Map<String, Object>> taxPayeeList = new ArrayList<Map<String, Object>>();
		ItemType1 itemType1 = new ItemType1();
		
		taxPayeeList = taxPayeeService.getTaxPayeeList();
		
		if(splCheckOut != null){
			dataList = supplierCheckService.getDetailByCheckOutNo(splCheckOut);
			supplier = supplierCheckService.get(Supplier.class, splCheckOut.getSplCode());
			amountMap = supplierCheckService.getAmountByCheckOutNo(splCheckOut);
			
			if(supplier != null && StringUtils.isNotBlank(supplier.getItemType1())){
				itemType1 = supplierCheckService.get(ItemType1.class, supplier.getItemType1());
				detailJson.put("fp__tWfCust_PurchaseExpense__0__ItemType1", itemType1.getDescr());
			}
			
			detailJson.put("fp__tWfCust_PurchaseExpense__0__ApplyDate", new Date());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__ClaimRemarks", "材料款");
			detailJson.put("fp__tWfCust_PurchaseExpense__0__RefNo", splCheckOut.getNo());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__IsSpecDay", (supplier.getSpecDay() != null && supplier.getSpecDay() == 1)?"是":"否");
			detailJson.put("fp__tWfCust_PurchaseExpense__0__RcvBank", supplier.getBank());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__RcvActName", supplier.getActName());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__RcvCardId", supplier.getCardID());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__Remarks", splCheckOut.getRemark());
			detailJson.put("PreAmount",splCheckOut.getPreAmount());
			
			if(amountMap != null){
				detailJson.put("fp__tWfCust_PurchaseExpense__0__PaidAmount",amountMap.get("PaidAmount").toString());
//				detailJson.put("fp__tWfCust_PurchaseExpense__0__RealAmount",
//						Double.parseDouble(amountMap.get("Amount").toString()) - Double.parseDouble(amountMap.get("PaidAmount").toString()));
				// 精度
				detailJson.put("fp__tWfCust_PurchaseExpense__0__RealAmount",
						Arith.sub(Double.parseDouble(amountMap.get("Amount").toString()),
								Double.parseDouble(amountMap.get("PaidAmount").toString())));
				
				detailJson.put("fp__tWfCust_PurchaseExpense__0__ClaimAmount", Double.parseDouble(amountMap.get("Amount").toString()));
			}
		}
		System.out.println("-------");
		System.out.println();
		
		detailMap.put("tWfCust_PurchaseExpense", 1);
		if(dataList != null && dataList.size()>0){
			for(int i = 0; i < dataList.size(); i++){
				Map<String, Object> dtlMap = dataList.get(i);
				if(i==0){
					if(dtlMap.get("DelivType") != null && "1".equals(dtlMap.get("DelivType").toString())){
						detailJson.put("fp__tWfCust_PurchaseExpense__0__Type", "集采");
					} else {
						detailJson.put("fp__tWfCust_PurchaseExpense__0__Type", "非集采");
					}
				}
				detailJson.put("fp__tWfCust_PurchaseExpenseDtl__"+i+"__Amount", dtlMap.get("DetailAmount"));
				detailJson.put("fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeCode", dtlMap.get("TaxPayeeCode"));
				detailJson.put("fp__tWfCust_PurchaseExpenseDtl__"+i+"__TaxPayeeDescr", dtlMap.get("TaxPayeeDescr"));
			}

			detailMap.put("tWfCust_PurchaseExpenseDtl", dataList.size());
		}
		
		// 初始化数据
		if(employee != null){
			Department department = new Department();
			Department1 department1 = new Department1();
			department = employeeService.get(Department.class, employee.getDepartment());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__EmpCode", this.getUserContext(request).getCzybh());
			detailJson.put("fp__tWfCust_PurchaseExpense__0__EmpName", employee.getNameChi());
			department1 = employeeService.get(Department1.class,employee.getDepartment1());
			if(department != null){
				Map<String, Object> cmpData = wfProcInstService.getEmpCompany(employee.getDepartment());
    			if(cmpData != null){
    				employee.setCmpCode(cmpData.get("Code").toString());
    				employee.setCmpDescr(cmpData.get("CmpDescr").toString());
    			}
			}
			if(department1 != null){
				detailJson.put("fp__tWfCust_PurchaseExpense__0__DeptCode", department1.getCode());
				detailJson.put("fp__tWfCust_PurchaseExpense__0__DeptDescr", department1.getDesc1());
			}
		}
		
		String startMan = this.getUserContext(request).getCzybh();
		
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("processDefinition", processDefinition)
			.addObject("processDefinitionKey", processDefinition.getId())
			.addObject("wfProcNo",wfProcNo)
			.addObject("applyPage", processDefinition.getKey()+".jsp")
			.addObject("m_umState", "A")
			.addObject("datas", detailJson)
			.addObject("detailJson", JSONObject.toJSONString(detailJson))
			.addObject("processInstanceId", "processInstanceId")
			.addObject("detailList",JSONObject.toJSONString(detailMap))
			.addObject("startMan", startMan)
			.addObject("wfProcess", wfProcess)
			.addObject("url", url)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("employee", employee).addObject("activityId", "startevent")
			.addObject("taxPayeeList", taxPayeeList);
	}
	
	@RequestMapping("/goViewProcTrack")
	public ModelAndView goViewProcTrack(HttpServletRequest request,
			HttpServletResponse response, SplCheckOut splcheckOut) throws Exception {
		
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_viewProcTrack").addObject("splchekcOut", splcheckOut);
	}
	
	/*操作相关*/
	
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		splCheckOut.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		supplierCheckService.goJqGrid(page, splCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"供应商结算-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
	
	/**
	 * 新增结算保存
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		try{
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			Result result = supplierCheckService.doSaveForProc(splCheckOut);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "新增结算异常");
		}
	}
	
	/**
	 * 供应商结算审核通过
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@RequestMapping("/doSavePass")
	public void doSavePass(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		try{
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			splCheckOut.setConfirmCzy(splCheckOut.getLastUpdatedBy());
			splCheckOut.setStatus("2");
			Result result = supplierCheckService.doShForProc(splCheckOut);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "新增结算异常");
		}
	}
	/**
	 * 供应商结算审核取消
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@RequestMapping("/doSaveCancel")
	public void doSaveCancel(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		try{
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			splCheckOut.setConfirmCzy(splCheckOut.getLastUpdatedBy());
			splCheckOut.setStatus("3");
			Result result = supplierCheckService.doShForProc(splCheckOut);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "新增结算异常");
		}
	}
	/**
	 * 供应商结算反审核
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@RequestMapping("/doSavePassBack")
	public void doSavePassBack(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		try{
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			splCheckOut.setConfirmCzy(splCheckOut.getLastUpdatedBy());
			splCheckOut.setStatus("4");
			Result result = supplierCheckService.doShForProc(splCheckOut);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "新增结算异常");
		}
	}
	/**
	 * 检查供应商付款情况
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/checkSupplierPay")
	@ResponseBody
	public boolean checkSupplierPay(HttpServletRequest request, HttpServletResponse response, String no){
		boolean result = false;
		Map<String, Object> map = supplierCheckService.checkSupplierPay(no);
		if(map != null){
			result = true;
		}
		return result;
	}
	/**
	 * 打印检查操作
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doPrintBefore")
	@ResponseBody
	public Map<String, Object> doPrintBefore(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = supplierCheckService.judgePrintPage(no);
		if(list != null && list.size() > 0){
			map.put("page", "2");
		}else{
			map.put("page", "1");
		}
		map.put("no", no);
		Xtcs cmpnyName = supplierCheckService.get(Xtcs.class, "CMPNYNAME");
		Xtcs titles = supplierCheckService.get(Xtcs.class, "Titles");
		map.put("cmpnyName", cmpnyName.getQz());
		map.put("titles", "('"+titles.getQz().trim().replace(",", "','")+"')");
		return map;
	}
	
	/**
	 *供应商采购费用明细生成
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@ResponseBody
	@RequestMapping("/doGenOtherCost")
	public void GenOtherCost(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			map = supplierCheckService.doGenOtherCostForProc(splCheckOut);
			ServletUtils.outPrint(request, response, true, "计算完成", map, true);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "生成其他费用失败");
		}
		//return map;
	}
	
	@RequestMapping("/goLpPrint")
	public ModelAndView goLpPrint(HttpServletRequest request ,
			HttpServletResponse response ,String no) throws Exception{
		
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_print_lp").addObject("no", no);
			
	}
	
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request ,
			HttpServletResponse response ,SplCheckOut splCheckOut) throws Exception{
		
		return new ModelAndView("admin/finance/supplierCheck/supplierCheck_printIncludeAll")
		.addObject("splCheckOut", splCheckOut);
			
	}
}
