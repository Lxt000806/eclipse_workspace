package com.house.home.web.controller.project;

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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.SoftInstall;
import com.house.home.service.project.SoftInstallService;

@Controller
@RequestMapping("/admin/softInstall")
public class SoftInstallController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SoftInstallController.class);

	@Autowired
	private SoftInstallService softInstallService;

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
			HttpServletResponse response, SoftInstall softInstall) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softInstallService.findPageBySql(page, softInstall);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SoftInstall列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/softInstall/softInstall_list");
	}
	/**
	 * SoftInstall查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/softInstall/softInstall_code");
	}
	/**
	 * 跳转到新增SoftInstall页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SoftInstall页面");
		SoftInstall softInstall = null;
		if (StringUtils.isNotBlank(id)){
			softInstall = softInstallService.get(SoftInstall.class, Integer.parseInt(id));
			softInstall.setPk(null);
		}else{
			softInstall = new SoftInstall();
		}
		
		return new ModelAndView("admin/project/softInstall/softInstall_save")
			.addObject("softInstall", softInstall);
	}
	/**
	 * 跳转到修改SoftInstall页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SoftInstall页面");
		SoftInstall softInstall = null;
		if (StringUtils.isNotBlank(id)){
			softInstall = softInstallService.get(SoftInstall.class, Integer.parseInt(id));
		}else{
			softInstall = new SoftInstall();
		}
		
		return new ModelAndView("admin/project/softInstall/softInstall_update")
			.addObject("softInstall", softInstall);
	}
	
	/**
	 * 跳转到查看SoftInstall页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SoftInstall页面");
		SoftInstall softInstall = softInstallService.get(SoftInstall.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/softInstall/softInstall_detail")
				.addObject("softInstall", softInstall);
	}
	/**
	 * 添加SoftInstall
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SoftInstall softInstall){
		logger.debug("添加SoftInstall开始");
		try{
			this.softInstallService.save(softInstall);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SoftInstall失败");
		}
	}
	
	/**
	 * 修改SoftInstall
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SoftInstall softInstall){
		logger.debug("修改SoftInstall开始");
		try{
			softInstall.setLastUpdate(new Date());
			softInstall.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.softInstallService.update(softInstall);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SoftInstall失败");
		}
	}
	
	/**
	 * 删除SoftInstall
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SoftInstall开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SoftInstall编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SoftInstall softInstall = softInstallService.get(SoftInstall.class, Integer.parseInt(deleteId));
				if(softInstall == null)
					continue;
				softInstall.setExpired("T");
				softInstallService.update(softInstall);
			}
		}
		logger.debug("删除SoftInstall IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SoftInstall导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SoftInstall softInstall){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		softInstallService.findPageBySql(page, softInstall);
	}

}
