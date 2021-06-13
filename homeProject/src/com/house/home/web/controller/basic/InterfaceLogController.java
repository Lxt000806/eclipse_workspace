package com.house.home.web.controller.basic;

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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.InterfaceLogService;

@Controller
@RequestMapping("/admin/interfaceLog")
public class InterfaceLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceLogController.class);

	@Autowired
	private InterfaceLogService interfaceLogService;
	
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
			HttpServletResponse response,InterfaceLog interfaceLog) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		interfaceLogService.findPageBySql(page, interfaceLog);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * InterfaceLog列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/interfaceLog/interfaceLog_list");
	}
	
	/**
	 * 跳转到新增InterfaceLog页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增InterfaceLog页面");
		InterfaceLog interfaceLog = null;
		if (StringUtils.isNotBlank(id)){
			interfaceLog = interfaceLogService.get(InterfaceLog.class, Integer.parseInt(id));
			interfaceLog.setId(null);
		}else{
			interfaceLog = new InterfaceLog();
		}
		
		return new ModelAndView("admin/basic/interfaceLog/interfaceLog_save")
			.addObject("interfaceLog", interfaceLog);
	}
	/**
	 * 跳转到修改InterfaceLog页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改InterfaceLog页面");
		InterfaceLog interfaceLog = null;
		if (StringUtils.isNotBlank(id)){
			interfaceLog = interfaceLogService.get(InterfaceLog.class, Integer.parseInt(id));
		}else{
			interfaceLog = new InterfaceLog();
		}
		
		return new ModelAndView("admin/basic/interfaceLog/interfaceLog_update")
			.addObject("interfaceLog", interfaceLog);
	}
	
	/**
	 * 跳转到查看InterfaceLog页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看InterfaceLog页面");
		InterfaceLog interfaceLog = interfaceLogService.get(InterfaceLog.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/interfaceLog/interfaceLog_detail")
				.addObject("interfaceLog", interfaceLog);
	}
	/**
	 * 添加InterfaceLog
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, InterfaceLog interfaceLog){
		logger.debug("添加InterfaceLog开始");
		try{
			InterfaceLog xt = this.interfaceLogService.get(InterfaceLog.class, interfaceLog.getId());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "InterfaceLog重复");
				return;
			}
			this.interfaceLogService.save(interfaceLog);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加InterfaceLog失败");
		}
	}
	
	/**
	 * 修改InterfaceLog
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, InterfaceLog interfaceLog){
		logger.debug("修改InterfaceLog开始");
		try{
			InterfaceLog xt = this.interfaceLogService.get(InterfaceLog.class, interfaceLog.getId());
			if (xt!=null){
				this.interfaceLogService.update(interfaceLog);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.interfaceLogService.save(interfaceLog);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改InterfaceLog失败");
		}
	}
	
	/**
	 * 删除InterfaceLog
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除InterfaceLog开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "InterfaceLog编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				InterfaceLog interfaceLog = interfaceLogService.get(InterfaceLog.class, Integer.parseInt(deleteId));
				if(interfaceLog == null)
					continue;
				interfaceLogService.update(interfaceLog);
			}
		}
		logger.debug("删除InterfaceLog IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *InterfaceLog导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, InterfaceLog interfaceLog){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		interfaceLogService.findPageBySql(page, interfaceLog);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"InterfaceLog_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
