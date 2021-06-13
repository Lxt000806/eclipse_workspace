package com.house.home.web.controller.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustIntProg;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustIntProgService;
import com.house.home.service.project.CustItemConfirmService;
import com.house.home.service.project.CustWorkerService;
import com.house.home.service.query.PrjDelayAnalyService;

@Controller
@RequestMapping("/admin/prjDelayAnaly")
public class PrjDelayAnalyController extends BaseController {
	@Autowired
	private PrjDelayAnalyService prjDelayAnalyService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustItemConfirmService custItemConfirmService;
	@Autowired
	private CustPayService custPayService;
	@Autowired
	private CustWorkerService custWorkerService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustIntProgService custIntProgService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDelayAnalyService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		return new ModelAndView("admin/query/prjDelayAnaly/prjDelayAnaly_list")
				.addObject("customer", customer);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		PrjProgTemp prjProgTemp=null;
		Integer delayDays= prjDelayAnalyService.getDelayDays(customer.getCode());
		customer = prjDelayAnalyService.get(Customer.class, customer.getCode());
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
			prjProgTemp=prjDelayAnalyService.get(PrjProgTemp.class,customer.getPrjProgTempNo());
			customer.setPrjProgTempNoDescr(prjProgTemp.getDescr());
		}
		String custTypeDescr="";
		if(StringUtils.isNotBlank(customer.getCustType())){
			custTypeDescr = customerService.get(CustType.class, customer.getCustType()).getDesc1();
		}
		String buildStatusDescr="";
		buildStatusDescr=prjDelayAnalyService.getMoreInfo(customer.getCode()).get("buildstatusdescr")+"";
		String planEnd="";
		Date planEndDate=null;
		if(customer.getConfirmBegin()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar =new GregorianCalendar(); 
			calendar.setTime(customer.getConfirmBegin());
			calendar.add(Calendar.DATE,customer.getConstructDay());
			planEndDate = calendar.getTime();
			planEnd=sdf.format(planEndDate);
		}
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(customer.getCode());
		Map<String,Object> balanceMap=customerService.getShouldBanlance(customer.getCode());
		customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(customer.getCode()));
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(customer.getCode()));
		
		// 查询客户集成进度信息
		CustIntProg custIntProg = new CustIntProg();
		custIntProg.setCustCode(customer.getCode());
		List<Map<String, Object>> custIntProgInfos =
		        custIntProgService.findPageBySql(new Page<Map<String, Object>>(), custIntProg).getResult();
		
		Map<String, Object> custIntProgInfo = new HashMap<String, Object>();
		if (custIntProgInfos != null && custIntProgInfos.size() > 0) {
            custIntProgInfo = custIntProgInfos.get(0);
        }
		
		return new ModelAndView("admin/query/prjDelayAnaly/prjDelayAnaly_view")
			.addObject("customer", customer)
			.addObject("customerPayMap", customerPayMap)
			.addObject("balanceMap", balanceMap)
			.addObject("planEnd",planEnd)
			.addObject("delayDays",delayDays)
			.addObject("custTypeDescr",custTypeDescr)
			.addObject("buildStatusDescr", buildStatusDescr)
			.addObject("custIntProg", custIntProgInfo);
	}
	/**
	 * 查询工种施工进度（工程进度拖延分析用）
	 * @author	created by zb
	 * @date	2019-11-8--下午6:13:02
	 * @param request
	 * @param response
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_sgjd")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_sgjd(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		custWorkerService.findWorkDaysPageByCustCode(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询客户材料选定信息（工地进度拖延分析查看用）
	 * @author	created by zb
	 * @date	2019-11-8--下午2:50:11
	 * @param request
	 * @param response
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustConfirmResultJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCustConfirmResultJqGrid(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfirmService.findCustConfirmResultPageBySql(page,custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 客户增减信息按照时间排序（工程进度拖延分析查看用）
	 * @author	created by zb
	 * @date	2019-11-8--下午3:58:38
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goChgInfoOrderDateJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getChgInfoOrderDateJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findChgInfoOrderDatePageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response){
		String jsonString = request.getParameter("jsonString");
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString);
		// 表格数据
		String colModel = (String) jo.get("colModel");
		String rows = (String) jo.get("rows");
		net.sf.json.JSONArray arryRows = net.sf.json.JSONArray.fromObject(rows);
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		//数据
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>(); 
		//列名
		List<String> titleList = new ArrayList<String>();
		List<String> columnList = new ArrayList<String>();
		//统计
		List<String> sumList = new ArrayList<String>();
		boolean hasSum = false;
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = (String) jsonObject.get("name");
            Boolean hidden = jsonObject.get("hidden")==null?false:(Boolean)jsonObject.get("hidden");
            Boolean sum = (Boolean) jsonObject.get("sum");
            //np：true则不打印
            Boolean np = (Boolean) (jsonObject.get("np")==null?false:jsonObject.get("np"));
            if (!"rn".equals(name) && !"cb".equals(name)&&!np){
            	if (hidden.booleanValue()==false){
            		columnList.add(name);
            		titleList.add(jsonObject.get("label").toString().replace("</br>", ""));//去掉 换行做列名
            		if (sum!=null && sum==true){
            			sumList.add(name);
            			hasSum = true;
            		}else{
            			sumList.add("");
            		}
            	}
            }
        } 
		if (hasSum==false){
			sumList = new ArrayList<String>();
		}
		for (int i = 0; i < arryRows.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryRows.getJSONObject(i); 
            Map<String, Object> map = new HashMap<String, Object>(); 
            int j=1;
            for (Iterator<?> iter = (Iterator<?>) jsonObject.keys(); iter.hasNext();) { 
            	net.sf.json.JSONObject jsonObject1 = arryCols.getJSONObject(j);
 	            String align = (String) jsonObject1.get("align");
 	            String key = (String) iter.next(); 
                String value = jsonObject.get(key).toString(); 
                if (columnList.indexOf(key)!=-1){
                	if("right".equals(align)){
                		try {
                			String st = Arith.strNumToStr(value);
                			map.put(key, Double.parseDouble(st));
						} catch (Exception e) {
	                		map.put(key, value);
						}
                	}else{
                		map.put(key, value);
                	}
                }
                j++;
            } 
            rsList.add(map); 
        }  
		ServletUtils.flushExcelOutputStream(request, response, rsList,
				"工程进度拖延分析"+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/go_tab")
	public ModelAndView go_tab(HttpServletRequest request,
			HttpServletResponse response, String id, String tabId) throws Exception {
		Customer customer = customerService.get(Customer.class, id);
		resetCustomer(customer);
		UserContext uc = this.getUserContext(request);
		String costRight = uc.getCostRight();
		String itemRight = uc.getItemRight();
		return new ModelAndView("admin/query/prjDelayAnaly/tab_lld")
		.addObject("customer", customer)
		.addObject("costRight", costRight)
		.addObject("itemRight", itemRight);

	}
	
	/**设置code对应的名称
	 * @param customer
	 */
	private void resetCustomer(Customer customer){
		if (customer!=null){
			if (StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = builderService.get(Builder.class, customer.getBuilderCode());
				if (builder!=null){
					customer.setBuilderCodeDescr(builder.getDescr());
				}
			}
			if (StringUtils.isNotBlank(customer.getDesignMan())){
				Employee employee = employeeService.get(Employee.class, customer.getDesignMan());
				if (employee!=null){
					customer.setDesignManDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(customer.getBusinessMan())){
				Employee employee = employeeService.get(Employee.class, customer.getBusinessMan());
				if (employee!=null){
					customer.setBusinessManDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(customer.getAgainMan())){
				Employee employee = employeeService.get(Employee.class, customer.getAgainMan());
				if (employee!=null){
					customer.setAgainManDescr(employee.getNameChi());
				}
			}else{
				customer.setAgainMan("");
			}
		}
	}
}
