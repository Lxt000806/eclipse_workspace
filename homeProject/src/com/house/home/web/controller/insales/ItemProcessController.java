package com.house.home.web.controller.insales;

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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.insales.ItemProcess;
import com.house.home.entity.insales.ItemProcessDetail;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.insales.ItemProcessService;
import com.house.home.service.insales.WareHouseOperaterService;
@Controller        
@RequestMapping("/admin/itemProcess")
public class ItemProcessController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemProcessController.class);

	@Autowired
	private ItemProcessService itemProcessService;
	@Autowired
	private WareHouseOperaterService wareHouseOperaterService;
	

	private void resetItemProcess(ItemProcess itemProcess){
		if (itemProcess==null){
			return;
		}
		if (StringUtils.isNotBlank(itemProcess.getProcessItemWHCode())){
			WareHouse wareHouse = itemProcessService.get(WareHouse.class, itemProcess.getProcessItemWHCode());
			if (wareHouse!=null){
				itemProcess.setProcessItemWHCodeDescr(wareHouse.getDesc1());
			}
		}
		if (StringUtils.isNotBlank(itemProcess.getSourceItemWHCode())){
			WareHouse wareHouse = itemProcessService.get(WareHouse.class, itemProcess.getSourceItemWHCode());
			if (wareHouse!=null){
				itemProcess.setSourceItemWHCodeDescr(wareHouse.getDesc1());
			}
		}
		if (StringUtils.isNotBlank(itemProcess.getAppCzy())){
			Czybm czybm = itemProcessService.get(Czybm.class, itemProcess.getAppCzy());
			if (czybm!=null){
				itemProcess.setAppCzyDescr(czybm.getZwxm());
			}
		}
		if (StringUtils.isNotBlank(itemProcess.getConfirmCzy())){
			Czybm czybm = itemProcessService.get(Czybm.class, itemProcess.getConfirmCzy());
			if (czybm!=null){
				itemProcess.setConfirmCzyDescr(czybm.getZwxm());
			}
		}	
	}

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
			HttpServletResponse response, ItemProcess itemProcess) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemProcessService.findPageBySql(page, itemProcess);
		return new WebPage<Map<String,Object>>(page);
	}
	

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemProcessDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemProcessDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemProcessDetail itemProcessDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemProcessService.findPageBySql_itemProcessDetail(page, itemProcessDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询ItemProcessSourceDetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemProcessSourceDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> findPageBySql_itemProcessSourceDetail(HttpServletRequest request,
			HttpServletResponse response, ItemProcessDetail itemProcessDetail) throws Exception {	
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemProcessService.findPageBySql_itemProcessSourceDetail(page, itemProcessDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * ItemProcess列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemProcess/itemProcess_list");
	}
	/**
	 * ItemProcess查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemProcess/itemProcess_code");
	}
	
	/**
	 * 跳转到新增ItemProcess页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			ItemProcess itemProcess){
		itemProcess.setM_umState("A");
		UserContext uc = getUserContext(request);
		itemProcess.setAppCzy(uc.getCzybh());
		itemProcess.setAppCzyDescr(uc.getZwxm());
		itemProcess.setStatus("1");
		itemProcess.setAppDate(new Date());
		List<Map<String,Object>> listWareHouse = wareHouseOperaterService.findByCzybh(uc.getCzybh());
	    if (listWareHouse.size() == 1){
	    	itemProcess.setProcessItemWHCode((String) listWareHouse.get(0).get("whcode"));
	    	itemProcess.setSourceItemWHCode((String) listWareHouse.get(0).get("whcode"));	
	    }
	    resetItemProcess(itemProcess);
		return new ModelAndView("admin/insales/itemProcess/itemProcess_save")
			.addObject("itemProcess", itemProcess)
			.addObject("czybh", uc.getCzybh());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, ItemProcess itemProcess) throws Exception {
		
		if(StringUtils.isNotBlank(itemProcess.getNo())){
			itemProcess = itemProcessService.get(ItemProcess.class, itemProcess.getNo());
		}
		itemProcess.setM_umState("M");
		resetItemProcess(itemProcess);
		return new ModelAndView("admin/insales/itemProcess/itemProcess_save")
				.addObject("itemProcess", itemProcess)
				.addObject("czybh", getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request,
			HttpServletResponse response, ItemProcess itemProcess) throws Exception {
		
		if(StringUtils.isNotBlank(itemProcess.getNo())){
			itemProcess = itemProcessService.get(ItemProcess.class, itemProcess.getNo());
		}
		
		UserContext uc= getUserContext(request);
		itemProcess.setConfirmCzy(uc.getCzybh());
		itemProcess.setConfirmDate(new Date());
		itemProcess.setM_umState("C");
		resetItemProcess(itemProcess);
		return new ModelAndView("admin/insales/itemProcess/itemProcess_save")
				.addObject("itemProcess", itemProcess)
				.addObject("czybh", uc.getCzybh());
	}
	
	/**
	 * 跳转到查看ItemProcess页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			ItemProcess itemProcess){
		if(StringUtils.isNotBlank(itemProcess.getNo())){
			itemProcess = itemProcessService.get(ItemProcess.class, itemProcess.getNo());
		}
		itemProcess.setM_umState("V");
		resetItemProcess(itemProcess);
		return new ModelAndView("admin/insales/itemProcess/itemProcess_save")
				.addObject("itemProcess", itemProcess)
				.addObject("czybh", getUserContext(request).getCzybh());
	}
	
	
	/**
	 * 跳转到新增ItemProcess页面
	 * @return
	 */
	@RequestMapping("/goDetailAdd")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			ItemProcessDetail itemProcessDetail){
		return new ModelAndView("admin/insales/itemProcess/itemProcess_detail_add")
			.addObject("itemProcessDetail", itemProcessDetail);
	}
	
	
	/**
	 * 添加ItemProcess
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemProcess itemProcess){
		logger.debug("添加ItemProcess开始");
		try {

			itemProcess.setLastUpdatedBy(this.getUserContext(request).getCzybh());

			Result result = this.itemProcessService.doSave(itemProcess);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	/**
	 * 修改ItemProcess
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemProcess itemProcess){
		logger.debug("修改ItemProcess开始");
		try{
			itemProcess.setLastUpdate(new Date());
			itemProcess.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemProcessService.update(itemProcess);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemProcess失败");
		}
	}
	

	/**
	 *ItemProcess导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemProcess itemProcess){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemProcessService.findPageBySql(page, itemProcess);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料加工信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSourceItemByTransform")
	@ResponseBody
	public WebPage<Map<String,Object>> getSourceItemByTransform(HttpServletRequest request,
			HttpServletResponse response, ItemProcessDetail itemProcessDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemProcessService.getSourceItemByTransform(page, itemProcessDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
}
