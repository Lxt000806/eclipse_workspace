package com.house.home.web.controller.commi;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.commi.CommiCycle;
import com.house.home.entity.commi.ItemCommiCycle;
import com.house.home.service.commi.ItemCommiCycleService;

@Controller
@RequestMapping("/admin/itemCommiCycle")
public class ItemCommiCycleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemCommiCycleController.class);

	@Autowired
	private ItemCommiCycleService itemCommiCycleService;

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
			HttpServletResponse response, ItemCommiCycle itemCommiCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemCommiCycleService.findPageBySql(page, itemCommiCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemCommiCycle列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/itemCommiCycle/itemCommiCycle_list");
	}
	/**
	 * ItemCommiCycle查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/itemCommiCycle/itemCommiCycle_code");
	}
	
	/**
	 * 跳转到计算页面
	 * @return
	 */
	@RequestMapping("/goCount")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String no,String m_umState){
		logger.debug("跳转到计算页面");
		ItemCommiCycle itemCommiCycle = itemCommiCycleService.get(ItemCommiCycle.class, no);
		itemCommiCycle.setM_umState(m_umState);
		return new ModelAndView("admin/commi/itemCommiCycle/itemCommiCycle_count")
				.addObject("itemCommiCycle", itemCommiCycle);
	}
	
	/**
	 * 跳转到新增ItemCommiCycle页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemCommiCycle页面");
		ItemCommiCycle itemCommiCycle = null;
		if (StringUtils.isNotBlank(id)){
			itemCommiCycle = itemCommiCycleService.get(ItemCommiCycle.class, id);
			itemCommiCycle.setNo(null);
		}else{
			itemCommiCycle = new ItemCommiCycle();
		}
		itemCommiCycle.setM_umState("A");
		return new ModelAndView("admin/commi/itemCommiCycle/itemCommiCycle_save")
			.addObject("itemCommiCycle", itemCommiCycle);
	}
	/**
	 * 跳转到修改ItemCommiCycle页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemCommiCycle页面");
		ItemCommiCycle itemCommiCycle = null;
		if (StringUtils.isNotBlank(id)){
			itemCommiCycle = itemCommiCycleService.get(ItemCommiCycle.class, id);
			itemCommiCycle.setM_umState("M");
		}else{
			itemCommiCycle = new ItemCommiCycle();
		}
		
		return new ModelAndView("admin/commi/itemCommiCycle/itemCommiCycle_update")
			.addObject("itemCommiCycle", itemCommiCycle);
	}
	
	/**
	 * 跳转到查看ItemCommiCycle页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemCommiCycle页面");
		ItemCommiCycle itemCommiCycle = itemCommiCycleService.get(ItemCommiCycle.class, id);
		itemCommiCycle.setM_umState("V");
		return new ModelAndView("admin/commi/itemCommiCycle/itemCommiCycle_count")
				.addObject("itemCommiCycle", itemCommiCycle);
	}
	/**
	 * 添加ItemCommiCycle
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemCommiCycle itemCommiCycle){
		logger.debug("添加ItemCommiCycle开始");
		try{
			String str = itemCommiCycleService.getSeqNo("tItemCommiCycle");
			itemCommiCycle.setNo(str);
			itemCommiCycle.setLastUpdate(new Date());
			itemCommiCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemCommiCycle.setExpired("F");
			itemCommiCycle.setActionLog("ADD");
			this.itemCommiCycleService.save(itemCommiCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemCommiCycle失败");
		}
	}
	
	/**
	 * 修改ItemCommiCycle
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemCommiCycle itemCommiCycle){
		logger.debug("修改ItemCommiCycle开始");
		try{
			itemCommiCycle.setExpired("F");
			itemCommiCycle.setLastUpdate(new Date());
			itemCommiCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemCommiCycle.setActionLog("EDIT");
			this.itemCommiCycleService.update(itemCommiCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemCommiCycle失败");
		}
	}

	/**
	 *ItemCommiCycle导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemCommiCycle itemCommiCycle){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemCommiCycleService.findPageBySql(page, itemCommiCycle);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料独立销售提成计算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查状态
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkStatus")
	public String checkStatus(HttpServletRequest request,
			HttpServletResponse response, String no) {
		logger.debug("提成计算查状态");
		return itemCommiCycleService.checkStatus(no);
	}
	/**
	 * 检查周期
	 * @param request
	 * @param response
	 * @param no
	 * @param beginDate
	 * @return
	 */
	@RequestMapping("/isExistsPeriod")
	@ResponseBody
	public String isExistsPeriod(HttpServletRequest request,HttpServletResponse response,
			String no,String beginDate){
	
		return itemCommiCycleService.isExistsPeriod(no,beginDate);
	}
	
	/**
	 * 计算完成
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doComplete")
	public void doComplete(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("提成计算完成");
		this.itemCommiCycleService.doComplete(no);
		ServletUtils.outPrintSuccess(request, response,"计算完成");
	}
	
	/**
	 * 退回
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("提成计算退回");
		this.itemCommiCycleService.doReturn(no);
		ServletUtils.outPrintSuccess(request, response,"退回成功");
	}
	
	/**
	 * 生成提成数据
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCount")
	public void doCount(HttpServletRequest request, HttpServletResponse response,ItemCommiCycle itemCommiCycle){
		logger.debug("生成提成数据");
		itemCommiCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
		Map<String, Object>resultMap=this.itemCommiCycleService.doCount(itemCommiCycle);
		ServletUtils.outPrintSuccess(request, response, resultMap);
	}
}
