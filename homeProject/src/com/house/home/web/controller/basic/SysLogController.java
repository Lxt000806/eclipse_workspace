package com.house.home.web.controller.basic;

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
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.bean.WebPage;
import com.house.home.entity.basic.SysLog;
import com.house.home.service.basic.SysLogService;

@Controller
@RequestMapping("/admin/sysLog")
public class SysLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysLogController.class);

	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param sysLog
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,SysLog sysLog) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sysLogService.findPageBySql(page, sysLog);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getAppJqGrid(HttpServletRequest request,
			HttpServletResponse response,SysLog sysLog) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sysLogService.findAppPageBySql(page, sysLog);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * SysLog列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sysLog/sysLog_list");
	}
	
	/**
	 * 跳转到新增SysLog页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SysLog页面");
		SysLog sysLog = null;
		if (StringUtils.isNotBlank(id)){
			sysLog = sysLogService.get(SysLog.class, Integer.parseInt(id));
			sysLog.setId(null);
		}else{
			sysLog = new SysLog();
		}
		
		return new ModelAndView("admin/basic/sysLog/sysLog_save")
			.addObject("sysLog", sysLog);
	}
	/**
	 * 跳转到修改SysLog页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SysLog页面");
		SysLog sysLog = null;
		if (StringUtils.isNotBlank(id)){
			sysLog = sysLogService.get(SysLog.class, Integer.parseInt(id));
		}else{
			sysLog = new SysLog();
		}
		
		return new ModelAndView("admin/basic/sysLog/sysLog_update")
			.addObject("sysLog", sysLog);
	}
	
	/**
	 * 跳转到查看SysLog页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SysLog页面");
		SysLog sysLog = sysLogService.get(SysLog.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/sysLog/sysLog_detail")
				.addObject("sysLog", sysLog);
	}
	/**
	 * 添加SysLog
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SysLog sysLog){
		logger.debug("添加SysLog开始");
		try{
			SysLog xt = this.sysLogService.get(SysLog.class, sysLog.getId());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "SysLog重复");
				return;
			}
			this.sysLogService.save(sysLog);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SysLog失败");
		}
	}
	
	/**
	 * 修改SysLog
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SysLog sysLog){
		logger.debug("修改SysLog开始");
		try{
			SysLog xt = this.sysLogService.get(SysLog.class, sysLog.getId());
			if (xt!=null){
				this.sysLogService.update(sysLog);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.sysLogService.save(sysLog);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SysLog失败");
		}
	}
	
	/**
	 * 删除SysLog
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SysLog开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SysLog编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SysLog sysLog = sysLogService.get(SysLog.class, Integer.parseInt(deleteId));
				if(sysLog == null)
					continue;
				sysLogService.update(sysLog);
			}
		}
		logger.debug("删除SysLog IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SysLog导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SysLog sysLog){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		sysLogService.findPageBySql(page, sysLog);
	}

}
