package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.ItemReqService;
import com.house.home.service.project.BaseItemChgService;
import com.house.home.service.project.ItemChgService;

@Controller
@RequestMapping("/admin/gcwg")
public class GcwgController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GcwgController.class);

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemReqService itemReqService;
	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private ItemChgService itemChgService;
	@Autowired
	private BaseItemChgService baseItemChgService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private EmployeeService employeeService;

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
			HttpServletResponse response, Customer customer) throws Exception {
		if (StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("4,5");
		}
		if (StringUtils.isBlank(customer.getCheckStatus())){
			customer.setCheckStatus("1,2,3,4");
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_gcwg(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Customer列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		if (StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("4,5");
		}
		if (StringUtils.isBlank(customer.getCheckStatus())){
			customer.setCheckStatus("1,2,3,4");
		}
		String companyName = xtcsService.getQzById("CmpnyName");
		return new ModelAndView("admin/project/gcwg/gcwg_list")
			.addObject("customer", customer)
			.addObject("companyName", companyName);
	}
	/**
	 * 跳转到完工信息录入页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到完工信息录入页面");
		Customer customer = new Customer();
		Map<String,Object> map = customerService.getCustomerByCode_gcwg(id);
		BeanConvertUtil.mapToBean(map, customer);
		customerService.getChgData(customer);
		
		return new ModelAndView("admin/project/gcwg/gcwg_update")
			.addObject("customer", customer);
	}
	/**
	 * 跳转到工程完工结转页面
	 * @return
	 */
	@RequestMapping("/goFinish")
	public ModelAndView goFinish(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到工程完工结转页面");
		Customer customer = customerService.get(Customer.class, id);
		customer.setEndCode("3");
		customer.setEndDate(new Date());
		String hintString = itemReqService.getHintString(id);
		
		return new ModelAndView("admin/project/gcwg/gcwg_finish")
			.addObject("customer", customer)
			.addObject("hintString", hintString);
	}
	/**
	 * 工程完工保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doFinish")
	public void doFinish(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("工程完工");
		try{
			if ("3".equals(customer.getEndCode())){
				int i = itemReqService.getCountNum(customer.getCode());
				if (i>0){
					ServletUtils.outPrintSuccess(request, response, "进入工程完工确认");
					return;
				}
			}
			if ("4".equals(customer.getEndCode())){
				double d = itemAppService.getTotalQty(customer.getCode());
				if (d!=0){
					ServletUtils.outPrintFail(request, response, "该楼盘已发过材料！");
					return;
				}
			}
			customer.setM_umState("J");
			customer.setFromStatus("4");
			customer.setToStatus("5");
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = customerService.doGcwg_jz(customer);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程完工失败");
		}
	}
	@RequestMapping("/goFinishConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFinishConfirmJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReqService.findPageBySql_gcwg(page, customer.getCode());
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到工程完工确认页面
	 * @return
	 */
	@RequestMapping("/goFinishConfirm")
	public ModelAndView goFinishConfirm(HttpServletRequest request, HttpServletResponse response, 
			Customer customer){
		logger.debug("跳转到工程完工确认页面");
		
		return new ModelAndView("admin/project/gcwg/gcwg_confirm")
			.addObject("customer", customer);
	}
	/**
	 * 工程完工确认
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doFinishConfirm")
	public void doFinishConfirm(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("工程完工确认");
		try{
			customer.setM_umState("J");
			customer.setFromStatus("4");
			customer.setToStatus("5");
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = customerService.doGcwg_jz(customer);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程完工失败");
		}
	}
	/**
	 * 工程完工回退
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doFinishReturn")
	public void doFinishReturn(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("工程完工回退");
		try{
			customer.setM_umState("B");
			customer.setFromStatus("5");
			customer.setToStatus("4");
			customer.setEndCode("");
			customer.setEndDate(new Date());
			customer.setEndRemark("");
			customer.setRealDesignFee(0D);
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = customerService.doGcwg_jz(customer);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程完工回退失败");
		}
	}
	/**
	 * 工程完工before客户结算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/beforeKhjs")
	public void beforeKhjs(HttpServletRequest request, HttpServletResponse response, String code){
		logger.debug("工程完工客户结算");
		try{
			int i = itemChgService.getCountByCustCode(code);
			if (i>0){
				ServletUtils.outPrintFail(request, response, "客户存在申请状态的材料增减单，不能进行结算操作！");
			}
			int j = baseItemChgService.getCountByCustCode(code);
			if (j>0){
				ServletUtils.outPrintFail(request, response, "客户存在申请状态的基础增减单，不能进行结算操作！");
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "客户结算失败");
		}
	}
	/**
	 * 工程完工客户结算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doKhjs")
	public void doKhjs(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("工程完工客户结算");
		try{
			customer.setM_umState("J");
			Result result = customerService.doGcwg_Khjs(customer);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "客户结算失败");
		}
	}
	
	/**
	 * 工程完工客户结算
	 * @return
	 */
	@RequestMapping("/goKhjs")
	public ModelAndView goKhjs(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("跳转到工程完工客户结算页面");
		
		Customer customer = customerService.get(Customer.class, id);
		customer.setCustCheckDate(new Date());
		// 修复leader为null时会报NullPointerException异常Bug
		// 张海洋 20200604
		String leaderNumber = "",
				leaderName = "",
				department1 = "",
				department2 = "";
		
		Employee leader = new Employee();
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			leader = employeeService.getDepLeaderByEmpNum(customer.getProjectMan());
			Employee projectMan = customerService.get(Employee.class, customer.getProjectMan());
			department1=projectMan.getDepartment1();
			department2=projectMan.getDepartment2();
		}
		
		
		if (leader != null) {
            leaderNumber = leader.getNumber();
            leaderName = leader.getNameChi();
        }

		return new ModelAndView("admin/project/gcwg/gcwg_khjs")
				.addObject("customer", customer)
				.addObject("leaderNumber", leaderNumber)
				.addObject("leaderDescr", leaderName)
				.addObject("dept1", department1)
				.addObject("dept2", department2);
	}
	
	/**
	 * 工程完工客户结算退回
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doKhjsReturn")
	public void doKhjsReturn(HttpServletRequest request, HttpServletResponse response, String code){
		logger.debug("工程完工客户结算退回");
		try{
			Customer customer = customerService.get(Customer.class, code);
			if (!"2".equals(customer.getCheckStatus().trim())){
				ServletUtils.outPrintFail(request, response, "结算状态已改变，结算回退失败，请重新检索！");
				return;
			}
			customer.setCheckStatus("1");
			customer.setCustCheckDate(null);
			customer.setCheckDocumentNo("");
			customer.setM_umState("R");
			Result result = customerService.doGcwg_Khjs(customer);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "结算退回失败");
		}
	}
	/**
	 * 跳转到查看Customer页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看完工信息页面");
		Customer customer = new Customer();
		Map<String,Object> map = customerService.getCustomerByCode_gcwg(id);
		BeanConvertUtil.mapToBean(map, customer);
		customerService.getChgData(customer);
		
		return new ModelAndView("admin/project/gcwg/gcwg_update")
			.addObject("customer", customer)
			.addObject("operType", "V");
	}
	
	/**
	 * 跳转到结算单打印页面
	 * @return
	 */
	@RequestMapping("/goJsdPrint")
	public ModelAndView goJsdPrint(HttpServletRequest request, HttpServletResponse response, 
			String custCode){
		logger.debug("跳转到结算单打印页面");
		
		return new ModelAndView("admin/project/gcwg/gcwg_jsd")
			.addObject("custCode", custCode);
	}
	
	/**
	 * 跳转到记账打印页面
	 * @return
	 */
	@RequestMapping("/beforeJzdPrint")
	public void beforeJzdPrint(HttpServletRequest request, HttpServletResponse response, 
			String custCode){
		logger.debug("跳转到记账打印页面");
		boolean result = itemChgService.hasOpenRecord(custCode);
		if (result){
			ServletUtils.outPrintSuccess(request, response);
		}else{
			ServletUtils.outPrintFail(request, response, "失败");
		}
	}
	
	/**
	 * 完工信息录入保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("修改Customer开始");
		try{
			Customer cust = customerService.get(Customer.class, customer.getCode());
			cust.setMaterialFee(customer.getMaterialFee());
			cust.setSpecialDisc(customer.getSpecialDisc());
			cust.setEndRemark(customer.getEndRemark());
			cust.setLastUpdate(new Date());
			cust.setLastUpdatedBy(getUserContext(request).getCzybh());
			cust.setActionLog("EDIT");
			this.customerService.update(cust);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Customer失败");
		}
	}
	
	/**
	 * 删除Customer
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Customer开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Customer编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Customer customer = customerService.get(Customer.class, deleteId);
				if(customer == null)
					continue;
				customer.setExpired("T");
				customerService.update(customer);
			}
		}
		logger.debug("删除Customer IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Customer导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		customerService.findPageBySql_gcwg(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工程完工_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 强制结算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doQzjs")
	public void doQzjs(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("修改Customer开始");
		try{
			this.customerService.doQzjs(customer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Customer失败");
		}
	}
	/**
	 * 强制结算退回
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doQzjsth")
	public void doQzjsth(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("修改Customer开始");
		try{
			this.customerService.doQzjsth(customer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Customer失败");
		}
	}
}
