package com.house.home.web.controller.prjMsg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.basic.prjMsgService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.query.PrjDelayAnalyService;

@Controller
@RequestMapping("/admin/prjMsg")
public class prjMsgController extends BaseController { 	
	
	@Autowired
	private prjMsgService prjJobMessageService;
	
	@Autowired
	private PersonMessageService personMessageService;
	
	@Autowired
    private CustomerService employeeService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CzybmService czybmService;
	
	@Autowired
	private PrjDelayAnalyService prjDelayAnalyService;
	/**
	 * itemSetService列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * 新增跳转itemSet表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, PersonMessage personMessage) throws Exception {
		Date d = new Date(); 
		personMessage.setSendDate(d);
		return new ModelAndView("admin/project/prjMsg/prjMsg_list").addObject("personMessage", personMessage).
				addObject("czybh", this.getUserContext(request).getCzybh());
		
	}
	/*this.getUserContext(request).getCzybh()   调用权限*/
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,PersonMessage personMessage) throws Exception {		
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		boolean hasSelfRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0332", "查看本人");
		boolean hasDeptRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0332", "查看本部门");
		boolean hasAllRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0332", "查看所有");
		prjJobMessageService.findPageBySql(page, personMessage,hasSelfRight,hasDeptRight,hasAllRight);
		return new WebPage<Map<String,Object>>(page);
	}
	 
	@RequestMapping("/goJqGridByWorkType12")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridByWorkType12(HttpServletRequest request,
			HttpServletResponse response,PersonMessage personMessage) throws Exception {		
		
		personMessage = personMessageService.get(PersonMessage.class, personMessage.getPk());
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobMessageService.goJqGridByWorkType12(page, personMessage);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrjProgView")
	public ModelAndView goPrjProgView(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		PrjProgTemp prjProgTemp=null;
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
		int delayDays=0;
		if(customer.getConfirmBegin()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar =new GregorianCalendar(); 
			calendar.setTime(customer.getConfirmBegin());
			calendar.add(Calendar.DATE,customer.getConstructDay());
			planEndDate = calendar.getTime();
			delayDays=Integer.parseInt(((new Date().getTime()-planEndDate.getTime())/1000/60/60/24)+"");
			planEnd=sdf.format(planEndDate);
		}
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(customer.getCode());
		Map<String,Object> balanceMap=customerService.getShouldBanlance(customer.getCode());
		customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(customer.getCode()));
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(customer.getCode()));
		return new ModelAndView("admin/query/prjDelayAnaly/prjDelayAnaly_view")
			.addObject("customer", customer)
			.addObject("customerPayMap", customerPayMap)
			.addObject("balanceMap", balanceMap)
			.addObject("planEnd",planEnd)
			.addObject("delayDays",delayDays)
			.addObject("custTypeDescr",custTypeDescr)
			.addObject("buildStatusDescr", buildStatusDescr);
	}
	/**
	 * 查询ReasonJqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReasonJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReasonJqGrid(HttpServletRequest request,
			HttpServletResponse response,PersonMessage personMessage) throws Exception {		
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobMessageService.goReasonJqGrid(page, personMessage);
		return new WebPage<Map<String,Object>>(page);
	}
	/*
	 *prjmsg到完成界面
	 * */
	@RequestMapping("/goComplete")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到完成页面");
		PersonMessage personMessage = null;
		if (StringUtils.isNotBlank(id)){
			personMessage = personMessageService.get(PersonMessage.class, Integer.parseInt(id));
		}else{
			personMessage = new PersonMessage();
		}
		if (personMessage.getRcvCzy()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, personMessage.getRcvCzy());
			personMessage.setRcvCzyDescr(employee==null?"":employee.getNameChi());
		}		
		
		Map<String,Object>  list1= prjJobMessageService.getAddress(personMessage.getPk());			
		if ((list1!=null)&&(list1.size()>0)){		
			if (list1.get("Address")!=null){
				personMessage.setAddress((String) list1.get("Address"));	
			}else {
				personMessage.setAddress("");				
			}
			if (list1.get("deal")!=null){
				personMessage.setDeal((String) list1.get("deal"));	
			}else {
				personMessage.setDeal("");				
			}
			if (list1.get("Deparment2Descr")!=null){
				personMessage.setDepartment2Descr((String) list1.get("Deparment2Descr"));	
			}else {
				personMessage.setDepartment2Descr("");				
			}
		}else{
			personMessage.setAddress("");	
			personMessage.setDepartment2Descr("");
		}
		
		personMessage.setRcvDate(new Date());
		personMessage.setRcvDate(new java.sql.Timestamp(personMessage.getRcvDate().getTime()));
		
		return new ModelAndView("admin/project/prjMsg/prjMsg_Complete")
			.addObject("personMessage", personMessage).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	//跳转到修改触发时间页面
	@RequestMapping("/goUpSendDate")
	public ModelAndView goUpSendDate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑页面");
		PersonMessage personMessage = null;
		if (StringUtils.isNotBlank(id)){
			personMessage = personMessageService.get(PersonMessage.class, Integer.parseInt(id));
		}else{
			personMessage = new PersonMessage();
		}
		if (personMessage.getRcvCzy()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, personMessage.getRcvCzy());
			personMessage.setRcvCzyDescr(employee==null?"":employee.getNameChi());
		}		
		
		Map<String,Object>  list1= prjJobMessageService.getAddress(personMessage.getPk());			
		if ((list1!=null)&&(list1.size()>0)){		
			if (list1.get("Address")!=null){
				personMessage.setAddress((String) list1.get("Address"));	
			}else {
				personMessage.setAddress("");				
			}
			if (list1.get("deal")!=null){
				personMessage.setDeal((String) list1.get("deal"));	
			}else {
				personMessage.setDeal("");				
			}
			if (list1.get("Deparment2Descr")!=null){
				personMessage.setDepartment2Descr((String) list1.get("Deparment2Descr"));	
			}else {
				personMessage.setDepartment2Descr("");				
			}
		}else{
			personMessage.setAddress("");	
			personMessage.setDepartment2Descr("");
		}
		
		personMessage.setRcvDate(new Date());
		personMessage.setRcvDate(new java.sql.Timestamp(personMessage.getRcvDate().getTime()));
		
		return new ModelAndView("admin/project/prjMsg/prjMsg_UpSendDate")
			.addObject("personMessage", personMessage).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	//跳转到查看页面
		@RequestMapping("/goView")
		public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
			logger.debug("跳转到查看页面");
			PersonMessage personMessage = null;
			if (StringUtils.isNotBlank(id)){
				personMessage = personMessageService.get(PersonMessage.class, Integer.parseInt(id));
			}else{
				personMessage = new PersonMessage();
			}
			if (personMessage.getRcvCzy()!=null) {
				Employee employee= null;
				employee = employeeService.get(Employee.class, personMessage.getRcvCzy());
				personMessage.setRcvCzyDescr(employee==null?"":employee.getNameChi());
			}		
			
			Map<String,Object>  list1= prjJobMessageService.getAddress(personMessage.getPk());			
			if ((list1!=null)&&(list1.size()>0)){		
				if (list1.get("Address")!=null){
					personMessage.setAddress((String) list1.get("Address"));	
				}else {
					personMessage.setAddress("");				
				}
				if (list1.get("deal")!=null){
					personMessage.setDeal((String) list1.get("deal"));	
				}else {
					personMessage.setDeal("");				
				}
				if (list1.get("Deparment2Descr")!=null){
					personMessage.setDepartment2Descr((String) list1.get("Deparment2Descr"));	
				}else {
					personMessage.setDepartment2Descr("");				
				}
			}else{
				personMessage.setAddress("");	
				personMessage.setDepartment2Descr("");
			}
			
			return new ModelAndView("admin/project/prjMsg/prjMsg_View")
				.addObject("personMessage", personMessage).addObject("czy", this.getUserContext(request).getCzybh());
		}

		
	/**
	 *材料分类保存 
	 *
	 */
	@RequestMapping("/doSave")
	public void doPurchaseSave(HttpServletRequest request,HttpServletResponse response,PersonMessage personMessage){
		logger.debug("确认完成");	
		if (personMessage.getSendDate()!=null){
			
			Date d = new Date(); 
	        Calendar c = Calendar.getInstance();  
		    c.setTime(personMessage.getSendDate());  
		    c.add(Calendar.DAY_OF_MONTH, 1);
		    personMessage.setSendDate1(c.getTime());
	        if (d.getTime()>personMessage.getSendDate1().getTime()){
			ServletUtils.outPrintFail(request, response, "触发时间必须大于等于今天！");
			return;
			}
		}
		
		
		try {
			personMessage.setRcvDate(new Date());
			personMessage.setLastUpdatedBy(getUserContext(request).getCzybh());
			if (personMessage.getSendDate() ==null){
				personMessage.setSendDate(new Date());
			}
			personMessage.setRcvStatus("1");
			Result result = this.prjJobMessageService.doSave(personMessage);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料新增失败");
		}
	}
	
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PersonMessage personMessage){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		boolean hasSelfRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0332", "查看本人");
		boolean hasDeptRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0332", "查看本部门");
		boolean hasAllRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0332", "查看所有");
		prjJobMessageService.findPageBySql(page, personMessage,hasSelfRight,hasDeptRight,hasAllRight);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"施工任务监控_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		
	}
}
