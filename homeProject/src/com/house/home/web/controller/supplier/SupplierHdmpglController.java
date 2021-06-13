package com.house.home.web.controller.supplier;

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
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Hdmpgl;
import com.house.home.service.basic.ActivityService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.HdmpglService;

@Controller
@RequestMapping("/admin/supplierHdmpgl")
public class SupplierHdmpglController extends BaseController{

	@Autowired
	private HdmpglService hdmpglService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 活动门票管理
	 * @param request
	 * @param response
	 * @param hdmpgl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Hdmpgl hdmpgl) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc =getUserContext(request);
		hdmpglService.findPageBySql(page, hdmpgl,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetail_OrderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetail_OrderJqGrid(HttpServletRequest request,
			HttpServletResponse response,Hdmpgl hdmpgl) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		hdmpglService.goDetail_OrderJqGrid(page, hdmpgl);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetail_giftJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetail_giftJqGrid(HttpServletRequest request,
			HttpServletResponse response,Hdmpgl hdmpgl) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		hdmpglService.goDetail_giftJqGrid(page, hdmpgl);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 活动门票管理主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		UserContext uc = getUserContext(request);
		String czybh= uc.getCzybh();
		String czylb=uc.getCzylb();
		String provideType="2";
		Map<String, Object> validActMap=hdmpglService.getValidActNum();
		if(validActMap!=null){
			hdmpgl.setActNo(String.valueOf(validActMap.get("No")));
			hdmpgl.setActDescr(String.valueOf(validActMap.get("ActName")));
		}
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 810);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0211", "高级管理");
		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_list").addObject("hdmpgl",hdmpgl)
		.addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("czybh",czybh)
		.addObject("czylb",czylb).addObject("provideType",provideType);
	}
	
	/**
	 * 活动门票管理-跳转发放页面
	 * @param request
	 * @param response
	 * @param hdmpgl
	 * @return
	 */
	@RequestMapping("/goProvide")
	public ModelAndView goProvide(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		String czylb=this.getUserContext(request).getCzylb();
		if("1".equals(czylb)){
			hdmpgl.setProvideType("1");
		}else if("2".equals(czylb)){
			hdmpgl.setProvideType("2");
		}
		hdmpgl.setActNo("");
		hdmpgl.setProvideDate(new Date());
		hdmpgl.setProvideCZY(this.getUserContext(request).getCzybh());
		hdmpgl.setProvideDescr(this.getUserContext(request).getZwxm());
		
		Map<String, Object> validActMap=hdmpglService.getValidActNum();
		if(validActMap!=null){
			hdmpgl.setActNo(String.valueOf(validActMap.get("No")));
			hdmpgl.setActDescr(String.valueOf(validActMap.get("ActName")));
		}
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 810);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0211", "高级管理");
		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_provide")
		.addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("hdmpgl",hdmpgl).addObject("czylb",czylb);
	}
	
	/**
	 * 活动门票管理-跳转查看页面
	 * @param request
	 * @param response
	 * @param hdmpgl
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,Integer pk) throws Exception{
		Hdmpgl hdmpgl=null;
		Employee businessMan=null;
		Employee designMan=null;
		Czybm provideCZY=null;
		Activity activity=null;
		String czylb=this.getUserContext(request).getCzylb();
	
		hdmpgl=hdmpglService.get(Hdmpgl.class, pk);
		activity=activityService.get(Activity.class, hdmpgl.getActNo());
		hdmpgl.setActDescr(activity.getActName());
		if(StringUtils.isNotBlank(hdmpgl.getBusinessMan())){
			businessMan=employeeService.get(Employee.class, hdmpgl.getBusinessMan());
			hdmpgl.setBusinessDescr(businessMan.getNameChi());
		}
		if(StringUtils.isNotBlank(hdmpgl.getDesignMan())){
			designMan=employeeService.get(Employee.class, hdmpgl.getDesignMan());
			hdmpgl.setDesignDescr(designMan.getNameChi());
		}
		if(StringUtils.isNotBlank(hdmpgl.getProvideCZY())){
			provideCZY=czybmService.get(Czybm.class, hdmpgl.getProvideCZY());
			hdmpgl.setProvideDescr(provideCZY==null?"":provideCZY.getZwxm());
		}
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 810);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0211", "高级管理");
		//hdmpgl.setProvideDescr(this.getUserContext(request).getZwxm());
		String czybh =this.getUserContext(request).getCzybh();
		
		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_view")
		.addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("hdmpgl",hdmpgl).addObject("czylb",czylb)
		.addObject("czybh",czybh);
	}
	
	/**
	 * 活动门票管理-跳转编辑页面
	 * @param request
	 * @param response
	 * @param hdmpgl
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response,Integer pk) throws Exception{
		Hdmpgl hdmpgl=null;
		Employee businessMan=null;
		Employee designMan=null;
		Czybm provideCZY=null;
		String czylb=this.getUserContext(request).getCzylb();
		Activity activity=null;
		
		hdmpgl=hdmpglService.get(Hdmpgl.class, pk);
		activity=activityService.get(Activity.class, hdmpgl.getActNo());
		hdmpgl.setActDescr(activity.getActName());
		
		if(StringUtils.isNotBlank(hdmpgl.getBusinessMan())){
			businessMan=employeeService.get(Employee.class, hdmpgl.getBusinessMan());
			hdmpgl.setBusinessDescr(businessMan.getNameChi());
		}
		if(StringUtils.isNotBlank(hdmpgl.getDesignMan())){
			designMan=czybmService.get(Employee.class, hdmpgl.getDesignMan());
			hdmpgl.setDesignDescr(designMan.getNameChi());
		}
		if(StringUtils.isNotBlank(hdmpgl.getProvideCZY())){
			provideCZY=czybmService.get(Czybm.class, hdmpgl.getProvideCZY());
			hdmpgl.setProvideDescr(provideCZY==null?"":provideCZY.getZwxm());
		}
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 810);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0211", "高级管理");

		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_update")
		.addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("hdmpgl",hdmpgl).addObject("czylb",czylb);
	}
	
	/**
	 * 活动门票管理-发放操作
	 * @param request
	 * @param response
	 * @param hdmpgl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doProvide")
	public void doProvide(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票发放");
		Hdmpgl hd=null;
		
		String pkString =hdmpglService.getPk(hdmpgl.getActNo(),hdmpgl.getTicketNo());
		if(pkString!=null){

		}else{
			ServletUtils.outPrintFail(request, response, "该门票已发放或不存在此门票号");
			return;
		}
		hd=hdmpglService.get(Hdmpgl.class,Integer.parseInt(pkString));
		
		//判断业主电话是否已存在
		if (hdmpglService.isExistPhone(hdmpgl.getActNo(), hdmpgl.getPhone())) {
			ServletUtils.outPrintFail(request, response, "该业主电话已存在，不能重复录入！");
			return;
		}
		
		try{
			hd.setLastUpdate(new Date());
			hd.setProvideDate(new Date());
			hd.setDescr(hdmpgl.getDescr());
			hd.setAddress(hdmpgl.getAddress());
			hd.setPhone(hdmpgl.getPhone());
			hd.setBusinessMan(hdmpgl.getBusinessMan());
			hd.setDesignMan(hdmpgl.getDesignMan());
			hd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			hd.setBusiManName(hdmpgl.getBusiManName());
			hd.setBusiManPhone(hdmpgl.getBusiManPhone());
			hd.setStatus("2");
			hd.setProvideCZY(this.getUserContext(request).getCzybh());
			hd.setProvideType(hdmpgl.getProvideType());
			hd.setActionLog("EDIT");
			hd.setPlanSupplType(hdmpgl.getPlanSupplType());

			this.hdmpglService.update(hd);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "发放门票失败");
		}
	}
	
	/**
	 * 活动门票管理-编辑操作
	 * @param request
	 * @param response
	 * @param hdmpgl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票发放编辑");
		Hdmpgl hd=null;
		hd=hdmpglService.get(Hdmpgl.class, hdmpgl.getPk());
		try{
			hd.setLastUpdate(new Date());
			hd.setProvideDate(new Date());
			hd.setDescr(hdmpgl.getDescr());
			hd.setAddress(hdmpgl.getAddress());
			hd.setPhone(hdmpgl.getPhone());
			hd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			hd.setBusiManName(hdmpgl.getBusiManName());
			hd.setBusiManPhone(hdmpgl.getBusiManPhone());
			hd.setBusinessMan(hdmpgl.getBusinessMan());
			hd.setDesignMan(hdmpgl.getDesignMan());
			hd.setStatus("2");
			hd.setProvideCZY(this.getUserContext(request).getCzybh());
			hd.setProvideType(hdmpgl.getProvideType());
			hd.setActionLog("EDIT");
			hd.setPlanSupplType(hdmpgl.getPlanSupplType());
			this.hdmpglService.update(hd);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 收回门票
	 * 
	 * */
	@RequestMapping("/doCallback")
	public void doCallback(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票发放编辑");
		try{
			
			this.hdmpglService.doCallBack(hdmpgl.getPk(),this.getUserContext(request).getCzybh());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Hdmpgl hdmpgl){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc=getUserContext(request);
		page.setPageSize(-1);
		hdmpglService.findPageBySql(page, hdmpgl,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"活动门票_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	
}
