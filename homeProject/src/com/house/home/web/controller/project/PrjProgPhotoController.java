package com.house.home.web.controller.project;

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
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.service.project.PrjProgPhotoService;

@Controller
@RequestMapping("/admin/prjProgPhoto")
public class PrjProgPhotoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjProgPhotoController.class);

	@Autowired
	private PrjProgPhotoService prjProgPhotoService;
	
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
			HttpServletResponse response,PrjProgPhoto prjProgPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgPhotoService.findPageBySql(page, prjProgPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPrjJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgPhoto prjProgPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgPhotoService.findPrjPageBySql(page, prjProgPhoto);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	@RequestMapping("/goXjPrjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getXjPrjJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgPhoto prjProgPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgPhotoService.findXjPrjPageBySql(page, prjProgPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goYsPrjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getYsPrjJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgPhoto prjProgPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgPhotoService.findYsPrjPageBySql(page, prjProgPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrjProgPhotoJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPrjProgPhotoJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgPhoto prjProgPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgPhotoService.findPrjProgPhotoPageBySql(page, prjProgPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询查看施工图片表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConstructionPictureJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getConstructionPictureJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgPhoto prjProgPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgPhotoService.findConstructionPicturePageBySql(page, prjProgPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * PrjProgPhoto列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjProgPhoto/prjProgPhoto_list");
	}
	
	/**
	 * 跳转到新增PrjProgPhoto页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrjProgPhoto页面");
		PrjProgPhoto prjProgPhoto = null;
		if (StringUtils.isNotBlank(id)){
			prjProgPhoto = prjProgPhotoService.get(PrjProgPhoto.class, Integer.parseInt(id));
			prjProgPhoto.setPk(null);
		}else{
			prjProgPhoto = new PrjProgPhoto();
		}
		
		return new ModelAndView("admin/project/prjProgPhoto/prjProgPhoto_save")
			.addObject("prjProgPhoto", prjProgPhoto);
	}
	/**
	 * 跳转到修改PrjProgPhoto页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrjProgPhoto页面");
		PrjProgPhoto prjProgPhoto = null;
		if (StringUtils.isNotBlank(id)){
			prjProgPhoto = prjProgPhotoService.get(PrjProgPhoto.class, Integer.parseInt(id));
		}else{
			prjProgPhoto = new PrjProgPhoto();
		}
		
		return new ModelAndView("admin/project/prjProgPhoto/prjProgPhoto_update")
			.addObject("prjProgPhoto", prjProgPhoto);
	}
	
	/**
	 * 跳转到查看PrjProgPhoto页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrjProgPhoto页面");
		PrjProgPhoto prjProgPhoto = prjProgPhotoService.get(PrjProgPhoto.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/prjProgPhoto/prjProgPhoto_detail")
				.addObject("prjProgPhoto", prjProgPhoto);
	}
	/**
	 * 添加PrjProgPhoto
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjProgPhoto prjProgPhoto){
		logger.debug("添加PrjProgPhoto开始");
		try{
			PrjProgPhoto xt = this.prjProgPhotoService.get(PrjProgPhoto.class, prjProgPhoto.getPk());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "PrjProgPhoto重复");
				return;
			}
			this.prjProgPhotoService.save(prjProgPhoto);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrjProgPhoto失败");
		}
	}
	
	/**
	 * 修改PrjProgPhoto
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjProgPhoto prjProgPhoto){
		logger.debug("修改PrjProgPhoto开始");
		try{
			PrjProgPhoto xt = this.prjProgPhotoService.get(PrjProgPhoto.class, prjProgPhoto.getPk());
			if (xt!=null){
				this.prjProgPhotoService.update(prjProgPhoto);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.prjProgPhotoService.save(prjProgPhoto);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrjProgPhoto失败");
		}
	}
	
	/**
	 * 删除PrjProgPhoto
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrjProgPhoto开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrjProgPhoto编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjProgPhoto prjProgPhoto = prjProgPhotoService.get(PrjProgPhoto.class, Integer.parseInt(deleteId));
				if(prjProgPhoto == null)
					continue;
				prjProgPhoto.setExpired("T");
				prjProgPhotoService.update(prjProgPhoto);
			}
		}
		logger.debug("删除PrjProgPhoto IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PrjProgPhoto导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjProgPhoto prjProgPhoto){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjProgPhotoService.findPageBySql(page, prjProgPhoto);
	}

}
