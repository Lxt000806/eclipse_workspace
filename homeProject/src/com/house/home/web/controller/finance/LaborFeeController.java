package com.house.home.web.controller.finance;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.finance.LaborFeeDetailBean;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.LaborFee;
import com.house.home.entity.finance.LaborFeeAccount;
import com.house.home.entity.workflow.Department;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.driver.ItemAppSendService;
import com.house.home.service.finance.LaborFeeService;
import com.house.home.service.project.WorkerService;
import com.house.home.service.workflow.WfProcInstService;

@Controller
@RequestMapping("/admin/laborFee")
public class LaborFeeController extends BaseController {

	@Autowired
	private WorkerService workerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private LaborFeeService laborFeeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemAppSendService itemAppSendService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private WfProcInstService wfProcInstService;

	@RequestMapping(value = "/laborFeeFeeType/{type}/{pCode}")
	// 获取商品类型1,2,3
	@ResponseBody
	public JSONObject getFeeType(@PathVariable Integer type,
			@PathVariable String pCode, HttpServletRequest request) {
		List<Map<String, Object>> feeTypeList = this.laborFeeService
				.findFeeType(type, pCode);
		return this.out(feeTypeList, true);
	}

	@RequestMapping("/goItemSendNoCode")
	public ModelAndView goItemSendNoCode(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {

		return new ModelAndView("admin/finance/laborFee/itemSendNo_code")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/getItemSendNo")
	@ResponseBody
	public JSONObject getItemSendNo(HttpServletRequest request,
			HttpServletResponse response, String id) {
		if (StringUtils.isEmpty(id)) {
			return this.out("传入的id为空", false);
		}
		ItemAppSend itemAppSend = itemAppSendService.get(ItemAppSend.class, id);
		if (itemAppSend == null) {
			return this.out("系统中不存在发货单号：" + id, false);
		}
		return this.out(itemAppSend, true);
	}

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFee.setItemRight(this.getUserContext(request).getItemRight());
		laborFeeService.findPageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goLaborFeeAccountJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goLaborFeeAccountJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFee.setItemRight(this.getUserContext(request).getItemRight());
		laborFeeService.findLaborFeeAccountBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goItemSendNoJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goItemSendNoJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			ItemAppSend itemAppSend) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findItemSendNoPageBySql(page, itemAppSend);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goItemSendDetialJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goItemSendDetialJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			ItemAppSend itemAppSend) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findItemAppSendDetailPageBySql(page, itemAppSend);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goFindItemReqBySql")
	@ResponseBody
	public WebPage<Map<String, Object>> goFindItemReqBySql(
			HttpServletRequest request, HttpServletResponse response,
			ItemAppSend itemAppSend) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findItemReqBySql(page, itemAppSend);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 明细表
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findDetailPageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goDetailActNameJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailActNameJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findDetailActNamePageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goDetailCompanyJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goDetailCompanyJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findDetailCompanyPageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 按客户类型汇总标签页
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 */
	@RequestMapping("/goDetailCustTypeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goDetailCustTypeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findDetailCustTypePageBySql(page, laborFee);

		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 明细查询
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLaborDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getLaborDetailJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findLaborDetailPageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goProcListJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goProcListJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findProcListJqGrid(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goProcTrackJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goProcTrackJqGrid(HttpServletRequest request,
			HttpServletResponse response,LaborFee laborFee) throws Exception {
		
		JSONObject jsonObject = JSONObject.parseObject(request.getParameter("el"));

		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findProcTrackJqGrid(page, laborFee);
		if(StringUtils.isNotBlank(laborFee.getActProcDefId()) && StringUtils.isNotBlank(laborFee.getWfProcInstNo())){
			List<Map<String, Object>> hisList = page.getResult();
			List<Map<String, Object>> addList = wfProcInstService.getProcBranch(laborFee.getWfProcInstNo(), laborFee.getActProcDefId(), jsonObject) ;
			if(hisList != null && addList != null){
				hisList.addAll(addList);
				page.setResult(hisList);
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}


	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_list")
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {
		laborFee.setDate(new Date());
		laborFee.setAppCZY(this.getUserContext(request).getCzybh());
		laborFee.setStatus("1");
		return new ModelAndView("admin/finance/laborFee/laborFee_save")
				.addObject("laborFee", laborFee).addObject("appDescr",
						this.getUserContext(request).getZwxm());
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		LaborFee laborFee = null;
		Employee appCZY = null;
		if (StringUtils.isNotBlank(no)) {
			laborFee = laborFeeService.get(LaborFee.class, no);
		}
		if (StringUtils.isNotBlank(laborFee.getAppCZY())) {
			appCZY = employeeService.get(Employee.class, laborFee.getAppCZY());

		}
		return new ModelAndView("admin/finance/laborFee/laborFee_update")
				.addObject("laborFee", laborFee).addObject("appCZY", appCZY);
	}

	/**
	 * 审核
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		LaborFee laborFee = null;
		Employee appCZY = null;
		if (StringUtils.isNotBlank(no)) {
			laborFee = laborFeeService.get(LaborFee.class, no);
		}
		if (StringUtils.isNotBlank(laborFee.getAppCZY())) {
			appCZY = employeeService.get(Employee.class, laborFee.getAppCZY());
		}
		laborFee.setM_umState("C");
		laborFee.setConfirmCZY(this.getUserContext(request).getCzybh());
		laborFee.setConfirmDate(new Date());
		String confirmDescr = this.getUserContext(request).getZwxm();
		return new ModelAndView("admin/finance/laborFee/laborFee_check")
				.addObject("laborFee", laborFee).addObject("appCZY", appCZY)
				.addObject("confirmDescr", confirmDescr);
	}

	/**
	 * 输入凭证号
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdateDocumentNo")
	public ModelAndView goUpdateDocumentNo(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		LaborFee laborFee = null;
		Employee appCZY = null;
		Employee confirmCZY = null;
		if (StringUtils.isNotBlank(no)) {
			laborFee = laborFeeService.get(LaborFee.class, no);
		}
		if (StringUtils.isNotBlank(laborFee.getAppCZY())) {
			appCZY = employeeService.get(Employee.class, laborFee.getAppCZY());
		}
		if (StringUtils.isNotBlank(laborFee.getConfirmCZY())) {
			confirmCZY = employeeService.get(Employee.class,
					laborFee.getConfirmCZY());

		}
		return new ModelAndView(
				"admin/finance/laborFee/laborFee_insertDocumentNo")
				.addObject("laborFee", laborFee).addObject("appCZY", appCZY)
				.addObject("confirmCZY", confirmCZY);
	}

	/**
	 * 反审核
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReCheck")
	public ModelAndView goReCheck(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		LaborFee laborFee = null;
		Employee appCZY = null;
		Employee confirmCZY = null;
		if (StringUtils.isNotBlank(no)) {
			laborFee = laborFeeService.get(LaborFee.class, no.trim());
		}
		if (StringUtils.isNotBlank(laborFee.getAppCZY())) {
			appCZY = employeeService.get(Employee.class, laborFee.getAppCZY());
		}
		if (StringUtils.isNotBlank(laborFee.getConfirmCZY())) {
			confirmCZY = employeeService.get(Employee.class,
					laborFee.getConfirmCZY());
		}
		return new ModelAndView("admin/finance/laborFee/laborFee_reCheck")
				.addObject("laborFee", laborFee).addObject("appCZY", appCZY)
				.addObject("confirmCZY", confirmCZY);
	}

	/**
	 * 出纳签字
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSignature")
	public ModelAndView goSignature(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		LaborFee laborFee = null;
		Employee appCZY = null;
		Employee confirmCZY = null;

		if (StringUtils.isNotBlank(no)) {
			laborFee = laborFeeService.get(LaborFee.class, no);
			laborFee.setM_umState("W");
		}
		if (StringUtils.isNotBlank(laborFee.getAppCZY())) {
			appCZY = employeeService.get(Employee.class, laborFee.getAppCZY());
		}
		if (StringUtils.isNotBlank(laborFee.getConfirmCZY())) {
			confirmCZY = employeeService.get(Employee.class,
					laborFee.getConfirmCZY());
		}
		laborFee.setPayCZY(this.getUserContext(request).getCzybh());
		laborFee.setPayDate(new Date());
		String payDescr = this.getUserContext(request).getZwxm();
		return new ModelAndView("admin/finance/laborFee/laborFee_signature")
				.addObject("laborFee", laborFee).addObject("appCZY", appCZY)
				.addObject("confirmCZY", confirmCZY)
				.addObject("payDescr", payDescr);
	}

	/**
	 * 明细查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLaborDetail")
	public ModelAndView goLaborDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/laborFee/laborFee_detail");
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		LaborFee laborFee = null;
		Employee appCZY = null;
		Employee confirmCZY = null;
		Employee payCZY = null;
		if (StringUtils.isNotBlank(no)) {
			laborFee = laborFeeService.get(LaborFee.class, no);
			laborFee.setM_umState("V");
		}
		if (StringUtils.isNotBlank(laborFee.getAppCZY())) {
			appCZY = employeeService.get(Employee.class, laborFee.getAppCZY());
		}
		if (StringUtils.isNotBlank(laborFee.getConfirmCZY())) {
			confirmCZY = employeeService.get(Employee.class,
					laborFee.getConfirmCZY());
		}
		if (StringUtils.isNotBlank(laborFee.getPayCZY())) {
			payCZY = employeeService.get(Employee.class, laborFee.getPayCZY());
		}
		return new ModelAndView("admin/finance/laborFee/laborFee_view")
				.addObject("laborFee", laborFee).addObject("appCZY", appCZY)
				.addObject("confirmCZY", confirmCZY)
				.addObject("payCZY", payCZY);
	}

	@RequestMapping("/goViewSendDetail")
	public ModelAndView goViewSendDetail(HttpServletRequest request,
			HttpServletResponse response, String appSendNo) throws Exception {

		return new ModelAndView(
				"admin/finance/laborFee/laborFee_viewSendDetail").addObject(
				"appSendNo", appSendNo);
	}

	@RequestMapping("/goViewItemReq")
	public ModelAndView goViewItemReq(HttpServletRequest request,
			HttpServletResponse response, String custCode, String itemType1)
			throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_viewItemReq")
				.addObject("custCode", custCode).addObject("itemType1",
						itemType1);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {
		Xtcs xtcs = laborFeeService.get(Xtcs.class, "AftCustCode");
		return new ModelAndView("admin/finance/laborFee/laborFee_add")
				.addObject("laborFee", laborFee).addObject("AftCustCode",
						xtcs.getQz());
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {
		Customer customer = null;
		if (StringUtils.isNotBlank(laborFee.getCustCode())) {
			customer = customerService.get(Customer.class,
					laborFee.getCustCode());
			laborFee.setAddress(customer.getAddress());
			laborFee.setDocumentNo(customer.getDocumentNo() == null ? ""
					: customer.getDocumentNo());
			laborFee.setCheckDate(customer.getCustCheckDate());
			laborFee.setCheckStatus(laborFeeService
					.getCheckStatusDescr(customer.getCheckStatus()));
		}
		Xtcs xtcs = laborFeeService.get(Xtcs.class, "AftCustCode");
		return new ModelAndView("admin/finance/laborFee/laborFee_addUpdate")
				.addObject("laborFee", laborFee).addObject("AftCustCode",
						xtcs.getQz());
	}

	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {
		Customer customer = null;
		if (StringUtils.isNotBlank(laborFee.getCustCode())) {
			customer = customerService.get(Customer.class,
					laborFee.getCustCode());
			laborFee.setAddress(customer.getAddress());
			laborFee.setDocumentNo(customer.getDocumentNo() == null ? ""
					: customer.getDocumentNo());
			laborFee.setCheckDate(customer.getCustCheckDate());
			laborFee.setCheckStatus(laborFeeService
					.getCheckStatusDescr(customer.getCheckStatus()));
		}
		Xtcs xtcs = laborFeeService.get(Xtcs.class, "AftCustCode");
		return new ModelAndView("admin/finance/laborFee/laborFee_addView")
				.addObject("laborFee", laborFee).addObject("AftCustCode",
						xtcs.getQz());
	}

	@RequestMapping("/goExcelImport")
	public ModelAndView goExcelImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_excelImport")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goAddAccount")
	public ModelAndView goAddAccount(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_addAccount")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goUpdateAccount")
	public ModelAndView goUpdateAccount(HttpServletRequest request,
			HttpServletResponse response, LaborFeeAccount laborFeeAccount)
			throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_updateAccount")
				.addObject("laborFeeAccount", laborFeeAccount);
	}

	@RequestMapping("/goViewProcTrack")
	public ModelAndView goViewProcTrack(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_viewProcTrack")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_print")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goWfProcApply")
	public ModelAndView goWfProcApply(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("no") String no) {
		// 根据流程标识获取最后一个版本的流程定义
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionKey(key)
				.latestVersion().singleResult();

		String url = FileUploadUtils.DOWNLOAD_URL;

		// 获取对应的流程
		WfProcess wfProcess = this.wfProcInstService
				.getWfProcessByProcKey(processDefinition.getKey());
		String wfProcNo = "";
		if (wfProcess != null) {
			wfProcNo = wfProcess.getNo();
			wfProcess.setRemarks((wfProcess.getRemarks() == null ? ""
					: wfProcess.getRemarks()).replace("\r\n", "<br/>"));
		}

		LaborFee laborFee = new LaborFee();
		laborFee = laborFeeService.get(LaborFee.class, no);

		Employee employee = wfProcInstService.get(Employee.class, this
				.getUserContext(request).getEmnum());

		// 获取明细 根据类型合计
		List<Map<String, Object>> dataList = laborFeeService
				.getLaborFeeDetail(no);
		Map<String, Object> detailJson = new HashMap<String, Object>();
		Map<String, Object> detailMap = new HashMap<String, Object>();
		Map<String, Object> datas = new HashMap<String, Object>();

		detailMap.put("tWfCust_ConstructionExpenseClaim", 1);
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> laborFeeDtl = dataList.get(i);
				detailJson.put("fp__tWfCust_ConstructionExpenseClaimDtl__" + i
						+ "__DtlAmount", laborFeeDtl.get("Amount"));
				detailJson.put("fp__tWfCust_ConstructionExpenseClaimDtl__" + i
						+ "__Remarks", laborFeeDtl.get("Descr"));
			}

			detailMap.put("tWfCust_ConstructionExpenseClaimDtl",
					dataList.size());
		}

		// 初始化数据
		if (employee != null) {
			Department1 department1 = new Department1();
			department1 = employeeService.get(Department1.class,
					employee.getDepartment1());
			Department department = new Department();
			department = employeeService.get(Department.class,
					employee.getDepartment());
			detailJson.put("fp__tWfCust_ConstructionExpenseClaim__0__EmpCode",
					this.getUserContext(request).getCzybh());
			detailJson.put("fp__tWfCust_ConstructionExpenseClaim__0__EmpName",
					employee.getNameChi());

			if (department1 != null) {
				detailJson.put(
						"fp__tWfCust_ConstructionExpenseClaim__0__DeptCode",
						department1.getCode());
				detailJson.put(
						"fp__tWfCust_ConstructionExpenseClaim__0__DeptDescr",
						department1.getDesc2());

			}
			if (department != null) {
				Map<String, Object> cmpData = wfProcInstService
						.getEmpCompany(employee.getDepartment());
				if (cmpData != null) {
					employee.setCmpCode(cmpData.get("Code").toString());
					employee.setCmpDescr(cmpData.get("CmpDescr").toString());

					detailJson.put(
							"fp__tWfCust_ConstructionExpenseClaim__0__Company",
							employee.getCmpDescr());
				}
			}
			if (laborFee != null) {

				ItemType1 itemType1 = new ItemType1();
				itemType1 = laborFeeService.get(ItemType1.class,
						laborFee.getItemType1());
				detailJson.put(
						"fp__tWfCust_ConstructionExpenseClaim__0__ItemType1",
						itemType1.getDescr());
				detailJson.put(
						"fp__tWfCust_ConstructionExpenseClaim__0__Amount",
						laborFeeService.getAmountByNo(no));
				detailJson.put(
						"fp__tWfCust_ConstructionExpenseClaim__0__Reason",
						laborFee.getRemarks());
			}
			detailJson
					.put("fp__tWfCust_ConstructionExpenseClaim__0__RefNo", no);
			detailJson.put("fp__tWfCust_ConstructionExpenseClaim__0__IsCommon",
					"是");
		}

		String startMan = this.getUserContext(request).getCzybh();

		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");

		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
				.addObject("processDefinition", processDefinition)
				.addObject("processDefinitionKey", processDefinition.getId())
				.addObject("wfProcNo", wfProcNo)
				.addObject("applyPage", processDefinition.getKey() + ".jsp")
				.addObject("m_umState", "A").addObject("datas", detailJson)
				.addObject("detailJson", JSONObject.toJSONString(detailJson))
				.addObject("processInstanceId", "processInstanceId")
				.addObject("processInstanceId", "processInstanceId")
				.addObject("detailList", JSONObject.toJSONString(detailMap))
				.addObject("startMan", startMan)
				.addObject("wfProcess", wfProcess).addObject("url", url)
				.addObject("cmpcode", xtcs.getQz())
				.addObject("employee", employee)
				.addObject("activityId", "startevent");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理新增");
		try {
			laborFee.setM_umState("A");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doSaveLaborFee(laborFee);
			if (result.isSuccess()) {// 如果tWorkCard不存在ActName 保存
				if (!laborFeeService.isExistsWorkCard(laborFee.getActName())) {
					laborFeeService.doSaveWorkCard(laborFee.getActName(),
							laborFee.getCardID(), this.getUserContext(request)
									.getCzybh());
				}
			}
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理编辑");
		try {
			laborFee.setM_umState("M");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doUpdateLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doCheck")
	public void doCheck(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理编辑");
		try {
			laborFee.setM_umState("C");
			laborFee.setStatus("3");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doCheckLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doCheckBack")
	public void doCheckBack(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理审核退回");
		try {
			laborFee.setM_umState("C");
			laborFee.setStatus("2");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doCheckLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doCheckCancel")
	public void doCheckCancel(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理审核退回");
		try {
			laborFee.setM_umState("C");
			laborFee.setStatus("5");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doCheckLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	@RequestMapping("/doReCancel")
	public void doReCancel(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理反审核");
		try {
			laborFee.setM_umState("B");
			laborFee.setStatus("1");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doCheckLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	@RequestMapping("/doSignature")
	public void doSignature(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("人工费用管理审核退回");
		try {
			laborFee.setM_umState("W");
			laborFee.setStatus("4");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doCheckLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doUpdateDocumentNo")
	public void doUpdateDocumentNo(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		logger.debug("输入凭证");
		try {
			laborFee.setM_umState("P");
			laborFee.setLastUpdate(new Date());
			laborFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.laborFeeService.doCheckLaborFee(laborFee);

			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "人工费用新增失败");
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param custCode
	 * @param feeType
	 * @param sendNo
	 * @return
	 */
	@RequestMapping("/getPayDetail")
	@ResponseBody
	public String[] getPayDetail(HttpServletRequest request,
			HttpServletResponse response, String custCode, String feeType,
			String sendNo) {
		logger.debug("ajax获取数据");
		String sendNoHaveAmount = laborFeeService.getSendNoHaveAmount(custCode,
				feeType);
		String haveAmount = laborFeeService.getHaveAmount(sendNo, feeType);
		String[] str = { sendNoHaveAmount, haveAmount };

		return str;

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param feeType
	 * @return
	 */
	@RequestMapping("/getHaveAmount")
	@ResponseBody
	public String getHaveAmount(HttpServletRequest request,
			HttpServletResponse response, String sendNo, String feeType) {
		logger.debug("ajax获取数据");

		String notHaveAmount = laborFeeService.getHaveAmount(sendNo, feeType);

		return notHaveAmount;

	}

	@RequestMapping("/getNoHaveAmount")
	@ResponseBody
	public String getNoHaveAmount(HttpServletRequest request,
			HttpServletResponse response, String custCode, String feeType) {
		logger.debug("ajax获取数据");

		String haveAmount = laborFeeService.getSendNoHaveAmount(custCode,
				feeType);

		return haveAmount;

	}

	@RequestMapping("/getIsSetItem")
	@ResponseBody
	public boolean getIsSetItem(HttpServletRequest request,
			HttpServletResponse response, String no) {
		logger.debug("ajax获取数据");

		return laborFeeService.getIsSetItem(no);

	}

	@RequestMapping("/getFeeTypeDescr")
	@ResponseBody
	public String[] getFeeTypeDescr(HttpServletRequest request,
			HttpServletResponse response, String feeType) {
		logger.debug("ajax获取数据");

		String[] feeTypeDescr = { laborFeeService.getFeeTypeDescr(feeType),
				laborFeeService.isHaveSendNo(feeType) };

		return feeTypeDescr;

	}

	@RequestMapping("/doSaveWorkCard")
	@ResponseBody
	public void doSaveWorkCard(HttpServletRequest request,
			HttpServletResponse response, String actNameReal, String cardID) {
		logger.debug("ajax获取数据");

		this.laborFeeService.doSaveWorkCard(actNameReal, cardID, this
				.getUserContext(request).getCzybh());

	}

	/**
	 * excel加载数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc = this.getUserContext(request);
		Map<String, Object> map = new HashMap<String, Object>();
		ExcelImportUtils<LaborFeeDetailBean> eUtils = new ExcelImportUtils<LaborFeeDetailBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		String itemType1 = "";
		List<String> titleList = new ArrayList<String>();
		InputStream in = null;
		while (it.hasNext()) {
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();

			if ("file".equals(fieldName)) {
				in = obit.getInputStream();
			}
			if ("itemType1".equals(fieldName)) {
				itemType1 = fieldValue;
			}
		}
		titleList.add("客户编号");
		titleList.add("费用类型");
		titleList.add("送货单号");
		titleList.add("金额");
		titleList.add("户名");
		titleList.add("卡号");
		titleList.add("备注");
		try {
			List<LaborFeeDetailBean> result = eUtils.importExcel(in,
					LaborFeeDetailBean.class, titleList);
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			for (LaborFeeDetailBean laborFeeDetailBean : result) {
				Customer customer = null;
				ItemAppSend itemAppSend = null;
				Item item = new Item();
				Map<String, Object> data = new HashMap<String, Object>();
				String feeType = "";
				String isHaveSendNo = "";
				data.put("isinvalid", "1");
				data.put("isinvaliddescr", "有效");

				feeType = laborFeeService.getFeeTypeDescr(laborFeeDetailBean
						.getFeeType());
				isHaveSendNo = laborFeeService.isHaveSendNo(laborFeeDetailBean
						.getFeeType());
				if ("".equals(feeType)) {
					data.put("isinvalid", "0");
					data.put("isinvaliddescr", "无效，费用类型有误");
				} else {
					data.put("feetypedescr", feeType);
				}
				data.put("lastupdatedby", uc.getCzybh());
				data.put("lastupdate", new Date());
				data.put("remarks", laborFeeDetailBean.getRemarks());

				if (!laborFeeService.getIsExistsFeeType(itemType1,
						laborFeeDetailBean.getFeeType())) {
					data.put("isinvalid", "0");
					data.put("isinvaliddescr", "无效，费用类型和材料类型不匹配");
				}
				data.put("feetype", laborFeeDetailBean.getFeeType());
				data.put("expired", "F");
				data.put("actionlog", "ADD");
				data.put("amount", laborFeeDetailBean.getAmount());

				customer = customerService.get(Customer.class,
						laborFeeDetailBean.getCustCode());
				if (customer == null) {
					data.put("isinvalid", "0");
					data.put("isinvaliddescr", "无效，客户编号无效");
				} else {
					data.put("address", customer.getAddress());
					data.put("documentno", customer.getDocumentNo());
					data.put(
							"checkstatusdescr",
							xtdmService.getByIdAndCbm("CheckStatus",
									customer.getCheckStatus()).getNote());
					data.put("custcheckdate", customer.getCheckOutDate());
				}
				data.put("custcode", laborFeeDetailBean.getCustCode());
				data.put("actname", laborFeeDetailBean.getActName());
				data.put("cardid", laborFeeDetailBean.getCardID());

				itemAppSend = itemAppSendService.get(ItemAppSend.class,
						laborFeeDetailBean.getAppSendNo());
				data.put("appsendno", laborFeeDetailBean.getAppSendNo());
				if (itemAppSend != null) {
					if (StringUtils.isNotBlank(itemAppSend.getIaNo())) {
						data.put("iano", itemAppSend.getIaNo());
					}
				} else {
					if ("1".equals(isHaveSendNo)) {
						data.put("isinvalid", "0");
						data.put("isinvaliddescr", "无效，送货单号无效");
					}
				}
				data.put("hadamount", laborFeeService.getHaveAmount(
						laborFeeDetailBean.getAppSendNo(),
						laborFeeDetailBean.getFeeType()));
				data.put("sendnohaveamount", laborFeeService
						.getSendNoHaveAmount(laborFeeDetailBean.getCustCode(),
								laborFeeDetailBean.getFeeType()));

				if (!"合计".equals(laborFeeDetailBean.getCustCode())) {// 导出模板有合计
					datas.add(data);
				}
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "导入失败，请检查是否缺少列：客户编号、费用类型、送货单号、金额、户名、卡号、备注");
			map.put("hasInvalid", true);
			return map;
		}
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		laborFeeService.findPageBySql(page, laborFee);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"人工费用管理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	@RequestMapping("/doLaborDetailExcel")
	public void doLaborDetailExcel(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		laborFeeService.findLaborDetailPageBySql(page, laborFee);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"人工费用管理明细_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	@RequestMapping("/doItemReqExcel")
	public void doItemReqExcel(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		laborFeeService.findItemReqBySql(page, itemAppSend);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"材料需求明细表_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	@RequestMapping("/doSendDetailExcel")
	public void doSendDetailExcel(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		laborFeeService.findItemAppSendDetailPageBySql(page, itemAppSend);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"发货明细表_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	@RequestMapping("/sendFeeImport")
	public ModelAndView sendFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {

		return new ModelAndView("admin/finance/laborFee/laborFee_sendFeeImport")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goIntFeeImport")
	public ModelAndView goIntFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		laborFee.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(
				new Date(), -1)));
		laborFee.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),
				-1)));
		Xtcs cupInsCalTyp = laborFeeService.get(Xtcs.class, "CupInsCalTyp");
		Xtcs intInsCalTyp = laborFeeService.get(Xtcs.class, "IntInsCalTyp");
		
		return new ModelAndView("admin/finance/laborFee/laborFee_intFeeImport")
				.addObject("laborFee", laborFee)
				.addObject("cupInsCalTyp", cupInsCalTyp.getQz())
				.addObject("intInsCalTyp", intInsCalTyp.getQz());
	}

	@RequestMapping("/goInStallFeeImport")
	public ModelAndView goInStallFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		laborFee.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(
				new Date(), -1)));
		laborFee.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),
				-1)));

		return new ModelAndView(
				"admin/finance/laborFee/laborFee_installFeeImport").addObject(
				"laborFee", laborFee);
	}

	/**
	 * 导入浴室柜安装费
	 * 
	 * @author created by zb
	 * @date 2019-6-25--下午4:41:48
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 */
	@RequestMapping("/goBathFeeImport")
	public ModelAndView goBathFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		// laborFee.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(new
		// Date(), -1)));
		// laborFee.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new
		// Date(), -1)));
		Xtcs xtcs = laborFeeService.get(Xtcs.class, "toilet");
		Xtcs xtcs2 = laborFeeService.get(Xtcs.class, "cabinet");
		return new ModelAndView("admin/finance/laborFee/laborFee_bathFeeImport")
				.addObject("laborFee", laborFee)
				.addObject("toilet", xtcs.getQz())
				.addObject("cabinet", xtcs2.getQz())
				.addObject("isHaveSendNo",laborFeeService.isHaveSendNo("YSGAZF"));
	}

	@RequestMapping("/goTransImport")
	public ModelAndView goTransFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		laborFee.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(
				new Date(), -1)));
		laborFee.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),
				-1)));

		return new ModelAndView(
				"admin/finance/laborFee/laborFee_transFeeImport").addObject(
				"laborFee", laborFee);
	}

	/**
	 * 查询SendFeeJqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goSendFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findSendFeePageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/getIntFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getIntFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findIntFeePageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/getCupFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getCupFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findCupFeePageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 浴室柜查询
	 * 
	 * @author created by zb
	 * @date 2019-6-25--下午5:21:01
	 * @param request
	 * @param response
	 * @param laborFee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBathFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getBathFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findBathFeePageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goTransFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goTransFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.goTransFeeJqGrid(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goPreFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goPreFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.goPreFeeJqGrid(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goCheckFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goCheckFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.goCheckFeeJqGrid(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@ResponseBody
	@RequestMapping("/getTransFeeList")
	public List<Map<String, Object>> getRegImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		List<Map<String, Object>> list = laborFeeService
				.getTransFeeList(laborFee);
		return list;
	}

	@RequestMapping("/goWhInstallFeeImport")
	public ModelAndView goWhInstallFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		laborFee.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(
				new Date(), -1)));
		laborFee.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),
				-1)));

		return new ModelAndView(
				"admin/finance/laborFee/laborFee_whInstallFeeImport")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goWhInstallFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goWhInstallFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		Xtcs xtcsToilet = laborFeeService.get(Xtcs.class, "toilet");
		Xtcs xtcsCabinet = laborFeeService.get(Xtcs.class, "cabinet");
		Xtcs xtcsBathSet = laborFeeService.get(Xtcs.class, "bathset");
		laborFee.setToilet(xtcsToilet.getQz());
		laborFee.setCabinet(xtcsCabinet.getQz());
		laborFee.setBathSet(xtcsBathSet.getQz());
		laborFeeService.goWhInstallFeeJqGrid(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goViewIntQty")
	public ModelAndView goViewIntQty(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {

		return new ModelAndView("admin/finance/laborFee/laborFee_viewIntQty")
				.addObject("laborFee", laborFee);
	}

	@RequestMapping("/goIntQtyJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goIntQtyJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findIntQtyPageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goTileCutFeeImport")
	public ModelAndView goTileCutFeeImport(HttpServletRequest request,
			HttpServletResponse response, LaborFee laborFee) {
		laborFee.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(
				new Date(), -1)));
		laborFee.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),
				-1)));

		return new ModelAndView(
				"admin/finance/laborFee/laborFee_tileCutFeeImport").addObject(
				"laborFee", laborFee);
	}

	@RequestMapping("/goTileCutFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goTileCutFeeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		if (StringUtils.isNotBlank(laborFee.getPks())
				&& ",".equals(laborFee.getPks().charAt(
						laborFee.getPks().length() - 1)
						+ "")) {
			laborFee.setPks(laborFee.getPks().substring(0,
					laborFee.getPks().length() - 1));
		}
		laborFeeService.goTileCutFeeJqGrid(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goViewCutDetail")
	public ModelAndView goViewCutDetail(HttpServletRequest request,
			HttpServletResponse response, String cutCheckOutNo, String iano)
			throws Exception {

		return new ModelAndView("admin/finance/laborFee/laborFee_viewCutDetail")
				.addObject("cutCheckOutNo", cutCheckOutNo).addObject("iano",
						iano);
	}

	@RequestMapping("/getGoodPrjJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getGoodPrjJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			LaborFee laborFee) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		laborFeeService.findGoodPrjPageBySql(page, laborFee);
		return new WebPage<Map<String, Object>>(page);
	}
}
