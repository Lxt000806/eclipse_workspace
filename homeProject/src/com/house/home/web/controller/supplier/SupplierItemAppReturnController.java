package com.house.home.web.controller.supplier;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.ItemAppService;

@Controller
@RequestMapping("/admin/supplierItemAppReturn")
public class SupplierItemAppReturnController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplierItemAppReturnController.class);

	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemApp.setType("R");//R 退回
		//itemApp.setSendType("1");//1 供应商直送
		itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemAppService.findPageBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemApp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemApp itemApp) throws Exception {

		return new ModelAndView("admin/supplier/itemAppReturn/itemAppReturn_list").addObject("itemApp", itemApp);
	}
	
	/**
	 * 跳转到新增ItemApp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemApp页面");
		ItemApp itemApp = null;
		String postData = request.getParameter("postData");
		if(StringUtils.isNotEmpty(postData)){
			JSONObject obj = JSONObject.parseObject(postData);
			id = obj.getString("no");
		}
		if (StringUtils.isNotBlank(id)){
			itemApp = itemAppService.get(ItemApp.class, id);
		}else{
			itemApp = new ItemApp();
			itemApp.setType("R");//R 退货
			itemApp.setStatus("OPEN");//OPEN 已申请
			//itemApp.setSendType("1");//1 供应商直送
			//itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		}
		
		return new ModelAndView("admin/supplier/itemAppReturn/itemAppReturn_save")
			.addObject("itemApp", itemApp)
			.addObject("czybh", this.getUserContext(request).getCzybh())
			.addObject("emnum", this.getUserContext(request).getEmnum());
	}
	/**
	 * 跳转到修改ItemApp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemApp页面");
		ItemApp itemApp = new ItemApp();
//		String postData = request.getParameter("postData");
		if (StringUtils.isNotBlank(id)){
			Map<String,Object> map = itemAppService.getByNo(id);
			BeanConvertUtil.mapToBean(map, itemApp);
		}
		itemApp.setDoubleString(NumberFormat.getInstance().format(itemApp.getOtherCost()).replace(",", ""));
		return new ModelAndView("admin/supplier/itemAppReturn/itemAppReturn_update")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * 跳转到查看ItemApp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemApp页面");
		ItemApp itemApp = new ItemApp();
		if (StringUtils.isNotBlank(id)){
			Map<String,Object> map = itemAppService.getByNo(id);
			BeanConvertUtil.mapToBean(map, itemApp);
		}
		itemApp.setDoubleString(NumberFormat.getInstance().format(itemApp.getOtherCost()).replace(",", ""));
		
		return new ModelAndView("admin/supplier/itemAppReturn/itemAppReturn_detail")
				.addObject("itemApp", itemApp);
	}
	/**
	 * 添加ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("添加ItemApp开始");
		try{
			ItemApp oldItemApp = itemAppService.get(ItemApp.class, itemApp.getOldNo());
			if (!"SEND".equals(oldItemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response,"只能对已发货的领料单进行退回操作！");
				return;
			}
			//楼盘处于项目经理提成已领或者项目经理提成要领状态，不允许供应商新增基础的领料退回单。
			if ("JZ".equals(oldItemApp.getItemType1().trim())){
				Customer customer = customerService.get(Customer.class, oldItemApp.getCustCode());
				if ("3".equals(customer.getCheckStatus().trim()) || "4".equals(customer.getCheckStatus().trim())){
					ServletUtils.outPrintFail(request, response,"楼盘【"+customer.getAddress()+"】已经完工归档，不允许领料退回！");
					return;
				}
			}
			
			itemApp.setM_umState("A");
			if (StringUtils.isNotBlank(itemApp.getSupplCode())){
				itemApp.setSendType("1");//1 退货到供应商
			}else{
				itemApp.setSendType("2");
			}
			itemApp.setDate(new Date());
			itemApp.setAppCzy(this.getUserContext(request).getCzybh());
			itemApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemApp.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			itemApp.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response,"无领料明细！请先添加领料明细！");
				return;
			}
			Result result = this.itemAppService.doItemAppBackForProc(itemApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemApp失败");
		}
	}
	
	/**
	 * 修改ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("修改ItemApp开始");
		try{
			itemApp.setM_umState("M");
			itemApp.setDate(new Date());
			itemApp.setAppCzy(this.getUserContext(request).getCzybh());
			itemApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemApp.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			itemApp.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response,"无领料明细！请先添加领料明细！");
				return;
			}
			Result result = this.itemAppService.doItemAppBackForProc(itemApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemApp失败");
		}
	}
	
	/**
	 * 删除ItemApp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemApp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemApp itemApp = itemAppService.get(ItemApp.class, deleteId);
				if(itemApp == null)
					continue;
				itemApp.setExpired("T");
				itemAppService.update(itemApp);
			}
		}
		logger.debug("删除ItemApp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemApp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppService.findPageBySql(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
						"退货_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到结算申请页面
	 * @return
	 */
	@RequestMapping("/goCheckApp")
	public ModelAndView goCheckApp(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemApp页面");

		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		if (StringUtils.isNotBlank(itemApp.getCustCode())){
			Customer customer = customerService.get(Customer.class, itemApp.getCustCode());
			if (customer!=null){
				itemApp.setCustAddress(customer.getAddress());
			}
		}
		if(StringUtils.isNotBlank(itemApp.getPuno())){
			Purchase purchase=customerService.get(Purchase.class, itemApp.getPuno());
			itemApp.setSplAmount(purchase.getSplAmount());
			itemApp.setPuSplStatus(purchase.getSplStatus());
			itemApp.setOtherCost(purchase.getOtherCost());
			itemApp.setAmount(purchase.getAmount());
			if(StringUtils.isNotBlank(purchase.getCheckOutNo())){
				SplCheckOut splCheckOut=customerService.get(SplCheckOut.class, purchase.getCheckOutNo());
				itemApp.setCheckStatus(splCheckOut.getStatus());
			}
		}
		return new ModelAndView("admin/supplier/itemAppReturn/itemAppReturn_checkApp")
			.addObject("itemApp", itemApp);
	}
	/**
	 * 结算申请保存
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doCheckApp")
	public void doCheckApp(HttpServletRequest request, HttpServletResponse response,ItemApp itemApp){
		logger.debug("结算申请保存开始");
		itemApp.setLastUpdatedBy(getUserContext(request).getCzybh());
		itemAppService.doCheckAppR(itemApp);
		ServletUtils.outPrintSuccess(request, response,"保存成功");
	}
}
