package com.house.home.web.controller.project;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.CacheUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.project.ProgCheckPlan;
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.project.ProgCheckPlanDetailService;
import com.house.home.service.project.XjrwapService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

@Controller
@RequestMapping("/admin/xjrwap")
public class XjrwapController extends BaseController {

	@Autowired
	private XjrwapService xjrwapService;
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 巡检任务安排
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ProgCheckPlan progCheckPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		xjrwapService.findPageBySql(page, progCheckPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrjJqGrid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		xjrwapService.findPrjPageBySql(page );
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询明细
	 * @param request
	 * @param response
	 * @param progCheckPlanDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,ProgCheckPlanDetail progCheckPlanDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		xjrwapService.findDetailPageBySql(page, progCheckPlanDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 新增页面明细
	 * @param request
	 * @param response
	 * @param customer
	 * @param custCodes
	 * @param checkType
	 * @param auto
	 * @param longitude
	 * @param latitude
	 * @param isCheckDept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getAddJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer,String custCodes,String checkType,String auto,
			String longitude,String latitude,String isCheckDept,String importancePrj) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		xjrwapService.findAddPageBySql(page, customer,custCodes,checkType,auto,longitude,latitude,isCheckDept,importancePrj);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 前端导入页面明细
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 * @param arr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFroAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getFroAddJqGrid(HttpServletRequest request,
			HttpServletResponse response,ProgCheckPlan progCheckPlan,String arr) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		xjrwapService.findFroAddPageBySql(page, progCheckPlan,arr);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 巡检任务安排主页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/project/xjrwap/xjrwap_list");
	}
	
	/**
	 * 跳转巡检任务安排新增页面
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response , ProgCheckPlan progCheckPlan) throws Exception{
		logger.debug("跳转到新增页面");
		if(DateUtil.isPM(new Date())){
			progCheckPlan.setCrtDate(DateUtil.addDate(new Date(), 1));
		}else{
			progCheckPlan.setCrtDate(new Date());
		}
		Integer froNum=this.xjrwapService.getFroNum();
		return new ModelAndView("admin/project/xjrwap/xjrwap_save")
			.addObject("progCheckPlan",progCheckPlan).addObject("froNum",froNum);
	}
	
	/**
	 * 跳转巡检任务安排复制页面
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request ,
			HttpServletResponse response , ProgCheckPlan progCheckPlan) throws Exception{
		logger.debug("跳转到新增页面");
		ProgCheckPlan  pcp=new ProgCheckPlan();
		Employee employee =new Employee();
		Integer froNum=this.xjrwapService.getFroNum();
		pcp=xjrwapService.get(ProgCheckPlan.class, progCheckPlan.getNo());
		employee=employeeService.get(Employee.class, pcp.getCheckCZY());
		pcp.setCheckCZYDescr(employee.getNameChi()==null?"":employee.getNameChi());

		return new ModelAndView("admin/project/xjrwap/xjrwap_copy")
			.addObject("progCheckPlan",pcp).addObject("froNum",froNum);
	}
	
	/**
	 * 跳转巡检任务安排编辑页面
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response , ProgCheckPlan progCheckPlan) throws Exception{
		logger.debug("跳转到新增页面");
		ProgCheckPlan  pcp=new ProgCheckPlan();
		Employee employee =new Employee();
		Integer froNum=this.xjrwapService.getFroNum();
		pcp=xjrwapService.get(ProgCheckPlan.class, progCheckPlan.getNo());
		employee=employeeService.get(Employee.class, pcp.getCheckCZY());
		pcp.setCheckCZYDescr(employee.getNameChi()==null?"":employee.getNameChi());

		return new ModelAndView("admin/project/xjrwap/xjrwap_update")
			.addObject("progCheckPlan",pcp).addObject("froNum",froNum);
	}
	
	/**
	 * 跳转查看页面
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response , ProgCheckPlan progCheckPlan) throws Exception{
		logger.debug("跳转到新增页面");
		ProgCheckPlan  pcp=new ProgCheckPlan();
		Employee employee =new Employee();
		
		pcp=xjrwapService.get(ProgCheckPlan.class, progCheckPlan.getNo());
		employee=employeeService.get(Employee.class, pcp.getCheckCZY());
		pcp.setCheckCZYDescr(employee.getNameChi()==null?"":employee.getNameChi());
		
		return new ModelAndView("admin/project/xjrwap/xjrwap_view")
			.addObject("progCheckPlan",pcp);
	}
	 
	@RequestMapping("/goUpdatePrjMan")
	public ModelAndView goUpdatePrjMan(HttpServletRequest request ,
		HttpServletResponse response) throws Exception{
		logger.debug("跳转重点巡检监理");
		return new ModelAndView("admin/project/xjrwap/xjrwap_updatePrjMan");
	}
	
	@RequestMapping("/goAddPrjMan")
	public ModelAndView goAddPrjMan(HttpServletRequest request ,
		HttpServletResponse response) throws Exception{
		logger.debug("跳转重点巡检监理");
		return new ModelAndView("admin/project/xjrwap/xjrwap_addPrjMan");
	}
	
	/**
	 * 新增明细页面
	 * @param request
	 * @param response
	 * @param custCodes
	 * @param type
	 * @param czybh
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request ,
			HttpServletResponse response,String custCodes,String type,String czybh ) throws Exception{
		Customer customer=new Customer();
		Employee employee =new Employee();
		String lastDate="";
		String autoNum="";
		if(getCookieByName(request, "lastDate")==null){
			customer.setBeginDate(new Date());
		}else{
			if("".equals(getCookieByName(request, "lastDate").getValue())){
				customer.setBeginDate(new Date());
			}else {
				lastDate=getCookieByName(request, "lastDate").getValue();
				customer.setBeginDate(DateUtil.addDate(new Date(),-Integer.parseInt(getCookieByName(request, "lastDate").getValue())));
			}
			if(getCookieByName(request, "autoNum")==null||"".equals(getCookieByName(request, "autoNum").getValue())){
				autoNum="";
			}else{
				autoNum=getCookieByName(request, "autoNum").getValue();
			}
		}
		customer.setDateFrom(DateUtil.addDate(new Date(), -10));
		employee=employeeService.get(Employee.class, czybh);
		String department2=employee.getDepartment2();
		if("3".equals(type)){
			customer.setDepartment2(department2);
		}else{
			department2="";
		}
		String isCheckDept=xjrwapService.getIsCheckDept(czybh);
		
		return new ModelAndView("admin/project/xjrwap/xjrwap_add")
		.addObject("custCodes",custCodes).addObject("customer",customer).addObject("department2",department2)
		.addObject("type",type).addObject("lastDate",lastDate).addObject("autoNum",autoNum)
		.addObject("isCheckDept",isCheckDept);
	}
	
	/**
	 * 前端导入页面
	 * @param request
	 * @param response
	 * @param arr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFroAdd")
	public ModelAndView goFroAdd(HttpServletRequest request ,
			HttpServletResponse response,String arr ) throws Exception{
		ProgCheckPlan progCheckPlan=new ProgCheckPlan();
		return new ModelAndView("admin/project/xjrwap/xjrwap_froAdd")
		.addObject("arr",arr);
	}
	
	/**
	 * 新增保存操作
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			ProgCheckPlan progCheckPlan){
		logger.debug("新增开始");
		try {
			
			progCheckPlan.setM_umState("A");
			//progCheckPlan.setCrtDate(new Date());
			progCheckPlan.setM_czy(this.getUserContext(request).getCzybh());
			progCheckPlan.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			
			progCheckPlan.setDetailJson(detailJson);
			Result result =this.xjrwapService.doSave(progCheckPlan) ;
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 编辑保存操作
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			ProgCheckPlan progCheckPlan){
		logger.debug("新增开始");
		try {
			
			progCheckPlan.setM_umState("M");
			//progCheckPlan.setCrtDate(new Date());
			progCheckPlan.setM_czy(this.getUserContext(request).getCzybh());
			if(StringUtils.isBlank(progCheckPlan.getExpired())){
				progCheckPlan.setExpired("F");
			}
			String detailJson = request.getParameter("detailJson");
			
			progCheckPlan.setDetailJson(detailJson);
			Result result =this.xjrwapService.doUpdate(progCheckPlan) ;
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doAddPrjMan")
	public void doAddPrjMan(HttpServletRequest request,HttpServletResponse response,
			String projectMan){
		logger.debug("新增开始");
		try {
			
			if(StringUtils.isNotBlank(projectMan)){
				if(xjrwapService.getPrjManByCode(projectMan)){
					ServletUtils.outPrintFail(request, response, "该监理已存在");
				}
				
				this.xjrwapService.doSavePrjMan(projectMan,
						this.getUserContext(request).getCzybh());
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			}else {
				ServletUtils.outPrintFail(request, response, "保存失败,监理编号为空");
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response,
			Integer pk){
		logger.debug("新增开始");
		try {
			if(pk!=null){
				this.xjrwapService.doDelPrjMan(pk);
				ServletUtils.outPrintSuccess(request, response, "删除成功");
			}else {
				ServletUtils.outPrintFail(request, response, "请选择一条记录");
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doSaveCookie")
	public void doSaveCookie(HttpServletRequest request,HttpServletResponse response ){
		logger.debug("保存缓存开始");
		try {
			addCookie(response, "lastDate",request.getParameter("cookieDays"),3600*24*365);
			addCookie(response, "autoNum",request.getParameter("autoNum"),3600*24*365);

		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存缓存失败");
		}
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param progCheckPlan
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ProgCheckPlan progCheckPlan){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		xjrwapService.findPageBySql(page, progCheckPlan);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"巡检任务_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date getDate() {
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(currentTime);
		   ParsePosition pos = new ParsePosition(8);
		   Date currentTime_2 = formatter.parse(dateString, pos);
		   return currentTime_2;
		}
	
	/**
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0) {
	    	cookie.setMaxAge(maxAge);
	    }
	    	response.addCookie(cookie);
	}
	
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	
}
