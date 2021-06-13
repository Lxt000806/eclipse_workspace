package com.house.home.web.controller.insales;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.insales.CutCheckOut;
import com.house.home.entity.project.WorkCost;
import com.house.home.service.insales.CutCheckOutService;

@Controller
@RequestMapping("/admin/cutCheckOut")
public class CutCheckOutController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CutCheckOutController.class);

	@Autowired
	private CutCheckOutService cutCheckOutService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, CutCheckOut cutCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cutCheckOutService.findPageBySql(page, cutCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAddDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, CutCheckOut cutCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cutCheckOutService.goAddDetailJqGrid(page, cutCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, CutCheckOut cutCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cutCheckOutService.goDetailJqGrid(page, cutCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridCheckInDtl")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCheckInDtl(HttpServletRequest request,
			HttpServletResponse response, CutCheckOut cutCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cutCheckOutService.goJqGridCheckInDtl(page, cutCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridCheckIn")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCheckIn(HttpServletRequest request,
			HttpServletResponse response, CutCheckOut cutCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cutCheckOutService.goJqGridCheckIn(page, cutCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * CutCheckOut列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_list");
	}
	/**
	 * CutCheckOut查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_code");
	}
	/**
	 * 跳转到新增CutCheckOut页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增CutCheckOut页面");
		CutCheckOut cutCheckOut = new CutCheckOut();
		Employee employee=cutCheckOutService.get(Employee.class,getUserContext(request).getEmnum());
		cutCheckOut.setStatus("0");
		cutCheckOut.setCrtCzy(employee.getNumber());
		cutCheckOut.setCrtCzyDescr(employee.getNameChi());
		cutCheckOut.setCrtDate(new Date());
		cutCheckOut.setAmount(0.0);
		cutCheckOut.setM_umState("A");
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_save")
			.addObject("cutCheckOut", cutCheckOut);
	}
	
	/**
	 * 跳转到修改CutCheckOut页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CutCheckOut页面");
		CutCheckOut cutCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			cutCheckOut = cutCheckOutService.get(CutCheckOut.class, id);
		}else{
			cutCheckOut = new CutCheckOut();
		}
		Employee employee=cutCheckOutService.get(Employee.class,cutCheckOut.getCrtCzy());
		cutCheckOut.setCrtCzyDescr(employee.getNameChi());
		cutCheckOut.setM_umState("M");
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_save")
			.addObject("cutCheckOut", cutCheckOut);
	}
	
	/**
	 * 跳转到查看CutCheckOut页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CutCheckOut页面");
		CutCheckOut cutCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			cutCheckOut = cutCheckOutService.get(CutCheckOut.class, id);
		}else{
			cutCheckOut = new CutCheckOut();
		}
		Employee employee=cutCheckOutService.get(Employee.class,cutCheckOut.getCrtCzy());
		cutCheckOut.setCrtCzyDescr(employee.getNameChi());
		cutCheckOut.setM_umState("V");
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_save")
			.addObject("cutCheckOut", cutCheckOut);
	}
	
	/**
	 * 跳转到查看CutCheckOut页面
	 * @return
	 */
	@RequestMapping("/goCheckIn")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CutCheckOut页面");
		CutCheckOut cutCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			cutCheckOut = cutCheckOutService.get(CutCheckOut.class, id);
		}else{
			cutCheckOut = new CutCheckOut();
		}
		Employee employee=cutCheckOutService.get(Employee.class,cutCheckOut.getCrtCzy());
		cutCheckOut.setCrtCzyDescr(employee.getNameChi());
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_checkIn")
			.addObject("cutCheckOut", cutCheckOut);
	}
	
	/**
	 * 跳转到新增明细页面
	 * @return
	 */
	@RequestMapping("/goAddDetail")
	public ModelAndView goAddDetail(HttpServletRequest request, HttpServletResponse response, 
			CutCheckOut cutCheckOut){
		logger.debug("跳转到新增明细页面");
		
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_addDetail")
				.addObject("cutCheckOut", cutCheckOut);
	}
	
	/**
	 * 跳转到明细查询页面
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response, 
			CutCheckOut cutCheckOut){
		logger.debug("跳转到明细查询页面");
		
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_detailQuery")
				.addObject("cutCheckOut", cutCheckOut);
	}

	/**
	 * 跳转到入库明细页面
	 * @return
	 */
	@RequestMapping("/goViewCheckIn")
	public ModelAndView goViewCheckIn(HttpServletRequest request, HttpServletResponse response, 
			CutCheckOut cutCheckOut){
		logger.debug("跳转到入库明细页面");
		
		return new ModelAndView("admin/insales/cutCheckOut/cutCheckOut_viewCheckIn")
				.addObject("cutCheckOut", cutCheckOut);
	}
	
	/**
	 * 添加CutCheckOut
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CutCheckOut cutCheckOut){
		logger.debug("添加CutCheckOut开始");
		try {	
			cutCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String ccod =request.getParameter("cutCheckOutDetailJson");
		    JSONObject jsonObject = JSON.parseObject(ccod);
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  cutCheckOutDetailJson=jsonArray.toString();
			cutCheckOut.setCutCheckOutDetailJson(cutCheckOutDetailJson);
			Result result = this.cutCheckOutService.doSaveProc(cutCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}

	/**
	 *CutCheckOut导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CutCheckOut cutCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		cutCheckOutService.findPageBySql(page, cutCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"瓷砖加工管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 *CutCheckOut导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, CutCheckOut cutCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		cutCheckOutService.goDetailJqGrid(page, cutCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 构建合并临时JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goTmpJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTmpJqGrid(HttpServletRequest request,
			HttpServletResponse response,String params,String orderBy) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		JSONArray jsonArray = JSONArray.fromObject(params); 
		@SuppressWarnings("rawtypes")
		List<Map<String,Object>> list = (List)jsonArray;
		if(orderBy!=null){
			String[] orders=orderBy.split(",");
			for(String order:orders){
				Collections.sort(list, new ListCompareUtil(order));
			}
		}
		if(page.isAutoCount()){
			page.setTotalCount(list.size());
		}
		page.setPageSize(10000);
		page.setResult(list);	
		return new WebPage<Map<String,Object>>(page);

	}
	
	/**
	 * 根据规格查切割类型和切割费
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCutTypeBySize")
	@ResponseBody
	public List<Map<String, Object>> getCutTypeBySize(HttpServletRequest request,HttpServletResponse response,CutCheckOut cutCheckOut){
		List<Map<String, Object>>list=cutCheckOutService.getCutTypeBySize(cutCheckOut);
		return list;
	}
	
	/**
	 *加工出库保存
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCheckOut")
	public void doCheckOut(HttpServletRequest request ,
			HttpServletResponse response , CutCheckOut cco){
		logger.debug("加工出库");
		try{
			CutCheckOut cutCheckOut = cutCheckOutService.get(CutCheckOut.class, cco.getNo());
			cutCheckOut.setStatus("1");
			cutCheckOut.setLastUpdate(new Date());
			cutCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh());
			cutCheckOut.setActionLog("EDIT");
			cutCheckOut.setSubmitDate(new Date());
			cutCheckOutService.update(cutCheckOut);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "加工出库失败");
		}
	}
	
	/**
	 *出库退回保存
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCheckOutReturn")
	public void doCheckOutReturn(HttpServletRequest request ,
			HttpServletResponse response , CutCheckOut cco){
		logger.debug("出库退回");
		try{
			CutCheckOut cutCheckOut = cutCheckOutService.get(CutCheckOut.class, cco.getNo());
			cutCheckOut.setStatus("0");
			cutCheckOut.setLastUpdate(new Date());
			cutCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh());
			cutCheckOut.setActionLog("EDIT");
			cutCheckOutService.update(cutCheckOut);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "出库退回失败");
		}
	}
	
	/**
	 *取消保存
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCancel")
	public void doCancel(HttpServletRequest request ,
			HttpServletResponse response , CutCheckOut cco){
		logger.debug("取消");
		try{
			CutCheckOut cutCheckOut = cutCheckOutService.get(CutCheckOut.class, cco.getNo());
			cutCheckOut.setStatus("4");
			cutCheckOut.setLastUpdate(new Date());
			cutCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh());
			cutCheckOut.setActionLog("EDIT");
			cutCheckOutService.update(cutCheckOut);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "取消失败");
		}
	}
	
	/**
	 *加工入库
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCheckIn")
	public void doCheckIn(HttpServletRequest request ,
			HttpServletResponse response , CutCheckOut cutCheckOut){
		logger.debug("加工入库");
		try{
			/*cutCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = this.cutCheckOutService.doCheckIn(cutCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"加工入库成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
			*/
			cutCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String ccod =request.getParameter("cutCheckOutDetailJson");
		    JSONObject jsonObject = JSON.parseObject(ccod);
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  cutCheckOutDetailJson=jsonArray.toString();
			cutCheckOut.setCutCheckOutDetailJson(cutCheckOutDetailJson);
			Result result = this.cutCheckOutService.doCheckIn(cutCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"加工入库成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
			
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "入库失败");
		}
	}
}
