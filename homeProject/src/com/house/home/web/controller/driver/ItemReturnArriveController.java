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
import com.house.home.entity.driver.ItemReturnArrive;
import com.house.home.service.driver.ItemReturnArriveService;

@Controller
@RequestMapping("/admin/itemReturnArrive")
public class ItemReturnArriveController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemReturnArriveController.class);

	@Autowired
	private ItemReturnArriveService itemReturnArriveService;

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
			HttpServletResponse response, ItemReturnArrive itemReturnArrive) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturnArriveService.findPageBySql(page, itemReturnArrive);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemReturnArrive列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/driver/itemReturnArrive/itemReturnArrive_list");
	}
	/**
	 * ItemReturnArrive查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/driver/itemReturnArrive/itemReturnArrive_code");
	}
	/**
	 * 跳转到新增ItemReturnArrive页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemReturnArrive页面");
		ItemReturnArrive itemReturnArrive = null;
		if (StringUtils.isNotBlank(id)){
			itemReturnArrive = itemReturnArriveService.get(ItemReturnArrive.class, id);
			itemReturnArrive.setNo(null);
		}else{
			itemReturnArrive = new ItemReturnArrive();
		}
		
		return new ModelAndView("admin/driver/itemReturnArrive/itemReturnArrive_save")
			.addObject("itemReturnArrive", itemReturnArrive);
	}
	/**
	 * 跳转到修改ItemReturnArrive页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemReturnArrive页面");
		ItemReturnArrive itemReturnArrive = null;
		if (StringUtils.isNotBlank(id)){
			itemReturnArrive = itemReturnArriveService.get(ItemReturnArrive.class, id);
		}else{
			itemReturnArrive = new ItemReturnArrive();
		}
		
		return new ModelAndView("admin/driver/itemReturnArrive/itemReturnArrive_update")
			.addObject("itemReturnArrive", itemReturnArrive);
	}
	
	/**
	 * 跳转到查看ItemReturnArrive页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemReturnArrive页面");
		ItemReturnArrive itemReturnArrive = itemReturnArriveService.get(ItemReturnArrive.class, id);
		
		return new ModelAndView("admin/driver/itemReturnArrive/itemReturnArrive_detail")
				.addObject("itemReturnArrive", itemReturnArrive);
	}
	/**
	 * 添加ItemReturnArrive
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemReturnArrive itemReturnArrive){
		logger.debug("添加ItemReturnArrive开始");
		try{
			String str = itemReturnArriveService.getSeqNo("tItemReturnArrive");
			itemReturnArrive.setNo(str);
			itemReturnArrive.setLastUpdate(new Date());
			itemReturnArrive.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemReturnArrive.setExpired("F");
			this.itemReturnArriveService.save(itemReturnArrive);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemReturnArrive失败");
		}
	}
	
	/**
	 * 修改ItemReturnArrive
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemReturnArrive itemReturnArrive){
		logger.debug("修改ItemReturnArrive开始");
		try{
			itemReturnArrive.setLastUpdate(new Date());
			itemReturnArrive.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemReturnArriveService.update(itemReturnArrive);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemReturnArrive失败");
		}
	}
	
	/**
	 * 删除ItemReturnArrive
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemReturnArrive开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemReturnArrive编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemReturnArrive itemReturnArrive = itemReturnArriveService.get(ItemReturnArrive.class, deleteId);
				if(itemReturnArrive == null)
					continue;
				itemReturnArrive.setExpired("T");
				itemReturnArriveService.update(itemReturnArrive);
			}
		}
		logger.debug("删除ItemReturnArrive IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemReturnArrive导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReturnArrive itemReturnArrive){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemReturnArriveService.findPageBySql(page, itemReturnArrive);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid2")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid2(HttpServletRequest request,
			HttpServletResponse response, ItemReturnArrive itemReturnArrive) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturnArriveService.findArriveByNo(page, itemReturnArrive);
		return new WebPage<Map<String,Object>>(page);
	}
}
