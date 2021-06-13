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
import com.house.home.entity.insales.ItemPreAppDetail;
import com.house.home.service.insales.ItemPreAppDetailService;

@Controller
@RequestMapping("/admin/itemPreAppDetail")
public class ItemPreAppDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPreAppDetailController.class);

	@Autowired
	private ItemPreAppDetailService itemPreAppDetailService;

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
			HttpServletResponse response, ItemPreAppDetail itemPreAppDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppDetailService.findPageBySql(page, itemPreAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemPreAppDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemPreAppDetail/itemPreAppDetail_list");
	}
	/**
	 * ItemPreAppDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemPreAppDetail/itemPreAppDetail_code");
	}
	/**
	 * 跳转到新增ItemPreAppDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPreAppDetail页面");
		ItemPreAppDetail itemPreAppDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemPreAppDetail = itemPreAppDetailService.get(ItemPreAppDetail.class, Integer.parseInt(id));
			itemPreAppDetail.setPk(null);
		}else{
			itemPreAppDetail = new ItemPreAppDetail();
		}
		
		return new ModelAndView("admin/insales/itemPreAppDetail/itemPreAppDetail_save")
			.addObject("itemPreAppDetail", itemPreAppDetail);
	}
	/**
	 * 跳转到修改ItemPreAppDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPreAppDetail页面");
		ItemPreAppDetail itemPreAppDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemPreAppDetail = itemPreAppDetailService.get(ItemPreAppDetail.class, Integer.parseInt(id));
		}else{
			itemPreAppDetail = new ItemPreAppDetail();
		}
		
		return new ModelAndView("admin/insales/itemPreAppDetail/itemPreAppDetail_update")
			.addObject("itemPreAppDetail", itemPreAppDetail);
	}
	
	/**
	 * 跳转到查看ItemPreAppDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPreAppDetail页面");
		ItemPreAppDetail itemPreAppDetail = itemPreAppDetailService.get(ItemPreAppDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/itemPreAppDetail/itemPreAppDetail_detail")
				.addObject("itemPreAppDetail", itemPreAppDetail);
	}
	/**
	 * 添加ItemPreAppDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemPreAppDetail itemPreAppDetail){
		logger.debug("添加ItemPreAppDetail开始");
		try{
			this.itemPreAppDetailService.save(itemPreAppDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemPreAppDetail失败");
		}
	}
	
	/**
	 * 修改ItemPreAppDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPreAppDetail itemPreAppDetail){
		logger.debug("修改ItemPreAppDetail开始");
		try{
			itemPreAppDetail.setLastUpdate(new Date());
			itemPreAppDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPreAppDetailService.update(itemPreAppDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPreAppDetail失败");
		}
	}
	
	/**
	 * 删除ItemPreAppDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPreAppDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPreAppDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPreAppDetail itemPreAppDetail = itemPreAppDetailService.get(ItemPreAppDetail.class, Integer.parseInt(deleteId));
				if(itemPreAppDetail == null)
					continue;
				itemPreAppDetail.setExpired("T");
				itemPreAppDetailService.update(itemPreAppDetail);
			}
		}
		logger.debug("删除ItemPreAppDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemPreAppDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPreAppDetail itemPreAppDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPreAppDetailService.findPageBySql(page, itemPreAppDetail);
	}

}
