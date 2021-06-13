package com.house.home.web.controller.finance;

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
import com.house.home.entity.finance.ManualPerfCust;
import com.house.home.service.finance.ManualPerfCustService;

@Controller
@RequestMapping("/admin/manualPerfCust")
public class ManualPerfCustController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ManualPerfCustController.class);

	@Autowired
	private ManualPerfCustService manualPerfCustService;

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
			HttpServletResponse response, ManualPerfCust manualPerfCust) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		manualPerfCustService.findPageBySql(page, manualPerfCust);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ManualPerfCust列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/manualPerfCust/manualPerfCust_list");
	}
	/**
	 * ManualPerfCust查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/manualPerfCust/manualPerfCust_code");
	}
	/**
	 * 跳转到新增ManualPerfCust页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ManualPerfCust页面");
		ManualPerfCust manualPerfCust = null;
		if (StringUtils.isNotBlank(id)){
			manualPerfCust = manualPerfCustService.get(ManualPerfCust.class, Integer.parseInt(id));
			manualPerfCust.setPk(null);
		}else{
			manualPerfCust = new ManualPerfCust();
		}
		
		return new ModelAndView("admin/finance/manualPerfCust/manualPerfCust_save")
			.addObject("manualPerfCust", manualPerfCust);
	}
	/**
	 * 跳转到修改ManualPerfCust页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ManualPerfCust页面");
		ManualPerfCust manualPerfCust = null;
		if (StringUtils.isNotBlank(id)){
			manualPerfCust = manualPerfCustService.get(ManualPerfCust.class, Integer.parseInt(id));
		}else{
			manualPerfCust = new ManualPerfCust();
		}
		
		return new ModelAndView("admin/finance/manualPerfCust/manualPerfCust_update")
			.addObject("manualPerfCust", manualPerfCust);
	}
	
	/**
	 * 跳转到查看ManualPerfCust页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ManualPerfCust页面");
		ManualPerfCust manualPerfCust = manualPerfCustService.get(ManualPerfCust.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/finance/manualPerfCust/manualPerfCust_detail")
				.addObject("manualPerfCust", manualPerfCust);
	}
	/**
	 * 添加ManualPerfCust
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ManualPerfCust manualPerfCust){
		logger.debug("添加ManualPerfCust开始");
		try{
			manualPerfCust.setExpired("F");
			manualPerfCust.setActionLog("ADD");
			manualPerfCust.setLastUpdate(new Date());
			manualPerfCust.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.manualPerfCustService.save(manualPerfCust);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ManualPerfCust失败");
		}
	}
	
	/**
	 * 修改ManualPerfCust
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ManualPerfCust manualPerfCust){
		logger.debug("修改ManualPerfCust开始");
		try{
			manualPerfCust.setLastUpdate(new Date());
			manualPerfCust.setLastUpdatedBy(getUserContext(request).getCzybh());
			manualPerfCust.setActionLog("EDIT");
			manualPerfCust.setExpired("F");
			this.manualPerfCustService.update(manualPerfCust);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改ManualPerfCust失败");
		}
	}
	
	/**
	 * 删除ManualPerfCust
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ManualPerfCust开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ManualPerfCust编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ManualPerfCust manualPerfCust = manualPerfCustService.get(ManualPerfCust.class, Integer.parseInt(deleteId));
				if(manualPerfCust == null)
					continue;
				manualPerfCustService.delete(manualPerfCust);
			}
		}
		logger.debug("删除ManualPerfCust IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ManualPerfCust导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ManualPerfCust manualPerfCust){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		manualPerfCustService.findPageBySql(page, manualPerfCust);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ManualPerfCust_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
