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
import com.house.home.entity.insales.ItemBatchDetail;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.service.insales.ItemBatchDetailService;

@Controller
@RequestMapping("/admin/itemBatchDetail")
public class ItemBatchDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemBatchDetailController.class);

	@Autowired
	private ItemBatchDetailService itemBatchDetailService;

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
			HttpServletResponse response, ItemBatchDetail itemBatchDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBatchDetailService.findPageBySql(page, itemBatchDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemBatchDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemBatchDetail/itemBatchDetail_list");
	}
	/**
	 * ItemBatchDetail查询code 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,ItemBatchDetail itemBatchDetail) throws Exception {

		return new ModelAndView("admin/insales/itemBatchDetail/itemBatchDetail_code")
			.addObject("itemBatchHeader", itemBatchDetail);
	}
	/**
	 * 跳转到新增ItemBatchDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemBatchDetail页面");
		ItemBatchDetail itemBatchDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemBatchDetail = itemBatchDetailService.get(ItemBatchDetail.class, Integer.parseInt(id));
			itemBatchDetail.setPk(null);
		}else{
			itemBatchDetail = new ItemBatchDetail();
		}
		
		return new ModelAndView("admin/insales/itemBatchDetail/itemBatchDetail_save")
			.addObject("itemBatchDetail", itemBatchDetail);
	}
	/**
	 * 跳转到修改ItemBatchDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemBatchDetail页面");
		ItemBatchDetail itemBatchDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemBatchDetail = itemBatchDetailService.get(ItemBatchDetail.class, Integer.parseInt(id));
		}else{
			itemBatchDetail = new ItemBatchDetail();
		}
		
		return new ModelAndView("admin/insales/itemBatchDetail/itemBatchDetail_update")
			.addObject("itemBatchDetail", itemBatchDetail);
	}
	
	/**
	 * 跳转到查看ItemBatchDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemBatchDetail页面");
		ItemBatchDetail itemBatchDetail = itemBatchDetailService.get(ItemBatchDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/itemBatchDetail/itemBatchDetail_detail")
				.addObject("itemBatchDetail", itemBatchDetail);
	}
	/**
	 * 添加ItemBatchDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemBatchDetail itemBatchDetail){
		logger.debug("添加ItemBatchDetail开始");
		try{
			this.itemBatchDetailService.save(itemBatchDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemBatchDetail失败");
		}
	}
	
	/**
	 * 修改ItemBatchDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemBatchDetail itemBatchDetail){
		logger.debug("修改ItemBatchDetail开始");
		try{
			itemBatchDetail.setLastUpdate(new Date());
			itemBatchDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemBatchDetailService.update(itemBatchDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemBatchDetail失败");
		}
	}
	
	/**
	 * 删除ItemBatchDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemBatchDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemBatchDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemBatchDetail itemBatchDetail = itemBatchDetailService.get(ItemBatchDetail.class, Integer.parseInt(deleteId));
				if(itemBatchDetail == null)
					continue;
				itemBatchDetail.setExpired("T");
				itemBatchDetailService.update(itemBatchDetail);
			}
		}
		logger.debug("删除ItemBatchDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemBatchDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemBatchDetail itemBatchDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemBatchDetailService.findPageBySql(page, itemBatchDetail);
	}
	/**
	 *材料增减模块批次号对应的ItemBatchDetail明细
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemBatchDetailByIbdNo")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemBatchDetailByIbdNo(HttpServletRequest request, 
			HttpServletResponse response,ItemBatchDetail itemBatchDetail){
		if(!StringUtils.isNotBlank(itemBatchDetail.getIbdno())){
			return null;
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBatchDetailService.getItemBatchDetailByIbdNo(page, itemBatchDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *材料增减模块选择材料下单批次明细
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goItemBatchDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemBatchDetailJqGrid(HttpServletRequest request, 
			HttpServletResponse response,ItemBatchDetail itemBatchDetail){
		if(!StringUtils.isNotBlank(itemBatchDetail.getIbdno())){
			return null;
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBatchDetailService.getItemBatchDetailJqGrid(page, itemBatchDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/itemChgImportingJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> itemChgImportingJqGrid(HttpServletRequest request,
	        HttpServletResponse response, ItemBatchDetail itemBatchDetail) {
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    
	    if (StringUtils.isNotBlank(itemBatchDetail.getIbdno())) {            
	        itemBatchDetailService.getItemChgImportingJqGrid(page, itemBatchDetail);
        }
        
        return new WebPage<Map<String,Object>>(page);
	}

}
