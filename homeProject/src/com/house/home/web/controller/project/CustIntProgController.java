package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.project.CustIntProg;
import com.house.home.entity.project.IntProduce;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.SupplierService;
import com.house.home.service.project.CustIntProgService;
import com.house.home.service.project.IntProduceService;

@Controller
@RequestMapping("/admin/custIntProg")
public class CustIntProgController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CustIntProgController.class);

	@Autowired
	private CustIntProgService custIntProgService;
	@Autowired
	private IntProduceService intProduceService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private CustomerService customerService;
	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustIntProg custIntProg)
			throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		custIntProgService.findPageBySql(page, custIntProg);
		return new WebPage<Map<String, Object>>(page);
	}
	
	@RequestMapping("/goIntProduceJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getIntProduceJqGrid(HttpServletRequest request,	
			HttpServletResponse response,IntProduce intProduce) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intProduceService.findPageBySql(page, intProduce);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustIntProg列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustIntProg custIntProg = new CustIntProg();
		custIntProg.setStatus("4");// 客户状态默认勾选4
		custIntProg.setCostRight(getUserContext(request).getCostRight());
		return new ModelAndView("admin/project/custIntProg/custIntProg_list")
				.addObject("custIntProg", custIntProg);
	}

	/**
	 * 跳转到新增CustIntProg页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到新增CustIntProg页面");
		CustIntProg custIntProg = null;
		if (StringUtils.isNotBlank(id)) {
			custIntProg = custIntProgService.get(CustIntProg.class, id);
			custIntProg.setCustCode(null);
		} else {
			custIntProg = new CustIntProg();
		}

		return new ModelAndView("admin/project/custIntProg/custIntProg_save")
				.addObject("custIntProg", custIntProg);
	}

	/**
	 * 跳转到修改CustIntProg页面
	 * 
	 * @return
	 */
	@RequestMapping("/goAddinfo")
	public ModelAndView goAddinfo(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		jsonObject.put("costRight", getUserContext(request).getCostRight());
		return new ModelAndView("admin/project/custIntProg/custIntProg_addinfo")
				.addObject("custIntProg", jsonObject);
	}

	/**
	 * 跳转到修改CustIntProg页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDelayAdd")
	public ModelAndView goDelayAdd(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/project/custIntProg/custIntProg_delay")
				.addObject("custIntProg", jsonObject);
	}

	/**
	 * 跳转到查看CustIntProg页面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		jsonObject.put("costRight", getUserContext(request).getCostRight());
		return new ModelAndView("admin/project/custIntProg/custIntProg_view")
				.addObject("custIntProg", jsonObject);
	}
	/**
	 * 生产进度管理
	 * @author	created by zb
	 * @date	2020-3-4--上午11:59:29
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goIntProduceView")
	public ModelAndView goIntProduceView(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("admin/project/custIntProg/custIntProg_intProduceView");
	}
	/**
	 * 跳转到生产进度管理--编辑
	 * @author	created by rlx
	 * @date	2020-12-9--上午10:08:29
	 * @param pk
	 * @return
	 */
	@RequestMapping("/goIntProduceViewUpdate")
	public ModelAndView goIntProduceViewUpdate(Integer pk) {
		logger.debug("跳转到修改生产进度管理页面");
		IntProduce intProduce = this.intProduceService.get(IntProduce.class, pk);
		Supplier supplier = this.supplierService.get(Supplier.class, intProduce.getSupplCode());
		Customer customer = this.customerService.get(Customer.class, intProduce.getCustCode());
		return new ModelAndView("admin/project/custIntProg/custIntProg_intProduceView_update")
			.addObject("intProduce", intProduce).addObject("supplier", supplier).addObject("customer", customer);
	}
	/**
	 * 生产进度管理--编辑
	 * @author	created by rlx
	 * @date	2020-12-9--上午10:08:29
	 * @param request
	 * @param response
	 * @param intProduce
	 * @return
	 */
	@RequestMapping("/doIntProduceViewUpdate")
	public void doIntProduceViewUpdate(HttpServletRequest req, HttpServletResponse resp, IntProduce intProduce) {
		try{
			IntProduce intProduceOld = this.intProduceService.get(IntProduce.class, intProduce.getPk());
			intProduce.setActionLog("EDIT");
			intProduce.setLastUpdate(new Date());
			intProduce.setLastUpdatedBy(getUserContext(req).getCzybh());
			intProduce.setCustCode(intProduceOld.getCustCode());
			intProduce.setSupplCode(intProduceOld.getSupplCode());
			intProduce.setExpired(intProduceOld.getExpired());
			intProduce.setCustName(intProduceOld.getCustName());
			intProduce.setIsCupboard(intProduceOld.getIsCupboard());
			intProduceService.update(intProduce);
			ServletUtils.outPrintSuccess(req, resp, "保存成功");
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(req, resp, "发生异常，请重试！");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除生产进度管理的一条数据");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "pk不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					IntProduce intProduce = this.intProduceService.get(IntProduce.class, Integer.parseInt(deleteId));
					this.intProduceService.delete(intProduce);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除设计费标准失败");
		}
	}
	/**
	 * 修改CustIntProg
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doDelayAdd")
	public void doDelayAdd(HttpServletRequest request,
			HttpServletResponse response, CustIntProg custIntProg) {
		logger.debug("修改CustIntProg开始");
		try {
			custIntProgService.doDelayAdd(custIntProg);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "制单拖期录入失败");
		}
	}

	/**
	 * CustIntProg导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, CustIntProg custIntProg) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custIntProgService.findPageBySql(page, custIntProg);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"集成进度信息管理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	/**
	 * 查根据材料查询发货天数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSenddaysByMaterial")
	@ResponseBody
	public String findSenddaysByMaterial(HttpServletRequest request,
			HttpServletResponse response, String material, String itemType12)
			throws Exception {
		String senddays="0";
		try {
			senddays = custIntProgService
					.findSenddaysByMaterial(material, itemType12).get("SendDay")
					.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return senddays;
	}

	/**
	 * 判断是否已录入该客户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkRegistered")
	@ResponseBody
	public Map<String, Object> checkRegistered(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		List<Map<String, Object>> list = custIntProgService
				.checkRegistered(custCode);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() > 0) {
			map.put("result", "yes");
		}else{
			map.put("result", "no");
		}
		return map;
	}

	/**
	 * 判断该客户集成或橱柜有没有拖期
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkDelayed")
	@ResponseBody
	public Map<String, Object> checkDelayed(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		List<Map<String, Object>> list = custIntProgService
				.checkDelayed(custCode);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() > 0) {
			map.put("result", "yes");
		} else {
			map.put("result", "no");
		}
		return map;
	}
	
	/**
	 *保存
	 *
	 */
	@RequestMapping("/doSave")
	public void doReturnSave(HttpServletRequest request,HttpServletResponse response,CustIntProg custIntProg){
		logger.debug("保存");		
		try {	
			custIntProg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custIntProg.setLastUpdate(new Date());
			custIntProg.setExpired("F"); 
			String intProgDetail =request.getParameter("intProgDetailJson");
		    JSONObject jsonObject = JSON.parseObject(intProgDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  intProgDetailJson=jsonArray.toString();
			custIntProg.setIntProgDetailJson(intProgDetailJson);
			Result result = this.custIntProgService.doSaveProc(custIntProg);
			
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
}
