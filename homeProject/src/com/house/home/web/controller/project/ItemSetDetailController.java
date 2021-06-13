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
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.service.basic.ItemSetDetailService;

@Controller
@RequestMapping("/admin/itemSetDetail")
public class ItemSetDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemSetDetailController.class);

	@Autowired
	private ItemSetDetailService itemSetDetailService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemSetDetail itemSetDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSetDetailService.findPageBySql(page, itemSetDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemSetDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemSetDetail/itemSetDetail_list");
	}
	/**
	 * ItemSetDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemSetDetail/itemSetDetail_code");
	}
	/**
	 * 跳转到新增ItemSetDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemSetDetail页面");
		ItemSetDetail itemSetDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemSetDetail = itemSetDetailService.get(ItemSetDetail.class, Integer.parseInt(id));
			itemSetDetail.setPk(null);
		}else{
			itemSetDetail = new ItemSetDetail();
		}
		
		return new ModelAndView("admin/project/itemSetDetail/itemSetDetail_save")
			.addObject("itemSetDetail", itemSetDetail);
	}
	/**
	 * 跳转到修改ItemSetDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemSetDetail页面");
		ItemSetDetail itemSetDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemSetDetail = itemSetDetailService.get(ItemSetDetail.class, Integer.parseInt(id));
		}else{
			itemSetDetail = new ItemSetDetail();
		}
		
		return new ModelAndView("admin/project/itemSetDetail/itemSetDetail_update")
			.addObject("itemSetDetail", itemSetDetail);
	}
	
	/**
	 * 跳转到查看ItemSetDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemSetDetail页面");
		ItemSetDetail itemSetDetail = itemSetDetailService.get(ItemSetDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/itemSetDetail/itemSetDetail_detail")
				.addObject("itemSetDetail", itemSetDetail);
	}
	/**
	 * 添加ItemSetDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemSetDetail itemSetDetail){
		logger.debug("添加ItemSetDetail开始");
		try{
			this.itemSetDetailService.save(itemSetDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemSetDetail失败");
		}
	}
	
	/**
	 * 修改ItemSetDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemSetDetail itemSetDetail){
		logger.debug("修改ItemSetDetail开始");
		try{
			itemSetDetail.setLastUpdate(new Date());
			itemSetDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemSetDetailService.update(itemSetDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemSetDetail失败");
		}
	}
	
	/**
	 * 删除ItemSetDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemSetDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemSetDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemSetDetail itemSetDetail = itemSetDetailService.get(ItemSetDetail.class, Integer.parseInt(deleteId));
				if(itemSetDetail == null)
					continue;
				itemSetDetail.setExpired("T");
				itemSetDetailService.update(itemSetDetail);
			}
		}
		logger.debug("删除ItemSetDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}



}
