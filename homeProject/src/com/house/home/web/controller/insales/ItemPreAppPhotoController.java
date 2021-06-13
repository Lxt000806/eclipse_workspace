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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.ItemPreAppPhoto;
import com.house.home.service.insales.ItemPreAppPhotoService;

@Controller
@RequestMapping("/admin/itemPreAppPhoto")
public class ItemPreAppPhotoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPreAppPhotoController.class);

	@Autowired
	private ItemPreAppPhotoService itemPreAppPhotoService;

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
			HttpServletResponse response, ItemPreAppPhoto itemPreAppPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppPhotoService.findPageBySql(page, itemPreAppPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemPreAppPhoto列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemPreAppPhoto/itemPreAppPhoto_list");
	}
	/**
	 * ItemPreAppPhoto查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemPreAppPhoto/itemPreAppPhoto_code");
	}
	/**
	 * 跳转到新增ItemPreAppPhoto页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPreAppPhoto页面");
		ItemPreAppPhoto itemPreAppPhoto = null;
		if (StringUtils.isNotBlank(id)){
			itemPreAppPhoto = itemPreAppPhotoService.get(ItemPreAppPhoto.class, Integer.parseInt(id));
			itemPreAppPhoto.setPk(null);
		}else{
			itemPreAppPhoto = new ItemPreAppPhoto();
		}
		
		return new ModelAndView("admin/insales/itemPreAppPhoto/itemPreAppPhoto_save")
			.addObject("itemPreAppPhoto", itemPreAppPhoto);
	}
	/**
	 * 跳转到修改ItemPreAppPhoto页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPreAppPhoto页面");
		ItemPreAppPhoto itemPreAppPhoto = null;
		if (StringUtils.isNotBlank(id)){
			itemPreAppPhoto = itemPreAppPhotoService.get(ItemPreAppPhoto.class, Integer.parseInt(id));
		}else{
			itemPreAppPhoto = new ItemPreAppPhoto();
		}
		
		return new ModelAndView("admin/insales/itemPreAppPhoto/itemPreAppPhoto_update")
			.addObject("itemPreAppPhoto", itemPreAppPhoto);
	}
	
	/**
	 * 跳转到查看ItemPreAppPhoto页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPreAppPhoto页面");
		ItemPreAppPhoto itemPreAppPhoto = itemPreAppPhotoService.get(ItemPreAppPhoto.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/itemPreAppPhoto/itemPreAppPhoto_detail")
				.addObject("itemPreAppPhoto", itemPreAppPhoto);
	}
	/**
	 * 添加ItemPreAppPhoto
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemPreAppPhoto itemPreAppPhoto){
		logger.debug("添加ItemPreAppPhoto开始");
		try{
			this.itemPreAppPhotoService.save(itemPreAppPhoto);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemPreAppPhoto失败");
		}
	}
	
	/**
	 * 修改ItemPreAppPhoto
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPreAppPhoto itemPreAppPhoto){
		logger.debug("修改ItemPreAppPhoto开始");
		try{
			itemPreAppPhoto.setLastUpdate(new Date());
			itemPreAppPhoto.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPreAppPhotoService.update(itemPreAppPhoto);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPreAppPhoto失败");
		}
	}
	
	/**
	 * 删除ItemPreAppPhoto
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPreAppPhoto开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPreAppPhoto编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPreAppPhoto itemPreAppPhoto = itemPreAppPhotoService.get(ItemPreAppPhoto.class, Integer.parseInt(deleteId));
				if(itemPreAppPhoto == null)
					continue;
				itemPreAppPhoto.setExpired("T");
				itemPreAppPhotoService.update(itemPreAppPhoto);
			}
		}
		logger.debug("删除ItemPreAppPhoto IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemPreAppPhoto导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPreAppPhoto itemPreAppPhoto){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPreAppPhotoService.findPageBySql(page, itemPreAppPhoto);
	}

}
