package com.house.home.web.controller.design;

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
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustConService;
import com.house.home.service.design.CustomerService;

@Controller
@RequestMapping("/admin/custCon")
public class CustConController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustConController.class);

	@Autowired
	private CustConService custConService;
	@Autowired
	private CustomerService customerService;
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
			HttpServletResponse response, CustCon custCon) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		custConService.findPageBySql(page,custCon,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getBsJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustCon custCon) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		custConService.findPageBySql_bs(page,custCon,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustCon列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date dateTo = new Date();
		Date dateFrom = DateUtil.addDate(dateTo, -6);
		return new ModelAndView("admin/design/custCon/custCon_list")
		.addObject("dateFrom", dateFrom).addObject("dateTo", dateTo);
	}
	/**
	 * CustCon查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custCon/custCon_code");
	}
	/**
	 * 跳转到新增CustCon页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustCon页面");
		CustCon custCon = null;
		if (StringUtils.isNotBlank(id)){
			custCon = custConService.get(CustCon.class, Integer.parseInt(id));
			resetCustCon(custCon);
			custCon.setPk(null);
		}else{
			custCon = new CustCon();
		}
		Xtcs xtcs= custConService.get(Xtcs.class,"ConRemMinLen");
		custCon.setConDate(new Date());
		custCon.setConMan(getUserContext(request).getCzybh());
		custCon.setConManDescr(custConService.get(Employee.class, custCon.getConMan()).getNameChi());
		
		return new ModelAndView("admin/design/custCon/custCon_save")
			.addObject("custCon", custCon).addObject("minLength", xtcs.getQz());
	}
	/**
	 * 补全客户跟踪信息
	 * @param custCon
	 */
	private void resetCustCon(CustCon custCon) {
		if(StringUtils.isNotBlank(custCon.getCustCode())){
			Customer customer=customerService.get(Customer.class, custCon.getCustCode());
			custCon.setCustAddress(customer.getAddress());
			custCon.setDescr(customer.getDescr());
			custCon.setCustType(customer.getCustType());
			if (StringUtils.isNotBlank(customer.getDesignMan())){
				Employee employee = employeeService.get(Employee.class, customer.getDesignMan());
				if (employee!=null){
					custCon.setDesignManDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(customer.getBusinessMan())){
				Employee employee = employeeService.get(Employee.class, customer.getBusinessMan());
				if (employee!=null){
					custCon.setBusinessManDescr(employee.getNameChi());
				}
			}
		}
		if (StringUtils.isNotBlank(custCon.getConMan())){
			Employee employee = employeeService.get(Employee.class, custCon.getConMan());
			if (employee!=null){
				custCon.setConManDescr(employee.getNameChi());
			}
		}
		if(StringUtils.isNotBlank(custCon.getResrCustCode())){
			ResrCust resrCust=employeeService.get(ResrCust.class, custCon.getResrCustCode());
			if(resrCust!=null){
				custCon.setResrAddress(resrCust.getAddress());
			}
		}
	}
	/**
	 * 跳转到修改CustCon页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustCon页面");
		CustCon custCon = null;
		if (StringUtils.isNotBlank(id)){
			custCon = custConService.get(CustCon.class, Integer.parseInt(id));
		}else{
			custCon = new CustCon();
		}
		resetCustCon(custCon);
		Xtcs xtcs= custConService.get(Xtcs.class,"ConRemMinLen");
		return new ModelAndView("admin/design/custCon/custCon_update")
			.addObject("custCon", custCon).addObject("minLength", xtcs.getQz());
	}
	
	/**
	 * 跳转到查看CustCon页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustCon页面");
		CustCon custCon = custConService.get(CustCon.class, Integer.parseInt(id));
		resetCustCon(custCon);
		return new ModelAndView("admin/design/custCon/custCon_detail")
				.addObject("custCon", custCon);
	}
	/**
	 * 添加CustCon
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustCon custCon){
		logger.debug("添加CustCon开始");
		try{
			//默认跟踪类型为 接触记录
			custCon.setType("3");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			this.custConService.save(custCon);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustCon失败");
		}
	}
	
	/**
	 * 修改CustCon
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustCon custCon){
		logger.debug("修改CustCon开始");
		try{
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCon.setActionLog("EDIT");
			custConService.update(custCon);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustCon失败");
		}
	}
	
	/**
	 * 删除CustCon
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustCon开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustCon编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustCon custCon = custConService.get(CustCon.class, Integer.parseInt(deleteId));
				if(custCon == null)
					continue;
				custCon.setExpired("T");
				custConService.update(custCon);
			}
		}
		logger.debug("删除CustCon IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CustCon导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustCon custCon){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		custConService.findPageBySql_bs(page, custCon,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"客户跟踪_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
