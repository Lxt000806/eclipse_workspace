package com.house.home.web.controller.driver;

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
import com.house.home.entity.driver.ItemReturnDetail;
import com.house.home.service.driver.ItemReturnDetailService;

@Controller
@RequestMapping("/admin/itemReturnDetail")
public class ItemReturnDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemReturnDetailController.class);

	@Autowired
	private ItemReturnDetailService itemReturnDetailService;

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
			HttpServletResponse response, ItemReturnDetail itemReturnDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturnDetailService.findPageBySql(page, itemReturnDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemReturnDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/driver/itemReturnDetail/itemReturnDetail_list");
	}
	/**
	 * ItemReturnDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/driver/itemReturnDetail/itemReturnDetail_code");
	}
	/**
	 * 跳转到新增ItemReturnDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemReturnDetail页面");
		ItemReturnDetail itemReturnDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemReturnDetail = itemReturnDetailService.get(ItemReturnDetail.class, Integer.parseInt(id));
			itemReturnDetail.setPk(null);
		}else{
			itemReturnDetail = new ItemReturnDetail();
		}
		
		return new ModelAndView("admin/driver/itemReturnDetail/itemReturnDetail_save")
			.addObject("itemReturnDetail", itemReturnDetail);
	}
	/**
	 * 跳转到修改ItemReturnDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemReturnDetail页面");
		ItemReturnDetail itemReturnDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemReturnDetail = itemReturnDetailService.get(ItemReturnDetail.class, Integer.parseInt(id));
		}else{
			itemReturnDetail = new ItemReturnDetail();
		}
		
		return new ModelAndView("admin/driver/itemReturnDetail/itemReturnDetail_update")
			.addObject("itemReturnDetail", itemReturnDetail);
	}
	
	/**
	 * 跳转到查看ItemReturnDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemReturnDetail页面");
		ItemReturnDetail itemReturnDetail = itemReturnDetailService.get(ItemReturnDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/driver/itemReturnDetail/itemReturnDetail_detail")
				.addObject("itemReturnDetail", itemReturnDetail);
	}
	/**
	 * 添加ItemReturnDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemReturnDetail itemReturnDetail){
		logger.debug("添加ItemReturnDetail开始");
		try{
			this.itemReturnDetailService.save(itemReturnDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemReturnDetail失败");
		}
	}
	
	/**
	 * 修改ItemReturnDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemReturnDetail itemReturnDetail){
		logger.debug("修改ItemReturnDetail开始");
		try{
			itemReturnDetail.setLastUpdate(new Date());
			itemReturnDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemReturnDetailService.update(itemReturnDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemReturnDetail失败");
		}
	}
	
	/**
	 * 删除ItemReturnDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemReturnDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemReturnDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemReturnDetail itemReturnDetail = itemReturnDetailService.get(ItemReturnDetail.class, Integer.parseInt(deleteId));
				if(itemReturnDetail == null)
					continue;
				itemReturnDetail.setExpired("T");
				itemReturnDetailService.update(itemReturnDetail);
			}
		}
		logger.debug("删除ItemReturnDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemReturnDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReturnDetail itemReturnDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemReturnDetailService.findPageBySql(page, itemReturnDetail);
	}
	/**
	 * 跳转到修改ItemReturnDetail页面
	 * @return
	 */
	@RequestMapping("/goReturnInfo")
	public ModelAndView goReturnInfo(HttpServletRequest request, HttpServletResponse response, 
			ItemReturnDetail itemReturnDetail){
		logger.debug("跳转到修改ItemReturnDetail页面");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_returnDetail_returnInfo")
			.addObject("itemReturnDetail", itemReturnDetail);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReturnDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReturnDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReturnDetail itemReturnDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturnDetailService.findReturnDetailByNo(page, itemReturnDetail);
		return new WebPage<Map<String,Object>>(page);
	}
}
