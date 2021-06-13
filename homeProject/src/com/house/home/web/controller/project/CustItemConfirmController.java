package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.project.CustItemConfDate;
import com.house.home.entity.project.ItemConfirmInform;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustItemConfirmService;
import com.house.home.service.project.ItemConfirmInformService;
@Controller
@RequestMapping("/admin/custItemConfirm")
public class CustItemConfirmController extends BaseController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemConfirmInformService itemConfirmInformService;
	@Autowired
	private CustItemConfirmService custItemConfirmService;
	@Autowired
	private XtcsService xtcsService;
	
	
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
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		customerService.findItemConfirmPageBySql(page,customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustItemConfirm列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Xtcs CmpnyCode=xtcsService.get(Xtcs.class, "CmpnyCode");
		Customer customer=new Customer();
		customer.setXtcs(CmpnyCode.getQz());
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		return new ModelAndView("admin/project/custItemConfirm/custItemConfirm_list").addObject("uc",uc).addObject("customer",customer);
	}
	/**
	 * 主材选定预约
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goOrder")
	public ModelAndView goOrder(HttpServletRequest request,
			HttpServletResponse response,ItemConfirmInform itemConfirmInform) throws Exception {
		Customer customer=customerService.get(Customer.class,itemConfirmInform.getCustCode());
		itemConfirmInform.setAddress(customer.getAddress());
		return new ModelAndView("admin/project/custItemConfirm/custItemConfirm_order").addObject("itemConfirmInform",itemConfirmInform);
	}
	/**
	 * 预约保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doOrder")
	public void doOrder(HttpServletRequest request,
			HttpServletResponse response,ItemConfirmInform itemConfirmInform) throws Exception {
		logger.debug("添加itemConfirmInform开始");
		try{
			UserContext uc=this.getUserContext(request);
			itemConfirmInform.setLastUpdate(new Date());
			itemConfirmInform.setLastUpdatedBy(uc.getCzybh());
			itemConfirmInform.setExpired("F");
			itemConfirmInform.setActionLog("ADD");
			itemConfirmInformService.save(itemConfirmInform);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加itemConfirmInform失败");
		}
	}
	/**
	 * 材料确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemConfrim")
	public ModelAndView goItemConfrim(HttpServletRequest request,
			HttpServletResponse response,CustItemConfDate custItemConfDate) throws Exception {
		Customer customer=customerService.get(Customer.class,custItemConfDate.getCustCode());
		custItemConfDate.setAddress(customer.getAddress());
		return new ModelAndView("admin/project/custItemConfirm/custItemConfirm_confirm").addObject("custItemConfDate",custItemConfDate);
	}
	/**
	 * 材料确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doConfirm")
	public void doConfirm(HttpServletRequest request,
			HttpServletResponse response,CustItemConfDate custItemConfDate) throws Exception {
		try{
			UserContext uc=this.getUserContext(request);
			custItemConfDate.setLastUpdatedBy(uc.getCzybh());
			custItemConfirmService.doItemConfirm(custItemConfDate);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "材料确认失败");
		}
	}
	/**
	 * 材料取消确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemCancel")
	public ModelAndView goItemCancel(HttpServletRequest request,
			HttpServletResponse response,CustItemConfDate custItemConfDate) throws Exception {
		Customer customer=customerService.get(Customer.class,custItemConfDate.getCustCode());
		custItemConfDate.setAddress(customer.getAddress());
		return new ModelAndView("admin/project/custItemConfirm/custItemConfirm_cancel").addObject("custItemConfDate",custItemConfDate);
	}
	/**
	 * 材料取消
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doCancel")
	public void doCancel(HttpServletRequest request,
			HttpServletResponse response,CustItemConfDate custItemConfDate) throws Exception {
		try{
			UserContext uc=this.getUserContext(request);
			custItemConfDate.setLastUpdate(new Date());
			custItemConfDate.setLastUpdatedBy(uc.getCzybh());
			custItemConfDate.setActionLog("ADD");
			custItemConfDate.setExpired("F");
			custItemConfirmService.doItemCancel(custItemConfDate);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "材料取消失败");
		}
	}
	/**
	 * 主材选定查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,CustItemConfDate custItemConfDate) throws Exception {
		return new ModelAndView("admin/project/custItemConfirm/custItemConfirm_detail").addObject("custItemConfDate",custItemConfDate);
	}
	
	
	/**
	 * 确认结果JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConfirmResultJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getConfirmResultJqGrid(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfirmService.findConfirmResultPageBySql(page,custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 确认项目的状态JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConfirmStatusJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getConfirmStatusJqGrid(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfirmService.findConfirmStatusPageBySql(page,custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		customerService.findItemConfirmPageBySql(page,customer,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材选定跟踪_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *导出确认结果Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doConfirmResultExcel")
	public void doConfirmResultExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custItemConfirmService.findConfirmResultPageBySql(page,customer.getCode());
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材选定跟踪_确认结果"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
