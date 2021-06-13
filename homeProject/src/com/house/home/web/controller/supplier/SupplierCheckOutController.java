package com.house.home.web.controller.supplier;

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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.PayRule;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.finance.SplCheckOutService;
import com.house.home.service.insales.SupplierService;

@Controller
@RequestMapping("/admin/supplierCheckOut")
public class SupplierCheckOutController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplierCheckOutController.class);

	@Autowired
	private SplCheckOutService splCheckOutService;
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,SplCheckOut splCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		splCheckOut.setSplCode(this.getUserContext(request).getEmnum());
		splCheckOut.setAppCZY(this.getUserContext(request).getCzybh());
		splCheckOutService.findPageBySql(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * splCheckOut列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {

		return new ModelAndView("admin/supplier/checkOut/checkOut_list").addObject("splCheckOut", splCheckOut);
	}
	
	/**
	 * 跳转到新增splCheckOut页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增splCheckOut页面");
		SplCheckOut splCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			splCheckOut = splCheckOutService.get(SplCheckOut.class, id);
		}else{
			splCheckOut = new SplCheckOut();
		}
		UserContext uc = this.getUserContext(request);
		String splCode = uc.getEmnum();
		splCheckOut.setSplCode(splCode);
		Supplier supplier = supplierService.get(Supplier.class, splCode);
		boolean isEdit = true;
		if (supplier!=null){
			splCheckOut.setSplCodeDescr(supplier.getDescr());
			splCheckOut.setDate(new Date());
			splCheckOut.setStatus("1");
			if (supplier.getItemType1()!=null && "JZ".equals(supplier.getItemType1().trim())){
				isEdit = false;
			}
		}
		
		return new ModelAndView("admin/supplier/checkOut/checkOut_save")
			.addObject("splCheckOut", splCheckOut)
			.addObject("costRight", uc.getCostRight())
			.addObject("isEdit", isEdit)
			.addObject("projectCostRight", uc.getProjectCostRight());
	}
	/**
	 * 跳转到修改splCheckOut页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改splCheckOut页面");
		UserContext uc = this.getUserContext(request);
		SplCheckOut splCheckOut = splCheckOutService.get(SplCheckOut.class, id);
		Supplier supplier = supplierService.get(Supplier.class, splCheckOut.getSplCode());
		boolean isEdit = true;
		if (supplier!=null){
			splCheckOut.setSplCodeDescr(supplier.getDescr());
			if (supplier.getItemType1()!=null && "JZ".equals(supplier.getItemType1().trim())){
				isEdit = false;
			}
		}
		
		return new ModelAndView("admin/supplier/checkOut/checkOut_update")
			.addObject("splCheckOut", splCheckOut)
			.addObject("costRight", uc.getCostRight())
			.addObject("isEdit", isEdit)
			.addObject("projectCostRight", uc.getProjectCostRight());
	}
	
	/**
	 * 跳转到查看splCheckOut页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看splCheckOut页面");
		UserContext uc = this.getUserContext(request);
		SplCheckOut splCheckOut = splCheckOutService.get(SplCheckOut.class, id);
		Supplier supplier = supplierService.get(Supplier.class, splCheckOut.getSplCode());
		if (supplier!=null){
			splCheckOut.setSplCodeDescr(supplier.getDescr());
		}
		
		return new ModelAndView("admin/supplier/checkOut/checkOut_detail")
				.addObject("splCheckOut", splCheckOut)
				.addObject("costRight", uc.getCostRight())
				.addObject("projectCostRight", uc.getProjectCostRight());
	}
	/**
	 * 跳转到查看splCheckOutAll页面
	 * @return
	 */
	@RequestMapping("/goDetailAll")
	public ModelAndView goDetailAll(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/supplier/checkOut/checkOut_detailAll");
	}
	/**
	 * 添加splCheckOut
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		logger.debug("添加splCheckOut开始");
		try{
			splCheckOut.setM_umState("A");
			splCheckOut.setBeginDate(new Date());
			splCheckOut.setEndDate(new Date());
			splCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			splCheckOut.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			splCheckOut.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response,"无结算明细！请先添加结算明细！");
				return;
			}
			Result result = this.splCheckOutService.doSaveSplCheckOutForProc(splCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加splCheckOut失败");
		}
	}
	
	/**
	 * 修改splCheckOut
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		logger.debug("修改splCheckOut开始");
		try{
			splCheckOut.setM_umState("M");
			splCheckOut.setBeginDate(new Date());
			splCheckOut.setEndDate(new Date());
			splCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			splCheckOut.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			splCheckOut.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response,"无结算明细！请先添加结算明细！");
				return;
			}
			Result result = this.splCheckOutService.doSaveSplCheckOutForProc(splCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改splCheckOut失败");
		}
	}
	
	/**
	 * 删除splCheckOut
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除splCheckOut开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "splCheckOut编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SplCheckOut splCheckOut = splCheckOutService.get(SplCheckOut.class, deleteId);
				if(splCheckOut == null)
					continue;
				splCheckOut.setExpired("T");
				splCheckOutService.update(splCheckOut);
			}
		}
		logger.debug("删除splCheckOut IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *splCheckOut导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SplCheckOut splCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		splCheckOutService.findPageBySql(page, splCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"供应商结算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 跳转到修改splCheckOut页面
	 * @return
	 */
	@RequestMapping("/goAddOtherCost")
	public ModelAndView goAddOtherCost(HttpServletRequest request, HttpServletResponse response, 
			Purchase purchase){
		logger.debug("跳转到修改splCheckOut页面");
		return new ModelAndView("admin/supplier/checkOut/checkOut_addOtherCost")
			.addObject("purchase", purchase);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchaseFeeDetailGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchaseFeeDetailGrid(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (StringUtils.isNotBlank(purchase.getSplcode())){
			Supplier supplier = supplierService.get(Supplier.class, purchase.getSplcode());
			purchase.setSupplFeeType(supplier.getSupplFeeType());
		}
		splCheckOutService.findPageBySql_purchaseFeeDetail(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doSaveOtherCost")
	public void doSave(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("其他费用");
		try {
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.splCheckOutService.doSaveSplOtherCostForProc(purchase);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增付款规则失败");
		}
	}

}
