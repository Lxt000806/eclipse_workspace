package com.house.home.web.controller.design;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustLog;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.ItemChg;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustSceneDesiService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;

@Controller
@RequestMapping("/admin/custSceneDesi")
public class CustSceneDesiController extends BaseController{
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustSceneDesiService custSceneDesiService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Customer customer) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		if(customer.getIsSchemeDesigner()==null){
			customer.setIsSchemeDesigner("0");
		}
		/*customer.setDateFrom(request.getParameter("dateFrom")==""?null:DateFormatString(request.getParameter("dateFrom")));
		customer.setDateTo(request.getParameter("dateTo")==""?null:DateFormatString(request.getParameter("dateTo")));
		customer.setSignDateFrom(request.getParameter("signDateFrom")==""?null:DateFormatString(request.getParameter("signDateFrom")));
		customer.setSignDateTo(request.getParameter("signDateTo")==""?null:DateFormatString(request.getParameter("signDateTo")));*/
		custSceneDesiService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isAddCustType=custSceneDesiService.getIsAddCustType();
		Map<String , Object> map =custSceneDesiService.getSceneDesiDepartment();
		String department1="",department2="";
		if(map!=null){
			department1=(String)map.get("Department1");
			department2=(String)map.get("Department2");
		}
		return new ModelAndView("admin/design/custSceneDesi/custSceneDesi_list")
		.addObject("isAddCustType",isAddCustType)
		.addObject("department1",department1)
		.addObject("department2",department2);
	}
	
	@RequestMapping("/goArr")
	public ModelAndView goArr(HttpServletRequest request,HttpServletResponse response,
			Customer customer) throws Exception {
		logger.debug("现场设计师安排");
		Customer ct=customerService.get(Customer.class, customer.getCode());
		Employee designer=null;
		Employee sceneDesign=null;
		if(!"".equals(custSceneDesiService.getDesignerCode(customer.getCode()))){
			designer=employeeService.get(Employee.class, custSceneDesiService.getDesignerCode(customer.getCode()));
		}
		if(!"".equals(custSceneDesiService.getSceneDesignerCode(customer.getCode()))){
			sceneDesign=employeeService.get(Employee.class, custSceneDesiService.getSceneDesignerCode(customer.getCode()));
		}
		ct.setDesignMan(designer==null?"":designer.getNumber());
		ct.setDesignManDescr(designer==null?"":designer.getNameChi());
		ct.setCustSceneDesi(sceneDesign==null?"":sceneDesign.getNumber());
		ct.setCustSceneDesiDescr(sceneDesign==null?"":sceneDesign.getNameChi());
		Map<String , Object> map =custSceneDesiService.getSceneDesiDepartment();
		String department1="",department2="";
		if(map!=null){
			department1=(String)map.get("Department1");
			department2=(String)map.get("Department2");
		}
		
		return new ModelAndView("admin/design/custSceneDesi/custSceneDesi_arr")
			.addObject("customer", ct)
			.addObject("department1",department1)
			.addObject("department2",department2).addObject("designManPhone",designer==null?"":designer.getPhone());
	
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,
			Customer customer) throws Exception {
		logger.debug("现场设计师安排查看");
		Customer ct=customerService.get(Customer.class, customer.getCode());
		Employee designer=null;
		Employee sceneDesign=null;
		if(!"".equals(custSceneDesiService.getDesignerCode(customer.getCode()))){
			designer=employeeService.get(Employee.class, custSceneDesiService.getDesignerCode(customer.getCode()));
		}
		if(!"".equals(custSceneDesiService.getSceneDesignerCode(customer.getCode()))){
			sceneDesign=employeeService.get(Employee.class, custSceneDesiService.getSceneDesignerCode(customer.getCode()));
		}
		ct.setDesignMan(designer==null?"":designer.getNumber());
		ct.setDesignManDescr(designer==null?"":designer.getNameChi());
		ct.setCustSceneDesi(sceneDesign==null?"":sceneDesign.getNumber());
		ct.setCustSceneDesiDescr(sceneDesign==null?"":sceneDesign.getNameChi());
		
		return new ModelAndView("admin/design/custSceneDesi/custSceneDesi_view").addObject("customer", ct)
				.addObject("designManPhone",designer==null?"":designer.getPhone());
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response ,String code ,String custSceneDesi){
		logger.debug("新增现场设计师开始");
		try{
			if(!"".equals(custSceneDesiService.getSceneDesignerCode(code))
					&&StringUtils.isNotBlank(custSceneDesi)
					&&!custSceneDesi.equals(custSceneDesiService.getSceneDesignerCode(code))){
				custSceneDesiService.doUpdateSceneDesi(code,custSceneDesi,this.getUserContext(request).getCzybh(),custSceneDesiService.getSceneDesignerCode(code));
			}else if("".equals(custSceneDesiService.getSceneDesignerCode(code))&&StringUtils.isNotBlank(custSceneDesi)){
				custSceneDesiService.doSaveSceneDesi(code,custSceneDesi,this.getUserContext(request).getCzybh());
			}else if(!"".equals(custSceneDesiService.getSceneDesignerCode(code))&&StringUtils.isBlank(custSceneDesi)){
				custSceneDesiService.doDeleteSceneDesi(code,custSceneDesiService.getSceneDesignerCode(code),this.getUserContext(request).getCzybh());
			}

			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改现场设计师失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		/*customer.setDateFrom(request.getParameter("dateFrom")==""?null:DateFormatString(request.getParameter("dateFrom")));
		customer.setDateTo(request.getParameter("dateTo")==""?null:DateFormatString(request.getParameter("dateTo")));
		customer.setSignDateFrom(request.getParameter("signDateFrom")==""?null:DateFormatString(request.getParameter("signDateFrom")));
		customer.setSignDateTo(request.getParameter("signDateTo")==""?null:DateFormatString(request.getParameter("signDateTo")));*/
		custSceneDesiService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"现场设计师安排表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public  Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
        return date;
	}
	
}
