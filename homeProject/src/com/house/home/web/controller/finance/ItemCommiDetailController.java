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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.finance.ItemCommiDetail;
import com.house.home.service.finance.ItemCommiDetailService;

@Controller
@RequestMapping("/admin/itemCommiDetail")
public class ItemCommiDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemCommiDetailController.class);

	@Autowired
	private ItemCommiDetailService itemCommiDetailService;

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
			HttpServletResponse response, ItemCommiDetail itemCommiDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemCommiDetailService.findPageBySql(page, itemCommiDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemCommiDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/itemCommiDetail/itemCommiDetail_list");
	}
	/**
	 * ItemCommiDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/itemCommiDetail/itemCommiDetail_code");
	}
	/**
	 * 跳转到新增ItemCommiDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemCommiDetail页面");
		ItemCommiDetail itemCommiDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemCommiDetail = itemCommiDetailService.get(ItemCommiDetail.class, Integer.parseInt(id));
			itemCommiDetail.setPk(null);
		}else{
			itemCommiDetail = new ItemCommiDetail();
		}
		
		return new ModelAndView("admin/finance/itemCommiDetail/itemCommiDetail_save")
			.addObject("itemCommiDetail", itemCommiDetail);
	}
	/**
	 * 跳转到修改ItemCommiDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemCommiDetail页面");
		ItemCommiDetail itemCommiDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemCommiDetail = itemCommiDetailService.get(ItemCommiDetail.class, Integer.parseInt(id));
		}else{
			itemCommiDetail = new ItemCommiDetail();
		}
		
		return new ModelAndView("admin/finance/itemCommiDetail/itemCommiDetail_update")
			.addObject("itemCommiDetail", itemCommiDetail);
	}
	
	/**
	 * 跳转到查看ItemCommiDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemCommiDetail页面");
		ItemCommiDetail itemCommiDetail = itemCommiDetailService.get(ItemCommiDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/finance/itemCommiDetail/itemCommiDetail_detail")
				.addObject("itemCommiDetail", itemCommiDetail);
	}
	/**
	 * 添加ItemCommiDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemCommiDetail itemCommiDetail){
		logger.debug("添加ItemCommiDetail开始");
		try{
			this.itemCommiDetailService.save(itemCommiDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemCommiDetail失败");
		}
	}
	
	/**
	 * 修改ItemCommiDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemCommiDetail itemCommiDetail){
		logger.debug("修改ItemCommiDetail开始");
		try{
			itemCommiDetail.setLastUpdate(new Date());
			itemCommiDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemCommiDetailService.update(itemCommiDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemCommiDetail失败");
		}
	}
	
	/**
	 * 删除ItemCommiDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemCommiDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemCommiDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemCommiDetail itemCommiDetail = itemCommiDetailService.get(ItemCommiDetail.class, Integer.parseInt(deleteId));
				if(itemCommiDetail == null)
					continue;
				itemCommiDetail.setExpired("T");
				itemCommiDetailService.update(itemCommiDetail);
			}
		}
		logger.debug("删除ItemCommiDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemCommiDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemCommiDetail itemCommiDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemCommiDetailService.findPageBySql(page, itemCommiDetail);
	}

}
