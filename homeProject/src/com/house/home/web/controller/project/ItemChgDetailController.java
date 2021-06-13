package com.house.home.web.controller.project;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.ItemChgDetail;
import com.house.home.service.project.ItemChgDetailService;
import com.house.home.bean.project.ItemChgDetailBean;

@Controller
@RequestMapping("/admin/itemChgDetail")
public class ItemChgDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemChgDetailController.class);

	@Autowired
	private ItemChgDetailService itemChgDetailService;
	
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
			HttpServletResponse response,ItemChgDetail itemChgDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChgDetailService.findPageBySql(page, itemChgDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemChgDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemChgDetail/itemChgDetail_list");
	}
	
	/**
	 * 跳转到新增ItemChgDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemChgDetail页面");
		ItemChgDetail itemChgDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemChgDetail = itemChgDetailService.get(ItemChgDetail.class, Integer.parseInt(id));
			itemChgDetail.setPk(null);
		}else{
			itemChgDetail = new ItemChgDetail();
		}
		
		return new ModelAndView("admin/project/itemChgDetail/itemChgDetail_save")
			.addObject("itemChgDetail", itemChgDetail);
	}
	/**
	 * 跳转到修改ItemChgDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemChgDetail页面");
		ItemChgDetail itemChgDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemChgDetail = itemChgDetailService.get(ItemChgDetail.class, Integer.parseInt(id));
		}else{
			itemChgDetail = new ItemChgDetail();
		}
		
		return new ModelAndView("admin/project/itemChgDetail/itemChgDetail_update")
			.addObject("itemChgDetail", itemChgDetail);
	}
	
	/**
	 * 跳转到查看ItemChgDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemChgDetail页面");
		ItemChgDetail itemChgDetail = itemChgDetailService.get(ItemChgDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/itemChgDetail/itemChgDetail_detail")
				.addObject("itemChgDetail", itemChgDetail);
	}
	/**
	 * 添加ItemChgDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemChgDetail itemChgDetail){
		logger.debug("添加ItemChgDetail开始");
		try{
			ItemChgDetail xt = this.itemChgDetailService.get(ItemChgDetail.class, itemChgDetail.getPk());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "ItemChgDetail重复");
				return;
			}
			this.itemChgDetailService.save(itemChgDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemChgDetail失败");
		}
	}
	
	/**
	 * 修改ItemChgDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemChgDetail itemChgDetail){
		logger.debug("修改ItemChgDetail开始");
		try{
			ItemChgDetail xt = this.itemChgDetailService.get(ItemChgDetail.class, itemChgDetail.getPk());
			if (xt!=null){
				this.itemChgDetailService.update(itemChgDetail);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.itemChgDetailService.save(itemChgDetail);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemChgDetail失败");
		}
	}
	
	/**
	 * 删除ItemChgDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemChgDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemChgDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemChgDetail itemChgDetail = itemChgDetailService.get(ItemChgDetail.class, Integer.parseInt(deleteId));
				if(itemChgDetail == null)
					continue;
				itemChgDetail.setExpired("T");
				itemChgDetailService.update(itemChgDetail);
			}
		}
		logger.debug("删除ItemChgDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemChgDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemChgDetail itemChgDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemChgDetailService.findPageBySql(page, itemChgDetail);
	}
	/**
	 * 构建合并临时JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goTmpJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTmpJqGrid(HttpServletRequest request,
			HttpServletResponse response,String params,String orderBy) {
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		JSONArray jsonArray = JSONArray.fromObject(params);  
		List<Map<String,Object>> list = (List)jsonArray;  
		Collections.sort(list, new ListCompareUtil(orderBy));
		if(page.isAutoCount()){
			page.setTotalCount(list.size());
		}
		page.setResult(list);		
		return new WebPage<Map<String,Object>>(page);

	}
	
}
