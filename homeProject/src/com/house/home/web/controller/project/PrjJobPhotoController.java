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
import com.house.home.entity.project.PrjJobPhoto;
import com.house.home.service.project.PrjJobPhotoService;

@Controller
@RequestMapping("/admin/prjJobPhoto")
public class PrjJobPhotoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjJobPhotoController.class);

	@Autowired
	private PrjJobPhotoService prjJobPhotoService;

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
			HttpServletResponse response, PrjJobPhoto prjJobPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobPhotoService.findPageBySql(page, prjJobPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrjJobPhoto列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjJobPhoto/prjJobPhoto_list");
	}
	/**
	 * PrjJobPhoto查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjJobPhoto/prjJobPhoto_code");
	}
	/**
	 * 跳转到新增PrjJobPhoto页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrjJobPhoto页面");
		PrjJobPhoto prjJobPhoto = null;
		if (StringUtils.isNotBlank(id)){
			prjJobPhoto = prjJobPhotoService.get(PrjJobPhoto.class, Integer.parseInt(id));
			prjJobPhoto.setPk(null);
		}else{
			prjJobPhoto = new PrjJobPhoto();
		}
		
		return new ModelAndView("admin/project/prjJobPhoto/prjJobPhoto_save")
			.addObject("prjJobPhoto", prjJobPhoto);
	}
	/**
	 * 跳转到修改PrjJobPhoto页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrjJobPhoto页面");
		PrjJobPhoto prjJobPhoto = null;
		if (StringUtils.isNotBlank(id)){
			prjJobPhoto = prjJobPhotoService.get(PrjJobPhoto.class, Integer.parseInt(id));
		}else{
			prjJobPhoto = new PrjJobPhoto();
		}
		
		return new ModelAndView("admin/project/prjJobPhoto/prjJobPhoto_update")
			.addObject("prjJobPhoto", prjJobPhoto);
	}
	
	/**
	 * 跳转到查看PrjJobPhoto页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrjJobPhoto页面");
		PrjJobPhoto prjJobPhoto = prjJobPhotoService.get(PrjJobPhoto.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/prjJobPhoto/prjJobPhoto_detail")
				.addObject("prjJobPhoto", prjJobPhoto);
	}
	/**
	 * 添加PrjJobPhoto
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjJobPhoto prjJobPhoto){
		logger.debug("添加PrjJobPhoto开始");
		try{
			this.prjJobPhotoService.save(prjJobPhoto);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrjJobPhoto失败");
		}
	}
	
	/**
	 * 修改PrjJobPhoto
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjJobPhoto prjJobPhoto){
		logger.debug("修改PrjJobPhoto开始");
		try{
			prjJobPhoto.setLastUpdate(new Date());
			prjJobPhoto.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjJobPhotoService.update(prjJobPhoto);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrjJobPhoto失败");
		}
	}
	
	/**
	 * 删除PrjJobPhoto
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrjJobPhoto开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrjJobPhoto编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjJobPhoto prjJobPhoto = prjJobPhotoService.get(PrjJobPhoto.class, Integer.parseInt(deleteId));
				if(prjJobPhoto == null)
					continue;
				prjJobPhoto.setExpired("T");
				prjJobPhotoService.update(prjJobPhoto);
			}
		}
		logger.debug("删除PrjJobPhoto IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PrjJobPhoto导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjJobPhoto prjJobPhoto){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjJobPhotoService.findPageBySql(page, prjJobPhoto);
	}

}
