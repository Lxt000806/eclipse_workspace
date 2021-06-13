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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Version;
import com.house.home.service.basic.VersionService;

@Controller
@RequestMapping("/admin/version")
public class VersionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(VersionController.class);

	@Autowired
	private VersionService versionService;
	
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
			HttpServletResponse response,Version version) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		versionService.findPageBySql(page, version);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Version列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/version/version_list");
	}
	
	/**
	 * 跳转到新增Version页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增Version页面");
		Version version = null;
		if (StringUtils.isNotBlank(id)){
			version = versionService.get(Version.class, id);
			version.setName(null);
		}else{
			version = new Version();
		}
		
		return new ModelAndView("admin/basic/version/version_save")
			.addObject("version", version);
	}
	/**
	 * 跳转到修改Version页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Version页面");
		Version version = null;
		if (StringUtils.isNotBlank(id)){
			version = versionService.get(Version.class, id);
		}else{
			version = new Version();
		}
		
		return new ModelAndView("admin/basic/version/version_update")
			.addObject("version", version);
	}
	
	/**
	 * 跳转到查看Version页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Version页面");
		Version version = versionService.get(Version.class, id);
		
		return new ModelAndView("admin/basic/version/version_detail")
				.addObject("version", version);
	}
	/**
	 * 添加Version
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Version version){
		logger.debug("添加Version开始");
		try{
			Version xt = this.versionService.get(Version.class, version.getName());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "Version重复");
				return;
			}
			String str = versionService.getSeqNo("tVersion");
			version.setName(str);
			this.versionService.save(version);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加Version失败");
		}
	}
	
	/**
	 * 修改Version
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Version version){
		logger.debug("修改Version开始");
		try{
			Version xt = this.versionService.get(Version.class, version.getName());
			if (xt!=null){
				this.versionService.update(version);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.versionService.save(version);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Version失败");
		}
	}
	
	/**
	 * 删除Version
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Version开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Version编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Version version = versionService.get(Version.class, deleteId);
				if(version == null)
					continue;
//				version.setExpired("T");
				versionService.update(version);
			}
		}
		logger.debug("删除Version IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Version导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Version version){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		versionService.findPageBySql(page, version);
	}

}
